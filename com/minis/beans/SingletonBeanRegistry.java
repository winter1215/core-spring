package com.minis.beans;

/**
 * 管理单例对象的工厂规范
 * @author winter
 * @create 2023-11-24 15:32
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);
    Object getSingleton(String beanName);
    boolean containsSingleton(String beanName);
    String[] getSingletonNames();
}
