package org.gurms.entity;

import java.io.Serializable;

public class Checkable implements Serializable{

	private String checker;
	private String checkdate;
	private String checktime;
	private String checknote;
	private String checkstatus; //0 待复核，1复核通过，2复核不通过，3已作废
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
	public String getChecktime() {
		return checktime;
	}
	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}
	public String getChecknote() {
		return checknote;
	}
	public void setChecknote(String checknote) {
		this.checknote = checknote;
	}
	public String getCheckstatus() {
		return checkstatus;
	}
	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}
	
}
