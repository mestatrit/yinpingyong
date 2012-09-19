package org.sunside.rycs.repository.dao.system;

import java.util.List;
import org.sunside.rycs.repository.model.system.SysRight;
import org.sunside.rycs.repository.model.system.SysRightExample;

public interface SysRightDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int countByExample(SysRightExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int deleteByExample(SysRightExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    void insert(SysRight record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    void insertSelective(SysRight record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    List selectByExample(SysRightExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    SysRight selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int updateByExampleSelective(SysRight record, SysRightExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int updateByExample(SysRight record, SysRightExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int updateByPrimaryKeySelective(SysRight record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int updateByPrimaryKey(SysRight record);
}