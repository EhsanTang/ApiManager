package cn.crap.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpPostGet {
    public final static String ACCEPT_JSON = "application/json";

    public static String get(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        return get(path, params, headers, 3000);
    }

    public static String get(String path, Map<String, String> params, Map<String, String> headers, int timeout) throws Exception {
        path = getPath(path, params);
        HttpGet method = new HttpGet(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
        method.setConfig(requestConfig);
        return getResponse(method, headers);
    }

    public static String delete(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        path = getPath(path, params);
        HttpDelete method = new HttpDelete(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000).setStaleConnectionCheckEnabled(true).build();
        method.setConfig(requestConfig);
        return getResponse(method, headers);
    }

    public static String options(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        path = getPath(path, params);
        HttpOptions method = new HttpOptions(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000).setStaleConnectionCheckEnabled(true).build();
        method.setConfig(requestConfig);
        return getResponse(method, headers);
    }

    public static String trace(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        path = getPath(path, params);
        HttpTrace method = new HttpTrace(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000).setStaleConnectionCheckEnabled(true).build();
        method.setConfig(requestConfig);
        return getResponse(method, headers);
    }

    public static String head(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        path = getPath(path, params);
        HttpHead method = new HttpHead(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000).setStaleConnectionCheckEnabled(true).build();
        method.setConfig(requestConfig);
        return getHead(method, headers);
    }

    public static String put(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        HttpPut method = new HttpPut(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000).setStaleConnectionCheckEnabled(true).build();
        // 请求的参数信息传递
        List<NameValuePair> pairs = buildPairs(params);
        if (pairs.size() > 0) {
            HttpEntity entity = new UrlEncodedFormEntity(pairs, "utf-8");
            method.setEntity(entity);
        }
        method.setConfig(requestConfig);
        return getResponse(method, headers);
    }
    public static String patch(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        HttpPatch method = new HttpPatch(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000).setStaleConnectionCheckEnabled(true).build();
        // 请求的参数信息传递
        List<NameValuePair> pairs = buildPairs(params);
        if (pairs.size() > 0) {
            HttpEntity entity = new UrlEncodedFormEntity(pairs, "utf-8");
            method.setEntity(entity);
        }
        method.setConfig(requestConfig);
        return getResponse(method, headers);
    }

    public static String post(String path, Map<String, String> params, Map<String, String> headers, int timeout) throws Exception {
        HttpPost method = new HttpPost(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
        // 请求的参数信息传递
        List<NameValuePair> pairs = buildPairs(params);
        if (pairs.size() > 0) {
            HttpEntity entity = new UrlEncodedFormEntity(pairs, "utf-8");
            method.setEntity(entity);
        }
        method.setConfig(requestConfig);
        return getResponse(method, headers);
    }

    public static String post(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        return post(path, params, headers, 3000);
    }

    public static String postBody(String url, String body, Map<String, String> headers) throws Exception {
       return postBody(url, body, headers, 3000);
    }


    public static String postBody(String url, String body, Map<String, String> headers, int timeout) throws Exception {
        HttpClient client = buildHttpClient(url);
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("charset", "utf-8");

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
        httppost.setConfig(requestConfig);
        buildHeader(headers, httppost);

        BasicHttpEntity requestBody = new BasicHttpEntity();
        requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
        requestBody.setContentLength(body.getBytes("UTF-8").length);
        httppost.setEntity(requestBody);
        // 执行客户端请求
        HttpResponse response = client.execute(httppost);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, "UTF-8");
    }

    /*********************************私有方法***************************************************/
    public static HttpClient buildHttpClient(String url) throws Exception{
        if (url.startsWith("https")){
            SSLContext sslcontext = createIgnoreVerifySSL();
            //创建自定义的httpclient对象
            SSLConnectionSocketFactory fac = new SSLConnectionSocketFactory(sslcontext,
                    new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"}, null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(fac).build();
            return client;
        }
        return HttpClients.createDefault();
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

    private static List<NameValuePair> buildPairs(Map<String, String> params) throws UnsupportedEncodingException {
        List<NameValuePair> pairs = new ArrayList<>();
        if (params != null) {
            Set<String> keys = params.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                pairs.add(new BasicNameValuePair(key, URLDecoder.decode(params.get(key), "UTF-8")));
            }
        }
        return pairs;
    }

    private static String getResponse(HttpUriRequest method, Map<String, String> headers)
            throws Exception {
        HttpClient client = buildHttpClient(method.getURI().getScheme());
        method.setHeader("charset", "utf-8");

        if (headers != null) {
            Set<String> keys = headers.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                method.setHeader(key, headers.get(key));
                method.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                method.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63");
            }
        }

        HttpResponse response = client.execute(method);
        int status = response.getStatusLine().getStatusCode();
        if (status < 200 || status >= 300) {
            throw new ClientProtocolException("Path:" + method.getURI() + " - Unexpected response status: " + status);
        }
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity, "UTF-8");
        return body;
    }

    private static String getHead(HttpUriRequest method, Map<String, String> headers) throws Exception {
        HttpClient client = buildHttpClient(method.getURI().getScheme());
        method.setHeader("charset", "utf-8");
        // 默认超时时间为15s。
        buildHeader(headers, method);
        HttpResponse response = client.execute(method);
        Header[] responseHeaders = response.getAllHeaders();
        StringBuilder sb = new StringBuilder("");
        for (Header h : responseHeaders) {
            sb.append(h.getName() + ":" + h.getValue() + "\r\n");
        }
        return sb.toString();
    }

    private static void buildHeader(Map<String, String> headers, HttpUriRequest request) {
        if (headers != null) {
            Set<String> keys = headers.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                request.setHeader(key, headers.get(key));
            }
        }
    }

    private static String getPath(String path, Map<String, String> params) throws UnsupportedEncodingException {
        if (params != null) {
            if (path.indexOf("?") > -1) {
                path += "&";
            } else {
                path += "?";
            }
            Set<String> keys = params.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                path += key + "=" + URLEncoder.encode(params.get(key), "UTF-8") + "&";
            }
            if (path.endsWith("&"))
                path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    // 获取页面代码
    public static InputStream getInputStream(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(3 * 1000);
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("User-Agent",
                "User-Agent:Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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
