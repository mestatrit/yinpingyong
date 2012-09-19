package org.sunside.rycs.repository.dao.system;

import java.util.List;
import org.sunside.rycs.repository.model.system.SysDataRight;
import org.sunside.rycs.repository.model.system.SysDataRightExample;

public interface SysDataRightDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_data_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int countByExample(SysDataRightExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_data_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int deleteByExample(SysDataRightExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_data_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_data_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    void insert(SysDataRight record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_data_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    void insertSelective(SysDataRight record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_data_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    List selectByExample(SysDataRightExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_data_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    SysDataRight selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_data_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int updateByExampleSelective(SysDataRight record, SysDataRightExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_data_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int updateByExample(SysDataRight record, SysDataRightExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_data_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int updateByPrimaryKeySelective(SysDataRight record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_data_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    int updateByPrimaryKey(SysDataRight record);
}