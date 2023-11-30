package com.minis.beans;

import com.minis.core.Resource;
import org.dom4j.Element;

import java.util.List;


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
            // 获取 property 的标签
            List<Element> propertyElements = element.elements("property");
            // 将 property 配置项实例化成 bd 的一部分
            PropertyValues PVS = new PropertyValues();
            for(Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                PVS.addPropertyValue(pType, pName, pValue);
            }
            beanDefinition.setPropertyValues(PVS);

            // 将 constructor_args 配置项实例化成 bd 的一部分
            List<Element> constructorElements = element.elements("constructor_arg");
            // 将 property 配置项实例化成 bd 的一部分
            ArgumentValues AVS = new ArgumentValues();
            for(Element e : constructorElements) {
                String aType = e.attributeValue("type");
                String aName = e.attributeValue("name");
                String aValue = e.attributeValue("value");
                AVS.addArgumentValue(new ArgumentValue(aType, aName, aValue));
            }
            beanDefinition.setConstructorArgumentValues(AVS);
            // 注册 beanDefinition
            simpleBeanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
    }
}
