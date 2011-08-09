package org.gurms.service.system.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalConfig;
import org.gurms.common.config.GlobalParam;
import org.gurms.common.util.EncryptUtil;
import org.gurms.common.util.FormatUtil;
import org.gurms.dao.hibernate.system.SysRoleDao;
import org.gurms.dao.hibernate.system.SysUserDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.PropertyFilter;
import org.gurms.entity.system.SysRole;
import org.gurms.entity.system.SysUser;
import org.gurms.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SysUserServiceImpl implements SysUserService{

	private Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
	private SysRoleDao sysRoleDao;

	public SysUserDao getSysUserDao() {
		return sysUserDao;
	}

	public SysRoleDao getSysRoleDao() {
		return sysRoleDao;
	}

	public void setSysRoleDao(SysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
	}

	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	@Transactional(readOnly = true)
	public SysUser get(String id){
		return sysUserDao.get(id);
	}
	
	public PageResult<SysUser> save(SysUser user) {
		if(StringUtils.isBlank(user.getUserid())){
			String pw = EncryptUtil.md5Encode(user.getLoginpassword());
			user.setLoginpassword(pw);
		}
		user.setLogintime(FormatUtil.getCurrentTime());
		user.setLogindate(FormatUtil.getCurrentDate());
		
		PageResult<SysUser> result = new PageResult<SysUser>();
		try{
			if(StringUtils.isNotBlank(user.getSysroleids())){
				String[] roleids = StringUtils.split(user.getSysroleids(), GlobalParam.STRING_SEPARATOR);
				for(String roleid : roleids){
					SysRole role = sysRoleDao.get(roleid);
					user.getSysroles().add(role);
				}
			}
			sysUserDao.save(user);
		}catch(Exception e){
			logger.warn("保存用户信息异常", e);
			result.setSuccess(false);
			result.setReturnmsg(e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<SysUser> query(Map<String, Object> request, PageRequest page) {
		return sysUserDao.findPage(page, PropertyFilter.buildFromRequestMap(request));
	}

	@Override
	public void delete(String userid) {
		sysUserDao.delete(userid);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isAdmin(String userid) {
		SysUser user = sysUserDao.get(userid);
		boolean isAdmin = false;
		for(SysRole role : user.getSysroles()){
			if(GlobalConfig.ID_SYS_ROLE_SYSTEM.equals(role.getRoleid())){
				isAdmin = true;
				break;
			}
		}
		
		return isAdmin;
	}

	@Override
	public PageResult<SysUser> setPassword(SysUser user) {
		PageResult<SysUser> result = new PageResult<SysUser>();
		
		try{
			SysUser u = sysUserDao.get(user.getUserid());
			String pw = EncryptUtil.md5Encode(user.getOldpassword());
			if(!u.getLoginpassword().equalsIgnoreCase(pw)){
				result.setReturnmsg("输入的原密码错误!");
				result.setSuccess(false);			
			}else{
				pw = EncryptUtil.md5Encode(user.getLoginpassword());
				u.setLoginpassword(pw);
				sysUserDao.save(u);
			}
		}catch(Exception e){
			logger.warn("修改密码异常", e);
			result.setReturnmsg(e.getMessage());
			result.setSuccess(false);			
		}
		
		return result;		
	}
	
}
