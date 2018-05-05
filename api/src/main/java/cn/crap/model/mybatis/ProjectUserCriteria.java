package cn.crap.model.mybatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectUserCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int maxResults = -1;

    public ProjectUserCriteria() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("userId is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("userId is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("userId =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("userId <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("userId >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("userId >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("userId <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("userId <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("userId like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("userId not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("userId in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("userId not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("userId between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("userId not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andAddModuleIsNull() {
            addCriterion("ADD_MODULE is null");
            return (Criteria) this;
        }

        public Criteria andAddModuleIsNotNull() {
            addCriterion("ADD_MODULE is not null");
            return (Criteria) this;
        }

        public Criteria andAddModuleEqualTo(Boolean value) {
            addCriterion("ADD_MODULE =", value, "ADD_MODULE");
            return (Criteria) this;
        }

        public Criteria andAddModuleNotEqualTo(Boolean value) {
            addCriterion("ADD_MODULE <>", value, "ADD_MODULE");
            return (Criteria) this;
        }

        public Criteria andAddModuleGreaterThan(Boolean value) {
            addCriterion("ADD_MODULE >", value, "ADD_MODULE");
            return (Criteria) this;
        }

        public Criteria andAddModuleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ADD_MODULE >=", value, "ADD_MODULE");
            return (Criteria) this;
        }

        public Criteria andAddModuleLessThan(Boolean value) {
            addCriterion("ADD_MODULE <", value, "ADD_MODULE");
            return (Criteria) this;
        }

        public Criteria andAddModuleLessThanOrEqualTo(Boolean value) {
            addCriterion("ADD_MODULE <=", value, "ADD_MODULE");
            return (Criteria) this;
        }

        public Criteria andAddModuleIn(List<Boolean> values) {
            addCriterion("ADD_MODULE in", values, "ADD_MODULE");
            return (Criteria) this;
        }

        public Criteria andAddModuleNotIn(List<Boolean> values) {
            addCriterion("ADD_MODULE not in", values, "ADD_MODULE");
            return (Criteria) this;
        }

        public Criteria andAddModuleBetween(Boolean value1, Boolean value2) {
            addCriterion("ADD_MODULE between", value1, value2, "ADD_MODULE");
            return (Criteria) this;
        }

        public Criteria andAddModuleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ADD_MODULE not between", value1, value2, "ADD_MODULE");
            return (Criteria) this;
        }

        public Criteria andDelModuleIsNull() {
            addCriterion("DEL_MODULE is null");
            return (Criteria) this;
        }

        public Criteria andDelModuleIsNotNull() {
            addCriterion("DEL_MODULE is not null");
            return (Criteria) this;
        }

        public Criteria andDelModuleEqualTo(Boolean value) {
            addCriterion("DEL_MODULE =", value, "DEL_MODULE");
            return (Criteria) this;
        }

        public Criteria andDelModuleNotEqualTo(Boolean value) {
            addCriterion("DEL_MODULE <>", value, "DEL_MODULE");
            return (Criteria) this;
        }

        public Criteria andDelModuleGreaterThan(Boolean value) {
            addCriterion("DEL_MODULE >", value, "DEL_MODULE");
            return (Criteria) this;
        }

        public Criteria andDelModuleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("DEL_MODULE >=", value, "DEL_MODULE");
            return (Criteria) this;
        }

        public Criteria andDelModuleLessThan(Boolean value) {
            addCriterion("DEL_MODULE <", value, "DEL_MODULE");
            return (Criteria) this;
        }

        public Criteria andDelModuleLessThanOrEqualTo(Boolean value) {
            addCriterion("DEL_MODULE <=", value, "DEL_MODULE");
            return (Criteria) this;
        }

        public Criteria andDelModuleIn(List<Boolean> values) {
            addCriterion("DEL_MODULE in", values, "DEL_MODULE");
            return (Criteria) this;
        }

        public Criteria andDelModuleNotIn(List<Boolean> values) {
            addCriterion("DEL_MODULE not in", values, "DEL_MODULE");
            return (Criteria) this;
        }

        public Criteria andDelModuleBetween(Boolean value1, Boolean value2) {
            addCriterion("DEL_MODULE between", value1, value2, "DEL_MODULE");
            return (Criteria) this;
        }

        public Criteria andDelModuleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("DEL_MODULE not between", value1, value2, "DEL_MODULE");
            return (Criteria) this;
        }

        public Criteria andModModuleIsNull() {
            addCriterion("MOD_MODULE is null");
            return (Criteria) this;
        }

        public Criteria andModModuleIsNotNull() {
            addCriterion("MOD_MODULE is not null");
            return (Criteria) this;
        }

        public Criteria andModModuleEqualTo(Boolean value) {
            addCriterion("MOD_MODULE =", value, "MOD_MODULE");
            return (Criteria) this;
        }

        public Criteria andModModuleNotEqualTo(Boolean value) {
            addCriterion("MOD_MODULE <>", value, "MOD_MODULE");
            return (Criteria) this;
        }

        public Criteria andModModuleGreaterThan(Boolean value) {
            addCriterion("MOD_MODULE >", value, "MOD_MODULE");
            return (Criteria) this;
        }

        public Criteria andModModuleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("MOD_MODULE >=", value, "MOD_MODULE");
            return (Criteria) this;
        }

        public Criteria andModModuleLessThan(Boolean value) {
            addCriterion("MOD_MODULE <", value, "MOD_MODULE");
            return (Criteria) this;
        }

        public Criteria andModModuleLessThanOrEqualTo(Boolean value) {
            addCriterion("MOD_MODULE <=", value, "MOD_MODULE");
            return (Criteria) this;
        }

        public Criteria andModModuleIn(List<Boolean> values) {
            addCriterion("MOD_MODULE in", values, "MOD_MODULE");
            return (Criteria) this;
        }

        public Criteria andModModuleNotIn(List<Boolean> values) {
            addCriterion("MOD_MODULE not in", values, "MOD_MODULE");
            return (Criteria) this;
        }

        public Criteria andModModuleBetween(Boolean value1, Boolean value2) {
            addCriterion("MOD_MODULE between", value1, value2, "MOD_MODULE");
            return (Criteria) this;
        }

        public Criteria andModModuleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("MOD_MODULE not between", value1, value2, "MOD_MODULE");
            return (Criteria) this;
        }

        public Criteria andAddInterIsNull() {
            addCriterion("ADD_INTER is null");
            return (Criteria) this;
        }

        public Criteria andAddInterIsNotNull() {
            addCriterion("ADD_INTER is not null");
            return (Criteria) this;
        }

        public Criteria andAddInterEqualTo(Boolean value) {
            addCriterion("ADD_INTER =", value, "ADD_INTER");
            return (Criteria) this;
        }

        public Criteria andAddInterNotEqualTo(Boolean value) {
            addCriterion("ADD_INTER <>", value, "ADD_INTER");
            return (Criteria) this;
        }

        public Criteria andAddInterGreaterThan(Boolean value) {
            addCriterion("ADD_INTER >", value, "ADD_INTER");
            return (Criteria) this;
        }

        public Criteria andAddInterGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ADD_INTER >=", value, "ADD_INTER");
            return (Criteria) this;
        }

        public Criteria andAddInterLessThan(Boolean value) {
            addCriterion("ADD_INTER <", value, "ADD_INTER");
            return (Criteria) this;
        }

        public Criteria andAddInterLessThanOrEqualTo(Boolean value) {
            addCriterion("ADD_INTER <=", value, "ADD_INTER");
            return (Criteria) this;
        }

        public Criteria andAddInterIn(List<Boolean> values) {
            addCriterion("ADD_INTER in", values, "ADD_INTER");
            return (Criteria) this;
        }

        public Criteria andAddInterNotIn(List<Boolean> values) {
            addCriterion("ADD_INTER not in", values, "ADD_INTER");
            return (Criteria) this;
        }

        public Criteria andAddInterBetween(Boolean value1, Boolean value2) {
            addCriterion("ADD_INTER between", value1, value2, "ADD_INTER");
            return (Criteria) this;
        }

        public Criteria andAddInterNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ADD_INTER not between", value1, value2, "ADD_INTER");
            return (Criteria) this;
        }

        public Criteria andDelInterIsNull() {
            addCriterion("DEL_INTER is null");
            return (Criteria) this;
        }

        public Criteria andDelInterIsNotNull() {
            addCriterion("DEL_INTER is not null");
            return (Criteria) this;
        }

        public Criteria andDelInterEqualTo(Boolean value) {
            addCriterion("DEL_INTER =", value, "DEL_INTER");
            return (Criteria) this;
        }

        public Criteria andDelInterNotEqualTo(Boolean value) {
            addCriterion("DEL_INTER <>", value, "DEL_INTER");
            return (Criteria) this;
        }

        public Criteria andDelInterGreaterThan(Boolean value) {
            addCriterion("DEL_INTER >", value, "DEL_INTER");
            return (Criteria) this;
        }

        public Criteria andDelInterGreaterThanOrEqualTo(Boolean value) {
            addCriterion("DEL_INTER >=", value, "DEL_INTER");
            return (Criteria) this;
        }

        public Criteria andDelInterLessThan(Boolean value) {
            addCriterion("DEL_INTER <", value, "DEL_INTER");
            return (Criteria) this;
        }

        public Criteria andDelInterLessThanOrEqualTo(Boolean value) {
            addCriterion("DEL_INTER <=", value, "DEL_INTER");
            return (Criteria) this;
        }

        public Criteria andDelInterIn(List<Boolean> values) {
            addCriterion("DEL_INTER in", values, "DEL_INTER");
            return (Criteria) this;
        }

        public Criteria andDelInterNotIn(List<Boolean> values) {
            addCriterion("DEL_INTER not in", values, "DEL_INTER");
            return (Criteria) this;
        }

        public Criteria andDelInterBetween(Boolean value1, Boolean value2) {
            addCriterion("DEL_INTER between", value1, value2, "DEL_INTER");
            return (Criteria) this;
        }

        public Criteria andDelInterNotBetween(Boolean value1, Boolean value2) {
            addCriterion("DEL_INTER not between", value1, value2, "DEL_INTER");
            return (Criteria) this;
        }

        public Criteria andModInterIsNull() {
            addCriterion("MOD_INTER is null");
            return (Criteria) this;
        }

        public Criteria andModInterIsNotNull() {
            addCriterion("MOD_INTER is not null");
            return (Criteria) this;
        }

        public Criteria andModInterEqualTo(Boolean value) {
            addCriterion("MOD_INTER =", value, "MOD_INTER");
            return (Criteria) this;
        }

        public Criteria andModInterNotEqualTo(Boolean value) {
            addCriterion("MOD_INTER <>", value, "MOD_INTER");
            return (Criteria) this;
        }

        public Criteria andModInterGreaterThan(Boolean value) {
            addCriterion("MOD_INTER >", value, "MOD_INTER");
            return (Criteria) this;
        }

        public Criteria andModInterGreaterThanOrEqualTo(Boolean value) {
            addCriterion("MOD_INTER >=", value, "MOD_INTER");
            return (Criteria) this;
        }

        public Criteria andModInterLessThan(Boolean value) {
            addCriterion("MOD_INTER <", value, "MOD_INTER");
            return (Criteria) this;
        }

        public Criteria andModInterLessThanOrEqualTo(Boolean value) {
            addCriterion("MOD_INTER <=", value, "MOD_INTER");
            return (Criteria) this;
        }

        public Criteria andModInterIn(List<Boolean> values) {
            addCriterion("MOD_INTER in", values, "MOD_INTER");
            return (Criteria) this;
        }

        public Criteria andModInterNotIn(List<Boolean> values) {
            addCriterion("MOD_INTER not in", values, "MOD_INTER");
            return (Criteria) this;
        }

        public Criteria andModInterBetween(Boolean value1, Boolean value2) {
            addCriterion("MOD_INTER between", value1, value2, "MOD_INTER");
            return (Criteria) this;
        }

        public Criteria andModInterNotBetween(Boolean value1, Boolean value2) {
            addCriterion("MOD_INTER not between", value1, value2, "MOD_INTER");
            return (Criteria) this;
        }

        public Criteria andAddArticleIsNull() {
            addCriterion("ADD_ARTICLE is null");
            return (Criteria) this;
        }

        public Criteria andAddArticleIsNotNull() {
            addCriterion("ADD_ARTICLE is not null");
            return (Criteria) this;
        }

        public Criteria andAddArticleEqualTo(Boolean value) {
            addCriterion("ADD_ARTICLE =", value, "ADD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andAddArticleNotEqualTo(Boolean value) {
            addCriterion("ADD_ARTICLE <>", value, "ADD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andAddArticleGreaterThan(Boolean value) {
            addCriterion("ADD_ARTICLE >", value, "ADD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andAddArticleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ADD_ARTICLE >=", value, "ADD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andAddArticleLessThan(Boolean value) {
            addCriterion("ADD_ARTICLE <", value, "ADD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andAddArticleLessThanOrEqualTo(Boolean value) {
            addCriterion("ADD_ARTICLE <=", value, "ADD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andAddArticleIn(List<Boolean> values) {
            addCriterion("ADD_ARTICLE in", values, "ADD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andAddArticleNotIn(List<Boolean> values) {
            addCriterion("ADD_ARTICLE not in", values, "ADD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andAddArticleBetween(Boolean value1, Boolean value2) {
            addCriterion("ADD_ARTICLE between", value1, value2, "ADD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andAddArticleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ADD_ARTICLE not between", value1, value2, "ADD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andDelArticleIsNull() {
            addCriterion("DEL_ARTICLE is null");
            return (Criteria) this;
        }

        public Criteria andDelArticleIsNotNull() {
            addCriterion("DEL_ARTICLE is not null");
            return (Criteria) this;
        }

        public Criteria andDelArticleEqualTo(Boolean value) {
            addCriterion("DEL_ARTICLE =", value, "DEL_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andDelArticleNotEqualTo(Boolean value) {
            addCriterion("DEL_ARTICLE <>", value, "DEL_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andDelArticleGreaterThan(Boolean value) {
            addCriterion("DEL_ARTICLE >", value, "DEL_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andDelArticleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("DEL_ARTICLE >=", value, "DEL_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andDelArticleLessThan(Boolean value) {
            addCriterion("DEL_ARTICLE <", value, "DEL_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andDelArticleLessThanOrEqualTo(Boolean value) {
            addCriterion("DEL_ARTICLE <=", value, "DEL_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andDelArticleIn(List<Boolean> values) {
            addCriterion("DEL_ARTICLE in", values, "DEL_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andDelArticleNotIn(List<Boolean> values) {
            addCriterion("DEL_ARTICLE not in", values, "DEL_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andDelArticleBetween(Boolean value1, Boolean value2) {
            addCriterion("DEL_ARTICLE between", value1, value2, "DEL_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andDelArticleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("DEL_ARTICLE not between", value1, value2, "DEL_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andModArticleIsNull() {
            addCriterion("MOD_ARTICLE is null");
            return (Criteria) this;
        }

        public Criteria andModArticleIsNotNull() {
            addCriterion("MOD_ARTICLE is not null");
            return (Criteria) this;
        }

        public Criteria andModArticleEqualTo(Boolean value) {
            addCriterion("MOD_ARTICLE =", value, "MOD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andModArticleNotEqualTo(Boolean value) {
            addCriterion("MOD_ARTICLE <>", value, "MOD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andModArticleGreaterThan(Boolean value) {
            addCriterion("MOD_ARTICLE >", value, "MOD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andModArticleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("MOD_ARTICLE >=", value, "MOD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andModArticleLessThan(Boolean value) {
            addCriterion("MOD_ARTICLE <", value, "MOD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andModArticleLessThanOrEqualTo(Boolean value) {
            addCriterion("MOD_ARTICLE <=", value, "MOD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andModArticleIn(List<Boolean> values) {
            addCriterion("MOD_ARTICLE in", values, "MOD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andModArticleNotIn(List<Boolean> values) {
            addCriterion("MOD_ARTICLE not in", values, "MOD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andModArticleBetween(Boolean value1, Boolean value2) {
            addCriterion("MOD_ARTICLE between", value1, value2, "MOD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andModArticleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("MOD_ARTICLE not between", value1, value2, "MOD_ARTICLE");
            return (Criteria) this;
        }

        public Criteria andAddSourceIsNull() {
            addCriterion("ADD_SOURCE is null");
            return (Criteria) this;
        }

        public Criteria andAddSourceIsNotNull() {
            addCriterion("ADD_SOURCE is not null");
            return (Criteria) this;
        }

        public Criteria andAddSourceEqualTo(Boolean value) {
            addCriterion("ADD_SOURCE =", value, "ADD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andAddSourceNotEqualTo(Boolean value) {
            addCriterion("ADD_SOURCE <>", value, "ADD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andAddSourceGreaterThan(Boolean value) {
            addCriterion("ADD_SOURCE >", value, "ADD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andAddSourceGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ADD_SOURCE >=", value, "ADD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andAddSourceLessThan(Boolean value) {
            addCriterion("ADD_SOURCE <", value, "ADD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andAddSourceLessThanOrEqualTo(Boolean value) {
            addCriterion("ADD_SOURCE <=", value, "ADD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andAddSourceIn(List<Boolean> values) {
            addCriterion("ADD_SOURCE in", values, "ADD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andAddSourceNotIn(List<Boolean> values) {
            addCriterion("ADD_SOURCE not in", values, "ADD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andAddSourceBetween(Boolean value1, Boolean value2) {
            addCriterion("ADD_SOURCE between", value1, value2, "ADD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andAddSourceNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ADD_SOURCE not between", value1, value2, "ADD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andDelSourceIsNull() {
            addCriterion("DEL_SOURCE is null");
            return (Criteria) this;
        }

        public Criteria andDelSourceIsNotNull() {
            addCriterion("DEL_SOURCE is not null");
            return (Criteria) this;
        }

        public Criteria andDelSourceEqualTo(Boolean value) {
            addCriterion("DEL_SOURCE =", value, "DEL_SOURCE");
            return (Criteria) this;
        }

        public Criteria andDelSourceNotEqualTo(Boolean value) {
            addCriterion("DEL_SOURCE <>", value, "DEL_SOURCE");
            return (Criteria) this;
        }

        public Criteria andDelSourceGreaterThan(Boolean value) {
            addCriterion("DEL_SOURCE >", value, "DEL_SOURCE");
            return (Criteria) this;
        }

        public Criteria andDelSourceGreaterThanOrEqualTo(Boolean value) {
            addCriterion("DEL_SOURCE >=", value, "DEL_SOURCE");
            return (Criteria) this;
        }

        public Criteria andDelSourceLessThan(Boolean value) {
            addCriterion("DEL_SOURCE <", value, "DEL_SOURCE");
            return (Criteria) this;
        }

        public Criteria andDelSourceLessThanOrEqualTo(Boolean value) {
            addCriterion("DEL_SOURCE <=", value, "DEL_SOURCE");
            return (Criteria) this;
        }

        public Criteria andDelSourceIn(List<Boolean> values) {
            addCriterion("DEL_SOURCE in", values, "DEL_SOURCE");
            return (Criteria) this;
        }

        public Criteria andDelSourceNotIn(List<Boolean> values) {
            addCriterion("DEL_SOURCE not in", values, "DEL_SOURCE");
            return (Criteria) this;
        }

        public Criteria andDelSourceBetween(Boolean value1, Boolean value2) {
            addCriterion("DEL_SOURCE between", value1, value2, "DEL_SOURCE");
            return (Criteria) this;
        }

        public Criteria andDelSourceNotBetween(Boolean value1, Boolean value2) {
            addCriterion("DEL_SOURCE not between", value1, value2, "DEL_SOURCE");
            return (Criteria) this;
        }

        public Criteria andModSourceIsNull() {
            addCriterion("MOD_SOURCE is null");
            return (Criteria) this;
        }

        public Criteria andModSourceIsNotNull() {
            addCriterion("MOD_SOURCE is not null");
            return (Criteria) this;
        }

        public Criteria andModSourceEqualTo(Boolean value) {
            addCriterion("MOD_SOURCE =", value, "MOD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andModSourceNotEqualTo(Boolean value) {
            addCriterion("MOD_SOURCE <>", value, "MOD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andModSourceGreaterThan(Boolean value) {
            addCriterion("MOD_SOURCE >", value, "MOD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andModSourceGreaterThanOrEqualTo(Boolean value) {
            addCriterion("MOD_SOURCE >=", value, "MOD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andModSourceLessThan(Boolean value) {
            addCriterion("MOD_SOURCE <", value, "MOD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andModSourceLessThanOrEqualTo(Boolean value) {
            addCriterion("MOD_SOURCE <=", value, "MOD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andModSourceIn(List<Boolean> values) {
            addCriterion("MOD_SOURCE in", values, "MOD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andModSourceNotIn(List<Boolean> values) {
            addCriterion("MOD_SOURCE not in", values, "MOD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andModSourceBetween(Boolean value1, Boolean value2) {
            addCriterion("MOD_SOURCE between", value1, value2, "MOD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andModSourceNotBetween(Boolean value1, Boolean value2) {
            addCriterion("MOD_SOURCE not between", value1, value2, "MOD_SOURCE");
            return (Criteria) this;
        }

        public Criteria andAddDictIsNull() {
            addCriterion("ADD_DICT is null");
            return (Criteria) this;
        }

        public Criteria andAddDictIsNotNull() {
            addCriterion("ADD_DICT is not null");
            return (Criteria) this;
        }

        public Criteria andAddDictEqualTo(Boolean value) {
            addCriterion("ADD_DICT =", value, "ADD_DICT");
            return (Criteria) this;
        }

        public Criteria andAddDictNotEqualTo(Boolean value) {
            addCriterion("ADD_DICT <>", value, "ADD_DICT");
            return (Criteria) this;
        }

        public Criteria andAddDictGreaterThan(Boolean value) {
            addCriterion("ADD_DICT >", value, "ADD_DICT");
            return (Criteria) this;
        }

        public Criteria andAddDictGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ADD_DICT >=", value, "ADD_DICT");
            return (Criteria) this;
        }

        public Criteria andAddDictLessThan(Boolean value) {
            addCriterion("ADD_DICT <", value, "ADD_DICT");
            return (Criteria) this;
        }

        public Criteria andAddDictLessThanOrEqualTo(Boolean value) {
            addCriterion("ADD_DICT <=", value, "ADD_DICT");
            return (Criteria) this;
        }

        public Criteria andAddDictIn(List<Boolean> values) {
            addCriterion("ADD_DICT in", values, "ADD_DICT");
            return (Criteria) this;
        }

        public Criteria andAddDictNotIn(List<Boolean> values) {
            addCriterion("ADD_DICT not in", values, "ADD_DICT");
            return (Criteria) this;
        }

        public Criteria andAddDictBetween(Boolean value1, Boolean value2) {
            addCriterion("ADD_DICT between", value1, value2, "ADD_DICT");
            return (Criteria) this;
        }

        public Criteria andAddDictNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ADD_DICT not between", value1, value2, "ADD_DICT");
            return (Criteria) this;
        }

        public Criteria andDelDictIsNull() {
            addCriterion("DEL_DICT is null");
            return (Criteria) this;
        }

        public Criteria andDelDictIsNotNull() {
            addCriterion("DEL_DICT is not null");
            return (Criteria) this;
        }

        public Criteria andDelDictEqualTo(Boolean value) {
            addCriterion("DEL_DICT =", value, "DEL_DICT");
            return (Criteria) this;
        }

        public Criteria andDelDictNotEqualTo(Boolean value) {
            addCriterion("DEL_DICT <>", value, "DEL_DICT");
            return (Criteria) this;
        }

        public Criteria andDelDictGreaterThan(Boolean value) {
            addCriterion("DEL_DICT >", value, "DEL_DICT");
            return (Criteria) this;
        }

        public Criteria andDelDictGreaterThanOrEqualTo(Boolean value) {
            addCriterion("DEL_DICT >=", value, "DEL_DICT");
            return (Criteria) this;
        }

        public Criteria andDelDictLessThan(Boolean value) {
            addCriterion("DEL_DICT <", value, "DEL_DICT");
            return (Criteria) this;
        }

        public Criteria andDelDictLessThanOrEqualTo(Boolean value) {
            addCriterion("DEL_DICT <=", value, "DEL_DICT");
            return (Criteria) this;
        }

        public Criteria andDelDictIn(List<Boolean> values) {
            addCriterion("DEL_DICT in", values, "DEL_DICT");
            return (Criteria) this;
        }

        public Criteria andDelDictNotIn(List<Boolean> values) {
            addCriterion("DEL_DICT not in", values, "DEL_DICT");
            return (Criteria) this;
        }

        public Criteria andDelDictBetween(Boolean value1, Boolean value2) {
            addCriterion("DEL_DICT between", value1, value2, "DEL_DICT");
            return (Criteria) this;
        }

        public Criteria andDelDictNotBetween(Boolean value1, Boolean value2) {
            addCriterion("DEL_DICT not between", value1, value2, "DEL_DICT");
            return (Criteria) this;
        }

        public Criteria andModDictIsNull() {
            addCriterion("MOD_DICT is null");
            return (Criteria) this;
        }

        public Criteria andModDictIsNotNull() {
            addCriterion("MOD_DICT is not null");
            return (Criteria) this;
        }

        public Criteria andModDictEqualTo(Boolean value) {
            addCriterion("MOD_DICT =", value, "MOD_DICT");
            return (Criteria) this;
        }

        public Criteria andModDictNotEqualTo(Boolean value) {
            addCriterion("MOD_DICT <>", value, "MOD_DICT");
            return (Criteria) this;
        }

        public Criteria andModDictGreaterThan(Boolean value) {
            addCriterion("MOD_DICT >", value, "MOD_DICT");
            return (Criteria) this;
        }

        public Criteria andModDictGreaterThanOrEqualTo(Boolean value) {
            addCriterion("MOD_DICT >=", value, "MOD_DICT");
            return (Criteria) this;
        }

        public Criteria andModDictLessThan(Boolean value) {
            addCriterion("MOD_DICT <", value, "MOD_DICT");
            return (Criteria) this;
        }

        public Criteria andModDictLessThanOrEqualTo(Boolean value) {
            addCriterion("MOD_DICT <=", value, "MOD_DICT");
            return (Criteria) this;
        }

        public Criteria andModDictIn(List<Boolean> values) {
            addCriterion("MOD_DICT in", values, "MOD_DICT");
            return (Criteria) this;
        }

        public Criteria andModDictNotIn(List<Boolean> values) {
            addCriterion("MOD_DICT not in", values, "MOD_DICT");
            return (Criteria) this;
        }

        public Criteria andModDictBetween(Boolean value1, Boolean value2) {
            addCriterion("MOD_DICT between", value1, value2, "MOD_DICT");
            return (Criteria) this;
        }

        public Criteria andModDictNotBetween(Boolean value1, Boolean value2) {
            addCriterion("MOD_DICT not between", value1, value2, "MOD_DICT");
            return (Criteria) this;
        }

        public Criteria andUserEmailIsNull() {
            addCriterion("userEmail is null");
            return (Criteria) this;
        }

        public Criteria andUserEmailIsNotNull() {
            addCriterion("userEmail is not null");
            return (Criteria) this;
        }

        public Criteria andUserEmailEqualTo(String value) {
            addCriterion("userEmail =", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotEqualTo(String value) {
            addCriterion("userEmail <>", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailGreaterThan(String value) {
            addCriterion("userEmail >", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailGreaterThanOrEqualTo(String value) {
            addCriterion("userEmail >=", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailLessThan(String value) {
            addCriterion("userEmail <", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailLessThanOrEqualTo(String value) {
            addCriterion("userEmail <=", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailLike(String value) {
            addCriterion("userEmail like", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotLike(String value) {
            addCriterion("userEmail not like", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailIn(List<String> values) {
            addCriterion("userEmail in", values, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotIn(List<String> values) {
            addCriterion("userEmail not in", values, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailBetween(String value1, String value2) {
            addCriterion("userEmail between", value1, value2, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotBetween(String value1, String value2) {
            addCriterion("userEmail not between", value1, value2, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("userName is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("userName is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("userName =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("userName <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("userName >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("userName >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("userName <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("userName <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("userName like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("userName not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("userName in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("userName not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("userName between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("userName not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andAddErrorIsNull() {
            addCriterion("ADD_ERROR is null");
            return (Criteria) this;
        }

        public Criteria andAddErrorIsNotNull() {
            addCriterion("ADD_ERROR is not null");
            return (Criteria) this;
        }

        public Criteria andAddErrorEqualTo(Boolean value) {
            addCriterion("ADD_ERROR =", value, "ADD_ERROR");
            return (Criteria) this;
        }

        public Criteria andAddErrorNotEqualTo(Boolean value) {
            addCriterion("ADD_ERROR <>", value, "ADD_ERROR");
            return (Criteria) this;
        }

        public Criteria andAddErrorGreaterThan(Boolean value) {
            addCriterion("ADD_ERROR >", value, "ADD_ERROR");
            return (Criteria) this;
        }

        public Criteria andAddErrorGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ADD_ERROR >=", value, "ADD_ERROR");
            return (Criteria) this;
        }

        public Criteria andAddErrorLessThan(Boolean value) {
            addCriterion("ADD_ERROR <", value, "ADD_ERROR");
            return (Criteria) this;
        }

        public Criteria andAddErrorLessThanOrEqualTo(Boolean value) {
            addCriterion("ADD_ERROR <=", value, "ADD_ERROR");
            return (Criteria) this;
        }

        public Criteria andAddErrorIn(List<Boolean> values) {
            addCriterion("ADD_ERROR in", values, "ADD_ERROR");
            return (Criteria) this;
        }

        public Criteria andAddErrorNotIn(List<Boolean> values) {
            addCriterion("ADD_ERROR not in", values, "ADD_ERROR");
            return (Criteria) this;
        }

        public Criteria andAddErrorBetween(Boolean value1, Boolean value2) {
            addCriterion("ADD_ERROR between", value1, value2, "ADD_ERROR");
            return (Criteria) this;
        }

        public Criteria andAddErrorNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ADD_ERROR not between", value1, value2, "ADD_ERROR");
            return (Criteria) this;
        }

        public Criteria andDelErrorIsNull() {
            addCriterion("DEL_ERROR is null");
            return (Criteria) this;
        }

        public Criteria andDelErrorIsNotNull() {
            addCriterion("DEL_ERROR is not null");
            return (Criteria) this;
        }

        public Criteria andDelErrorEqualTo(Boolean value) {
            addCriterion("DEL_ERROR =", value, "DEL_ERROR");
            return (Criteria) this;
        }

        public Criteria andDelErrorNotEqualTo(Boolean value) {
            addCriterion("DEL_ERROR <>", value, "DEL_ERROR");
            return (Criteria) this;
        }

        public Criteria andDelErrorGreaterThan(Boolean value) {
            addCriterion("DEL_ERROR >", value, "DEL_ERROR");
            return (Criteria) this;
        }

        public Criteria andDelErrorGreaterThanOrEqualTo(Boolean value) {
            addCriterion("DEL_ERROR >=", value, "DEL_ERROR");
            return (Criteria) this;
        }

        public Criteria andDelErrorLessThan(Boolean value) {
            addCriterion("DEL_ERROR <", value, "DEL_ERROR");
            return (Criteria) this;
        }

        public Criteria andDelErrorLessThanOrEqualTo(Boolean value) {
            addCriterion("DEL_ERROR <=", value, "DEL_ERROR");
            return (Criteria) this;
        }

        public Criteria andDelErrorIn(List<Boolean> values) {
            addCriterion("DEL_ERROR in", values, "DEL_ERROR");
            return (Criteria) this;
        }

        public Criteria andDelErrorNotIn(List<Boolean> values) {
            addCriterion("DEL_ERROR not in", values, "DEL_ERROR");
            return (Criteria) this;
        }

        public Criteria andDelErrorBetween(Boolean value1, Boolean value2) {
            addCriterion("DEL_ERROR between", value1, value2, "DEL_ERROR");
            return (Criteria) this;
        }

        public Criteria andDelErrorNotBetween(Boolean value1, Boolean value2) {
            addCriterion("DEL_ERROR not between", value1, value2, "DEL_ERROR");
            return (Criteria) this;
        }

        public Criteria andModErrorIsNull() {
            addCriterion("MOD_ERROR is null");
            return (Criteria) this;
        }

        public Criteria andModErrorIsNotNull() {
            addCriterion("MOD_ERROR is not null");
            return (Criteria) this;
        }

        public Criteria andModErrorEqualTo(Boolean value) {
            addCriterion("MOD_ERROR =", value, "MOD_ERROR");
            return (Criteria) this;
        }

        public Criteria andModErrorNotEqualTo(Boolean value) {
            addCriterion("MOD_ERROR <>", value, "MOD_ERROR");
            return (Criteria) this;
        }

        public Criteria andModErrorGreaterThan(Boolean value) {
            addCriterion("MOD_ERROR >", value, "MOD_ERROR");
            return (Criteria) this;
        }

        public Criteria andModErrorGreaterThanOrEqualTo(Boolean value) {
            addCriterion("MOD_ERROR >=", value, "MOD_ERROR");
            return (Criteria) this;
        }

        public Criteria andModErrorLessThan(Boolean value) {
            addCriterion("MOD_ERROR <", value, "MOD_ERROR");
            return (Criteria) this;
        }

        public Criteria andModErrorLessThanOrEqualTo(Boolean value) {
            addCriterion("MOD_ERROR <=", value, "MOD_ERROR");
            return (Criteria) this;
        }

        public Criteria andModErrorIn(List<Boolean> values) {
            addCriterion("MOD_ERROR in", values, "MOD_ERROR");
            return (Criteria) this;
        }

        public Criteria andModErrorNotIn(List<Boolean> values) {
            addCriterion("MOD_ERROR not in", values, "MOD_ERROR");
            return (Criteria) this;
        }

        public Criteria andModErrorBetween(Boolean value1, Boolean value2) {
            addCriterion("MOD_ERROR between", value1, value2, "MOD_ERROR");
            return (Criteria) this;
        }

        public Criteria andModErrorNotBetween(Boolean value1, Boolean value2) {
            addCriterion("MOD_ERROR not between", value1, value2, "MOD_ERROR");
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