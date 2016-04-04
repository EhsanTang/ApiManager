package cn.crap.framework;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class ErrorInfos {
	
	private Map<String, String> errMsgs;

	public Map<String, String> getErrMsgs() {
		return errMsgs;
	}

	public void setErrMsgs(Map<String, String> errMsgs) {
		this.errMsgs = errMsgs;
	}

	public Map<String, String> getErrMap(String code) {
		Map<String, String> returnMap = new HashMap<String, String>();
		if (errMsgs.get(code) != null) {
			returnMap.put("code", code);
			returnMap.put("msg", errMsgs.get(code));
		}
		return returnMap;
	}
	
	public String getMessage(String code) {
		return errMsgs.get(code);
	}
	public Map<String, String> getErrMap(String code, String msg) {
		Map<String, String> returnMap = new HashMap<String, String>();
		if (errMsgs.get(code) != null) {
			returnMap.put("code", code);
			returnMap.put("msg", errMsgs.get(code) + msg);
		}
		return returnMap;
	}
	
	

}
