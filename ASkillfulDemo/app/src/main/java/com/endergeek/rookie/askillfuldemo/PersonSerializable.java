package com.endergeek.rookie.askillfuldemo;

import java.io.Serializable;

/**
 * Created by wangsenhui on 11/10/16.
 * 实现Serializable接口，这样所有对象都是可以序列化(将一个对象转换成可存储或可传输的状态)的，实现见MainActivity
 * 效率较低
 */
public class PersonSerializable implements Serializable{

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
