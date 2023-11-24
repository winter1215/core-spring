package com.minis.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的单例注册中心(真正的工厂实现)
 * @author winter
 * @create 2023-11-24 15:36
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{
    protected List<String> beanNames = new ArrayList<>();
    // 注意: 使用的并发安全的 HashMap
    protected Map<String, Object> singletons = new ConcurrentHashMap<>(256);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletons) {
            this.beanNames.add(beanName);
            this.singletons.put(beanName, singletonObject);
        }

    }

    @Override
    public Object getSingleton(String beanName) {
        return singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return beanNames.toArray(new String[0]);
    }
}
