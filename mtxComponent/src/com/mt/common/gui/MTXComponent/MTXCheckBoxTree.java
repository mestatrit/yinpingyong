/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.List;
import java.util.Vector;

/**
 * CheckBox树组件
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-28
 */
public class MTXCheckBoxTree extends MTXTree {

    private CheckTreeWrapper checkTreeWrapper;

    public MTXCheckBoxTree() {
        checkTreeWrapper = new CheckTreeWrapper(this);
    }

    public MTXCheckBoxTree(Object[] value) {
        super(value);
        checkTreeWrapper = new CheckTreeWrapper(this);
    }

    public MTXCheckBoxTree(Vector<?> value) {
        super(value);
        checkTreeWrapper = new CheckTreeWrapper(this);
    }

    public MTXCheckBoxTree(List<?> value) {
        super(new Vector(value));
        checkTreeWrapper = new CheckTreeWrapper(this);
    }

    public MTXCheckBoxTree(TreeNode root) {
        super(root);
        checkTreeWrapper = new CheckTreeWrapper(this);
    }

    public MTXCheckBoxTree(TreeNode root, boolean asksAllowsChildren) {
        super(root);
        checkTreeWrapper = new CheckTreeWrapper(this);
    }

    public MTXCheckBoxTree(TreeModel newModel) {
        super(newModel);
        checkTreeWrapper = new CheckTreeWrapper(this);
    }

    public void setCheckTreePathSelectable(CheckTreePathSelectable selectable) {
        checkTreeWrapper.setCheckTreePathSelectable(selectable);
    }

    public CheckTreePathSelectable getCheckTreePathSelectable() {
        return checkTreeWrapper.getCheckTreePathSelectable();
    }

    public CheckTreeSelectionModel getCheckSelectionModel() {
        return checkTreeWrapper.getSelectionModel();
    }

    public TreePath[] getCheckSelectionPaths() {
        return getCheckSelectionModel().getSelectionPaths();
    }

    public TreePath getCheckSelectionPath() {
        return getCheckSelectionModel().getSelectionPath();
    }

    public int[] getCheckSelectionRows() {
        return getCheckSelectionModel().getSelectionRows();
    }
}
