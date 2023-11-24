package com.minis.context;

import java.util.EventObject;

/**
 * 事件对象
 * @author winter
 * @create 2023-11-24 17:14
 */
public class ApplicationEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
