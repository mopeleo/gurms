package org.gurms.service.system;

import java.util.List;
import java.util.Map;

import org.gurms.common.validate.GurmsValid;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysDictIndex;
import org.gurms.entity.system.SysDictPK;
import org.gurms.entity.system.SysDictValue;

public interface SysDictService {

	public PageResult<SysDictValue> query(Map<String, Object> request, PageRequest page);
	
	public SysDictValue get(SysDictPK id);
	
//	public List<SysDictValue> getList(int dictitem);
	
	public Map<String,List<SysDictValue>> getDictMap();

	@GurmsValid(type=SysDictValue.class)
	public PageResult<SysDictValue> save(SysDictValue dict);
	
	public PageResult<SysDictValue> delete(SysDictPK id);
	
//	public SysDictIndex getDictIndex(int dictitem);
//
	public List<SysDictIndex> getDictIndex();
//	
//	public List<SysDictIndex> getDictIndex(String dicttype);
}
