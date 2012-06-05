package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="sys_log_login")
public class SysLogLogin implements Serializable {

	private String logid;
	private String userid;
	private String logindate;
	private String logintime;
	private String loginpassword;
	private String loginip;
	private String success;
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
	public String getLogindate() {
		return logindate;
	}
	public void setLogindate(String logindate) {
		this.logindate = logindate;
	}
	public String getLogintime() {
		return logintime;
	}
	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}
	public String getLoginpassword() {
		return loginpassword;
	}
	public void setLoginpassword(String loginpassword) {
		this.loginpassword = loginpassword;
	}
	public String getLoginip() {
		return loginip;
	}
	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysLogLogin)) {
			return false;
		} else {
			SysLogLogin login = (SysLogLogin) o;
			if (login.getLogid() == null) {
				return false;
			} else {
				return login.getLogid().equals(logid);
			}
		}
	}

	public int hashCode() {
		if (logid == null)
			return super.hashCode();
		return logid.hashCode();
	}
}
