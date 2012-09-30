package org.sunside.rycs.repository.dao.system;

import java.util.List;
import org.sunside.rycs.repository.model.system.SysUserRole;
import org.sunside.rycs.repository.model.system.SysUserRoleExample;

public interface SysUserRoleDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user_role
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int countByExample(SysUserRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user_role
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int deleteByExample(SysUserRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user_role
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user_role
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    void insert(SysUserRole record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user_role
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    void insertSelective(SysUserRole record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user_role
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    List selectByExample(SysUserRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user_role
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    SysUserRole selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user_role
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int updateByExampleSelective(SysUserRole record, SysUserRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user_role
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int updateByExample(SysUserRole record, SysUserRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user_role
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int updateByPrimaryKeySelective(SysUserRole record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user_role
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int updateByPrimaryKey(SysUserRole record);
}