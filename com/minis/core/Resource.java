package com.minis.core;

import java.util.Iterator;

/**
 * 抽象出来的外部配置接口: 我们把所有的外部配置都看作 Resource (eg: beans.xml) <br/>
 * 出来 xml, 也可以扩展出出 网络,数据库中获取配置
 * @author winter
 * @create 2023-11-24 11:28
 */
public interface Resource extends Iterator<Object> {}
