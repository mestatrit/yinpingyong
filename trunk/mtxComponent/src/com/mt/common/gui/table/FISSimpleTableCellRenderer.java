package com.mt.common.gui.table;

import com.mt.common.gui.ColorLib;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * FIS表格单元格表现器
 */
public class FISSimpleTableCellRenderer extends DefaultTableCellRenderer {

    private Color selected_color = ColorLib.FIS_SELECTED_BGCOLOR;
    private Color selected_textColor = ColorLib.FIS_SELECTED_TEXT_COLOR;

    private Color even = ColorLib.FIS_EVEN_ROW_COLOR;
    private Color odd = ColorLib.FIS_ODD_ROW_COLOR;
    private Color textColor = ColorLib.FIS_TEXT_COLOR;

    private int alignment;

    public FISSimpleTableCellRenderer() {
        this(ColorLib.FIS_EVEN_ROW_COLOR, ColorLib.FIS_ODD_ROW_COLOR, SwingConstants.LEFT);
    }

    /**
     * @param alignment
     */
    public FISSimpleTableCellRenderer(int alignment) {
        this(ColorLib.FIS_EVEN_ROW_COLOR, ColorLib.FIS_ODD_ROW_COLOR, alignment);
    }

    /**
     * @param even
     * @param odd
     * @param alignment
     */
    public FISSimpleTableCellRenderer(Color even, Color odd, int alignment) {
        this.even = even;
        this.odd = odd;
        this.alignment = alignment;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected) {
            cell.setBackground(selected_color);
            cell.setForeground(selected_textColor);
        } else {
            cell.setForeground(textColor);
            if (row % 2 == 0) {
                cell.setBackground(even);
            } else {
                cell.setBackground(odd);
            }
        }
        cell.setHorizontalAlignment(alignment);
        return cell;
    }
}
