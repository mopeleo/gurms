package ${project}.dao.hiberante.${model};

import org.gurms.dao.hibernate.HibernateDao;
import ${project}.entity.${model}.${entity};
import org.springframework.stereotype.Repository;

@Repository
public class ${entity}Dao extends HibernateDao<${entity}>{
}
