package cn.crap.enu;

public enum LoginType {
	
	
	COMMON("普通登录",0),
	GITHUB("GitHub",1),
	OSCHINA("OsChina",2);
	
	private String name;
	private int value;
	
	private LoginType(String name,int value){
		this.name = name;
		this.value = value;
	}

	public LoginType getMonitorType(int value){
		for (LoginType c : LoginType.values())
		{
			if (c.getValue() == value)
			{
				return c;
			}
		}
		return null;
	}
	
	// 普通方法
	public static String getName(int value)
	{
		for (LoginType c : LoginType.values())
		{
			if (c.getValue() == value)
			{
				return c.getName();
			}
		}
		return "";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
	
}
