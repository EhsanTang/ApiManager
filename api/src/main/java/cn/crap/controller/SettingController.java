package cn.crap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.BiyaoBizException;
import cn.crap.framework.JsonResult;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.IModuleService;
import cn.crap.inter.service.ISettingService;
import cn.crap.model.Setting;
import cn.crap.model.Module;
import cn.crap.utils.Cache;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/setting")
public class SettingController extends BaseController{

	@Autowired
	private ISettingService settingService;
	@Autowired
	private IModuleService moduleService;
	/**
	 * MenuDemo
	 * @return 
	 * @throws Exception 
	 * */
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute Setting setting,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		map = Tools.getMap("key|like",setting.getKey(),"remark|like",setting.getRemark());
		return new JsonResult(1,settingService.findByMap(map,page, null),page);
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute Setting setting){
		setting= settingService.get(setting.getId());
		if(setting==null){
			setting=new Setting();
		}
		return new JsonResult(1,setting);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ERROR)
	public JsonResult addOrUpdate(@ModelAttribute Setting setting){
			if(!MyString.isEmpty(setting.getId())){
				settingService.update(setting);
			}else{
				setting.setId(null);
				if(settingService.getCount(Tools.getMap("key",setting.getKey()))==0){
					settingService.save(setting);
				}else{
					return new JsonResult(new BiyaoBizException("000006"));
				}
			}
		Cache.setSetting(setting);
		return new JsonResult(1,setting);
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute Setting setting) throws BiyaoBizException{
		setting = settingService.get(setting.getId());
		Tools.hasAuth(Const.AUTH_ERROR, request.getSession(),"");
		settingService.delete(setting);
		return new JsonResult(1,null);
	}

}
