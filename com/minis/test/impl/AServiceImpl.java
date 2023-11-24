package com.minis.test.impl;

import com.minis.test.AService;

/**
 * @author winter
 * @create 2023-11-24 11:10
 */
public class AServiceImpl implements AService {
    private String property1;

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    @Override
    public void sayHello() {
        System.out.println("A service 1 say hello");
    }
}
