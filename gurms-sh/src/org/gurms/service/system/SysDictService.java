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

	public PageResult<SysDictValue> queryDict(Map<String, Object> request, PageRequest page);
	
	public SysDictValue getDict(SysDictPK id);
	
    public List<SysDictValue> getDict(int dictcode);
    
    public List<SysDictValue> getDict(int dictcode, String prefix);
    
	public Map<String,List<SysDictValue>> getDictMap();

	@GurmsValid(type=SysDictValue.class)
	public PageResult<SysDictValue> saveDict(SysDictValue dict);
	
//	public PageResult<SysDictValue> deleteDict(SysDictPK id);
	
	public SysDictIndex getDictIndex(int dictcode);

	public List<SysDictIndex> getDictIndex();
}
