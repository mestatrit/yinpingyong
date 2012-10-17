package com.mt.common.selectionBind;

import com.mt.common.dynamicDataDef.Field;
import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapCustomToString;
import com.mt.common.dynamicDataDef.FieldMapNode;
import com.mt.common.export.PdfExporter;
import com.mt.common.gui.ColorLib;
import com.mt.common.gui.MTXComponent.MTXFileChooser;
import com.mt.common.gui.MTXComponent.MTXTreeTable;
import com.mt.common.gui.table.MCDate;
import com.mt.common.gui.table.MCNumber;
import com.mt.common.gui.table.MCTerm;
import com.mt.common.settingManager.SettingDataManager;
import com.mt.print.SimpleTablePrinter;
import com.mt.util.DateUtil;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * FieldMapNode树表
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2010-3-8
 * Time: 11:06:58
 * To change this template use File | Settings | File Templates.
 */
public class MTFieldMapNodeTreeTable extends MTXTreeTable implements IMTFieldMapTable {

    /**
     * 数据模型
     */
    private FieldMapNode data;
    /**
     * 表格列和数据模型的关系定义
     */
    private FieldColumnList fcs;
    /**
     * 当前的列定义，比如有些列会隐藏
     */
    private FieldColumnList curfcs;
    /**
     * 当前的隐藏列
     */
    private HashMap hideColMap;
    /**
     * FieldMapNode的树表模型封装
     */
    private FieldMapNodeTreeTableModel fTreeTableModel;
    /**
     * 字段名和字段转换器的的映射表
     */
    private HashMap<String, FieldConverter> fConverterMap = new HashMap<String, FieldConverter>();
    private ComponentResizeAutoColumn comResize;
    private MTFieldMapTableExcelCopyer copyer;
    private boolean isAuto = true;
    private boolean customColumn = true;
    private int columnResizeMax = 380;
    private ExcelExporter excelExporter = new ExcelExporter();
    private PdfExporter pdfExporter = new PdfExporter(this, "", "");
    private boolean isExpandAll = true;
    private boolean isRs = true;

    /**
     * 构造一个空表格
     */
    public MTFieldMapNodeTreeTable() {
        this(null, new FieldColumnList());
    }

    /**
     * 从字符串描述的列配置信息构建一个MTFieldMapNodeTreeTable
     * 必须按照下面的顺序
     * 列名;字段名;转换类型;显示格式;存储格式;ColumnClass;是否可编辑
     * 比如:"到期日;endDate;Date;yyyy/MM/dd;yyyyMMdd",java.util.Date;false"
     *
     * @param fieldColumns
     */
    public MTFieldMapNodeTreeTable(String... fieldColumns) {
        this(null, fieldColumns);
    }

    /**
     * 从一个资源路径获取XML文件描述的列配置信息构建一个MTFieldMapNodeTreeTable
     * XML的格式是
     * <FieldColumnList>
     * <p/>
     * <FieldColumn columnName="" fieldName="" converterType=""
     * viewFormat="" saveFormat="" columnClass="" isEditable="true/false"/>
     * <p/>
     * <FieldColumn columnName="" fieldName="" converterType=""
     * viewFormat="" saveFormat="" columnClass="" isEditable="true/false"/>
     * <FieldColumn/>
     * <p/>
     * </FieldColumnList>
     *
     * @param path
     */
    public MTFieldMapNodeTreeTable(String path) {
        this(null, path);
    }

    /**
     * 从一个XMLDocument描述的列配置信息构建一个MTFieldMapNodeTreeTable
     *
     * @param xmlDoc
     */
    public MTFieldMapNodeTreeTable(Document xmlDoc) {
        this(null, xmlDoc);
    }

    /**
     * 从一个列配置信息构建一个MTFieldMapNodeTreeTable
     *
     * @param fcs
     */
    public MTFieldMapNodeTreeTable(FieldColumnList fcs) {
        this(null, fcs);
    }

    /**
     * 从一个FieldMapNode数据模型和字符串描述的列配置信息构建一个MTFieldMapNodeTreeTable
     * 必须按照下面的顺序
     * 列名;字段名;转换类型;显示格式;存储格式;ColumnClass;是否可编辑
     * 比如:"到期日;endDate;Date;yyyy/MM/dd;yyyyMMdd";java.util.Date;false"
     *
     * @param fmn
     * @param fieldColumns
     */
    public MTFieldMapNodeTreeTable(FieldMapNode fmn, String... fieldColumns) {
        this(fmn, new FieldColumnList(fieldColumns));
    }

    /**
     * 从一个FieldMapNode数据模型和一个资源路径获取XML文件描述的列配置信息构建一个MTFieldMapNodeTreeTable
     *
     * @param fmn
     * @param path
     */
    public MTFieldMapNodeTreeTable(FieldMapNode fmn, String path) {
        this(fmn, new FieldColumnList(path));
    }

    /**
     * 从一个FieldMapNode数据模型和一个XMLDocument描述的列配置信息构建一个MTFieldMapNodeTreeTable
     *
     * @param fmn
     * @param xmlDoc
     */
    public MTFieldMapNodeTreeTable(FieldMapNode fmn, Document xmlDoc) {
        this(fmn, new FieldColumnList(xmlDoc));
    }

    /**
     * 从一个FieldMapNode数据模型和列配置信息构建一个MTFieldMapNodeTreeTable
     *
     * @param fmn
     * @param fcs
     */
    public MTFieldMapNodeTreeTable(FieldMapNode fmn, FieldColumnList fcs) {
        initTable();
        this.fcs = fcs;
        if (isCustomColumn()) {//是否开启自定义列功能
            this.curfcs = removeHideColumn(fcs);
        } else {
            this.curfcs = fcs;
        }
        setFieldMapNode(fmn,isExpandAll,isRs);
    }

    private void initTable() {
        copyer = new MTFieldMapTableExcelCopyer(this);
        copyer.setFCL(false);//默认复制不采用列定义
        copyer.getPopupMenu().add(new AbstractAction("打印表格") {
            @Override
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * modify on 2012.07.26 for 打印表格不在锁定页面
            	 */
               /* final ViewFunction vf = ViewFunction.getViewFunction(MTFieldMapNodeTreeTable.this);
                if (vf != null) {
                    vf.startInfiniteLock("打印中,请稍后...");
                }*/
                changeWholeTableUI(columnResizeMax);
                Thread thread = new Thread() {

                    @Override
                    public void run() {
                        SimpleTablePrinter.print(MTFieldMapNodeTreeTable.this);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                changeFTableUI();
//                                vf.stopInfiniteLock();
                            }
                        });
                    }
                };
                thread.start();
            }
        });
        copyer.getPopupMenu().add(new AbstractAction("用Excel查看") {
            @Override
            public void actionPerformed(ActionEvent e) {
                excelExporter.openExcel();
            }
        });
        copyer.getPopupMenu().add(new AbstractAction("用PDF查看") {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdfExporter.openPDF();
            }
        });
        copyer.getPopupMenu().add(excelExporter);
        JMenuItem menuItem = new JMenuItem("导出PDF文件");
        menuItem.addActionListener(pdfExporter);
        copyer.getPopupMenu().add(menuItem);

        //this.addHighlighter(HighlighterFactory.createAlternateStriping(ColorLib.tableColor4, ColorLib.tableColor3));
        this.addHighlighter(HighlighterFactory.createAlternateStriping(ColorLib.TRow_EvenColor, ColorLib.TRow_OddColor));

        this.setRowHeight(25);
        this.setShowGrid(true, true);
        this.setEditable(false);
        this.getMainTableHeader().setReorderingAllowed(false);
        this.getTreeColumnTableHeader().setReorderingAllowed(false);
        TableHeaderHandler thh = new TableHeaderHandler();
        this.getMainTableHeader().addMouseListener(thh);
        this.getTreeColumnTableHeader().addMouseListener(thh);
        this.setRootVisible(true);


        //配置默认的表现器
        MTFieldMapNodeTableCellRenderer left = new MTFieldMapNodeTableCellRenderer(SwingConstants.LEFT);
        MTFieldMapNodeTableCellRenderer right = new MTFieldMapNodeTableCellRenderer(SwingConstants.RIGHT);
        this.setDefaultRenderer(Object.class, left);
        this.setDefaultRenderer(String.class, left);
        this.setDefaultRenderer(MCNumber.class, right);
        this.setDefaultRenderer(MCDate.class, left);
        this.setDefaultRenderer(MCTerm.class, right);

        MouseSortHandler msh = new MouseSortHandler();
        SortableHeaderRenderer shr = new SortableHeaderRenderer(
                this.getMainTableHeader().getDefaultRenderer());
        this.getTreeColumnTableHeader().addMouseListener(msh);
        this.getTreeColumnTableHeader().setDefaultRenderer(shr);
        this.getMainTableHeader().addMouseListener(msh);
        this.getMainTableHeader().setDefaultRenderer(shr);

    }

    /**
     * 设置复制的时候是不是连表头也一起复制
     *
     * @param isH
     */
    public void setCopyHeader(boolean isH) {
        copyer.setHeader(isH);
    }

    /**
     * 设置复制的时候值是否采用列定义格式化
     *
     * @param isf
     */
    public void setFCL(boolean isf) {
        copyer.setFCL(isf);
    }

    /**
     * 设置FieldMapNode
     *
     * @param fmNode
     * @param isExpandAll
     * @param isRs
     */
    public void setFieldMapNode(FieldMapNode fmNode, boolean isExpandAll, boolean isRs) {
        this.data = fmNode;
        this.isExpandAll = isExpandAll;
        if (this.data != null) {
            if (this.sortColumn >= 0) {//表格某一列处于排序状态
                sortFieldMapNode(this.data, this.sortColumn, this.descending);
            }
            final FieldColumn fc = curfcs.getFieldColumn(0);
            this.data.setFieldMapCustomToString(new FieldMapCustomToString() {

                public String toString(FieldMap fm) {
                    Field field = fm.getField(fc.getFieldName());
                    if (field != null) {
                        try {
                            return convertField(field, fc).toString();
                        } catch (Exception e) {
                            //logger.error(e.getMessage(),e);;
                            //如果转换失败返回field的原值
                            return field.getStringValue();
                        }
                    } else {
                        //在FieldColumn定义的FieldName在实际的FieldMapSet中没有找到
                        //System.err.println(fc.getColumnName() + "对应的" + fc.getFieldName() + "在FieldMap中没有找到");
                        return "";
                    }
                }
            });
        }
        this.fTreeTableModel = new FieldMapNodeTreeTableModel(this.data);
        this.setTreeTableModel(this.fTreeTableModel);
        if (isExpandAll) {
            this.expandAll();
        } else {
            this.expandRow(0);
        }
        if (isRs) {
            this.reSizeColumn(this.columnResizeMax);
        }

    }

    /**
     * 设置FieldMapNode
     *
     * @param fmNode
     * @param isRs
     */
    public void setFieldMapNode(FieldMapNode fmNode, boolean isRs) {
        setFieldMapNode(fmNode, true, isRs);
    }

    /**
     * 设置FieldMapNode
     *
     * @param fmNode
     * @param isExpandAll
     */
    public void setFieldMapNode_E(FieldMapNode fmNode, boolean isExpandAll) {
        setFieldMapNode(fmNode, isExpandAll, true);
    }

    /**
     * 设置FieldMapNode
     *
     * @param fmNode
     */
    public void setFieldMapNode(FieldMapNode fmNode) {
        setFieldMapNode(fmNode, true, true);
    }

    /**
     * 获取FieldMapNode
     *
     * @return
     */
    public FieldMapNode getFieldMapNode() {
        return data;
    }

    /**
     * 清空数据,默认不清空
     */
    public void clearData() {
        clearData(false);
    }

    /**
     * 清空数据,指定是否清空排序状态
     *
     * @param isclearSort
     */
    public void clearData(boolean isclearSort) {
        if (isclearSort) {
            clearSort();
        }
        setFieldMapNode(null,isExpandAll,isRs);
    }

    /**
     * 设置字符串描述的列配置信息
     * 必须按照下面的顺序
     * 列名;字段名;转换类型;显示格式;存储格式;ColumnClass;是否可编辑
     * 比如:"到期日;endDate;Date;yyyy/MM/dd;yyyyMMdd",java.util.Date;false"
     *
     * @param fieldColumns
     */
    public void setFieldColumnList(String... fieldColumns) {
        setFieldColumnList(new FieldColumnList(fieldColumns));
    }

    /**
     * 设置XML描述的列配置信息资源路径
     *
     * @param path
     */
    public void setFieldColumnList(String path) {
        setFieldColumnList(new FieldColumnList(path));
    }

    /**
     * 设置列配置信息
     *
     * @param fcs
     */
    public void setFieldColumnList(FieldColumnList fcs) {
        FieldColumnList tempCurfcs = null;
        if (isCustomColumn()) {//是否开启自定义列功能
            tempCurfcs = removeHideColumn(fcs);
        } else {
            tempCurfcs = fcs;
        }
        if (this.sortColumn >= 0) {//表格某一列处于排序状态

            int newCount = tempCurfcs.getFieldColumnCount();
            if (this.sortColumn > newCount) {//新的列定义列数小于排序列
                clearSort();
            } else {
                String oldName = fcs.getFieldColumn(this.sortColumn).getColumnName();
                String newName = tempCurfcs.getFieldColumn(this.sortColumn).getColumnName();
                if (!oldName.equals(newName)) {//同一列对应的列名不相同
                    clearSort();
                }
            }
        }
        this.fcs = fcs;
        this.curfcs = tempCurfcs;

        setFieldMapNode(this.data,isExpandAll,isRs);

    }

    /**
     * 移除掉隐藏的列
     *
     * @param fcs
     * @return
     */
    private FieldColumnList removeHideColumn(FieldColumnList fcs) {
        hideColMap = readHideColumn(fcs.getName());
        FieldColumnList temp = new FieldColumnList();
        temp.setName(fcs.getName());
        for (int i = 0; i < fcs.getFieldColumnCount(); i++) {
            String colName = fcs.getFieldColumn(i).getColumnName();
            if (hideColMap.get(colName) == null) {//不隐藏
                temp.addFieldColumn(fcs.getFieldColumn(i));
            }
        }
        return temp;
    }

    private HashMap readHideColumn(String name) {
        HashMap map = new HashMap();
        FieldMap fm = (FieldMap) SettingDataManager.readLocalSetting(name, FieldMap.class);
        if (fm == null) {
            return map;
        }
        Field hcField = fm.getField("HideColumn");
        if (hcField != null) {
            String[] hcs = hcField.getValue().toString().split("\t");
            if (hcs != null) {
                for (String hcName : hcs) {
                    map.put(hcName, "");
                }
            }
        }
        return map;
    }

    private void saveHideColumn(String name, List<String> colNames) {
        FieldMap fm = new FieldMap(name);
        String colns = "";
        for (String col : colNames) {
            colns += col + "\t";
        }
        if (colns.length() > 0) {
            colns = colns.substring(0, colns.length() - 1);
        }
        fm.putField("HideColumn", colns);
        SettingDataManager.saveLocalSetting(fm.getName(), fm);
    }

    /**
     * @return 是否开启列定义
     */
    public boolean isCustomColumn() {
        return customColumn;
    }

    /**
     * 设置是否开启列定义
     *
     * @param isCustomColumn
     */
    public void setCustomColumn(boolean isCustomColumn) {
        this.customColumn = isCustomColumn;
    }

    /**
     * 获得当前列定义
     *
     * @return
     */
    public FieldColumnList getCurFieldColumnList() {
        return this.curfcs;
    }

    /**
     * 获得列配置信息
     *
     * @return
     */
    public FieldColumnList getFieldColumnList() {
        return this.fcs;
    }

    /**
     * 根据某个字段的值来决定行的高亮
     *
     * @param fName
     * @param patternStr
     * @param background
     */
    public void setRowColorHighlight(String fName, String patternStr, Color background) {
        setRowColorHighlight(fName, patternStr, background, null);
    }

    /**
     * 根据某个字段的值来决定行的高亮
     *
     * @param fName
     * @param patternStr
     * @param background
     * @param foreground
     */
    public void setRowColorHighlight(String fName, String patternStr, Color background, Color foreground) {
        setRowColorHighlight(fName, Pattern.compile(patternStr), background, foreground);
    }

    /**
     * 根据某个字段的值来决定行的高亮
     *
     * @param fName
     * @param p
     * @param background
     * @param foreground
     */
    public void setRowColorHighlight(String fName, Pattern p, Color background, Color foreground) {
        ColorHighlighter ch = new ColorHighlighter(new FieldMapNodePatternHighlightPredicate(fName, p));
        ch.setBackground(background);
        ch.setForeground(foreground);
        this.addHighlighter(ch);

    }

    /**
     * 设置高亮
     *
     * @param background
     * @param hc
     */
    public void setColorHighlight(Color background, HighlightCondition hc) {
        setColorHighlight(background, null, hc);
    }

    /**
     * 设置高亮
     *
     * @param background
     * @param foreground
     * @param hc
     */
    public void setColorHighlight(Color background, Color foreground, HighlightCondition hc) {
        ColorHighlighter ch = new ColorHighlighter(new FieldMapNodeCusHighlightPredicate(hc));
        ch.setBackground(background);
        ch.setForeground(foreground);
        this.addHighlighter(ch);
    }

    /**
     * 设置是否列需要自动根据画面大小的变动调整
     *
     * @param isAuto
     */
    public void setAutoColumnResize(boolean isAuto) {
        if (isAuto) {
            installComponentResizeAutoColumn();
        } else {
            uninstallComponentResizeAutoColumn();
        }
        this.isAuto = isAuto;
    }

    /**
     * 设置列的Resize最大宽度
     *
     * @param maxSize
     */
    public void setColumnResizeMax(int maxSize) {
        this.columnResizeMax = maxSize;
    }

    /**
     * 增加一个右击命令
     *
     * @param action
     */
    public void addPopupAction(Action action) {
        copyer.getPopupMenu().add(action);
    }

    /**
     * 增加一个右击命令
     *
     * @param item
     */
    public void addPopupAction(JMenuItem item) {
        copyer.getPopupMenu().add(item);
    }

    public void addPopupActionFirst(JMenuItem item) {
        copyer.getPopupMenu().insert(item, 0);
    }

    public void addPopupActionFirst(Action action) {
        copyer.getPopupMenu().insert(action, 0);
    }

    public void addPopupMenuSeparator() {
        copyer.getPopupMenu().addSeparator();
    }

    public void addPopupMenuSeparatorFirst() {
        copyer.getPopupMenu().insert(new JPopupMenu.Separator(), 0);
    }

    /**
     * 新增PopupMenuListener
     *
     * @param listener
     */
    public void addPopupMenuListener(PopupMenuListener listener) {
        copyer.getPopupMenu().addPopupMenuListener(listener);
    }

    /**
     * 获得被选中的FieldMapNode
     *
     * @return
     */
    public FieldMapNode getSelectedFieldMapNode() {
        int row = this.getSelectedRow();
        if (row >= 0) {
            this.getValueAt(row, 0);//确定节点，以便下面从model中取出这个节点
            FieldMapNodeTreeTableModel model = (FieldMapNodeTreeTableModel) this.getTreeTableModel();
            return model.getCurFieldMapNode();
        }
        return null;
    }

    /**
     * 获得被选中FieldMapNodeList
     *
     * @return
     */
    public ArrayList<FieldMapNode> getSelectedFieldMapNodeList() {
        int[] idxs = this.getSelectedRows();
        ArrayList<FieldMapNode> list = new ArrayList<FieldMapNode>();
        for (int idx : idxs) {
            if (idx >= 0) {
                this.getValueAt(idx, 0);
                FieldMapNodeTreeTableModel model = (FieldMapNodeTreeTableModel) this.getTreeTableModel();
                list.add(model.getCurFieldMapNode());
            }
        }
        return list;
    }

    /**
     * 复制被选中的FieldMapNode
     *
     * @return
     */
    public FieldMapNode copySelectedFieldMapNode() {
        FieldMapNode fmn = getSelectedFieldMapNode();
        if (fmn != null) {
            return fmn.copyFieldMapNode();
        }
        return null;
    }

    /**
     * 当前表格的列是否能自适应调整
     *
     * @return
     */
    public boolean isAutoColumnResize() {
        return isAuto;
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

    /**
     * 为某一列配置自定义的字段转换器
     * 一般情况下你不需要定义自己的FieldConverter，应优先考虑ConverterType的支持
     *
     * @param fName
     * @param fc
     */
    public void setFieldConverter(String fName, FieldConverter fc) {
        fConverterMap.put(fName, fc);
    }

    public void setFieldValue(Object obj, int row, int col) {
        Object node = getPathForRow(row).getLastPathComponent();
        if (node != null) {
            fTreeTableModel.setValueAt(obj, node, col);
        }
    }

    /**
     * Field转化为表格显示的值
     *
     * @param field
     * @param fieldColumn
     * @return
     */
    protected Object convertField(Field field, FieldColumn fieldColumn) {
        FieldConverter fc = fConverterMap.get(field.getName());
        if (fc != null) {
            return fc.convertField(field);
        }
        String type = fieldColumn.getConverterType();
        if (type != null) {
            Object rs = null;
            if (type.equals("Date")) {
                String format = fieldColumn.getViewFormat();
                format = format == null ? "yyyy-MM-dd" : format;
                rs = field.getDateStringValue(format);
            } else if (type.equals("Number")) {
                String format = fieldColumn.getViewFormat();
                rs = format == null ? field.getNumberStringValue()
                        : field.getNumberStringValue(Integer.parseInt(format));
            } else if (type.equals("NumberYW")) {
                String format = fieldColumn.getViewFormat();
                rs = format == null ? field.getNumberStringValue_YW()
                        : field.getNumberStringValue_YW(Integer.parseInt(format));
            } else if (type.equals("NumberFP")) {
                String format = fieldColumn.getViewFormat();
                rs = format == null ? field.getNumberStringValue_FP()
                        : field.getNumberStringValue_FP(Integer.parseInt(format));
            } else if (type.equals("Boolean")) {
                rs = field.getBooleanValue();
            } else {
                rs = field.getValue().toString();
            }
            return rs;
        } else {
            return field.getValue();
        }
    }

    /**
     * 表格编辑的值设置到Field中
     *
     * @param field
     * @param value
     * @param fieldColumn
     */
    protected void setField(Field field, Object value, FieldColumn fieldColumn) {
        FieldConverter fc = fConverterMap.get(field.getName());
        if (fc != null) {
            fc.setField(field, value);
            return;
        }
        String type = fieldColumn.getConverterType();
        if (type != null) {
            if (type.equals("Date")) {
                String format = fieldColumn.getSaveFormat();
                format = format == null ? "yyyyMMdd" : format;
                field.setStringDateValue(value.toString(), format);
            } else if (type.equals("Number")) {
                String format = fieldColumn.getSaveFormat();
                if (format != null) {
                    field.setStringNumberValue(value.toString(), Integer.parseInt(format));
                } else {
                    field.setValue(value.toString().replaceAll(",", ""));
                }
            } else if (type.equals("NumberYW")) {
                String format = fieldColumn.getSaveFormat();
                if (format != null) {
                    field.setStringNumberValue_WY(value.toString(), Integer.parseInt(format));
                } else {
                    field.setStringNumberValue_WY(value.toString());
                }
            } else if (type.equals("NumberFP")) {
                String format = fieldColumn.getSaveFormat();
                if (format != null) {
                    field.setStringNumberValue_PF(value.toString(), Integer.parseInt(format));
                } else {
                    field.setStringNumberValue_PF(value.toString());
                }
            } else if (type.equals("Boolean")) {
                String format = fieldColumn.getSaveFormat();
                if (format != null) {
                    if (format.equals("1/0")) {
                        field.setValue(((Boolean) value) ? "1" : "0");
                    } else if (format.equals("Y/N")) {
                        field.setValue(((Boolean) value) ? "Y" : "N");
                    } else {
                        throw new RuntimeException("Boolean不支持这种\"" + format + "\"SaveFormat");
                    }
                } else {
                    field.setValue(((Boolean) value) ? "Y" : "N");
                }
            } else {
                field.setValue(value.toString());
            }
        } else {
            field.setValue(value);
        }
    }

    private DefaultMutableTreeTableNode getTreeNode(FieldMapNode node) {
        if (node == null) {
            return null;
        }
        DefaultMutableTreeTableNode treeNode = new DefaultMutableTreeTableNode(node);
        if (node.isLeaf()) {
            return treeNode;
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            treeNode.add(getTreeNode(node.getChildAt(i)));
        }
        return treeNode;
    }

    public FieldMapNode getFieldMapNode(int row) {
        Object node = getPathForRow(row).getLastPathComponent();
        FieldMapNode fmn = (FieldMapNode) ((DefaultMutableTreeTableNode) node).getUserObject();
        return fmn;
    }

    public Object getFieldValueAt(int row, int col) {
        TreePath treePath = getPathForRow(row);
        if (treePath == null) {
            return null;
        }
        Object node = treePath.getLastPathComponent();
        if (node != null) {
            FieldMapNode fmn = (FieldMapNode) ((DefaultMutableTreeTableNode) node).getUserObject();
            FieldColumn fc = curfcs.getFieldColumn(col);
            Field f = fmn.getField(fc.getFieldName());
            if (f != null) {
                return f.getValue();
            }
        }
        return null;
    }

    /**
     * 是否可编辑,子类可覆盖实现
     *
     * @param node
     * @param field
     * @return
     */
    protected Boolean isCellEditable(FieldMapNode node, Field field) {
        return null;
    }

    /**
     * 字段改变
     *
     * @param field
     * @param oldValue
     * @param row
     */
    protected void fieldChanged(Field field, Object oldValue, FieldMapNode row) {
        //子类可以覆盖这个函数以处理一些字段被更新后的动作
    }

    class FieldMapNodeTreeTableModel extends DefaultTreeTableModel {

        private Object curNode;

        public FieldMapNodeTreeTableModel(FieldMapNode root) {
            super(getTreeNode(root));
        }

        @Override
        public boolean isCellEditable(Object node, int column) {
            //System.err.println("tree model cell editable---------------------"+column);
            FieldColumn fc = curfcs.getFieldColumn(column);
            FieldMapNode fNode = (FieldMapNode) ((DefaultMutableTreeTableNode) node).getUserObject();
            Field field = fNode.getField(fc.getFieldName());
            if (field != null) {
                Boolean rs = MTFieldMapNodeTreeTable.this.isCellEditable(fNode, field);
                if (rs != null) {
                    return rs;
                } else {
                    return fc.isEditable();
                }
            } else {
                return false;
            }
        }

        @Override
        public Class getColumnClass(int columnIndex) {
            //第0列必须是树,不可以是其他ColumnClass
            if (columnIndex == 0) {
                return super.getColumnClass(columnIndex);
            }

            FieldColumn fc = curfcs.getFieldColumn(columnIndex);
            Class<?> cClass = fc.getColumnClass();
            if (cClass != null) {
                return cClass;
            }

            //如果没有配置ColumnClass,根据ConverterType生成ColumnClass
            String type = curfcs.getFieldColumn(columnIndex).getConverterType();
            if (type == null) {
                type = "String";
            }
            if (type.equals("Date")) {
                return MCDate.class;
            } else if (type.equals("Number") || type.equals("NumberYW") || type.equals("NumberFP")) {
                return MCNumber.class;
            } else if (type.equals("Boolean")) {
                return Boolean.class;
            } else if (type.equals("String")) {
                return String.class;
            } else {
                return Object.class;
            }
        }

        @Override
        public String getColumnName(int column) {
            return curfcs.getFieldColumn(column).getColumnName();
        }

        @Override
        public int getColumnCount() {
            return curfcs == null ? 0 : curfcs.getFieldColumnCount();
        }

        @Override
        public Object getValueAt(Object o, int i) {
            this.curNode = o;
            FieldColumn fc = curfcs.getFieldColumn(i);
            Field field = ((FieldMapNode) ((DefaultMutableTreeTableNode) o).getUserObject()).getField(fc.getFieldName());
            if (field != null) {
                try {
                    return convertField(field, fc);
                } catch (Exception e) {
                    //logger.error(e.getMessage(),e);;
                    //如果转换失败返回field的原值
                    return field.getValue();
                }
            } else {
                //在FieldColumn定义的FieldName在实际的FieldMapSet中没有找到
                //System.err.println(fc.getColumnName() + "对应的" + fc.getFieldName() + "在FieldMap中没有找到");
                return null;
            }
        }

        @Override
        public void setValueAt(Object o, Object o1, int i) {
            FieldColumn fc = curfcs.getFieldColumn(i);
            FieldMapNode fmn = (FieldMapNode) ((DefaultMutableTreeTableNode) o1).getUserObject();
            Field field = fmn.getField(fc.getFieldName());
            if (field != null) {
                Object oldValue = field.getValue();
                setField(field, o, fc);
                fieldChanged(field, oldValue, fmn);
            } else {
                //在FieldColumn定义的FieldName在实际的FieldMapSet中没有找到
                //System.err.println(fc.getColumnName()+"对应的"+fc.getFieldName()+"在FieldMap中没有找到");
            }
        }

        public FieldMapNode getCurFieldMapNode() {
            return (FieldMapNode) ((DefaultMutableTreeTableNode) this.curNode).getUserObject();
        }
    }

    class ComponentResizeAutoColumn extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent e) {
            reSizeColumn();
        }
    }

    final static Color WDArrowColor = new Color(0, 192, 192);

    class MTFieldMapNodeTableCellRenderer extends DefaultTableCellRenderer {

        private int alignment;

        public MTFieldMapNodeTableCellRenderer(int alignment) {
            this.alignment = alignment;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            JLabel cell = (JLabel) super.getTableCellRendererComponent(table,
                    value, isSelected, hasFocus, row, column);
            cell.setHorizontalAlignment(alignment);
            return cell;
        }
    }

    class FieldMapNodeCusHighlightPredicate implements HighlightPredicate {

        private HighlightCondition hc;

        public FieldMapNodeCusHighlightPredicate(HighlightCondition hc) {
            this.hc = hc;
        }

        @Override
        public boolean isHighlighted(Component component, org.jdesktop.swingx.decorator.ComponentAdapter ca) {
            Object node = getPathForRow(ca.row).getLastPathComponent();
            if (node != null) {
                String fName = curfcs.getFieldColumn(ca.column).getFieldName();
                FieldMapNode fmn = (FieldMapNode) ((DefaultMutableTreeTableNode) node).getUserObject();
                return hc.isHighlighted(fmn, fmn.getField(fName), fName, ca.row, ca.column);
            }
            return false;
        }
    }

    class FieldMapNodePatternHighlightPredicate implements HighlightPredicate {

        private String fName;
        private Pattern pattern;

        public FieldMapNodePatternHighlightPredicate(String fName, Pattern pattern) {
            this.fName = fName;
            this.pattern = pattern;
        }

        public boolean isHighlighted(Component cmpnt, org.jdesktop.swingx.decorator.ComponentAdapter ca) {
            if (this.fName != null) {
                Object node = getPathForRow(ca.row).getLastPathComponent();
                if (node != null) {
                    FieldMapNode fmn = (FieldMapNode) ((DefaultMutableTreeTableNode) node).getUserObject();
                    Field ff = fmn.getField(this.fName);
                    if (ff != null) {
                        return pattern.matcher(ff.getValue() == null ? "" : ff.getValue().toString()).find();
                    }
                }
            }
            return false;
        }
    }

    class ExcelExporter extends AbstractAction {

        public ExcelExporter() {
            super("导出Excel文件");
        }

        public void actionPerformed(ActionEvent e) {
            try {
                MTXFileChooser fChooser = new MTXFileChooser();
                String dateStr = DateUtil.getDateString(new Date(), "yyyyMMddHHmmss");
                fChooser.setSelectedFile(new File(dateStr + ".xls"));
                int rs = fChooser.showSaveDialog(SwingUtilities.getWindowAncestor(MTFieldMapNodeTreeTable.this));
                if (rs == MTXFileChooser.APPROVE_OPTION) {
                    File fileSave = fChooser.getSelectedFile();
                    if (!fileSave.exists()) {
                        fileSave.createNewFile();
                    }
                    MTFieldMapDataFileExporter.exportFieldMapNodeExcel(data, fileSave, curfcs);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(MTFieldMapNodeTreeTable.this, "保存失败\n" + ex.getMessage());
            }
        }

        private static final String tempPath = "temp/excel/";

        public void openExcel() {
            String dateFile = DateUtil.getDateString(new Date(), "yyyyMMddHHmmss");
            File tempDir = new File(tempPath);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            /**
             * modify by yinpy for 导出的文件名不再取父类容器Title
             */
            /*ViewFunction vf = ViewFunction.getViewFunction(MTFieldMapNodeTreeTable.this);
            if (vf != null) {
                dateFile = StringUtil.rmFChar(vf.getViewTitle()) + "_" + dateFile;
            }*/
            File tempFile = new File(tempPath + dateFile + ".xls");
            try {
                MTFieldMapDataFileExporter.exportFieldMapNodeExcel(data, tempFile, curfcs);
                Desktop.getDesktop().open(tempFile);
            } catch (Throwable t) {
                t.printStackTrace();
                JOptionPane.showMessageDialog(MTFieldMapNodeTreeTable.this, "执行失败\n" + t.getMessage());
            }
        }
    }

    /**
     * 表头列定义处理器
     */
    class TableHeaderHandler extends MouseAdapter {

        private JPanel columnPanel;
        private JPopupMenu mainView;

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
            if (e.getButton() == MouseEvent.BUTTON3 && isCustomColumn()) {
                columnPanel.removeAll();
                int count = fcs.getFieldColumnCount();
                int row = count / 5 + 1;
                columnPanel.setLayout(new GridLayout(row, 5));
                for (int i = 1; i < count; i++) {
                    String cName = fcs.getFieldColumn(i).getColumnName();
                    JCheckBox cb = new JCheckBox(cName);
                    if (hideColMap.get(cName) != null) {//隐藏
                        cb.setSelected(false);
                    } else {
                        cb.setSelected(true);
                    }

                    columnPanel.add(cb);
                }

                mainView.pack();
                mainView.show(e.getComponent(), e.getX(), e.getY() - 18);
            }
        }

        private void confirm() {
            mainView.setVisible(false);
            List columns = new ArrayList<String>();
            int count = columnPanel.getComponentCount();
            for (int i = 0; i < count; i++) {
                JCheckBox cb = (JCheckBox) columnPanel.getComponent(i);
                if (!cb.isSelected()) {
                    columns.add(cb.getText());
                }
            }
            saveHideColumn(fcs.getName(), columns);
            setFieldColumnList(fcs);
        }
    }

    private String lName = "LEAF";

    /**
     * 设置排序层的节点名字
     *
     * @param lName
     */
    public void setSortLName(String lName) {
        this.lName = lName;
    }

    private boolean onlySortBPCode = true;

    public void setSortBPCode(boolean f) {
        onlySortBPCode = f;
    }

    private FieldMapNode sortFieldMapNode(FieldMapNode node, int column, boolean descending) {
        if (node != null && column >= 0) {
            String fName = curfcs.getFieldColumn(column).getFieldName();
            if (column == 0 && onlySortBPCode) {//树结构列暂时使用括号对内容排序
                node.sortBPCodeString(fName, descending, lName);
                return node;
            }
            Class classV = this.getColumnClass(column);
            if (classV == MCNumber.class) {
                node.sortNumber(fName, descending, lName);
            } else if (classV == MCDate.class) {
                node.sortDate(fName, descending, lName);
            } else {
                node.sortString(fName, descending, lName);
            }
        }
        return node;
    }

    private int sortColumn = -1;
    private boolean descending = true;
    private boolean isSort = true;

    private void clearSort() {
        sortColumn = -1;
        descending = true;
    }

    /**
     * 设置是否需要排序
     * 如果你设置false的话,接下来表格将不会有排序能力
     * 但是并不会清空已经存在的排序
     *
     * @param isSort
     */
    public void setSort(boolean isSort) {
        this.isSort = isSort;
        if (!isSort)
            clearSort();
    }

    /**
     * 设置是否显示弹出菜单 , 會一併設置setCopyEnable(isV)
     *
     * @param isV
     */
    public void setPopupMenuVisible(boolean isV) {
        copyer.setMenuVisible(isV);

    }

    private class MouseSortHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (isSort) {
                    JTableHeader h = (JTableHeader) e.getSource();
                    TableColumnModel columnModel = h.getColumnModel();
                    int column = columnModel.getColumnIndexAtX(e.getX());
                    if (column != -1) {
                        if (sortColumn == column) {
                            descending = !descending;
                        } else {
                            sortColumn = column;
                            descending = false;
                        }

                        //重新设置模型
                        setFieldMapNode(data,isExpandAll,isRs);
                    }
                }
            }
        }
    }

    private class SortableHeaderRenderer implements TableCellRenderer,
            Serializable {

        private TableCellRenderer tableCellRenderer;

        public SortableHeaderRenderer(TableCellRenderer tableCellRenderer) {
            this.tableCellRenderer = tableCellRenderer;
        }

        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus, int row,
                                                       int column) {
            Component c = tableCellRenderer.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            if (c instanceof JLabel) {
                JLabel l = (JLabel) c;
                l.setHorizontalTextPosition(JLabel.LEFT);
                if (column == sortColumn) {
                    l.setIcon(new Arrow(descending, l.getFont().getSize()));
                } else {
                    l.setIcon(null);
                }
            }
            return c;
        }
    }

    private static class Arrow implements Icon, Serializable {

        private boolean descending;
        private int size;

        public Arrow(boolean descending, int size) {
            this.descending = descending;
            this.size = size;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {

            // Override base size with a value calculated from the
            // component's font.
            updateSize(c);

            //Color color = c == null ? Color.BLACK : c.getForeground();
            Color color = WDArrowColor;
            g.setColor(color);

            int npoints = 3;
            int[] xpoints = new int[]{0, size / 2, size};
            int[] ypoints = descending ? new int[]{0, size, 0} : new int[]{
                    size, 0, size};

            Polygon triangle = new Polygon(xpoints, ypoints, npoints);

            // Center icon vertically within the column heading label.
            int dy = (c.getHeight() - size) / 2;

            g.translate(x, dy);
            g.drawPolygon(triangle);
            g.fillPolygon(triangle);
            g.translate(-x, -dy);
        }

        public int getIconWidth() {
            return size;
        }

        public int getIconHeight() {
            return size;
        }

        private void updateSize(Component c) {
            if (c != null) {
                FontMetrics fm = c.getFontMetrics(c.getFont());
                int baseHeight = fm.getAscent();
                size = (int) (baseHeight * 3 / 4);// * Math.pow(0.8,
            }
        }
    }
}
