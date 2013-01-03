/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mt.common.net.TBCServer;

/**
 * OID错误消息的监听器
 * 如果某个消息通信底层无法解析成key/value格式的就通过这个监听器通知
 * Created by NetBeans.
 * Author: hanhui
 * Date:2010-6-5
 * Time:13:18:25
 */
public interface OIDErrorListener {

    public void onErrorOIDMsg(CommonMsg errorMsg);
}
