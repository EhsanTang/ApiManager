package cn.crap.enumeration;

public enum ModuleStatus {
	公开("1"),私有("2"),推荐("3");
	private final String name;
	
	public static String getNameByValue(String value){
		for(ModuleStatus type : ModuleStatus.values()){
			if(type.getName().equals(value))
				return type.name();
		}
		return "";
	}
	
	private ModuleStatus(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
