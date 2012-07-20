package com.mt.common.gui.MTXComponent;

import com.mt.common.gui.ComponentResizer;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * 树表组件
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 11-11-4
 * Time: 下午3:21
 * To change this template use File | Settings | File Templates.
 */
public class MTXTreeTable extends JPanel {
    private JXTreeTable treeColumn;
    private JXTreeTable mainTable;
    private JXTreeTable wholeTable;
    private TreeTableModel model;
    private JViewport rowHeaderViewport;

    public MTXTreeTable() {
        initView(null);
    }

    public MTXTreeTable(TreeTableModel treeModel) {
        initView(treeModel);
    }

    private void initView(TreeTableModel treeModel) {
        buildTable(treeModel);
        initTable();
        buildUI();
    }

    private void refreshUI() {
        rowHeaderViewport.setPreferredSize(treeColumn.getPreferredSize());
        rowHeaderViewport.revalidate();
        rowHeaderViewport.repaint();
    }

    private void buildTable(TreeTableModel treeModel) {
        //为了适应各种需求这里构造三个表格，但是其背后的模型是一致的，所以不会有太大的负担
        if (treeModel != null) {
            treeColumn = new JXTreeTable(treeModel);
            mainTable = new JXTreeTable(treeModel);
            wholeTable = new JXTreeTable(treeModel);
            setTreeTableModel(treeModel);
        } else {
            treeColumn = new JXTreeTable();
            mainTable = new JXTreeTable();
            wholeTable = new JXTreeTable();
        }
    }

    private void initTable() {
        treeColumn.addTreeExpansionListener(new TreeExpansionListener() {
            @Override
            public void treeExpanded(TreeExpansionEvent event) {
                int row = treeColumn.getRowForPath(event.getPath());
                if (row >= 0) {
                    mainTable.expandRow(row);
                    wholeTable.expandRow(row);
                }

                reSizeColumn();
                //refreshUI();
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent event) {
                int row = treeColumn.getRowForPath(event.getPath());
                if (row >= 0) {
                    mainTable.collapseRow(row);
                    wholeTable.collapseRow(row);
                }
                reSizeColumn();
                //refreshUI();
            }
        });
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(mainTable);
        rowHeaderViewport = new JViewport();
        rowHeaderViewport.setView(treeColumn);
        rowHeaderViewport.setPreferredSize(treeColumn.getPreferredSize());
        scroll.setRowHeaderView(rowHeaderViewport);
        scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, treeColumn
                .getTableHeader());
        this.removeAll();
        this.add(scroll, BorderLayout.CENTER);
    }

    public void changeWholeTableUI() {
        changeWholeTableUI(10000);
    }

    public void changeWholeTableUI(final int max) {
        setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(wholeTable);
        this.removeAll();
        this.add(scroll, BorderLayout.CENTER);
        ComponentResizer.resizeTableColumnWithCC(getWholeTable(), max);
        this.revalidate();
        this.repaint();
    }

    public void changeFTableUI() {
        buildUI();
    }

    public void reSizeColumn() {
        reSizeColumn(10000);
    }

    public void reSizeColumn(int max) {
        int cc = mainTable.getColumnModel().getColumnCount();
        ComponentResizer.resizeTableColumnWithCC(this.getMainTable(), max);
        ComponentResizer.resizeTableColumnWithCC(this.getTreeColumnTable(), max);

        if (cc > 0) {
            TableColumn tc = mainTable.getColumnModel().getColumn(0);
            tc.setWidth(0);
            tc.setMaxWidth(0);
            tc.setMinWidth(0);
        }
        refreshUI();
    }

    public void addHighlighter(Highlighter highlighter) {
        mainTable.addHighlighter(highlighter);
        treeColumn.addHighlighter(highlighter);
        wholeTable.addHighlighter(highlighter);
    }

    public void setRowHeight(int rowHeight) {
        mainTable.setRowHeight(rowHeight);
        treeColumn.setRowHeight(rowHeight);
        wholeTable.setRowHeight(rowHeight);
    }

    public int getRowHeight() {
        return mainTable.getRowHeight();
    }

    public void setShowGrid(boolean hl, boolean vl) {
        mainTable.setShowGrid(hl, vl);
        treeColumn.setShowGrid(hl, vl);
        wholeTable.setShowGrid(hl, vl);
    }

    public void setEditable(boolean isE) {
        mainTable.setEditable(isE);
        treeColumn.setEditable(isE);
        wholeTable.setEditable(isE);
    }

    public void setRootVisible(boolean isRoot) {
        mainTable.setRootVisible(isRoot);
        treeColumn.setRootVisible(isRoot);
        wholeTable.setRootVisible(isRoot);
    }

    public boolean isRootVisible() {
        return mainTable.isRootVisible();
    }

    public void setDefaultRenderer(Class clazz, TableCellRenderer renderer) {
        mainTable.setDefaultRenderer(clazz, renderer);
        treeColumn.setDefaultRenderer(clazz, renderer);
        wholeTable.setDefaultRenderer(clazz, renderer);
    }

    public void setDefaultEditor(Class clazz, TableCellEditor editor) {
        mainTable.setDefaultEditor(clazz, editor);
        treeColumn.setDefaultEditor(clazz, editor);
        wholeTable.setDefaultEditor(clazz, editor);
    }

    public void setTreeTableModel(TreeTableModel model) {
        this.model = model;
        mainTable.setTreeTableModel(model);
        treeColumn.setTreeTableModel(new TreeColumnModel());
        wholeTable.setTreeTableModel(model);
        mainTable.setSelectionModel(treeColumn.getSelectionModel());
        wholeTable.setSelectionMode(treeColumn.getSelectionMode());
        refreshUI();
    }

    public TreeTableModel getTreeTableModel() {
        return model;
    }

    public TableModel getModel() {
        return mainTable.getModel();
    }

    public Class getColumnClass(int col) {
        return mainTable.getColumnClass(col);
    }

    public int getRowCount() {
        return mainTable.getRowCount();
    }

    public void clearSelection() {
        mainTable.clearSelection();
        treeColumn.clearSelection();
        wholeTable.clearSelection();
    }

    public void setRowSelectionInterval(int idx0, int idx1) {
        mainTable.setRowSelectionInterval(idx0, idx1);
        treeColumn.setRowSelectionInterval(idx0, idx1);
        wholeTable.setRowSelectionInterval(idx0, idx1);
    }

    public int getSelectedRow() {
        return mainTable.getSelectedRow();
    }

    public int[] getSelectedRows() {
        return mainTable.getSelectedRows();
    }

    public TreePath getPathForRow(int row) {
        return wholeTable.getPathForRow(row);
    }

    public Object getValueAt(int row, int col) {
        return wholeTable.getValueAt(row, col);
    }

    public void expandAll() {
        mainTable.expandAll();
        treeColumn.expandAll();
        wholeTable.expandAll();
        refreshUI();
    }

    public void expandRow(int row) {
        mainTable.expandRow(row);
        treeColumn.expandRow(row);
        wholeTable.expandRow(row);
        refreshUI();
    }

    public void expandPath(TreePath tp) {
        mainTable.expandPath(tp);
        treeColumn.expandPath(tp);
        wholeTable.expandPath(tp);
        refreshUI();
    }

    public void collapseAll() {
        mainTable.collapseAll();
        treeColumn.collapseAll();
        wholeTable.collapseAll();
        refreshUI();
    }

    public void collapseRow(int row) {
        mainTable.collapseRow(row);
        treeColumn.collapseRow(row);
        wholeTable.collapseRow(row);
        refreshUI();
    }

    public void collapsePath(TreePath tp) {
        mainTable.collapsePath(tp);
        treeColumn.collapsePath(tp);
        wholeTable.collapsePath(tp);
        refreshUI();
    }

    public JXTreeTable getMainTable() {
        return mainTable;
    }

    public JXTreeTable getTreeColumnTable() {
        return treeColumn;
    }

    public JXTreeTable getWholeTable() {
        return wholeTable;
    }

    public JTableHeader getMainTableHeader() {
        return mainTable.getTableHeader();
    }

    public JTableHeader getTreeColumnTableHeader() {
        return treeColumn.getTableHeader();
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        mainTable.addMouseListener(l);
        treeColumn.addMouseListener(l);
    }

    @Override
    public synchronized void removeMouseListener(MouseListener l) {
        mainTable.removeMouseListener(l);
        treeColumn.removeMouseListener(l);
    }

    class TreeColumnModel extends AbstractTreeTableModel {

        @Override
        public int getColumnCount() {
            return model.getColumnCount() == 0 ? 0 : 1;
        }

        @Override
        public Class<?> getColumnClass(int column) {
            return model.getColumnClass(column);
        }

        @Override
        public String getColumnName(int column) {
            return model.getColumnName(column);
        }

        @Override
        public int getHierarchicalColumn() {
            return model.getHierarchicalColumn();
        }

        @Override
        public Object getRoot() {
            return model.getRoot();
        }

        @Override
        public boolean isCellEditable(Object node, int column) {
            return model.isCellEditable(node, column);
        }

        @Override
        public boolean isLeaf(Object node) {
            return model.isLeaf(node);
        }

        @Override
        public void valueForPathChanged(TreePath path, Object newValue) {
            model.valueForPathChanged(path, newValue);
        }

        @Override
        public void setValueAt(Object value, Object node, int column) {
            model.setValueAt(value, node, column);
        }

        @Override
        public Object getValueAt(Object o, int i) {
            return model.getValueAt(o, i);
        }

        @Override
        public Object getChild(Object parent, int index) {
            return model.getChild(parent, index);
        }

        @Override
        public int getChildCount(Object parent) {
            return model.getChildCount(parent);
        }

        @Override
        public int getIndexOfChild(Object parent, Object child) {
            return model.getIndexOfChild(parent, child);
        }
    }

}
