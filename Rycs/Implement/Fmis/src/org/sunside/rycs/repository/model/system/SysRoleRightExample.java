package org.sunside.rycs.repository.model.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysRoleRightExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table sys_role_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table sys_role_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_role_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public SysRoleRightExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_role_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    protected SysRoleRightExample(SysRoleRightExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_role_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_role_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_role_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_role_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_role_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_role_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_role_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table sys_role_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public static class Criteria {
        protected List criteriaWithoutValue;

        protected List criteriaWithSingleValue;

        protected List criteriaWithListValue;

        protected List criteriaWithBetweenValue;

        protected Criteria() {
            super();
            criteriaWithoutValue = new ArrayList();
            criteriaWithSingleValue = new ArrayList();
            criteriaWithListValue = new ArrayList();
            criteriaWithBetweenValue = new ArrayList();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
        }

        public List getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List list = new ArrayList();
            list.add(value1);
            list.add(value2);
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return this;
        }

        public Criteria andIdIn(List values) {
            addCriterion("id in", values, "id");
            return this;
        }

        public Criteria andIdNotIn(List values) {
            addCriterion("id not in", values, "id");
            return this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return this;
        }

        public Criteria andRoleIdIsNull() {
            addCriterion("role_id is null");
            return this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("role_id is not null");
            return this;
        }

        public Criteria andRoleIdEqualTo(Long value) {
            addCriterion("role_id =", value, "roleId");
            return this;
        }

        public Criteria andRoleIdNotEqualTo(Long value) {
            addCriterion("role_id <>", value, "roleId");
            return this;
        }

        public Criteria andRoleIdGreaterThan(Long value) {
            addCriterion("role_id >", value, "roleId");
            return this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("role_id >=", value, "roleId");
            return this;
        }

        public Criteria andRoleIdLessThan(Long value) {
            addCriterion("role_id <", value, "roleId");
            return this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(Long value) {
            addCriterion("role_id <=", value, "roleId");
            return this;
        }

        public Criteria andRoleIdIn(List values) {
            addCriterion("role_id in", values, "roleId");
            return this;
        }

        public Criteria andRoleIdNotIn(List values) {
            addCriterion("role_id not in", values, "roleId");
            return this;
        }

        public Criteria andRoleIdBetween(Long value1, Long value2) {
            addCriterion("role_id between", value1, value2, "roleId");
            return this;
        }

        public Criteria andRoleIdNotBetween(Long value1, Long value2) {
            addCriterion("role_id not between", value1, value2, "roleId");
            return this;
        }

        public Criteria andRightIdIsNull() {
            addCriterion("right_id is null");
            return this;
        }

        public Criteria andRightIdIsNotNull() {
            addCriterion("right_id is not null");
            return this;
        }

        public Criteria andRightIdEqualTo(Long value) {
            addCriterion("right_id =", value, "rightId");
            return this;
        }

        public Criteria andRightIdNotEqualTo(Long value) {
            addCriterion("right_id <>", value, "rightId");
            return this;
        }

        public Criteria andRightIdGreaterThan(Long value) {
            addCriterion("right_id >", value, "rightId");
            return this;
        }

        public Criteria andRightIdGreaterThanOrEqualTo(Long value) {
            addCriterion("right_id >=", value, "rightId");
            return this;
        }

        public Criteria andRightIdLessThan(Long value) {
            addCriterion("right_id <", value, "rightId");
            return this;
        }

        public Criteria andRightIdLessThanOrEqualTo(Long value) {
            addCriterion("right_id <=", value, "rightId");
            return this;
        }

        public Criteria andRightIdIn(List values) {
            addCriterion("right_id in", values, "rightId");
            return this;
        }

        public Criteria andRightIdNotIn(List values) {
            addCriterion("right_id not in", values, "rightId");
            return this;
        }

        public Criteria andRightIdBetween(Long value1, Long value2) {
            addCriterion("right_id between", value1, value2, "rightId");
            return this;
        }

        public Criteria andRightIdNotBetween(Long value1, Long value2) {
            addCriterion("right_id not between", value1, value2, "rightId");
            return this;
        }
    }
}