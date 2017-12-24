package cn.crap.controller.admin;

import cn.crap.adapter.SettingAdapter;
import cn.crap.dto.SettingDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.Setting;
import cn.crap.model.mybatis.SettingCriteria;
import cn.crap.service.mybatis.imp.MybatisSettingService;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import cn.crap.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SettingController extends BaseController{

	@Autowired
	private MybatisSettingService mybatisSettingService;
	@Autowired
	private Config config;
	private final static String[] indexUrls = new String[]{"index.do", "front/","project.do"};
	/**
	 * 
	 * @param currentPage 当前页
	 * @return
	 */
	@RequestMapping("/setting/list.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_SETTING)
	public JsonResult list(String key, String remark,@RequestParam(defaultValue="1") int currentPage){
		Page page= new Page(15, currentPage);

		SettingCriteria example = new SettingCriteria();
		SettingCriteria.Criteria criteria= example.createCriteria();
		if (key != null){
			criteria.andMkeyLike(key);
		}
		if (remark != null){
			criteria.andRemarkLike(remark);
		}
		example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
		example.setLimitStart(page.getStart());
		example.setMaxResults(page.getSize());

		page.setAllRow(mybatisSettingService.countByExample(example));
		return new JsonResult(1, SettingAdapter.getDto(mybatisSettingService.selectByExample(example)) , page);
	}
	
	@RequestMapping("/setting/detail.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_SETTING)
	public JsonResult detail(String id, String key, String type){
		Setting model = null;
		if(id != null){
			model = mybatisSettingService.selectByPrimaryKey(id);
		}else if(key != null){
			SettingCriteria example = new SettingCriteria();
			SettingCriteria.Criteria criteria = example.createCriteria();
			criteria.andMkeyEqualTo(key);

			List<Setting> settings= mybatisSettingService.selectByExample(example);
			if(settings.size()>0){
				model = settings.get(0);
			}
		}

		if(model==null){
			model=new Setting();
			model.setType(type);
		}
		return new JsonResult(1, SettingAdapter.getDto(model));
	}
	
	@RequestMapping("/setting/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_SETTING)
	public JsonResult addOrUpdate(@ModelAttribute SettingDto settingDto, HttpServletRequest req) throws Exception{
			if(settingDto.getId() != null){
				Setting old = mybatisSettingService.selectByPrimaryKey(settingDto.getId());
				settingDto.setCanDelete(old.getCanDelete());
				if (Const.SETTING_INDEX_PAGE.equals(settingDto.getKey())){
					boolean legalUrl = false;
					for(String indexUrl : indexUrls){
						if(settingDto.getValue().startsWith(indexUrl)){
							legalUrl = true;
							break;
						}
					}
					if (!legalUrl){
						return new JsonResult(new MyException("000059"));
					}
				}
				mybatisSettingService.update(SettingAdapter.getModel(settingDto));
			}else{
				SettingCriteria example = new SettingCriteria();
				SettingCriteria.Criteria criteria = example.createCriteria();
				criteria.andMkeyEqualTo(settingDto.getKey());

				List<Setting> settings= mybatisSettingService.selectByExample(example);
				if(settings.size()>0){
					return new JsonResult(new MyException("000006"));
				}

				mybatisSettingService.insert(SettingAdapter.getModel(settingDto));
			}
			settingCache.del(settingDto.getKey());

			// 更新css模板
			String path = Tools.getServicePath(req) + "resources/css/"; 
			Tools.createFile(path);
			String content = Tools.readFile(path + "setting.tpl.css");
			for(SettingDto s:settingCache.getAll()){
				String value = s.getValue();
				if (value != null && (value.toLowerCase().endsWith(".jpg") || value.toLowerCase().endsWith(".png")) ){
					if (!value.startsWith("http://") && !value.startsWith("https://")){
						value = config.getDomain() + "/" + value;
					}
				}
				content = content.replace("{{settings."+ s.getKey() + "}}", value);
			}
			Tools.staticize(content, path + "/setting.css");
		return new JsonResult(1,settingDto);
	}

	@RequestMapping("/setting/delete.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_SETTING)
	public JsonResult delete(@RequestParam  String id) throws MyException{
		Setting setting = mybatisSettingService.selectByPrimaryKey(id);
		if(setting.getCanDelete()==0){
			throw new MyException("000009");
		}
		mybatisSettingService.delete(id);
		settingCache.del(setting.getMkey());
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/back/setting/changeSequence.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_SETTING)
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) {
		Setting change = mybatisSettingService.selectByPrimaryKey(changeId);
		Setting model = mybatisSettingService.selectByPrimaryKey(id);
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);

		mybatisSettingService.update(model);
		mybatisSettingService.update(change);
		return new JsonResult(1, null);
	}

}
