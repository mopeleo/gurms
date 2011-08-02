package org.gurms.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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

}
