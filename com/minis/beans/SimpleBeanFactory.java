package com.minis.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanFactory 的简单实现
 *
 * @author winter
 * @create 2023-11-24 14:29
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private List<String> beanDefinitionNames = new ArrayList<>();

    public SimpleBeanFactory() {
    }

    // 需要时反射加载对象
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿取实例
        Object singleton = this.getSingleton(beanName);
        if (singleton == null) {
            // 创建 bean 并实现属性注入
            BeanDefinition bd = beanDefinitionMap.get(beanName);
            singleton = createBean(bd);
            // 注册单例对象进容器
            this.registerSingleton(beanName, singleton);
            if (bd.getInitMethodName() != null) {
                // todo: invoke init method
            }
        }

        // 创建 bean 失败，抛出异常
        if (singleton == null) {
            throw new BeansException("bean is null");
        }
        return singleton;
    }

    public Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Constructor<?> constructor = null; // 构造器，用于指定参数列表的实例化成对象
        Object obj = null; // 实例化后的对象
        try {
            clz = Class.forName(beanDefinition.getClassName());
            // 处理构造器参数
            ArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
            // 如果构造器参数不为空
            if (!argumentValues.isEmpty()) {
                // 参数的类型数组，对应下面的参数的 value 数组（用于寻找对应的构造器）
                Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
                Object[] paramValues = new Object[argumentValues.getArgumentCount()];
                // 处理每一个参数进行：类型判断(暂时只匹配3个类型)
                for (int i = 0; i < argumentValues.getArgumentCount(); i++) {
                    ArgumentValue argumentValue = argumentValues.getIndexedArgumentValue(i);
                    // 根据用户配置的类型对比（用户配置的才是他认为的类型）
                    String aType = argumentValue.getType();
                    if ("String".equals(aType) || "java.lang.String".equals(aType)) {
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    } else if ("Integer".equals(aType) || "java.lang.Integer".equals(aType)) {
                        paramTypes[i] = Integer.class;
                        // 转换成 Integer
                        paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                    } else if ("int".equals(aType)) {
                        paramTypes[i] = int.class;
                        // 转换成 int
                        paramValues[i] = Integer.parseInt((String) argumentValue.getValue());
                    } else {
                        // 默认为 string
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    }
                }
                // 按照特定的参数类型寻找构造器,并实例化
                try {
                    constructor = clz.getConstructor(paramTypes);
                    obj = constructor.newInstance(paramValues);
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                         IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            } else {
                // 如果没有配置构造器参数，直接实例化
                try {
                    obj = clz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            // 处理属性的 setter 注入(反射注入)
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            if (!propertyValues.isEmpty()) {
                for (int i = 0; i < propertyValues.size(); i++) {
                    PropertyValue propertyValue = propertyValues.getPropertyValues()[i];
                    String pType = propertyValue.getType();
                    String pName = propertyValue.getName();
                    Object pValue = propertyValue.getValue();
                    Class<?>[] paramTypes = new Class[1];
                    if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                        paramTypes[0] = String.class;
                    } else if ("Integer".equals(pName) || "java.lang.Integer".equals(pName)) {
                        paramTypes[0] = Integer.class;
                    } else if ("int".equals(pType)) {
                        paramTypes[0] = int.class;
                    } else {
                        // 默认为 string
                        paramTypes[0] = String.class;
                    }
                    // 组装 values
                    Object[] paramValue = new Object[1];
                    paramValue[0] = pValue;
                    // 根据 setter 规范找到 Method,再调用它实现属性注入 (set + name首字母大写)
                    String setterMethodName = "set" + pName.substring(0,1).toUpperCase() + pName.substring(1);
                    Method method = clz.getMethod(setterMethodName, paramTypes);
                    // 调用 setter
                    method.invoke(obj, paramValue);
                }
            }

            return obj;
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean containsBeans(String beanName) {
        return super.containsSingleton(beanName);
    }

    @Override
    public boolean isSingleton(String beanName) {
        return beanDefinitionMap.get(beanName).isSingleton();
    }

    @Override
    public boolean isPrototype(String beanName) {
        return beanDefinitionMap.get(beanName).isPrototype();
    }

    @Override
    public Class<?> getType(String beanName) {
        return beanDefinitionMap.get(beanName).getClass();
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanName, beanDefinition);
        this.beanDefinitionNames.add(beanName);
        // 注册时如果不是懒加载，就直接实例化
        if (!beanDefinition.isLazyInit()) {
            try {
                // 除了 createBean, getBean 还将 bean 放进 ioc
                getBean(beanName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void removeBeanDefinition(String beanName) {

    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }
}
