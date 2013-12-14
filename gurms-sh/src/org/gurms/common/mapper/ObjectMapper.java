package org.gurms.common.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.dozer.DozerBeanMapper;

/**
 * 对象转换工具类.
 * 1.封装Dozer, 深度转换对象到对象
 * 2.封装Apache Commons BeanUtils, 将字符串转换为对象.
 * 
 */
public class ObjectMapper {
	
	private ObjectMapper(){}

	/**
	 * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
	 */
	private static DozerBeanMapper dozer = new DozerBeanMapper();

	public static DozerBeanMapper getDozer(){
		return dozer;
	}
	
	/**
	 * 基于Dozer转换对象的类型.
	 */
	public static <T> T map(Object source, Class<T> destinationClass) {
		return dozer.map(source, destinationClass);
	}

	public static void copy(Object source, Object destination) {
		dozer.map(source, destination);
	}

	/**
	 * 基于Dozer转换Collection中对象的类型.
	 */
	public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
		List<T> destinationList = new ArrayList();
		for (Object sourceObject : sourceList) {
			T destinationObject = dozer.map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}
		return destinationList;
	}

	/**
	 * String->Object.
	 * 
	 * @param value 待转换的字符串.
	 * @param toType 转换目标类型.
	 */
	public static Object fromString(String value, Class<?> toType) {
		return ConvertUtils.convert(value, toType);
	}

}