package com.jk.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@Document(collection="t_long")
public class LoginBean {
    private String id;
    private String methodName;  //方法名
    private String className;	//类名字（方法属于哪个类）
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createtime;	//访问方法时间
    private Integer userid;	   //访问用户
    private String 	param;	   //请求参数    ，
    private String returnValue; //返回值


    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date staTime;	//开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;  //结束时间
}
