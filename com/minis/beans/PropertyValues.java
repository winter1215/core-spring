package com.minis.beans;

import java.util.ArrayList;
import java.util.List;

/**
* 一个 bean 中可能有很多参数,这区别于 PropertyValue
*/
public class PropertyValues {
	/**
	* 属性列表
	*/
	private final List<PropertyValue> propertyValueList;

	public PropertyValues() {
		this.propertyValueList = new ArrayList<PropertyValue>(10);
	}

	public List<PropertyValue> getPropertyValueList() {
		return this.propertyValueList;
	}

	/**
	* 获取数量
	*/
	public int size() {
		return this.propertyValueList.size();
	}

	/**
	* 添加
	*/
	public void addPropertyValue(PropertyValue pv) {
		this.propertyValueList.add(pv);
	}

	public void addPropertyValue(String propertyType, String propertyName, Object propertyValue) {
		addPropertyValue(new PropertyValue(propertyType, propertyName, propertyValue));
	}

	/**
	* 移除
	*/
	public void removePropertyValue(PropertyValue pv) {
		this.propertyValueList.remove(pv);
	}

	public void removePropertyValue(String propertyName) {
		this.propertyValueList.remove(getPropertyValue(propertyName));
	}


	/**
	* 数组形式返回
	*/
	public PropertyValue[] getPropertyValues() {
		return this.propertyValueList.toArray(new PropertyValue[0]);
	}

	public PropertyValue getPropertyValue(String propertyName) {
		for (PropertyValue pv : this.propertyValueList) {
			if (pv.getName().equals(propertyName)) {
				return pv;
			}
		}
		return null;
	}

	public Object get(String propertyName) {
		PropertyValue pv = getPropertyValue(propertyName);
		return (pv != null ? pv.getValue() : null);
	}

	public boolean contains(String propertyName) {
		return (getPropertyValue(propertyName) != null);
	}

	public boolean isEmpty() {
		return this.propertyValueList.isEmpty();
	}


}