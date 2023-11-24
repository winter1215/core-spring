package com.minis.beans;

/**
 *  <beans>
 *  <bean id = "xxxid" class = "com.minis.xxxclass"></bean>
 *  </beans>
*/
public class BeanDefinition {
    /**
    * beanName
    */
    private String id;
    /**
    * bean 的类名
    */
    private String className;

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
