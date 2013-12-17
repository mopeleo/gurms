package org.gurms.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 加载Spring配置文件时，如果Spring配置文件中所定义的Bean类实现了ApplicationContextAware 接口，
 * 那么在加载Spring配置文件时，会自动调用ApplicationContextAware 接口中的
 * public void setApplicationContext(ApplicationContext context) throws BeansException
 * 方法，获得ApplicationContext对象。
 * 前提必须在Spring配置文件中指定该类
 * @author Leo
 *
 */
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	private SpringUtil(){}
	
	/**
	 * ApplicationContextAware接口的context注入函数.
	 */
	public void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		if (applicationContext == null)
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) getApplicationContext().getBean(name);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		return (T) getApplicationContext().getBeansOfType(clazz);
	}

	public static HttpServletRequest getHttpRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static HttpSession getHttpSession(){
		return getHttpRequest().getSession();
	}
}
