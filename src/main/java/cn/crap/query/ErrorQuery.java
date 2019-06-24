package cn.crap.query;

import cn.crap.framework.MyException;
import cn.crap.utils.SafetyUtil;

import java.util.List;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class ErrorQuery extends BaseQuery<ErrorQuery> {
    private String errorCode;
    private String equalErrorCode;
    private String errorMsg;
    private List<String> errorCodeList;

    @Override
    public ErrorQuery getQuery() {
        return this;
    }

    public String getErrorCode() throws MyException {
        SafetyUtil.checkSqlParam(errorCode);
        return errorCode;
    }

    public ErrorQuery setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getEqualErrorCode() throws MyException {
        SafetyUtil.checkSqlParam(equalErrorCode);
        return equalErrorCode;
    }

    public ErrorQuery setEqualErrorCode(String equalErrorCode) {
        this.equalErrorCode = equalErrorCode;
        return this;
    }

    public String getErrorMsg() throws MyException {
        SafetyUtil.checkSqlParam(errorMsg);
        return errorMsg;
    }

    public ErrorQuery setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public List<String> getErrorCodeList() {
        return errorCodeList;
    }

    public ErrorQuery setErrorCodeList(List<String> errorCodeList) {
        this.errorCodeList = errorCodeList;
        return this;
    }
}
