package org.sunside.rycs.repository.dao.cust;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.sunside.rycs.repository.model.cust.CusLicense;
import org.sunside.rycs.repository.model.cust.CusLicenseExample;

public class CusLicenseDAOImpl extends SqlMapClientDaoSupport implements CusLicenseDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_license
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public CusLicenseDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_license
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int countByExample(CusLicenseExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("cus_license.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_license
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int deleteByExample(CusLicenseExample example) {
        int rows = getSqlMapClientTemplate().delete("cus_license.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_license
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int deleteByPrimaryKey(Integer id) {
        CusLicense key = new CusLicense();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("cus_license.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_license
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void insert(CusLicense record) {
        getSqlMapClientTemplate().insert("cus_license.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_license
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public void insertSelective(CusLicense record) {
        getSqlMapClientTemplate().insert("cus_license.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_license
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public List selectByExample(CusLicenseExample example) {
        List list = getSqlMapClientTemplate().queryForList("cus_license.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_license
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public CusLicense selectByPrimaryKey(Integer id) {
        CusLicense key = new CusLicense();
        key.setId(id);
        CusLicense record = (CusLicense) getSqlMapClientTemplate().queryForObject("cus_license.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_license
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByExampleSelective(CusLicense record, CusLicenseExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("cus_license.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_license
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByExample(CusLicense record, CusLicenseExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("cus_license.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_license
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByPrimaryKeySelective(CusLicense record) {
        int rows = getSqlMapClientTemplate().update("cus_license.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table cus_license
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    public int updateByPrimaryKey(CusLicense record) {
        int rows = getSqlMapClientTemplate().update("cus_license.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table cus_license
     *
     * @ibatorgenerated Thu Sep 20 01:29:51 CST 2012
     */
    private static class UpdateByExampleParms extends CusLicenseExample {
        private Object record;

        public UpdateByExampleParms(Object record, CusLicenseExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}