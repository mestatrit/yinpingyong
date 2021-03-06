package org.sunside.rycs.repository.dao.system;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.sunside.rycs.repository.model.system.SysUser;
import org.sunside.rycs.repository.model.system.SysUserExample;

public class SysUserDAOImpl extends SqlMapClientDaoSupport implements SysUserDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public SysUserDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int countByExample(SysUserExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("sys_user.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int deleteByExample(SysUserExample example) {
        int rows = getSqlMapClientTemplate().delete("sys_user.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int deleteByPrimaryKey(Integer id) {
        SysUser key = new SysUser();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("sys_user.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void insert(SysUser record) {
        getSqlMapClientTemplate().insert("sys_user.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void insertSelective(SysUser record) {
        getSqlMapClientTemplate().insert("sys_user.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public List selectByExample(SysUserExample example) {
        List list = getSqlMapClientTemplate().queryForList("sys_user.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public SysUser selectByPrimaryKey(Integer id) {
        SysUser key = new SysUser();
        key.setId(id);
        SysUser record = (SysUser) getSqlMapClientTemplate().queryForObject("sys_user.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByExampleSelective(SysUser record, SysUserExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("sys_user.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByExample(SysUser record, SysUserExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("sys_user.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByPrimaryKeySelective(SysUser record) {
        int rows = getSqlMapClientTemplate().update("sys_user.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_user
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByPrimaryKey(SysUser record) {
        int rows = getSqlMapClientTemplate().update("sys_user.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table sys_user
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    private static class UpdateByExampleParms extends SysUserExample {
        private Object record;

        public UpdateByExampleParms(Object record, SysUserExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}