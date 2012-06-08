package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="sys_accessory")
public class SysAccessory implements Serializable {

	public String accessoryid;
	public String accessoryname;
	public String accessorysize;
	public String savepath;
	public String userid;
	public String uploaddate;
	public String accessorytype;
	public String status;
	public int downtimes;
	@Id
	@GeneratedValue(generator = "uuid")    
	@GenericGenerator(name = "uuid", strategy = "uuid") 
	public String getAccessoryid() {
		return accessoryid;
	}
	public void setAccessoryid(String accessoryid) {
		this.accessoryid = accessoryid;
	}
	public String getAccessoryname() {
		return accessoryname;
	}
	public void setAccessoryname(String accessoryname) {
		this.accessoryname = accessoryname;
	}
	public String getAccessorysize() {
		return accessorysize;
	}
	public void setAccessorysize(String accessorysize) {
		this.accessorysize = accessorysize;
	}
	public String getSavepath() {
		return savepath;
	}
	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUploaddate() {
		return uploaddate;
	}
	public void setUploaddate(String uploaddate) {
		this.uploaddate = uploaddate;
	}
	public int getDowntimes() {
		return downtimes;
	}
	public void setDowntimes(int downtimes) {
		this.downtimes = downtimes;
	}	
	
	public String getAccessorytype() {
		return accessorytype;
	}
	public void setAccessorytype(String accessorytype) {
		this.accessorytype = accessorytype;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysAccessory)) {
			return false;
		} else {
			SysAccessory accessory = (SysAccessory) o;
			if (accessory.getAccessoryid() == null) {
				return false;
			} else {
				return accessory.getAccessoryid().equals(accessoryid);
			}
		}
	}

	public int hashCode() {
		if (accessoryid == null)
			return super.hashCode();
		return accessoryid.hashCode();
	}
}
