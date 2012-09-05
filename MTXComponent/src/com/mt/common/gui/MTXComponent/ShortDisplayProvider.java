/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mt.common.gui.MTXComponent;

/**
 * 简短表示提供器
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-21
 */
public interface ShortDisplayProvider {

    /**
     * 获得某个对象的简短表示,这简短的表示通常是一个简单的文本或一个小图标
     *
     * @param obj
     * @return
     */
    public Object getShortDisplay(Object obj);

}
