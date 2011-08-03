package org.gurms.common.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value=ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface GurmsValid {

	//要效验的数据类型
	Class type();
	
	//需要效验的属性，默认空，即只要gurms-validate.properties配置了的属性都要效验
	String[] props() default {};
	
	//属性过滤，props中的属性字段默认include包含，可选项exclude不包含
	FilterType filter() default FilterType.INCLUDE;
	
	public enum FilterType{INCLUDE,EXCLUDE}
}
