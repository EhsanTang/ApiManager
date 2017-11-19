package cn.crap.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crap.enumeration.UserType;
import cn.crap.model.mybatis.ProjectUser;
import cn.crap.service.IProjectUserService;
import cn.crap.service.IRoleService;
import cn.crap.model.mybatis.Project;
import cn.crap.model.mybatis.ProjectUser;
import cn.crap.model.mybatis.Role;
import cn.crap.model.mybatis.User;
import cn.crap.service.mybatis.custom.CustomProjectService;
import cn.crap.utils.Const;
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

	public LoginInfoDto(User user, IRoleService roleService, CustomProjectService customProjectService, IProjectUserService projectUserService){
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
			sb.append(Const.AUTH_PROJECT + project.getId()+",");
		}
		
		// 管理员，将最高管理员，管理员
		if( type == UserType.ADMIN.getType() ){
			sb.append(authStr+",");
			sb.append("ADMIN,");
			if(roleId.indexOf("super") >= 0)
				sb.append("super,");
			if (roleId != null && !"".equals(roleId)) {
				List<Role> roles = roleService.findByMap(
						Tools.getMap("id|in", Tools.getIdsFromField(roleId)), null, null);
				// 将角色中的权限添加至用户权限中
				for (Role role : roles) {
					sb.append(role.getAuth()+",");
				}
			}
		}
		
		// 项目成员
		for(ProjectUser p: projectUserService.findByMap(Tools.getMap("userId", id), null, null)){
			projects.put(p.getProjectId(), p);
			sb.append(Const.AUTH_PROJECT + p.getProjectId()+",");
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
