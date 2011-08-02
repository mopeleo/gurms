package org.gurms.service.system.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalMessage;
import org.gurms.common.exception.GurmsException;
import org.gurms.dao.hibernate.system.SysOrgDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.PropertyFilter;
import org.gurms.entity.PropertyFilter.MatchType;
import org.gurms.entity.system.SysMenu;
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
			if(org.getParentorg() != null && StringUtils.isNotBlank(org.getParentorg().getOrgid())){
				SysOrg parent = sysOrgDao.get(org.getParentorg().getOrgid());
				parent.addOrg(org);
				sysOrgDao.save(parent);
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
		List<SysOrg> result = sysOrgDao.findBy("parentorg", null, MatchType.NULL);
		if(result == null || result.size() != 1){
			logger.warn(GlobalMessage.getMessage("9100"));
			throw new GurmsException(GlobalMessage.getMessage("9100"));
		}
		SysOrg root = result.get(0);
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
