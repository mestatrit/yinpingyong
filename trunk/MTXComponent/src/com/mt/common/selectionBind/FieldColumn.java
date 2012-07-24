package com.mt.common.selectionBind;

/**
 * 一个定义Field与Table列的配置类
 * @author hanhui
 *
 */
public class FieldColumn extends FieldForm {

    /**
     * 表格表现的列名
     */
    private String columnName;
    /**
     * 表格getColumnClass的类型
     */
    private Class<?> columnClass;
    /**
     * 是否可以编辑
     */
    private boolean isEditable = false;

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnClass(Class<?> columnClass) {
        this.columnClass = columnClass;
    }

    public Class<?> getColumnClass() {
        return columnClass;
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public boolean isEditable() {
        return isEditable;
    }
}
