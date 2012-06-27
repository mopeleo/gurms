package org.gurms.dao.datasource;

public class DbContextHolder {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static final String DS_GURMS = "gurms";
	public static final String DS_ACTIVITI = "activiti";
	
	public static void setDbType(String dbType) {
	    contextHolder.set(dbType);
	  }

	  public static String getDbType() {
	    return contextHolder.get();
	  }

	  public static void clearDbType() {
	    contextHolder.remove();
	  }
}
