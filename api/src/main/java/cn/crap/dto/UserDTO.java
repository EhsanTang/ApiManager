package cn.crap.dto;

import cn.crap.enu.UserType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class UserDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String userName;
	private String roleId = "";
	private String trueName;
	private String roleName = "";
	private String auth = "";
	private String authName = "";
	private Byte type;
	private String email;
	private String avatarUrl;
	private Integer loginType;
	private String loginTypeStr;
	private String thirdlyId;
	private Byte status;
	private Long sequence;
	private String createTimeStr;
    private String attributes;
    private String attrKey;
    private String attrVal;

	public String getTypeName(){
		if (type == null){
			return "";
		}
		return UserType.getNameByValue(type);
	}
}