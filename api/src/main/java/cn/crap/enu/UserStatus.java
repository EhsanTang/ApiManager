package cn.crap.enu;

public enum UserStatus {

	FORBID("禁用", Byte.valueOf("0")), INVALID("邮箱未验证",Byte.valueOf("1")), VALID("邮箱验证成功",Byte.valueOf("2"));
	private final byte type;
	private final String name;

	UserStatus(String name, byte type){
		this.type = type;
		this.name = name;
	}

	public static String getNameByValue(byte type){
		for(UserStatus userStatus : UserStatus.values()){
			if(userStatus.getType() == type)
				return userStatus.getName();
		}
		return "";
	}

	public byte getType(){
		return type;
	}

	public String getName(){
		return name;
	}
}
