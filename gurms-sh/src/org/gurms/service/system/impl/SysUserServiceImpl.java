package org.gurms.service.system.impl;

import java.text.ParseException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalConfig;
import org.gurms.common.config.GlobalParam;
import org.gurms.common.util.EncryptUtil;
import org.gurms.common.util.FormatUtil;
import org.gurms.common.util.ObjectMapper;
import org.gurms.dao.hibernate.system.SysLogLoginDao;
import org.gurms.dao.hibernate.system.SysParamDao;
import org.gurms.dao.hibernate.system.SysRoleDao;
import org.gurms.dao.hibernate.system.SysUserConfigDao;
import org.gurms.dao.hibernate.system.SysUserDao;
import org.gurms.dao.hibernate.system.SysUserInfoDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.PropertyFilter;
import org.gurms.entity.system.SysLogLogin;
import org.gurms.entity.system.SysParam;
import org.gurms.entity.system.SysRole;
import org.gurms.entity.system.SysUser;
import org.gurms.entity.system.SysUserConfig;
import org.gurms.entity.system.SysUserInfo;
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

	@Autowired
	private SysParamDao sysParamDao;
	
	@Autowired
	private SysLogLoginDao sysLogLoginDao;
	
	@Autowired
	private SysUserInfoDao sysUserInfoDao;

	@Autowired
	private SysUserConfigDao sysUserConfigDao;
	
	@Transactional(readOnly = true)
	public SysUser get(String id){
		return sysUserDao.get(id);
	}
	
	public PageResult<SysUser> save(SysUser user) {
		PageResult<SysUser> result = new PageResult<SysUser>();
		if(StringUtils.isNotBlank(user.getSysroleids())){
			String[] roleids = StringUtils.split(user.getSysroleids(), GlobalParam.STRING_SEPARATOR);
			for(String roleid : roleids){
				SysRole role = sysRoleDao.get(roleid);
				user.getSysroles().add(role);
			}
		}
		
		SysUser po = sysUserDao.get(user.getUserid());
		po.setSysroles(user.getSysroles());
		po.setUsername(user.getUsername());
		po.setSysorg(user.getSysorg());
		sysUserDao.save(po);
		return result;
	}

	public PageResult<SysUser> insert(SysUser user) {
		PageResult<SysUser> result = new PageResult<SysUser>();
		if(sysUserDao.get(user.getUserid()) != null){
			result.setSuccess(false);
			result.setReturnmsg("用户[" + user.getUserid() + "]已存在");
		}else{
			if(StringUtils.isNotBlank(user.getSysroleids())){
				String[] roleids = StringUtils.split(user.getSysroleids(), GlobalParam.STRING_SEPARATOR);
				for(String roleid : roleids){
					SysRole role = sysRoleDao.get(roleid);
					user.getSysroles().add(role);
				}
			}
			if(StringUtils.isBlank(user.getLoginpassword())){
				user.setLoginpassword(EncryptUtil.md5Encode(GlobalConfig.INIT_PASSWORD));
			}
			sysUserDao.save(user);
			
			SysUserInfo userinfo = new SysUserInfo();
			userinfo.setUserid(userinfo.getUserid());
			userinfo.setCreatedate(FormatUtil.getCurrentDate());
			sysUserInfoDao.save(userinfo);
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<SysUser> query(Map<String, Object> request, PageRequest page) {
		return sysUserDao.findPage(page, PropertyFilter.buildFromRequestMap(request));
	}

	@Override
	public PageResult<SysUser> delete(String userid) {
		PageResult<SysUser> result = new PageResult<SysUser>();
		SysUser user = sysUserDao.get(userid);
		if(GlobalParam.DICT_ONLINEFLAG_YES.equals(user.getOnlineflag())){
			result.setSuccess(false);
			result.setReturnmsg("在线用户不能删除");
		}else{
			sysUserDao.delete(userid);
			sysUserInfoDao.delete(userid);
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isAdmin(String userid) {
		SysUser user = sysUserDao.get(userid);
		boolean isAdmin = false;
		for(SysRole role : user.getSysroles()){
			if(GlobalParam.SYSTEM_ROLE.equals(role.getRoleid())){
				isAdmin = true;
				break;
			}
		}
		
		return isAdmin;
	}

	@Override
	public PageResult<SysUser> setPassword(SysUser user) {
		PageResult<SysUser> result = new PageResult<SysUser>();
		SysUser u = sysUserDao.get(user.getUserid());
		if(!u.getLoginpassword().equalsIgnoreCase(user.getOldpassword())){
			result.setReturnmsg("输入的原密码错误!");
			result.setSuccess(false);			
		}else{
			u.setLoginpassword(user.getLoginpassword());
			sysUserDao.save(u);
		}
		return result;		
	}

	@Override
	public PageResult<SysUser> login(SysUser user) {
		
		PageResult<SysUser> result = new PageResult<SysUser>();
		SysUser sessionUser = null;

		SysUser u = sysUserDao.get(user.getUserid());
		//用户不存在
		if(u == null){
			result.setSuccess(false);
			result.setReturnmsg("用户名或密码错误");
		}else{
			SysParam errorcount = sysParamDao.get(GlobalParam.PARAM_ERRORCOUNT);
			SysParam locktime = sysParamDao.get(GlobalParam.PARAM_LOCKTIME);
			String currentDate = FormatUtil.getCurrentDate();
			String currentTime = FormatUtil.getCurrentTime();
			
			//用户被锁定
			if (GlobalParam.DICT_USERSTSTUS_PWLOCK.equals(user.getUserstatus())) {
				result.setSuccess(false);
				result.setReturnmsg("用户密码已被锁定");
			//密码一致登录成功
			}else if (user.getLoginpassword().equalsIgnoreCase(u.getLoginpassword())) {
				u.setLogincount(u.getLogincount() + 1);
				u.setErrorcount(0);
				u.setErrordate(null);
				u.setErrortime(null);
				
				sessionUser = ObjectMapper.map(u, SysUser.class);
				sessionUser.setLoginip(user.getLoginip());
				sessionUser.setSysroles(null);
				
				u.setLogindate(currentDate);
				u.setLogintime(currentTime);
				u.setOnlineflag(GlobalParam.DICT_ONLINEFLAG_YES);
			//密码错误
			}else{
				//距上次锁定间隔是否达到清0条件
				if(StringUtils.isNotBlank(u.getLogindate())&&StringUtils.isNotBlank(u.getLogintime())){
					String logtime = u.getLogindate() + u.getLogintime();
					long dif = 0;
					try {
						dif = System.currentTimeMillis() - FormatUtil.getDate(FormatUtil.pattern_fulltime, logtime).getTime();
					} catch (ParseException e) {
						logger.warn("密码错误时间格式错误", e);
						result.setReturnmsg(e.getMessage());
						result.setSuccess(false);
						return result;
					}
					if(dif > (Long.parseLong(locktime.getParamvalue()))*3600*1000){
						u.setErrorcount(0);
					}
				}
				//记录时间周期内第一次错误时间
				if(u.getErrorcount() == 0){
					u.setErrordate(currentDate);
					u.setErrortime(currentTime);
				}
				u.setErrorcount(u.getErrorcount() + 1);
				//时间周期内达到错误次数锁定
				if(u.getErrorcount() == Integer.parseInt(errorcount.getParamvalue())){
					u.setUserstatus(GlobalParam.DICT_USERSTSTUS_PWLOCK);
				}
				result.setSuccess(false);
				result.setReturnmsg("用户名或密码错误");
			}
			
			sysUserDao.save(u);
			
			//记录登录日志
			SysLogLogin sll = new SysLogLogin();
			sll.setUserid(u.getUserid());
			sll.setLogindate(currentDate);
			sll.setLogintime(currentTime);
			sll.setLoginip(user.getLoginip());
			sll.setLoginpassword(user.getLoginpassword());
			sll.setSuccess(result.isSuccess() ? GlobalParam.DICT_YESORNO_YES : GlobalParam.DICT_YESORNO_NO);
			sysLogLoginDao.save(sll);
		}
		result.addResult(sessionUser);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public SysUserInfo getUserInfo(String userid) {
		return sysUserInfoDao.get(userid);
	}

	@Override
	public void saveUserInfo(SysUserInfo userinfo) {
		SysUserInfo info = sysUserInfoDao.get(userinfo.getUserid());
		if(info == null){
			userinfo.setCreatedate(FormatUtil.getCurrentDate());
			sysUserInfoDao.save(userinfo);
		}else{
			String createDate = info.getCreatedate();
			ObjectMapper.map(userinfo, info);
			info.setCreatedate(createDate);
			sysUserInfoDao.save(info);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public SysUserConfig getUserConfig(String userid) {
		return sysUserConfigDao.get(userid);
	}

	@Override
	public void saveUserConfig(SysUserConfig config) {
		sysUserConfigDao.save(config);
	}
	
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

	public SysParamDao getSysParamDao() {
		return sysParamDao;
	}

	public void setSysParamDao(SysParamDao sysParamDao) {
		this.sysParamDao = sysParamDao;
	}

	public SysLogLoginDao getSysLogLoginDao() {
		return sysLogLoginDao;
	}

	public void setSysLogLoginDao(SysLogLoginDao sysLogLoginDao) {
		this.sysLogLoginDao = sysLogLoginDao;
	}

	public SysUserInfoDao getSysUserInfoDao() {
		return sysUserInfoDao;
	}

	public void setSysUserInfoDao(SysUserInfoDao sysUserInfoDao) {
		this.sysUserInfoDao = sysUserInfoDao;
	}

	public SysUserConfigDao getSysUserConfigDao() {
		return sysUserConfigDao;
	}

	public void setSysUserConfigDao(SysUserConfigDao sysUserConfigDao) {
		this.sysUserConfigDao = sysUserConfigDao;
	}
	
}
