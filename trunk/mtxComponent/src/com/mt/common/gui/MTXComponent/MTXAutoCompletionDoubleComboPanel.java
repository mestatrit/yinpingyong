/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import com.mt.common.gui.CommonIconDef;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 模糊比对双组合面板
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-20
 */
public class MTXAutoCompletionDoubleComboPanel extends JPanel {

    private MTXAutoCompletionComboPanel autoPanel;
    private DropListBox dropListBox;
    private List autoDataList;
    private FiltrationListProvider flProvider;
    private DropItemProvider dropProvider;

    public MTXAutoCompletionDoubleComboPanel() {
        this((List) null, (List) null);
    }

    public MTXAutoCompletionDoubleComboPanel(Object[] dlItems, Object[] autoCItems) {
        this(new ArrayList(Arrays.asList(dlItems)), new ArrayList(Arrays.asList(autoCItems)));
    }

    public MTXAutoCompletionDoubleComboPanel(List dlItems, List autoCItems) {
        initView();
        setShortDisplayProvider(new DefaultShortDisplayProvider());
        setFiltrationListProvider(new DefaultFiltrationListProvider());
        setDropItemProvider(new DefaultDropItemProvider());
        autoPanel.setSrcComponent(this);
        if (dlItems != null) {
            setDropListItem(dlItems);
        }
        if (autoCItems != null) {
            setAutoCDataList(autoCItems);
        }
        setMinimumSize(new Dimension(0, 0));
    }

    final public void setIconVisible(boolean isV) {
        autoPanel.setVisible(isV);
    }

    final public boolean isIconVisible() {
        return autoPanel.isVisible();
    }

    final void setAcceptEditData(boolean isedit) {
        autoPanel.setAcceptEditData(isedit);
    }

    final public boolean isAcceptEditData() {
        return autoPanel.isAcceptEditData();
    }

    final public String getEditData() {
        return autoPanel.getEditData();
    }

    final public void setDropListItem(List selList) {
        dropListBox.setDropListItem(selList);
    }

    final public void setDropListItem(List selList, int selIndex) {
        dropListBox.setDropListItem(selList, selIndex);
    }

    final public void setDropListItem(List selList, Object selObj) {
        dropListBox.setDropListItem(selList, selObj);
    }

    final public List getDropListItem() {
        return dropListBox.getDropListItem();
    }

    final public void setDropListSelectedItem(Object item) {
        dropListBox.setSelectedItem(item);
    }

    final public Object getDropListSelectedItem() {
        return dropListBox.getSelectedItem();
    }

    final public void setAutoCDataList(List autoDataList) {
        this.autoDataList = autoDataList;
        filtrateAutoList();
    }

    final public List getAutoCDataList() {
        return autoDataList;
    }

    final public void setAutoCSelectedIndex(int index) {
        autoPanel.setAutoCSelectedIndex(index);
    }

    final public int getAutoCSelectedIndex() {
        return autoPanel.getAutoCSelectedIndex();
    }

    final public void setAutoCSelectedItem(Object item) {
        procDropItem(item);
        autoPanel.setAutoCSelectedItem(item);
    }

    final public void setAutoCSelectedItemWithAction(Object item) {
        procDropItem(item);
        autoPanel.setAutoCSelectedItemWithAction(item);
    }

    final public Object getAutoCSelectedItem() {
        return autoPanel.getAutoCSelectedItem();
    }

    final public Object getAutoCItemAt(int index) {
        return autoPanel.getAutoCItemAt(index);
    }

    final public int getAutoCItemCount() {
        return autoPanel.getAutoCItemCount();
    }

    final public void addAutoCActionListener(AutoCActionListener listener) {
        autoPanel.addAutoCActionListener(listener);
    }

    final public void removeAutoCActionListener(AutoCActionListener listener) {
        autoPanel.removeAutoCActionListener(listener);
    }

    final public void setShortDisplayProvider(ShortDisplayProvider sdProvider) {
        dropListBox.setShortDisplayProvider(sdProvider);
    }

    final public void setFiltrationListProvider(FiltrationListProvider flProvider) {
        this.flProvider = flProvider;
    }

    final public void setDropItemProvider(DropItemProvider dropProvider) {
        this.dropProvider = dropProvider;
    }

    public void putBoxClientProperty(Object k, Object v) {
        autoPanel.putClientProperty(k, v);
    }

    private void procDropItem(Object value) {
        Object selDropItem = getDropListSelectedItem();
        Object dropItem = dropProvider.getDropItem(value, getDropListItem());
        if (dropItem != null) {
            if (!selDropItem.equals(dropItem)) {
                setDropListSelectedItem(dropItem);
            }
        }
    }

    private void filtrateAutoList() {
        if (getDropListSelectedItem() != null) {
            autoPanel.setListItem(flProvider.getFiltrationList(getDropListSelectedItem(), autoDataList != null ? autoDataList : new ArrayList()));
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        dropListBox.setEnabled(enabled);
        autoPanel.setEnabled(enabled);
    }

    public boolean isAutoWide() {
        return autoPanel.isAutoWide();
    }

    public void setAutoWide(boolean wide) {
        autoPanel.setAutoWide(wide);
    }

    private void initView() {
        this.setLayout(new BorderLayout());
        dropListBox = new DropListBox();
        autoPanel = new MTXAutoCompletionComboPanel();
        this.setBorder(autoPanel.getBorder());
        autoPanel.setBorder(null);
        JSeparator sp = new JSeparator(SwingConstants.VERTICAL);
        JPanel selPanel = new JPanel(new BorderLayout(1, 0));
        this.setBackground(autoPanel.getBackground());
        selPanel.setBackground(autoPanel.getBackground());
        selPanel.add(Box.createHorizontalStrut(1), BorderLayout.WEST);
        selPanel.add(dropListBox, BorderLayout.CENTER);
        selPanel.add(sp, BorderLayout.EAST);
        this.add(selPanel, BorderLayout.WEST);
        this.add(autoPanel, BorderLayout.CENTER);
    }

    class DropListBox extends JLabel {

        final int X = -2;
        private JPopupMenu popupList = new JPopupMenu();
        private ShortDisplayProvider sdProvider;
        private Object selItem;

        public DropListBox() {
            this.setHorizontalTextPosition(JLabel.LEFT);
            this.setIconTextGap(0);
            this.setIcon(CommonIconDef.DropDownIcon);
            this.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    if (isEnabled()) {
                        popupList.show(DropListBox.this, X, DropListBox.this.getHeight() + 1);
                    }
                }
            });
        }

        public void setShortDisplayProvider(ShortDisplayProvider sdProvider) {
            this.sdProvider = sdProvider;
        }

        public void setDropListItem(List items, Object selObj) {
            popupList.removeAll();
            ButtonGroup bg = new ButtonGroup();
            for (Object item : items) {
                DataCheckBoxMenuItem menuItem = new DataCheckBoxMenuItem(item);
                menuItem.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DataCheckBoxMenuItem s = (DataCheckBoxMenuItem) e.getSource();
                        selItem = s.getDataItem();
                        setText(sdProvider.getShortDisplay(selItem).toString());
                        filtrateAutoList();
                    }
                });
                bg.add(menuItem);
                popupList.add(menuItem);
            }
            setSelectedItem(selObj);
            popupList.pack();
        }

        public void setDropListItem(List items, int selIndex) {
            setDropListItem(items, items.get(selIndex));
        }

        public void setDropListItem(List items) {
            setDropListItem(items, items.get(0));
        }

        public List getDropListItem() {
            List rs = new ArrayList();
            int count = popupList.getComponentCount();
            for (int i = 0; i < count; i++) {
                Component com = popupList.getComponent(i);
                if (com instanceof DataCheckBoxMenuItem) {
                    Object data = ((DataCheckBoxMenuItem) com).getDataItem();
                    rs.add(data);
                }
            }
            return rs;
        }

        public void setSelectedItem(Object item) {
            int count = popupList.getComponentCount();
            for (int i = 0; i < count; i++) {
                Component com = popupList.getComponent(i);
                if (com instanceof DataCheckBoxMenuItem) {
                    Object data = ((DataCheckBoxMenuItem) com).getDataItem();
                    if (data.equals(item)) {
                        this.selItem = data;
                        ((DataCheckBoxMenuItem) com).setSelected(true);
                        this.setText(sdProvider.getShortDisplay(selItem).toString());
                        filtrateAutoList();
                        break;
                    }
                }
            }
        }

        public Object getSelectedItem() {
            return this.selItem;
        }

        class DataCheckBoxMenuItem extends JCheckBoxMenuItem {

            private Object item;

            public DataCheckBoxMenuItem(Object item) {
                super(item.toString());
                this.item = item;
            }

            public Object getDataItem() {
                return item;
            }
        }
    }

    class DefaultShortDisplayProvider implements ShortDisplayProvider {

        @Override
        public Object getShortDisplay(Object obj) {
            return obj.toString().substring(0, 1);
        }
    }

    class DefaultFiltrationListProvider implements FiltrationListProvider {

        @Override
        public List getFiltrationList(Object sel, List dataList) {
            List rs = new ArrayList();
            for (Object obj : dataList) {
                if (obj.toString().startsWith(sel.toString())) {
                    rs.add(obj);
                }
            }
            return rs;
        }
    }

    class DefaultDropItemProvider implements DropItemProvider {

        @Override
        public Object getDropItem(Object value, List dropItemList) {
            for (Object obj : dropItemList) {
                if (value.toString().startsWith(obj.toString())) {
                    return obj;
                }
            }
            return null;
        }
    }
}
