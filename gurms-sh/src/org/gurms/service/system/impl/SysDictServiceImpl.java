package org.gurms.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gurms.dao.hibernate.system.SysDictDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PropertyFilter;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysDict;
import org.gurms.entity.system.SysDictPK;
import org.gurms.service.system.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysDictServiceImpl implements SysDictService {

	@Autowired
	private SysDictDao sysDictDao;

	public SysDictDao getSysDictDao() {
		return sysDictDao;
	}

	public void setSysDictDao(SysDictDao sysDictDao) {
		this.sysDictDao = sysDictDao;
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<SysDict> query(Map<String, Object> request, PageRequest page) {
		if(!page.isOrderBySetted()){
			page.setOrderBy("dicttype,dictorder");
		}
		return sysDictDao.findPage(page, PropertyFilter.buildFromRequestMap(request));
	}

	@Override
	@Transactional(readOnly = true)
	public SysDict get(SysDictPK id) {
		return sysDictDao.get(id);
	}

	@Override
	public PageResult<SysDict> save(SysDict dict) {
		PageResult<SysDict> result = new PageResult<SysDict>();
		try{
			sysDictDao.save(dict);
			result.addResult(dict);
		}catch(Exception e){
			result.setSuccess(false);
			result.setReturnmsg(e.getMessage());
		}
		return result;
	}

	@Override
	public PageResult<SysDict> delete(SysDictPK id) {
		PageResult<SysDict> result = new PageResult<SysDict>();
		try{
			sysDictDao.delete(id);
		}catch(Exception e){
			result.setSuccess(false);
			result.setReturnmsg(e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, List<SysDict>> getDictMap() {
		Map<String, List<SysDict>> map = new HashMap<String, List<SysDict>>();
		List<SysDict> list = sysDictDao.getAll();
		if(list != null){
			Iterator<SysDict> it = list.iterator();
			while(it.hasNext()){
				SysDict dict = it.next();
				List<SysDict> dicttypeList = map.get(dict.getDicttype());
				if(dicttypeList == null){
					dicttypeList = new ArrayList<SysDict>();
					dicttypeList.add(dict);
					map.put(dict.getDicttype(), dicttypeList);
				}else{
					if(!dicttypeList.contains(dict)){
						dicttypeList.add(dict);
					}
				}
				
				it.remove();
			}
		}
		return map;
	}

}
