package com.minis.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BeanFactory 的简单实现
 * @author winter
 * @create 2023-11-24 14:29
 */
public class SimpleBeanFactory implements BeanFactory{
    List<BeanDefinition> beanDefinitions = new ArrayList<>();
    List<String> beanNames = new ArrayList<>();
    Map<String, Object> singletons = new HashMap<>();

    public SimpleBeanFactory() {
    }

    @Override
    // 需要时反射加载对象
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿取实例
        Object singleton = singletons.get(beanName);
        // 还未加载则反射加载
        if (singleton == null) {
            int i = beanNames.indexOf(beanName);
            if (i == -1) {
                throw new BeansException("bean has not registered: " + beanName);
            }
            // 获取 bean 定义
            BeanDefinition beanDefinition = beanDefinitions.get(i);

            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            singletons.put(beanDefinition.getId(), singleton);
        }
        return singleton;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }
}
