package com.endergeek.rookie.alistviewdemo;

/**
 * Created by wangsenhui on 10/14/16.
 * ListView 适配类型
 */
public class Fruit {

    private String fruitItemName;

    private int fruitImageId;

    public Fruit(String name, int id) {
        this.fruitItemName = name;
        this.fruitImageId = id;
    }

    public String getFruitName() {
        return fruitItemName;
    }

    public int getFruitImageId() {
        return fruitImageId;
    }
}
