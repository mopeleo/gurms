package org.gurms.common.config;

import java.util.ResourceBundle;

public class GlobalMessage {

	public static final ResourceBundle message = ResourceBundle.getBundle("gurms-message");

    private GlobalMessage(){}
    
    public static String getMessage(String key){
    	return message.getString(key);
    }
}
