package com.minis.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanFactory 的简单实现
 * @author winter
 * @create 2023-11-24 14:29
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    public SimpleBeanFactory() {
    }

    @Override
    // 需要时反射加载对象
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿取实例
        Object singleton = this.getSingleton(beanName);
        // 还未加载则反射加载 todo: 线程安全
        if (singleton == null) {
            // 获取 bean 定义
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if (beanDefinition == null) {
                throw new BeansException("bean has not registered: " + beanName);
            }

            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            this.registerBean(beanDefinition.getId(), singleton);
        }
        return singleton;
    }

    @Override
    public boolean containsBeans(String beanName) {
        return super.containsSingleton(beanName);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        super.registerSingleton(beanName, obj);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        beanDefinitions.put(beanDefinition.getId(), beanDefinition);
    }
}
