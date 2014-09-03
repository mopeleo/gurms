package org.gurms.entity.easyflow;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ef_flow")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EfFlow implements Serializable {

	private Long flowid;
	private String flowname;
	private String flowstatus;
	private String userid;
	private String remark;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sys_seq")   
	@SequenceGenerator(name="sys_seq", sequenceName="sys_seq", allocationSize=1) 
	public Long getFlowid() {
		return flowid;
	}

	public void setFlowid(Long flowid) {
		this.flowid = flowid;
	}

	public String getFlowname() {
		return flowname;
	}

	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}

	public String getFlowstatus() {
		return flowstatus;
	}

	public void setFlowstatus(String flowstatus) {
		this.flowstatus = flowstatus;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof EfFlow)) {
			return false;
		} else {
			EfFlow flow = (EfFlow) o;
			return flow.getFlowid() == this.flowid;
		}
	}

	public int hashCode() {
		return flowid.hashCode();
	}

}
