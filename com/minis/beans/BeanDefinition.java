package com.minis.beans;

/**
 *  <beans>
 *  <bean id = "xxxid" class = "com.minis.xxxclass"></bean>
 *  </beans>
*/
public class BeanDefinition {
    private final String SCOPE_SINGLETON = "singleton";
    private final String SCOPE_PROTOTYPE = "prototype";
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

    /**
    * 是否单例(默认单例)
    */
    private String scope = SCOPE_SINGLETON;
    /**
    * 懒加载
    */
    private boolean lazyInit = false;
    /**
    * 当前 bean 依赖的其他 beans(会等待依赖的 beans 初始化完毕)
    */
    private String[] dependsOn;
    /**
    * 实例化 bean 时需调用的初始化 bean 方法
    */
    private String initMethodName;
    /**
    * 注入时的参数列表
    */
    private ArgumentValues argumentValues;
    /**
    * 注入时的属性列表
    */
    private PropertyValues propertyValues;

    public BeanDefinition(String id, String className, boolean lazyInit, String[] dependsOn, String initMethodName, ArgumentValues argumentValues, PropertyValues propertyValues) {
        this.id = id;
        this.className = className;
        this.lazyInit = lazyInit;
        this.dependsOn = dependsOn;
        this.initMethodName = initMethodName;
        this.argumentValues = argumentValues;
        this.propertyValues = propertyValues;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String[] getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String[] dependsOn) {
        this.dependsOn = dependsOn;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public ArgumentValues getArgumentValues() {
        return argumentValues;
    }

    public void setArgumentValues(ArgumentValues argumentValues) {
        this.argumentValues = argumentValues;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
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
