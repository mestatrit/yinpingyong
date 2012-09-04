/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import com.mt.common.gui.CommonIconDef;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusListener;
import java.util.List;

/**
 * 模糊比对组合面板
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-8
 */
public class MTXAutoCompletionComboPanel extends JPanel {

    private MTXAutoCompletionComboBox box;
    private JLabel iconLabel;

    public MTXAutoCompletionComboPanel() {
        box = new NullBorderAutoCompletionComboBox();
        init();
    }

    public MTXAutoCompletionComboPanel(Object[] items) {
        box = new NullBorderAutoCompletionComboBox(items);
        init();
    }

    public MTXAutoCompletionComboPanel(List<?> items) {
        box = new NullBorderAutoCompletionComboBox(items);
        init();
    }

    private void init() {
        this.setBackground(box.getBackground());
        this.setForeground(box.getForeground());
        iconLabel = new JLabel(CommonIconDef.SearchIcon);
        setLayout(new BorderLayout());
        setBorder(box.getBorder());
        box.setBorder(null);
        add(box, BorderLayout.CENTER);
        add(iconLabel, BorderLayout.WEST);
        box.setSrcComponent(this);
        setPreferredSize(getPreferredSize());
        setMinimumSize(new Dimension(0, 0));
    }

    public void setSrcComponent(JComponent com) {
        box.setSrcComponent(com);
    }

    public void setIconVisible(boolean isV) {
        iconLabel.setVisible(isV);
    }

    public boolean isIconVisible() {
        return iconLabel.isVisible();
    }

    public void setAcceptEditData(boolean isedit) {
        box.setAcceptEditData(isedit);
    }

    public boolean isAcceptEditData() {
        return box.isAcceptEditData();
    }

    public String getEditData() {
        return box.getEditData();
    }

    public void addAutoCActionListener(AutoCActionListener listener) {
        box.addAutoCActionListener(listener);
    }

    public void removeAutoCActionListener(AutoCActionListener listener) {
        box.removeAutoCActionListener(listener);
    }

    public void setAutoCSelectedItem(Object anObject) {
        box.setAutoCSelectedItem(anObject);
    }

    public void setAutoCSelectedItemWithAction(Object sItem) {
        box.setAutoCSelectedItemWithAction(sItem);
    }

    @Override
    public void addFocusListener(FocusListener fl) {
        box.addFocusListener(fl);
    }

    @Override
    public void removeFocusListener(FocusListener fl) {
        box.removeFocusListener(fl);
    }

    public Object getAutoCSelectedItem() {
        return box.getAutoCSelectedItem();
    }

    public List getListItem() {
        return box.getListItem();
    }

    public List getCurFListItem() {
        return box.getCurFListItem();
    }

    public void setListItem(List<?> items) {
        box.setListItem(items);
    }

    public void setAutoCSelectedIndex(int index) {
        box.setAutoCSelectedIndex(index);
    }

    public int getAutoCSelectedIndex() {
        return box.getAutoCSelectedIndex();
    }

    public Object getAutoCItemAt(int index) {
        return box.getAutoCItemAt(index);
    }

    public int getAutoCItemCount() {
        return box.getAutoCItemCount();
    }

    @Override
    public void requestFocus() {
        box.requestFocus();
    }

    public void setRenderer(ListCellRenderer renderer) {
        box.setRenderer(renderer);
    }

    public ListCellRenderer getRenderer() {
        return box.getRenderer();
    }

    public void putBoxClientProperty(Object k, Object v) {
        box.putClientProperty(k, v);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        iconLabel.setEnabled(enabled);
        box.setEnabled(enabled);
    }

    public boolean isAutoWide() {
        return box.isAutoWide();
    }

    public void setAutoWide(boolean wide) {
        box.setAutoWide(wide);
    }

    /**
     * 因为仅仅调用box的setBorder(null)无法完全去掉边框，存在一点残留,可能是Swing的bug
     * 所以这边再特地覆盖掉paint的方法
     */
    class NullBorderAutoCompletionComboBox extends MTXAutoCompletionComboBox {

        public NullBorderAutoCompletionComboBox() {
            super();
        }

        public NullBorderAutoCompletionComboBox(Object[] items) {
            super(items);
        }

        public NullBorderAutoCompletionComboBox(List<?> items) {
            super(items);
        }

        @Override
        protected void paintBorder(Graphics g) {

        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }

    }
}
