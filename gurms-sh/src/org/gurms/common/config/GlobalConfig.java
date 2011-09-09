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
//    public static final String SYSTEM_MODE = getConfig("system_mode");
    
    private GlobalConfig() {}

    public static String getConfig(String key) {
        return CONFIG.getString(key);
    }

    public static int getIntConfig(String key) {
        return Integer.parseInt(getConfig(key));
    }
}
