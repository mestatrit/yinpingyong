/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.List;

/**
 * 模糊比对组合框单元格编辑器
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-7
 */
public class MTXAutoCompletionCellEditor extends AbstractCellEditor implements TableCellEditor {

    private MTXAutoCompletionComboPanel combo;

    public void setListItems(List<?> items) {
        combo.setListItem(items);
    }

    public MTXAutoCompletionCellEditor(Object[] items) {
        this(items, true);
    }

    public MTXAutoCompletionCellEditor(List<?> items) {
        this(items, true);
    }

    public MTXAutoCompletionCellEditor(Object[] items, boolean isIconVisible) {
        combo = new MTXAutoCompletionComboPanel(items);
        init(isIconVisible);
    }

    public MTXAutoCompletionCellEditor(List<?> items, boolean isIconVisible) {
        combo = new MTXAutoCompletionComboPanel(items);
        init(isIconVisible);
    }

    public boolean isAutoWide() {
        return combo.isAutoWide();
    }

    public void setAutoWide(boolean wide) {
        combo.setAutoWide(wide);
    }

    private void init(boolean isIconVisible) {
        combo.putBoxClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        combo.setBorder(null);
        combo.setIconVisible(isIconVisible);
        combo.addAutoCActionListener(new AutoCActionListener() {

            @Override
            public void onAutoCAction(JComponent source, Object o) {
                stopCellEditing();
            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        return combo.getAutoCSelectedItem();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        combo.setAutoCSelectedItem(value);
        return combo;
    }
}
