package com.mt.common.gui.MTXComponent;

/**
 * 
 * 一个对于顶级视图的规范
 * @author hanhui
 *
 */
public interface MTXTopView {
    /**
     * 开启模糊锁定
     */
    public void startInfiniteLock();

    /**
     * 开启模糊锁定,可以设置文本描述
     */
    public void startInfiniteLock(String text);

    /**
     * 停止模糊锁定
     */
    public void stopInfiniteLock();

    /**
     * 开启进度锁定,只需传进度初始总量
     * @param initValue
     */
    public void startProgressLock(int initValue);

    /**
     * 开启进度锁定,只需传进度初始总量
     * 注册强行停止监听器
     * @param initValue
     * @param listener
     */
    public void startProgressLock(int initValue, ProgressStopListener listener);

    /**
     * 开启进度锁定,传这个进度锁定的描述信息,进度初始总量
     * @param msg
     * @param initValue
     */
    public void startProgressLock(String msg, int initValue);

    /**
     * 开启进度锁定,传进度初始总量,和量的单位描述信息
     * @param initValue
     * @param unit
     */
    public void startProgressLock(int initValue, String unit);

    /**
     * 开启进度锁定,传这个进度锁定的描述信息,进度初始总量,是否需要强行停止按钮
     * @param msg
     * @param initValue
     * @param isStop
     */
    public void startProgressLock(String msg, int initValue, boolean isStop);

    /**
     * 开启进度锁定,传这个进度锁定的描述信息,进度初始总量,量的单位描述信息
     * @param msg
     * @param initValue
     * @param unit
     */
    public void startProgressLock(String msg, int initValue, String unit);

    /**
     * 开启进度锁定,传这个进度锁定的描述信息,进度初始总量,量的单位描述信息
     * 注册强行停止监听器
     * @param msg
     * @param initValue
     * @param listener
     */
    public void startProgressLock(String msg, int initValue, ProgressStopListener listener);

    /**
     * 开启进度锁定,传这个进度锁定的描述信息,进度初始总量,量的单位描述信息,是否需要强行停止按钮
     * @param msg
     * @param initValue
     * @param unit
     * @param isStop
     */
    public void startProgressLock(String msg, int initValue, String unit, boolean isStop);

    /**
     * 开启进度锁定,传这个进度锁定的描述信息,进度初始总量,量的单位描述信息,是否需要强行停止按钮
     * 注册强行停止监听器
     * @param msg
     * @param initValue
     * @param unit
     * @param isStop
     * @param listener
     */
    public void startProgressLock(String msg, int initValue, String unit, boolean isStop, ProgressStopListener listener);

    /**
     * 更新进度状态,当更新的值达到初始总量时自动停止进度锁定
     * @param sValue
     */
    public void updateProgressStatus(int sValue);

    /**
     * 强行停止进度锁定
     */
    public void stopProgressLock();

    /**
     * 是否处于锁定状态
     * @return
     */
    public boolean isViewLock();

    /**
     * 显示
     */
    public void showView();
}
