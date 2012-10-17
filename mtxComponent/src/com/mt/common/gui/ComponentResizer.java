package com.mt.common.gui;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

/**
 * 组件Resizer主要是表格
 *
 * @author hanhui
 */
public class ComponentResizer {

    public static final int DefaultMaxW = 5000;
    public static final int DefaultSpace = 8;

    /**
     * 调整表格的列宽与内容一致
     *
     * @param table
     */
    public static int resizeTableColumnWithContent(final JTable table) {
        return resizeTableColumnWithContent(table, DefaultMaxW);

    }

    /**
     * 调整表格的列宽与内容一致
     *
     * @param table
     * @param wMax,列宽的最大尺寸
     */
    public static int resizeTableColumnWithContent(final JTable table, int wMax) {
        return resizeTableColumnWithContent(table, wMax, new ArrayList<String>());
    }

    public static int resizeTableColumnWithContent(final JTable table, ArrayList<String> unCol) {
        return resizeTableColumnWithContent(table, DefaultMaxW, unCol);
    }

    /**
     * @param table
     * @param wMax
     * @param unCol
     */
    public static int resizeTableColumnWithContent(final JTable table, int wMax, ArrayList<String> unCol) {
        int cols = table.getColumnCount();
        int rows = table.getRowCount();

        int width = 0;

        for (int i = 0; i < cols; i++) {
            int rowMax = 0;
            String colName = table.getModel().getColumnName(i);
            if (unCol.contains(colName)) {
                continue;
            }

            int modelIndex = table.getTableHeader().getColumnModel().getColumn(i).getModelIndex();
            Object hValue = table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue();
            Component comH = table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, hValue, false, false,
                    -1, i);

            for (int j = 0; j < rows; j++) {
                Object value = table.getModel().getValueAt(j, modelIndex);
                TableCellRenderer renderer = table.getCellRenderer(j, i);
                if (renderer != null) {
                    Component com = renderer.getTableCellRendererComponent(
                            table, value, false, false, j, i);
                    rowMax = rowMax <= com.getPreferredSize().width ? com.getPreferredSize().width : rowMax;
                }
            }

            rowMax = rowMax < comH.getPreferredSize().width ? comH.getPreferredSize().width : rowMax;

            rowMax = rowMax > wMax ? wMax : rowMax;
            // System.err.println("rowMax="+rowMax);
            table.getColumnModel().getColumn(i).setPreferredWidth(rowMax + DefaultSpace);
            table.getColumnModel().getColumn(i).setWidth(rowMax + DefaultSpace);

            //只是控制Resize的时候最大值不是控制这个列的最大值，还是要允许用户调整列的
            //table.getColumnModel().getColumn(i).setMaxWidth(wMax);
            table.getColumnModel().getColumn(i).setMinWidth(0);
            table.getColumnModel().getColumn(i).setResizable(true);

            width += rowMax + DefaultSpace;
        }
        return width;

        //这一段没有必要而且还可能导致流程不同步
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				table.revalidate();
//				table.repaint();
//			}
//		});
    }

    /**
     * 根据内容和父容器的大小调整列宽
     *
     * @param table
     */
    public static void resizeTableColumnWithCC(final JTable table) {
        resizeTableColumnWithContent(table);
        changeTableResizeMode(table);
    }

    /**
     * 根据内容和父容器的大小调整列宽,可以指定最大值
     *
     * @param table
     * @param max
     */
    public static void resizeTableColumnWithCC(final JTable table, int max) {
        resizeTableColumnWithContent(table, max);
        changeTableResizeMode(table);
    }

    public static void changeTableResizeMode(JTable table) {
        if (table.getParent() == null) {
            return;
        }
        int pw;
        if (table.getParent().getParent() == null) {
            pw = table.getParent().getWidth();
        } else {
            pw = table.getParent().getParent().getWidth();
        }

        int cw = 0;
        int count = table.getColumnCount();
        for (int i = 0; i < count; i++) {
            cw += table.getColumnModel().getColumn(i).getPreferredWidth();
        }
        if (cw > pw) {
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        } else {
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        }
    }

    /**
     * 在现有的基础上调整表格某一列的头部
     *
     * @param table
     * @param col
     */
    static public void resizeTableHeader(final JTable table, int col) {
        Object hValue = table.getTableHeader().getColumnModel().getColumn(col).getHeaderValue();
        Component comH = table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, hValue, false, false,
                -1, col);

        int w = table.getColumnModel().getColumn(col).getPreferredWidth();
        w = comH.getPreferredSize().width + DefaultSpace > w ? comH.getPreferredSize().width + DefaultSpace : w;
        table.getColumnModel().getColumn(col).setPreferredWidth(w);

        //这一段没有必要而且还可能导致流程不同步
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				table.revalidate();
//				table.repaint();
//			}
//		});
    }

    /**
     * 针对公告表格的Resize
     *
     * @param table
     */
    public static void resizeBulletnTable(final JTable table) {
        int cols = table.getColumnCount();
        int rows = table.getRowCount();
        int allW = 0;
        for (int i = 0; i < cols; i++) {
            int rowMax = 0;
            for (int j = 0; j < rows; j++) {
                Object value = table.getModel().getValueAt(j, i);
                TableCellRenderer renderer = table.getCellRenderer(j, i);
                if (renderer != null) {
                    Component com = renderer.getTableCellRendererComponent(
                            table, value, false, false, j, i);
                    rowMax = rowMax <= com.getPreferredSize().width ? com.getPreferredSize().width : rowMax;
                }
            }
            Object hValue = table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue();
            Component comH = table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, hValue, false, false,
                    -1, i);

            rowMax = rowMax < comH.getPreferredSize().width ? comH.getPreferredSize().width : rowMax;

            rowMax = rowMax > DefaultMaxW ? DefaultMaxW : rowMax;
            // System.err.println(rowMax);
            allW += rowMax;
            table.getColumnModel().getColumn(i).setPreferredWidth(rowMax + DefaultSpace);
        }
        int tableW = Toolkit.getDefaultToolkit().getScreenSize().width - 200;
        //int tableW = table.getPreferredSize().width - 50;
        if (allW > tableW) {
            int v = allW - tableW;
            int w = table.getColumnModel().getColumn(3).getPreferredWidth();
            table.getColumnModel().getColumn(3).setPreferredWidth(w - v);
        }
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                table.revalidate();
                table.repaint();
            }
        });
    }

    /**
     * 展开树
     *
     * @param tree
     */
    public static void expandTree(JTree tree) {
        int count = tree.getRowCount();
        for (int i = 0; i < count; i++) {
            tree.expandRow(i);

            count = tree.getRowCount();
        }
    }

    /**
     * 折叠树
     *
     * @param tree
     */
    public static void collapseHisTree(JTree tree) {
        for (int i = tree.getRowCount(); i >= 0; i--) {
            tree.collapseRow(i);
        }
        tree.expandRow(1);
        tree.expandRow(0);
    }

    /**
     * 实时图的折叠树
     *
     * @param tree
     */
    public static void collapseTree(JTree tree) {
        for (int i = tree.getRowCount(); i >= 0; i--) {
            tree.collapseRow(i);
        }
        tree.expandRow(1);
        tree.expandRow(2);
        tree.expandRow(4);
    }

    public static void expandTree_one(JTree tree) {
        tree.expandRow(0);
    }

    /**
     * 设置某一列不可见
     *
     * @param table
     * @param col
     */
    public static void setColumnInvisible(JTable table, int col) {
        table.getColumnModel().getColumn(col).setMaxWidth(0);
        table.getColumnModel().getColumn(col).setMinWidth(0);
        table.getColumnModel().getColumn(col).setPreferredWidth(0);
    }

    public static void scrollToVisible(JTable table, int rowIndex, int vColIndex) {
        if (!(table.getParent() instanceof JViewport)) {
            return;
        }
        JViewport viewport = (JViewport) table.getParent();
        // This rectangle is relative to the table where the
        // northwest corner of cell (0,0) is always (0,0).
        Rectangle rect = table.getCellRect(rowIndex, vColIndex, true);
        // The location of the viewport relative to the table
        Point pt = viewport.getViewPosition();
        // Translate the cell location so that it is relative
        // to the view, assuming the northwest corner of the
        // view is (0,0)
        rect.setLocation(rect.x - pt.x, rect.y - pt.y);
        // Scroll the area into view
        viewport.scrollRectToVisible(rect);

    }

}
