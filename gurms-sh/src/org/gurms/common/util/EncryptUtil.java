package org.gurms.common.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import org.gurms.common.exception.GurmsException;

public class EncryptUtil {

	private final static String DESKEY = "gurms"; // 默认密钥
	private final static String MD5 = "MD5";
	private final static String DES = "DES";
	private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private EncryptUtil() {}

	private static Key getDesKey(){
//		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		byte[] byteKey = DESKEY.getBytes();
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];
		// 将原始字节数组转换为8位
		for (int i = 0; i < byteKey.length && i < arrB.length; i++) {
			arrB[i] = byteKey[i];
		}
		// 生成密钥
		return new javax.crypto.spec.SecretKeySpec(arrB, DES);
	}
	
	private static String byteArr2HexStr(byte[] msg){
		int len = msg.length;
		char str[] = new char[len * 2];
		int k = 0;
		for (int i = 0; i < len; i++) {
			byte byte0 = msg[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}

	private static byte[] hexStr2ByteArr(String msg){
		byte[] arrB = msg.getBytes();
		int iLen = arrB.length;
		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	public static String md5Encode(String msg){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(MD5);
		} catch (NoSuchAlgorithmException e) {
			throw new GurmsException("数据加密异常", e);
		}
		byte[] md5byte = md.digest(msg.getBytes());
		return byteArr2HexStr(md5byte);
	}

	public static String desEncode(String msg){
		Key key = getDesKey();
		byte[] desbyte = null;
		try {
			Cipher encryptCipher = Cipher.getInstance(DES);
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			desbyte = encryptCipher.doFinal(msg.getBytes());
		} catch (Exception e) {
			throw new GurmsException("数据加密异常", e);
		}
		return byteArr2HexStr(desbyte);
	}

	public static String desDecode(String msg){
		Key key = getDesKey();
		try {
			Cipher decryptCipher = Cipher.getInstance(DES);
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
			byte[] desbyte = hexStr2ByteArr(msg);
			return new String(decryptCipher.doFinal(desbyte));
		} catch (Exception e) {
			throw new GurmsException("数据加密异常", e);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//202cb962ac59075b964b07152d234b70 === 123
		String s = "wym123";
		try {
			System.out.println(s.getBytes().length);
			System.out.println(md5Encode(s));
			System.out.println(desEncode(s));
			System.out.println(desDecode(desEncode(s)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
