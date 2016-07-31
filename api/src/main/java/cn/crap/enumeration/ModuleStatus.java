package cn.crap.enumeration;

public enum ModuleStatus {
	公开("1"),私有("2"),推荐("3");
	private final String name;
	
	private ModuleStatus(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
