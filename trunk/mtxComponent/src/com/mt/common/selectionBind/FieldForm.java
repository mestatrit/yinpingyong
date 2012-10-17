package com.mt.common.selectionBind;

/**
 * 一个Field与表单配置类
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-12
 * Time: 1:44:08
 * To change this template use File | Settings | File Templates.
 */
public class FieldForm {

    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 转换类型
     */
    private String converterType;
    /**
     * 显示格式
     */
    private String viewFormat;
    /**
     * 保存格式
     */
    private String saveFormat;

    public FieldForm() {
    }

    /**
     * 通过fieldName,converterType构造
     *
     * @param fName
     * @param converterType
     */
    public FieldForm(String fName, String converterType) {
        this(fName, converterType, null);
    }

    /**
     * 通过fieldName,converterType,viewFormat构造
     *
     * @param fName
     * @param converterType
     * @param viewFormat
     */
    public FieldForm(String fName, String converterType, String viewFormat) {
        this(fName, converterType, viewFormat, null);
    }

    /**
     * 通过fieldName,converterType,viewFormat,saveFormat构造
     *
     * @param fName
     * @param converterType
     * @param viewFormat
     * @param saveFormat
     */
    public FieldForm(String fName, String converterType, String viewFormat, String saveFormat) {
        this.fieldName = fName;
        this.converterType = converterType;
        this.viewFormat = viewFormat;
        this.saveFormat = saveFormat;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getConverterType() {
        return converterType;
    }

    public void setConverterType(String converterType) {
        this.converterType = converterType;
    }

    public String getViewFormat() {
        return viewFormat;
    }

    public void setViewFormat(String viewFormat) {
        this.viewFormat = viewFormat;
    }

    public String getSaveFormat() {
        return saveFormat;
    }

    public void setSaveFormat(String saveFormat) {
        this.saveFormat = saveFormat;
    }
}
