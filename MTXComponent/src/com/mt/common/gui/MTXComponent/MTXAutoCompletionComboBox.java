/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import javax.swing.*;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * 模糊比对组合框
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-7
 */
public class MTXAutoCompletionComboBox extends MTXComboBox {

    private List<?> items;
    private List<FObject> fObjects;
    private TextEditor textEditor;
    private List<AutoCActionListener> actionListener = new ArrayList<AutoCActionListener>();
    private Object selItem = null;
    private boolean isAcceptEditData = false;
    private JList popupList;
    private JComponent srcComponent;
    private boolean isVK_ENTERPressed = false;
    private FocusListener textFocusListener;

    public MTXAutoCompletionComboBox() {
        this(new ArrayList());
    }

    public MTXAutoCompletionComboBox(Object[] items) {
        this(new ArrayList(Arrays.asList(items)));
    }

    public MTXAutoCompletionComboBox(List<?> items) {
        this.items = items;
        if (this.items != null) {
            createFObjectList(this.items);
        }
        init();
    }

    private void init() {
        this.textEditor = new TextEditor();
        this.setEditor(textEditor);
        this.setEditable(true);
        this.setFItems(items);
        this.textEditor.addEditorKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!isVK_ENTERPressed) {//上一次没有被按下
                        isVK_ENTERPressed = true;
                        selItem = popupList.getSelectedValue();
                        setEditorSelectedValue();
                        notifyAutoCActionListener();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP
                        || e.getKeyCode() == KeyEvent.VK_DOWN
                        || e.getKeyCode() == KeyEvent.VK_LEFT
                        || e.getKeyCode() == KeyEvent.VK_RIGHT
                        || e.getKeyCode() == KeyEvent.VK_CAPS_LOCK
                        || e.getKeyCode() == KeyEvent.VK_TAB
                        || e.getKeyCode() == KeyEvent.VK_PAGE_UP
                        || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                    return;
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    isVK_ENTERPressed = false;
                    return;
                } else {
                    String ev = textEditor.getText();
                    filtrateData(ev, true);
                }
            }
        });

        textFocusListener = new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                textEditor.selectAll();
                //JCcomboBox处于可编辑状态后，编辑器获得焦点后弹出菜单不会消失
                if (!isPopupVisible()) {
                    MenuSelectionManager.defaultManager().clearSelectedPath();
                    //当你隐藏当前菜单时，为了处理焦点问题才有了下面这段
                    textEditor.removeFocusListener(textFocusListener);
                    textEditor.requestFocus();
                    textEditor.addFocusListener(textFocusListener);
                }
                if (selItem != null) {
                    popupList.setSelectedValue(selItem, true);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                isVK_ENTERPressed = false;
                if (selItem != null) {
                    if (!selItem.toString().equals(getEditData())) {
                        //当前文本框内容不匹配任何项的数据
                        selItem = null;
                    }
                }
            }
        };
        textEditor.addFocusListener(textFocusListener);

        ComboPopup popup = (ComboPopup) this.getUI().getAccessibleChild(
                this, 0);
        popupList = popup.getList();
        popupList.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                selItem = popupList.getSelectedValue();
                setEditorSelectedValue();
                notifyAutoCActionListener();
            }
        });

        JButton arrowButton = findArrowButton();

        if (arrowButton != null) {
            arrowButton.removeMouseListener(popup.getMouseListener());
            arrowButton.addMouseListener(new ArrowButtonListener(popup.getMouseListener()));
        }
        setMinimumSize(new Dimension(0, 0));
    }

    private void filtrateData(String ev, boolean isShowPopup) {
        if (ev.equals("")) {
            setFItems(items);
            if (isShowPopup) {
                showPopup();
            }
        } else {
            List fList = new ArrayList();
            for (FObject fObj : fObjects) {
                if (fObj.contains(ev)) {
                    fList.add(fObj.getInternalObject());
                }
            }
            setFItems(fList);
            if (!fList.isEmpty()) {
                if (isShowPopup) {
                    showPopup();
                }
            }
        }
    }

    /**
     * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4618607
     * 针对Popup大小固定问题一个轻巧的解决办法
     */
    private boolean layingOut = false;

    @Override
    public void doLayout() {
        try {
            layingOut = true;
            super.doLayout();
        } finally {
            layingOut = false;
        }
    }

    @Override
    public Dimension getSize() {
        Dimension dim = super.getSize();
        if (!layingOut && isAutoWide()) {
            dim.width = Math.max(dim.width, getPreferredSize().width);
        }
        return dim;
    }

    private boolean wide = true;

    public boolean isAutoWide() {
        return wide;
    }

    public void setAutoWide(boolean wide) {
        this.wide = wide;
    }

    private void notifyAutoCActionListener() {
        for (AutoCActionListener listener : actionListener) {
            try {
                if (srcComponent != null) {
                    listener.onAutoCAction(srcComponent, getAutoCSelectedItem());
                } else {
                    listener.onAutoCAction(this, getAutoCSelectedItem());
                }
            } catch (Exception ex) {
            }
        }
    }

    public void setSrcComponent(JComponent com) {
        this.srcComponent = com;
    }

    public void addAutoCActionListener(AutoCActionListener listener) {
        actionListener.add(listener);
    }

    public void removeAutoCActionListener(AutoCActionListener listener) {
        actionListener.remove(listener);
    }

    public void setAcceptEditData(boolean isedit) {
        this.isAcceptEditData = isedit;
    }

    public boolean isAcceptEditData() {
        return this.isAcceptEditData;
    }

    public String getEditData() {
        return textEditor.getText();
    }

    public void setAutoCSelectedItem(Object sItem) {
        if (sItem == null) {
            selItem = sItem;
            this.popupList.clearSelection();
            this.textEditor.setEditorValue(null);
        } else {
            boolean isfound = false;
            for (Object item : items) {
                if (item != null && item.equals(sItem)) {
                    isfound = true;
                    sItem = item;
                    break;
                }
            }
            selItem = isfound ? sItem : null;
            if (selItem != null) {
                this.popupList.setSelectedValue(selItem, isfound);
                this.textEditor.setEditorValue(selItem);
            } else {
                this.popupList.clearSelection();
                if (isAcceptEditData()) {
                    this.textEditor.setEditorValue(sItem);
                } else {
                    this.textEditor.setEditorValue(null);
                }
            }
        }
    }

    public void setAutoCSelectedItemWithAction(Object sItem) {
        setAutoCSelectedItem(sItem);
        notifyAutoCActionListener();
    }

    public Object getAutoCSelectedItem() {
        if (selItem != null) {
            return selItem;
        } else {
            if (isAcceptEditData()) {
                return this.textEditor.getEditorValue();
            } else {
                return null;
            }
        }
    }

    public void setAutoCSelectedIndex(int index) {
        if (index < 0) {
            setAutoCSelectedItem(null);
        } else {
            if (items != null) {
                setAutoCSelectedItem(this.items.get(index));
            }
        }
    }

    public int getAutoCSelectedIndex() {
        Object o = getAutoCSelectedItem();
        if (o == null) {
            return -1;
        }
        return this.items.indexOf(o);
    }

    public Object getAutoCItemAt(int index) {
        return items == null ? null : this.items.get(index);
    }

    public int getAutoCItemCount() {
        return items == null ? 0 : this.items.size();
    }

    /**
     * 获得全部的数据
     *
     * @return
     */
    @Override
    final public List getListItem() {
        return this.items;
    }

    /**
     * 设置新的数据
     *
     * @param items
     */
    @Override
    final public void setListItem(List<?> items) {
        this.items = items;
        createFObjectList(this.items);
        selItem = null;
        this.popupList.clearSelection();
        setEditorSelectedValue();
    }

    /**
     * 设置新的数据
     * 如果原被选中的数据在新的数据列表中还存在那么仍然设置为选中
     *
     * @param items
     */
    final public void setListItem_OldSel(List<?> items) {
        if (this.items == null) {
            this.items = items;
            createFObjectList(this.items);
            return;
        } else {
            this.items = items;
            createFObjectList(this.items);
            if (selItem != null) {
                boolean isfound = false;
                for (Object item : items) {
                    if (item.equals(selItem)) {
                        isfound = true;
                        break;
                    }
                }
                selItem = isfound ? selItem : null;
                if (selItem == null) {
                    this.popupList.clearSelection();
                } else {
                    this.popupList.setSelectedValue(selItem, true);
                }
                setEditorSelectedValue();
            }
        }
    }

    private void createFObjectList(List<?> items) {
        this.fObjects = new ArrayList<FObject>();
        for (Object item : items) {
            fObjects.add(new FObject(item));
        }
    }

    /**
     * 获得当前被过滤的数据
     *
     * @return
     */
    public List getCurFListItem() {
        ComboBoxModel model = this.getModel();
        List rs = new ArrayList();
        for (int i = 0, j = model.getSize(); i < j; i++) {
            rs.add(model.getElementAt(i));
        }
        return rs;
    }

    private void setEditorSelectedValue() {
        if (selItem != null) {
            textEditor.setEditorValue(selItem);
            textEditor.selectAll();
        } else {
            if (!isAcceptEditData()) {
                textEditor.setEditorValue(null);
            }
        }
    }

    private void setFItems(List<?> items) {
        setFItems(items, items.isEmpty() ? null : items.get(0));
    }

    private void setFItems(List<?> items, Object sel) {
        DefaultComboBoxModel dModel = new DefaultComboBoxModel(new Vector(items));
        dModel.setSelectedItem(sel);
        this.setModel(dModel);
    }

    private JButton findArrowButton() {
        for (int i = 0, n = this.getComponentCount(); i < n; i++) {
            final Component comp = this.getComponent(i);
            if (comp instanceof JButton) {
                return (JButton) comp;
            }
        }
        return null;
    }

    class TextEditor extends JTextField implements ComboBoxEditor {

        public TextEditor() {
            this.setBorder(null);
            this.setFocusable(false);
            this.setPreferredSize(this.getPreferredSize());
        }

        @Override
        public Component getEditorComponent() {
            return this;
        }

        @Override
        public void setItem(Object anObject) {
        }

        @Override
        public Object getItem() {
            return this.getText();
        }

        public Object getEditorValue() {
            return getText().equals("") ? null : getText();
        }

        public void setEditorValue(Object value) {
            this.setText(value == null ? "" : value.toString());
        }

        @Override
        public void selectAll() {
            super.selectAll();
        }

        @Override
        public void addActionListener(ActionListener l) {
        }

        @Override
        public void removeActionListener(ActionListener l) {
        }

        public void addEditorKeyListener(KeyListener listener) {
            super.addKeyListener(listener);
        }

        public void removeEditorKeyListener(KeyListener listener) {
            super.removeKeyListener(listener);
        }

        @Override
        public void addKeyListener(KeyListener listener) {
        }

        @Override
        public void removeKeyListener(KeyListener listener) {
        }
    }

    class ArrowButtonListener implements MouseListener {

        private final MouseListener decorated;

        public ArrowButtonListener(MouseListener decorated) {
            this.decorated = decorated;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            decorated.mouseClicked(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            boolean isPopupVisible = isPopupVisible() ? true : false;

            //设置全部数据
            ActionListener[] ls = MTXAutoCompletionComboBox.super.getActionListeners();
            removeAllActionListeners(ls);
            setFItems(items, selItem);
            addAllActionListeners(ls);
            decorated.mousePressed(e);
            if (isPopupVisible) {
                hidePopup();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            decorated.mouseReleased(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            decorated.mouseEntered(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            decorated.mouseExited(e);
        }
    }

    private void removeAllActionListeners(ActionListener[] listeners) {
        for (int i = 0; i < listeners.length; i++) {
            super.removeActionListener(listeners[i]);
        }
    }

    private void addAllActionListeners(ActionListener[] listeners) {
        for (int i = 0; i < listeners.length; i++) {
            super.addActionListener(listeners[i]);
        }
    }
}
