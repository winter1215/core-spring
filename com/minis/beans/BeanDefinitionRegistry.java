package com.minis.beans;

/**
 * BeanDefinition 的注册器，类似 SingleTonBean 注册器，将储存的职责抽离出来
 * @author winter
 * @create 2023-11-26 下午4:07
 */
public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
    void removeBeanDefinition(String beanName);
    BeanDefinition getBeanDefinition(String beanName);
    boolean containsBeanDefinition(String beanName);
}
