package org.gurms.web.filter;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.gurms.common.config.GlobalParam;
import org.gurms.common.exception.GurmsException;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysUser;
import org.gurms.service.system.SysUserService;
import org.gurms.web.SpringUtil;
import org.gurms.web.WebConstants;

public class SysSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		SysUser sessionUser = (SysUser)event.getSession().getAttribute(WebConstants.S_KEY_USER);
		if(sessionUser != null){
			SysUserService userService = SpringUtil.getBean("sysUserServiceImpl");
			SysUser user = userService.get(sessionUser.getUserid());
			user.setOnlineflag(GlobalParam.DICT_ONLINEFLAG_NO);
			PageResult<SysUser> result = userService.save(user);
			if(!result.isSuccess()){
				throw new GurmsException(result.getReturnmsg());
			}
		}
	}

}
