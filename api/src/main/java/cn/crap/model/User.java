package cn.crap.model;

import javax.persistence.Transient;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.enumeration.UserType;
import cn.crap.framework.base.BaseModel;


@Entity
@Table(name="user")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class User extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * userName(用户昵称)
	 * */
	private String userName;
	/**
	 * roleId(所属角色编码)
	 * */
	private String roleId = "";
	private String password;
	private String trueName;
	private String roleName = "";
	private String auth = "";
	private String authName = "";
	private byte type;
	private String email;
	
	@Column(name="userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="roleId")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="trueName")
	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	@Column(name="roleName")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

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

	
	@Column(name="type")
	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Transient
	public String getTypeName(){
		return UserType.getNameByValue(type+"");
	}
	
}