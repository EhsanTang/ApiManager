package cn.crap.framework;

import cn.crap.enu.MyError;

public class MyException  extends Exception{

	private static final long serialVersionUID = 8638237486526577302L;

	private MyError myError;
	private String tip;
	
    public MyException(MyError error) {
		this.myError = error;
    }
    
    public MyException(MyError error, String msgExtention) {
    	this.myError = error;
        this.setTip(msgExtention);
    }

	public String getTip() {
		return tip;
	}

	private void setTip(String tip) {
		this.tip = tip;
	}

	public String getMessage(){
        return myError == null ? "" : myError.getMessage();
    }

    public String getErrorCode(){
        return myError == null ? "" : myError.name();
    }

    public String getEnMessage(){
        return myError == null ? "" : myError.getEnMessage();
    }
}
