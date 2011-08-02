package org.gurms.web.filter;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SysSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent httpsessionevent) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpsessionevent) {
		// TODO session 过期时修改用户状态
	}

}
