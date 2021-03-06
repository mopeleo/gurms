package org.gurms.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.mapper.ObjectMapper;

/**
 * 与具体ORM实现无关的属性过滤条件封装类, 主要记录页面中简单的搜索过滤条件.
 * 
 */
public class PropertyFilter {

	/** 多个属性间OR关系的分隔符. */
	public static final String OR_SEPARATOR = "_OR_";

	/** 属性比较类型. */
	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE, NE, IN, NULL, NOTNULL;
	}

	/** 属性数据类型. */
	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class);

		private Class<?> clazz;

		private PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	private MatchType matchType = null;
	private Object matchValue = null;

	private Class<?> propertyClass = null;
	private String[] propertyNames = null;

	public PropertyFilter() {
	}

	/**
	 * @param filterName 比较属性字符串,含待比较的比较类型、属性值类型及属性列表. 
	 *                   eg. LIKES_NAME_OR_LOGIN_NAME
	 * @param value 待比较的值.
	 */
	public PropertyFilter(final String filterName, final String value) {
		String firstPart = StringUtils.substringBefore(filterName, "_");
		String matchTypeCode = StringUtils.substring(firstPart, 0, firstPart.length() - 1);
		String propertyTypeCode = StringUtils.substring(firstPart, firstPart.length() - 1, firstPart.length());
		try {
			matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("匹配类型" + matchType + "没有按规则编写,无法得到属性比较类型.", e);
		}

		try {
			propertyClass = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("数据类型" + propertyClass + "没有按规则编写,无法得到属性值类型.", e);
		}

		String propertyNameStr = StringUtils.substringAfter(filterName, "_");
		if(StringUtils.isBlank(propertyNameStr)){
			throw new IllegalArgumentException("过滤名称" + propertyNameStr + "没有按规则编写,无法得到属性值类型.");
		}
		propertyNames = StringUtils.splitByWholeSeparator(propertyNameStr, PropertyFilter.OR_SEPARATOR);

		this.matchValue = ObjectMapper.fromString(value, propertyClass);
	}

	/**
	 * 从HttpRequest中创建PropertyFilter列表
	 * PropertyFilter命名规则为Filter属性前缀_比较类型属性类型_属性名.
	 * 
	 * eg.
	 * filter_EQS_name
	 * filter_LIKES_name_OR_email
	 */
	public static List<PropertyFilter> buildFromRequestMap(final Map<String, Object> request) {
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();

		if(request != null && request.size() > 0){
			//分析参数Map,构造PropertyFilter列表
			for (Map.Entry<String, Object> entry : request.entrySet()) {
				String filterName = entry.getKey();
				String value = (String) entry.getValue();
				//如果value值为空,则忽略此filter.
				if (StringUtils.isNotBlank(value)) {
					PropertyFilter filter = new PropertyFilter(filterName, value);
					filterList.add(filter);
				}
			}
		}

		return filterList;
	}

	/**
	 * 获取比较值的类型.
	 */
	public Class<?> getPropertyClass() {
		return propertyClass;
	}

	/**
	 * 获取比较方式.
	 */
	public MatchType getMatchType() {
		return matchType;
	}

	/**
	 * 获取比较值.
	 */
	public Object getMatchValue() {
		return matchValue;
	}

	/**
	 * 获取比较属性名称列表.
	 */
	public String[] getPropertyNames() {
		return propertyNames;
	}

	/**
	 * 获取唯一的比较属性名称.
	 */
	public String getPropertyName() {
		if(propertyNames.length != 1){
			throw new IllegalArgumentException("There are not only one property in this filter.");
		}
		return propertyNames[0];
	}

	/**
	 * 是否比较多个属性.
	 */
	public boolean hasMultiProperties() {
		return (propertyNames.length > 1);
	}
}
