package cn.crap.schedule;

import cn.crap.utils.HttpPostGet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取开源统计信息
 * @author Ehsan
 * @date 2018/10/5 15:51
 */
public class OpenSourceInfoTask extends AbstractTask {
    private static Logger log = Logger.getLogger(OpenSourceInfoTask.class);

    private static final String GITHUB_REPOS_API = "https://api.github.com/repos/EhsanTang/ApiManager";
    private static final String GITEE_REPOS_API = "https://gitee.com/api/v5/repos/CrapApi/CrapApi";
    private static long time = 0;
    public static volatile String forNumStr = "--";
    public static volatile String starNumStr = "--";

    @Override
    void doTask() {
        try {
            int forNum = 0;
            int starNum = 0;
            // 一个小时获取一次新的数据
            if (System.currentTimeMillis() - time > 60 * 60 * 1000) {
                log.info("----查询git fork、stars数量-----");
                JSONObject jsonObject = JSON.parseObject( HttpPostGet.get(GITHUB_REPOS_API, null, null, 5000));
                forNum = jsonObject.getInteger("forks_count");
                starNum = jsonObject.getInteger("stargazers_count");

                Map<String,String> headers = new HashMap<>();
                headers.put("Cache-Control", "no-cache");
                jsonObject =  JSON.parseObject( HttpPostGet.get(GITEE_REPOS_API, null, headers, 5000));
                forNum = forNum + jsonObject.getInteger("forks_count");
                starNum = starNum + jsonObject.getInteger("stargazers_count");

                forNumStr = forNum + "";
                starNumStr = starNum + "";
                time = System.currentTimeMillis();
            }
        }catch (Exception e){
            time = System.currentTimeMillis() - 50 * 60 * 1000L;
            log.info("----查询git fork、stars数量异常，10分钟后再次尝试-----", e);
        }
    }
}
