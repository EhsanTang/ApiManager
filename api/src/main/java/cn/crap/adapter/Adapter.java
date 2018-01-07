package cn.crap.adapter;

import cn.crap.enumer.LogType;
import cn.crap.model.mybatis.Log;
import cn.crap.utils.LoginUserHelper;
import net.sf.json.JSONObject;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.Assert;

/**
 * @author Ehsan
 * @date 18/1/1 18:52
 */
public class Adapter {
    public static Log getLog(String id, String modelName, String remark, LogType type, Class c, Object model){
        Assert.notNull(id);
        Assert.notNull(type);
        Log log = new Log();
        log.setModelName(modelName);
        log.setRemark(remark);
        log.setType(type.name());
        log.setContent(JSONObject.fromObject(model).toString());
        log.setModelClass(c.getSimpleName());
        log.setIdenty(id);
        try {
            log.setUpdateBy(LoginUserHelper.getUser().getTrueName());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  log;
    }
}
