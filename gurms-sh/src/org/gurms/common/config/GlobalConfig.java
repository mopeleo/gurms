package org.gurms.common.config;

import java.util.ResourceBundle;

/**
 * 通过配置文件配置的参数
 */
public final class GlobalConfig {

    private static final ResourceBundle CONFIG = ResourceBundle.getBundle("gurms-config");

    // -------------分页------------
    public static final int MIN_PAGESIZE = getIntConfig("min_pagesize");
    public static final int MAX_PAGESIZE = getIntConfig("max_pagesize");

    public static final String INIT_PASSWORD = getConfig("init_password");
    public static final String PRIVILEGE_LEVEL = getConfig("privilege_level");
    public static final String PRIVILEGE_LEVEL_MENU = "1";
    public static final String PRIVILEGE_LEVEL_BUTTON = "2";
    
    public static final String FILE_DIR = getConfig("file_dir");
    public static final String FILE_DIR_MODE = getConfig("file_dir_mode");
    public static final String FILE_DIR_MODE_RELATIVE = "1";  //相对路径
    public static final String FILE_DIR_MODE_ABSOLUTE = "2";  //绝对路径
    
    private GlobalConfig() {}

    public static String getConfig(String key) {
        return CONFIG.getString(key);
    }

    public static String getConfig(String key, String defaultValue) {
		String value = CONFIG.getString(key);
		return value != null ? value : defaultValue;
    }

    public static int getIntConfig(String key) {
        return Integer.parseInt(getConfig(key));
    }
}
