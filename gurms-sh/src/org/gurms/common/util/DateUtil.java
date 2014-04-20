package org.gurms.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.gurms.common.exception.GurmsException;

public class DateUtil {

	public static final String pattern_time = "HHmmss";
	public static final String pattern_date = "yyyyMMdd";
	public static final String pattern_fulltime = "yyyyMMddHHmmss";
	// 支持(190000101|1900-00-00|1900/00/00)
	public static final String regex_date = "((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?" //year1 闰年
				+ "((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|"      //month+day 大
				+ "(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|"               //month+day 小
				+ "(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|"                               //month+day 二月
				+ "(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?"                //year2 平年
				+ "((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|"      //month+day 大
				+ "(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|"               //month+day 小
				+ "(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";                        //month+day 二月

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
	
	public static long dateSub(String begindate, String enddate){
        long begin = parseDate(begindate).getTime();
        long end = parseDate(enddate).getTime();
	    return  (end - begin)/(24*60*60*1000);	    
	}
	/**
	 * 
	 * @param date  ：格式为YYYYMMDD的日期字符串
	 * @param num ： 要加减的数字，可为负
	 * @param arithtype ： 要加减的类型，1天、2月、3年
	 * @return
	 * @throws ParseException 
	 */
	private static String dateArith(String date, int num, int arithtype){
		Date d = parseDate(date);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		switch(arithtype){
			case 1:
				c.add(Calendar.DAY_OF_MONTH, num);
				break;
			case 2:
				c.add(Calendar.MONTH, num);
				break;
			case 3:
				c.add(Calendar.YEAR, num);
				break;
			default:
				break;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern_date);
		return sdf.format(c.getTime());
	}
	
	public static int getCurrentYear(){
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public static String addYear(String date, int year){
		return dateArith(date, year, 3);
	}

	public static int getCurrentMonth(){
		return Calendar.getInstance().get(Calendar.MONTH);
	}
	
	public static String addMonth(String date, int month){
		return dateArith(date, month, 2);
	}

	public static int getCurrentDay(){
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}
	
	public static String addDay(String date, int day){
		return dateArith(date, day, 1);
	}

	public static String getCurrentTime(String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		return sdf.format(Calendar.getInstance().getTime());
	}
	
	public static Date parseDate(String datestring){
		if(datestring == null || datestring.length() != 8){
			throw new GurmsException(datestring + "必须为yyyyMMdd形式字符串");
		}
		if(isDate(datestring)){
			return parseDate(pattern_date, datestring);
		}else{
			throw new GurmsException(datestring + "是错误的日期字符串");
		}
	}
	
	public static Date parseDate(String pattern, String datestring){
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		try {
			return sdf.parse(datestring);
		} catch (ParseException e) {
			throw new GurmsException(e.getMessage());
		}
	}
	
	public static boolean dateBetween(String date, String startdate, String enddate){
		return dateBetween(pattern_date, date, startdate, enddate);
	}
	
	public static boolean dateBetween(String pattern, String date, String startdate, String enddate){
		long d = parseDate(pattern, date).getTime();
		long start = parseDate(pattern, startdate).getTime();
		long end = parseDate(pattern, enddate).getTime();

		return d >= start && d < end;
	}
	
	//效验字符串是否为日期格式，支持(190000101|1900-00-00|1900/00/00)
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern.compile(regex_date);
		return pattern.matcher(strDate).matches();
	}
	
	public static String getTimeStamp(){
		return String.valueOf(System.currentTimeMillis());
	}
	
	public static int getAge(String birthDate){
		Date birth = parseDate(birthDate);
		Calendar c = Calendar.getInstance();
		c.setTime(birth);
		int currentYear = getCurrentYear();
		int currentMonth = getCurrentMonth();
		int currentDay = getCurrentDay();
		int birthYear = c.get(Calendar.YEAR);
		int birthMonth = c.get(Calendar.MONTH);
		int birthDay = c.get(Calendar.DAY_OF_MONTH);
		int age = currentYear - birthYear;
		if(age < 0){
			throw new GurmsException("请输入正确的出生年份:[" + birthYear + "]");
		}
		if(currentMonth < birthMonth){
			age = age - 1;
		}else if(currentMonth == birthMonth){
			if(currentDay < birthDay){
				age = age - 1;
			}
		}
		return age;
	}
	
	public static void main(String[] args){
		String s = "20081231";
		System.out.println(isDate(s));
		System.out.println(addDay(s, 3));
		System.out.println(getAge(s));
	}
}
