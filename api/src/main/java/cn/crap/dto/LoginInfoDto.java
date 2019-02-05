package cn.crap.dto;

import cn.crap.enu.UserType;
import cn.crap.framework.MyException;
import cn.crap.model.Project;
import cn.crap.model.ProjectUserPO;
import cn.crap.model.User;
import cn.crap.query.ProjectQuery;
import cn.crap.query.ProjectUserQuery;
import cn.crap.service.ProjectService;
import cn.crap.service.ProjectUserService;
import cn.crap.utils.IConst;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginInfoDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private String userName;
	private String trueName;
	private String authStr;//权限，由用户权限拼接而成
	private String id;
	private byte type;
	private String email;
	private String avatarUrl;
	private Map<String, ProjectUserPO> projects = new HashMap<>();

	public LoginInfoDto(User user, ProjectService projectService,
						ProjectUserService projectUserService) throws MyException{
		this.userName = user.getUserName();
		this.trueName = user.getTrueName();
		this.id = user.getId();
		this.type = user.getType();
		this.email = user.getEmail();
		this.avatarUrl = user.getAvatarUrl();
		this.authStr = user.getAuth();
		
		StringBuilder sb = new StringBuilder(",");
		// 将用户的自己的模块添加至权限中
		List<Project> myProjects = projectService.query(new ProjectQuery().setUserId(id));
		for(Project project:myProjects){
			sb.append(IConst.C_AUTH_PROJECT + project.getId()+",");
		}
		
		if( type == UserType.ADMIN.getType() ){
			sb.append(authStr+",");
			sb.append("ADMIN,");
		}
		
		// 项目成员
		for(ProjectUserPO p: projectUserService.select(new ProjectUserQuery().setUserId(id), null)){
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

	public Map<String, ProjectUserPO> getProjects() {
		return projects;
	}

	public void setProjects(Map<String, ProjectUserPO> projects) {
		this.projects = projects;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

}
