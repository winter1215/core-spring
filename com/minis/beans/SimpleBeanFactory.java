package com.minis.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanFactory 的简单实现
 * @author winter
 * @create 2023-11-24 14:29
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private List<String> beanDefinitionNames = new ArrayList<>();

    public SimpleBeanFactory() {
    }

    // 需要时反射加载对象
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿取实例
        Object singleton = this.getSingleton(beanName);
        return singleton;
    }

    @Override
    public boolean containsBeans(String beanName) {
        return super.containsSingleton(beanName);
    }

    @Override
    public boolean isSingleton(String beanName) {
        return beanDefinitionMap.get(beanName).isSingleton();
    }

    @Override
    public boolean isPrototype(String beanName) {
        return beanDefinitionMap.get(beanName).isPrototype();
    }

    @Override
    public Class<?> getType(String beanName) {
        return beanDefinitionMap.get(beanName).getClass();
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanName, beanDefinition);
        this.beanDefinitionNames.add(beanName);
        // 注册时如果不是懒加载，就直接实例化
        if (!beanDefinition.isLazyInit()) {
            try {
                // todo: 这里 createBean 是不是好一些
                getBean(beanName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void removeBeanDefinition(String beanName) {

    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }
}
