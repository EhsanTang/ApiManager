package cn.crap.dto;

import cn.crap.enu.AttributeEnum;
import cn.crap.enu.UserType;
import cn.crap.model.UserPO;
import cn.crap.utils.AttributeUtils;
import lombok.Getter;

import java.io.Serializable;

public class LoginInfoDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private String userName;
	private String trueName;
	private String authStr;//权限，由用户权限拼接而成
	private String id;
	private byte type;
	private String email;
	private String avatarUrl;
	@Getter
	public String attributes;


	public LoginInfoDto(UserPO user){
		this.userName = user.getUserName();
		this.trueName = user.getTrueName();
		this.id = user.getId();
		this.type = user.getType();
		this.email = user.getEmail();
		this.avatarUrl = user.getAvatarUrl();
		this.authStr = user.getAuth();
		this.attributes = user.getAttributes();
		
		StringBuilder sb = new StringBuilder(",");
		if( type == UserType.ADMIN.getType() ){
			sb.append(authStr+",ADMIN,");
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
		return authStr == null ? "" : authStr;
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

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getAttribute(AttributeEnum attributeEnum){
		return AttributeUtils.getAttr(this.attributes, attributeEnum);
	}

}
