package ${project}.dao.hibernate<#if model?exists>.${model}</#if>;

import org.gurms.dao.hibernate.HibernateDao;
import ${project}.entity<#if model?exists>.${model}</#if>.${entity};
import org.springframework.stereotype.Repository;

@Repository
public class ${entity}Dao extends HibernateDao<${entity}>{
}
