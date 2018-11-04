package cn.crap.dto;

import java.io.Serializable;

import cn.crap.enu.MyError;
import cn.crap.framework.MyException;
import cn.crap.utils.MyString;

public class FindPwdDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String code;
	private String newPwd;
	private String imgCode;
	
	public void check() throws MyException{
		if(MyString.isEmpty(email) || MyString.isEmpty(code) || MyString.isEmpty(newPwd)){
			throw new MyException(MyError.E000029);
		}
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getImgCode() {
		return imgCode;
	}
	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}
	
	
}
