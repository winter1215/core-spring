package com.minis.core;

import com.minis.core.Resource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * xml 的 Resource 实现,拥有从 xml 中获取配置文件的能力
 * @author winter
 * @create 2023-11-24 11:30
 */
public class ClassPathXmlResource implements Resource {
    Iterator<Element> elementIterator;
    public ClassPathXmlResource(String fileName) {
        URL resource = this.getClass().getClassLoader().getResource(fileName);
        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(resource);
            Element rootElement = document.getRootElement();
            // 暴露出 xml 迭代器
            elementIterator = rootElement.elementIterator();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasNext() {
        return elementIterator.hasNext();
    }

    @Override
    public Object next() {
        return elementIterator.next();
    }
}
