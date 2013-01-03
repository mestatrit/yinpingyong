package com.mt.common.net.TBCServer;

/**
 * 一个CommonMsg的异步回调接口
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-23
 * Time: 14:35:02
 * To change this template use File | Settings | File Templates.
 */
public interface CommonMsgCallback {

    public void onMessage(CommonMsg msg);
}
