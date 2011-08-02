package org.gurms.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.exception.GurmsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 反射的Utils函数集合.
 * 
 * 提供访问私有变量,获取泛型类型Class,提取集合中元素的属性等Utils函数.
 * 
 */
public class ReflectionUtil {

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

	public static final String CGLIB_CLASS_SEPARATOR = "$$";

	/**
	 * 调用Getter方法.
	 */
	public static Object invokeGetterMethod(Object obj, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(obj, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 调用Setter方法.使用value的Class来查找Setter方法.
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
		invokeSetterMethod(obj, propertyName, value, null);
	}

	/**
	 * 调用Setter方法.
	 * 
	 * @param propertyType 用于查找Setter方法,为空时使用value的Class替代.
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(obj, setterMethodName, new Class[] { type }, new Object[] { value });
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField,	 并强制设置为可访问.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {//NOSONAR
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 对于被cglib AOP过的对象, 取得真实的Class类型.
	 */
	public static Class<?> getUserClass(Class<?> clazz) {
		if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 * 用于一次性调用的情况.
	 */
	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			return null;
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw new GurmsException(e);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
	 * 如向上转型到Object仍无法找到, 返回null.
	 * 
	 * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {

		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

				method.setAccessible(true);

				return method;

			} catch (NoSuchMethodException e) {//NOSONAR
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
	 * 如无法找到, 返回Object.class.
	 * eg.
	 * public UserDao extends HibernateDao<User>
	 *
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
	 * 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao<User,Long>
	 *
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	public static Class getSuperClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}


	/**
	 * 递归调用获得属性的值.
	 */
	public static Object recGetPropertyValue(final Object object, final String fieldName) {
		int idx = fieldName.indexOf('.');
		//判断是单个属性还是对象的属性
		if(idx > 0){
			String objName = fieldName.substring(0, idx);
			Object objValue= recGetPropertyValue(object, objName);
			return recGetPropertyValue(objValue, fieldName.substring(idx + 1));
		}

		//map类型的对象不能反射属性赋值
		if(isMapType(object)){
			return ((Map)object).get(fieldName);
		}
		
		Object result = invokeGetterMethod(object, fieldName);
		return result;
	}


	/**
	 * 递归调用为属性赋值.
	 */
	public static void recSetPropertyValue(final Object object,final String fieldName, final Object value) {
		int idx = fieldName.indexOf('.');
		//判断是单个属性还是对象的属性
		if(idx > 0){
			String objName = fieldName.substring(0, idx);
			Object objValue= recGetPropertyValue(object, objName);
			recSetPropertyValue(objValue, fieldName.substring(idx + 1), value);
			return;
		}

		//map类型的对象不能反射属性赋值
		if(isMapType(object)){
			((Map)object).put(fieldName, value);
			return;
		}
		
		Field field = getAccessibleField(object, fieldName);
		if (field == null)
			return;

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/**
	 * 从源获得属性的值赋值给目标对象的属性
	 * 
	 * @param target  目标对象
	 * @param tfield  目标对象属性
	 * @param source  源对象
	 * @param sfield  源属性
	 */
	public static void copyPropertyValue(Object target, String tfield, Object source,
			String sfield) {
		Object value = recGetPropertyValue(source, sfield);
		if(value != null){
			recSetPropertyValue(target, tfield, value);
		}
	}


	/**
	 * 循环向上转型,获取对象所有的DeclaredField.
	 */
	public static List<Field> getDeclaredFields(final Class clazz){
		List<Field> fields = new ArrayList<Field>();
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
		}
		return fields;
	}

	/**
	 * 判断对象的属性是否是基本类型
	 * 
	 * @param f  字段属性对象
	 * @return
	 */
	public static boolean isBaseType(Field f) {
		Class clz = f.getType();
		if (clz == String.class) {
			return true;
		} else if (clz == int.class || clz == Integer.class) {
			return true;
		} else if (clz == long.class || clz == Long.class) {
			return true;
		} else if (clz == float.class || clz == Float.class) {
			return true;
		} else if (clz == double.class || clz == Double.class) {
			return true;
		} else if (clz == boolean.class || clz == Boolean.class) {
			return true;
		} else if (clz == byte.class || clz == Byte.class) {
			return true;
		} else if (clz == char.class || clz == Character.class) {
			return true;
		} else if (clz == short.class || clz == Short.class) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isMapType(Object obj){
		return obj instanceof Map;
	}
}
