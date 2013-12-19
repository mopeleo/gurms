package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="sys_log_operate")
public class SysLogOperate implements Serializable{

	private String logid;
	private String userid;
	private String operatedate;
	private String operatetime;
	private String operatetype;
	private String operatetable;
	private String operateurl;
	private String dataid;
	@Id
	@GeneratedValue(generator = "uuid")    
	@GenericGenerator(name = "uuid", strategy = "uuid") 
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getOperatedate() {
		return operatedate;
	}
	public void setOperatedate(String operatedate) {
		this.operatedate = operatedate;
	}
	public String getOperatetime() {
		return operatetime;
	}
	public void setOperatetime(String operatetime) {
		this.operatetime = operatetime;
	}
	public String getOperatetype() {
		return operatetype;
	}
	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}
	public String getOperatetable() {
		return operatetable;
	}
	public void setOperatetable(String operatetable) {
		this.operatetable = operatetable;
	}
	public String getOperateurl() {
		return operateurl;
	}
	public void setOperateurl(String operateurl) {
		this.operateurl = operateurl;
	}
	public String getDataid() {
		return dataid;
	}
	public void setDataid(String dataid) {
		this.dataid = dataid;
	}
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysLogOperate)) {
			return false;
		} else {
			SysLogOperate operate = (SysLogOperate) o;
			if (operate.getLogid() == null) {
				return false;
			} else {
				return operate.getLogid().equals(logid);
			}
		}
	}

	public int hashCode() {
		if (logid == null)
			return super.hashCode();
		return logid.hashCode();
	}
}
