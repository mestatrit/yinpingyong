/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mt.common.gui.MTXComponent;

import java.util.List;

/**
 * FiltrationList提供器
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-21
 */
public interface FiltrationListProvider {

    public List getFiltrationList(Object sel, List dataList);
}
