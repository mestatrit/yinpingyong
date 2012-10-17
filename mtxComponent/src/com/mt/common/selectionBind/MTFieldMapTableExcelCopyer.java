/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.selectionBind;

import com.mt.common.dynamicDataDef.FieldMapNode;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * FieldMap表格ExcelCopyer
 * Created by NetBeans.
 * Author: hanhui
 * Date:2010-4-23
 * Time:13:28:16
 */
public class MTFieldMapTableExcelCopyer implements ActionListener {

    private boolean isMenu;
    private boolean isFCL;
    private boolean isHeader;
    private JTable table;
    private JComponent srcCom;
    private CopyMenu cm;
    private boolean isCopy = true;

    public MTFieldMapTableExcelCopyer(JComponent table) {
        this(table, true);
    }

    public MTFieldMapTableExcelCopyer(JComponent table, boolean isMenu) {
        this(table, true, true);
    }

    public MTFieldMapTableExcelCopyer(JComponent table, boolean isMenu, boolean isFCL) {
        this(table, true, true, true);
    }

    public MTFieldMapTableExcelCopyer(JComponent table, boolean isMenu, boolean isFCL, boolean isHeader) {
        this.isMenu = isMenu;
        this.isFCL = isFCL;
        this.isHeader = isHeader;
        this.srcCom = table;
        KeyStroke copySKey = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK,
                false);
        KeyStroke copyAKey = KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK,
                false);
        if (table instanceof JTable) {
            this.table = (JTable) table;
            table.registerKeyboardAction(this, "CopySelection", copySKey,
                    JComponent.WHEN_FOCUSED);
            table.registerKeyboardAction(this, "CopyAll", copyAKey,
                    JComponent.WHEN_FOCUSED);
            if (this.isMenu) {
                this.cm = new CopyMenu();
                table.addMouseListener(cm);
            }
        } else if (table instanceof MTFieldMapNodeTreeTable) {
            JTable mainTable = ((MTFieldMapNodeTreeTable) table).getMainTable();
            JTable tcTable = ((MTFieldMapNodeTreeTable) table).getTreeColumnTable();

            //复制和选择状况有关，WholeTable并不显示其选择未必和显示的表格一致
            this.table = mainTable;
            //this.table = ((MTFieldMapNodeTreeTable) table).getWholeTable();
            mainTable.registerKeyboardAction(this, "CopySelection", copySKey,
                    JComponent.WHEN_FOCUSED);
            mainTable.registerKeyboardAction(this, "CopyAll", copyAKey,
                    JComponent.WHEN_FOCUSED);
            tcTable.registerKeyboardAction(this, "CopySelection", copySKey,
                    JComponent.WHEN_FOCUSED);
            tcTable.registerKeyboardAction(this, "CopyAll", copyAKey,
                    JComponent.WHEN_FOCUSED);
            if (this.isMenu) {
                this.cm = new CopyMenu();
                mainTable.addMouseListener(cm);
                tcTable.addMouseListener(cm);
            }
        } else {
            throw new RuntimeException("不支持的类型");
        }
    }

    public void setFCL(boolean fcl) {
        this.isFCL = fcl;
    }

    public boolean isFCL() {
        return this.isFCL;
    }

    public void setHeader(boolean h) {
        this.isHeader = h;
    }

    public boolean isHeader() {
        return this.isHeader;
    }

    public JTable getTable() {
        return this.table;
    }

    public JPopupMenu getPopupMenu() {
        if (this.cm != null) {
            return this.cm.pMenu;
        }
        return null;
    }

    public void copySelectionData() {
        if (isCopy == false)
            return;

        int numcols = table.getSelectedColumnCount();
        int numrows = table.getSelectedRowCount();
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
        	/**
        	 * modify on 2012.07.26 for 弹出框的显示位置，以当前table为基顶
        	 */
        	/* Component com = ViewFunction.getViewFunction(table);
            if (com == null) {
                com = table;
            }*/
        	Component com = table;
            JOptionPane.showMessageDialog(com, "选择单元格要相邻，否则无法复制");
            return;
        }

        copy(numrows, rowsselected, numcols, colsselected);
    }

    public void copyAll() {
        if (isCopy == false)
            return;

        if (srcCom instanceof MTFieldMapNodeTreeTable) {
            MTFieldMapNodeTreeTable treeTable = (MTFieldMapNodeTreeTable) srcCom;
            treeTable.expandAll();
            String rs = new FieldMapNodeTXTBuilder(treeTable.getFieldMapNode(), treeTable.getCurFieldColumnList()).build();
            setClipboard(rs);
        } else {
            int numcols = table.getColumnCount();
            int numrows = table.getRowCount();
            int[] rows = new int[numrows];
            int[] cols = new int[numcols];
            for (int i = 0; i < numrows; i++) {
                rows[i] = i;
            }
            for (int i = 0; i < numcols; i++) {
                cols[i] = i;
            }
            copy(numrows, rows, numcols, cols);
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (isCopy == false)
                return;

            String cmd = e.getActionCommand();
            if (cmd.equals("CopySelection")) {
                copySelectionData();
            } else if (cmd.equals("CopyAll")) {
                copyAll();
            } else {
                setHeader(cm.headerItem.isSelected());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void copy(int rowCount, int[] rows, int colCount, int[] cols) {
        StringBuilder sbf = new StringBuilder();
        if (isHeader) {
            for (int i = 0; i < colCount; i++) {
                String temp = table.getColumnModel().getColumn(cols[i]).getHeaderValue().toString();
                sbf.append(temp);
                if (i < colCount - 1) {
                    sbf.append("\t");
                }
            }
            sbf.append("\n");
        }

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                Object rs = null;
                if (isFCL) {
                    rs = table.getValueAt(rows[i], cols[j]);
                } else {
                    rs = ((IMTFieldMapTable) srcCom).getFieldValueAt(rows[i], cols[j]);
                }
                rs = rs == null ? "" : rs;
                if (rs instanceof Number) {
                    rs = getNumberStrValue((Number) rs);
                }
                sbf.append(rs);
                if (j < colCount - 1) {
                    sbf.append("\t");
                }
            }
            sbf.append("\n");
        }
        setClipboard(sbf.toString());
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

    private void setClipboard(String rs) {
        StringSelection stsel = new StringSelection(rs);
        Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
        system.setContents(stsel, stsel);
    }

    class CopyMenu extends MouseAdapter {

        private JPopupMenu pMenu;
        private JMenuItem csItem;
        private JMenuItem caItem;
        private JCheckBoxMenuItem headerItem;

        public CopyMenu() {
            pMenu = new JPopupMenu();

            csItem = new JMenuItem("复制选中数据(Ctrl+C)");
            csItem.setActionCommand("CopySelection");
            csItem.addActionListener(MTFieldMapTableExcelCopyer.this);
            pMenu.add(csItem);

            caItem = new JMenuItem("复制整个表格(Ctrl+B)");
            caItem.setActionCommand("CopyAll");
            caItem.addActionListener(MTFieldMapTableExcelCopyer.this);
            pMenu.add(caItem);

            headerItem = new JCheckBoxMenuItem("是否复制表头");
            headerItem.setSelected(true);
            headerItem.setActionCommand("isCopyHeader");
            headerItem.addActionListener(MTFieldMapTableExcelCopyer.this);
            pMenu.add(headerItem);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                if (table.getRowCount() <= 0) {//表格没内容
                    return;
                }
                int clos = table.getSelectedColumnCount();
                int rows = table.getSelectedRowCount();
                if (rows == 0 && clos == 0) {
                    csItem.setEnabled(false);
                } else {
                    csItem.setEnabled(true);
                }
                pMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    public void setMenuVisible(boolean isEnable) {
        cm.pMenu.setVisible(isEnable);
        this.isCopy = isEnable;
    }

    class FieldMapNodeTXTBuilder {

        private FieldMapNode node;
        private FieldColumnList fcl;
        int maxLevel = 0;

        public FieldMapNodeTXTBuilder(FieldMapNode node, FieldColumnList fcl) {
            this.node = node;
            this.fcl = fcl;
        }

        public String build() {

            StringBuilder sbf = new StringBuilder();
            List<List<String>> rows = getFieldMapNodeValue();
            if (isHeader) {
                List<String> col = getHeaderName();
                for (int i = 0; i < col.size(); i++) {
                    sbf.append(col.get(i));
                    if (i < col.size() - 1) {
                        sbf.append("\t");
                    }
                }
                sbf.append("\n");
            }
            for (int i = 0; i < rows.size(); i++) {
                List<String> row = rows.get(i);
                for (int j = 0; j < row.size(); j++) {
                    sbf.append(row.get(j));
                    if (j < row.size() - 1) {
                        sbf.append("\t");
                    }
                }
                sbf.append("\n");
            }
            return sbf.toString();
        }

        private List<String> getHeaderName() {
            List<String> temp = new ArrayList<String>();
            temp.add(table.getColumnName(0));
            for (int i = 1; i < maxLevel; i++) {
                temp.add("");
            }
            for (int i = 1; i < table.getColumnCount(); i++) {
                temp.add(table.getColumnName(i));
            }
            return temp;
        }

        private List<List<String>> getFieldMapNodeValue() {
            boolean isRootV = ((MTFieldMapNodeTreeTable) srcCom).isRootVisible();
            maxLevel = 0;
            List<List<String>> rows = new ArrayList<List<String>>();
            procFieldMapNode(node, fcl, 0, rows);
            maxLevel++;
            for (int i = 0; i < rows.size(); i++) {
                List<String> row = rows.get(i);
                for (int j = row.size(); j < maxLevel; j++) {
                    row.add("");
                }
                //根节点被隐藏
                if (i == 0 && !isRootV) {
                    for (int j = 1; j < table.getColumnCount(); j++) {
                        row.add("");
                    }
                } else {
                    for (int j = 1; j < table.getColumnCount(); j++) {
                        Object rs = null;
                        if (isFCL) {
                            rs = table.getValueAt(isRootV ? i : i - 1, j);
                        } else {
                            rs = ((IMTFieldMapTable) srcCom).getFieldValueAt(isRootV ? i : i - 1, j);
                        }
                        rs = rs == null ? "" : rs;
                        row.add(rs.toString());
                    }
                }
            }
            return rows;
        }

        private void procFieldMapNode(FieldMapNode node, FieldColumnList fcl, int level, List<List<String>> rows) {
            maxLevel = level > maxLevel ? level : maxLevel;
            List<String> row = new ArrayList<String>();
            for (int i = 0; i < level; i++) {
                row.add("");
            }
            String fName = fcl.getFieldColumn(0).getFieldName();
            row.add(node.getStringValue(fName));
            rows.add(row);
            //递归处理
            int count = node.getChildCount();
            for (int i = 0; i < count; i++) {
                procFieldMapNode(node.getChildAt(i), fcl, level + 1, rows);
            }
        }
    }
}
