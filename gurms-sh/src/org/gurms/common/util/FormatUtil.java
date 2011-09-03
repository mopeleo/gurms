package org.gurms.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatUtil {

	private static final String symbol = "\r\n\t";
	public static final String pattern_time = "HHmmss";
	public static final String pattern_date = "yyyyMMdd";
	public static final String pattern_fulltime = "yyyyMMddHHmmss";

	private FormatUtil(){}
	
	public static String bean2string(Object obj){
		StringBuffer sb = new StringBuffer();
		Class c = obj.getClass();
		sb.append(c).append(" : ").append(symbol);
		Field[] fields = c.getDeclaredFields();
		try{
			for(Field f : fields){
				String field = f.getName();
				String methodName = "get" + field.substring(0,1).toUpperCase()
						+ field.substring(1);
				Method method = c.getMethod(methodName);
				sb.append(field).append(" = ");
				sb.append(method.invoke(obj)).append(symbol);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String getCurrentTime(){
		return getCurrentTime(pattern_time);
	}
	
	public static String getCurrentDate(){
		return getCurrentTime(pattern_date);
	}
	
	public static String getCurrentFullTime(){
		return getCurrentTime(pattern_fulltime);
	}
	
	public static String getCurrentTime(String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		return sdf.format(Calendar.getInstance().getTime());
	}
	
	public static Date getDate(String pattern, String datestring) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		return sdf.parse(datestring);
	}
	
}
