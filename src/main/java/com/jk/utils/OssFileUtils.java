package com.jk.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class OssFileUtils {
    private static String endpoint = "oss-cn-beijing.aliyuncs.com";

    private static String accessKeyId = "LTAICcx1Q8fdzpuF";

    private static String accessKeySecret = "MaDCmQV7Km0RaWqthEt62PmVytVjZr";

    private static String bucketName = "android19";

    /**
     * 功能描述:
     *
     * @param:[objectKey, multipartFile 文件的新名称]
     * @return:java.lang.String
     * @date:2018/10/14 15:46
     **/
    public static String uploadFile(MultipartFile multipartFile)
            throws OSSException, ClientException, FileNotFoundException {
        String toString = String.valueOf(new Random().nextInt(899999) + 100000)+".png";
        // 创建OSSClient的实例
        OSSClient ossClient  = new OSSClient(endpoint,accessKeyId,accessKeySecret);

        StringBuffer sb = new StringBuffer();
        // 上传的文件不是空，并且文件的名字不是空字符串
        if (multipartFile.getSize() != 0 && !"".equals(multipartFile.getName())) {
            ObjectMetadata om = new ObjectMetadata();
            om.setContentLength(multipartFile.getSize());
            // 设置文件上传到服务器的名称
            om.addUserMetadata("filename", toString);
            try {
                ossClient.putObject(bucketName, toString, new ByteArrayInputStream(multipartFile.getBytes()), om);
            } catch (IOException e) {

            }
        }
        // 设置这个文件地址的有效时间
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(bucketName, toString, expiration).toString();
        return url;
    }

    public static OSSObject downLoadImage(String fileName) {
        //获取资源中文件的名称
        String fileNames = fileName.substring(45, 55);
        //打开到此 URL 的连接并返回一个用于从该连接读入的 InputStream。
        OSSClient ossClient  = new OSSClient(endpoint,accessKeyId,accessKeySecret);
        //设置存储时间
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        //进行条件拼接
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileNames, HttpMethod.GET);
        // 设置过期时间。
        request.setExpiration(expiration);
        // 生成签名URL（HTTP GET请求）。
        URL signedUrl = ossClient.generatePresignedUrl(request);
        // 使用签名URL发送请求。
        Map<String, String> customHeaders = new HashMap<String, String>();
        // 添加GetObject请求头。
        //customHeaders.put("Range", "bytes=100-1000");
        OSSObject object = ossClient.getObject(signedUrl, customHeaders);
        return object;
    }
}
