/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ActionMapUIResource;
import java.awt.event.*;

/**
 * 三态CheckBox
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-28
 */
public class MTXTristateCheckBox extends JCheckBox {

    public static class State {

        private State() {
        }
    }

    public static final State NOT_SELECTED = new State();
    public static final State SELECTED = new State();
    public static final State DONT_CARE = new State();
    private final TristateDecorator model;

    public MTXTristateCheckBox(String text, Icon icon, State initial) {
        super(text, icon);

        super.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                grabFocus();
                model.nextState();
            }
        });
        ActionMap map = new ActionMapUIResource();
        map.put("pressed", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                grabFocus();
                model.nextState();
            }
        });
        map.put("released", null);
        SwingUtilities.replaceUIActionMap(this, map);
        model = new TristateDecorator(getModel());
        setModel(model);
        setState(initial);
    }

    public MTXTristateCheckBox(String text, State initial) {
        this(text, null, initial);
    }

    public MTXTristateCheckBox(String text) {
        this(text, DONT_CARE);
    }

    public MTXTristateCheckBox() {
        this(null);
    }

    public void addMouseListener(MouseListener l) {
    }

    public void setState(State state) {
        model.setState(state);
    }

    public State getState() {
        return model.getState();
    }

    public void setSelected(boolean b) {
        if (b) {
            setState(SELECTED);
        } else {
            setState(NOT_SELECTED);
        }
    }

    private class TristateDecorator implements ButtonModel {

        private final ButtonModel other;

        private TristateDecorator(ButtonModel other) {
            this.other = other;
        }

        private void setState(State state) {
            if (state == NOT_SELECTED) {
                other.setArmed(false);
                setPressed(false);
                setSelected(false);
            } else if (state == SELECTED) {
                other.setArmed(false);
                setPressed(false);
                setSelected(true);
            } else { // either "null" or DONT_CARE
                other.setArmed(true);
                setPressed(true);
                setSelected(true);
            }
        }

        private State getState() {
            if (isSelected() && !isArmed()) {
                return SELECTED;
            } else if (isSelected() && isArmed()) {
                return DONT_CARE;
            } else {
                return NOT_SELECTED;
            }
        }

        private void nextState() {
            State current = getState();
            if (current == NOT_SELECTED) {
                setState(SELECTED);
            } else if (current == SELECTED) {
                setState(DONT_CARE);
            } else if (current == DONT_CARE) {
                setState(NOT_SELECTED);
            }
        }

        public void setArmed(boolean b) {
        }

        public void setEnabled(boolean b) {
            setFocusable(b);
            other.setEnabled(b);
        }

        public boolean isArmed() {
            return other.isArmed();
        }

        public boolean isSelected() {
            return other.isSelected();
        }

        public boolean isEnabled() {
            return other.isEnabled();
        }

        public boolean isPressed() {
            return other.isPressed();
        }

        public boolean isRollover() {
            return other.isRollover();
        }

        public void setSelected(boolean b) {
            other.setSelected(b);
        }

        public void setPressed(boolean b) {
            other.setPressed(b);
        }

        public void setRollover(boolean b) {
            other.setRollover(b);
        }

        public void setMnemonic(int key) {
            other.setMnemonic(key);
        }

        public int getMnemonic() {
            return other.getMnemonic();
        }

        public void setActionCommand(String s) {
            other.setActionCommand(s);
        }

        public String getActionCommand() {
            return other.getActionCommand();
        }

        public void setGroup(ButtonGroup group) {
            other.setGroup(group);
        }

        public void addActionListener(ActionListener l) {
            other.addActionListener(l);
        }

        public void removeActionListener(ActionListener l) {
            other.removeActionListener(l);
        }

        public void addItemListener(ItemListener l) {
            other.addItemListener(l);
        }

        public void removeItemListener(ItemListener l) {
            other.removeItemListener(l);
        }

        public void addChangeListener(ChangeListener l) {
            other.addChangeListener(l);
        }

        public void removeChangeListener(ChangeListener l) {
            other.removeChangeListener(l);
        }

        public Object[] getSelectedObjects() {
            return other.getSelectedObjects();
        }
    }
}
