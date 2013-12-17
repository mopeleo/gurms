package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.gurms.entity.Logable;
import org.gurms.entity.PageRequest;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Logable
@Entity
@Table(name = "sys_user_config")
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true) 
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysUserConfig implements Serializable {

	private String userid;
	private int pagesize = PageRequest.MIN_PAGESIZE;
	private String fastmenu;
	
	private String[] sysmenuids;
	
	@Id
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public String getFastmenu() {
		return fastmenu;
	}
	public void setFastmenu(String fastmenu) {
		this.fastmenu = fastmenu;
	}
	/////扩展属性，非持久层属性
	@Transient
	public String[] getSysmenuids() {
		return sysmenuids;
	}
	
	public void setSysmenuids(String[] sysmenuids) {
		this.sysmenuids = sysmenuids;
	}
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysUserConfig)) {
			return false;
		} else {
			SysUserConfig user = (SysUserConfig) o;
			if (user.getUserid() == null) {
				return false;
			} else {
				return user.getUserid().equals(userid);
			}
		}
	}

	public int hashCode() {
		if (userid == null)
			return super.hashCode();
		return userid.hashCode();
	}
	
}
