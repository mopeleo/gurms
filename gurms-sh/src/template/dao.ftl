package ${package}.dao.hiberante<#if model?exists>.${model}</#if>;

import org.gurms.dao.hibernate.HibernateDao;
import ${package}.entity<#if model?exists>.${model}</#if>.${entity};
import org.springframework.stereotype.Repository;

@Repository
public class ${entity}Dao extends HibernateDao<${entity}>{
}
