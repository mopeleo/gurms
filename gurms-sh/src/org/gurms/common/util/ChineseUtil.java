package org.gurms.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class ChineseUtil {
    private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

    static {
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 大写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 没有音调数字
        format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);// u显示
    }

    public static String getAllLeter(String str) {
        if (str == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        if (chars != null && chars.length > 0) {
            for (int i = 0; i < chars.length; i++) {
                try {
                    String[] pinYin = PinyinHelper.toHanyuPinyinStringArray(chars[i], format);
                    if (pinYin != null && pinYin.length > 0) {
                        sb.append(pinYin[0]);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                }
            }
        }
        return sb.toString();
    }

    public static String getFirstLeter(String str) {
        if (str == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        if (chars != null && chars.length > 0) {
            for (int i = 0; i < chars.length; i++) {
                try {
                    String[] pinYin = PinyinHelper.toHanyuPinyinStringArray(chars[i], format);
                    if (pinYin != null && pinYin.length > 0) {
                        sb.append(pinYin[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String chinese = "汉语拼音";
        System.out.println(getFirstLeter(chinese));
        System.out.println(getAllLeter(chinese));
    }

}
