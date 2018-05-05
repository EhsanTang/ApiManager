package cn.crap.dto;

import java.util.Date;

/**
 * Automatic generation by tools
 * dto: exchange data with view
 */
public class RoleDto{
	private String id;
	private String roleName;
	private String auth;
	private String authName;
	private Byte status;
	private Integer sequence;

	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}

	public void setRoleName(String roleName){
		this.roleName=roleName;
	}
	public String getRoleName(){
		return roleName;
	}

	public void setAuth(String auth){
		this.auth=auth;
	}
	public String getAuth(){
		return auth;
	}

	public void setAuthName(String authName){
		this.authName=authName;
	}
	public String getAuthName(){
		return authName;
	}

	public void setStatus(Byte status){
		this.status=status;
	}
	public Byte getStatus(){
		return status;
	}

	public void setSequence(Integer sequence){
		this.sequence=sequence;
	}
	public Integer getSequence(){
		return sequence;
	}


}
