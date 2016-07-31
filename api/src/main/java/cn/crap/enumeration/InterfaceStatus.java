package cn.crap.enumeration;

public enum InterfaceStatus {
	不可用("0"),可用("1");
	private final String name;
	
	private InterfaceStatus(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
