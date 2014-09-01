package org.gurms.service.system;

import java.util.List;
import java.util.Map;

import org.gurms.common.validate.GurmsValid;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysDictIndex;
import org.gurms.entity.system.SysDictValue;
import org.gurms.entity.system.SysDictValueId;

public interface SysDictService {

	public PageResult<SysDictValue> queryDict(Map<String, Object> request, PageRequest page);
	
	public SysDictValue getDict(SysDictValueId id);
	
    public List<SysDictValue> getDict(Integer dictcode);
    
    public List<SysDictValue> getDict(Integer dictcode, String prefix);
    
	public Map<String,List<SysDictValue>> getDictMap();

	@GurmsValid(type=SysDictValue.class)
	public PageResult<SysDictValue> saveDict(SysDictValue dict);
	
//	public PageResult<SysDictValue> deleteDict(SysDictValuePk id);
	
	public SysDictIndex getDictIndex(Integer dictcode);

	public List<SysDictIndex> getDictIndex();
	
	public void initDictPinyin();
}
