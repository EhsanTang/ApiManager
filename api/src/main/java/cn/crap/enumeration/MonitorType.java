package cn.crap.enumeration;

public enum MonitorType {
	
	
	No("不监控",0),
	Network("网络异常",1),
	NetworkInclude("网络异常、包含指定字符串",2),
	NetworkNotInclude("网络异常、不包含指定字符串",3),
	NetworkNotEqual("网络异常、不包含指定字符串",4);
	
	private String name;
	private int value;
	
	private MonitorType(String name,int value){
		this.name = name;
		this.value = value;
	}

	public MonitorType getMonitorType(int value){
		for (MonitorType c : MonitorType.values())
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
		for (MonitorType c : MonitorType.values())
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
