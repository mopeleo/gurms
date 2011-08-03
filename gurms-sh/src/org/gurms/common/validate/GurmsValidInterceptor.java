package org.gurms.common.validate;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.gurms.common.exception.GurmsException;
import org.gurms.common.validate.GurmsValid.FilterType;
import org.gurms.entity.PageResult;

public class GurmsValidInterceptor implements MethodInterceptor {

	/**
	 * 拦截器，主要用来根据配置自动效验dto
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		if(method.isAnnotationPresent(GurmsValid.class)){
			GurmsValid valid = method.getAnnotation(GurmsValid.class);
			
			String clzName = valid.type().getName();
			String[] props = valid.props();
			FilterType filter = valid.filter();

			Object[] args = invocation.getArguments();
			String validMsg = null;
			for(Object arg : args){
				if(valid.type().isInstance(arg)){
					validMsg = GurmsValidator.valid(clzName, props, filter, arg);
					break;
				}
			}
			
			if(StringUtils.isNotBlank(validMsg)){
				System.out.println("valid msg:  " + validMsg);

				if(PageResult.class.isAssignableFrom(method.getReturnType())){
					PageResult<Object> result = new PageResult<Object>();
					result.setSuccess(false);
					result.setReturnmsg(validMsg);
					return result;
				}else{
					throw new GurmsException(validMsg);
				}
			}
		}
		
		return invocation.proceed();
	}
}
