package cn.crap.framework;

import java.util.HashMap;
import java.util.Map;

public class ErrorInfos {
	
	private static Map<String, String> errMsgs;

	public static Map<String, String> getErrMsgs() {
		return errMsgs;
	}

	public static void setErrMsgs(Map<String, String> errMsgs) {
		ErrorInfos.errMsgs = errMsgs;
	}

	public Map<String, String> getErrMap(String code) {
		Map<String, String> returnMap = new HashMap<String, String>();
		if (errMsgs.get(code) != null) {
			returnMap.put("code", code);
			returnMap.put("msg", errMsgs.get(code));
		}
		return returnMap;
	}
	
	public static String getMessage(String code) {
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
