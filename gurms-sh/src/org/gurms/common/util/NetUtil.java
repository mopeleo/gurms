package org.gurms.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * tomcat参数中添加：-Djava.net.preferIPv4Stack=true 让tomcat只支持ipV4.
 * @author Leo
 *
 */
public class NetUtil {

	public static String getIPv4() {
		System.setProperty("java.net.preferIPv4Stack", "true");
		return getIPAddress();
	}

	public static String getIPv6() {
		System.setProperty("java.net.preferIPv6Addresses", "true");
		return getIPAddress();
	}
	
	public static String getIPAddress(){
		String ip = "";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress();
			System.out.println("HostName := " + addr.getHostName());
			System.out.println("HostAddress := " + ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ip;
	}
	
}
