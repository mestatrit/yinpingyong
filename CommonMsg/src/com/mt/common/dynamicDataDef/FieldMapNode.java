package com.mt.common.dynamicDataDef;

import com.mt.common.dynamicDataDef.comparator.FieldMapBPCodeStringComparator;
import com.mt.common.dynamicDataDef.comparator.FieldMapDateComparator;
import com.mt.common.dynamicDataDef.comparator.FieldMapNumberComparator;
import com.mt.common.dynamicDataDef.comparator.FieldMapStringComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 一个FieldMap树节点
 *
 * @author hanhui
 */
public class FieldMapNode extends FieldMap {

    /**
     * 存放子节点
     */
    private List<FieldMapNode> children = new ArrayList<FieldMapNode>();
    /**
     * 父节点
     */
    private FieldMapNode parent;
    /**
     * 记录节点数量
     */
    private int nodeCount = 0;

    /**
     * 从一个名字构造FieldMapNode
     *
     * @param name
     */
    public FieldMapNode(String name) {
        super(name);
    }

    /**
     * 在某个位置插入一个子节点
     *
     * @param node
     * @param index
     */
    public FieldMapNode insertChildNode(FieldMapNode node, int index) {
        FieldMapNode oldParent = node.getParent();
        if (oldParent != null) {
            oldParent.removeChildNode(node);
        }
        node.setParent(this);
        children.add(index, node);
        return this;
    }

    /**
     * 加入一个子节点
     *
     * @param node
     * @return
     */
    public FieldMapNode addChildNode(FieldMapNode node) {
        FieldMapNode oldParent = node.getParent();
        if (oldParent != null) {
            oldParent.removeChildNode(node);
        }
        node.setParent(this);
        children.add(node);
        return this;
    }

    /**
     * 加入一批子节点
     *
     * @param list
     * @return
     */
    public FieldMapNode addChildNodeList(List<FieldMapNode> list) {
        for (FieldMapNode node : list) {
            addChildNode(node);
        }
        return this;
    }

    /**
     * 加入一批子节点,数组
     *
     * @param list
     * @return
     */
    public FieldMapNode addChildNodeArray(FieldMapNode[] list) {
        for (FieldMapNode node : list) {
            addChildNode(node);
        }
        return this;
    }

    /**
     * 移除一个子节点,通过index
     *
     * @param index
     * @return
     */
    public FieldMapNode removeChildNode(int index) {
        FieldMapNode node = children.get(index);
        node.setParent(null);
        children.remove(index);
        return this;
    }

    /**
     * 去除特定的子节点
     *
     * @param attrName
     * @param attrValue
     * @return
     */
    public FieldMapNode removeChildNode(String attrName, String attrValue) {
        List<FieldMapNode> list = new ArrayList<FieldMapNode>();
        for (FieldMapNode child : children) {
            if (child.getField(attrName) != null
                    && child.getStringValue(attrName).equals(attrValue)) {
                list.add(child);
            }
        }
        return removeChildNodeList(list);
    }

    /**
     * 移除一个子节点
     *
     * @param node
     * @return
     */
    public FieldMapNode removeChildNode(FieldMapNode node) {
        node.setParent(null);
        children.remove(node);
        return this;
    }

    /**
     * 移除一批子节点
     *
     * @param list
     * @return
     */
    public FieldMapNode removeChildNodeList(List<FieldMapNode> list) {
        for (FieldMapNode node : list) {
            removeChildNode(node);
        }
        return this;
    }

    /**
     * 移除所有子节点
     *
     * @return
     */
    public FieldMapNode removeAllChildNode() {
        for (FieldMapNode node : children) {
            node.setParent(null);
        }
        children.clear();
        return this;
    }

    /**
     * 获得子节点的数量
     *
     * @return
     */
    public int getChildCount() {
        return children.size();
    }

    /**
     * 获得Node下所有的节点数量
     *
     * @return
     */
    public int getAllNodeCount() {
        nodeCount = 0;
        this.nodeTraversal(new FieldMapNodeTraversalHandler() {

            public void process(FieldMapNode node) {
                nodeCount++;
            }
        });
        return nodeCount;
    }

    /**
     * 通过index获得子节点
     *
     * @param index
     * @return
     */
    public FieldMapNode getChildAt(int index) {
        return children.get(index);
    }

    /**
     * 通过节点名字获得子节点
     *
     * @param name
     * @return
     */
    public FieldMapNode getChildAt(String name) {
        for (FieldMapNode node : children) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    /**
     * 从树中根据某个字段搜索节点
     *
     * @param fName
     * @param value
     * @return
     */
    public FieldMapNode searchNode(String fName, String value) {
        Field f = this.getField(fName);
        if (f != null) {
            if (f.getValue().equals(value)) {
                return this;
            }
        }

        for (int i = 0; i < getChildCount(); i++) {
            FieldMapNode node = getChildAt(i).searchNode(fName, value);
            if (node != null) {
                return node;
            }
        }

        return null;
    }

    public boolean removeNode(String fName, String value) {
        boolean isDel = false;
        for (int i = 0; i < getChildCount(); i++) {
            FieldMapNode fmn = getChildAt(i);
            if (fmn.isSetField(fName) && fmn.getStringValue(fName).equals(value)) {
                removeChildNode(i);
                isDel = true;
                break;
            }
        }
        if (!isDel) {
            for (int i = 0; i < getChildCount(); i++) {
                if (isDel = getChildAt(i).removeNode(fName, value)) {
                    break;
                }
            }
        }
        return isDel;
    }

    /**
     * 获得子节点的列表
     *
     * @return
     */
    public List<FieldMapNode> toChildNodeList() {
        List<FieldMapNode> list = new ArrayList<FieldMapNode>();
        for (FieldMapNode node : children) {
            list.add(node);
        }
        return list;
    }

    /**
     * 设置父节点
     *
     * @param parent
     */
    public void setParent(FieldMapNode parent) {
        this.parent = parent;
    }

    /**
     * 获得父节点
     *
     * @return
     */
    public FieldMapNode getParent() {
        return this.parent;
    }

    /**
     * 是否是根节点
     *
     * @return
     */
    public boolean isRoot() {
        return getParent() == null;
    }

    /**
     * 是否是叶节点
     *
     * @return
     */
    public boolean isLeaf() {
        return getChildCount() == 0;
    }

    /**
     * 浅复制，不包含子节点
     *
     * @return
     */
    public FieldMapNode copyFieldMapNode() {
        FieldMapNode fmn = new FieldMapNode(this.getName());
        List<Field> fields = toFieldList();
        for (Field f : fields) {
            fmn.addField((Field) f.clone());
        }
        return fmn;
    }

    /**
     * 浅复制，包含子节点
     *
     * @return
     */
    public FieldMapNode copyFieldMapTree() {
        return copyFieldMapTree(this);
    }

    /**
     * 复制整棵树
     *
     * @param node
     * @return
     */
    private FieldMapNode copyFieldMapTree(FieldMapNode node) {
        FieldMapNode copyNode = node.copyFieldMapNode();
        if (node.isLeaf()) {
            return copyNode;
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            copyNode.addChildNode(copyFieldMapTree(node.getChildAt(i)));
        }
        return copyNode;
    }

    /**
     * 将某个名字的节点收集到list，假设每一层的节点名字是相同的
     *
     * @param name
     * @return
     */
    public void collectNodesByName(String name, List<FieldMapNode> list) {
        int count = getChildCount();
        if (count > 0) {
            String n = getChildAt(0).getName();
            if (n.equals(name)) {
                list.addAll(toChildNodeList());
            } else {
                for (int i = 0; i < count; i++) {
                    getChildAt(i).collectNodesByName(name, list);
                }
            }
        }
    }

    /**
     * 将某个名字的父节点收集到list，假设每一层的节点名字是相同的
     *
     * @param name
     * @param list
     */
    public void collectNodeParentByName(String name, List<FieldMapNode> list) {
        int count = getChildCount();
        if (count > 0) {
            String n = getChildAt(0).getName();
            if (n.equals(name)) {
                list.add(this);
            } else {
                for (int i = 0; i < count; i++) {
                    getChildAt(i).collectNodeParentByName(name, list);
                }
            }
        }
    }

    /**
     * 更新自己以及下面所有子孙节点的某个字段的值，如果某个节点没有这字段，那么就忽略
     *
     * @param fName
     * @param value
     */
    public void updateFieldValue(String fName, Object value) {
        Field f = this.getField(fName);
        if (f != null) {
            f.setValue(f);
        }
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).updateFieldValue(fName, value);
        }
    }

    /**
     * 为整棵树所有节点都加上某个字段
     *
     * @param fName
     * @param value
     */
    public void addField(String fName, Object value) {
        this.putField(fName, value);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).addField(fName, value);
        }
    }

    /**
     * 为整棵树中某个名字的节点加上某个字段
     *
     * @param nodeName
     * @param fName
     * @param value
     */
    public void addField(String nodeName, String fName, Object value) {
        if (this.getName().equals(nodeName)) {
            this.putField(fName, value);
        }
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).addField(nodeName, fName, value);
        }
    }

    /**
     * 对树的lName这一层进行排序,假设每一层的节点名字是相同的
     *
     * @param comparator
     */
    @SuppressWarnings("unchecked")
	public void sort(Comparator comparator, String lName) {
        int count = getChildCount();
        FieldMapNode[] nodes = new FieldMapNode[count];
        for (int i = 0; i < count; i++) {
            nodes[i] = getChildAt(i);
            nodes[i].sort(comparator, lName);
        }
        if (count > 1 && (lName == null || nodes[0].getName().equals(lName))) {
            Arrays.sort(nodes, comparator);
            this.removeAllChildNode();
            this.addChildNodeArray(nodes);
        }
    }

    /**
     * 每一层都排序
     *
     * @param comparator
     */
    @SuppressWarnings("unchecked")
	public void sort(Comparator comparator) {
        sort(comparator, null);
    }

    /**
     * 数值排序
     *
     * @param fName
     * @param descending
     * @param lName
     */
    public void sortNumber(String fName, boolean descending, String lName) {
        sort(new FieldMapNumberComparator(fName, descending), lName);
    }

    /**
     * 字符串排序
     *
     * @param fName
     * @param descending
     * @param lName
     */
    public void sortString(String fName, boolean descending, String lName) {
        sort(new FieldMapStringComparator(fName, descending), lName);
    }

    /**
     * 括号对的字符串排序
     *
     * @param fName
     * @param descending
     * @param lName
     */
    public void sortBPCodeString(String fName, boolean descending, String lName) {
        sort(new FieldMapBPCodeStringComparator(fName, descending), lName);
    }

    /**
     * 日期排序
     *
     * @param fName
     * @param descending
     * @param lName
     */
    public void sortDate(String fName, boolean descending, String lName) {
        sort(new FieldMapDateComparator(fName, descending), lName);
    }

    /**
     * 遍历整棵树
     *
     * @param handler
     */
    public void nodeTraversal(FieldMapNodeTraversalHandler handler) {
        handler.process(this);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).nodeTraversal(handler);
        }
    }

    /**
     * 外部配置一个自定义的toString格式
     *
     * @param fmd
     */
    @Override
    public FieldMapNode setFieldMapCustomToString(FieldMapCustomToString fmd) {
        super.setFieldMapCustomToString(fmd);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setFieldMapCustomToString(fmd);
        }
        return this;
    }
}
