package org.gurms.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ConfigUtil {
	private Properties props = null;
	private File configFile = null;
	private long fileLastModified = 0L;

	private ConfigUtil(){}
	
	public static ConfigUtil getConfigUtil(String propertyFile){
		ConfigUtil config = new ConfigUtil();
		config.init(propertyFile);
		return config;
	}
	
	private void init(String propertyFile) {
		URL url = ConfigUtil.class.getClassLoader().getResource(propertyFile);
		configFile = new File(url.getFile());
		fileLastModified = configFile.lastModified();
		props = new Properties();
		load();
	}

	private void load() {
		try {
			props.load(new FileInputStream(configFile));
			fileLastModified = configFile.lastModified();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getProperty(String key) {
		if (configFile.lastModified() > fileLastModified)
			load();
		return props.getProperty(key);
	}

	public Properties getProps() {
		if (configFile.lastModified() > fileLastModified)
			load();
		return props;
	}

}
