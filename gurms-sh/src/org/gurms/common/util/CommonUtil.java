package org.gurms.common.util;

public class CommonUtil {

	private CommonUtil(){}
	
	/**
	 * 判断key是否存在于array数组中
	 * @param key
	 * @param array
	 * @param ignoreCase 是否忽略大小写
	 * @return
	 */
	public static boolean exists(String key, String[] array, boolean ignoreCase){
		if(array == null || array.length == 0){
			return false;
		}
		if(ignoreCase){
			for(String tmp : array){
				if(tmp.equalsIgnoreCase(key)){
					return true;
				}else{
					continue;
				}
			}
		}else{
			for(String tmp : array){
				if(tmp.equals(key)){
					return true;
				}else{
					continue;
				}
			}
		}
		return false;
	}

	/**
	 * //判断key是否是以head数组中的字符串开头
	 * @param key
	 * @param prefix
	 * @param ignoreCase
	 * @return
	 */
	public static boolean existPrefix(String key, String[] prefix, boolean ignoreCase){
		if(prefix == null || prefix.length == 0){
			return true;
		}
		if(ignoreCase){
			String lowerKey = key.toLowerCase();
			for(String tmpPrefix : prefix){
				if(lowerKey.indexOf(tmpPrefix.toLowerCase())==0){
					return true;
				}else{
					continue;
				}
			}
		}else{
			for(String tmpPrefix : prefix){
				if(key.indexOf(tmpPrefix)==0){
					return true;
				}else{
					continue;
				}
			}
		}
		return false;
	}

	public static boolean existPrefix(String key, String[] suffix){
		return existPrefix(key, suffix, true);
	}

	/**
	 * 	//判断key是否是以head数组中的字符串结尾
	 * @param key
	 * @param suffix
	 * @param ignoreCase
	 * @return
	 */
	public static boolean existSuffix(String key, String[] suffix, boolean ignoreCase){
		if(suffix == null || suffix.length == 0){
			return true;
		}
		if(ignoreCase){
			String lowerKey = key.toLowerCase();
			for(String tmpSuffix : suffix){
				if(lowerKey.endsWith(tmpSuffix.toLowerCase())){
					return true;
				}else{
					continue;
				}
			}
		}else{
			for(String tmpSuffix : suffix){
				if(key.endsWith(tmpSuffix)){
					return true;
				}else{
					continue;
				}
			}
		}
		return false;
	}

	public static boolean existSuffix(String key, String[] suffix){
		return existSuffix(key, suffix, true);
	}
	
	public static String getFileSuffix(String fileName){
		if(fileName != null){
			int index = fileName.lastIndexOf(".");
			if(index > 0){
				return fileName.substring(index);
			}
		}
		return "";
	}
	
	public static void main(String[] args) {
		String[] test = {".jpg",".js"};
		String lowerkey = "jpg";
		String upperkey = "a.a.JPG";
		
		System.out.println(existSuffix(upperkey, test));
		System.out.println(existSuffix(upperkey, test, false));
		System.out.println(getFileSuffix(upperkey));
	}
}
