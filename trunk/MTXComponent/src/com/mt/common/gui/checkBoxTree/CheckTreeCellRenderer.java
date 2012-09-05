package com.mt.common.gui.checkBoxTree;

import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * CheckTree单元格表现器
 */
public class CheckTreeCellRenderer extends DefaultTreeCellRenderer {

    private JCheckBox checkBox;
    private boolean isView;
    static final private ImageIcon s1 = new ImageIcon(CheckTreeCellRenderer.class.getResource("/s1.PNG"));
    static final private ImageIcon s2 = new ImageIcon(CheckTreeCellRenderer.class.getResource("/s2.PNG"));
    static final private ImageIcon s3 = new ImageIcon(CheckTreeCellRenderer.class.getResource("/s3.PNG"));

    public CheckTreeCellRenderer(boolean isView) {
        this.isView = isView;
        checkBox = new JCheckBox();
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {
        Component renderer = super.getTreeCellRendererComponent(tree, value,
                selected, expanded, leaf, row, hasFocus);

        if (value instanceof TristateCheckBoxNode) {
            Object o = ((TristateCheckBoxNode) value).getUserObject();
            State state = ((TristateCheckBoxNode) value).getState();
            if (o != null) {
                if (o instanceof Element) {
                    checkBox.setText(((Element) o).getAttribute("name"));
                } else {
                    checkBox.setText(o.toString());
                }
            }
            checkBox.setEnabled(true);
            if (!((TristateCheckBoxNode) value).isEditable()) {
                checkBox.setEnabled(false);
            }
            if (state != null) {
                if (state == State.SELECTED) {
                    checkBox.setIcon(s2);
                } else if (state == State.NOT_SELECTED) {
                    checkBox.setIcon(s3);
                } else {
                    checkBox.setIcon(s1);
                }
            }
            checkBox.setBackground(Color.WHITE);

            return checkBox;
        } else {
            return renderer;
        }
    }
}
