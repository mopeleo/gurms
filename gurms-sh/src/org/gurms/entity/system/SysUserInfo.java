package org.gurms.entity.system;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="sys_userinfo")
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true) 
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysUserInfo {
	private String userid;
	private String mobile;
	private String linktel;
	private String faxno;
	private String address;
	private String postcode;
	private String email;
	private String sex;
	private String birthday;
	private String province;
	private String city;
	private String education;
	private String createdate;
	@Id
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLinktel() {
		return linktel;
	}
	public void setLinktel(String linktel) {
		this.linktel = linktel;
	}
	public String getFaxno() {
		return faxno;
	}
	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysUserInfo)) {
			return false;
		} else {
			SysUserInfo user = (SysUserInfo) o;
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
