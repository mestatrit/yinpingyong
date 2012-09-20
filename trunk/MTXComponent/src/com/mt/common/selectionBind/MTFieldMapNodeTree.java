package com.mt.common.selectionBind;

import com.mt.common.dynamicDataDef.FieldMapCustomToString;
import com.mt.common.dynamicDataDef.FieldMapNode;
import com.mt.common.gui.MTXComponent.MTXTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * FieldMapNode树组件
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-17
 * Time: 17:59:19
 * To change this template use File | Settings | File Templates.
 */
public class MTFieldMapNodeTree extends MTXTree {

    private FieldMapNode fieldMapNode;

    public MTFieldMapNodeTree() {
        this(null);
    }

    /**
     * 从FieldMapNode构造数据
     *
     * @param node
     */
    public MTFieldMapNodeTree(FieldMapNode node) {
        setFieldMapNode(node);
    }

    /**
     * 获得FieldMapTree数据
     *
     * @return
     */
    public FieldMapNode getFieldMapNode() {
        return fieldMapNode;
    }

    /**
     * 设置FieldMapTree数据
     *
     * @param fieldMapNode
     */
    public void setFieldMapNode(FieldMapNode fieldMapNode) {
        this.fieldMapNode = fieldMapNode;
        setModel(new DefaultTreeModel(getTreeNode(this.fieldMapNode)));
    }

    /**
     * 外部配置一个节点自定义的toString格式
     *
     * @param fmd
     */
    public void setFieldMapCustomToString(FieldMapCustomToString fmd) {
        if (this.fieldMapNode != null) {
            this.fieldMapNode.setFieldMapCustomToString(fmd);
            ((DefaultTreeModel) getModel()).reload();
        }
    }

    private DefaultMutableTreeNode getTreeNode(FieldMapNode node) {
        if (node == null) {
            return new DefaultMutableTreeNode();
        }
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(node);
        if (node.isLeaf()) {
            return treeNode;
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            treeNode.add(getTreeNode(node.getChildAt(i)));
        }
        return treeNode;
    }
}
