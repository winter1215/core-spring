package com.minis.context;

/**
 * 监听容器启动的事件监听发布者
 * @author winter
 * @create 2023-11-24 17:13
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
