package com.minis.beans;

/**
 * 最基础的容器: 拆解出来的 BeanFactory 概念,用于管理 Beans 的容器
 * @author winter
 * @create 2023-11-24 11:25
 */
public interface BeanFactory {
    /**
    * 获取 bean
    */
    Object getBean(String beanName) throws BeansException;
    /**
    * 注册 beanDefinition
    */
    void registerBeanDefinition(BeanDefinition beanDefinition);
}