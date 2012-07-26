/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mt.common.selectionBind;

/**
 * FieldMap型数据表格接口定义
 * Created by NetBeans.
 * Author: hanhui
 * Date:2010-4-23
 * Time:15:17:47
 */
public interface IMTFieldMapTable {

    /**
     * 获得对应字段的原生值
     *
     * @param row
     * @param col
     * @return
     */
    public Object getFieldValueAt(int row, int col);

}
