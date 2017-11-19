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
            addCriterion("addModule is null");
            return (Criteria) this;
        }

        public Criteria andAddModuleIsNotNull() {
            addCriterion("addModule is not null");
            return (Criteria) this;
        }

        public Criteria andAddModuleEqualTo(Boolean value) {
            addCriterion("addModule =", value, "addModule");
            return (Criteria) this;
        }

        public Criteria andAddModuleNotEqualTo(Boolean value) {
            addCriterion("addModule <>", value, "addModule");
            return (Criteria) this;
        }

        public Criteria andAddModuleGreaterThan(Boolean value) {
            addCriterion("addModule >", value, "addModule");
            return (Criteria) this;
        }

        public Criteria andAddModuleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("addModule >=", value, "addModule");
            return (Criteria) this;
        }

        public Criteria andAddModuleLessThan(Boolean value) {
            addCriterion("addModule <", value, "addModule");
            return (Criteria) this;
        }

        public Criteria andAddModuleLessThanOrEqualTo(Boolean value) {
            addCriterion("addModule <=", value, "addModule");
            return (Criteria) this;
        }

        public Criteria andAddModuleIn(List<Boolean> values) {
            addCriterion("addModule in", values, "addModule");
            return (Criteria) this;
        }

        public Criteria andAddModuleNotIn(List<Boolean> values) {
            addCriterion("addModule not in", values, "addModule");
            return (Criteria) this;
        }

        public Criteria andAddModuleBetween(Boolean value1, Boolean value2) {
            addCriterion("addModule between", value1, value2, "addModule");
            return (Criteria) this;
        }

        public Criteria andAddModuleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("addModule not between", value1, value2, "addModule");
            return (Criteria) this;
        }

        public Criteria andDelModuleIsNull() {
            addCriterion("delModule is null");
            return (Criteria) this;
        }

        public Criteria andDelModuleIsNotNull() {
            addCriterion("delModule is not null");
            return (Criteria) this;
        }

        public Criteria andDelModuleEqualTo(Boolean value) {
            addCriterion("delModule =", value, "delModule");
            return (Criteria) this;
        }

        public Criteria andDelModuleNotEqualTo(Boolean value) {
            addCriterion("delModule <>", value, "delModule");
            return (Criteria) this;
        }

        public Criteria andDelModuleGreaterThan(Boolean value) {
            addCriterion("delModule >", value, "delModule");
            return (Criteria) this;
        }

        public Criteria andDelModuleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("delModule >=", value, "delModule");
            return (Criteria) this;
        }

        public Criteria andDelModuleLessThan(Boolean value) {
            addCriterion("delModule <", value, "delModule");
            return (Criteria) this;
        }

        public Criteria andDelModuleLessThanOrEqualTo(Boolean value) {
            addCriterion("delModule <=", value, "delModule");
            return (Criteria) this;
        }

        public Criteria andDelModuleIn(List<Boolean> values) {
            addCriterion("delModule in", values, "delModule");
            return (Criteria) this;
        }

        public Criteria andDelModuleNotIn(List<Boolean> values) {
            addCriterion("delModule not in", values, "delModule");
            return (Criteria) this;
        }

        public Criteria andDelModuleBetween(Boolean value1, Boolean value2) {
            addCriterion("delModule between", value1, value2, "delModule");
            return (Criteria) this;
        }

        public Criteria andDelModuleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("delModule not between", value1, value2, "delModule");
            return (Criteria) this;
        }

        public Criteria andModModuleIsNull() {
            addCriterion("modModule is null");
            return (Criteria) this;
        }

        public Criteria andModModuleIsNotNull() {
            addCriterion("modModule is not null");
            return (Criteria) this;
        }

        public Criteria andModModuleEqualTo(Boolean value) {
            addCriterion("modModule =", value, "modModule");
            return (Criteria) this;
        }

        public Criteria andModModuleNotEqualTo(Boolean value) {
            addCriterion("modModule <>", value, "modModule");
            return (Criteria) this;
        }

        public Criteria andModModuleGreaterThan(Boolean value) {
            addCriterion("modModule >", value, "modModule");
            return (Criteria) this;
        }

        public Criteria andModModuleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("modModule >=", value, "modModule");
            return (Criteria) this;
        }

        public Criteria andModModuleLessThan(Boolean value) {
            addCriterion("modModule <", value, "modModule");
            return (Criteria) this;
        }

        public Criteria andModModuleLessThanOrEqualTo(Boolean value) {
            addCriterion("modModule <=", value, "modModule");
            return (Criteria) this;
        }

        public Criteria andModModuleIn(List<Boolean> values) {
            addCriterion("modModule in", values, "modModule");
            return (Criteria) this;
        }

        public Criteria andModModuleNotIn(List<Boolean> values) {
            addCriterion("modModule not in", values, "modModule");
            return (Criteria) this;
        }

        public Criteria andModModuleBetween(Boolean value1, Boolean value2) {
            addCriterion("modModule between", value1, value2, "modModule");
            return (Criteria) this;
        }

        public Criteria andModModuleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("modModule not between", value1, value2, "modModule");
            return (Criteria) this;
        }

        public Criteria andAddInterIsNull() {
            addCriterion("addInter is null");
            return (Criteria) this;
        }

        public Criteria andAddInterIsNotNull() {
            addCriterion("addInter is not null");
            return (Criteria) this;
        }

        public Criteria andAddInterEqualTo(Boolean value) {
            addCriterion("addInter =", value, "addInter");
            return (Criteria) this;
        }

        public Criteria andAddInterNotEqualTo(Boolean value) {
            addCriterion("addInter <>", value, "addInter");
            return (Criteria) this;
        }

        public Criteria andAddInterGreaterThan(Boolean value) {
            addCriterion("addInter >", value, "addInter");
            return (Criteria) this;
        }

        public Criteria andAddInterGreaterThanOrEqualTo(Boolean value) {
            addCriterion("addInter >=", value, "addInter");
            return (Criteria) this;
        }

        public Criteria andAddInterLessThan(Boolean value) {
            addCriterion("addInter <", value, "addInter");
            return (Criteria) this;
        }

        public Criteria andAddInterLessThanOrEqualTo(Boolean value) {
            addCriterion("addInter <=", value, "addInter");
            return (Criteria) this;
        }

        public Criteria andAddInterIn(List<Boolean> values) {
            addCriterion("addInter in", values, "addInter");
            return (Criteria) this;
        }

        public Criteria andAddInterNotIn(List<Boolean> values) {
            addCriterion("addInter not in", values, "addInter");
            return (Criteria) this;
        }

        public Criteria andAddInterBetween(Boolean value1, Boolean value2) {
            addCriterion("addInter between", value1, value2, "addInter");
            return (Criteria) this;
        }

        public Criteria andAddInterNotBetween(Boolean value1, Boolean value2) {
            addCriterion("addInter not between", value1, value2, "addInter");
            return (Criteria) this;
        }

        public Criteria andDelInterIsNull() {
            addCriterion("delInter is null");
            return (Criteria) this;
        }

        public Criteria andDelInterIsNotNull() {
            addCriterion("delInter is not null");
            return (Criteria) this;
        }

        public Criteria andDelInterEqualTo(Boolean value) {
            addCriterion("delInter =", value, "delInter");
            return (Criteria) this;
        }

        public Criteria andDelInterNotEqualTo(Boolean value) {
            addCriterion("delInter <>", value, "delInter");
            return (Criteria) this;
        }

        public Criteria andDelInterGreaterThan(Boolean value) {
            addCriterion("delInter >", value, "delInter");
            return (Criteria) this;
        }

        public Criteria andDelInterGreaterThanOrEqualTo(Boolean value) {
            addCriterion("delInter >=", value, "delInter");
            return (Criteria) this;
        }

        public Criteria andDelInterLessThan(Boolean value) {
            addCriterion("delInter <", value, "delInter");
            return (Criteria) this;
        }

        public Criteria andDelInterLessThanOrEqualTo(Boolean value) {
            addCriterion("delInter <=", value, "delInter");
            return (Criteria) this;
        }

        public Criteria andDelInterIn(List<Boolean> values) {
            addCriterion("delInter in", values, "delInter");
            return (Criteria) this;
        }

        public Criteria andDelInterNotIn(List<Boolean> values) {
            addCriterion("delInter not in", values, "delInter");
            return (Criteria) this;
        }

        public Criteria andDelInterBetween(Boolean value1, Boolean value2) {
            addCriterion("delInter between", value1, value2, "delInter");
            return (Criteria) this;
        }

        public Criteria andDelInterNotBetween(Boolean value1, Boolean value2) {
            addCriterion("delInter not between", value1, value2, "delInter");
            return (Criteria) this;
        }

        public Criteria andModInterIsNull() {
            addCriterion("modInter is null");
            return (Criteria) this;
        }

        public Criteria andModInterIsNotNull() {
            addCriterion("modInter is not null");
            return (Criteria) this;
        }

        public Criteria andModInterEqualTo(Boolean value) {
            addCriterion("modInter =", value, "modInter");
            return (Criteria) this;
        }

        public Criteria andModInterNotEqualTo(Boolean value) {
            addCriterion("modInter <>", value, "modInter");
            return (Criteria) this;
        }

        public Criteria andModInterGreaterThan(Boolean value) {
            addCriterion("modInter >", value, "modInter");
            return (Criteria) this;
        }

        public Criteria andModInterGreaterThanOrEqualTo(Boolean value) {
            addCriterion("modInter >=", value, "modInter");
            return (Criteria) this;
        }

        public Criteria andModInterLessThan(Boolean value) {
            addCriterion("modInter <", value, "modInter");
            return (Criteria) this;
        }

        public Criteria andModInterLessThanOrEqualTo(Boolean value) {
            addCriterion("modInter <=", value, "modInter");
            return (Criteria) this;
        }

        public Criteria andModInterIn(List<Boolean> values) {
            addCriterion("modInter in", values, "modInter");
            return (Criteria) this;
        }

        public Criteria andModInterNotIn(List<Boolean> values) {
            addCriterion("modInter not in", values, "modInter");
            return (Criteria) this;
        }

        public Criteria andModInterBetween(Boolean value1, Boolean value2) {
            addCriterion("modInter between", value1, value2, "modInter");
            return (Criteria) this;
        }

        public Criteria andModInterNotBetween(Boolean value1, Boolean value2) {
            addCriterion("modInter not between", value1, value2, "modInter");
            return (Criteria) this;
        }

        public Criteria andAddArticleIsNull() {
            addCriterion("addArticle is null");
            return (Criteria) this;
        }

        public Criteria andAddArticleIsNotNull() {
            addCriterion("addArticle is not null");
            return (Criteria) this;
        }

        public Criteria andAddArticleEqualTo(Boolean value) {
            addCriterion("addArticle =", value, "addArticle");
            return (Criteria) this;
        }

        public Criteria andAddArticleNotEqualTo(Boolean value) {
            addCriterion("addArticle <>", value, "addArticle");
            return (Criteria) this;
        }

        public Criteria andAddArticleGreaterThan(Boolean value) {
            addCriterion("addArticle >", value, "addArticle");
            return (Criteria) this;
        }

        public Criteria andAddArticleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("addArticle >=", value, "addArticle");
            return (Criteria) this;
        }

        public Criteria andAddArticleLessThan(Boolean value) {
            addCriterion("addArticle <", value, "addArticle");
            return (Criteria) this;
        }

        public Criteria andAddArticleLessThanOrEqualTo(Boolean value) {
            addCriterion("addArticle <=", value, "addArticle");
            return (Criteria) this;
        }

        public Criteria andAddArticleIn(List<Boolean> values) {
            addCriterion("addArticle in", values, "addArticle");
            return (Criteria) this;
        }

        public Criteria andAddArticleNotIn(List<Boolean> values) {
            addCriterion("addArticle not in", values, "addArticle");
            return (Criteria) this;
        }

        public Criteria andAddArticleBetween(Boolean value1, Boolean value2) {
            addCriterion("addArticle between", value1, value2, "addArticle");
            return (Criteria) this;
        }

        public Criteria andAddArticleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("addArticle not between", value1, value2, "addArticle");
            return (Criteria) this;
        }

        public Criteria andDelArticleIsNull() {
            addCriterion("delArticle is null");
            return (Criteria) this;
        }

        public Criteria andDelArticleIsNotNull() {
            addCriterion("delArticle is not null");
            return (Criteria) this;
        }

        public Criteria andDelArticleEqualTo(Boolean value) {
            addCriterion("delArticle =", value, "delArticle");
            return (Criteria) this;
        }

        public Criteria andDelArticleNotEqualTo(Boolean value) {
            addCriterion("delArticle <>", value, "delArticle");
            return (Criteria) this;
        }

        public Criteria andDelArticleGreaterThan(Boolean value) {
            addCriterion("delArticle >", value, "delArticle");
            return (Criteria) this;
        }

        public Criteria andDelArticleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("delArticle >=", value, "delArticle");
            return (Criteria) this;
        }

        public Criteria andDelArticleLessThan(Boolean value) {
            addCriterion("delArticle <", value, "delArticle");
            return (Criteria) this;
        }

        public Criteria andDelArticleLessThanOrEqualTo(Boolean value) {
            addCriterion("delArticle <=", value, "delArticle");
            return (Criteria) this;
        }

        public Criteria andDelArticleIn(List<Boolean> values) {
            addCriterion("delArticle in", values, "delArticle");
            return (Criteria) this;
        }

        public Criteria andDelArticleNotIn(List<Boolean> values) {
            addCriterion("delArticle not in", values, "delArticle");
            return (Criteria) this;
        }

        public Criteria andDelArticleBetween(Boolean value1, Boolean value2) {
            addCriterion("delArticle between", value1, value2, "delArticle");
            return (Criteria) this;
        }

        public Criteria andDelArticleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("delArticle not between", value1, value2, "delArticle");
            return (Criteria) this;
        }

        public Criteria andModArticleIsNull() {
            addCriterion("modArticle is null");
            return (Criteria) this;
        }

        public Criteria andModArticleIsNotNull() {
            addCriterion("modArticle is not null");
            return (Criteria) this;
        }

        public Criteria andModArticleEqualTo(Boolean value) {
            addCriterion("modArticle =", value, "modArticle");
            return (Criteria) this;
        }

        public Criteria andModArticleNotEqualTo(Boolean value) {
            addCriterion("modArticle <>", value, "modArticle");
            return (Criteria) this;
        }

        public Criteria andModArticleGreaterThan(Boolean value) {
            addCriterion("modArticle >", value, "modArticle");
            return (Criteria) this;
        }

        public Criteria andModArticleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("modArticle >=", value, "modArticle");
            return (Criteria) this;
        }

        public Criteria andModArticleLessThan(Boolean value) {
            addCriterion("modArticle <", value, "modArticle");
            return (Criteria) this;
        }

        public Criteria andModArticleLessThanOrEqualTo(Boolean value) {
            addCriterion("modArticle <=", value, "modArticle");
            return (Criteria) this;
        }

        public Criteria andModArticleIn(List<Boolean> values) {
            addCriterion("modArticle in", values, "modArticle");
            return (Criteria) this;
        }

        public Criteria andModArticleNotIn(List<Boolean> values) {
            addCriterion("modArticle not in", values, "modArticle");
            return (Criteria) this;
        }

        public Criteria andModArticleBetween(Boolean value1, Boolean value2) {
            addCriterion("modArticle between", value1, value2, "modArticle");
            return (Criteria) this;
        }

        public Criteria andModArticleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("modArticle not between", value1, value2, "modArticle");
            return (Criteria) this;
        }

        public Criteria andAddSourceIsNull() {
            addCriterion("addSource is null");
            return (Criteria) this;
        }

        public Criteria andAddSourceIsNotNull() {
            addCriterion("addSource is not null");
            return (Criteria) this;
        }

        public Criteria andAddSourceEqualTo(Boolean value) {
            addCriterion("addSource =", value, "addSource");
            return (Criteria) this;
        }

        public Criteria andAddSourceNotEqualTo(Boolean value) {
            addCriterion("addSource <>", value, "addSource");
            return (Criteria) this;
        }

        public Criteria andAddSourceGreaterThan(Boolean value) {
            addCriterion("addSource >", value, "addSource");
            return (Criteria) this;
        }

        public Criteria andAddSourceGreaterThanOrEqualTo(Boolean value) {
            addCriterion("addSource >=", value, "addSource");
            return (Criteria) this;
        }

        public Criteria andAddSourceLessThan(Boolean value) {
            addCriterion("addSource <", value, "addSource");
            return (Criteria) this;
        }

        public Criteria andAddSourceLessThanOrEqualTo(Boolean value) {
            addCriterion("addSource <=", value, "addSource");
            return (Criteria) this;
        }

        public Criteria andAddSourceIn(List<Boolean> values) {
            addCriterion("addSource in", values, "addSource");
            return (Criteria) this;
        }

        public Criteria andAddSourceNotIn(List<Boolean> values) {
            addCriterion("addSource not in", values, "addSource");
            return (Criteria) this;
        }

        public Criteria andAddSourceBetween(Boolean value1, Boolean value2) {
            addCriterion("addSource between", value1, value2, "addSource");
            return (Criteria) this;
        }

        public Criteria andAddSourceNotBetween(Boolean value1, Boolean value2) {
            addCriterion("addSource not between", value1, value2, "addSource");
            return (Criteria) this;
        }

        public Criteria andDelSourceIsNull() {
            addCriterion("delSource is null");
            return (Criteria) this;
        }

        public Criteria andDelSourceIsNotNull() {
            addCriterion("delSource is not null");
            return (Criteria) this;
        }

        public Criteria andDelSourceEqualTo(Boolean value) {
            addCriterion("delSource =", value, "delSource");
            return (Criteria) this;
        }

        public Criteria andDelSourceNotEqualTo(Boolean value) {
            addCriterion("delSource <>", value, "delSource");
            return (Criteria) this;
        }

        public Criteria andDelSourceGreaterThan(Boolean value) {
            addCriterion("delSource >", value, "delSource");
            return (Criteria) this;
        }

        public Criteria andDelSourceGreaterThanOrEqualTo(Boolean value) {
            addCriterion("delSource >=", value, "delSource");
            return (Criteria) this;
        }

        public Criteria andDelSourceLessThan(Boolean value) {
            addCriterion("delSource <", value, "delSource");
            return (Criteria) this;
        }

        public Criteria andDelSourceLessThanOrEqualTo(Boolean value) {
            addCriterion("delSource <=", value, "delSource");
            return (Criteria) this;
        }

        public Criteria andDelSourceIn(List<Boolean> values) {
            addCriterion("delSource in", values, "delSource");
            return (Criteria) this;
        }

        public Criteria andDelSourceNotIn(List<Boolean> values) {
            addCriterion("delSource not in", values, "delSource");
            return (Criteria) this;
        }

        public Criteria andDelSourceBetween(Boolean value1, Boolean value2) {
            addCriterion("delSource between", value1, value2, "delSource");
            return (Criteria) this;
        }

        public Criteria andDelSourceNotBetween(Boolean value1, Boolean value2) {
            addCriterion("delSource not between", value1, value2, "delSource");
            return (Criteria) this;
        }

        public Criteria andModSourceIsNull() {
            addCriterion("modSource is null");
            return (Criteria) this;
        }

        public Criteria andModSourceIsNotNull() {
            addCriterion("modSource is not null");
            return (Criteria) this;
        }

        public Criteria andModSourceEqualTo(Boolean value) {
            addCriterion("modSource =", value, "modSource");
            return (Criteria) this;
        }

        public Criteria andModSourceNotEqualTo(Boolean value) {
            addCriterion("modSource <>", value, "modSource");
            return (Criteria) this;
        }

        public Criteria andModSourceGreaterThan(Boolean value) {
            addCriterion("modSource >", value, "modSource");
            return (Criteria) this;
        }

        public Criteria andModSourceGreaterThanOrEqualTo(Boolean value) {
            addCriterion("modSource >=", value, "modSource");
            return (Criteria) this;
        }

        public Criteria andModSourceLessThan(Boolean value) {
            addCriterion("modSource <", value, "modSource");
            return (Criteria) this;
        }

        public Criteria andModSourceLessThanOrEqualTo(Boolean value) {
            addCriterion("modSource <=", value, "modSource");
            return (Criteria) this;
        }

        public Criteria andModSourceIn(List<Boolean> values) {
            addCriterion("modSource in", values, "modSource");
            return (Criteria) this;
        }

        public Criteria andModSourceNotIn(List<Boolean> values) {
            addCriterion("modSource not in", values, "modSource");
            return (Criteria) this;
        }

        public Criteria andModSourceBetween(Boolean value1, Boolean value2) {
            addCriterion("modSource between", value1, value2, "modSource");
            return (Criteria) this;
        }

        public Criteria andModSourceNotBetween(Boolean value1, Boolean value2) {
            addCriterion("modSource not between", value1, value2, "modSource");
            return (Criteria) this;
        }

        public Criteria andAddDictIsNull() {
            addCriterion("addDict is null");
            return (Criteria) this;
        }

        public Criteria andAddDictIsNotNull() {
            addCriterion("addDict is not null");
            return (Criteria) this;
        }

        public Criteria andAddDictEqualTo(Boolean value) {
            addCriterion("addDict =", value, "addDict");
            return (Criteria) this;
        }

        public Criteria andAddDictNotEqualTo(Boolean value) {
            addCriterion("addDict <>", value, "addDict");
            return (Criteria) this;
        }

        public Criteria andAddDictGreaterThan(Boolean value) {
            addCriterion("addDict >", value, "addDict");
            return (Criteria) this;
        }

        public Criteria andAddDictGreaterThanOrEqualTo(Boolean value) {
            addCriterion("addDict >=", value, "addDict");
            return (Criteria) this;
        }

        public Criteria andAddDictLessThan(Boolean value) {
            addCriterion("addDict <", value, "addDict");
            return (Criteria) this;
        }

        public Criteria andAddDictLessThanOrEqualTo(Boolean value) {
            addCriterion("addDict <=", value, "addDict");
            return (Criteria) this;
        }

        public Criteria andAddDictIn(List<Boolean> values) {
            addCriterion("addDict in", values, "addDict");
            return (Criteria) this;
        }

        public Criteria andAddDictNotIn(List<Boolean> values) {
            addCriterion("addDict not in", values, "addDict");
            return (Criteria) this;
        }

        public Criteria andAddDictBetween(Boolean value1, Boolean value2) {
            addCriterion("addDict between", value1, value2, "addDict");
            return (Criteria) this;
        }

        public Criteria andAddDictNotBetween(Boolean value1, Boolean value2) {
            addCriterion("addDict not between", value1, value2, "addDict");
            return (Criteria) this;
        }

        public Criteria andDelDictIsNull() {
            addCriterion("delDict is null");
            return (Criteria) this;
        }

        public Criteria andDelDictIsNotNull() {
            addCriterion("delDict is not null");
            return (Criteria) this;
        }

        public Criteria andDelDictEqualTo(Boolean value) {
            addCriterion("delDict =", value, "delDict");
            return (Criteria) this;
        }

        public Criteria andDelDictNotEqualTo(Boolean value) {
            addCriterion("delDict <>", value, "delDict");
            return (Criteria) this;
        }

        public Criteria andDelDictGreaterThan(Boolean value) {
            addCriterion("delDict >", value, "delDict");
            return (Criteria) this;
        }

        public Criteria andDelDictGreaterThanOrEqualTo(Boolean value) {
            addCriterion("delDict >=", value, "delDict");
            return (Criteria) this;
        }

        public Criteria andDelDictLessThan(Boolean value) {
            addCriterion("delDict <", value, "delDict");
            return (Criteria) this;
        }

        public Criteria andDelDictLessThanOrEqualTo(Boolean value) {
            addCriterion("delDict <=", value, "delDict");
            return (Criteria) this;
        }

        public Criteria andDelDictIn(List<Boolean> values) {
            addCriterion("delDict in", values, "delDict");
            return (Criteria) this;
        }

        public Criteria andDelDictNotIn(List<Boolean> values) {
            addCriterion("delDict not in", values, "delDict");
            return (Criteria) this;
        }

        public Criteria andDelDictBetween(Boolean value1, Boolean value2) {
            addCriterion("delDict between", value1, value2, "delDict");
            return (Criteria) this;
        }

        public Criteria andDelDictNotBetween(Boolean value1, Boolean value2) {
            addCriterion("delDict not between", value1, value2, "delDict");
            return (Criteria) this;
        }

        public Criteria andModDictIsNull() {
            addCriterion("modDict is null");
            return (Criteria) this;
        }

        public Criteria andModDictIsNotNull() {
            addCriterion("modDict is not null");
            return (Criteria) this;
        }

        public Criteria andModDictEqualTo(Boolean value) {
            addCriterion("modDict =", value, "modDict");
            return (Criteria) this;
        }

        public Criteria andModDictNotEqualTo(Boolean value) {
            addCriterion("modDict <>", value, "modDict");
            return (Criteria) this;
        }

        public Criteria andModDictGreaterThan(Boolean value) {
            addCriterion("modDict >", value, "modDict");
            return (Criteria) this;
        }

        public Criteria andModDictGreaterThanOrEqualTo(Boolean value) {
            addCriterion("modDict >=", value, "modDict");
            return (Criteria) this;
        }

        public Criteria andModDictLessThan(Boolean value) {
            addCriterion("modDict <", value, "modDict");
            return (Criteria) this;
        }

        public Criteria andModDictLessThanOrEqualTo(Boolean value) {
            addCriterion("modDict <=", value, "modDict");
            return (Criteria) this;
        }

        public Criteria andModDictIn(List<Boolean> values) {
            addCriterion("modDict in", values, "modDict");
            return (Criteria) this;
        }

        public Criteria andModDictNotIn(List<Boolean> values) {
            addCriterion("modDict not in", values, "modDict");
            return (Criteria) this;
        }

        public Criteria andModDictBetween(Boolean value1, Boolean value2) {
            addCriterion("modDict between", value1, value2, "modDict");
            return (Criteria) this;
        }

        public Criteria andModDictNotBetween(Boolean value1, Boolean value2) {
            addCriterion("modDict not between", value1, value2, "modDict");
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
            addCriterion("addError is null");
            return (Criteria) this;
        }

        public Criteria andAddErrorIsNotNull() {
            addCriterion("addError is not null");
            return (Criteria) this;
        }

        public Criteria andAddErrorEqualTo(Boolean value) {
            addCriterion("addError =", value, "addError");
            return (Criteria) this;
        }

        public Criteria andAddErrorNotEqualTo(Boolean value) {
            addCriterion("addError <>", value, "addError");
            return (Criteria) this;
        }

        public Criteria andAddErrorGreaterThan(Boolean value) {
            addCriterion("addError >", value, "addError");
            return (Criteria) this;
        }

        public Criteria andAddErrorGreaterThanOrEqualTo(Boolean value) {
            addCriterion("addError >=", value, "addError");
            return (Criteria) this;
        }

        public Criteria andAddErrorLessThan(Boolean value) {
            addCriterion("addError <", value, "addError");
            return (Criteria) this;
        }

        public Criteria andAddErrorLessThanOrEqualTo(Boolean value) {
            addCriterion("addError <=", value, "addError");
            return (Criteria) this;
        }

        public Criteria andAddErrorIn(List<Boolean> values) {
            addCriterion("addError in", values, "addError");
            return (Criteria) this;
        }

        public Criteria andAddErrorNotIn(List<Boolean> values) {
            addCriterion("addError not in", values, "addError");
            return (Criteria) this;
        }

        public Criteria andAddErrorBetween(Boolean value1, Boolean value2) {
            addCriterion("addError between", value1, value2, "addError");
            return (Criteria) this;
        }

        public Criteria andAddErrorNotBetween(Boolean value1, Boolean value2) {
            addCriterion("addError not between", value1, value2, "addError");
            return (Criteria) this;
        }

        public Criteria andDelErrorIsNull() {
            addCriterion("delError is null");
            return (Criteria) this;
        }

        public Criteria andDelErrorIsNotNull() {
            addCriterion("delError is not null");
            return (Criteria) this;
        }

        public Criteria andDelErrorEqualTo(Boolean value) {
            addCriterion("delError =", value, "delError");
            return (Criteria) this;
        }

        public Criteria andDelErrorNotEqualTo(Boolean value) {
            addCriterion("delError <>", value, "delError");
            return (Criteria) this;
        }

        public Criteria andDelErrorGreaterThan(Boolean value) {
            addCriterion("delError >", value, "delError");
            return (Criteria) this;
        }

        public Criteria andDelErrorGreaterThanOrEqualTo(Boolean value) {
            addCriterion("delError >=", value, "delError");
            return (Criteria) this;
        }

        public Criteria andDelErrorLessThan(Boolean value) {
            addCriterion("delError <", value, "delError");
            return (Criteria) this;
        }

        public Criteria andDelErrorLessThanOrEqualTo(Boolean value) {
            addCriterion("delError <=", value, "delError");
            return (Criteria) this;
        }

        public Criteria andDelErrorIn(List<Boolean> values) {
            addCriterion("delError in", values, "delError");
            return (Criteria) this;
        }

        public Criteria andDelErrorNotIn(List<Boolean> values) {
            addCriterion("delError not in", values, "delError");
            return (Criteria) this;
        }

        public Criteria andDelErrorBetween(Boolean value1, Boolean value2) {
            addCriterion("delError between", value1, value2, "delError");
            return (Criteria) this;
        }

        public Criteria andDelErrorNotBetween(Boolean value1, Boolean value2) {
            addCriterion("delError not between", value1, value2, "delError");
            return (Criteria) this;
        }

        public Criteria andModErrorIsNull() {
            addCriterion("modError is null");
            return (Criteria) this;
        }

        public Criteria andModErrorIsNotNull() {
            addCriterion("modError is not null");
            return (Criteria) this;
        }

        public Criteria andModErrorEqualTo(Boolean value) {
            addCriterion("modError =", value, "modError");
            return (Criteria) this;
        }

        public Criteria andModErrorNotEqualTo(Boolean value) {
            addCriterion("modError <>", value, "modError");
            return (Criteria) this;
        }

        public Criteria andModErrorGreaterThan(Boolean value) {
            addCriterion("modError >", value, "modError");
            return (Criteria) this;
        }

        public Criteria andModErrorGreaterThanOrEqualTo(Boolean value) {
            addCriterion("modError >=", value, "modError");
            return (Criteria) this;
        }

        public Criteria andModErrorLessThan(Boolean value) {
            addCriterion("modError <", value, "modError");
            return (Criteria) this;
        }

        public Criteria andModErrorLessThanOrEqualTo(Boolean value) {
            addCriterion("modError <=", value, "modError");
            return (Criteria) this;
        }

        public Criteria andModErrorIn(List<Boolean> values) {
            addCriterion("modError in", values, "modError");
            return (Criteria) this;
        }

        public Criteria andModErrorNotIn(List<Boolean> values) {
            addCriterion("modError not in", values, "modError");
            return (Criteria) this;
        }

        public Criteria andModErrorBetween(Boolean value1, Boolean value2) {
            addCriterion("modError between", value1, value2, "modError");
            return (Criteria) this;
        }

        public Criteria andModErrorNotBetween(Boolean value1, Boolean value2) {
            addCriterion("modError not between", value1, value2, "modError");
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