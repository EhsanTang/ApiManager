package cn.crap.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginDto implements Serializable{
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
	public String attributes;
}
