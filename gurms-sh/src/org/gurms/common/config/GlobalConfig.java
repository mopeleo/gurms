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

    // ------------数据库------------
    public static final String ID_SYS_ROLE_SYSTEM = getConfig("id_sys_role_system");
    public static final String ID_SYS_MENU_ROOT = getConfig("id_sys_menu_root");

    private GlobalConfig() {}

    public static String getConfig(String key) {
        return CONFIG.getString(key);
    }

    public static int getIntConfig(String key) {
        return Integer.parseInt(getConfig(key));
    }
}
