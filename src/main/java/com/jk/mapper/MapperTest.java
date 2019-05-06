package com.jk.mapper;

import com.jk.model.Pl;
import com.jk.model.Tree;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MapperTest {
    @Select("SELECT * FROM MENUTREE WHERE PID = #{value}")
     List<Tree> getalchi(String pid);

//    Integer queryPL(@Param("id")String id);

    List<Pl> findPL(@Param("id") String id,@Param("start") Integer start,@Param("rows") Integer rows, @Param("comments")String comments, @Param("startDate")String startDate, @Param("endDate")String endDate);
    Integer queryPL(@Param("id")String id, @Param("comments")String comments, @Param("startDate")String startDate, @Param("endDate")String endDate);

    void addGoods(Pl pl);
//
}
