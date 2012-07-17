package com.mt.common.export;

import com.mt.core.functionDef.ViewFunction;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Excel（复制 粘贴）器
 *
 * @author hanhui
 *         <p/>
 */
public class ExcelCopyPaster implements ActionListener {

    private JTable table;
    private Clipboard system;
    private StringSelection stsel;
    private String rowstring, value;
    private boolean isCopy = true;
    private boolean isPaste = false;
    private boolean isShowPopupMenu = true;
    private JPopupMenu pMenu;
    private JMenuItem item_copy;
    private JMenuItem item_paste;
    private KeyStroke copy;
    private KeyStroke cut;
    private KeyStroke paste;

    public ExcelCopyPaster(JTable table) {
        this.table = table;
        this.table.addMouseListener(new CommandMenu());

        copy = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK,
                false);

        cut = KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK,
                false);

        paste = KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK,
                false);

//        if (isCopy) {
        table.registerKeyboardAction(this, "Copy", copy,
                JComponent.WHEN_FOCUSED);
        table.registerKeyboardAction(this, "Cut", cut,
                JComponent.WHEN_FOCUSED);
//        }
//
//        if (isPaste) {
        table.registerKeyboardAction(this, "Paste", paste,
                JComponent.WHEN_FOCUSED);
//        }
    }

    public void setJTable(JTable table) {
        this.table = table;
    }

    public JTable getJTable(JTable table) {
        return table;
    }

    public void setCopyEnable(boolean isCopy) {
        this.isCopy = isCopy;
        item_copy.setVisible(isCopy);
//        if (isCopy) {
//            table.registerKeyboardAction(this, "Copy", copy,
//                    JComponent.WHEN_FOCUSED);
//            table.registerKeyboardAction(this, "Cut", cut,
//                    JComponent.WHEN_FOCUSED);
//        } else {
//            table.unregisterKeyboardAction(copy);
//            table.unregisterKeyboardAction(cut);
//        }
    }

    public boolean getCopyEnable() {
        return isCopy;
    }

    public void setPasteEnable(boolean isPaste) {
        this.isPaste = isPaste;
        item_paste.setVisible(isPaste);
//        if (isPaste) {
//            table.registerKeyboardAction(this, "Paste", paste,
//                    JComponent.WHEN_FOCUSED);
//        } else {
//            table.unregisterKeyboardAction(paste);
//        }
    }

    public JPopupMenu getPopupMenu() {
        return this.pMenu;
    }

    public void setShowPopupMenu(boolean isShow) {
        this.isShowPopupMenu = isShow;
    }

    class CommandMenu extends MouseAdapter implements ActionListener {

        public CommandMenu() {
            pMenu = new JPopupMenu();
            if (isCopy) {
                item_copy = new JMenuItem("复制");
                item_copy.addActionListener(this);
                pMenu.add(item_copy);
            }

            if (isPaste) {
                item_paste = new JMenuItem("粘贴");
                item_paste.addActionListener(this);
                pMenu.add(item_paste);
            }
        }

        public void mousePressed(MouseEvent e) {
            if (isShowPopupMenu) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int clos = table.getSelectedColumnCount();
                    int rows = table.getSelectedRowCount();
                    if (rows == 0 && clos == 0) {
                        return;
                    }
                    pMenu.show(table, e.getX(), e.getY());
                    pMenu.getComponent(0).requestFocus();
                }
            }
        }

        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();
            if (command.equals("复制")) {
                copy();
            } else {
                paste();
            }
        }
    }

    private void copy() {
        int numcols = table.getSelectedColumnCount();
        int numrows = table.getSelectedRowCount();
        if (numcols < 1 || numrows < 1) {
            return;
        }
        int[] rowsselected = table.getSelectedRows();
        int[] colsselected = table.getSelectedColumns();

        if (table.getRowSelectionAllowed()
                && !table.getColumnSelectionAllowed()) {
            numcols = table.getColumnCount();
            colsselected = new int[numcols];
            for (int i = 0; i < numcols; i++) {
                colsselected[i] = i;
            }
        }

        if (numcols == 0 && numrows == 0 || numcols == 1 && numrows == 1) {
            numcols = table.getColumnCount();
            colsselected = new int[numcols];
            for (int i = 0; i < numcols; i++) {
                colsselected[i] = i;
            }

            numrows = table.getRowCount();
            rowsselected = new int[numrows];
            for (int i = 0; i < numrows; i++) {
                rowsselected[i] = i;
            }
        }

        if (!((numrows - 1 == rowsselected[rowsselected.length - 1]
                - rowsselected[0] && numrows == rowsselected.length) && (numcols - 1 == colsselected[colsselected.length - 1]
                - colsselected[0] && numcols == colsselected.length))) {
            Component com = ViewFunction.getViewFunction(table);
            if (com == null) {
                com = table;
            }
            JOptionPane.showMessageDialog(com, "选择单元格要相邻，否则无法复制");
            return;
        }

        // System.err.println("ColumnCount:======"+numcols);
        // System.err.println("RowCount:==========="+numrows);

        StringBuilder sbf = new StringBuilder();
        ArrayList<Integer> sList = new ArrayList<Integer>();
        for (int i = 0; i < numcols; i++) {
            int w = table.getColumnModel().getColumn(i).getWidth();
            if (w == 0) {
                continue;
            }
            String temp = table.getColumnModel().getColumn(colsselected[i]).getHeaderValue().toString();

            //特殊处理,原来此栏位用逗号分隔多张债券，复制时格式会乱掉，现改为用分号分隔后再复制
            if (temp.equals("抵押品") || temp.equals("抵押品券面金额")) {
                sList.add(i);
            }

            sbf.append(temp);
            if (i < numcols - 1) {
                sbf.append("\t");
            }
        }
        sbf.append("\n");

        for (int i = 0; i < numrows; i++) {
            for (int j = 0; j < numcols; j++) {
                int w = table.getColumnModel().getColumn(j).getWidth();
                if (w == 0) {
                    continue;
                }
                Object v = table.getValueAt(rowsselected[i], colsselected[j]);
                v = v != null ? v : "";
                if (v instanceof Number) {
                    v = getNumberStrValue((Number) v);
                }
                String vStr = v.toString();
                vStr = vStr == null ? "" : vStr;
                if (sList.contains(j)) {
                    sbf.append(vStr.replaceAll(",", ";"));
                } else {
                    sbf.append(vStr);
                }

                if (j < numcols - 1) {
                    sbf.append("\t");
                }
            }
            sbf.append("\n");
        }

        stsel = new StringSelection(sbf.toString());
        system = Toolkit.getDefaultToolkit().getSystemClipboard();
        system.setContents(stsel, stsel);
    }

    private void paste() {
        int startRow = (table.getSelectedRows())[0];
        int startCol = (table.getSelectedColumns())[0];
        try {
            String trstring = (String) (system.getContents(this).getTransferData(DataFlavor.stringFlavor));

            StringTokenizer st1 = new StringTokenizer(trstring, "\n");
            for (int i = 0; st1.hasMoreTokens(); i++) {
                rowstring = st1.nextToken();
                StringTokenizer st2 = new StringTokenizer(rowstring, "\t");
                for (int j = 0; st2.hasMoreTokens(); j++) {
                    value = (String) st2.nextToken();
                    if (startRow + i < table.getRowCount()
                            && startCol + j < table.getColumnCount()) {
                        table.setValueAt(value, startRow + i, startCol + j);
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static DecimalFormat df = new DecimalFormat();

    static {
        df.setMinimumIntegerDigits(1);
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(30);
        df.setGroupingUsed(false);
    }

    private String getNumberStrValue(Number v) {
        return df.format(v);
    }

    public void actionPerformed(ActionEvent e) {

        //only do copy and paste when the flag is true
        if (e.getActionCommand().equals("Copy") && isCopy) {
            copy();
        } else if (e.getActionCommand().equals("Paste") && isPaste) {
            paste();
        }
    }

    private boolean isShow(TableColumn tc) {
        if (tc.getMaxWidth() == 0 && tc.getMinWidth() == 0
                && tc.getPreferredWidth() == 0) {
            return false;
        }

        return true;
    }
}
