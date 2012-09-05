/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mt.common.gui.MTXComponent;

import java.util.List;

/**
 * Drop型组合框下拉项目的提供器接口
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-27
 */
public interface DropItemProvider {

    public Object getDropItem(Object value, List dropItemList);
}
