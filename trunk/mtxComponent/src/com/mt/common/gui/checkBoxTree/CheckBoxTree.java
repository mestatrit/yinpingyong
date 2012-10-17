package com.mt.common.gui.checkBoxTree;

import com.mt.common.gui.MTXComponent.MTXTree;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * CheckBox树组件
 */
public class CheckBoxTree extends MTXTree implements StateChangedListener, MouseListener {

    int hotspot = new JCheckBox().getPreferredSize().width;

    public List<TreePath> getSelectedPaths() {
        return null;
    }

    public List<Object> getSelectedObjects() {
        return null;
    }

    public CheckBoxTree() {
        this(null, false);
    }

    public CheckBoxTree(boolean isView) {
        this(null, isView);
    }

    public CheckBoxTree(DefaultTreeModel dtm, boolean isView) {
        setDefaultTreeModel(dtm);
        setCellRenderer(new CheckTreeCellRenderer(isView));
        if (!isView) {
            addMouseListener(this);
        }
    }

    public void setDefaultTreeModel(DefaultTreeModel dtm) {
        if (dtm == null) {
            return;
        }

        try {
            TristateCheckBoxNode tcbn = (TristateCheckBoxNode) dtm.getRoot();
            addListeners(tcbn);
            setModel(dtm);

        } catch (ClassCastException e) {
            throw new RuntimeException();
        }

    }

    private void addListeners(TristateCheckBoxNode tcbn) {
        if (tcbn == null) {
            return;
        }
        tcbn.addStateChangedListener(this);
        for (TristateCheckBoxNode tmp : tcbn.getChildren()) {
            addListeners(tmp);
        }

    }

    private void down(TristateCheckBoxNode t, State state) {
        if (t == null) {
            return;
        }

        t.justSetState(state);
        List<TristateCheckBoxNode> tmps = t.getChildren();
        for (TristateCheckBoxNode tcb : tmps) {
            down(tcb, state);
        }
    }

    public void stateChanged(TristateCheckBoxNode t, State oState, State nState) {
        if (t == null) {
            throw new NullPointerException();
        }
        // t.removeStateChangedListener(this);

        State s = changeState(t, nState);

        t.justSetState(s);
        up((TristateCheckBoxNode) t.getParent());
    }

    // for (TristateCheckBoxNode tcbn : t.getChildren()) {
    // down(tcbn, nState);
    //
    // }
    // up((TristateCheckBoxNode) t.getParent());
    // t.addStateChangedListener(this);
    private State changeState(TristateCheckBoxNode t, State state) {
        if (t == null || state == null) {
            throw new NullPointerException();
        }
        List<TristateCheckBoxNode> ts = t.getChildren();
        int selectedI = 0;
        int notSelectedI = 0;
        if (ts == null || ts.size() == 0) {
            t.justSetState(state);
            return t.getState();
        }

        for (TristateCheckBoxNode tmp : t.getChildren()) {
            State s = changeState(tmp, state);
            if (s == State.NOT_SELECTED) {
                notSelectedI++;
            }
            if (s == State.SELECTED) {
                selectedI++;
            }
        }

        if (notSelectedI == t.getChildCount()) {
            t.justSetState(State.NOT_SELECTED);
        } else if (selectedI == t.getChildCount()) {
            t.justSetState(State.SELECTED);
        } else {
            t.justSetState(State.DONT_CARE);
        }

        return t.getState();
    }

    private void up(TristateCheckBoxNode t) {
        if (t == null) {
            return;
        }
        int selectedI = 0;
        int notSelectedI = 0;

        for (TristateCheckBoxNode tcb : t.getChildren()) {
            if (tcb.getState() == State.NOT_SELECTED) {
                notSelectedI++;
            }
            if (tcb.getState() == State.SELECTED) {
                selectedI++;
            }
        }
        if (notSelectedI == t.getChildCount()) {
            t.justSetState(State.NOT_SELECTED);
        } else if (selectedI == t.getChildCount()) {
            t.justSetState(State.SELECTED);
        } else {
            t.justSetState(State.DONT_CARE);
        }

        up((TristateCheckBoxNode) t.getParent());
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent me) {
        TreePath path = getPathForLocation(me.getX(), me.getY());
        if (path == null) {
            return;
        }
        if (me.getX() > getPathBounds(path).x + hotspot) {
            return;
        }

        ((TristateCheckBoxNode) path.getLastPathComponent()).nextState();
        revalidate();
        updateUI();

    }

    public void mouseReleased(MouseEvent e) {

    }
}
