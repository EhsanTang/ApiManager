package cn.crap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.model.DataCenter;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/module")
public class ModuleController extends BaseController<DataCenter>{

	@Autowired
	private IDataCenterService moduleService;
	@Autowired
	private ICacheService cacheService;
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute DataCenter module){
		if(!module.getId().equals(Const.NULL_ID)){
			model= moduleService.get(module.getId());
		}else{
			model=new DataCenter();
			if(MyString.isEmpty((module.getType())))
				model.setType("MODULE");
			else
				model.setType(module.getType());
			model.setParentId(module.getParentId());
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute DataCenter module) throws MyException{
		DataCenter oldDataCenter = cacheService.getModule(module.getId());
		if( (  oldDataCenter.getType() == null && module.getType().equals(Const.MODULE)  ) ||
				(  oldDataCenter.getType() != null && oldDataCenter.getType().equals(Const.MODULE) ) ){
			Tools.hasAuth(Const.AUTH_MODULE, request.getSession(), module.getParentId());
		}else{
			Tools.hasAuth(Const.AUTH_SOURCE, request.getSession(), "");
		}
		
		if(!MyString.isEmpty(module.getId())){
			moduleService.update(module);
		}else{
			module.setId(null);
			moduleService.save(module);
		}
		cacheService.delObj("cache:model:"+module.getId());
		return new JsonResult(1,module);
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute DataCenter module) throws MyException{
		DataCenter oldDataCenter = cacheService.getModule(module.getId());
		if(oldDataCenter.getType().equals(Const.MODULE)){
			Tools.hasAuth(Const.AUTH_MODULE, request.getSession(), module.getParentId());
		}else{
			Tools.hasAuth(Const.AUTH_SOURCE, request.getSession(), "");
		}
		cacheService.delObj("cache:model:"+module.getId());
		moduleService.delete(module);
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	@Override
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) {
		DataCenter change = moduleService.get(changeId);
		model = moduleService.get(id);
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		moduleService.update(model);
		moduleService.update(change);

		return new JsonResult(1, null);
	}

}
