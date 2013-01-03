package com.mt.common.net.TBCServer;

/**
 * 监听TBCServerConnection的状态变化
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-23
 * Time: 15:07:20
 * To change this template use File | Settings | File Templates.
 */
public interface TBCServerConnectionListener {

    /**
     * 状态变化
     *
     * @param status
     */
    public void statusChange(int status);
}
