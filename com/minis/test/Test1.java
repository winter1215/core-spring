package com.minis.test;

import com.minis.context.ClassPathXmlApplicationContext_bank;

/**
 * @author winter
 * @create 2023-11-24 11:13
 */
public class Test1 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext_bank ctx = new ClassPathXmlApplicationContext_bank("beans.xml");
        AService aService = (AService) ctx.getBean("AService");
        aService.sayHello();
    }
}
