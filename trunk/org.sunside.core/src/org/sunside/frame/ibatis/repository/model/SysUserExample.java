package org.sunside.frame.ibatis.repository.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysUserExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table sys_user
	 * @ibatorgenerated  Mon Oct 22 00:29:10 CST 2012
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table sys_user
	 * @ibatorgenerated  Mon Oct 22 00:29:10 CST 2012
	 */
	@SuppressWarnings("rawtypes")
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sys_user
	 * @ibatorgenerated  Mon Oct 22 00:29:10 CST 2012
	 */
	public SysUserExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sys_user
	 * @ibatorgenerated  Mon Oct 22 00:29:10 CST 2012
	 */
	protected SysUserExample(SysUserExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sys_user
	 * @ibatorgenerated  Mon Oct 22 00:29:10 CST 2012
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sys_user
	 * @ibatorgenerated  Mon Oct 22 00:29:10 CST 2012
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sys_user
	 * @ibatorgenerated  Mon Oct 22 00:29:10 CST 2012
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sys_user
	 * @ibatorgenerated  Mon Oct 22 00:29:10 CST 2012
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sys_user
	 * @ibatorgenerated  Mon Oct 22 00:29:10 CST 2012
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sys_user
	 * @ibatorgenerated  Mon Oct 22 00:29:10 CST 2012
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sys_user
	 * @ibatorgenerated  Mon Oct 22 00:29:10 CST 2012
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table sys_user
	 * @ibatorgenerated  Mon Oct 22 00:29:10 CST 2012
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

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("value", value);
			criteriaWithSingleValue.add(map);
		}

		protected void addCriterion(String condition, List values,
				String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("values", values);
			criteriaWithListValue.add(map);
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
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

		public Criteria andUserNameIsNull() {
			addCriterion("user_name is null");
			return this;
		}

		public Criteria andUserNameIsNotNull() {
			addCriterion("user_name is not null");
			return this;
		}

		public Criteria andUserNameEqualTo(String value) {
			addCriterion("user_name =", value, "userName");
			return this;
		}

		public Criteria andUserNameNotEqualTo(String value) {
			addCriterion("user_name <>", value, "userName");
			return this;
		}

		public Criteria andUserNameGreaterThan(String value) {
			addCriterion("user_name >", value, "userName");
			return this;
		}

		public Criteria andUserNameGreaterThanOrEqualTo(String value) {
			addCriterion("user_name >=", value, "userName");
			return this;
		}

		public Criteria andUserNameLessThan(String value) {
			addCriterion("user_name <", value, "userName");
			return this;
		}

		public Criteria andUserNameLessThanOrEqualTo(String value) {
			addCriterion("user_name <=", value, "userName");
			return this;
		}

		public Criteria andUserNameLike(String value) {
			addCriterion("user_name like", value, "userName");
			return this;
		}

		public Criteria andUserNameNotLike(String value) {
			addCriterion("user_name not like", value, "userName");
			return this;
		}

		public Criteria andUserNameIn(List values) {
			addCriterion("user_name in", values, "userName");
			return this;
		}

		public Criteria andUserNameNotIn(List values) {
			addCriterion("user_name not in", values, "userName");
			return this;
		}

		public Criteria andUserNameBetween(String value1, String value2) {
			addCriterion("user_name between", value1, value2, "userName");
			return this;
		}

		public Criteria andUserNameNotBetween(String value1, String value2) {
			addCriterion("user_name not between", value1, value2, "userName");
			return this;
		}

		public Criteria andPasswordIsNull() {
			addCriterion("password is null");
			return this;
		}

		public Criteria andPasswordIsNotNull() {
			addCriterion("password is not null");
			return this;
		}

		public Criteria andPasswordEqualTo(String value) {
			addCriterion("password =", value, "password");
			return this;
		}

		public Criteria andPasswordNotEqualTo(String value) {
			addCriterion("password <>", value, "password");
			return this;
		}

		public Criteria andPasswordGreaterThan(String value) {
			addCriterion("password >", value, "password");
			return this;
		}

		public Criteria andPasswordGreaterThanOrEqualTo(String value) {
			addCriterion("password >=", value, "password");
			return this;
		}

		public Criteria andPasswordLessThan(String value) {
			addCriterion("password <", value, "password");
			return this;
		}

		public Criteria andPasswordLessThanOrEqualTo(String value) {
			addCriterion("password <=", value, "password");
			return this;
		}

		public Criteria andPasswordLike(String value) {
			addCriterion("password like", value, "password");
			return this;
		}

		public Criteria andPasswordNotLike(String value) {
			addCriterion("password not like", value, "password");
			return this;
		}

		public Criteria andPasswordIn(List values) {
			addCriterion("password in", values, "password");
			return this;
		}

		public Criteria andPasswordNotIn(List values) {
			addCriterion("password not in", values, "password");
			return this;
		}

		public Criteria andPasswordBetween(String value1, String value2) {
			addCriterion("password between", value1, value2, "password");
			return this;
		}

		public Criteria andPasswordNotBetween(String value1, String value2) {
			addCriterion("password not between", value1, value2, "password");
			return this;
		}

		public Criteria andChineseNameIsNull() {
			addCriterion("chinese_name is null");
			return this;
		}

		public Criteria andChineseNameIsNotNull() {
			addCriterion("chinese_name is not null");
			return this;
		}

		public Criteria andChineseNameEqualTo(String value) {
			addCriterion("chinese_name =", value, "chineseName");
			return this;
		}

		public Criteria andChineseNameNotEqualTo(String value) {
			addCriterion("chinese_name <>", value, "chineseName");
			return this;
		}

		public Criteria andChineseNameGreaterThan(String value) {
			addCriterion("chinese_name >", value, "chineseName");
			return this;
		}

		public Criteria andChineseNameGreaterThanOrEqualTo(String value) {
			addCriterion("chinese_name >=", value, "chineseName");
			return this;
		}

		public Criteria andChineseNameLessThan(String value) {
			addCriterion("chinese_name <", value, "chineseName");
			return this;
		}

		public Criteria andChineseNameLessThanOrEqualTo(String value) {
			addCriterion("chinese_name <=", value, "chineseName");
			return this;
		}

		public Criteria andChineseNameLike(String value) {
			addCriterion("chinese_name like", value, "chineseName");
			return this;
		}

		public Criteria andChineseNameNotLike(String value) {
			addCriterion("chinese_name not like", value, "chineseName");
			return this;
		}

		public Criteria andChineseNameIn(List values) {
			addCriterion("chinese_name in", values, "chineseName");
			return this;
		}

		public Criteria andChineseNameNotIn(List values) {
			addCriterion("chinese_name not in", values, "chineseName");
			return this;
		}

		public Criteria andChineseNameBetween(String value1, String value2) {
			addCriterion("chinese_name between", value1, value2, "chineseName");
			return this;
		}

		public Criteria andChineseNameNotBetween(String value1, String value2) {
			addCriterion("chinese_name not between", value1, value2,
					"chineseName");
			return this;
		}

		public Criteria andDeptIdIsNull() {
			addCriterion("dept_id is null");
			return this;
		}

		public Criteria andDeptIdIsNotNull() {
			addCriterion("dept_id is not null");
			return this;
		}

		public Criteria andDeptIdEqualTo(Long value) {
			addCriterion("dept_id =", value, "deptId");
			return this;
		}

		public Criteria andDeptIdNotEqualTo(Long value) {
			addCriterion("dept_id <>", value, "deptId");
			return this;
		}

		public Criteria andDeptIdGreaterThan(Long value) {
			addCriterion("dept_id >", value, "deptId");
			return this;
		}

		public Criteria andDeptIdGreaterThanOrEqualTo(Long value) {
			addCriterion("dept_id >=", value, "deptId");
			return this;
		}

		public Criteria andDeptIdLessThan(Long value) {
			addCriterion("dept_id <", value, "deptId");
			return this;
		}

		public Criteria andDeptIdLessThanOrEqualTo(Long value) {
			addCriterion("dept_id <=", value, "deptId");
			return this;
		}

		public Criteria andDeptIdIn(List values) {
			addCriterion("dept_id in", values, "deptId");
			return this;
		}

		public Criteria andDeptIdNotIn(List values) {
			addCriterion("dept_id not in", values, "deptId");
			return this;
		}

		public Criteria andDeptIdBetween(Long value1, Long value2) {
			addCriterion("dept_id between", value1, value2, "deptId");
			return this;
		}

		public Criteria andDeptIdNotBetween(Long value1, Long value2) {
			addCriterion("dept_id not between", value1, value2, "deptId");
			return this;
		}

		public Criteria andPostIdIsNull() {
			addCriterion("post_id is null");
			return this;
		}

		public Criteria andPostIdIsNotNull() {
			addCriterion("post_id is not null");
			return this;
		}

		public Criteria andPostIdEqualTo(Long value) {
			addCriterion("post_id =", value, "postId");
			return this;
		}

		public Criteria andPostIdNotEqualTo(Long value) {
			addCriterion("post_id <>", value, "postId");
			return this;
		}

		public Criteria andPostIdGreaterThan(Long value) {
			addCriterion("post_id >", value, "postId");
			return this;
		}

		public Criteria andPostIdGreaterThanOrEqualTo(Long value) {
			addCriterion("post_id >=", value, "postId");
			return this;
		}

		public Criteria andPostIdLessThan(Long value) {
			addCriterion("post_id <", value, "postId");
			return this;
		}

		public Criteria andPostIdLessThanOrEqualTo(Long value) {
			addCriterion("post_id <=", value, "postId");
			return this;
		}

		public Criteria andPostIdIn(List values) {
			addCriterion("post_id in", values, "postId");
			return this;
		}

		public Criteria andPostIdNotIn(List values) {
			addCriterion("post_id not in", values, "postId");
			return this;
		}

		public Criteria andPostIdBetween(Long value1, Long value2) {
			addCriterion("post_id between", value1, value2, "postId");
			return this;
		}

		public Criteria andPostIdNotBetween(Long value1, Long value2) {
			addCriterion("post_id not between", value1, value2, "postId");
			return this;
		}

		public Criteria andSexIsNull() {
			addCriterion("sex is null");
			return this;
		}

		public Criteria andSexIsNotNull() {
			addCriterion("sex is not null");
			return this;
		}

		public Criteria andSexEqualTo(String value) {
			addCriterion("sex =", value, "sex");
			return this;
		}

		public Criteria andSexNotEqualTo(String value) {
			addCriterion("sex <>", value, "sex");
			return this;
		}

		public Criteria andSexGreaterThan(String value) {
			addCriterion("sex >", value, "sex");
			return this;
		}

		public Criteria andSexGreaterThanOrEqualTo(String value) {
			addCriterion("sex >=", value, "sex");
			return this;
		}

		public Criteria andSexLessThan(String value) {
			addCriterion("sex <", value, "sex");
			return this;
		}

		public Criteria andSexLessThanOrEqualTo(String value) {
			addCriterion("sex <=", value, "sex");
			return this;
		}

		public Criteria andSexLike(String value) {
			addCriterion("sex like", value, "sex");
			return this;
		}

		public Criteria andSexNotLike(String value) {
			addCriterion("sex not like", value, "sex");
			return this;
		}

		public Criteria andSexIn(List values) {
			addCriterion("sex in", values, "sex");
			return this;
		}

		public Criteria andSexNotIn(List values) {
			addCriterion("sex not in", values, "sex");
			return this;
		}

		public Criteria andSexBetween(String value1, String value2) {
			addCriterion("sex between", value1, value2, "sex");
			return this;
		}

		public Criteria andSexNotBetween(String value1, String value2) {
			addCriterion("sex not between", value1, value2, "sex");
			return this;
		}

		public Criteria andAgeIsNull() {
			addCriterion("age is null");
			return this;
		}

		public Criteria andAgeIsNotNull() {
			addCriterion("age is not null");
			return this;
		}

		public Criteria andAgeEqualTo(Integer value) {
			addCriterion("age =", value, "age");
			return this;
		}

		public Criteria andAgeNotEqualTo(Integer value) {
			addCriterion("age <>", value, "age");
			return this;
		}

		public Criteria andAgeGreaterThan(Integer value) {
			addCriterion("age >", value, "age");
			return this;
		}

		public Criteria andAgeGreaterThanOrEqualTo(Integer value) {
			addCriterion("age >=", value, "age");
			return this;
		}

		public Criteria andAgeLessThan(Integer value) {
			addCriterion("age <", value, "age");
			return this;
		}

		public Criteria andAgeLessThanOrEqualTo(Integer value) {
			addCriterion("age <=", value, "age");
			return this;
		}

		public Criteria andAgeIn(List values) {
			addCriterion("age in", values, "age");
			return this;
		}

		public Criteria andAgeNotIn(List values) {
			addCriterion("age not in", values, "age");
			return this;
		}

		public Criteria andAgeBetween(Integer value1, Integer value2) {
			addCriterion("age between", value1, value2, "age");
			return this;
		}

		public Criteria andAgeNotBetween(Integer value1, Integer value2) {
			addCriterion("age not between", value1, value2, "age");
			return this;
		}

		public Criteria andZipCodeIsNull() {
			addCriterion("zip_code is null");
			return this;
		}

		public Criteria andZipCodeIsNotNull() {
			addCriterion("zip_code is not null");
			return this;
		}

		public Criteria andZipCodeEqualTo(String value) {
			addCriterion("zip_code =", value, "zipCode");
			return this;
		}

		public Criteria andZipCodeNotEqualTo(String value) {
			addCriterion("zip_code <>", value, "zipCode");
			return this;
		}

		public Criteria andZipCodeGreaterThan(String value) {
			addCriterion("zip_code >", value, "zipCode");
			return this;
		}

		public Criteria andZipCodeGreaterThanOrEqualTo(String value) {
			addCriterion("zip_code >=", value, "zipCode");
			return this;
		}

		public Criteria andZipCodeLessThan(String value) {
			addCriterion("zip_code <", value, "zipCode");
			return this;
		}

		public Criteria andZipCodeLessThanOrEqualTo(String value) {
			addCriterion("zip_code <=", value, "zipCode");
			return this;
		}

		public Criteria andZipCodeLike(String value) {
			addCriterion("zip_code like", value, "zipCode");
			return this;
		}

		public Criteria andZipCodeNotLike(String value) {
			addCriterion("zip_code not like", value, "zipCode");
			return this;
		}

		public Criteria andZipCodeIn(List values) {
			addCriterion("zip_code in", values, "zipCode");
			return this;
		}

		public Criteria andZipCodeNotIn(List values) {
			addCriterion("zip_code not in", values, "zipCode");
			return this;
		}

		public Criteria andZipCodeBetween(String value1, String value2) {
			addCriterion("zip_code between", value1, value2, "zipCode");
			return this;
		}

		public Criteria andZipCodeNotBetween(String value1, String value2) {
			addCriterion("zip_code not between", value1, value2, "zipCode");
			return this;
		}

		public Criteria andEmailIsNull() {
			addCriterion("email is null");
			return this;
		}

		public Criteria andEmailIsNotNull() {
			addCriterion("email is not null");
			return this;
		}

		public Criteria andEmailEqualTo(String value) {
			addCriterion("email =", value, "email");
			return this;
		}

		public Criteria andEmailNotEqualTo(String value) {
			addCriterion("email <>", value, "email");
			return this;
		}

		public Criteria andEmailGreaterThan(String value) {
			addCriterion("email >", value, "email");
			return this;
		}

		public Criteria andEmailGreaterThanOrEqualTo(String value) {
			addCriterion("email >=", value, "email");
			return this;
		}

		public Criteria andEmailLessThan(String value) {
			addCriterion("email <", value, "email");
			return this;
		}

		public Criteria andEmailLessThanOrEqualTo(String value) {
			addCriterion("email <=", value, "email");
			return this;
		}

		public Criteria andEmailLike(String value) {
			addCriterion("email like", value, "email");
			return this;
		}

		public Criteria andEmailNotLike(String value) {
			addCriterion("email not like", value, "email");
			return this;
		}

		public Criteria andEmailIn(List values) {
			addCriterion("email in", values, "email");
			return this;
		}

		public Criteria andEmailNotIn(List values) {
			addCriterion("email not in", values, "email");
			return this;
		}

		public Criteria andEmailBetween(String value1, String value2) {
			addCriterion("email between", value1, value2, "email");
			return this;
		}

		public Criteria andEmailNotBetween(String value1, String value2) {
			addCriterion("email not between", value1, value2, "email");
			return this;
		}

		public Criteria andPhoneNoIsNull() {
			addCriterion("phone_no is null");
			return this;
		}

		public Criteria andPhoneNoIsNotNull() {
			addCriterion("phone_no is not null");
			return this;
		}

		public Criteria andPhoneNoEqualTo(String value) {
			addCriterion("phone_no =", value, "phoneNo");
			return this;
		}

		public Criteria andPhoneNoNotEqualTo(String value) {
			addCriterion("phone_no <>", value, "phoneNo");
			return this;
		}

		public Criteria andPhoneNoGreaterThan(String value) {
			addCriterion("phone_no >", value, "phoneNo");
			return this;
		}

		public Criteria andPhoneNoGreaterThanOrEqualTo(String value) {
			addCriterion("phone_no >=", value, "phoneNo");
			return this;
		}

		public Criteria andPhoneNoLessThan(String value) {
			addCriterion("phone_no <", value, "phoneNo");
			return this;
		}

		public Criteria andPhoneNoLessThanOrEqualTo(String value) {
			addCriterion("phone_no <=", value, "phoneNo");
			return this;
		}

		public Criteria andPhoneNoLike(String value) {
			addCriterion("phone_no like", value, "phoneNo");
			return this;
		}

		public Criteria andPhoneNoNotLike(String value) {
			addCriterion("phone_no not like", value, "phoneNo");
			return this;
		}

		public Criteria andPhoneNoIn(List values) {
			addCriterion("phone_no in", values, "phoneNo");
			return this;
		}

		public Criteria andPhoneNoNotIn(List values) {
			addCriterion("phone_no not in", values, "phoneNo");
			return this;
		}

		public Criteria andPhoneNoBetween(String value1, String value2) {
			addCriterion("phone_no between", value1, value2, "phoneNo");
			return this;
		}

		public Criteria andPhoneNoNotBetween(String value1, String value2) {
			addCriterion("phone_no not between", value1, value2, "phoneNo");
			return this;
		}

		public Criteria andAddressIsNull() {
			addCriterion("address is null");
			return this;
		}

		public Criteria andAddressIsNotNull() {
			addCriterion("address is not null");
			return this;
		}

		public Criteria andAddressEqualTo(String value) {
			addCriterion("address =", value, "address");
			return this;
		}

		public Criteria andAddressNotEqualTo(String value) {
			addCriterion("address <>", value, "address");
			return this;
		}

		public Criteria andAddressGreaterThan(String value) {
			addCriterion("address >", value, "address");
			return this;
		}

		public Criteria andAddressGreaterThanOrEqualTo(String value) {
			addCriterion("address >=", value, "address");
			return this;
		}

		public Criteria andAddressLessThan(String value) {
			addCriterion("address <", value, "address");
			return this;
		}

		public Criteria andAddressLessThanOrEqualTo(String value) {
			addCriterion("address <=", value, "address");
			return this;
		}

		public Criteria andAddressLike(String value) {
			addCriterion("address like", value, "address");
			return this;
		}

		public Criteria andAddressNotLike(String value) {
			addCriterion("address not like", value, "address");
			return this;
		}

		public Criteria andAddressIn(List values) {
			addCriterion("address in", values, "address");
			return this;
		}

		public Criteria andAddressNotIn(List values) {
			addCriterion("address not in", values, "address");
			return this;
		}

		public Criteria andAddressBetween(String value1, String value2) {
			addCriterion("address between", value1, value2, "address");
			return this;
		}

		public Criteria andAddressNotBetween(String value1, String value2) {
			addCriterion("address not between", value1, value2, "address");
			return this;
		}

		public Criteria andNoteIsNull() {
			addCriterion("note is null");
			return this;
		}

		public Criteria andNoteIsNotNull() {
			addCriterion("note is not null");
			return this;
		}

		public Criteria andNoteEqualTo(String value) {
			addCriterion("note =", value, "note");
			return this;
		}

		public Criteria andNoteNotEqualTo(String value) {
			addCriterion("note <>", value, "note");
			return this;
		}

		public Criteria andNoteGreaterThan(String value) {
			addCriterion("note >", value, "note");
			return this;
		}

		public Criteria andNoteGreaterThanOrEqualTo(String value) {
			addCriterion("note >=", value, "note");
			return this;
		}

		public Criteria andNoteLessThan(String value) {
			addCriterion("note <", value, "note");
			return this;
		}

		public Criteria andNoteLessThanOrEqualTo(String value) {
			addCriterion("note <=", value, "note");
			return this;
		}

		public Criteria andNoteLike(String value) {
			addCriterion("note like", value, "note");
			return this;
		}

		public Criteria andNoteNotLike(String value) {
			addCriterion("note not like", value, "note");
			return this;
		}

		public Criteria andNoteIn(List values) {
			addCriterion("note in", values, "note");
			return this;
		}

		public Criteria andNoteNotIn(List values) {
			addCriterion("note not in", values, "note");
			return this;
		}

		public Criteria andNoteBetween(String value1, String value2) {
			addCriterion("note between", value1, value2, "note");
			return this;
		}

		public Criteria andNoteNotBetween(String value1, String value2) {
			addCriterion("note not between", value1, value2, "note");
			return this;
		}

		public Criteria andVersionIdIsNull() {
			addCriterion("version_id is null");
			return this;
		}

		public Criteria andVersionIdIsNotNull() {
			addCriterion("version_id is not null");
			return this;
		}

		public Criteria andVersionIdEqualTo(Long value) {
			addCriterion("version_id =", value, "versionId");
			return this;
		}

		public Criteria andVersionIdNotEqualTo(Long value) {
			addCriterion("version_id <>", value, "versionId");
			return this;
		}

		public Criteria andVersionIdGreaterThan(Long value) {
			addCriterion("version_id >", value, "versionId");
			return this;
		}

		public Criteria andVersionIdGreaterThanOrEqualTo(Long value) {
			addCriterion("version_id >=", value, "versionId");
			return this;
		}

		public Criteria andVersionIdLessThan(Long value) {
			addCriterion("version_id <", value, "versionId");
			return this;
		}

		public Criteria andVersionIdLessThanOrEqualTo(Long value) {
			addCriterion("version_id <=", value, "versionId");
			return this;
		}

		public Criteria andVersionIdIn(List values) {
			addCriterion("version_id in", values, "versionId");
			return this;
		}

		public Criteria andVersionIdNotIn(List values) {
			addCriterion("version_id not in", values, "versionId");
			return this;
		}

		public Criteria andVersionIdBetween(Long value1, Long value2) {
			addCriterion("version_id between", value1, value2, "versionId");
			return this;
		}

		public Criteria andVersionIdNotBetween(Long value1, Long value2) {
			addCriterion("version_id not between", value1, value2, "versionId");
			return this;
		}

		public Criteria andReccreatorIsNull() {
			addCriterion("reccreator is null");
			return this;
		}

		public Criteria andReccreatorIsNotNull() {
			addCriterion("reccreator is not null");
			return this;
		}

		public Criteria andReccreatorEqualTo(String value) {
			addCriterion("reccreator =", value, "reccreator");
			return this;
		}

		public Criteria andReccreatorNotEqualTo(String value) {
			addCriterion("reccreator <>", value, "reccreator");
			return this;
		}

		public Criteria andReccreatorGreaterThan(String value) {
			addCriterion("reccreator >", value, "reccreator");
			return this;
		}

		public Criteria andReccreatorGreaterThanOrEqualTo(String value) {
			addCriterion("reccreator >=", value, "reccreator");
			return this;
		}

		public Criteria andReccreatorLessThan(String value) {
			addCriterion("reccreator <", value, "reccreator");
			return this;
		}

		public Criteria andReccreatorLessThanOrEqualTo(String value) {
			addCriterion("reccreator <=", value, "reccreator");
			return this;
		}

		public Criteria andReccreatorLike(String value) {
			addCriterion("reccreator like", value, "reccreator");
			return this;
		}

		public Criteria andReccreatorNotLike(String value) {
			addCriterion("reccreator not like", value, "reccreator");
			return this;
		}

		public Criteria andReccreatorIn(List values) {
			addCriterion("reccreator in", values, "reccreator");
			return this;
		}

		public Criteria andReccreatorNotIn(List values) {
			addCriterion("reccreator not in", values, "reccreator");
			return this;
		}

		public Criteria andReccreatorBetween(String value1, String value2) {
			addCriterion("reccreator between", value1, value2, "reccreator");
			return this;
		}

		public Criteria andReccreatorNotBetween(String value1, String value2) {
			addCriterion("reccreator not between", value1, value2, "reccreator");
			return this;
		}

		public Criteria andReccreateTimeIsNull() {
			addCriterion("reccreate_time is null");
			return this;
		}

		public Criteria andReccreateTimeIsNotNull() {
			addCriterion("reccreate_time is not null");
			return this;
		}

		public Criteria andReccreateTimeEqualTo(Date value) {
			addCriterion("reccreate_time =", value, "reccreateTime");
			return this;
		}

		public Criteria andReccreateTimeNotEqualTo(Date value) {
			addCriterion("reccreate_time <>", value, "reccreateTime");
			return this;
		}

		public Criteria andReccreateTimeGreaterThan(Date value) {
			addCriterion("reccreate_time >", value, "reccreateTime");
			return this;
		}

		public Criteria andReccreateTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("reccreate_time >=", value, "reccreateTime");
			return this;
		}

		public Criteria andReccreateTimeLessThan(Date value) {
			addCriterion("reccreate_time <", value, "reccreateTime");
			return this;
		}

		public Criteria andReccreateTimeLessThanOrEqualTo(Date value) {
			addCriterion("reccreate_time <=", value, "reccreateTime");
			return this;
		}

		public Criteria andReccreateTimeIn(List values) {
			addCriterion("reccreate_time in", values, "reccreateTime");
			return this;
		}

		public Criteria andReccreateTimeNotIn(List values) {
			addCriterion("reccreate_time not in", values, "reccreateTime");
			return this;
		}

		public Criteria andReccreateTimeBetween(Date value1, Date value2) {
			addCriterion("reccreate_time between", value1, value2,
					"reccreateTime");
			return this;
		}

		public Criteria andReccreateTimeNotBetween(Date value1, Date value2) {
			addCriterion("reccreate_time not between", value1, value2,
					"reccreateTime");
			return this;
		}

		public Criteria andRecrevisorIsNull() {
			addCriterion("recrevisor is null");
			return this;
		}

		public Criteria andRecrevisorIsNotNull() {
			addCriterion("recrevisor is not null");
			return this;
		}

		public Criteria andRecrevisorEqualTo(String value) {
			addCriterion("recrevisor =", value, "recrevisor");
			return this;
		}

		public Criteria andRecrevisorNotEqualTo(String value) {
			addCriterion("recrevisor <>", value, "recrevisor");
			return this;
		}

		public Criteria andRecrevisorGreaterThan(String value) {
			addCriterion("recrevisor >", value, "recrevisor");
			return this;
		}

		public Criteria andRecrevisorGreaterThanOrEqualTo(String value) {
			addCriterion("recrevisor >=", value, "recrevisor");
			return this;
		}

		public Criteria andRecrevisorLessThan(String value) {
			addCriterion("recrevisor <", value, "recrevisor");
			return this;
		}

		public Criteria andRecrevisorLessThanOrEqualTo(String value) {
			addCriterion("recrevisor <=", value, "recrevisor");
			return this;
		}

		public Criteria andRecrevisorLike(String value) {
			addCriterion("recrevisor like", value, "recrevisor");
			return this;
		}

		public Criteria andRecrevisorNotLike(String value) {
			addCriterion("recrevisor not like", value, "recrevisor");
			return this;
		}

		public Criteria andRecrevisorIn(List values) {
			addCriterion("recrevisor in", values, "recrevisor");
			return this;
		}

		public Criteria andRecrevisorNotIn(List values) {
			addCriterion("recrevisor not in", values, "recrevisor");
			return this;
		}

		public Criteria andRecrevisorBetween(String value1, String value2) {
			addCriterion("recrevisor between", value1, value2, "recrevisor");
			return this;
		}

		public Criteria andRecrevisorNotBetween(String value1, String value2) {
			addCriterion("recrevisor not between", value1, value2, "recrevisor");
			return this;
		}

		public Criteria andRecreviseTimeIsNull() {
			addCriterion("recrevise_time is null");
			return this;
		}

		public Criteria andRecreviseTimeIsNotNull() {
			addCriterion("recrevise_time is not null");
			return this;
		}

		public Criteria andRecreviseTimeEqualTo(Date value) {
			addCriterion("recrevise_time =", value, "recreviseTime");
			return this;
		}

		public Criteria andRecreviseTimeNotEqualTo(Date value) {
			addCriterion("recrevise_time <>", value, "recreviseTime");
			return this;
		}

		public Criteria andRecreviseTimeGreaterThan(Date value) {
			addCriterion("recrevise_time >", value, "recreviseTime");
			return this;
		}

		public Criteria andRecreviseTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("recrevise_time >=", value, "recreviseTime");
			return this;
		}

		public Criteria andRecreviseTimeLessThan(Date value) {
			addCriterion("recrevise_time <", value, "recreviseTime");
			return this;
		}

		public Criteria andRecreviseTimeLessThanOrEqualTo(Date value) {
			addCriterion("recrevise_time <=", value, "recreviseTime");
			return this;
		}

		public Criteria andRecreviseTimeIn(List values) {
			addCriterion("recrevise_time in", values, "recreviseTime");
			return this;
		}

		public Criteria andRecreviseTimeNotIn(List values) {
			addCriterion("recrevise_time not in", values, "recreviseTime");
			return this;
		}

		public Criteria andRecreviseTimeBetween(Date value1, Date value2) {
			addCriterion("recrevise_time between", value1, value2,
					"recreviseTime");
			return this;
		}

		public Criteria andRecreviseTimeNotBetween(Date value1, Date value2) {
			addCriterion("recrevise_time not between", value1, value2,
					"recreviseTime");
			return this;
		}

		public Criteria andArchiveFlagIsNull() {
			addCriterion("archive_flag is null");
			return this;
		}

		public Criteria andArchiveFlagIsNotNull() {
			addCriterion("archive_flag is not null");
			return this;
		}

		public Criteria andArchiveFlagEqualTo(String value) {
			addCriterion("archive_flag =", value, "archiveFlag");
			return this;
		}

		public Criteria andArchiveFlagNotEqualTo(String value) {
			addCriterion("archive_flag <>", value, "archiveFlag");
			return this;
		}

		public Criteria andArchiveFlagGreaterThan(String value) {
			addCriterion("archive_flag >", value, "archiveFlag");
			return this;
		}

		public Criteria andArchiveFlagGreaterThanOrEqualTo(String value) {
			addCriterion("archive_flag >=", value, "archiveFlag");
			return this;
		}

		public Criteria andArchiveFlagLessThan(String value) {
			addCriterion("archive_flag <", value, "archiveFlag");
			return this;
		}

		public Criteria andArchiveFlagLessThanOrEqualTo(String value) {
			addCriterion("archive_flag <=", value, "archiveFlag");
			return this;
		}

		public Criteria andArchiveFlagLike(String value) {
			addCriterion("archive_flag like", value, "archiveFlag");
			return this;
		}

		public Criteria andArchiveFlagNotLike(String value) {
			addCriterion("archive_flag not like", value, "archiveFlag");
			return this;
		}

		public Criteria andArchiveFlagIn(List values) {
			addCriterion("archive_flag in", values, "archiveFlag");
			return this;
		}

		public Criteria andArchiveFlagNotIn(List values) {
			addCriterion("archive_flag not in", values, "archiveFlag");
			return this;
		}

		public Criteria andArchiveFlagBetween(String value1, String value2) {
			addCriterion("archive_flag between", value1, value2, "archiveFlag");
			return this;
		}

		public Criteria andArchiveFlagNotBetween(String value1, String value2) {
			addCriterion("archive_flag not between", value1, value2,
					"archiveFlag");
			return this;
		}
	}
}