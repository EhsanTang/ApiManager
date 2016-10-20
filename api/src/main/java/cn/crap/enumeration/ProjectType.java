package cn.crap.enumeration;

public enum ProjectType {
	PRIVATE("私有项目", 1), PUBLIC("公开项目",2);
	private final int type;
	private final String name;
	
	ProjectType(String name, int type){
		this.type = type;
		this.name = name;
	}
	
	public static String getNameByValue(int type){
		for(ProjectType projectType : ProjectType.values()){
			if(projectType.getType() == type)
				return projectType.getName();
		}
		return "";
	}
	
	public int getType(){
		return type;
	}
	
	public String getName(){
		return name;
	}
}
