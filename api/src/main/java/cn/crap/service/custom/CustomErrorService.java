package cn.crap.service.custom;

import cn.crap.dao.mybatis.ErrorDao;
import cn.crap.model.mybatis.Error;
import cn.crap.model.mybatis.ErrorCriteria;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

@Service
public class CustomErrorService {
    @Autowired
    private ErrorDao mapper;

    public int countByProjectId(String projectId){
        Assert.notNull(projectId, "projectId can't be null");
        ErrorCriteria example = new ErrorCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);
        return mapper.countByExample(example);
    }

    public int countByProjectIdAndErrorCode(String projectId, String errorCode){
        Assert.notNull(projectId, "projectId can't be null");
        Assert.notNull(errorCode, "errorCode can't be null");
        ErrorCriteria example = new ErrorCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId).andErrorCodeEqualTo(errorCode);
        return mapper.countByExample(example);
    }

    public List<Error> queryByProjectIdAndErrorCode(String projectId, List<String> errorCodes){
        Assert.notNull(projectId, "projectId can't be null");
        if (errorCodes == null || errorCodes.size() == 0){
            return Collections.emptyList();
        }

        ErrorCriteria example = new ErrorCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId).andErrorCodeIn(errorCodes);

        return mapper.selectByExample(example);
    }

    /**
     * 根据项目id查询错误码
     * @param projectId
     * @param errorCode 非必填
     * @param errorMsg 非必填
     * @param page 非必填
     * @return
     */
    public List<Error> queryByProjectId(String projectId, String errorCode, String errorMsg, Page page){
        Assert.notNull(projectId, "projectId can't be null");

        ErrorCriteria example = new ErrorCriteria();
        ErrorCriteria.Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId);
        if (errorCode != null) {
            criteria.andErrorCodeLike("%" + errorCode + "%");
        }
        if (errorMsg != null) {
            criteria.andErrorMsgLike("%" + errorMsg + "%");
        }
        example.setOrderByClause(TableField.SORT.ERROR_CODE_ASC);

        if (page != null) {
            example.setLimitStart(page.getStart());
            example.setMaxResults(page.getSize());
            page.setAllRow(mapper.countByExample(example));
        }

        return mapper.selectByExample(example);
    }


}
