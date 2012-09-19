package org.sunside.rycs.repository.dao.system;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.sunside.rycs.repository.model.system.SysDictionary;
import org.sunside.rycs.repository.model.system.SysDictionaryExample;

public class SysDictionaryDAOImpl extends SqlMapClientDaoSupport implements SysDictionaryDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_dictionary
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public SysDictionaryDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_dictionary
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int countByExample(SysDictionaryExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("sys_dictionary.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_dictionary
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int deleteByExample(SysDictionaryExample example) {
        int rows = getSqlMapClientTemplate().delete("sys_dictionary.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_dictionary
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int deleteByPrimaryKey(Integer id) {
        SysDictionary key = new SysDictionary();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("sys_dictionary.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_dictionary
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void insert(SysDictionary record) {
        getSqlMapClientTemplate().insert("sys_dictionary.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_dictionary
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void insertSelective(SysDictionary record) {
        getSqlMapClientTemplate().insert("sys_dictionary.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_dictionary
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public List selectByExample(SysDictionaryExample example) {
        List list = getSqlMapClientTemplate().queryForList("sys_dictionary.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_dictionary
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public SysDictionary selectByPrimaryKey(Integer id) {
        SysDictionary key = new SysDictionary();
        key.setId(id);
        SysDictionary record = (SysDictionary) getSqlMapClientTemplate().queryForObject("sys_dictionary.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_dictionary
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByExampleSelective(SysDictionary record, SysDictionaryExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("sys_dictionary.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_dictionary
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByExample(SysDictionary record, SysDictionaryExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("sys_dictionary.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_dictionary
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByPrimaryKeySelective(SysDictionary record) {
        int rows = getSqlMapClientTemplate().update("sys_dictionary.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sys_dictionary
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByPrimaryKey(SysDictionary record) {
        int rows = getSqlMapClientTemplate().update("sys_dictionary.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table sys_dictionary
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    private static class UpdateByExampleParms extends SysDictionaryExample {
        private Object record;

        public UpdateByExampleParms(Object record, SysDictionaryExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}