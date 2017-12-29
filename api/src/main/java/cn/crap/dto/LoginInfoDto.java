package cn.crap.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crap.enumer.UserType;
import cn.crap.model.mybatis.*;
import cn.crap.model.mybatis.ProjectUser;
import cn.crap.service.custom.CustomProjectService;
import cn.crap.service.mybatis.RoleService;
import cn.crap.service.mybatis.ProjectUserService;
import cn.crap.utils.IConst;
import cn.crap.utils.Tools;

public class LoginInfoDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private String userName;
	private String trueName;
	private String authStr;//权限，由用户权限、角色拼接而成
	private String roleId; 
	private String id;
	private byte type;
	private String email;
	private String avatarUrl;
	private Map<String, ProjectUser> projects = new HashMap<String, ProjectUser>();

	public LoginInfoDto(User user, RoleService roleService, CustomProjectService customProjectService, ProjectUserService projectUserService){
		this.userName = user.getUserName();
		this.trueName = user.getTrueName();
		this.roleId = user.getRoleId();
		this.id = user.getId();
		this.type = user.getType();
		this.email = user.getEmail();
		this.avatarUrl = user.getAvatarUrl();
		this.authStr = user.getAuth();
		
		StringBuilder sb = new StringBuilder(",");
		// 将用户的自己的模块添加至权限中
		List<Project> myProjects = customProjectService.queryMyProjectByUserId(id);
		for(Project project:myProjects){
			sb.append(IConst.C_AUTH_PROJECT + project.getId()+",");
		}
		
		// 管理员，将最高管理员，管理员
		if( type == UserType.ADMIN.getType() ){
			sb.append(authStr+",");
			sb.append("ADMIN,");
			if(roleId != null && roleId.indexOf("super") >= 0)
				sb.append("super,");
			if (roleId != null && !"".equals(roleId)) {
				RoleCriteria example = new RoleCriteria();
				RoleCriteria.Criteria criteria = example.createCriteria().andIdIn(Tools.getIdsFromField(roleId));
				List<RoleWithBLOBs> roles = roleService.selectByExampleWithBLOBs(example);
				// 将角色中的权限添加至用户权限中
				for (RoleWithBLOBs role : roles) {
					sb.append(role.getAuth()+",");
				}
			}
		}
		
		// 项目成员
		ProjectUserCriteria example = new ProjectUserCriteria();
		ProjectUserCriteria.Criteria criteria = example.createCriteria().andUserIdEqualTo(id);

		for(ProjectUser p: projectUserService.selectByExample(example)){
			projects.put(p.getProjectId(), p);
			sb.append(IConst.C_AUTH_PROJECT + p.getProjectId()+",");
		}
		
		this.authStr = sb.toString();
	}

	public String getUserName() {
		return userName;
	}

	public String getTrueName() {
		return trueName;
	}

	public String getAuthStr() {
		if(authStr == null)
			return "";
		return authStr;
	}

	public String getRoleId() {
		return roleId;
	}
	
	public String getId(){
		return id;
	}
	
	public byte getType(){
		return type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, ProjectUser> getProjects() {
		return projects;
	}

	public void setProjects(Map<String, ProjectUser> projects) {
		this.projects = projects;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

}
