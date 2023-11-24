package com.minis;

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
public class ClassPathXmlApplicationContext {
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    /**
    * core
    */
    private Map<String, Object> singletons = new HashMap<>();

    public ClassPathXmlApplicationContext(String fileName) {
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

    }

    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }
}
