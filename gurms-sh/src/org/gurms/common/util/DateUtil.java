package org.gurms.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtil {

	public static final String pattern_time = "HHmmss";
	public static final String pattern_date = "yyyyMMdd";
	public static final String pattern_fulltime = "yyyyMMddHHmmss";

	private DateUtil(){}
	
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
	
	public static boolean dateBetween(String date, String startdate, String enddate){
		int d = Integer.parseInt(date);
		int startint = 0;
		int endint = 0;
		
		if(StringUtils.isBlank(startdate)){
			startint = 0;
		}else{
			try{
				startint = Integer.parseInt(startdate);
			}catch(Exception e){
				return false;
			}
		}
		if(StringUtils.isBlank(enddate)){
			endint = 99999999;
		}else{
			try{
				endint = Integer.parseInt(enddate);
			}catch(Exception e){
				return false;
			}
		}
		
		return d >= startint && d < endint;
	}
}
