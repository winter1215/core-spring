package com.minis.context;

import com.minis.beans.*;
import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;

/**
 * 拆解过后的 context 职责更加单一,负责容器的启动,组织各个组件
 * @author winter
 * @create 2023-11-24 14:44
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
    BeanFactory beanFactory;
    public ClassPathXmlApplicationContext(String fileName) {
        // 容器启动的过程
        // 初始化 BeanFactory
        beanFactory = new SimpleBeanFactory();
        // 加载配置文件资源,转化成可迭代的产物
        Resource resource = new ClassPathXmlResource(fileName);
        // 解析配置文件,转化成 BeanDefinition
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader((SimpleBeanFactory)beanFactory);
        reader.loadBeanDefinitions(resource);
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return beanFactory.getBean(beanName);
    }

    @Override
    public boolean containsBeans(String beanName) {
        return beanFactory.containsBeans(beanName);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        beanFactory.registerBean(beanName,obj);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        beanFactory.registerBeanDefinition(beanDefinition);
    }
}
