package org.gurms.web.spring;

import org.gurms.entity.Logable;
import org.gurms.entity.system.SysOrg;
import org.gurms.entity.system.SysUser;
import org.gurms.web.WebConstants;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

public class OperatorInjectResolver implements WebArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter arg, NativeWebRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		Class argType = arg.getParameterType();
		if(Logable.class.isAssignableFrom(argType)){
			SysUser user = (SysUser)request.getAttribute(WebConstants.S_KEY_USER, RequestAttributes.SCOPE_SESSION);
			Logable ext = (Logable)argType.newInstance();
			ext.setOperator(user.getUserid());
			return ext;
		}
		return UNRESOLVED;
	}
	
	public static void main(String[] args) {
		SysOrg user = new SysOrg();
		System.out.println(user.getClass().isAssignableFrom(Logable.class));
		System.out.println(Object.class.isAssignableFrom(user.getClass()));
	}

}
