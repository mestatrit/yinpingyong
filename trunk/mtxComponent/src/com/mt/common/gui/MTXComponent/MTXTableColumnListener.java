package com.mt.common.gui.MTXComponent;

import java.util.List;

/**
 * 列变动的监听器
 * Created with IntelliJ IDEA.
 * User: hanhui
 * Date: 12-6-26
 * Time: 下午6:43
 * To change this template use File | Settings | File Templates.
 */
public interface MTXTableColumnListener {

    /**
     * 列发生变动
     * @param hideCol 隐藏的列
     */
    public void columnChanged(MTXTable table,List<String> hideCol);

}
