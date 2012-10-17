/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.util.ArrayList;
import java.util.Stack;

/**
 * CheckTree选择模型
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-28
 */
public class CheckTreeSelectionModel extends DefaultTreeSelectionModel {

    private TreeModel model;

    public CheckTreeSelectionModel(TreeModel model) {
        this.model = model;
        setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    }

    public boolean isPartiallySelected(TreePath path) {
        if (isPathSelected(path, true)) {
            return false;
        }
        TreePath[] selectionPaths = getSelectionPaths();
        if (selectionPaths == null) {
            return false;
        }
        for (int j = 0; j < selectionPaths.length; j++) {
            if (isDescendant(selectionPaths[j], path)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPathSelected(TreePath path, boolean dig) {
        if (!dig) {
            return super.isPathSelected(path);
        }
        while (path != null && !super.isPathSelected(path)) {
            path = path.getParentPath();
        }
        return path != null;
    }

    private boolean isDescendant(TreePath path1, TreePath path2) {
        Object obj1[] = path1.getPath();
        Object obj2[] = path2.getPath();
        for (int i = 0; i < obj2.length; i++) {
            if (obj1[i] != obj2[i]) {
                return false;
            }
        }
        return true;
    }

    public void setSelectionPaths(TreePath[] pPaths) {
        throw new UnsupportedOperationException("not implemented yet!");
    }

    public void addSelectionPaths(TreePath[] paths) {
        for (int i = 0; i < paths.length; i++) {
            TreePath path = paths[i];
            TreePath[] selectionPaths = getSelectionPaths();
            if (selectionPaths == null) {
                break;
            }
            ArrayList toBeRemoved = new ArrayList();
            for (int j = 0; j < selectionPaths.length; j++) {
                if (isDescendant(selectionPaths[j], path)) {
                    toBeRemoved.add(selectionPaths[j]);
                }
            }
            super.removeSelectionPaths((TreePath[]) toBeRemoved.toArray(new TreePath[0]));
        }

        for (int i = 0; i < paths.length; i++) {
            TreePath path = paths[i];
            TreePath temp = null;
            while (areSiblingsSelected(path)) {
                temp = path;
                if (path.getParentPath() == null) {
                    break;
                }
                path = path.getParentPath();
            }
            if (temp != null) {
                if (temp.getParentPath() != null) {
                    addSelectionPath(temp.getParentPath());
                } else {
                    if (!isSelectionEmpty()) {
                        removeSelectionPaths(getSelectionPaths());
                    }
                    super.addSelectionPaths(new TreePath[]{temp});
                }
            } else {
                super.addSelectionPaths(new TreePath[]{path});
            }
        }
    }

    private boolean areSiblingsSelected(TreePath path) {
        TreePath parent = path.getParentPath();
        if (parent == null) {
            return true;
        }
        Object node = path.getLastPathComponent();
        Object parentNode = parent.getLastPathComponent();

        int childCount = model.getChildCount(parentNode);
        for (int i = 0; i < childCount; i++) {
            Object childNode = model.getChild(parentNode, i);
            if (childNode == node) {
                continue;
            }
            if (!isPathSelected(parent.pathByAddingChild(childNode))) {
                return false;
            }
        }
        return true;
    }

    public void removeSelectionPaths(TreePath[] paths) {
        for (int i = 0; i < paths.length; i++) {
            TreePath path = paths[i];
            if (path.getPathCount() == 1) {
                super.removeSelectionPaths(new TreePath[]{path});
            } else {
                toggleRemoveSelection(path);
            }
        }
    }

    private void toggleRemoveSelection(TreePath path) {
        Stack stack = new Stack();
        TreePath parent = path.getParentPath();
        while (parent != null && !isPathSelected(parent)) {
            stack.push(parent);
            parent = parent.getParentPath();
        }
        if (parent != null) {
            stack.push(parent);
        } else {
            super.removeSelectionPaths(new TreePath[]{path});
            return;
        }

        while (!stack.isEmpty()) {
            TreePath temp = (TreePath) stack.pop();
            TreePath peekPath = stack.isEmpty() ? path : (TreePath) stack.peek();
            Object node = temp.getLastPathComponent();
            Object peekNode = peekPath.getLastPathComponent();
            int childCount = model.getChildCount(node);
            for (int i = 0; i < childCount; i++) {
                Object childNode = model.getChild(node, i);
                if (childNode != peekNode) {
                    super.addSelectionPaths(new TreePath[]{temp.pathByAddingChild(childNode)});
                }
            }
        }
        super.removeSelectionPaths(new TreePath[]{parent});
    }
}
