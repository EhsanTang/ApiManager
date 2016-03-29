package cn.crap.framework;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import cn.crap.utils.Page;

public class JsonResult implements Serializable {
	private static final long serialVersionUID = 7553249056983455065L;
	private Page page;
	@Autowired
	private BiyaoErrors biyaoErrors;
	
	public JsonResult(Integer success,Object data,String errorCode,String errorMessage){
		this.data = data;
		this.success = success;
		if(success == 0){
			this.error = new ErrorMessage(errorCode,errorMessage);
		}
	}
	
	public JsonResult(Integer success,Object data,Page page){
		this.data = data;
		this.success = success;
		this.page = page;
	}
	public JsonResult(Integer success,Object data){
		this.data = data;
		this.success = success;
	}

	public JsonResult(BiyaoBizException exception){
		this.data = null;
		this.success = 0;
		
		BiyaoErrors errors = SpringContextHolder.getBean("biyaoErrors", BiyaoErrors.class);
		String errorCode = exception.getMessage();
		String errorMsg =  errors.getMessage(errorCode);
		this.setError( new ErrorMessage(errorCode,errorMsg+(exception.getMsgExtention()==null?"":exception.getMsgExtention()),exception.getMsgExtention()));
	}
	
	
	private Integer success;
	private Object data;
	private  ErrorMessage error;

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ErrorMessage getError() {
		return error;
	}

	public void setError(ErrorMessage error) {
		this.error = error;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
}

class ErrorMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8519677473220335082L;
	private String code;
	private String message;
	private Object data;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public ErrorMessage(String code,String message){
		this.setCode(code);
		this.setMessage(message);
	}
	public ErrorMessage(String code,String message,Object data){
		this.setCode(code);
		this.setMessage(message);
		this.setData(data);
	}
	
}
