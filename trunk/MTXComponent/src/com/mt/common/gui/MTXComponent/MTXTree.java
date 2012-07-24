/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import javax.swing.*;
import javax.swing.tree.*;
import java.util.*;

/**
 * 扩展标准 支持搜索的树
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-27
 */
public class MTXTree extends JTree {

    private Map<DefaultMutableTreeNode, List<DefaultMutableTreeNode>> hiddenNodes = new HashMap<DefaultMutableTreeNode, List<DefaultMutableTreeNode>>();
    private Map<DefaultMutableTreeNode, Integer> hiddenIndexNodes = new HashMap<DefaultMutableTreeNode, Integer>();
    private String filterText = "";
    private OverlayListener ol;

    public MTXTree() {
        init();
    }

    public MTXTree(Object[] value) {
        super(value);
        init();
    }

    public MTXTree(Vector<?> value) {
        super(value);
        init();
    }

    public MTXTree(List<?> value) {
        super(new Vector(value));
        init();
    }

    public MTXTree(TreeNode root) {
        super(root);
        init();
    }

    public MTXTree(TreeNode root, boolean asksAllowsChildren) {
        super(root);
        init();
    }

    public MTXTree(TreeModel newModel) {
        super(newModel);
        init();
    }

    public boolean setFilterText(String fText) {
        filterText = fText == null ? "" : fText;
        return refresh();
    }

    public String getFilterText() {
        return filterText;
    }

    @Override
    public void setModel(TreeModel model) {
        super.setModel(model);
        if (hiddenNodes == null || hiddenIndexNodes == null) {
            return;
        }
        refresh();
    }

    public void expandAllChildren() {
        DefaultMutableTreeNode sourceRootNode = (DefaultMutableTreeNode) treeModel.getRoot();
        expandAllChildren(sourceRootNode);
    }

    public void expandSelectedNode() {
        TreePath path = getSelectionPath();
        DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode) path.getLastPathComponent());
        expandAllChildren(selectedNode);
    }

    public void expandAllChildren(DefaultMutableTreeNode node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
            if (child.isLeaf()) {
                expandSiblings(child);
            } else {
                expandAllChildren(child);
            }
        }
    }

    public void expandSiblings(DefaultMutableTreeNode node) {
        TreePath path = new TreePath(((DefaultMutableTreeNode) node.getParent()).getPath());
        if (!isExpanded(path)) {
            expandPath(path);
        }
    }

    public void resetGlassPane() {
        ol.resetGlassPane();
    }

    private void init() {
        ol = new OverlayListener(this);
    }

    private boolean refresh() {
        DefaultMutableTreeNode sourceRootNode = (DefaultMutableTreeNode) treeModel.getRoot();
        displayFullModel(sourceRootNode);
        if (!filterText.equals("")) {
            return transformNode(sourceRootNode);
        } else {
            expandAllChildren(sourceRootNode);
            return true;
        }
    }

    private void displayFullModel(DefaultMutableTreeNode node) {
        List<DefaultMutableTreeNode> hiddenChildren = getHiddenChildren(node);

        if (hiddenChildren != null) {
            Map<Integer, DefaultMutableTreeNode> childrenMap = new HashMap<Integer, DefaultMutableTreeNode>();
            List<Integer> listIndex = new ArrayList<Integer>();
            for (DefaultMutableTreeNode child : hiddenChildren) {
                int index = hiddenIndexNodes.get(child);
                childrenMap.put(index, child);
                listIndex.add(index);
            }
            Collections.sort(listIndex);
            for (Integer index : listIndex) {
                DefaultMutableTreeNode child = childrenMap.get(index);
                if (node.getChildCount() < index) {
                    index--;
                }
                child.removeAllChildren();
                ((DefaultTreeModel) treeModel).insertNodeInto(child, node, index);
            }
            hiddenNodes.remove(node);
            hiddenIndexNodes.remove(node);
        }
        for (int i = node.getChildCount() - 1; i >= 0; i--) {
            displayFullModel((DefaultMutableTreeNode) node.getChildAt(i));
        }

    }

    private boolean transformNode(DefaultMutableTreeNode node) {
        boolean match = false;
        boolean currentNodeMatches = match(node.getUserObject());
        boolean atLeastOneChildMatches = false;
        TreePath path = new TreePath(node.getPath());
        if (currentNodeMatches && !node.isRoot()) {
            match = true;
        } else {
            for (int i = node.getChildCount() - 1; i >= 0; i--) {
                DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
                atLeastOneChildMatches = transformNode(childNode);
                if (!atLeastOneChildMatches) {
                    addHiddenNode(node, childNode);
                    hiddenIndexNodes.put(childNode, i);
                    ((DefaultTreeModel) treeModel).removeNodeFromParent(childNode);
                } else {
                    match = true;
                }
            }
        }
        if (match) {
            if (!isExpanded(path)) {
                expandAllChildren(node);
            }
        }
        return match;
    }

    private void addHiddenNode(DefaultMutableTreeNode parent, DefaultMutableTreeNode child) {
        if (hiddenNodes.containsKey(parent)) {
            ((List<DefaultMutableTreeNode>) hiddenNodes.get(parent)).add(child);
        } else {
            List<DefaultMutableTreeNode> list = new ArrayList<DefaultMutableTreeNode>();
            list.add(child);
            hiddenNodes.put(parent, list);
        }
    }

    private List<DefaultMutableTreeNode> getHiddenChildren(DefaultMutableTreeNode parent) {
        List<DefaultMutableTreeNode> hiddenChildren = hiddenNodes.get(parent);
        if (hiddenChildren == null) {
            hiddenChildren = Collections.emptyList();
        }
        return hiddenChildren;
    }

    private boolean match(Object o) {
        if (filterText.equals("") || new FObject(o).contains(filterText)) {
            return true;
        }
        return false;
    }
}
