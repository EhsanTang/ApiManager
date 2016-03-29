package cn.crap.framework;

/**
 * biyao涓氬姟寮傚父绫�   鏈嶅姟灞傝繍琛屽紓甯告椂鐩存帴濉啓閿欒鐮侊紝鎶涘嚭寮傚父鍗冲彲
 * @author liuhui
 *
 */
public class BiyaoBizException  extends Exception{

	private static final long serialVersionUID = 8638237486526577302L;
	
	private String msgExtention;
	
	/**
	 * 鍚弬鏁版瀯閫犲櫒
	 * @param errorCode 涓氬姟閿欒鐮�
	 */
    public BiyaoBizException(String errorCode) {       
        super(errorCode);
    }
    
	/**
	 * 鍚弬鏁版瀯閫犲櫒
	 * @param errorCode 涓氬姟閿欒鐮�
	 */
    public BiyaoBizException(String errorCode,String msgExtention) {       
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
