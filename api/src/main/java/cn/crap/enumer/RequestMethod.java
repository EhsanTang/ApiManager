package cn.crap.enumer;

public enum RequestMethod {
	POST("POST"),GET("GET"),PUT("PUT"),HEAD("HEAD"),DELETE("DELETE"),OPTIONS("OPTIONS"),TRACE("TRACE");
	private final String name;
	
	private RequestMethod(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
