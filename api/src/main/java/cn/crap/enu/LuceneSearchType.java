package cn.crap.enu;

public enum LuceneSearchType {
	
	
	No("不开放搜索",new Byte("0")),
	Yes("开放搜索",new Byte("1"));
	
	private String name;
	private byte value;
	
	LuceneSearchType(String name, byte value){
		this.name = name;
		this.value = value;
	}
	
	public LuceneSearchType getMonitorType(Integer value){
		if (value == null){
			return null;
		}
		for (LuceneSearchType c : LuceneSearchType.values())
		{
			if (c.getValue() == value)
			{
				return c;
			}
		}
		return null;
	}
	
	// 普通方法
	public static String getName(Byte value)
	{
		if (value == null){
			return "";
		}
		for (LuceneSearchType c : LuceneSearchType.values())
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

	public Byte getByteValue() {
		return new Byte(value);
	}

	public void setValue(byte value) {
		this.value = value;
	}
	
	
	
}
