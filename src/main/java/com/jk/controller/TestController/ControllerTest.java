package com.jk.controller.TestController;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.model.OSSObject;
import com.jk.model.Pl;
import com.jk.model.Tree;
import com.jk.service.ServiceTest;
import com.jk.utils.OssFileUtils;
import com.sun.deploy.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("demo")
public class ControllerTest {
    @Autowired
    private ServiceTest serviceTest;
    @Autowired
    private JedisPool jedisPool;
    /*树*/
    @RequestMapping("queryTree")
    @ResponseBody
    public String queryTree(){
        Jedis resource = jedisPool.getResource();
        String tree = resource.get("Tree");
        if (tree != null && tree != "") {
            resource.close();
            return tree;
        } else {
            List<Tree> treeAll = serviceTest.queryTree();
            String jsonString = JSON.toJSONString(treeAll);
            resource.set("Tree", jsonString);
            resource.close();
            return jsonString;
        }
    }
    @RequestMapping("queryGoods")
    @ResponseBody
    public String queryGoods(Integer page, Integer rows){
        HashMap<String, Object> map = serviceTest.queryGoods(page, rows);
        String jsonString = JSON.toJSONString(map);
        Jedis resource = jedisPool.getResource();
        resource.set("Goods",jsonString);
        resource.close();
        return jsonString;
    }
    @RequestMapping("tohx")
    @ResponseBody
    public HashMap<String,Object> queryPL(String id, Integer page, Integer rows, Pl pl){

        return serviceTest.queryPL(id,page,rows,pl);
    }
    @RequestMapping("addGoods")
    @ResponseBody
    public String addGoods(Pl pl){
        System.out.println(pl);
        serviceTest.addGoods(pl);
        return null;
    }







    //图片上传的
    @RequestMapping("upload")
    @ResponseBody
    public HashMap<String,Object> fileUpload(MultipartFile imgId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            //调方法
            String uploadFile = OssFileUtils.uploadFile(imgId);
            //返会给前台
            String substring = uploadFile.substring(44, 55);
            hashMap.put("imgid",uploadFile);
            hashMap.put("imgname",substring);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return hashMap;
    }
    //图片下载
    @RequestMapping("flieimgByID")
    @ResponseBody
    public  String downFile(String imgId, HttpServletResponse response) {
        //调用工具类
        OSSObject ossObject = OssFileUtils.downLoadImage(imgId);
        //给本地下载的时候生成文件名
        String fileName = new Date().toString().substring(0,5)+".png";
        //判断 返会文件件不为空
        if (ossObject != null) {
            //HTTPServletResponse 相应流
            InputStream inputStream = ossObject.getObjectContent();
            int buffer = 1024 * 10;
            byte data[] = new byte[buffer];
            try {
                response.setContentType("application/octet-stream");
                // 文件名可以任意指定
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));//将文件名转为ASCLL编码
                int read;
                //迭代器
                while ((read = inputStream.read(data)) != -1) {
                    response.getOutputStream().write(data, 0, read);
                }
                response.getOutputStream().flush();//刷新
                response.getOutputStream().close();//关闭
                ossObject.close();//关闭
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
}
