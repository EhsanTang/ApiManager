package cn.crap.dto;

import java.io.Serializable;

public class LoginDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String id;
	public String userName;
	public String password;
	public String rpassword;
	public String remberPwd;
	public String verificationCode;
	public String sessionAdminName; // 如果sessionAdminName 不为null，则表示已经登录
	public String tipMessage;
	public String email;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemberPwd() {
		return remberPwd;
	}
	public void setRemberPwd(String remberPwd) {
		this.remberPwd = remberPwd;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getSessionAdminName() {
		return sessionAdminName;
	}
	public void setSessionAdminName(String sessionAdminName) {
		this.sessionAdminName = sessionAdminName;
	}
	public String getTipMessage() {
		return tipMessage;
	}
	public void setTipMessage(String tipMessage) {
		this.tipMessage = tipMessage;
	}
	public String getRpassword() {
		return rpassword;
	}
	public void setRpassword(String rpassword) {
		this.rpassword = rpassword;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
