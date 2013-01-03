/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mt.common.net.TBCServer;

/**
 * OID消息的监听器
 * Created by NetBeans.
 * Author: hanhui
 * Date:2010-6-5
 * Time:12:37:31
 */
public interface OIDListener {

    /**
     * OID消息的监听器定义
     *
     * @param key
     * @param value
     */
    public void onOIDMessage(String key, String value);
}
