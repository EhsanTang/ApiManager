package cn.crap.enu;

public enum ArticleStatus {
	/**
	 * PAGE 站点页面，不可删除(锁定)，key为唯一
	 */
	COMMON("普通", 1), RECOMMEND("推荐", 2), PAGE("站点页面", 100);
	private final int status;
	private final String name;
	
	ArticleStatus(String name, int status){
		this.status = status;
		this.name = name;
	}
	
	public static String getNameByValue(Byte status){
		if (status == null){
			return "";
		}
		for(ArticleStatus articleStatus : ArticleStatus.values()){
			if(articleStatus.getStatus() == status)
				return articleStatus.getName();
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
