package org.gurms.web.spring;

import java.io.IOException;
import java.io.Writer;

import org.gurms.common.exception.GurmsException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkerConfigurerExt extends FreeMarkerConfigurer {
	@Override
	public void afterPropertiesSet() throws IOException, TemplateException {
		if (getConfiguration() == null) {
			setConfiguration(createConfiguration());
		}
		getConfiguration().setTemplateExceptionHandler(new TemplateExceptionHandler(){

			@Override
			public void handleTemplateException(TemplateException te,
					Environment env, Writer out) throws TemplateException {
				throw new GurmsException(te.getMessage());
			}			
		});
	}
}
