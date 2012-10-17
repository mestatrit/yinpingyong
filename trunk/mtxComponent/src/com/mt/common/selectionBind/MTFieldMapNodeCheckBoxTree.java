package com.mt.common.selectionBind;

import com.mt.common.dynamicDataDef.FieldMapCustomToString;
import com.mt.common.dynamicDataDef.FieldMapNode;
import com.mt.common.gui.MTXComponent.MTXCheckBoxTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * 支持FieldMapNode的CheckBoxTree
 *
 * Created with IntelliJ IDEA.
 * User: hanhui
 * Date: 12-8-2
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 */
public class MTFieldMapNodeCheckBoxTree extends MTXCheckBoxTree {

    private FieldMapNode fieldMapNode;

    public MTFieldMapNodeCheckBoxTree() {
        this(null);
    }

    /**
     * 从FieldMapNode构造数据
     *
     * @param node
     */
    public MTFieldMapNodeCheckBoxTree(FieldMapNode node) {
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
