package org.gurms.common.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.gurms.common.exception.GurmsException;

public class NumberUtil {

	public static final String CHINESE_NUM = "零壹贰叁肆伍陆柒捌玖";     //大写
	public static final String[] CHINESE_UNIT = {"仟佰拾", "角分"};    //单位
	
	private NumberUtil(){}
	
	/**
	 * 数字转换为二进制数组
	 * @param number
	 * @return
	 */
	public static int[] number2binaryArray(int number){
		String binaryString= Integer.toBinaryString(number);
		int    oneCount    = Integer.bitCount(number);
		int[]  binaryArray = new int[oneCount];
		
		int start = 0;
		int len   = binaryString.length();
		for(int i = 0 ; i < oneCount ; i ++){
			start = binaryString.indexOf("1", start);
			System.out.println("start :" + start);
			binaryArray[i] = 1<<(len-start-1);
			start ++;
		}
		return binaryArray;
	}
	
	/**
	 * 判断字符窜是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 转换人民币金额大写
	 * @param amount
	 * @return
	 */
	public static String rmbNumber2Chinese(double amount){
		String s = new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		if(s.endsWith(".00")){  
            s = s.replace(".00", "");  
        }
        String result = "";  
          
        //处理整数部分  
		String[] parts = s.split("\\."); //区别整数、小数部分  
        String intPart = parts[0];          
        if(intPart.length() > 15){  
            throw new GurmsException("金额太大,不能处理整数部分超过15位的金额！");  
        }  

        //填充到16位，因为是分4组，每组4个数字  
        while(intPart.length() < 16){  
            intPart = '0' + intPart;  
        }
        
        //共分四组,右起四位一组,例如：0001,2003,0030,3400  
        String[] groups = new String[4];   
        for(int i=0; i < groups.length; i++){  
            int start = intPart.length()-(i+1)*4;   //开始位置  
            int end = intPart.length()-i*4;         //结束位置  
            groups[i] = intPart.substring(start, end);  
            groups[i] = transformGroup(groups[i]);  //当前组的四位数字转换成大写  
        }  
          
        //对这四组结果从高位到低位拼接起来，规则：[组4]万[组3]亿[组2]万[组1]圆  
        for(int i=groups.length-1; i>=0; i--){  
            if(i==3){   //第四组：万亿级  
                if(!"零".equals(groups[i])){  
                    result += groups[i] + "万";  
                }  
            }else if(i==2){ //第三组：亿级  
                if(!"零".equals(groups[i])){  
                    result += groups[i] + "亿";  
                }else{  
                    if(result.length()>0){  
                        result += "亿";  
                    }  
                }  
            }else if(i==1){ //第二组：万级  
                if(!"零".equals(groups[i])){  
                    result += groups[i] + "万";  
                }else if(!groups[i].startsWith("零")){  
                    result += groups[i];  
                }   
            }else{  //第一组：千级  
                if(!"零".equals(groups[i]) || result.length()==0){  
                    result += groups[i];  
                }  
                result += "圆";  
            }  
        }  
        if(!"零圆".equals(result) && result.startsWith("零")){  
            result = result.substring(1, result.length()); //最前面的可能出现的“零”去掉  
        }  
  
        //处理小数部分  
        if(parts.length == 2){  
            String decimalPart = parts[1];  //小数部分 
            for(int i = 0; i < decimalPart.length(); i++){  
                int num = Integer.valueOf(decimalPart.charAt(i) + "");  //提取数字，左起 
                result += CHINESE_NUM.charAt(num) + "" + CHINESE_UNIT[1].charAt(i); //数字变大写加上单位  
            }  
            result = result.replace("零角", "零"); //去掉"零角"的"角"  
            result = result.replace("零分", "");  //去掉"零分"  
        }else{
            result += "整";  //没有小数部分，则加上“整”  
        }  
          
        return result;
    }
	
	public static String transformGroup(String group){  
        String result = "";  
        int length = group.length();  
        for(int i=0; i < length; i++){  
            int digit = Integer.valueOf(group.charAt(i)+"");    //单个数字，左起  
            String unit = "";   //单位  
            if(i!=length-1){  
                unit = CHINESE_UNIT[0].charAt(i) + "";   
            }  
            result += CHINESE_NUM.charAt(digit) + unit; //数字变大写加上单位  
        }  
          
        result = result.replace("零仟", "零");  
        result = result.replace("零佰", "零");  
        result = result.replace("零拾", "零");  
          
        while(result.contains("零零")){  
            result = result.replace("零零", "零"); //如果有“零零”则变成一个“零”  
        }  
          
        if(!"零".equals(result) && result.endsWith("零")){  
            result = result.substring(0, result.length()-1); //最未尾的可能出现的“零”去掉  
        }  
        return result;  
    }
	
	/**
	 * double 类型 保留小数点后N位数四舍五入
	 * @param sourse       原数据
	 *                dotlength  小数点后位数
	 *                halfup       是否四舍五入
	 */
	public static double doubleFormat(double sourse, int dotlength, boolean halfup){
	    BigDecimal  df = new BigDecimal(sourse);
	    double dest ;
	    if(halfup){
	        dest = df.setScale(dotlength, BigDecimal.ROUND_HALF_UP).doubleValue();
	    }else{
            dest = df.setScale(dotlength, BigDecimal.ROUND_DOWN).doubleValue();
	    }
	    return dest;
	}
	
	public static void main(String[] args) {
		int num = 777;
		System.out.println(Integer.toBinaryString(num));
		System.out.println(Integer.bitCount(num));
		long l = System.currentTimeMillis();
		int[] test = number2binaryArray(num);
		l = System.currentTimeMillis() - l;
		System.out.println("total times : " + l);
		for(int i : test){
			System.out.println(i);
		}
		
		System.out.println(rmbNumber2Chinese(2345678.09));
	}
}
