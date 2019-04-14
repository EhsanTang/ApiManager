package cn.crap.enu;

import cn.crap.utils.IConst;
import cn.crap.utils.MyString;

public enum ProjectPermissionEnum {

	 MY_DATE("myData", "只有项目创建人才能执行该操作"),
	 READ("read", IConst.NULL),
	 MOD_INTER("modInter","修改接口", true, "接口权限"),
	 ADD_INTER("addInter","添加接口"),
	 DEL_INTER("delInter","删除接口"),

	 MOD_MODULE("modModule","修改模块", true, "模块权限"),
	 ADD_MODULE("addModule","添加模块"),
	 DEL_MODULE("delModule","删除模块"),

	MOD_ERROR("modError","修改状态码", true, "状态码权限"),
	ADD_ERROR("addError","添加状态码"),
	DEL_ERROR("delError","删除状态码"),

	 MOD_ARTICLE("modArticle","修改文档", true, "文档权限"),
	 ADD_ARTICLE("addArticle","添加文档"),
	 DEL_ARTICLE("delArticle","删除文档"),

	 MOD_DICT("modDict","修改数据库表", true, "数据库表权限"),
	 ADD_DICT("addDict","添加数据库表"),
	 DEL_DICT("delDict","删除数据库表"),

	MOD_BUG("modBug","修改缺陷", true, "缺陷权限"),
	ADD_BUG("addBug","添加缺陷"),
	DEL_BUG("delBug","删除缺陷"),

	 MOD_SOURCE("modSource","修改文件", true, "文件权限"),
	 ADD_SOURCE("addSource","添加文件"),
	 DEL_SOURCE("delSource","删除文件"),

	MOD_ENV("modEnv","修改项目环境", true, "项目环境权限"),
	ADD_ENV("addEnv","添加项目环境"),
	DEL_ENV("delEnv","删除项目环境");




	 public static boolean isDefaultPermission(ProjectPermissionEnum permissionEnum){
	 	if (permissionEnum == null){
	 		return false;
		}
		if (permissionEnum == ProjectPermissionEnum.MY_DATE || permissionEnum == ProjectPermissionEnum.READ
		|| permissionEnum == ProjectPermissionEnum.DEL_ERROR || permissionEnum == ProjectPermissionEnum.DEL_ARTICLE
		|| permissionEnum == ProjectPermissionEnum.DEL_DICT || permissionEnum == ProjectPermissionEnum.DEL_INTER
		|| permissionEnum == ProjectPermissionEnum.DEL_MODULE || permissionEnum == ProjectPermissionEnum.DEL_SOURCE){
			return false;
		}
		return true;
	 }
	private final String value;
	private final String desc;
	private final boolean separator;
	private final String separatorTitle;


	ProjectPermissionEnum(String value, String desc, boolean separator, String separatorTitle){
		this.value = value;
		this.desc = desc;
		this.separator = separator;
		this.separatorTitle = separatorTitle;
	}

	ProjectPermissionEnum(String value, String desc){
		this(value, desc, false, "");
	}

	public static ProjectPermissionEnum getByValue(String value){
		if (MyString.isEmpty(value)){
			return null;
		}
		for( ProjectPermissionEnum permissionEnum : ProjectPermissionEnum.values()){
			if(permissionEnum.getValue().equals(value)){
				return permissionEnum;
			}
		}
		return null;
	}

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

	public boolean isSeparator() {
		return separator;
	}

	public String getSeparatorTitle() {
		return separatorTitle;
	}
}
