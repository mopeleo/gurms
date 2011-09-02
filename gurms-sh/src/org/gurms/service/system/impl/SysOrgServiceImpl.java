package org.gurms.service.system.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalParam;
import org.gurms.dao.hibernate.system.SysOrgDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.PropertyFilter;
import org.gurms.entity.system.SysOrg;
import org.gurms.service.system.SysOrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysOrgServiceImpl implements SysOrgService {

	private Logger logger = LoggerFactory.getLogger(SysOrgServiceImpl.class);

	@Autowired
	private SysOrgDao sysOrgDao;
	
	public SysOrgDao getSysOrgDao() {
		return sysOrgDao;
	}

	public void setSysOrgDao(SysOrgDao sysOrgDao) {
		this.sysOrgDao = sysOrgDao;
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<SysOrg> query(Map<String, Object> request, PageRequest page) {
		return sysOrgDao.findPage(page, PropertyFilter.buildFromRequestMap(request));
	}

	@Override
	@Transactional(readOnly = true)
	public SysOrg get(String id) {
		return sysOrgDao.get(id);
	}

	@Override
	public PageResult<SysOrg> save(SysOrg org) {
		PageResult<SysOrg> result = new PageResult<SysOrg>();
		try{
			SysOrg parent = org.getParentorg();
			if(parent != null && StringUtils.isNotBlank(parent.getOrgid())){
				if(parent.getOrgid().equals(org.getOrgid())){
					result.setSuccess(false);
					result.setReturnmsg("上级机构不能是自己");
				}else{
					parent = sysOrgDao.get(parent.getOrgid());
					parent.addOrg(org);
					sysOrgDao.save(parent);
				}
			}else{
				sysOrgDao.save(org);
			}
		}catch(Exception e){
			logger.warn("保存机构信息异常", e);
			result.setSuccess(false);
			result.setReturnmsg(e.getMessage());
		}
		return result;
	}

	@Override
	public void delete(String id) {
		sysOrgDao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public SysOrg getRoot() {
		SysOrg root = sysOrgDao.get(GlobalParam.ORG_ROOTID);
//		initOrgs(root);
		return root;
	}

	@Transactional(readOnly = true)
	private void initOrgs(SysOrg root){
		sysOrgDao.initProxyObject(root);
		for(SysOrg org : root.getSuborgs()){
			initOrgs(org);
		}
	}

}
