package org.gurms.entity.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "sys_dict_index")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysDictIndex implements Serializable{

	private Integer dictcode;
	private String dictname;
	private String dicttype;
	private String editflag;
	private List<SysDictValue> dictvalue = new ArrayList<SysDictValue>();

	@Id
	public Integer getDictcode() {
		return dictcode;
	}

	public void setDictcode(Integer dictcode) {
		this.dictcode = dictcode;
	}
	
	@OneToMany(
		cascade = {CascadeType.PERSIST, CascadeType.MERGE}, 
		fetch = FetchType.LAZY, 
		mappedBy = "dictindex"
	)
	@OrderBy(value = "dictorder asc")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<SysDictValue> getDictvalue() {
		return dictvalue;
	}

	public void setDictvalue(List<SysDictValue> dictvalue) {
		this.dictvalue = dictvalue;
	}

	public String getDictname() {
		return dictname;
	}

	public void setDictname(String dictname) {
		this.dictname = dictname;
	}

	public String getDicttype() {
		return dicttype;
	}

	public void setDicttype(String dicttype) {
		this.dicttype = dicttype;
	}

	public String getEditflag() {
		return editflag;
	}

	public void setEditflag(String editflag) {
		this.editflag = editflag;
	}

	public boolean equals(Object o){
		if(o == null || !(o instanceof SysDictIndex)){
			return false;
		}
		SysDictIndex dict = (SysDictIndex)o;
		return dict.getDictcode().intValue() == dictcode.intValue();
	}
	
	public int hashCode(){
		return dictcode.hashCode();
	}
}
