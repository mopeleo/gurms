package ${project}.entity<#if model?exists>.${model}</#if>;

import java.io.Serializable;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

<#macro type datatype precision><#if datatype?contains("CHAR")>String<#elseif datatype=="INT">Integer<#elseif precision != "">Double<#else>Long</#if></#macro>
public class ${entity}Id implements Serializable {

<#list table.keys as column>
	private <@type datatype=column.datatype precision=column.precision /> ${column.code};    //${column.comment}
</#list>

	public ${entity}Id(){}
	
	public ${entity}Id(<#list table.keys as column><@type datatype=column.datatype precision=column.precision /> ${column.code}<#if (column_index+1)!=table.keys?size>, </#if></#list>){
	<#list table.keys as column>
		this.${column.code} = ${column.code};
	</#list>
	}
	
<#list table.keys as column>
	public <@type datatype=column.datatype precision=column.precision /> get${column.code?cap_first}() {
		return ${column.code};
	}

	public void set${column.code?cap_first}(<@type datatype=column.datatype precision=column.precision /> ${column.code}) {
		this.${column.code} = ${column.code};
	}
	
</#list>

	@Transient
	public boolean isNull(){
		return <#list table.keys as column><#if column.datatype?contains("CHAR")>StringUtils.isBlank(${column.code})<#else>(${column.code} == null)<#if (column_index+1)!=table.keys?size>|| </#if></#if></#list>;
	}
	
	@Override
	public String toString(){
		return <#list table.keys as column>"${column.code}: '" + ${column.code} + "'"<#if (column_index+1)!=table.keys?size> + ", " + </#if></#list>;
	}
	
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
		return hcb.toHashCode();
	}
}
