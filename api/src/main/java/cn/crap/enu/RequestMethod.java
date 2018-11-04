package cn.crap.enu;

public enum RequestMethod {
	POST("POST"),GET("GET"),PUT("PUT"),HEAD("HEAD"),DELETE("DELETE"),OPTIONS("OPTIONS"),TRACE("TRACE"),PATCH("PATCH");
	private final String name;
	
	private RequestMethod(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
