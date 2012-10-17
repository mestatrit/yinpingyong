package com.mt.common.gui.checkBoxTree;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 三态CheckBox树节点
 */
public class TristateCheckBoxNode extends DefaultMutableTreeNode {

    private State state = State.NOT_SELECTED;
    private boolean editable = true;

    public List<TristateCheckBoxNode> getChildren() {

        List<TristateCheckBoxNode> tcbs = new ArrayList<TristateCheckBoxNode>();
        int len = getChildCount();
        for (int i = 0; i < len; i++) {
            tcbs.add((TristateCheckBoxNode) getChildAt(i));
        }

        return tcbs;
    }

    public TristateCheckBoxNode(Object o) {
        setUserObject(o);
    }

    public TristateCheckBoxNode() {
    }

    public void setState(State state) {
        if (!isEditable()) {
            return;
        }

        if (state == null) {
            throw new NullPointerException();
        }
        if (state == State.DONT_CARE) {
            throw new RuntimeException();
        }

        State oldState = this.state;
        this.state = state;

        if (oldState != state) {
            fireStateChanged(oldState, state);
        }
    }

    Set<StateChangedListener> scls = new HashSet<StateChangedListener>();

    void addStateChangedListener(StateChangedListener scl) {
        if (scl != null) {
            scls.add(scl);
        }

    }

    void removeStateChangedListener(StateChangedListener scl) {
        scls.remove(scl);

    }

    private void fireStateChanged(State oState, State nState) {
        for (StateChangedListener scl : scls) {
            scl.stateChanged(this, oState, nState);
        }
    }

    void justSetState(State state) {
        if (!isEditable()) {
            return;
        }
        this.state = state;

    }

    public State getState() {
        return state;
    }

    public void nextState() {
        if (!editable) {
            return;
        }
        if (state == State.SELECTED) {
            setState(State.NOT_SELECTED);
        } else if (state == State.NOT_SELECTED) {
            setState(State.SELECTED);
        } else {
            setState(State.SELECTED);
        }

    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }
}
