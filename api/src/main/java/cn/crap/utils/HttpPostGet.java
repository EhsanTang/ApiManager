package cn.crap.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpPostGet {
	public final static String ACCEPT_JSON = "application/json";
	public static String post(String path, Map<String, String> params,Map<String, String> headers) throws Exception {
		HttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(path);
		httpPost.setHeader("charset", "utf-8");
		//默认超时时间为15s。
		RequestConfig requestConfig = RequestConfig.custom()
			    .setSocketTimeout(150000)
			    .setConnectTimeout(150000)
			    .setConnectionRequestTimeout(150000)
			    .setStaleConnectionCheckEnabled(true)
			    .build();	
		httpPost.setConfig(requestConfig);
		// 请求的参数信息传递
		List<NameValuePair> paires = new ArrayList<NameValuePair>();
		if( params != null ){
			Set<String> keys = params.keySet();
			Iterator<String> iterator = keys.iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				paires.add(new BasicNameValuePair(key, URLDecoder.decode(params.get(key), "UTF-8")));
			}
		}
		if( headers != null ){
			Set<String> keys = headers.keySet();
			Iterator<String> iterator = keys.iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				httpPost.setHeader(key,headers.get(key));
			}
		}
		
		if(paires.size() > 0){
			HttpEntity entity = new UrlEncodedFormEntity(paires,"utf-8");				  
			httpPost.setEntity(entity);
		}
		HttpResponse response = client.execute(httpPost);
		int status = response.getStatusLine().getStatusCode();
        if (status < 200 || status >=300) {                
            throw new ClientProtocolException("Path:"+path+"-Unexpected response status: " + status);
        }
		HttpEntity entity = response.getEntity();
		String body = EntityUtils.toString(entity, "UTF-8");
		return body;
	}

	public static String postBody(String cType,String url,String body) throws Exception{
		HttpClient client = HttpClients.createDefault();	
		HttpPost httppost = new HttpPost(url);
			httppost.setHeader("Accept", cType);
			httppost.setHeader("Content-Type", cType+";charset=utf-8");
			BasicHttpEntity requestBody = new BasicHttpEntity();
	        requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
	        requestBody.setContentLength(body.getBytes("UTF-8").length);
	        httppost.setEntity(requestBody);
	        // 执行客户端请求
			HttpResponse response = client.execute(httppost);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "UTF-8");
	}
	// 获取页面代码
	public static String Get(String path) throws Exception {
		HttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(path);
		httpGet.setHeader("charset", "utf-8");
		//默认超时时间为15s。
		RequestConfig requestConfig = RequestConfig.custom()
			    .setSocketTimeout(150000)
			    .setConnectTimeout(150000)
			    .setConnectionRequestTimeout(150000)
			    .setStaleConnectionCheckEnabled(true)
			    .build();	
		httpGet.setConfig(requestConfig);
		HttpResponse response = client.execute(httpGet);
		int status = response.getStatusLine().getStatusCode();
        if (status < 200 || status >=300) {                
            throw new ClientProtocolException("Path:"+path+"-Unexpected response status: " + status);
        }
		HttpEntity entity = response.getEntity();
		String body = EntityUtils.toString(entity, "UTF-8");
		return body;
	}
	// 获取页面代码
		public static InputStream GetString(String path) throws Exception {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(3 * 1000);
			conn.setRequestProperty("Accept-Language", "zh-CN");
			conn.setRequestProperty(
					"User-Agent",
					"User-Agent:Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setUseCaches(false);// 不进行缓存
			// 头部必须设置不缓存，否则第二次获取不到sessionID
			conn.setUseCaches(false);
			if (conn.getResponseCode() == 200) {
				return conn.getInputStream();
			}
			return null;
		}
}
