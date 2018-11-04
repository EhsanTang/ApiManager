package cn.crap.enu;

import org.springframework.util.StringUtils;

public enum InterfaceContentType {
	JSON("application/json", "application/json"), HTML("text/html", "text/html"),
	X_APPLICATION("x-application", "x-application"), XML("application/xml", "application/xml");
	private final String name;
	private final String type;

	public static InterfaceContentType getByType(String type){
	    if (StringUtils.isEmpty(type)){
	        return null;
        }

		for( InterfaceContentType contentType : InterfaceContentType.values()){
			if(contentType.getType().equals(type)){
				return contentType;
			}
		}
		return null;
	}

	public static String getNameByType(String type){
	    return getByType(type) == null ? "" : getByType(type).getName();
    }

	InterfaceContentType(String name, String type){
		this.name = name;
		this.type = type;
	}
	public String getName(){
		return name;
	}

	public String getType() {
		return type;
	}
}
