package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.gurms.entity.Logable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Logable
@Entity
@IdClass(SysDictPK.class)
@Table(name = "sys_dict_value")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysDictValue implements Serializable {
	
	private int dictcode;
	private String dictitem;
	private String itemname;
	private String pinyin;
	private int dictorder;
	
	private SysDictIndex dictindex;

	public SysDictValue(){}
	
	public SysDictValue(SysDictPK pk){
		this.dictitem = pk.getDictitem();
		this.dictcode = pk.getDictcode();
	}
	
	public SysDictValue(int dictcode, String dictitem){
		this.dictitem = dictitem;
		this.dictcode = dictcode;
	}
	
	@Id
	@Column(insertable=false, updatable=false)
	public int getDictcode() {
		return dictcode;
	}

	public void setDictcode(int dictcode) {
		this.dictcode = dictcode;
	}

	@Id
	public String getDictitem() {
		return dictitem;
	}

	public void setDictitem(String dictitem) {
		this.dictitem = dictitem;
	}

	@ManyToOne
	@JoinColumn(name = "dictcode")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public SysDictIndex getDictindex() {
		return dictindex;
	}

	public void setDictindex(SysDictIndex dictindex) {
		this.dictindex = dictindex;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public int getDictorder() {
		return dictorder;
	}

	public void setDictorder(int dictorder) {
		this.dictorder = dictorder;
	}
	
	public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public boolean equals(Object o) {
		if (o == null || !(o instanceof SysDictPK)) {
			return false;
		} else {
			SysDictPK pk = (SysDictPK) o;
			return new EqualsBuilder().append(pk.getDictitem(), dictitem)
					.append(pk.getDictcode(), dictcode).isEquals();
		}
	}

	public int hashCode() {
		return new HashCodeBuilder().append(dictitem).append(dictcode).hashCode();
	}
}
