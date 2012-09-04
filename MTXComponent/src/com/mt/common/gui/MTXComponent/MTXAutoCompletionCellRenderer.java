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
