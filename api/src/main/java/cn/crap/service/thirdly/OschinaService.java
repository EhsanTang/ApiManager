package cn.crap.service.thirdly;

import cn.crap.beans.Config;
import cn.crap.dto.thirdly.GitHubAccessToken;
import cn.crap.dto.thirdly.GitHubUser;
import cn.crap.enu.MyError;
import cn.crap.framework.MyException;
import cn.crap.service.tool.SettingCache;
import cn.crap.utils.HttpPostGet;
import cn.crap.utils.Tools;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OschinaService {

	@Autowired
	private SettingCache settingCache;

	   public GitHubAccessToken getAccessToken(String code,String redirect_uri) throws Exception{
			String oschinaAuthUrl = "https://gitee.com/oauth/token";

	        Map<String,String> params = Tools.getStrMap("grant_type", "authorization_code", "client_id", Config.oschinaClientID,
	        		"client_secret", Config.oschinaClientSecret,"code", code,"redirect_uri", redirect_uri);
	        
	        String rs = HttpPostGet.post(oschinaAuthUrl, params, Tools.getStrMap("Accept","application/json"), 10000);
	        System.out.println(rs);
	        GitHubAccessToken accessToken = JSON.parseObject(rs, GitHubAccessToken.class);
	        if(accessToken == null || accessToken.getAccess_token() == null) {
				throw new MyException(MyError.E000026);
			}
	        return accessToken;
	    }

	    public GitHubUser getUser(String accessToken) throws Exception{
	        String url = "http://gitee.com/api/v5/user?access_token="+accessToken;
	        String rs = HttpPostGet.get(url, null, Tools.getStrMap("Accept","application/json"), 10000);
	        System.out.println(rs);
	        if(rs.contains("message"))
	        	throw new MyException(MyError.E000026, rs);
	        return JSON.parseObject(rs,GitHubUser.class);
	    }
}
