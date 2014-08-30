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
@Table(name = "sys_role")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EfFlow implements Serializable {

	private long flowId;
	private String flowName;
	private String flowStatus;
	private String userid;
	private String remark;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sys_seq")   
	@SequenceGenerator(name="sys_seq", sequenceName="sys_seq", allocationSize=1) 
	public long getFlowId() {
		return flowId;
	}

	public void setFlowId(long flowId) {
		this.flowId = flowId;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
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
			return flow.getFlowId() == this.flowId;
		}
	}

	public int hashCode() {
		return String.valueOf(flowId).hashCode();
	}

}
