/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 扩展JComboBox的组合框
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-8
 */
public class MTXComboBox extends JComboBox {

    public MTXComboBox() {
        super();
    }

    public MTXComboBox(Object[] items) {
        super(items);
    }

    public MTXComboBox(List<?> items) {
        super(new Vector(items));
    }

    public List getListItem() {
        ComboBoxModel model = this.getModel();
        List rs = new ArrayList();
        for (int i = 0, j = model.getSize(); i < j; i++) {
            rs.add(model.getElementAt(i));
        }
        return rs;
    }

    public void setListItem(List<?> items) {
        this.setModel(new DefaultComboBoxModel(new Vector(items)));
    }
}
