package com.jk.model;

import lombok.Data;

import java.util.List;

@Data
public class Tree {
   private String id;

   private String pId;

   private String text;

   private String url;

   private List<Tree> children;
}
