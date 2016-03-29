package cn.crap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.framework.base.BaseModel;

/**
 * @author Ehsan
 *
 */
@Entity
@Table(name="role")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Role extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4223473013771467071L;
	/**
	 * roleId(角色ID)
	 * */
	private String roleId;
	/**
	 * roleName(角色名称)
	 * */
	private String roleName;
	private String auth;
	private String authName;
	@Column(name="auth")
	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	@Column(name="authName")
	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	@Id
	@GeneratedValue(generator="Generator")
	@Column(name="roleId")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name="roleName")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}