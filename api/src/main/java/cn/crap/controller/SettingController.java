package cn.crap.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.crap.framework.MyException;
import cn.crap.framework.JsonResult;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ISettingService;
import cn.crap.model.Setting;
import cn.crap.utils.Cache;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/setting")
public class SettingController extends BaseController<Setting>{

	@Autowired
	private ISettingService settingService;
	
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
		if(!MyString.isEmpty(setting.getId())){
			model = settingService.get(setting.getId());
		}else if(!MyString.isEmpty(setting.getKey())){
			List<Setting> settings= settingService.findByMap(Tools.getMap("key",setting.getKey()),null,null);
			if(settings.size()>0){
				model = settings.get(0);
			}
		}
		if(model==null){
			model=new Setting();
			model.setType(setting.getType());
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_SETTING)
	public JsonResult addOrUpdate(@ModelAttribute Setting setting){
			if(!MyString.isEmpty(setting.getId())){
				settingService.update(setting);
			}else{
				setting.setId(null);
				if(settingService.getCount(Tools.getMap("key",setting.getKey()))==0){
					setting.setCanDelete(Byte.valueOf("1"));
					settingService.save(setting);
				}else{
					return new JsonResult(new MyException("000006"));
				}
			}
		Cache.setSetting(setting,Tools.getServletContext());
		return new JsonResult(1,setting);
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute Setting setting) throws MyException{
		setting = settingService.get(setting.getId());
		if(setting.getCanDelete()==0){
			throw new MyException("000009");
		}
		Tools.hasAuth(Const.AUTH_SETTING, request.getSession(),"");
		settingService.delete(setting);
		return new JsonResult(1,null);
	}

}
