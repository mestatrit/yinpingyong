package org.sunside.rycs.repository.model.system;

public class SysDept {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column sys_dept.id
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column sys_dept.name
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    private String name;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column sys_dept.parent_id
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    private Long parentId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column sys_dept.remark
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    private String remark;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column sys_dept.id
     *
     * @return the value of sys_dept.id
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column sys_dept.id
     *
     * @param id the value for sys_dept.id
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column sys_dept.name
     *
     * @return the value of sys_dept.name
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column sys_dept.name
     *
     * @param name the value for sys_dept.name
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column sys_dept.parent_id
     *
     * @return the value of sys_dept.parent_id
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column sys_dept.parent_id
     *
     * @param parentId the value for sys_dept.parent_id
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column sys_dept.remark
     *
     * @return the value of sys_dept.remark
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column sys_dept.remark
     *
     * @param remark the value for sys_dept.remark
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}