package org.gurms.service.system;


public interface SysSerialnoService {

	public String getId(String type);
	
	public String[] getBatchId(String type,int num);
}
