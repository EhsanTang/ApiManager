package cn.crap.framework;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 蹇呰鐨勯敊璇俊鎭�
 * @author liuhui
 *
 */
@Component
public class BiyaoErrors {
	
	private Map<String, String> errMsgs;

	public Map<String, String> getErrMsgs() {
		return errMsgs;
	}

	public void setErrMsgs(Map<String, String> errMsgs) {
		this.errMsgs = errMsgs;
	}

	/**
	 * @娉ㄦ硶璇存槑:浠ap琛岃繑鍥為敊璇俊鎭強缂栫爜
	 **/
	public Map<String, String> getErrMap(String code) {
		Map<String, String> returnMap = new HashMap<String, String>();
		if (errMsgs.get(code) != null) {
			returnMap.put("code", code);
			returnMap.put("msg", errMsgs.get(code));
		}
		return returnMap;
	}
	
	/**
	 * @娉ㄦ硶璇存槑:浠ap琛岃繑鍥為敊璇俊鎭強缂栫爜
	 **/
	public String getMessage(String code) {
		return errMsgs.get(code);
	}

	/**
	 * @娉ㄦ硶璇存槑:浠ap琛岃繑鍥為敊璇俊鎭強缂栫爜
	 **/
	public Map<String, String> getErrMap(String code, String msg) {
		Map<String, String> returnMap = new HashMap<String, String>();
		if (errMsgs.get(code) != null) {
			returnMap.put("code", code);
			returnMap.put("msg", errMsgs.get(code) + msg);
		}
		return returnMap;
	}
	
	

}
