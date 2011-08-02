package org.gurms.web.spring;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

public class IntPropertyBinder extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isBlank(text)) {
			setValue(0);
		} else {
			// 通过传入参数的类型来匹配相应的databind
			setValue(Integer.parseInt(text));
		}
	}
}
