package com.jk.service;

import com.jk.model.Goods;
import com.jk.model.Pl;
import com.jk.model.Tree;

import java.util.HashMap;
import java.util.List;

public interface ServiceTest {
    List<Tree> queryTree();


    HashMap<String, Object> queryGoods(Integer page, Integer rows);

    HashMap<String, Object> queryPL(String id, Integer page, Integer rows, Pl pl);

    void addGoods(Pl pl);
}
