package org.sunside.rycs.repository.model.system;

public class SysPost {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column sys_post.id
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column sys_post.name
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    private String name;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column sys_post.parent_id
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    private Long parentId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column sys_post.remark
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    private String remark;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column sys_post.id
     *
     * @return the value of sys_post.id
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column sys_post.id
     *
     * @param id the value for sys_post.id
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column sys_post.name
     *
     * @return the value of sys_post.name
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column sys_post.name
     *
     * @param name the value for sys_post.name
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column sys_post.parent_id
     *
     * @return the value of sys_post.parent_id
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column sys_post.parent_id
     *
     * @param parentId the value for sys_post.parent_id
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column sys_post.remark
     *
     * @return the value of sys_post.remark
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column sys_post.remark
     *
     * @param remark the value for sys_post.remark
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}