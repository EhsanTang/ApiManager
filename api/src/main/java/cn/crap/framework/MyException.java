package cn.crap.framework;

import cn.crap.enumer.MyError;
import org.springframework.util.Assert;

public class MyException  extends Exception{

	private static final long serialVersionUID = 8638237486526577302L;

	private MyError myError;
	private String msgExtention;
	
    public MyException(MyError error) {
		this.myError = error;
    }
    
    public MyException(MyError error, String msgExtention) {
    	this.myError = error;
        this.setMsgExtention(msgExtention);
    }

	public String getMsgExtention() {
		return msgExtention;
	}

	private void setMsgExtention(String msgExtention) {
		this.msgExtention = msgExtention;
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
