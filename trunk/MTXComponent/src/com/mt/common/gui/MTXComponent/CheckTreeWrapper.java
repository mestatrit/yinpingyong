/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * CheckTree包装器
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-28
 */
public class CheckTreeWrapper extends MouseAdapter implements TreeSelectionListener {

    protected CheckTreeSelectionModel selectionModel;
    protected JTree tree = new JTree();
    protected CheckTreePathSelectable selectable;
    int hotspot = new JCheckBox().getPreferredSize().width;

    public CheckTreeWrapper(JTree tree) {
        this(tree, null);
    }

    public CheckTreeWrapper(JTree tree, CheckTreePathSelectable selectable) {
        this.tree = tree;
        this.selectable = selectable;
        if (selectable != null) {
            tree.setLargeModel(true);
        }
        selectionModel = new CheckTreeSelectionModel(tree.getModel());
        tree.setCellRenderer(new CheckTreeCellRenderer(tree.getCellRenderer(), selectionModel, selectable));
        tree.addMouseListener(this);
        selectionModel.addTreeSelectionListener(this);
    }

    public void setCheckTreePathSelectable(CheckTreePathSelectable selectable) {
        this.selectable = selectable;
        if (selectable != null) {
            tree.setLargeModel(true);
        }
        ((CheckTreeCellRenderer) tree.getCellRenderer()).setCheckTreePathSelectable(selectable);
    }

    public CheckTreePathSelectable getCheckTreePathSelectable() {
        return this.selectable;
    }

    public void mouseClicked(MouseEvent me) {
        TreePath path = tree.getPathForLocation(me.getX(), me.getY());
        if (path == null) {
            return;
        }
        if (me.getX() > tree.getPathBounds(path).x + hotspot) {
            return;
        }

        if (selectable != null && !selectable.isSelectable(path)) {
            return;
        }

        boolean selected = selectionModel.isPathSelected(path, true);
        selectionModel.removeTreeSelectionListener(this);

        try {
            if (selected) {
                selectionModel.removeSelectionPath(path);
            } else {
                selectionModel.addSelectionPath(path);
            }
        } finally {
            selectionModel.addTreeSelectionListener(this);
            tree.treeDidChange();
        }
    }

    public CheckTreeSelectionModel getSelectionModel() {
        return selectionModel;
    }

    public void valueChanged(TreeSelectionEvent e) {
        tree.treeDidChange();
    }
}
