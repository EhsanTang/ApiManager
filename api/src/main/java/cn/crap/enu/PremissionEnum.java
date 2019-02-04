package cn.crap.enu;

public enum PremissionEnum {

	 MY_DATE("myData",""),
	 READ("read",""),
	 MOD_INTER("modInter",""),
	 ADD_INTER("addInter",""),
	 DEL_INTER("delInter",""),

	 MOD_MODULE("modModule",""),
	 ADD_MODULE("addModule",""),
	 DEL_MODULE("delMODULE",""),

	 MOD_ARTICLE("modArticle",""),
	 ADD_ARTICLE("addArticle",""),
	 DEL_ARTICLE("delArticle",""),

	 MOD_DICT("modDict",""),
	 ADD_DICT("addDict",""),
	 DEL_DICT("delDict",""),

	 MOD_SOURCE("modSource",""),
	 ADD_SOURCE("addSource",""),
	 DEL_SOURCE("delSource",""),

	 MOD_ERROR("modError",""),
	 ADD_ERROR("addError",""),
	 DEL_ERROR("delError","");

	private final String premission;
	private final String desc;

	PremissionEnum(String premission, String desc){
		this.premission = premission;
		this.desc = desc;
	}
	public String  getPremission(){
		return premission;
	}
}
