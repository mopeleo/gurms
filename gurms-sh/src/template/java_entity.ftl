package ${project}.entity<#if model?exists>.${model}</#if>;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

<#assign entityid="">
<#assign id_is_string=true>
<#if table.keys[0].datatype?contains("CHAR")><#else><#assign id_is_string=false></#if>

<#macro type datatype precision><#if datatype?contains("CHAR")>String<#elseif datatype=="INT">int<#elseif precision != "">double<#else>long</#if></#macro>
@Entity
@Table(name = "${table.code}")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ${entity} implements Serializable {

<#list table.columns as column>
	private <@type datatype=column.datatype precision=column.precision /> ${column.code};    //${column.comment}
</#list>

<#list table.columns as column>
	<#if table.keys?seq_contains(column)>
		<#assign entityid=column.code>
	@Id
	</#if>
	public <@type datatype=column.datatype precision=column.precision /> get${column.code?cap_first}() {
		return ${column.code};
	}

	public void set${column.code?cap_first}(<@type datatype=column.datatype precision=column.precision /> ${column.code}) {
		this.${column.code} = ${column.code};
	}

</#list>
	public boolean equals(Object o) {
		if(o == null || !(o instanceof ${entity})){
			return false;
		}else{
			${entity} entity = (${entity})o;
		<#if id_is_string>
			if(entity.get${entityid?cap_first}() == null){
				return false;
			}else{
				return entity.get${entityid?cap_first}().equals(${entityid});
			}
		<#else>
			return entity.get${entityid?cap_first}()==${entityid};
		</#if>
		}
	}

	public int hashCode() {
	<#if id_is_string>
		if(${entityid} == null)
			return super.hashCode();
		return ${entityid}.hashCode();
	<#else>
		return String.valueOf(${entityid}).hashCode();
	</#if>
	}
}
