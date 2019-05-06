package com.jk.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="cnm")
@Data
public class Goods {
    private String gid;
    private String goodsName;
    private Integer goodsCount;
    private String goodsImg;
}
