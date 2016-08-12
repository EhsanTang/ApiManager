package cn.crap.framework;

public class MyException  extends Exception{

	private static final long serialVersionUID = 8638237486526577302L;
	
	private String msgExtention;
	
    public MyException(String errorCode) {       
        super(errorCode);
    }
    
    public MyException(String errorCode,String msgExtention) {       
        super(errorCode);
        this.setMsgExtention(msgExtention);
    }

	public String getMsgExtention() {
		return msgExtention;
	}

	private void setMsgExtention(String msgExtention) {
		this.msgExtention = msgExtention;
	}
}
