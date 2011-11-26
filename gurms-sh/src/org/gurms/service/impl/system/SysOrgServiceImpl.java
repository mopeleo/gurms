package org.gurms.service.impl.system;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalParam;
import org.gurms.dao.hibernate.system.SysOrgDao;
import org.gurms.dao.hibernate.system.SysSerialnoDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.PropertyFilter;
import org.gurms.entity.system.SysOrg;
import org.gurms.service.system.SysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysOrgServiceImpl implements SysOrgService {

	@Autowired
	private SysOrgDao sysOrgDao;
	
	@Autowired
	private SysSerialnoDao serialnoDao;
	
	public SysOrgDao getSysOrgDao() {
		return sysOrgDao;
	}

	public void setSysOrgDao(SysOrgDao sysOrgDao) {
		this.sysOrgDao = sysOrgDao;
	}

	public SysSerialnoDao getSerialnoDao() {
		return serialnoDao;
	}

	public void setSerialnoDao(SysSerialnoDao serialnoDao) {
		this.serialnoDao = serialnoDao;
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
		
		SysOrg oldParent = null;
		if(StringUtils.isBlank(org.getOrgid())){
			long nextvalue = serialnoDao.next(GlobalParam.SERIAL_SYS_ORG);
			org.setOrgid(String.valueOf(nextvalue));
		}else{
			if(org.getOrgid().equals(GlobalParam.ORG_ROOTID)){
				org.setParentorg(null);
			}
			oldParent = sysOrgDao.get(org.getOrgid()).getParentorg();
		}
		SysOrg parent = org.getParentorg();
		if(parent != null && StringUtils.isNotBlank(parent.getOrgid())){
			if(parent.getOrgid().equals(org.getOrgid())){
				result.setSuccess(false);
				result.setReturnmsg("上级机构不能是自己");
			}else{
				parent = sysOrgDao.get(parent.getOrgid());
				//同一父节点下不能同名
				List<SysOrg> sons = parent.getSuborgs();
				for(SysOrg son : sons){
					if(!son.getOrgid().equals(org.getOrgid()) 
							&& son.getShortname().equals(org.getShortname())){
						result.setSuccess(false);
						result.setReturnmsg(org.getShortname() + "己存在");
						return result;
					}
				}
				
				//如果更换了父节点
				if(oldParent!= null && !parent.equals(oldParent)){
					oldParent.getSuborgs().remove(org);
				}
				//若包含，则更新子节点
				if(sons.contains(org)){
					sons.remove(org);
				}
				parent.addOrg(org);
				sysOrgDao.merge(parent);
			}
		}else{
			sysOrgDao.save(org);
		}
		return result;
	}

	@Override
	public PageResult<SysOrg> delete(String id) {
		PageResult<SysOrg> result = new PageResult<SysOrg>();
		if(StringUtils.isBlank(id)){
			result.setSuccess(false);
			result.setReturnmsg("机构ID不能为空");
		}else if(id.equals(GlobalParam.ORG_ROOTID)){
			result.setSuccess(false);
			result.setReturnmsg("根机构不能删除");
		}else{
			sysOrgDao.delete(id);
		}
		return result;
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