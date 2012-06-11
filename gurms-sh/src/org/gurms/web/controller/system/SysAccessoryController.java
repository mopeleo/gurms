package org.gurms.web.controller.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalParam;
import org.gurms.common.util.CommonUtil;
import org.gurms.common.util.DateUtil;
import org.gurms.common.util.EncryptUtil;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysAccessory;
import org.gurms.entity.system.SysUser;
import org.gurms.service.system.SysAccessoryService;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;
import org.gurms.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class SysAccessoryController extends BaseController {
	
	public static final String ACCESSORY_PULBIC = "/sysaccessory/publics";
	public static final String ACCESSORY_PRIVATE = "/sysaccessory/privates";

	@Autowired
	private SysAccessoryService accessoryService;
	
	/**
	 * 当Web请求到达DispatcherServlet并等待处理的时候,
	 * DispatcherServlet首先会检查能否从自己的WebApplicationContext中找到一个名称为multipartResolver
	 * (由DispatcherServet的MULTIPART_RESOLVER_BEAN_NAME决定)实例.
	 * 如果能获得这个实例,DispatcherServlet将通过MultipartResolver的isMultipart(request)
	 * 方法检查当前Web请求是否为multipart类型.
	 * 如果是,DispatcherServlet将调用MultipartResolver的resolveMultipart(request)方法,
	 * 返回MultipartHttpServletRequest,否则返回HttpServletRequest.
	 */
	@RequestMapping
	public String upload(HttpServletRequest request,HttpServletResponse response){

		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multiRequest.getFile("filename");

		// 获取路径
		String ctxPath = getPath(request);
		// 获得文件名：
		String realFileName = file.getOriginalFilename();
		String fileSuffix = CommonUtil.getFileSuffix(realFileName);
		String accessoryId = EncryptUtil.md5Encode(DateUtil.getTimeStamp());
		String fullPath = ctxPath + accessoryId + "." + fileSuffix;
		// 创建文件
		File dirPath = new File(ctxPath);
		if (!dirPath.exists()) {
			dirPath.mkdir();
		}
		File uploadFile = new File(fullPath);
		try {
			FileCopyUtils.copy(file.getBytes(), uploadFile);
			
			SysAccessory accessory = new SysAccessory();
			accessory.setAccessoryid(accessoryId);
			accessory.setAccessoryname(realFileName);
			accessory.setAccessorysize(file.getSize()/1024 + "KB");
			accessory.setAccessorytype("1");
			accessory.setSavepath(fullPath);
			accessory.setStatus(GlobalParam.DICT_ROLETYPE_PUBLIC);
			accessory.setUploaddate(DateUtil.getCurrentDate());
			SysUser user = (SysUser)request.getSession().getAttribute(WebConstants.S_KEY_USER);
			accessory.setUserid(user.getUserid());
			accessoryService.save(accessory);			
		} catch (IOException e) {
			processException(e, "附件上传异常");
		}
		
		return redirect(ACCESSORY_PRIVATE);
	}

	@RequestMapping
	public void publics(HttpServletRequest request, PageRequest page, Model model){
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		requestMap.put("EQS_status", GlobalParam.DICT_ROLETYPE_PUBLIC);
		PageResult<SysAccessory> result = accessoryService.query(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
		model.addAllAttributes(requestMap);
	}
	
	@RequestMapping
	public void privates(HttpServletRequest request, PageRequest page, Model model){
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		SysUser user = (SysUser)request.getSession().getAttribute(WebConstants.S_KEY_USER);
		requestMap.put("EQS_userid", user.getUserid());
		PageResult<SysAccessory> result = accessoryService.query(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
		model.addAllAttributes(requestMap);
	}
	
	@RequestMapping
	public void detail(String roleid, Model model){
		info(roleid, model);
	}
	
	@RequestMapping
	public void info(String accessoryid, Model model){
		if(StringUtils.isNotBlank(accessoryid)){
			SysAccessory role = accessoryService.get(accessoryid);
			model.addAttribute(WebConstants.KEY_RESULT, role);
		}
	}
	
	@RequestMapping
	public String save(SysAccessory accessory){
		accessoryService.save(accessory);
		return redirect(ACCESSORY_PULBIC);
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxSave(SysAccessory accessory){
		PageResult page = null;
		try{
			page = accessoryService.save(accessory);
		}catch(Exception e){
			page = processException(e, "保存附件出错");
		}
		return page;
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxDelete(String accessoryid){
		PageResult page = null;
		try{
			SysAccessory accessory = accessoryService.get(accessoryid);
			File accFile = new File(accessory.getSavepath());
			accFile.delete();
			
			accessoryService.delete(accessoryid);
			page = new PageResult();
		}catch(Exception e){
			page = processException(e, "删除附件出错");
		}
		return page;
	}
	
	@RequestMapping
	public String delete(String accessoryid){
		SysAccessory accessory = accessoryService.get(accessoryid);
		File accFile = new File(accessory.getSavepath());
		accFile.delete();

		accessoryService.delete(accessoryid);
		return redirect(ACCESSORY_PULBIC);
	}
	
	@RequestMapping
	public void download(HttpServletRequest request, HttpServletResponse response, String accessoryid) {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		SysAccessory accessory = accessoryService.get(accessoryid);		
		String ctxPath = accessory.getSavepath();

		try {
			response.setHeader("Content-disposition", "attachment; filename=" 
					+ new String(accessory.getAccessoryname().getBytes("gb2312"), "ISO8859-1"));

			bis = new BufferedInputStream(new FileInputStream(ctxPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
		} finally {
			try{
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
