package ${package}.dao.hiberante.${model};

import org.gurms.dao.hibernate.HibernateDao;
import ${package}.entity.${model}.${entity};
import org.springframework.stereotype.Repository;

@Repository
public class ${entity}Dao extends HibernateDao<${entity}>{
}
