package com.minis.beans;

/**
 * xml 属性注入的 属性映射 bean,还有一个参数映射 bean(构造器里就是参数)
 * @author winter
 * @create 2023-11-24 17:31
 */
public class PropertyValue {
    // todo: 这个会在什么时候用?
    private String type;
    private String name;
    private Object value;

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public PropertyValue(String type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }
}
