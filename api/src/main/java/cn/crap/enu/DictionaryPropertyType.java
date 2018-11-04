package cn.crap.enu;

public enum DictionaryPropertyType {
	
	
	primary("主键","primary"),
	common("普通","common"),
	associate("关联","associate"),
	foreign("外键","foreign");
	
	private String name;
	private String remark;
	
	private DictionaryPropertyType(String remark,String name){
		this.name = name;
		this.remark = remark;
	}

	
	// 普通方法
	public static String getRemark(String name)
	{
		for (DictionaryPropertyType c : DictionaryPropertyType.values())
		{
			if (c.getName() == name)
			{
				return c.getRemark();
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
	
}
