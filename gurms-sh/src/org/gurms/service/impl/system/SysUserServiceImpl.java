package org.gurms.service.impl.system;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalConfig;
import org.gurms.common.config.GlobalParam;
import org.gurms.common.mapper.ObjectMapper;
import org.gurms.common.util.DateUtil;
import org.gurms.common.util.EncryptUtil;
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

	private static Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

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
		log.info("method :: save(SysUser user) begin......");
		PageResult<SysUser> result = new PageResult<SysUser>();
		if(StringUtils.isNotBlank(user.getSysroleids())){
			String[] roleids = StringUtils.split(user.getSysroleids(), GlobalParam.STRING_SEPARATOR);
			for(String roleid : roleids){
				SysRole role = sysRoleDao.get(Long.parseLong(roleid));
				user.getSysroles().add(role);
			}
		}
        if(StringUtils.isBlank(user.getSysorg().getOrgid())){
            user.setSysorg(null);
        }
		
		SysUser po = sysUserDao.get(user.getUserid());
		po.setSysroles(user.getSysroles());
		po.setUsername(user.getUsername());
		po.setSysorg(user.getSysorg());
		sysUserDao.save(po);
		log.info("user [id] is : " + po.getUserid());
		log.info("method :: save(SysUser user) end......");
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
					SysRole role = sysRoleDao.get(Long.parseLong(roleid));
					user.getSysroles().add(role);
				}
			}
			if(StringUtils.isBlank(user.getSysorg().getOrgid())){
			    user.setSysorg(null);
			}
			if(StringUtils.isBlank(user.getLoginpassword())){
				user.setLoginpassword(EncryptUtil.md5Encode(GlobalConfig.INIT_PASSWORD));
			}
			sysUserDao.save(user);
			
			SysUserInfo userinfo = new SysUserInfo();
			userinfo.setUserid(user.getUserid());
			userinfo.setCreatedate(DateUtil.getCurrentDate());
			sysUserInfoDao.save(userinfo);
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SysUser> query(Map<String, Object> request) {
		return sysUserDao.find(PropertyFilter.buildFromRequestMap(request));
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
			if(GlobalParam.SYSTEM_ROLE == role.getRoleid()){
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
			u.setUptpwdate(DateUtil.getCurrentDate());
			sysUserDao.save(u);
		}
		return result;		
	}

	@Override
	public PageResult<SysUser> login(SysUser user) {
		log.info("method :: login(SysUser user) begin......");
		PageResult<SysUser> result = new PageResult<SysUser>();
		SysUser sessionUser = null;

		SysUser userEntity = sysUserDao.get(user.getUserid());
		//用户不存在
		if(userEntity == null){
			result.setSuccess(false);
			result.setReturnmsg("用户名或密码错误");
		}else{
			SysParam errorcount = sysParamDao.get(GlobalParam.PARAM_ERRORCOUNT);
			SysParam locktime = sysParamDao.get(GlobalParam.PARAM_LOCKTIME);
			String currentDate = DateUtil.getCurrentDate();
			String currentTime = DateUtil.getCurrentTime();
			
			//用户被锁定
			if (GlobalParam.DICT_USERSTSTUS_PWLOCK.equals(userEntity.getUserstatus())) {
				result.setSuccess(false);
				result.setReturnmsg("用户密码已被锁定");
			//密码一致登录成功
			}else if (userEntity.getLoginpassword().equalsIgnoreCase(user.getLoginpassword())) {
				userEntity.setLogincount(userEntity.getLogincount() + 1);
				userEntity.setErrorcount(0);
				userEntity.setErrordate(null);
				userEntity.setErrortime(null);
				
				sessionUser = ObjectMapper.map(userEntity, SysUser.class);
				sessionUser.setLoginip(user.getLoginip());
				sessionUser.setSysroles(null);
				
				userEntity.setLogindate(currentDate);
				userEntity.setLogintime(currentTime);
				userEntity.setOnlineflag(GlobalParam.DICT_ONLINEFLAG_YES);
			//密码错误
			}else{
				//距上次错误时间间隔是否达到错误次数清0条件
				if(StringUtils.isNotBlank(userEntity.getErrordate())&&StringUtils.isNotBlank(userEntity.getErrortime())){
					String lastErrortime = userEntity.getErrordate() + userEntity.getErrortime();
					long dif = System.currentTimeMillis() - DateUtil.parseDate(DateUtil.pattern_fulltime, lastErrortime).getTime();
					if(dif > (Long.parseLong(locktime.getParamvalue()))*3600*1000){
						userEntity.setErrorcount(0);
					}
				}
				//记录密码错误时间和次数
				userEntity.setErrordate(currentDate);
				userEntity.setErrortime(currentTime);
				userEntity.setErrorcount(userEntity.getErrorcount() + 1);
				//时间周期内达到错误次数锁定
				if(userEntity.getErrorcount() == Integer.parseInt(errorcount.getParamvalue())){
					userEntity.setUserstatus(GlobalParam.DICT_USERSTSTUS_PWLOCK);
				}
				result.setSuccess(false);
				result.setReturnmsg("用户名或密码错误");
			}
			
			sysUserDao.save(userEntity);
			
			//记录登录日志
			SysLogLogin sll = new SysLogLogin();
			sll.setUserid(user.getUserid());
			sll.setLogindate(currentDate);
			sll.setLogintime(currentTime);
			sll.setLoginip(user.getLoginip());
			sll.setLoginpassword(user.getLoginpassword());
			sll.setSuccess(result.isSuccess() ? GlobalParam.DICT_YESORNO_YES : GlobalParam.DICT_YESORNO_NO);
			sysLogLoginDao.save(sll);
		}
		result.addResult(sessionUser);
		log.info("method :: login(SysUser user) end......");
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public SysUserInfo getUserInfo(String userid) {
		return sysUserInfoDao.get(userid);
	}

	@Override
	public PageResult<SysUserInfo> saveUserInfo(SysUserInfo userinfo) {
		PageResult<SysUserInfo> page = new PageResult<SysUserInfo>();
		SysUserInfo info = sysUserInfoDao.get(userinfo.getUserid());
		if(info == null){
			userinfo.setCreatedate(DateUtil.getCurrentDate());
			sysUserInfoDao.save(userinfo);
		}else{
			String createDate = info.getCreatedate();
			ObjectMapper.copy(userinfo, info);
			info.setCreatedate(createDate);
			sysUserInfoDao.save(info);
		}
		return page;
	}

	@Override
	@Transactional(readOnly = true)
	public SysUserConfig getUserConfig(String userid) {
		return sysUserConfigDao.get(userid);
	}

	@Override
	public PageResult<SysUser> saveUserConfig(SysUserConfig config) {
		PageResult<SysUser> page = new PageResult<SysUser>();
		String[] menus = config.getSysmenuids();
		if(menus != null && menus.length > 0){
			String fastmenu = StringUtils.join(menus, GlobalParam.STRING_SEPARATOR);
			config.setFastmenu(fastmenu);
		}
		sysUserConfigDao.save(config);
		return page;
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
