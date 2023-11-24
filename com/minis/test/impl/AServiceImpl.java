package com.minis.test.impl;

import com.minis.test.AService;

/**
 * @author winter
 * @create 2023-11-24 11:10
 */
public class AServiceImpl implements AService {
    @Override
    public void sayHello() {
        System.out.println("A service 1 say hello");
    }
}
