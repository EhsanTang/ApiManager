package cn.crap.service.tool;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.PickDto;
import cn.crap.enumeration.ProjectType;
import cn.crap.framework.MyException;
import cn.crap.inter.service.table.IArticleService;
import cn.crap.inter.service.table.IMenuService;
import cn.crap.inter.service.table.IModuleService;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.inter.service.table.IRoleService;
import cn.crap.inter.service.table.IUserService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.inter.service.tool.IPickService;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.model.User;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

/**
 * 下拉选着
 * @author Ehsan
 *
 */
@Service("userPickService")
public class UserPickService implements IPickService{
	@Autowired
	IMenuService menuService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IModuleService moduleService;
	@Autowired
	private IArticleService articleService;

	@Override
	public void getPickList(List<PickDto> picks, String code, String key, LoginInfoDto user) throws MyException {
		// 需要登陆才能
				PickDto pick = null;
				List<String> projectIds = null;
				switch (code) {
					case "CATEGORY":
					int i = 0;
					List<String> categorys = (List<String>) articleService.queryByHql("select distinct category from Article where moduleId in( select id from Module where userId='" + user.getId()+"')", null);
					for (String w : categorys) {
						if (w == null)
							continue;
						i++;
						pick = new PickDto("cat_" + i, w, w);
						picks.add(pick);
					}
					return;
//					case "MYPROJECT":// 用户所有项目
//						// 普通用户，只能查看自己的模块
//						for (Project p : projectService.findByMap(Tools.getMap("userId", Tools.getUser().getId()), null, null)) {
//							pick = new PickDto(p.getId(), p.getName());
//							picks.add(pick);
//						}
//						return;
					case "MYMODULE":// 用户所有模块
						// 普通用户，只能查看自己的模块
						for (Project p : projectService.findByMap(Tools.getMap("userId", Tools.getUser().getId()), null, null)) {
							pick = new PickDto(Const.SEPARATOR, p.getName());
							picks.add(pick);
							
							for(Module m : moduleService.findByMap(Tools.getMap("projectId", p.getId()), null, null)){
								pick = new PickDto(m.getId(), m.getName());
								picks.add(pick);
							}
						}
						return;
					case "PROJECT_MODULE":
						// 普通用户，只能查看自己的项目和模块
						projectIds = projectService.getProjectIdByUid(Tools.getUser().getId());
						moduleService.getDataCenterPick(picks, projectIds , "", "", "");
						return;
					case "MODULES":
						// 查看某个项目下的模块
						if(!MyString.isEmpty(key)){
							for(Module m : moduleService.findByMap(Tools.getMap("projectId", key), null, null)){
								pick = new PickDto(m.getId(), m.getName());
								picks.add(pick);
							}
						}
						return;
					case "PROJECTTYPE":
						for (ProjectType pt : ProjectType.values()) {
							pick = new PickDto(pt.getType()+"", pt.getName());
							picks.add(pick);
						}
						return;
					case "USER":
						if(key!= null && key.length()>=4){
							if(key.indexOf("@")>0){
								for (User u : userService.findByMap(Tools.getMap("email|like", key), null, null)) {
									pick = new PickDto(u.getId(), u.getUserName());
									picks.add(pick);
								}
							}
							else{
								for (User u : userService.findByMap(Tools.getMap("userName|like", key), null, null)) {
									pick = new PickDto(u.getId(), u.getUserName());
									picks.add(pick);
								}
							}
						}else{
							pick = new PickDto(Const.SEPARATOR, "输入的搜索长度必须大于3");
							picks.add(pick);
						}
				}
			}

}
