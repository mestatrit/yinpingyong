package com.mt.common.dynamicDataDef;

/**
 * FieldMapNode的遍历处理接口
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2010-3-9
 * Time: 18:52:39
 * To change this template use File | Settings | File Templates.
 * <p/>
 */
public interface FieldMapNodeTraversalHandler {

    /**
     * 当前遍历到的节点
     *
     * @param node
     */
    public void process(FieldMapNode node);
}
