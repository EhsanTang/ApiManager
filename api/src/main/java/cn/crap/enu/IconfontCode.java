package cn.crap.enu;

public enum IconfontCode {
	GET("&#xe645;","G"),
	POST("&#xe6c4;", "P"),
	PASSWORD("&#xe82b;", "密码"),
	SEND("&#xe634;", "发送"),
	SPEED_UP("&#xe640;", "加速"),
	HELP("&#xe6a3;", "帮助"),
	NOTICE("&#xe6bc;", "通知"),
	RECOMMEND("&#xe636;", "推荐"),
	ERM("&#xe6a9;", "二维码"),
    CLICK("&#xe6bb;", "时间"),
	TIME("&#xe6bb;", "时间"),
	CRY("&#xe69c;", "哭脸"),
	DIR("&#xe699;", "目录"),
	SETTING("&#xe6ae;", "设置"),
	PDF("&#xe740;", "PDF"),
	DATA("&#xe70c;", "数据"),
	COPY("&#xe744;", "拷贝"),
	CATEGORY("&#xe6b4;", "分类"),
	EDIT("&#xe69e;", "编辑"),
	INTER("&#xe648;", "接口"),
	MODULE("&#xe83b;", "模块"),
	REFRESH("&#xe6aa;", "刷新"),
	SEARCH("&#xe6ac;", "搜索"),
	DELETE("&#xe69d;", "删除"),
	USER("&#xe6b8;", "用户"),
	SAVE("&#xe747;", "保存"),
	ON_WAY("&#xe757;", "趋势"),
	DOLLAR("&#xe702;", "美元"),
	DEBUG("&#xe613;", "调试"),
	BUG("&#xe748;", "BUG"),
	CLOSE("&#xe69a;", "关闭");
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
