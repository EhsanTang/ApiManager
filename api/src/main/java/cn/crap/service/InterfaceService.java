package cn.crap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.JsonResult;
import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IInterfaceService;
import cn.crap.model.DataCenter;
import cn.crap.model.Interface;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class InterfaceService extends BaseService<Interface>
		implements IInterfaceService {
	
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IDataCenterService dataCenterService;
	
	@Resource(name="interfaceDao")
	public void setDao(IBaseDao<Interface> dao) {
		super.setDao(dao, new Interface());
	}

	@Override
	@Transactional
	public JsonResult getInterfaceList(Page page,List<String> moduleIds, Interface interFace, Integer currentPage) {
		page.setCurrentPage(currentPage);
		
		Map<String, Object> params = Tools.getMap("moduleId", interFace.getModuleId(),
				"interfaceName|like", interFace.getInterfaceName(),"url|like", interFace.getUrl()==null?"":interFace.getUrl().trim());
		if(moduleIds != null){
			moduleIds.add("NULL");// 防止长度为0，导致in查询报错
			params.put("moduleId|in", moduleIds);
		}
			
		List<Interface> interfaces = findByMap(
				params, " new Interface(id,moduleId,interfaceName,version,createTime,updateBy,updateTime,remark,sequence)", page, null);
		
		List<DataCenter> modules = new ArrayList<DataCenter>();
		// 搜索接口时，modules为空
		if (interFace.getModuleId() != null && MyString.isEmpty(interFace.getInterfaceName())) {
			params = Tools.getMap("parentId", interFace.getModuleId(), "type", "MODULE");
			if(moduleIds != null){
				moduleIds.add("NULL");// 防止长度为0，导致in查询报错
				params.put("id|in", moduleIds);
			}
			params.put("id|!=", "top");// 顶级目录不显示
			modules = dataCenterService.findByMap(params, null, null);
		}
		params.clear();
		params.put("interfaces", interfaces);
		params.put("modules", modules);
		return new JsonResult(1, params, page, 
				Tools.getMap("crumbs", Tools.getCrumbs("接口列表:"+cacheService.getModuleName(interFace.getModuleId()),"void"),
						"module",cacheService.getModule(interFace.getModuleId())));
	}
	
	@Override
	@Transactional
	public void getInterFaceRequestExam(Interface interFace) {
			interFace.setRequestExam("请求地址:"+interFace.getModuleUrl()+interFace.getUrl()+"\r\n");
			
			// 请求头
			JSONArray headers = JSONArray.fromObject(interFace.getHeader());
			StringBuilder strHeaders = new StringBuilder("请求头:\r\n");
			JSONObject obj = null;
			for(int i=0;i<headers.size();i++){  
				obj = (JSONObject) headers.get(i);
		        strHeaders.append("\t"+obj.getString("name") + "="+ (obj.containsKey("def")?obj.getString("def"):"")+"\r\n");
		    }  
			
			// 请求参数
			StringBuilder strParams = new StringBuilder("请求参数:\r\n");
			if(!MyString.isEmpty(interFace.getParam())){
				JSONArray params = null;
				if(interFace.getParam().startsWith("form=")){
					 params = JSONArray.fromObject(interFace.getParam().substring(5));
					 for(int i=0;i<params.size();i++){  
							obj = (JSONObject) params.get(i);
							if(obj.containsKey("inUrl") && obj.getString("inUrl").equals("true")){
								interFace.setRequestExam(interFace.getRequestExam().replace("{"+obj.getString("name")+"}", (obj.containsKey("def")?obj.getString("def"):"")));
							}else{
								strParams.append("\t"+obj.getString("name") + "=" + (obj.containsKey("def")?obj.getString("def"):"")+"\r\n");
							}
					 }  
				}else{
					strParams.append(interFace.getParam()); 
				}
			}
			interFace.setRequestExam(interFace.getRequestExam()+strHeaders.toString()+strParams.toString());
	}
}
