package com.minis.context;

import com.minis.beans.BeanDefinition;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1. 解析 xml 成 BeanDefinition <br/>
 * 2. 将 BeanDefinition 反射成对象 <br/>
 * 3. 暴露出一个获取 Bean 的方法
 * @author winter
 * @create 2023-11-23 下午8:54
 */
public class ClassPathXmlApplicationContext_bank {
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    /**
    * core
    */
    private Map<String, Object> singletons = new HashMap<>();

    // todo: 什么时候被调用
    public ClassPathXmlApplicationContext_bank(String fileName) {
        readXml(fileName);
        instanceBeans();
    }

    /**
    * 解析 xml 成 BeanDefinition
    */
    private void readXml(String fileName) {
        SAXReader saxReader = new SAXReader();
        // 用 classLoader 加载类路径资源文件： xml
        URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
        try {
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            // 处理配置文件中的每一个 bean 节点
            for (Element element : (List<Element>) rootElement.elements()) {
                String beanID = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);

                beanDefinitions.add(beanDefinition);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
    * 将 BeanDefinition 反射成对象
    */
    private void instanceBeans() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Object instance = null;
            try {
                // 通过类名反射成对象
                instance = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (InstantiationException e) {
                // todo: 这些是异常是该抛出还是直接处理?
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // 将对象放入单例池
            singletons.put(beanDefinition.getId(), instance);
        }
    }

    /**
    * 容器对外获取实例的方法,会组件演化成核心方法
    */
    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }
}
