package cn.crap.enumer;

public enum Iconfont {
	LOCAL_HOST("iconfont","本地图标地址（服务器不能连接外网时使用）"),
	REMOTE("//at.alicdn.com/t/font_afbmuhv5zc15rk9", "阿里CDN图标库地址");
	private String name;
	private String value;

	private Iconfont(String value, String name){
		this.name = name;
		this.value = value;
	}
	public String getName(){
		return name;
	}
	public String getValue(){
		return value;
	}
}
