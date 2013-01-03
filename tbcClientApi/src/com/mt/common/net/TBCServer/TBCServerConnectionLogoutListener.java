/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mt.common.net.TBCServer;

/**
 * 服务器强行要求连接断开的监听器
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-9-29
 */
public interface TBCServerConnectionLogoutListener {

    public void onLogout(String info);

}
