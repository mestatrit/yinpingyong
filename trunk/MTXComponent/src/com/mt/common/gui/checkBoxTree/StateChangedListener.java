package com.mt.common.gui.checkBoxTree;

/**
 * CheckTree状态变动监听器
 */
public interface StateChangedListener {

    public void stateChanged(TristateCheckBoxNode t, State oState, State nState);
}
