package com.minis.beans;

import com.minis.core.Resource;
import org.dom4j.Element;


/**
 * 组装解析好的 xml
 * @author winter
 * @create 2023-11-24 11:52
 */
public class XmlBeanDefinitionReader {
    SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }

    /**
    * 这里就能窥见抽象,接口的优势: 只要外部资源配置都实现 Resource 接口的规范,这里就能正常将其解析
    */
    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String className = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, className);
            // 注册 beanDefinition
            simpleBeanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
