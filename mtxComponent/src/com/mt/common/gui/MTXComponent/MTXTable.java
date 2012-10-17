package com.mt.common.gui.MTXComponent;

import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapSet;
import com.mt.common.export.TableCopyExporter;
import com.mt.common.gui.ComponentResizer;
import com.mt.common.gui.table.*;
import com.mt.common.settingManager.SettingDataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 一个整合了列宽自适应，排序，导出复制等功能的表格
 *
 * @author hanhhui
 */
@SuppressWarnings("serial")
public class MTXTable extends JTable {

    private final static Logger logger = LoggerFactory.getLogger(MTXTable.class);

    private boolean isAuto = true;
    private boolean isSort = true;
    private MTXTableGroup tableGroup;
    private ComponentResizeAutoColumn comResize;
    private TableModelChangeAutoColumn modelChange;
    private TableCopyExporter tce;
    private List<MouseClickedListener> mListeners = new ArrayList<MouseClickedListener>();
    private List<DoubleMouseClickedListener> dmListeners = new ArrayList<DoubleMouseClickedListener>();
    private List<MTXTableColumnListener> cListeners = new ArrayList<MTXTableColumnListener>();
    private TableHeaderHandler thh = new TableHeaderHandler();
    private boolean isCustomTableColumn = true;
    private List<String> nManagedColumns = Collections.emptyList();
    private String md5Str;
    private static final String CURCOL = "CC";
    static private MessageDigest md5;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            logger.error("MD5 Exception", ex);
        }
    }

    public MTXTable() {
        this(null, true, true);
    }

    public MTXTable(boolean isAuto, boolean isSort) {
        this(null, isAuto, isSort);
    }

    public MTXTable(TableModel model) {
        this(model, true, true);
    }

    public MTXTable(TableModel model, boolean isAuto, boolean isSort) {
        init(model, isAuto, isSort);
    }

    private void init(TableModel model, boolean isAuto, boolean isSort) {
        this.isAuto = isAuto;
        this.isSort = isSort;

        /**
         * 启用导入、导出等功能
         */
        tce = new TableCopyExporter(this);

        //配置默认的表现器
        SimpleTableCellRenderer left = new SimpleTableCellRenderer(SwingConstants.LEFT);
        SimpleTableCellRenderer right = new SimpleTableCellRenderer(SwingConstants.RIGHT);
        this.setDefaultRenderer(Object.class, left);
        this.setDefaultRenderer(String.class, left);
        this.setDefaultRenderer(MCNumber.class, right);
        this.setDefaultRenderer(MCDate.class, left);
        this.setDefaultRenderer(MCTerm.class, right);

        installDefaultListener();
        this.setRowHeight(22);
        this.getTableHeader().addMouseListener(thh);
        this.addPropertyChangeListener("model", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                loadColDef();
                resizeTable();
            }
        });
        if (model != null) {
            setDataModel(model);
        }

        this.getColumnModel().addColumnModelListener(new TableColumnModelListener() {
            @Override
            public void columnAdded(TableColumnModelEvent e) {

            }

            @Override
            public void columnRemoved(TableColumnModelEvent e) {

            }

            @Override
            public void columnMoved(TableColumnModelEvent e) {
                saveCurColumn();
            }

            @Override
            public void columnMarginChanged(ChangeEvent e) {

            }

            @Override
            public void columnSelectionChanged(ListSelectionEvent e) {

            }
        });
    }

    private void saveCurColumn() {
        FieldMapSet curCol = new FieldMapSet("curCol");
        int count = this.getColumnCount();
        for (int i = 0; i < count; i++) {
            FieldMap fm = new FieldMap("col");
            fm.putField("colName", this.getColumnName(i));
            fm.putField("colIndex", this.getColumnModel().getColumn(i).getModelIndex());
            curCol.addFieldMap(fm);
        }
        if (md5Str == null) {
            md5Str = getMD5(getModel());
        }
        try {
            SettingDataManager.saveLocalSetting(md5Str + CURCOL, curCol);
        } catch (Throwable t) {
            logger.error("save CurCol error", t);
        }
    }

    private void loadCurColumn() {
        if (isCustomTableColumn) {
            FieldMapSet curCol = SettingDataManager.readFieldMapSetLocalSetting(md5Str + CURCOL);
            if (curCol != null) {
                for (int i = 0; i < curCol.getFieldMapCount(); i++) {
                    if (i < this.getColumnCount()) {
                        TableColumn tc = this.getColumnModel().getColumn(i);
                        FieldMap fm = curCol.getFieldMap(i);
                        tc.setHeaderValue(fm.getStringValue("colName"));
                        tc.setModelIndex(fm.getIntValue("colIndex"));
                    }
                }
            }
        }
    }

    private void loadColDef() {
        if (isCustomTableColumn) {
            refMD5();
            loadCurColumn();
            loadHideColumn();
        }
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return getPreferredSize().height < getParent().getHeight();
    }

    /**
     * 设置是否需要自定义列头
     *
     * @param isC
     */
    public void setCustomTableColumn(boolean isC) {
        isCustomTableColumn = isC;
    }

    /**
     * 设置不受列头自定义托管的列名
     *
     * @param cols
     */
    public void setCustomNotManagedColumn(String... cols) {
        nManagedColumns = Arrays.asList(cols);
    }

    /**
     * 设置是否列需要自动根据模型的变动或画面大小的变动调整
     *
     * @param isAuto
     */
    public void setAutoColumnResize(boolean isAuto) {
        if (isAuto) {
            installComponentResizeAutoColumn();
            installTableModelChangeAutoColumn();
        } else {
            uninstallComponentResizeAutoColumn();
            uninstallTableModelChangeAutoColumn();
        }
        this.isAuto = isAuto;
    }

    void setMTXTableGroup(MTXTableGroup tg) {
        this.tableGroup = tg;
    }

    /**
     * 当前表格的列是否能自适应调整
     *
     * @return
     */
    public boolean isAutoColumnResize() {
        return isAuto;
    }

    /**
     * 设置某一列的排序,通过列名
     *
     * @param columnName
     * @param status
     */
    public void setSortColumn(String columnName, int status) {
        if (!isSort) {
            return;
        }
        TableModel model = super.getModel();
        for (int i = 0; i < model.getColumnCount(); i++) {
            if (model.getColumnName(i).equals(columnName)) {
                setSortColumn(i, status);
                break;
            }
        }
    }

    /**
     * 设置某一列的排序,通过index
     *
     * @param columnIndex
     * @param status
     */
    public void setSortColumn(int columnIndex, int status) {
        if (!isSort) {
            return;
        }
        TableModel model = super.getModel();
        if (model instanceof MultiColumnTableSorter) {
            ((MultiColumnTableSorter) model).setSortingStatus(columnIndex, status);
        }
    }

    /**
     * 设置是否需要排序
     * 如果你设置false的话,接下来表格将不会有排序能力
     * 但是并不会清空已经存在的排序
     *
     * @param isSort
     */
    public void setSort(boolean isSort) {
        TableModel model = super.getModel();
        if (model instanceof MultiColumnTableSorter) {
            if (isSort) {
                ((MultiColumnTableSorter) model).openSorting();
            } else {
                ((MultiColumnTableSorter) model).closeSorting();
            }
            this.isSort = isSort;
        }
    }

    /**
     * 是否可以排序
     *
     * @return
     */
    public boolean isSort() {
        return isSort;
    }

    /**
     * 如果当前有列排序,清除当前的排序
     */
    public void clearSort() {
        TableModel model = super.getModel();
        if (model instanceof MultiColumnTableSorter) {
            ((MultiColumnTableSorter) model).clearSorting();
        }
    }

    public void setExportTableTitle(String title) {
        tce.setTitle(title);
    }

    /**
     * 将表格内容导出Excel
     */
    public void exportExcel() {
        tce.exportExcel();
    }

    /**
     * 将表格内容导出PDF
     */
    public void exportPDF() {
        tce.exportPDF();
    }

    /**
     * 设置右击PDF菜单是否显示
     *
     * @param isV
     */
    public void setPDFMenuItemVisible(boolean isV) {
        tce.setPDFMenuItemVisible(isV);
    }

    /**
     * 设置右击Excel菜单是否显示
     *
     * @param isV
     */
    public void setExcelMenuItemVisible(boolean isV) {
        tce.setExcelMenuItemVisible(isV);
    }

    /**
     * 设置是否显示弹出菜单 , 會一併設置setCopyEnable(isV)
     *
     * @param isV
     */
    public void setPopupMenuVisible(boolean isV) {
        setCopyEnable(isV);
        tce.setPopupMenuVisible(isV);
    }

    /**
     * 新增PopupMenuListener
     *
     * @param listener
     */
    public void addPopupMenuListener(PopupMenuListener listener) {
        tce.getPopupMenu().addPopupMenuListener(listener);
    }

    /**
     * 移除PopupMenuListener
     *
     * @param listener
     */
    public void removePopupMenuListener(PopupMenuListener listener) {
        tce.getPopupMenu().removePopupMenuListener(listener);
    }

    /**
     * 在表格右击菜单里面增加一项Action，尾部增加
     *
     * @param actions
     */
    public void addPopupMenuAction(Action... actions) {
        JMenuItem[] items = new JMenuItem[actions.length];
        for (int i = 0; i < actions.length; i++) {
            items[i] = new JMenuItem(actions[i]);
        }
        addPopupMenuItem(items);
    }

    /**
     * 在表格右击菜单里面增加一项Action，头部增加
     *
     * @param actions
     */
    public void addPopupMenuActionFirst(Action... actions) {
        JMenuItem[] items = new JMenuItem[actions.length];
        for (int i = 0; i < actions.length; i++) {
            items[i] = new JMenuItem(actions[i]);
        }
        addPopupMenuItemFirst(items);
    }

    /**
     * 在表格右击菜单里面增加一项MenuItem，尾部增加
     *
     * @param items
     */
    public void addPopupMenuItem(JMenuItem... items) {
        for (JMenuItem item : items) {
            tce.addMenuItem(item);
        }

    }

    /**
     * 在表格右击菜单里面增加一项MenuItem，头部增加
     *
     * @param items
     */
    public void addPopupMenuItemFirst(JMenuItem... items) {
        for (JMenuItem item : items) {
            tce.addMenuItemFirst(item);
        }
    }

    /**
     * 在表格右击菜单里面增加一个Separator
     */
    public void addPopupMenuSeparator() {
        tce.addSeparator();
    }

    public void addPopupMenuSeparatorFirst() {
        tce.addSeparatorFirst();
    }

    /**
     * 在表格右击菜单里面删除一项MenuItem
     *
     * @param item
     */
    public void removePopupMenuItem(JMenuItem item) {
        tce.removeMenuItem(item);
    }


    /**
     * 设置排序时不参与排序,需要固定的行
     *
     * @param s
     * @param e
     */
    public void setSortFixedRows(int s, int e) {
        TableModel model = super.getModel();
        if (model instanceof MultiColumnTableSorter) {
            ((MultiColumnTableSorter) model).setFixedRows(s, e);
        }
    }

    public void setDataModel(TableModel model) {
        uninstallTableModelChangeAutoColumn();
        if (!(model instanceof MultiColumnTableSorter)) {
            model = new MultiColumnTableSorter(model, super.getTableHeader());

        }
        if (isSort) {
            ((MultiColumnTableSorter) model).openSorting();
        } else {
            ((MultiColumnTableSorter) model).closeSorting();
        }
        super.setModel(model);
        if (isAutoColumnResize()) {
            installTableModelChangeAutoColumn();
        }
    }

    public TableModel getDataModel() {
        TableModel model = super.getModel();
        if (model instanceof MultiColumnTableSorter) {
            return ((MultiColumnTableSorter) model).getTableModel();
        } else {
            return model;
        }
    }

    /**
     * 显示层面行的index到实际模型行的index的映射
     * 如果一个表格排序了，这两个index会不一致
     *
     * @param index
     * @return
     */
    public int viewRowIndexToModelRowIndex(int index) {
        TableModel model = super.getModel();
        if (model instanceof MultiColumnTableSorter) {
            return ((MultiColumnTableSorter) model).modelIndex(index);
        } else {
            return index;
        }
    }

    /**
     * 增加MouseClickedListener
     *
     * @param listener
     */
    synchronized public void addMouseClickedListener(MouseClickedListener listener) {
        mListeners.add(listener);
    }

    /**
     * 删除MouseClickedListener
     *
     * @param listener
     */
    synchronized public void removeMouseClickedListener(MouseClickedListener listener) {
        mListeners.remove(listener);
    }

    /**
     * 增加DoubleMouseClickedListener
     *
     * @param listener
     */
    synchronized public void addDoubleMouseClickedListener(DoubleMouseClickedListener listener) {
        dmListeners.add(listener);
    }

    /**
     * 删除DoubleMouseClickedListener
     *
     * @param listener
     */
    synchronized public void removeDoubleMouseClickedListener(DoubleMouseClickedListener listener) {
        dmListeners.remove(listener);
    }

    protected void installDefaultListener() {
        this.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int col = columnAtPoint(e.getPoint());
                if (row >= 0 && col >= 0) {
                    if (e.getClickCount() == 1) {
                        onMouseClicked(row, col, e.getButton());
                    } else if (e.getClickCount() == 2) {
                        onDoubleMouseClicked(row, col, e.getButton());
                    }
                }
            }
        });
    }

    /**
     * 一个实现鼠标双击处理的便利方法
     *
     * @param row
     * @param column
     */
    protected void onDoubleMouseClicked(int row, int column, int button) {
        for (DoubleMouseClickedListener listener : dmListeners) {
            try {
                listener.onDoubleMouseClicked(row, column, button);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                ;
                continue;
            }
        }
    }

    /**
     * 一个实现鼠标单击处理的便利方法
     *
     * @param row
     * @param column
     */
    protected void onMouseClicked(int row, int column, int button) {
        for (MouseClickedListener listener : mListeners) {
            try {
                listener.onMouseClicked(row, column, button);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                continue;
            }
        }
    }

    private void installComponentResizeAutoColumn() {
        Component parent = getParent();
        if (parent != null) {
            if (comResize == null) {
                comResize = new ComponentResizeAutoColumn();
                parent.addComponentListener(comResize);
            }
        } else {
            throw new RuntimeException("表格parent不存在,安装ComponentResizeAutoColumn失败");
        }
    }

    private void uninstallComponentResizeAutoColumn() {
        Component parent = getParent();
        if (parent != null) {
            if (comResize != null) {
                parent.removeComponentListener(comResize);
                comResize = null;
            }
        } else {
            throw new RuntimeException("表格parent不存在,卸载ComponentResizeAutoColumn失败");
        }
    }

    private void installTableModelChangeAutoColumn() {
        if (modelChange == null) {
            modelChange = new TableModelChangeAutoColumn();
            super.getModel().addTableModelListener(modelChange);
        }

    }

    private void uninstallTableModelChangeAutoColumn() {
        if (modelChange != null) {
            super.getModel().removeTableModelListener(modelChange);
            modelChange = null;
        }
    }

    private void refMD5() {
        md5Str = getMD5(getModel());
    }

    protected void loadHideColumn() {
        if (isCustomTableColumn) {
            resizeTable();
            loadHideColumn(md5Str);
        }
    }

    @SuppressWarnings("unchecked")
    private void loadHideColumn(String md5) {
        Object rs = SettingDataManager.readLocalSetting(md5, List.class);
        if (rs != null) {
            List<String> hideCol = (List<String>) rs;
            TableColumnModel tcm = getColumnModel();
            TableModel tm = getModel();
            for (int i = 0; i < tcm.getColumnCount(); i++) {
                TableColumn tc = tcm.getColumn(i);
                String colName = tm.getColumnName(tc.getModelIndex());
                if (nManagedColumns.contains(colName)) {
                    continue;
                }
                if (hideCol.contains(colName)) {
                    tc.setMaxWidth(0);
                    tc.setMinWidth(0);
                    tc.setWidth(0);
                    tc.setPreferredWidth(0);
                } else {
                    //放开最大尺寸
                    tc.setMaxWidth(1000);
                }
            }
            notifyMTXTableColumnListener(hideCol);
        }
    }

    private void saveHideColumn(String md5, List<String> hideCol) {
        SettingDataManager.saveLocalSetting(md5, hideCol);
        notifyMTXTableColumnListener(hideCol);
    }

    private String getMD5(TableModel tm) {
        StringBuilder tnBuilder = new StringBuilder();
        for (int i = 0; i < tm.getColumnCount(); i++) {
            tnBuilder.append(tm.getColumnName(i));
        }
        return getMD5(tnBuilder.toString());
    }

    private String getMD5(String v) {
        try {
            StringBuilder md5Builder = new StringBuilder();
            byte[] bytes = md5.digest(v.getBytes("UTF-8"));
            for (byte b : bytes) {
                md5Builder.append(toHex(b));
            }
            return md5Builder.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private String toHex(byte one) {
        String HEX = "0123456789ABCDEF";
        char[] result = new char[2];
        result[0] = HEX.charAt((one & 0xf0) >> 4);
        result[1] = HEX.charAt(one & 0x0f);
        String mm = new String(result);
        return mm;
    }

    private void resizeTable() {
        ComponentResizer.resizeTableColumnWithContent(MTXTable.this);
        if (this.tableGroup == null) {
            ComponentResizer.changeTableResizeMode(MTXTable.this);
        } else {
            this.tableGroup.resizeTableGroup();
        }
    }

    class ComponentResizeAutoColumn extends ComponentAdapter {

        public void componentResized(ComponentEvent e) {
            resizeTable();
        }
    }

    class TableModelChangeAutoColumn implements TableModelListener {

        public void tableChanged(TableModelEvent e) {
            if (e.getType() == TableModelEvent.HEADER_ROW) {//表格结构发生变化
                loadColDef();
            }
            //不是模型整体变动刷新的话需要考虑resize的代价
            if (!(e.getColumn() == TableModelEvent.ALL_COLUMNS && e.getLastRow() >= getRowCount())) {
                if (getRowCount() > 1200) {//数据量过大的话resize的代价太大
                    return;
                }
            }
            resizeTable();
        }
    }

    /**
     * 表头列定义处理器
     */
    class TableHeaderHandler extends MouseAdapter {

        private JPanel columnPanel;
        private JPopupMenu mainView;
        private static final String COLUMN_INDEX = "ColumnIndex";

        public TableHeaderHandler() {
            mainView = new JPopupMenu();
            mainView.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            mainView.setLayout(new BorderLayout());
            columnPanel = new JPanel(new GridLayout(7, 5));
            columnPanel.setBorder(BorderFactory.createTitledBorder("定制您自己的表格列头"));
            mainView.add(columnPanel, BorderLayout.CENTER);
            JButton allS = new JButton("全选");
            allS.setMargin(new Insets(2, 2, 2, 2));
            allS.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    int count = columnPanel.getComponentCount();
                    for (int i = 0; i < count; i++) {
                        ((JCheckBox) columnPanel.getComponent(i)).setSelected(true);
                    }
                }
            });
            JButton clear = new JButton("清空");
            clear.setMargin(new Insets(2, 2, 2, 2));
            clear.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    int count = columnPanel.getComponentCount();
                    for (int i = 0; i < count; i++) {
                        ((JCheckBox) columnPanel.getComponent(i)).setSelected(false);
                    }

                }
            });
            JButton suer = new JButton("确定");
            suer.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    confirm();
                }
            });

            JButton cancel = new JButton("取消");
            cancel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    mainView.setVisible(false);
                }
            });
            JPanel control = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JPanel topControl = new JPanel(new FlowLayout(FlowLayout.CENTER));
            topControl.add(allS);
            topControl.add(clear);
            control.add(suer);
            control.add(cancel);
            mainView.add(topControl, BorderLayout.NORTH);
            mainView.add(control, BorderLayout.SOUTH);

        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (isCustomTableColumn && SwingUtilities.isRightMouseButton(e)) {
                columnPanel.removeAll();
                TableModel tableModel = getModel();
                TableColumnModel columnModel = getColumnModel();
                int count = tableModel.getColumnCount();
                int row = count / 5 + 1;
                columnPanel.setLayout(new GridLayout(row, 5));
                for (int i = 0; i < count; i++) {
                    String cName = tableModel.getColumnName(i);
                    if (nManagedColumns.contains(cName)) {
                        continue;
                    }
                    JCheckBox cb = new JCheckBox(cName);
                    cb.setSelected(isVisibleColumn(columnModel, i));
                    cb.putClientProperty(COLUMN_INDEX, new Integer(i));
                    columnPanel.add(cb);
                }
                mainView.pack();
                mainView.show(getTableHeader(), e.getX(), e.getY() - 18);
            }
        }

        private boolean isVisibleColumn(TableColumnModel model, int modelCol) {
            for (int i = 0; i < model.getColumnCount(); i++) {
                TableColumn tc = model.getColumn(i);
                if (tc.getModelIndex() == modelCol) {
                    return !(tc.getWidth() == 0 && tc.getMaxWidth() == 0 && tc.getMinWidth() == 0);
                }
            }
            return false;
        }

        private TableColumn getTableColumn(TableColumnModel model, int modelCol) {
            for (int i = 0; i < model.getColumnCount(); i++) {
                TableColumn tc = model.getColumn(i);
                if (tc.getModelIndex() == modelCol) {
                    return tc;
                }
            }
            return null;
        }

        private void confirm() {
            mainView.setVisible(false);
            List<String> hideCols = new ArrayList<String>();
            TableColumnModel columnModel = getColumnModel();
            int count = columnPanel.getComponentCount();
            for (int i = 0; i < count; i++) {
                JCheckBox cb = (JCheckBox) columnPanel.getComponent(i);
                Integer columnIndex = (Integer) cb.getClientProperty(COLUMN_INDEX);
                TableColumn tc = getTableColumn(columnModel, columnIndex.intValue());
                if (cb.isSelected()) {
                    //放开最大尺寸
                    tc.setMaxWidth(1000);
                } else {
                    tc.setWidth(0);
                    tc.setMaxWidth(0);
                    tc.setMinWidth(0);
                    hideCols.add(cb.getText());
                }
            }
            resizeTable();
            if (md5Str == null) {
                md5Str = getMD5(getModel());
            }
            saveHideColumn(md5Str, hideCols);
        }
    }

    /**
     * Set the Export menuitem is enable or disable , both excel and pdf export.
     *
     * @param isEnable
     */
    public void setExportEnable(boolean isEnable) {
        tce.setExportVisible(isEnable);
    }

    /**
     * Set the table is enable or disable copy.
     *
     * @param isEnable
     */
    public void setCopyEnable(boolean isEnable) {
        tce.setCopyEnable(isEnable);
    }

    public void addMTXTableColumnListener(MTXTableColumnListener listener) {
        cListeners.add(listener);
    }

    public void removeMTXTableColumnListener(MTXTableColumnListener listener) {
        cListeners.remove(listener);
    }

    private void notifyMTXTableColumnListener(List<String> hideCol) {
        for (MTXTableColumnListener listener : cListeners) {
            try {
                listener.columnChanged(this, hideCol);
            } catch (Throwable t) {
            }
        }
    }

    /**
     * 是否设置Excel导出颜色
     * @return
     */
    public boolean isExcelExportColorFlag() {
        return tce.isExcelExportColorFlag();
    }

    /**
     * 设置Excel导出颜色
     * @param colorFlag
     */
    public void setExcelExportColorFlag(boolean colorFlag) {
        tce.setExcelExportColorFlag(colorFlag);
    }
}
