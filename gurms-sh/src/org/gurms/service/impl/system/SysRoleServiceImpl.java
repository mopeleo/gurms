package org.gurms.service.impl.system;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalParam;
import org.gurms.common.util.DateUtil;
import org.gurms.dao.hibernate.system.SysMenuDao;
import org.gurms.dao.hibernate.system.SysRoleDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.PropertyFilter;
import org.gurms.entity.system.SysMenu;
import org.gurms.entity.system.SysRole;
import org.gurms.service.system.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;	
	
	@Autowired
	private SysMenuDao sysMenuDao;
	
	public SysRoleDao getSysRoleDao() {
		return sysRoleDao;
	}

	public void setSysRoleDao(SysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
	}

	public SysMenuDao getSysMenuDao() {
		return sysMenuDao;
	}

	public void setSysMenuDao(SysMenuDao sysMenuDao) {
		this.sysMenuDao = sysMenuDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SysRole> query(Map<String, Object> request) {
		return sysRoleDao.find(PropertyFilter.buildFromRequestMap(request));
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<SysRole> query(Map<String, Object> request, PageRequest page) {
		return sysRoleDao.findPage(page, PropertyFilter.buildFromRequestMap(request));
	}

	@Override
	@Transactional(readOnly = true)
	public SysRole getById(Long id) {
		return sysRoleDao.getById(id);
	}

	@Override
	public PageResult<SysRole> save(SysRole role) {
		PageResult<SysRole> page = new PageResult<SysRole>();
		if(StringUtils.isBlank(role.getRolestatus())){
			String today = DateUtil.getCurrentDate();
			if(DateUtil.dateBetween(today, role.getStartdate(), role.getEnddate())){
				role.setRolestatus(GlobalParam.DICT_VALID_YES);
			}else{
				role.setRolestatus(GlobalParam.DICT_VALID_NO);
			}
		}
		if(role.getSysmenuids() != null){
			for(String menuid : role.getSysmenuids()){
				SysMenu menu = sysMenuDao.getById(menuid);
				role.getSysmenus().add(menu);
			}
		}
		sysRoleDao.save(role);
		
		/* activiti test
		ProcessEngine pe = ActivitiTest.getProcessEngine();
		RepositoryService rs = pe.getRepositoryService();
		String workflowId = rs.createDeployment().addClasspathResource("workflow/activititest.xml").deploy().getId();
		System.out.println(workflowId);
		*/
		
		return page;
	}

	@Override
	public PageResult<SysRole> insert(SysRole role) {
		PageResult<SysRole> page = new PageResult<SysRole>();
		if(sysRoleDao.findUniqueBy("rolename", role.getRolename()) == null){
			page = save(role);
		}else{
			page.setSuccess(false);
			page.setReturnmsg("角色[" + role.getRolename() + "]已存在");
		}
		return page;
	}

	@Override
	public void deleteById(Long id) {
		sysRoleDao.deleteById(id);
	}

	@Override
	public List<SysRole> getAll() {
		return sysRoleDao.getAll();
	}	

	@Override
	public void freshRole() {
		sysRoleDao.freshRole();
	}	

}
