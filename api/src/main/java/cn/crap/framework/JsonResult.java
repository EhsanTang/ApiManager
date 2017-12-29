package cn.crap.framework;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import cn.crap.utils.Page;

public class JsonResult implements Serializable {
	private static final long serialVersionUID = 7553249056983455065L;
	private Page page;
	private Integer success;
	private Object data;
	private  ErrorMessage error;
	//传递至前端的其他参数
	private Object others;

	public JsonResult(){
		this.success = 1;
	}
	public JsonResult(Integer success,Object data,String errorCode,String errorMessage){
		this.data = data;
		this.success = success;
		if(success == 0){
			this.error = new ErrorMessage(errorCode,errorMessage);
		}
	}
	public JsonResult(Integer success,Object data,Page page,Object others){
		this( success, data, page);
		this.others = others;
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

	public JsonResult(MyException exception){
		this.data = null;
		this.success = 0;
		String errorCode = exception.getMessage();
		String errorMsg =  ErrorInfos.getMessage(errorCode);
		this.setError( new ErrorMessage(errorCode,errorMsg+(exception.getMsgExtention()==null?"":exception.getMsgExtention())));
	}

	public JsonResult putOthers(String key, Object value){
		if (this.others == null){
			this.others = new HashMap<>();
		}
		((Map<String, Object>)this.others).put(key, value);
		return this;
	}
	public JsonResult(String errorCode){
		this.data = null;
		this.success = 0;
		String errorMsg =  ErrorInfos.getMessage(errorCode);
		this.setError( new ErrorMessage(errorCode,errorMsg) );
	}

	public JsonResult success(){
		this.success = 1;
		return this;
	}

	public JsonResult others(Object others){
		this.others = others;
		return this;
	}


	public JsonResult data(Object data){
		this.data = data;
		return this;
	}

	public JsonResult page(Page page){
		this.page = page;
		return this;
	}

	
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
	public Object getOthers() {
		return others;
	}
	public void setOthers(Object others) {
		this.others = others;
	}
	
	
}

class ErrorMessage{
	private String code;
	private String message;
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
	
	public ErrorMessage(String code,String message){
		this.setCode(code);
		this.setMessage(message);
	}
}
