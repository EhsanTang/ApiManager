package cn.crap.enumeration;

public enum ProjectStatus {
	COMMON("普通项目", 1),RECOMMEND("推荐项目", 2), ADMIN("管理员项目",3), RECOMMENDADMIN("管理员项目&推荐项目",4);
	private final int status;
	private final String name;
	
	ProjectStatus(String name, int status){
		this.status = status;
		this.name = name;
	}
	
	public static String getNameByValue(int status){
		for(ProjectStatus projectStatus : ProjectStatus.values()){
			if(projectStatus.getStatus() == status)
				return projectStatus.getName();
		}
		return "";
	}
	
	public Byte getStatus(){
		return Byte.valueOf(status+"");
	}
	
	public String getName(){
		return name;
	}
}
