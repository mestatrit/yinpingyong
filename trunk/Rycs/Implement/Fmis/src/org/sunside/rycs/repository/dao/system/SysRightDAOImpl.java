package org.sunside.rycs.repository.dao.system;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.sunside.rycs.repository.model.system.SysRight;
import org.sunside.rycs.repository.model.system.SysRightExample;

public class SysRightDAOImpl extends SqlMapClientDaoSupport implements SysRightDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public SysRightDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int countByExample(SysRightExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("sys_right.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int deleteByExample(SysRightExample example) {
        int rows = getSqlMapClientTemplate().delete("sys_right.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int deleteByPrimaryKey(Integer id) {
        SysRight key = new SysRight();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("sys_right.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void insert(SysRight record) {
        getSqlMapClientTemplate().insert("sys_right.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void insertSelective(SysRight record) {
        getSqlMapClientTemplate().insert("sys_right.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public List selectByExample(SysRightExample example) {
        List list = getSqlMapClientTemplate().queryForList("sys_right.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public SysRight selectByPrimaryKey(Integer id) {
        SysRight key = new SysRight();
        key.setId(id);
        SysRight record = (SysRight) getSqlMapClientTemplate().queryForObject("sys_right.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByExampleSelective(SysRight record, SysRightExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("sys_right.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByExample(SysRight record, SysRightExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("sys_right.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByPrimaryKeySelective(SysRight record) {
        int rows = getSqlMapClientTemplate().update("sys_right.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByPrimaryKey(SysRight record) {
        int rows = getSqlMapClientTemplate().update("sys_right.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table sys_right
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    private static class UpdateByExampleParms extends SysRightExample {
        private Object record;

        public UpdateByExampleParms(Object record, SysRightExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}