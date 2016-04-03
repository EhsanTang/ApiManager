package cn.crap.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.IBaseDao;
import cn.crap.framework.JsonResult;
import cn.crap.framework.base.BaseService;
import cn.crap.inter.service.IInterfaceService;
import cn.crap.inter.service.IModuleService;
import cn.crap.model.Interface;
import cn.crap.model.Module;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Service
public class InterfaceService extends BaseService<Interface>
		implements IInterfaceService {
	@Autowired
	private IModuleService moduleService;
	
	@Resource(name="interfaceDao")
	public void setDao(IBaseDao<Interface> dao) {
		super.setDao(dao);
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
		return new JsonResult(1, map, page);
	}

}
