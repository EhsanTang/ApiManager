package cn.crap.enu;

public enum CanDeleteEnum {
	CAN("可以删除", 1), CAN_NOT("不可以删除", 0);
	private final int canDelete;
	private final String name;

	CanDeleteEnum(String name, int status){
		this.canDelete = status;
		this.name = name;
	}
	
	public static String getNameByValue(Byte status){
		if (status == null){
			return "";
		}
		for(CanDeleteEnum articleStatus : CanDeleteEnum.values()){
			if(articleStatus.getCanDelete() == status)
				return articleStatus.getName();
		}
		return "";
	}
	
	public Byte getCanDelete(){
		return Byte.valueOf(canDelete +"");
	}
	
	public String getName(){
		return name;
	}
}
