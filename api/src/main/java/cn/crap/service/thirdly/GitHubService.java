package cn.crap.service.thirdly;

import java.util.Map;

import cn.crap.enu.MyError;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import cn.crap.dto.thirdly.GitHubAccessToken;
import cn.crap.dto.thirdly.GitHubUser;
import cn.crap.framework.MyException;
import cn.crap.beans.Config;
import cn.crap.utils.HttpPostGet;
import cn.crap.utils.Tools;

@Service
public class GitHubService {
	   public GitHubAccessToken getAccessToken(String code,String redirect_uri) throws Exception{
	        String url ="https://github.com/login/oauth/access_token";
	        Map<String,String> params = Tools.getStrMap("client_id",Config.clientID,
	        		"client_secret",Config.clientSecret,"code",code,"redirect_uri",redirect_uri);
	        
	        String rs = HttpPostGet.post(url, params, Tools.getStrMap("Accept","application/json"), 8000);
	        GitHubAccessToken accessToken = JSON.parseObject(rs,GitHubAccessToken.class);
	        if(accessToken == null || accessToken.getAccess_token() == null)
	            throw new MyException(MyError.E000026);
	        return accessToken;
	    }

	    public GitHubUser getUser(String accessToken) throws Exception{
	        String url = "https://api.github.com/user?access_token="+accessToken;
	        Map<String, String> headerMap = Maps.newHashMap();
			headerMap.put("Authorization", "token " + accessToken);
	        String rs = HttpPostGet.get(url, null, headerMap, 8000);
	        if(rs.contains("message")){
	        	throw new MyException(MyError.E000026, rs);
			}
	        return JSON.parseObject(rs,GitHubUser.class);
	    }

}
