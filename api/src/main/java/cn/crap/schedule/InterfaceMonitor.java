//package cn.crap.schedule;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//import org.apache.commons.lang.StringEscapeUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//
//import cn.crap.enumeration.MonitorType;
//import cn.crap.service.IInterfaceService;
//import cn.crap.service.ICacheService;
//import cn.crap.service.IEmailService;
//import cn.crap.model.Interface;
//import cn.crap.springbeans.Config;
//import cn.crap.utils.Const;
//import cn.crap.utils.HttpPostGet;
//import cn.crap.utils.MyString;
//import cn.crap.utils.Tools;
//
//public class InterfaceMonitor implements Task {
//	@Autowired
//	private ICacheService cacheService;
//	@Autowired
//	private IInterfaceService interfaceService;
//	@Autowired
//	private IEmailService emailService;
//
//	private int time=0;// 接口读取缓存实际
//	private int tryTimes = 0;
//	private int emailSendIndex = 2;// 邮件发送指数
//	private Executor exec;
//	private String errorTemplet = "第%s次：返回数据有误，result=%s<br/>";
//	@Autowired
//	public void setExecutor(Config config){
//		if(exec == null){
//			exec = Executors.newFixedThreadPool(Config.cacheTime);
//			time = config.getMonitorCacheTime();
//			tryTimes = config.getMonitorTryTimes();
//		}
//	}
//
//    @SuppressWarnings("unchecked")
//	@Override
//	public void doTask(){
//    	try{
//			List<Interface> interfaces = null;
//	    	if(time>0){
//	    		interfaces = (List<Interface>) cacheService.getObj(Const.CACHE_MONITOR_INTERFACES);
//	    	}
//
//	    	if(interfaces == null){
//	    		interfaces = interfaceService.findByMap(Tools.getMap("monitorType|>",0), null, null);
//	    		if(time>0){
//	    			cacheService.setObj(Const.CACHE_MONITOR_INTERFACES, interfaces, time);
//	    		}
//	    	}
//	    	for(Interface inter:interfaces){
//	    		task(inter);
//	    	}
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}
//    }
//
//    private void task(final Interface inter){
//    	Runnable task = new Runnable() {
//			@Override
//			public void run() {
//				inter.getMonitorType();
//				String method = (","+inter.getMethod());
//				String result = "";
//				int myTryTimes = 0;
//				int errorTimes = 0;
//				StringBuilder errorMesg = new StringBuilder();
//				while(myTryTimes < tryTimes){
//					myTryTimes ++;
//					try{
//						if(method.contains(",POST,") && !inter.getParam().startsWith("form=")){
//							result = HttpPostGet.postBody(inter.getFullUrl(), inter.getParam(), getMapFromStr(inter.getHeader()));
//						}else if(method.contains(",POST,")){
//							result = HttpPostGet.post(inter.getFullUrl(), getMapFromStr(inter.getParam().substring(5)), getMapFromStr(inter.getHeader()));
//						}else if(method.contains(",GET,")){
//							result = HttpPostGet.post(inter.getFullUrl(), getMapFromStr(inter.getParam().substring(5)), getMapFromStr(inter.getHeader()));
//						}else if(method.contains(",PUT,")){
//							result = HttpPostGet.post(inter.getFullUrl(), getMapFromStr(inter.getParam().substring(5)), getMapFromStr(inter.getHeader()));
//						}else if(method.contains(",DELETE,")){
//							result = HttpPostGet.post(inter.getFullUrl(), getMapFromStr(inter.getParam().substring(5)), getMapFromStr(inter.getHeader()));
//						}else if(method.contains(",HEAD,")){
//							result = HttpPostGet.post(inter.getFullUrl(), getMapFromStr(inter.getParam().substring(5)), getMapFromStr(inter.getHeader()));
//						}else if(method.contains(",OPTIONS,")){
//							result = HttpPostGet.post(inter.getFullUrl(), getMapFromStr(inter.getParam().substring(5)), getMapFromStr(inter.getHeader()));
//						}else if(method.contains(",TRACE,")){
//							result = HttpPostGet.post(inter.getFullUrl(), getMapFromStr(inter.getParam().substring(5)), getMapFromStr(inter.getHeader()));
//						}
//					}catch(Exception e){
//						// 网络异常
//						errorTimes ++;
//						errorMesg.append("第"+myTryTimes+"次：网络异常，"+e.getMessage()+"<br/>");
//						continue;
//					}
//
//					if(inter.getMonitorType() == MonitorType.NetworkInclude.getValue()){
//						if( result.contains(inter.getMonitorText().trim()) ){
//							errorTimes ++;
//							errorMesg.append( String.format(errorTemplet, myTryTimes+"", StringEscapeUtils.escapeHtml(result) ) );
//							continue;
//						}
//					}
//
//					if(inter.getMonitorType() == MonitorType.NetworkNotInclude.getValue()){
//						if( !result.contains(inter.getMonitorText().trim()) ){
//							errorTimes ++;
//							errorMesg.append( String.format(errorTemplet, myTryTimes+"", StringEscapeUtils.escapeHtml(result) ) );
//							continue;
//						}
//					}
//
//					if(inter.getMonitorType() == MonitorType.NetworkNotEqual.getValue()){
//						if( inter.getMonitorText().trim().equals(result.trim()) ){
//							errorTimes ++;
//							errorMesg.append( String.format(errorTemplet, myTryTimes+"", StringEscapeUtils.escapeHtml(result) ) );
//							continue;
//						}
//					}
//
//					if(inter.getMonitorType() == MonitorType.NetworkEqual.getValue()){
//						if( !inter.getMonitorText().trim().equals(result.trim()) ){
//							errorTimes ++;
//							errorMesg.append( String.format(errorTemplet, myTryTimes+"", StringEscapeUtils.escapeHtml(result) ) );
//							continue;
//						}
//					}
//					break;
//				}
//				if(errorTimes > 0 && !MyString.isEmpty(inter.getMonitorEmails())){
//					int hasSendTimes = 1;
//					if(cacheService.getStr(Const.CACHE_MONITOR_INTERFACES_HAS_SEND_EMAIL + inter.getId()) == null){
//						String hasSendTimesStr = cacheService.getStr(Const.CACHE_MONITOR_INTERFACES_EMAIL_TIMES + inter.getId());
//						if( hasSendTimesStr != null){
//							hasSendTimes = Integer.parseInt(hasSendTimesStr) + 1;
//						}
//						// 一天内有效
//						cacheService.setStr(Const.CACHE_MONITOR_INTERFACES_EMAIL_TIMES + inter.getId(), hasSendTimes+"", 3*60*60);
//						cacheService.setStr(Const.CACHE_MONITOR_INTERFACES_HAS_SEND_EMAIL + inter.getId(), "1", (int) Math.pow(emailSendIndex, hasSendTimes));
//
//						for(String email:inter.getMonitorEmails().split(";")){
//							emailService.sendMail("接口监控：" + inter.getInterfaceName(), email, "尝试："+myTryTimes+"次，失败："+errorTimes+"次<br/>"+errorMesg.toString());
//						}
//					}
//				}
//			}
//		};
//    	exec.execute(task);
//    }
//
//    private Map<String, String> getMapFromStr(String str){
//    	JSONArray jsonStr = JSONArray.parseArray(str);
//    	Map<String, String> map = new HashMap<String, String>();
//    	for(int i=0; i< jsonStr.size(); i++){
//    		JSONObject json = jsonStr.getJSONObject(i);
//    		map.put(json.getString("name"), json.getString("def"));
//    	}
//		return map;
//    }
//}