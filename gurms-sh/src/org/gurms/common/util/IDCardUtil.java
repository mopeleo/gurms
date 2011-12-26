package org.gurms.common.util;

import java.text.ParseException;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.exception.GurmsException;

public class IDCardUtil {

	/**
	 * 身份证号码验证 
	 * 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。
	 * 排列顺序从左至右依次为：六位数字地址码， 八位数字出生日期码，三位数字顺序码和一位数字校验码。 
	 * 2、地址码(前六位数） 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
	 * 3、出生日期码（第七位至十四位） 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
	 * 4、顺序码（第十五位至十七位） 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。
	 * 5、校验码（第十八位数）
	 * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
	 * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 
	 * （2）计算模 Y = mod(S, 11) 
	 * （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2 
	 */

	public static int[] Wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
	public static String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
	
	public static boolean validateIDCard(String idcard) {
		//效验身份证长度
		if (idcard == null || (idcard.length() != 15 && idcard.length() != 18)) {
			return false;
		}
		
		String Ai = "";
		if (idcard.length() == 18) {
			Ai = idcard.substring(0, 17);
		} else if (idcard.length() == 15) {
			Ai = idcard.substring(0, 6) + "19" + idcard.substring(6, 15);
		}
		
		//身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。
		if (!NumberUtil.isNumber(Ai)) {
			return false;
		}
		
		//身份证出生年月
		String year = Ai.substring(6, 10);
		String birth = Ai.substring(6, 14);
		if (!DateUtil.isDate(birth)) {
			return false;
		}
		
		//身份证出生年月不在有效范围
		try {
			if ((DateUtil.getCurrentYear() - Integer.parseInt(year)) > 150|| (System.currentTimeMillis() - DateUtil.parseDate(birth).getTime()) < 0) {
				return false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		
		//身份证地区编码错误
		Hashtable<?, ?> h = getAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			return false;
		}
		
		if (idcard.length() == 18) {
			// 判断最后一位的值
			Ai += getLastCharAtIdCard(Ai);
			
			return Ai.equals(idcard);
		}
		
		return true;
	}
	
	public static String get18IDCard(String idcard){
		if(!validateIDCard(idcard)){
			throw new GurmsException("错误的身份证号码");
		}
		
		if(idcard.length() == 18){
			return idcard;
		}
		
		String idcard17 = idcard.substring(0, 6) + "19" + idcard.substring(6, 15);
		idcard17 += getLastCharAtIdCard(idcard17);
		return idcard17;
	}
	
	private static String getLastCharAtIdCard(String idcard17){
		int totalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			totalmulAiWi += Integer.parseInt(String.valueOf(idcard17.charAt(i)))* Wi[i];
		}
		int modValue = totalmulAiWi % 11;
		return ValCodeArr[modValue];
	}
	
	private static Hashtable<String, String> getAreaCode() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		
		return hashtable;
	}
}
