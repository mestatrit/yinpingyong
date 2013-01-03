/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.net.TBCServer;

import com.mt.common.gui.MTXComponent.MTXErrorMsgPanel;
import com.mt.exception.MTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * 一个对于Swing来说线程安全的回调
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-6-16
 * <p/>
 */
public abstract class CommonMsgSwingCallback implements CommonMsgCallback {

    private Component com;
    private final Logger logger = LoggerFactory.getLogger(CommonMsgSwingCallback.class);

    public CommonMsgSwingCallback(Component com) {
        this.com = com;
    }

    @Override
    public void onMessage(final CommonMsg msg) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    onSwingMessage(msg);
                } catch (Throwable ex) {
                    logger.error(msg.getFID() + "消息处理模块发生未处理异常", ex);
                    MTXErrorMsgPanel.showMessageDialog(com, "系统捕捉到" + msg.getFID() + "功能处理模块发生异常:\n"
                            + MTException.getExceptionMsg(ex)+"\n一般不会影响其他功能", ex);
                }

            }
        });
    }

    /**
     * 对于SwingGUI线程安全的回调
     *
     * @param msg
     */
    abstract public void onSwingMessage(CommonMsg msg);
}
