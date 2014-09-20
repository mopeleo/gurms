package org.gurms.entity.easyflow;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ef_link")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EfLink implements Serializable {

	private String linkid;    //环节ID
	private String linkflag;
	private String linkname;    //环节名称
	private String linkvalue;    //流程中环节对应的值
	private String prelink;    //上个环节，可为空，也可有多个环节，由,分隔
	private String nextlink;    //下个环节，可为空，也可有多个环节，由,分隔
	private EfFlow flow;

	@Id
	public String getLinkid() {
		return linkid;
	}

	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}
	

	@ManyToOne
	@JoinColumn(name = "flowid")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public EfFlow getFlow() {
		return flow;
	}

	public void setFlow(EfFlow flow) {
		this.flow = flow;
	}

	public String getLinkname() {
		return linkname;
	}

	public void setLinkname(String linkname) {
		this.linkname = linkname;
	}

	public String getLinkflag() {
		return linkflag;
	}

	public void setLinkflag(String linkflag) {
		this.linkflag = linkflag;
	}

	public String getLinkvalue() {
		return linkvalue;
	}

	public void setLinkvalue(String linkvalue) {
		this.linkvalue = linkvalue;
	}

	public String getPrelink() {
		return prelink;
	}

	public void setPrelink(String prelink) {
		this.prelink = prelink;
	}

	public String getNextlink() {
		return nextlink;
	}

	public void setNextlink(String nextlink) {
		this.nextlink = nextlink;
	}

	public boolean equals(Object o) {
		if(o == null || !(o instanceof EfLink)){
			return false;
		}else{
			EfLink entity = (EfLink)o;
			EqualsBuilder eb = new EqualsBuilder();
			eb.append(this.getLinkid(), entity.getLinkid());
			return eb.isEquals();
		}
	}

	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(linkid);
		return hcb.hashCode();
	}
}
