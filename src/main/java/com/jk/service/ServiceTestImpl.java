package com.jk.service;

import com.jk.mapper.MapperTest;
import com.jk.model.Goods;
import com.jk.model.Pl;
import com.jk.model.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class ServiceTestImpl implements ServiceTest {
    @Autowired
    private MapperTest mapperTest;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Tree> queryTree() {
        String pid = "0";
        List<Tree> list = gettre(pid);
        return list;
    }


    private List<Tree> gettre(String pid) {
        List<Tree> list = mapperTest.getalchi(pid);
        for (Tree tree : list) {
            String id = tree.getId();
            List<Tree> gettre = gettre(id);
            tree.setChildren(gettre);
        }
        return list;
    }

    @Override
    //gid
    public HashMap<String, Object> queryGoods(Integer page, Integer rows) {
//        Goods goods1 = new Goods();
//        UUID uuid = UUID.randomUUID();
//        goods1.setGid("s"+uuid);
//        goods1.setGoodsCount(5);
//        goods1.setGoodsImg("tttt");
//        goods1.setGoodsName("cccc");
//        mongoTemplate.save(goods1);
        Query query = new Query();
        long count = mongoTemplate.count(query, Goods.class);
        int start = (page - 1) * rows;
        query.skip(start).limit(rows);
        List<Goods> goods = mongoTemplate.find(query, Goods.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", count);
        map.put("rows", goods);
        return map;
    }

    @Override
    public HashMap<String, Object> queryPL(String id, Integer page, Integer rows,Pl pl) {
        HashMap<String, Object> map = new HashMap<>();
        int start = (page - 1) * rows;
        Integer count = mapperTest.queryPL(id,pl.getComments(),pl.getStartDate(),pl.getEndDate());
        List<Pl> list = mapperTest.findPL(id,start,rows,pl.getComments(),pl.getStartDate(),pl.getEndDate());
        map.put("total",count);
        map.put("rows",list);
        return map;
    }

    @Override
    public void addGoods(Pl pl) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = now.format(formatter);
        System.out.println("日期"+format);
        pl.setCommentDate(format);
        mapperTest.addGoods(pl);
    }
}
