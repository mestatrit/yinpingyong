package com.mt.common.dynamicDataDef;

/**
 * 一个从FieldMap自定义提取NameCode的接口
 * <p/>
 * hanhui
 * <p/>
 */
public interface NameCodeGetter {

    /**
     * 自定义FieldMap的Name
     *
     * @param map
     * @return
     */
    public String getName(FieldMap map);

    /**
     * 自定义FieldMap的Code
     *
     * @param map
     * @return
     */
    public String getCode(FieldMap map);
}
