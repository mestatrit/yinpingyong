package com.mt.common.gui.MTXComponent;

/**
 * 进度停止监听器
 *
 * @author hanhui
 */
public interface ProgressStopListener {

    /**
     * 进度强行停止
     *
     * @param curValue
     */
    public void progressStop(int curValue);
}
