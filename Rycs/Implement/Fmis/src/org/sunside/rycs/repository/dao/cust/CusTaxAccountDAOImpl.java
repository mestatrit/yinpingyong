package org.sunside.rycs.repository.dao.cust;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.sunside.rycs.repository.model.cust.CusTaxAccount;
import org.sunside.rycs.repository.model.cust.CusTaxAccountExample;

public class CusTaxAccountDAOImpl extends SqlMapClientDaoSupport implements CusTaxAccountDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_tax_account
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public CusTaxAccountDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_tax_account
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int countByExample(CusTaxAccountExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("cus_tax_account.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_tax_account
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int deleteByExample(CusTaxAccountExample example) {
        int rows = getSqlMapClientTemplate().delete("cus_tax_account.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_tax_account
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int deleteByPrimaryKey(Integer id) {
        CusTaxAccount key = new CusTaxAccount();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("cus_tax_account.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_tax_account
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void insert(CusTaxAccount record) {
        getSqlMapClientTemplate().insert("cus_tax_account.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_tax_account
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void insertSelective(CusTaxAccount record) {
        getSqlMapClientTemplate().insert("cus_tax_account.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_tax_account
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public List selectByExample(CusTaxAccountExample example) {
        List list = getSqlMapClientTemplate().queryForList("cus_tax_account.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_tax_account
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public CusTaxAccount selectByPrimaryKey(Integer id) {
        CusTaxAccount key = new CusTaxAccount();
        key.setId(id);
        CusTaxAccount record = (CusTaxAccount) getSqlMapClientTemplate().queryForObject("cus_tax_account.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_tax_account
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByExampleSelective(CusTaxAccount record, CusTaxAccountExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("cus_tax_account.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_tax_account
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByExample(CusTaxAccount record, CusTaxAccountExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("cus_tax_account.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_tax_account
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByPrimaryKeySelective(CusTaxAccount record) {
        int rows = getSqlMapClientTemplate().update("cus_tax_account.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_tax_account
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByPrimaryKey(CusTaxAccount record) {
        int rows = getSqlMapClientTemplate().update("cus_tax_account.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table cus_tax_account
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    private static class UpdateByExampleParms extends CusTaxAccountExample {
        private Object record;

        public UpdateByExampleParms(Object record, CusTaxAccountExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}