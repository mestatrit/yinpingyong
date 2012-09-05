/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

/**
 * 模糊比对组合框单元格表现器
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-7
 * 
 * Note：
 * 重写渲染器可能有二种情况：
 * 第一：修改组件默认单元格的属性，继承DefaultTableCellRenderer，重写getTableCellRendererComponent方法
 * 		例如：com.mt.common.gui.table.SimpleTableCellRenderer
 * 
 * 第二：更改组件单元格，继承替换的组件以及实现TableCellRenderer，重写getTableCellRendererComponent方法
 * 		例如：本实例
 */
public class MTXAutoCompletionCellRenderer extends MTXAutoCompletionComboPanel implements TableCellRenderer {

    public MTXAutoCompletionCellRenderer(Object[] items) {
        this(items, true);
    }

    public MTXAutoCompletionCellRenderer(List<?> items) {
        this(items, true);
    }

    public MTXAutoCompletionCellRenderer(Object[] items, boolean isIconVisible) {
        super(items);
        setBorder(null);
        setIconVisible(isIconVisible);
    }

    public MTXAutoCompletionCellRenderer(List<?> items, boolean isIconVisible) {
        super(items);
        setBorder(null);
        setIconVisible(isIconVisible);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.setAutoCSelectedItem(value);
        return this;
    }
}
