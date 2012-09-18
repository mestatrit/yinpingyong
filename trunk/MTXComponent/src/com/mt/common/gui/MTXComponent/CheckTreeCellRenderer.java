/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;

/**
 * CheckTree单元格表现器
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-28
 */
public class CheckTreeCellRenderer extends JPanel implements TreeCellRenderer {

    private CheckTreeSelectionModel selectionModel;
    private TreeCellRenderer delegate;
    private CheckTreePathSelectable selectable;
    private MTXTristateCheckBox checkBox = new MTXTristateCheckBox();

    public CheckTreeCellRenderer(TreeCellRenderer delegate, CheckTreeSelectionModel selectionModel) {
        this(delegate, selectionModel, null);
    }

    public CheckTreeCellRenderer(TreeCellRenderer delegate, CheckTreeSelectionModel selectionModel, CheckTreePathSelectable selectable) {
        this.delegate = delegate;
        this.selectionModel = selectionModel;
        this.selectable = selectable;
        setLayout(new BorderLayout());
        setOpaque(false);
        checkBox.setOpaque(false);
    }

    public void setCheckTreePathSelectable(CheckTreePathSelectable selectable) {
        this.selectable = selectable;
    }

    public CheckTreePathSelectable getCheckTreePathSelectable() {
        return this.selectable;
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component renderer = delegate.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        TreePath path = tree.getPathForRow(row);
        checkBox.setVisible(path == null || selectable == null || selectable.isSelectable(path));
        if (path != null) {
            if (selectionModel.isPathSelected(path, true)) {
                checkBox.setState(MTXTristateCheckBox.SELECTED);
            } else if (selectionModel.isPartiallySelected(path)) {
                checkBox.setState(MTXTristateCheckBox.DONT_CARE);
            } else {
                checkBox.setState(MTXTristateCheckBox.NOT_SELECTED);
            }
        }
        removeAll();
        add(checkBox, BorderLayout.WEST);
        add(renderer, BorderLayout.CENTER);
        return this;
    }
}
