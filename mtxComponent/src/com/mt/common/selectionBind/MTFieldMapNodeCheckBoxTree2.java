package com.mt.common.selectionBind;

import com.mt.common.dynamicDataDef.FieldMapCustomToString;
import com.mt.common.dynamicDataDef.FieldMapNode;
import com.mt.common.gui.checkBoxTree.CheckBoxTree;
import com.mt.common.gui.checkBoxTree.State;
import com.mt.common.gui.checkBoxTree.TristateCheckBoxNode;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;

/**
 * 继承自CheckBoxTree的FieldMapNodeCheckBoxTree
 * <p/>
 * Created with IntelliJ IDEA.
 * User: hanhui
 * Date: 12-8-6
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
public class MTFieldMapNodeCheckBoxTree2 extends CheckBoxTree {
    private FieldMapNode fieldMapNode;

    public MTFieldMapNodeCheckBoxTree2() {
        this(null);
    }

    /**
     * 从FieldMapNode构造数据
     *
     * @param node
     */
    public MTFieldMapNodeCheckBoxTree2(FieldMapNode node) {
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
     * 获得所有选中的FieldMapNode的某个字段值
     *
     * @param fName
     * @return
     */
    public List getSelNodeValue(String fName) {
        return getNodeValue(fName, true);
    }

    /**
     * 获得所有没有选中的FieldMapNode的某个字段值
     *
     * @param fName
     * @return
     */
    public List getNSelNodeValue(String fName) {
        return getNodeValue(fName, false);
    }

    /**
     * 设置FieldMapNode某个字段值包含在vList里面的为选中
     * @param vList
     * @param fName
     */
    public void setSelNodeValue(List vList, String fName) {
        setNodeValue(vList,fName,true);
    }

    /**
     * 设置FieldMapNode某个字段值不包含在vList里面的为选中
     * @param vList
     * @param fName
     */
    public void setNSelNodeValue(List vList, String fName) {
        setNodeValue(vList,fName,false);
    }

    private void setNodeValue(List vList, String fName, boolean isSel) {
        clearSelection();
        setNodeValue((TristateCheckBoxNode)getModel().getRoot(),vList,fName,isSel);
    }

    private void setNodeValue(TristateCheckBoxNode node, List vList, String fName, boolean isSel) {
        if (node == null) {
            return;
        }
        FieldMapNode fmn = (FieldMapNode) node.getUserObject();
        boolean isS = isSel?vList.contains(fmn.getFieldValue(fName)):!vList.contains(fmn.getFieldValue(fName));
        if(isS && node.isLeaf()){
            node.setState(State.SELECTED);
        }
        for (TristateCheckBoxNode tmp : node.getChildren()) {
            setNodeValue(tmp, vList,fName,isSel);
        }
    }

    private List getNodeValue(String fName, boolean isSel) {
        List vList = new ArrayList();
        getNodeValue((TristateCheckBoxNode) getModel().getRoot(), vList, fName, isSel);
        return vList;
    }

    private void getNodeValue(TristateCheckBoxNode node, List v, String fName, boolean isSel) {
        if (node == null) {
            return;
        }
        boolean isV = isSel ? State.SELECTED.equals(node.getState()) : State.NOT_SELECTED.equals(node.getState());
        if (isV) {
            FieldMapNode fmn = (FieldMapNode) node.getUserObject();
            v.add(fmn.getFieldValue(fName));
        }
        for (TristateCheckBoxNode tmp : node.getChildren()) {
            getNodeValue(tmp, v, fName, isSel);
        }
    }

    /**
     * 设置FieldMapTree数据
     *
     * @param fieldMapNode
     */
    public void setFieldMapNode(FieldMapNode fieldMapNode) {
        this.fieldMapNode = fieldMapNode;
        setDefaultTreeModel(new DefaultTreeModel(getTreeNode(this.fieldMapNode)));

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
            return new TristateCheckBoxNode();
        }
        TristateCheckBoxNode treeNode = new TristateCheckBoxNode(node);
        if (node.isLeaf()) {
            return treeNode;
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            treeNode.add(getTreeNode(node.getChildAt(i)));
        }
        return treeNode;
    }
}
