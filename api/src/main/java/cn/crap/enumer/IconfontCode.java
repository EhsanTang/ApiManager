package cn.crap.enumer;

public enum IconfontCode {
	GET("&#xe645;","G"),
	POST("&#xe6c4;", "P"),
	SEND("&#xe634;", "发送"),
	SPEED_UP("&#xe640;", "加速"),
	HELP("&#xe63e;", "帮助"),
	NOTICE("&#xe632;", "通知"),
	RECOMMEND("&#xe636;", "推荐"),
	ERM("&#xe62d;", "二维码"),
	TIME("&#xe62b;", "时间"),
	CRY("&#xe626;", "哭脸"),
	DIR("&#xe628;", "目录"),
	SETTING("&#xe61a;", "设置"),
	PDF("&#xe61e;", "PDF"),
	DATA("&#xe61c;", "数据"),
	COPY("&#xe61d;", "拷贝"),
	CATEGORY("&#xe61f;", "分类"),
	EDIT("&#xe618;", "编辑"),
	INTER("&#xe614;", "接口"),
	MODULE("&#xe613;", "模块"),
	REFRESH("&#xe611;", "刷新"),
	SEARCH("&#xe60d;", "搜索"),
	DELETE("&#xe60e;", "删除"),
	USER("&#xe603;", "用户"),
	SAVE("&#xe602;", "保存"),
	CLOSE("&#xe63f;", "关闭");
	private String name;
	private String value;

	private IconfontCode(String value, String name){
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
