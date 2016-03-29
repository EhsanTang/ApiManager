package cn.crap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.BiyaoBizException;
import cn.crap.framework.JsonResult;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.ModuleInfoService;
import cn.crap.model.ModuleInfoModel;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/moduleInfo")
public class ModuleInfoController extends BaseController<ModuleInfoModel>{

	@Autowired
	private ModuleInfoService moduleInfoService;
	
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute ModuleInfoModel module,String currentId){
		module= moduleInfoService.get(module.getModuleId());
		if(module==null){
			module=new ModuleInfoModel();
			module.setParentId(currentId);
		}
		return new JsonResult(1,module);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute ModuleInfoModel module) throws BiyaoBizException{
		Tools.hasAuth(Const.AUTH_MODULE, request.getSession(), module.getParentId());
		if(!MyString.isEmpty(module.getModuleId())){
			moduleInfoService.update(module);
		}else{
			module.setModuleId(null);
			moduleInfoService.save(module);
		}
		return new JsonResult(1,module);
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute ModuleInfoModel module) throws BiyaoBizException{
		module = moduleInfoService.get(module.getModuleId());
		Tools.hasAuth(Const.AUTH_MODULE, request.getSession(), module.getParentId());
		moduleInfoService.delete(module);
		return new JsonResult(1,null);
	}

}
