package cn.crap.framework.base;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.BiyaoBizException;
import cn.crap.framework.JsonResult;
import cn.crap.utils.Page;


@Scope("prototype")
public class BaseController<T extends BaseModel> {
	protected Page page= new Page(15);
	protected Map<String,Object> map;
	protected Map<String,Object> returnMap = new HashMap<String,Object>();
	@Autowired
	protected HttpServletRequest request;

	/**
	 * @return
	 */
	protected HashMap<String, String> getRequestHeaders()  {
		HashMap<String, String> requestHeaders = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();

		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			requestHeaders.put(headerName, headerValue);
		}
		return requestHeaders;
	}

	/**
	 * @return
	 */
	protected HashMap<String, String> getRequestParams() {
		HashMap<String, String> requestParams = new HashMap<String, String>();
		Enumeration<String> paramNames = request.getParameterNames();

		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			requestParams.put(paramName, paramValue);
		}
		return requestParams;
	}
	
	 @RequestMapping("/error.do")
	 @ExceptionHandler  
	 @ResponseBody  
     public JsonResult expHandler(HttpServletRequest request, Exception ex) {  
        if(ex instanceof BiyaoBizException) {  
            return new JsonResult((BiyaoBizException)ex);
        } else {  
        	ex.printStackTrace();
        	return new JsonResult(new BiyaoBizException("000001",ex.getMessage()));
        }  
    }  
	 
	 @RequestMapping("/loginCheckFail.do")
	 @ResponseBody  
     public JsonResult loginCheckFail() {  
		 BiyaoBizException ex = new BiyaoBizException("0000001");
         return new JsonResult((BiyaoBizException)ex);
    }  

	 protected void handleBindingValidation(BindingResult bindingResult) throws BiyaoBizException{
	        if(bindingResult.hasErrors()){
	            List<ObjectError> list = bindingResult.getAllErrors();
	            StringBuilder msg= new StringBuilder();
	            for(ObjectError error:list){
	            	msg.append(error.getDefaultMessage()+";");
	            }
	            throw new BiyaoBizException("0",msg.toString());
	        }
	    }
	 protected Object getParam(String key, String def) {
			String value = request.getParameter(key);
			return value==null?def:value;
		}
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	 
}  