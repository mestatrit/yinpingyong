package com.mt.common.net.TBCServer.internal;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 文本消息队列
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-23
 * Time: 12:18:15
 * To change this template use File | Settings | File Templates.
 */
public class MsgQueue {

    private BlockingQueue<String> bq = new LinkedBlockingQueue<String>();

    public void putMsg(String msg) throws InterruptedException {
        bq.put(msg);
    }

    public String getMsg() throws InterruptedException {
        return bq.take();
    }

    public int size() {
        return bq.size();
    }
}
