package cn.crap.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InterfaceCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int maxResults = -1;

    public InterfaceCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults=maxResults;
    }

    public int getMaxResults() {
        return maxResults;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andMethodIsNull() {
            addCriterion("method is null");
            return (Criteria) this;
        }

        public Criteria andMethodIsNotNull() {
            addCriterion("method is not null");
            return (Criteria) this;
        }

        public Criteria andMethodEqualTo(String value) {
            addCriterion("method =", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotEqualTo(String value) {
            addCriterion("method <>", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodGreaterThan(String value) {
            addCriterion("method >", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodGreaterThanOrEqualTo(String value) {
            addCriterion("method >=", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodLessThan(String value) {
            addCriterion("method <", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodLessThanOrEqualTo(String value) {
            addCriterion("method <=", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodLike(String value) {
            addCriterion("method like", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotLike(String value) {
            addCriterion("method not like", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodIn(List<String> values) {
            addCriterion("method in", values, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotIn(List<String> values) {
            addCriterion("method not in", values, "method");
            return (Criteria) this;
        }

        public Criteria andMethodBetween(String value1, String value2) {
            addCriterion("method between", value1, value2, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotBetween(String value1, String value2) {
            addCriterion("method not between", value1, value2, "method");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andModuleIdIsNull() {
            addCriterion("moduleId is null");
            return (Criteria) this;
        }

        public Criteria andModuleIdIsNotNull() {
            addCriterion("moduleId is not null");
            return (Criteria) this;
        }

        public Criteria andModuleIdEqualTo(String value) {
            addCriterion("moduleId =", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andUniKeyEqualTo(String value) {
            addCriterion("uniKey =", value, "uniKey");
            return (Criteria) this;
        }

        public Criteria andModuleIdNotEqualTo(String value) {
            addCriterion("moduleId <>", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdGreaterThan(String value) {
            addCriterion("moduleId >", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdGreaterThanOrEqualTo(String value) {
            addCriterion("moduleId >=", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdLessThan(String value) {
            addCriterion("moduleId <", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdLessThanOrEqualTo(String value) {
            addCriterion("moduleId <=", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdLike(String value) {
            addCriterion("moduleId like", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdNotLike(String value) {
            addCriterion("moduleId not like", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdIn(List<String> values) {
            addCriterion("moduleId in", values, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdNotIn(List<String> values) {
            addCriterion("moduleId not in", values, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdBetween(String value1, String value2) {
            addCriterion("moduleId between", value1, value2, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdNotBetween(String value1, String value2) {
            addCriterion("moduleId not between", value1, value2, "moduleId");
            return (Criteria) this;
        }

        public Criteria andInterfaceNameIsNull() {
            addCriterion("interfaceName is null");
            return (Criteria) this;
        }

        public Criteria andInterfaceNameIsNotNull() {
            addCriterion("interfaceName is not null");
            return (Criteria) this;
        }

        public Criteria andInterfaceNameEqualTo(String value) {
            addCriterion("interfaceName =", value, "interfaceName");
            return (Criteria) this;
        }

        public Criteria andInterfaceNameNotEqualTo(String value) {
            addCriterion("interfaceName <>", value, "interfaceName");
            return (Criteria) this;
        }

        public Criteria andInterfaceNameGreaterThan(String value) {
            addCriterion("interfaceName >", value, "interfaceName");
            return (Criteria) this;
        }

        public Criteria andInterfaceNameGreaterThanOrEqualTo(String value) {
            addCriterion("interfaceName >=", value, "interfaceName");
            return (Criteria) this;
        }

        public Criteria andInterfaceNameLessThan(String value) {
            addCriterion("interfaceName <", value, "interfaceName");
            return (Criteria) this;
        }

        public Criteria andInterfaceNameLessThanOrEqualTo(String value) {
            addCriterion("interfaceName <=", value, "interfaceName");
            return (Criteria) this;
        }

        public Criteria andInterfaceNameLike(String value) {
            addCriterion("interfaceName like", value, "interfaceName");
            return (Criteria) this;
        }

        public Criteria andInterfaceNameNotLike(String value) {
            addCriterion("interfaceName not like", value, "interfaceName");
            return (Criteria) this;
        }

        public Criteria andInterfaceNameIn(List<String> values) {
            addCriterion("interfaceName in", values, "interfaceName");
            return (Criteria) this;
        }

        public Criteria andInterfaceNameNotIn(List<String> values) {
            addCriterion("interfaceName not in", values, "interfaceName");
            return (Criteria) this;
        }

        public Criteria andInterfaceNameBetween(String value1, String value2) {
            addCriterion("interfaceName between", value1, value2, "interfaceName");
            return (Criteria) this;
        }

        public Criteria andInterfaceNameNotBetween(String value1, String value2) {
            addCriterion("interfaceName not between", value1, value2, "interfaceName");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("updateBy is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("updateBy is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("updateBy =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("updateBy <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("updateBy >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("updateBy >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("updateBy <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("updateBy <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("updateBy like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("updateBy not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("updateBy in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("updateBy not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("updateBy between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("updateBy not between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("updateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("updateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("updateTime =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("updateTime <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("updateTime >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updateTime >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("updateTime <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("updateTime <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("updateTime in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("updateTime not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("updateTime between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("updateTime not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("createTime <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(String value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(String value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(String value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(String value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(String value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(String value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(String value) {
            addCriterion("version like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(String value) {
            addCriterion("version not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<String> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<String> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(String value1, String value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(String value1, String value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andSequenceIsNull() {
            addCriterion("sequence is null");
            return (Criteria) this;
        }

        public Criteria andSequenceIsNotNull() {
            addCriterion("sequence is not null");
            return (Criteria) this;
        }

        public Criteria andSequenceEqualTo(Integer value) {
            addCriterion("sequence =", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotEqualTo(Integer value) {
            addCriterion("sequence <>", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceGreaterThan(Integer value) {
            addCriterion("sequence >", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceGreaterThanOrEqualTo(Integer value) {
            addCriterion("sequence >=", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceLessThan(Integer value) {
            addCriterion("sequence <", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceLessThanOrEqualTo(Integer value) {
            addCriterion("sequence <=", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceIn(List<Integer> values) {
            addCriterion("sequence in", values, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotIn(List<Integer> values) {
            addCriterion("sequence not in", values, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceBetween(Integer value1, Integer value2) {
            addCriterion("sequence between", value1, value2, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotBetween(Integer value1, Integer value2) {
            addCriterion("sequence not between", value1, value2, "sequence");
            return (Criteria) this;
        }

        public Criteria andFullUrlIsNull() {
            addCriterion("fullUrl is null");
            return (Criteria) this;
        }

        public Criteria andFullUrlIsNotNull() {
            addCriterion("fullUrl is not null");
            return (Criteria) this;
        }

        public Criteria andFullUrlEqualTo(String value) {
            addCriterion("fullUrl =", value, "fullUrl");
            return (Criteria) this;
        }

        public Criteria andFullUrlNotEqualTo(String value) {
            addCriterion("fullUrl <>", value, "fullUrl");
            return (Criteria) this;
        }

        public Criteria andFullUrlGreaterThan(String value) {
            addCriterion("fullUrl >", value, "fullUrl");
            return (Criteria) this;
        }

        public Criteria andFullUrlGreaterThanOrEqualTo(String value) {
            addCriterion("fullUrl >=", value, "fullUrl");
            return (Criteria) this;
        }

        public Criteria andFullUrlLessThan(String value) {
            addCriterion("fullUrl <", value, "fullUrl");
            return (Criteria) this;
        }

        public Criteria andFullUrlLessThanOrEqualTo(String value) {
            addCriterion("fullUrl <=", value, "fullUrl");
            return (Criteria) this;
        }

        public Criteria andFullUrlLike(String value) {
            addCriterion("fullUrl like", value, "fullUrl");
            return (Criteria) this;
        }

        public Criteria andFullUrlNotLike(String value) {
            addCriterion("fullUrl not like", value, "fullUrl");
            return (Criteria) this;
        }

        public Criteria andFullUrlIn(List<String> values) {
            addCriterion("fullUrl in", values, "fullUrl");
            return (Criteria) this;
        }

        public Criteria andFullUrlNotIn(List<String> values) {
            addCriterion("fullUrl not in", values, "fullUrl");
            return (Criteria) this;
        }

        public Criteria andFullUrlBetween(String value1, String value2) {
            addCriterion("fullUrl between", value1, value2, "fullUrl");
            return (Criteria) this;
        }

        public Criteria andFullUrlNotBetween(String value1, String value2) {
            addCriterion("fullUrl not between", value1, value2, "fullUrl");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeIsNull() {
            addCriterion("monitorType is null");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeIsNotNull() {
            addCriterion("monitorType is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeEqualTo(Integer value) {
            addCriterion("monitorType =", value, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeNotEqualTo(Integer value) {
            addCriterion("monitorType <>", value, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeGreaterThan(Integer value) {
            addCriterion("monitorType >", value, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("monitorType >=", value, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeLessThan(Integer value) {
            addCriterion("monitorType <", value, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeLessThanOrEqualTo(Integer value) {
            addCriterion("monitorType <=", value, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeIn(List<Integer> values) {
            addCriterion("monitorType in", values, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeNotIn(List<Integer> values) {
            addCriterion("monitorType not in", values, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeBetween(Integer value1, Integer value2) {
            addCriterion("monitorType between", value1, value2, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("monitorType not between", value1, value2, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTextIsNull() {
            addCriterion("monitorText is null");
            return (Criteria) this;
        }

        public Criteria andMonitorTextIsNotNull() {
            addCriterion("monitorText is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorTextEqualTo(String value) {
            addCriterion("monitorText =", value, "monitorText");
            return (Criteria) this;
        }

        public Criteria andMonitorTextNotEqualTo(String value) {
            addCriterion("monitorText <>", value, "monitorText");
            return (Criteria) this;
        }

        public Criteria andMonitorTextGreaterThan(String value) {
            addCriterion("monitorText >", value, "monitorText");
            return (Criteria) this;
        }

        public Criteria andMonitorTextGreaterThanOrEqualTo(String value) {
            addCriterion("monitorText >=", value, "monitorText");
            return (Criteria) this;
        }

        public Criteria andMonitorTextLessThan(String value) {
            addCriterion("monitorText <", value, "monitorText");
            return (Criteria) this;
        }

        public Criteria andMonitorTextLessThanOrEqualTo(String value) {
            addCriterion("monitorText <=", value, "monitorText");
            return (Criteria) this;
        }

        public Criteria andMonitorTextLike(String value) {
            addCriterion("monitorText like", value, "monitorText");
            return (Criteria) this;
        }

        public Criteria andMonitorTextNotLike(String value) {
            addCriterion("monitorText not like", value, "monitorText");
            return (Criteria) this;
        }

        public Criteria andMonitorTextIn(List<String> values) {
            addCriterion("monitorText in", values, "monitorText");
            return (Criteria) this;
        }

        public Criteria andMonitorTextNotIn(List<String> values) {
            addCriterion("monitorText not in", values, "monitorText");
            return (Criteria) this;
        }

        public Criteria andMonitorTextBetween(String value1, String value2) {
            addCriterion("monitorText between", value1, value2, "monitorText");
            return (Criteria) this;
        }

        public Criteria andMonitorTextNotBetween(String value1, String value2) {
            addCriterion("monitorText not between", value1, value2, "monitorText");
            return (Criteria) this;
        }

        public Criteria andMonitorEmailsIsNull() {
            addCriterion("monitorEmails is null");
            return (Criteria) this;
        }

        public Criteria andMonitorEmailsIsNotNull() {
            addCriterion("monitorEmails is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorEmailsEqualTo(String value) {
            addCriterion("monitorEmails =", value, "monitorEmails");
            return (Criteria) this;
        }

        public Criteria andMonitorEmailsNotEqualTo(String value) {
            addCriterion("monitorEmails <>", value, "monitorEmails");
            return (Criteria) this;
        }

        public Criteria andMonitorEmailsGreaterThan(String value) {
            addCriterion("monitorEmails >", value, "monitorEmails");
            return (Criteria) this;
        }

        public Criteria andMonitorEmailsGreaterThanOrEqualTo(String value) {
            addCriterion("monitorEmails >=", value, "monitorEmails");
            return (Criteria) this;
        }

        public Criteria andMonitorEmailsLessThan(String value) {
            addCriterion("monitorEmails <", value, "monitorEmails");
            return (Criteria) this;
        }

        public Criteria andMonitorEmailsLessThanOrEqualTo(String value) {
            addCriterion("monitorEmails <=", value, "monitorEmails");
            return (Criteria) this;
        }

        public Criteria andMonitorEmailsLike(String value) {
            addCriterion("monitorEmails like", value, "monitorEmails");
            return (Criteria) this;
        }

        public Criteria andMonitorEmailsNotLike(String value) {
            addCriterion("monitorEmails not like", value, "monitorEmails");
            return (Criteria) this;
        }

        public Criteria andMonitorEmailsIn(List<String> values) {
            addCriterion("monitorEmails in", values, "monitorEmails");
            return (Criteria) this;
        }

        public Criteria andMonitorEmailsNotIn(List<String> values) {
            addCriterion("monitorEmails not in", values, "monitorEmails");
            return (Criteria) this;
        }

        public Criteria andMonitorEmailsBetween(String value1, String value2) {
            addCriterion("monitorEmails between", value1, value2, "monitorEmails");
            return (Criteria) this;
        }

        public Criteria andMonitorEmailsNotBetween(String value1, String value2) {
            addCriterion("monitorEmails not between", value1, value2, "monitorEmails");
            return (Criteria) this;
        }

        public Criteria andIsTemplateIsNull() {
            addCriterion("isTemplate is null");
            return (Criteria) this;
        }

        public Criteria andIsTemplateIsNotNull() {
            addCriterion("isTemplate is not null");
            return (Criteria) this;
        }

        public Criteria andIsTemplateEqualTo(Boolean value) {
            addCriterion("isTemplate =", value, "isTemplate");
            return (Criteria) this;
        }

        public Criteria andIsTemplateNotEqualTo(Boolean value) {
            addCriterion("isTemplate <>", value, "isTemplate");
            return (Criteria) this;
        }

        public Criteria andIsTemplateGreaterThan(Boolean value) {
            addCriterion("isTemplate >", value, "isTemplate");
            return (Criteria) this;
        }

        public Criteria andIsTemplateGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isTemplate >=", value, "isTemplate");
            return (Criteria) this;
        }

        public Criteria andIsTemplateLessThan(Boolean value) {
            addCriterion("isTemplate <", value, "isTemplate");
            return (Criteria) this;
        }

        public Criteria andIsTemplateLessThanOrEqualTo(Boolean value) {
            addCriterion("isTemplate <=", value, "isTemplate");
            return (Criteria) this;
        }

        public Criteria andIsTemplateIn(List<Boolean> values) {
            addCriterion("isTemplate in", values, "isTemplate");
            return (Criteria) this;
        }

        public Criteria andIsTemplateNotIn(List<Boolean> values) {
            addCriterion("isTemplate not in", values, "isTemplate");
            return (Criteria) this;
        }

        public Criteria andIsTemplateBetween(Boolean value1, Boolean value2) {
            addCriterion("isTemplate between", value1, value2, "isTemplate");
            return (Criteria) this;
        }

        public Criteria andIsTemplateNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isTemplate not between", value1, value2, "isTemplate");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNull() {
            addCriterion("projectId is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("projectId is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(String value) {
            addCriterion("projectId =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(String value) {
            addCriterion("projectId <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(String value) {
            addCriterion("projectId >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("projectId >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(String value) {
            addCriterion("projectId <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(String value) {
            addCriterion("projectId <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLike(String value) {
            addCriterion("projectId like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotLike(String value) {
            addCriterion("projectId not like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<String> values) {
            addCriterion("projectId in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<String> values) {
            addCriterion("projectId not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(String value1, String value2) {
            addCriterion("projectId between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(String value1, String value2) {
            addCriterion("projectId not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andContentTypeIsNull() {
            addCriterion("contentType is null");
            return (Criteria) this;
        }

        public Criteria andContentTypeIsNotNull() {
            addCriterion("contentType is not null");
            return (Criteria) this;
        }

        public Criteria andContentTypeEqualTo(String value) {
            addCriterion("contentType =", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotEqualTo(String value) {
            addCriterion("contentType <>", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeGreaterThan(String value) {
            addCriterion("contentType >", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeGreaterThanOrEqualTo(String value) {
            addCriterion("contentType >=", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeLessThan(String value) {
            addCriterion("contentType <", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeLessThanOrEqualTo(String value) {
            addCriterion("contentType <=", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeLike(String value) {
            addCriterion("contentType like", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotLike(String value) {
            addCriterion("contentType not like", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeIn(List<String> values) {
            addCriterion("contentType in", values, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotIn(List<String> values) {
            addCriterion("contentType not in", values, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeBetween(String value1, String value2) {
            addCriterion("contentType between", value1, value2, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotBetween(String value1, String value2) {
            addCriterion("contentType not between", value1, value2, "contentType");
            return (Criteria) this;
        }

        public Criteria andVersionNumIsNull() {
            addCriterion("versionNum is null");
            return (Criteria) this;
        }

        public Criteria andVersionNumIsNotNull() {
            addCriterion("versionNum is not null");
            return (Criteria) this;
        }

        public Criteria andVersionNumEqualTo(Integer value) {
            addCriterion("versionNum =", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumNotEqualTo(Integer value) {
            addCriterion("versionNum <>", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumGreaterThan(Integer value) {
            addCriterion("versionNum >", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("versionNum >=", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumLessThan(Integer value) {
            addCriterion("versionNum <", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumLessThanOrEqualTo(Integer value) {
            addCriterion("versionNum <=", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumIn(List<Integer> values) {
            addCriterion("versionNum in", values, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumNotIn(List<Integer> values) {
            addCriterion("versionNum not in", values, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumBetween(Integer value1, Integer value2) {
            addCriterion("versionNum between", value1, value2, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumNotBetween(Integer value1, Integer value2) {
            addCriterion("versionNum not between", value1, value2, "versionNum");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}