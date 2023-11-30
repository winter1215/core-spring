package com.minis.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanFactory 的简单实现
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
                    if ("string".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType())) {
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    } else if ("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        // 转换成 Integer
                        paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                    } else if ("int".equals(argumentValue.getType())) {
                        paramTypes[i] = int.class;
                        // 转换成 int
                        paramValues[i] = Integer.parseInt((String) argumentValue.getValue());
                    } else {
                        // 默认为 string
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    }

                    // 按照特定的参数类型寻找构造器,并实例化
                    try {
                        constructor = clz.getConstructor(paramTypes);
                        obj = constructor.newInstance(paramValues);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                // 如果没有配置构造器参数，直接实例化
                try {
                    obj = clz.newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (ClassNotFoundException e) {
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
                // todo: 这里 createBean 是不是好一些
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
