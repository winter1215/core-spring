package com.minis.test.impl;

import com.minis.test.AService;

/**
 * @author winter
 * @create 2023-11-24 11:10
 */
public class AServiceImpl implements AService {
    private String property1;
    private String property2;
    private String name;
    private int level;

    /**
    * 测试构造方法的属性注入
    */
    public AServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
    }

    /**
    * 测试 setter 方法注入
    */
    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    @Override
    public void sayHello() {
        System.out.println("A service 1 say hello");
    }
}
