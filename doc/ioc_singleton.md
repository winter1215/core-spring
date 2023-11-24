## 目前进度
1. 单例 2. 事件 3. 注入 

**BeanFactory 与 SingletonBeanRegister**

BeanFactory 是 bean 工厂的抽象,抽象出来 bean 的注册和获取等规范,但在目前阶段
存放 bean 的容器并不在 BeanFactory的实现类: SimpleBeanFactory 而是 SingletonBeanRegister
的实现类: DefaultSingletonBeanRegister 这个单例对象的注册中心.

**注意:**

``public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {}
``

SimpleBeanFactory  继承了 DefaultSingletonBeanRegistry 实现了 BeanFactory 接口,就相当于 SimpleBeanFactory 拥有了
工厂的全部功能.