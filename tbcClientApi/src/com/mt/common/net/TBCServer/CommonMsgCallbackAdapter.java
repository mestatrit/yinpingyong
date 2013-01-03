package com.mt.common.net.TBCServer;

/**
 * CommonMsgCallback的Adapter
 *
 * @author hanhui
 */
public abstract class CommonMsgCallbackAdapter implements CommonMsgCallback {

    protected CommonMsgCallback mcb;

    public CommonMsgCallbackAdapter(CommonMsgCallback mcb) {
        this.mcb = mcb;
    }

    public CommonMsgCallback getCommonMsgCallback() {
        return this.mcb;
    }

    abstract public void onMessage(CommonMsg msg);
}
