package ${project}.entity<#if model?exists>.${model}</#if>;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
<#if (table.keys?size > 1) >
import javax.persistence.IdClass;
</#if>
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

<#macro type datatype precision><#if datatype?contains("CHAR")>String<#elseif datatype=="INT">Integer<#elseif precision != "">Double<#else>Long</#if></#macro>
@Entity
<#if (table.keys?size > 1) >
@IdClass(${entity}Id.class)
</#if>
@Table(name = "${table.code}")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ${entity} implements Serializable {

<#list table.keys as column>
	private <@type datatype=column.datatype precision=column.precision /> ${column.code};    //${column.comment}
</#list>

<#list table.columns as column>
	private <@type datatype=column.datatype precision=column.precision /> ${column.code};    //${column.comment}
</#list>

<#list table.keys as column>
	@Id
	public <@type datatype=column.datatype precision=column.precision /> get${column.code?cap_first}() {
		return ${column.code};
	}

	public void set${column.code?cap_first}(<@type datatype=column.datatype precision=column.precision /> ${column.code}) {
		this.${column.code} = ${column.code};
	}
	
</#list>

<#list table.columns as column>
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
			EqualsBuilder eb = new EqualsBuilder();
		<#list table.keys as column>
			eb.append(this.get${column.code?cap_first}(), entity.get${column.code?cap_first}());
		</#list>
			return eb.isEquals();
		}
	}

	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
	<#list table.keys as column>
		hcb.append(${column.code});
	</#list>
		return hcb.hashCode();
	}
}
