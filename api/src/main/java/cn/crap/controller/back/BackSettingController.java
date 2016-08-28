package cn.crap.controller.back;

import java.util.List;

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
import cn.crap.inter.service.ISettingService;
import cn.crap.model.Setting;
import cn.crap.service.CacheService;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
public class BackSettingController extends BaseController<Setting>{

	@Autowired
	private ISettingService settingService;
	@Autowired
	private ICacheService cacheService;
	/**
	 * 
	 * @param setting
	 * @param currentPage 当前页
	 * @param pageSize 每页显示多少条，-1表示查询全部
	 * @return
	 */
	@RequestMapping("/setting/list.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_SETTING)
	public JsonResult list(@ModelAttribute Setting setting,@RequestParam(defaultValue="1") int currentPage){
		page.setCurrentPage(currentPage);
		// 搜索条件
		map = Tools.getMap(  "key|like", setting.getKey()  ,  "remark|like", setting.getRemark()  );
		return new JsonResult(1,  settingService.findByMap(map, page, null)   , page);
	}
	
	@RequestMapping("/setting/detail.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_SETTING)
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
	
	@RequestMapping("/setting/addOrUpdate.do")
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
			cacheService.delObj(CacheService.cacheSettingKeyPre);
			cacheService.delObj(CacheService.cacheSettingList);
		return new JsonResult(1,setting);
	}
	@RequestMapping("/setting/delete.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_SETTING)
	public JsonResult delete(@ModelAttribute Setting setting) throws MyException{
		setting = settingService.get(setting.getId());
		if(setting.getCanDelete()==0){
			throw new MyException("000009");
		}
		Tools.hasAuth(Const.AUTH_SETTING, "");
		settingService.delete(setting);
		cacheService.delObj(CacheService.cacheSettingKeyPre,setting.getKey());
		cacheService.delObj(CacheService.cacheSettingList);
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/back/setting/changeSequence.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_SETTING)
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) {
		Setting change = settingService.get(changeId);
		model = settingService.get(id);
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		settingService.update(model);
		settingService.update(change);
		cacheService.delObj(CacheService.cacheSettingKeyPre);
		cacheService.delObj(CacheService.cacheSettingList);
		return new JsonResult(1, null);
	}

}
