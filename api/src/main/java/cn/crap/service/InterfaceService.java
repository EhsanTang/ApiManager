package cn.crap.service;

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
import cn.crap.inter.service.IInterfaceService;
import cn.crap.inter.service.IModuleService;
import cn.crap.model.Interface;
import cn.crap.model.Module;
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
	private IModuleService moduleService;
	
	@Resource(name="interfaceDao")
	public void setDao(IBaseDao<Interface> dao) {
		super.setDao(dao, new Interface());
	}

	@Override
	@Transactional
	public JsonResult getInterfaceList(Page page,Map<String,Object> map, Interface interFace, Integer currentPage) {
		page.setCurrentPage(currentPage);
		map = Tools.getMap("moduleId", interFace.getModuleId(),
				"interfaceName|like", interFace.getInterfaceName(),"url|like", interFace.getUrl()==null?"":interFace.getUrl().trim());
		List<Interface> interfaces = findByMap(
				map, page, null);
		map.clear();
		List<Module> modules = null;
		// 搜索接口时，module为空
		if (interFace.getModuleId() != null) {
			map = Tools.getMap("parentId", interFace.getModuleId());
			modules = moduleService.findByMap(map, null, null);
		}
		map.put("interfaces", interfaces);
		map.put("modules", modules);
		return new JsonResult(1, map, page, 
				Tools.getMap("crumbs", Tools.getCrumbs("接口列表:"+cacheService.getModuleName(interFace.getModuleId()),"void")));
	}
	
	@Override
	@Transactional
	public void getInterFaceRequestExam(Interface interFace) {
			interFace.setRequestExam("请求地址:"+interFace.getUrl()+"\r\n");
			
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
							strParams.append("\t"+obj.getString("name") + "=" + (obj.containsKey("def")?obj.getString("def"):"")+"\r\n");
					 }  
				}else{
					strParams.append(interFace.getParam()); 
				}
			}
			interFace.setRequestExam(interFace.getRequestExam()+strHeaders.toString()+strParams.toString());
	}
}
