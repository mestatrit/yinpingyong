package com.mt.common.gui.table;

import com.mt.common.gui.ColorLib;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * 通用的简单表格单元格表现器
 */
public class SimpleTableCellRenderer extends DefaultTableCellRenderer {

    private Color even;

    private Color odd;

    private int alignment;

    private boolean isOE = true;

    public SimpleTableCellRenderer() {
        //this(ColorLib.tableColor4, ColorLib.tableColor3, SwingConstants.LEFT);
        this(ColorLib.TRow_EvenColor, ColorLib.TRow_OddColor, SwingConstants.LEFT);
    }

    /**
     * @param alignment
     */
    public SimpleTableCellRenderer(int alignment) {
        //this(ColorLib.tableColor4, ColorLib.tableColor3, alignment);
        this(ColorLib.TRow_EvenColor, ColorLib.TRow_OddColor, alignment);
    }

    /**
     * @param even
     * @param odd
     * @param alignment
     */
    public SimpleTableCellRenderer(Color even, Color odd, int alignment) {
        this.even = even;
        this.odd = odd;
        this.alignment = alignment;
    }

    public void setOdd_Even(boolean o) {
        this.isOE = o;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel cell = (JLabel) super.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, column);
        if (!isSelected) {
            if (isOE) {
                if (row % 2 == 0) {
                    cell.setBackground(even);
                    cell.setForeground(Color.BLACK);
                } else {
                    cell.setBackground(odd);
                    cell.setForeground(Color.BLACK);
                }
            } else {
                cell.setBackground(Color.WHITE);
                cell.setForeground(Color.BLACK);
            }
        }
        
        cell.setHorizontalAlignment(alignment);
        return cell;
    }
}
