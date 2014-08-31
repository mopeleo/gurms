package org.gurms.service.impl.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gurms.common.util.ChineseUtil;
import org.gurms.dao.hibernate.system.SysDictIndexDao;
import org.gurms.dao.hibernate.system.SysDictValueDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.PropertyFilter;
import org.gurms.entity.system.SysDictIndex;
import org.gurms.entity.system.SysDictValue;
import org.gurms.entity.system.SysDictValueId;
import org.gurms.service.system.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysDictServiceImpl implements SysDictService {

	@Autowired
	private SysDictValueDao sysDictValueDao;
	
	@Autowired
	private SysDictIndexDao sysDictIndexDao;


	public SysDictValueDao getSysDictValueDao() {
		return sysDictValueDao;
	}

	public void setSysDictValueDao(SysDictValueDao sysDictValueDao) {
		this.sysDictValueDao = sysDictValueDao;
	}

	public SysDictIndexDao getSysDictIndexDao() {
		return sysDictIndexDao;
	}

	public void setSysDictIndexDao(SysDictIndexDao sysDictIndexDao) {
		this.sysDictIndexDao = sysDictIndexDao;
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<SysDictValue> queryDict(Map<String, Object> request, PageRequest page) {
		return sysDictValueDao.findPage(page, PropertyFilter.buildFromRequestMap(request));
	}

	@Override
	@Transactional(readOnly = true)
	public SysDictValue getDict(SysDictValueId id) {
		return sysDictValueDao.get(id);
	}

    @Override
    @Transactional(readOnly = true)
    public List<SysDictValue> getDict(int dictcode) {
        return sysDictValueDao.findBy("dictcode", dictcode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysDictValue> getDict(int dictcode, String prefix) {
        String pinyin = prefix.toUpperCase();
        return sysDictValueDao.findByPrefix(dictcode, pinyin);
    }

	@Override
	public PageResult<SysDictValue> saveDict(SysDictValue dict) {
		PageResult<SysDictValue> result = new PageResult<SysDictValue>();
		sysDictValueDao.save(dict);
		result.addResult(dict);
		return result;
	}

	public PageResult<SysDictValue> deleteDict(SysDictValueId id) {
		PageResult<SysDictValue> result = new PageResult<SysDictValue>();
		sysDictValueDao.delete(id);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, List<SysDictValue>> getDictMap() {
		Map<String, List<SysDictValue>> map = new HashMap<String, List<SysDictValue>>();
		List<SysDictValue> list = sysDictValueDao.getAll();
		if(list != null){
			Iterator<SysDictValue> it = list.iterator();
			while(it.hasNext()){
				SysDictValue dict = it.next();
				List<SysDictValue> dictvalueList = map.get(String.valueOf(dict.getDictcode()));
				if(dictvalueList == null){
					dictvalueList = new ArrayList<SysDictValue>();
					dictvalueList.add(dict);
					map.put(String.valueOf(dict.getDictcode()), dictvalueList);
				}else{
					if(!dictvalueList.contains(dict)){
						dictvalueList.add(dict);
					}
				}
				
				it.remove();
			}
		}
		return map;
	}

	@Override
	@Transactional(readOnly = true)
	public SysDictIndex getDictIndex(int dictcode) {
		return sysDictIndexDao.get(dictcode);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SysDictIndex> getDictIndex() {
		return sysDictIndexDao.getAll();
	}

	public void initDictPinyin(){
	    List<SysDictValue> list = sysDictValueDao.fintPinYinIsNull();
	    for(SysDictValue dictValue : list){
	        String pinyin = ChineseUtil.getFirstLeter(dictValue.getItemname());
	        pinyin += "," + ChineseUtil.getAllLeter(dictValue.getItemname());
	        if(pinyin.length()>64){
	            pinyin = pinyin.substring(0,63);
	        }
	        dictValue.setPinyin(pinyin);
	    }
	}
}
