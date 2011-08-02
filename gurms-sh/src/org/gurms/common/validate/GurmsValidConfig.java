package org.gurms.common.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;

public class GurmsValidConfig {
	
    private static final ResourceBundle rb = ResourceBundle.getBundle("gurms-validate");
    private static final Set<String> keySet = rb.keySet(); 

    //验证信息返回的格式
    public static final String MSG_TYPE = rb.getString("msg_type");
    
    private GurmsValidConfig(){}
    
    /**
     * 根据完整的key获得效验规则
     * @param key 完整的key
     * @return GurmsValidRule
     */
    public static List<GurmsValidRule> getFieldRules(String key){
    	return json2Rule(key);
    }
    
    /**
     * 根据类名，获得全部属性的效验规则
     * @param className 类名
     * @return ArrayList<GurmsValidRule>
     */
    public static List<GurmsValidRule> getBeanRules(String className){
    	List<GurmsValidRule> rules = new ArrayList<GurmsValidRule>();
    	String tmpKey = className + ".";
    	for(String key : keySet){
    		if(key.startsWith(tmpKey)){
				List<GurmsValidRule> gvr = json2Rule(key);
    			rules.addAll(gvr);
    		}
    	}
    	return rules;
    }
    
    /**
     * 根据类名，获得全部属性的效验规则
     * @param className 类名
     * @param props 属性集合
     * @return ArrayList<GurmsValidRule>
     */
    public static List<GurmsValidRule> getBeanRules(String className, String[] props){
		List<GurmsValidRule> rules = null;
		if(props == null || props.length == 0){
			rules = GurmsValidConfig.getBeanRules(className);			
		}else{
			rules = new ArrayList<GurmsValidRule>();
			for(String field : props){
				String validField = className + "." + field;
				List<GurmsValidRule> gvr = GurmsValidConfig.getFieldRules(validField);
				rules.addAll(gvr);
			}
		}
		return rules;
    }
    
    /**
     * 从json字符串转换为GurmsValidRule对象
     * @param 完整的key
     * @return GurmsValidRule
     */
    private static List<GurmsValidRule> json2Rule(String key){
    	String rule = rb.getString(key);
		int fieldIdx = key.lastIndexOf(".") + 1;
		String field = key.substring(fieldIdx);
		
    	JSONArray o = JSONArray.fromObject(rule);
    	List<GurmsValidRule> list = JSONArray.toList(o, GurmsValidRule.class);
    	for(GurmsValidRule gvr : list){
			//若没有特别执行页面空间的ID，则默认为属性名
			if(StringUtils.isBlank(gvr.getField())){
	    		gvr.setField(field);
	    	}
    	}
		return list;    	
    }    
}
