package com.mt.common.selectionBind;

import com.mt.common.dynamicDataDef.Field;
import com.mt.common.dynamicDataDef.FieldMapNode;

/**
 * 高亮条件的接口
 * Created with IntelliJ IDEA.
 * User: hanhui
 * Date: 12-5-8
 * Time: 下午12:36
 * To change this template use File | Settings | File Templates.
 */
public interface HighlightCondition {

    public boolean isHighlighted(FieldMapNode fmn, Field f,String fName, int row, int col);

}
