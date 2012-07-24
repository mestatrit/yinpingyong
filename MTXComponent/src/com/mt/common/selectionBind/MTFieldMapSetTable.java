package com.mt.common.selectionBind;

import com.mt.common.dynamicDataDef.Field;
import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapSet;
import com.mt.common.gui.MTXComponent.MTXGUIUtil;
import com.mt.common.gui.MTXComponent.MTXTable;
import com.mt.common.gui.MTXComponent.MTXTopView;
import com.mt.common.gui.table.MCDate;
import com.mt.common.gui.table.MCNumber;
import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 一个直接支持FieldMapSet的表格
 * 
 * @author hanhui
 * 
 * MTFieldMapSetTable和通讯层的绑定去除，保证控件内聚性（2012-07-24 yinpy）
 */
public class MTFieldMapSetTable extends MTXTable{

    /**
     * 数据模型
     */
    private FieldMapSet data;
    /**
     * 表格列和数据模型的关系定义
     */
    private FieldColumnList fcs;
    /**
     * FieldMapSet的表格模型封装
     */
    private FieldMapSetTableModel fTableModel;
    /**
     * 字段名和字段转换器的的映射表
     */
    private HashMap<String, FieldConverter> fConverterMap = new HashMap<String, FieldConverter>();

    /**
     * 构造一个空表格
     */
    public MTFieldMapSetTable() {
        this(null, new FieldColumnList());
    }

    /**
     * 从字符串描述的列配置信息构建一个FieldMapSetTable
     * 必须按照下面的顺序
     * 列名;字段名;转换类型;显示格式;存储格式;ColumnClass;是否可编辑
     * 比如:"到期日;endDate;Date;yyyy/MM/dd;yyyyMMdd",java.util.Date;false"
     *
     * @param fieldColumns
     */
    public MTFieldMapSetTable(String... fieldColumns) {
        this(null, fieldColumns);
    }

    /**
     * 从一个资源路径获取XML文件描述的列配置信息构建一个FieldMapSetTable
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
    public MTFieldMapSetTable(String path) {
        this(null, path);
    }

    /**
     * 从一个XMLDocument描述的列配置信息构建一个FieldMapSetTable
     *
     * @param xmlDoc
     */
    public MTFieldMapSetTable(Document xmlDoc) {
        this(null, xmlDoc);
    }

    /**
     * 从一个列配置信息构建一个FieldMapSetTable
     *
     * @param fcs
     */
    public MTFieldMapSetTable(FieldColumnList fcs) {
        this(null, fcs);
    }

    /**
     * 从一个FieldMapSet数据模型和字符串描述的列配置信息构建一个FieldMapSetTable
     * 必须按照下面的顺序
     * 列名;字段名;转换类型;显示格式;存储格式;ColumnClass;是否可编辑
     * 比如:"到期日;endDate;Date;yyyy/MM/dd;yyyyMMdd";java.util.Date;false"
     *
     * @param fms
     * @param fieldColumns
     */
    public MTFieldMapSetTable(FieldMapSet fms, String... fieldColumns) {
        this(fms, new FieldColumnList(fieldColumns));
    }

    /**
     * 从一个FieldMapSet数据模型和一个资源路径获取XML文件描述的列配置信息构建一个FieldMapSetTable
     *
     * @param fms
     * @param path
     */
    public MTFieldMapSetTable(FieldMapSet fms, String path) {
        this(fms, new FieldColumnList(path));
    }

    /**
     * 从一个FieldMapSet数据模型和一个XMLDocument描述的列配置信息构建一个FieldMapSetTable
     *
     * @param fms
     * @param xmlDoc
     */
    public MTFieldMapSetTable(FieldMapSet fms, Document xmlDoc) {
        this(fms, new FieldColumnList(xmlDoc));
    }

    /**
     * 从一个FieldMapSet数据模型和列配置信息构建一个FieldMapSetTable
     *
     * @param fms
     * @param fcs
     */
    public MTFieldMapSetTable(FieldMapSet fms, FieldColumnList fcs) {
        setRowHeight(25);
        fTableModel = new FieldMapSetTableModel();
        this.setDataModel(fTableModel);
        setFieldColumnList(fcs);
        setFieldMapSet(fms != null ? fms : new FieldMapSet(""));
    }

    /**
     * 设置FieldMapSet
     *
     * @param set
     */
    public void setFieldMapSet(FieldMapSet set) {
        this.data = set;
        fTableModel.fireTableDataChanged();
    }

    /**
     * 获得FieldMapSet
     *
     * @return
     */
    public FieldMapSet getFieldMapSet() {
        return data;
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
        this.fcs = fcs;
        this.setDataModel(this.fTableModel);//列模型应该随之改变
        //fTableModel.fireTableStructureChanged();
        super.loadHideColumn();
    }

    /**
     * 获得列配置信息
     *
     * @return
     */
    public FieldColumnList getFieldColumnList() {
        return fcs;
    }

    /**
     * 设置List<FieldMap>作为新的数据模型
     *
     * @param list
     */
    public void setFieldMapList(List<FieldMap> list) {
        setFieldMapSet(new FieldMapSet("").addFieldMapList(list));
    }

    /**
     * 在现有的数据模型上增加一批数据
     *
     * @param list
     */
    public void addFieldMapList(List<FieldMap> list) {
        if (this.data != null) {
            this.data.addFieldMapList(list);
            fTableModel.fireTableDataChanged();
        }

    }

    /**
     * 在现有的数据模型上增加一笔数据
     *
     * @param fm
     */
    public void addFieldMap(FieldMap fm) {
        if (this.data != null) {
            this.data.addFieldMap(fm);
            fTableModel.fireTableDataChanged();
        }
    }

    public void insertFieldMap(int idx, FieldMap fm) {
        if (this.data != null) {
            this.data.addFieldMap(idx, fm);
            fTableModel.fireTableDataChanged();
        }

    }

    /**
     * 更新一笔数据,通过表格的index
     *
     * @param index
     * @param fm
     */
    public void updateFieldMap(int index, FieldMap fm) {
        if (this.data != null && index >= 0) {
            this.data.updateFieldMap(viewRowIndexToModelRowIndex(index), fm);
            fTableModel.fireTableDataChanged();
        }
    }

    /**
     * 更新一笔数据,指明原数据
     *
     * @param oldfm
     * @param newfm
     */
    public void updateFieldMap(FieldMap oldfm, FieldMap newfm) {
        for (int i = 0; i < data.getFieldMapCount(); i++) {
            if (data.getFieldMap(i) == oldfm) {
                data.updateFieldMap(i, newfm);
                fTableModel.fireTableDataChanged();
                break;
            }
        }
    }

    /**
     * 更新当前选中的行
     *
     * @param newfm
     */
    public void updateSelectedFieldMap(FieldMap newfm) {
        int index = getSelectedRow();
        if (index >= 0) {
            updateFieldMap(index, newfm);
        }
    }

    /**
     * 删除一笔数据,通过index
     *
     * @param index
     */
    public void removeFieldMap(int index) {
        if (this.data != null && index >= 0) {
            this.data.removeFieldMap(viewRowIndexToModelRowIndex(index));
            fTableModel.fireTableDataChanged();
        }

    }

    /**
     * 删除一笔数据,通过数据
     *
     * @param fm
     */
    public void removeFieldMap(FieldMap fm) {
        for (int i = 0; i < data.getFieldMapCount(); i++) {
            if (data.getFieldMap(i) == fm) {
                data.removeFieldMap(i);
                fTableModel.fireTableDataChanged();
                break;
            }
        }
    }

    /**
     * 获得表格FieldMap的数量
     *
     * @return
     */
    public int getFieldMapCount() {
        return this.data == null ? 0 : this.data.getFieldMapCount();
    }

    /**
     * 获得一笔数据,一般你拿了数据是只读的，应该用这个方法
     * 当然拿到FieldMap后你也可以copy
     *
     * @param index
     * @return
     */
    public FieldMap getFieldMap(int index) {
        if (data != null) {
            return data.getFieldMap(viewRowIndexToModelRowIndex(index));
        } else {
            return null;
        }
    }

    /**
     * 复制一笔数据,一般你拿了数据要改写而又不想影响表格原有数据，应该用这个方法
     *
     * @param index
     * @return
     */
    public FieldMap copyFieldMap(int index) {
        if (data != null) {
            return data.getFieldMap(viewRowIndexToModelRowIndex(index)).copyFieldMap();
        } else {
            return null;
        }
    }

    /**
     * 获得被选中的FieldMap
     *
     * @return
     */
    public FieldMap getSelectedFieldMap() {
        List<FieldMap> fms = getSelectedFieldMaps();
        return fms.isEmpty() ? null : fms.get(0);
    }

    /**
     * 获得被选中的FieldMap集合
     *
     * @return
     */
    public List<FieldMap> getSelectedFieldMaps() {
        List<FieldMap> sData = new ArrayList<FieldMap>();
        int[] rows = getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            sData.add(getFieldMap(rows[i]));
        }
        return sData;
    }

    /**
     * 设置选中的FieldMap
     * @param fm
     */
    public void setSelectedFieldMap(FieldMap fm){
        for(int i = 0; i< getFieldMapCount();i++){
            FieldMap f = getFieldMap(i);
            if(f == fm){
                getSelectionModel().setSelectionInterval(i,i);
                break;
            }
        }
    }

    /**
     * 删除选中的数据
     *
     * @return
     */
    public List<FieldMap> removeSelectedFieldMaps() {
        List<FieldMap> sData = new ArrayList<FieldMap>();
        List<FieldMap> nData = new ArrayList<FieldMap>();
        int[] rows = getSelectedRows();
        if (rows.length == 0) {
            return sData;
        }
        for (int i = 0; i < getFieldMapCount(); i++) {
            FieldMap fm = getFieldMap(i);
            if (isRow(i, rows)) {
                sData.add(fm);
            } else {
                nData.add(fm);
            }
        }
        FieldMapSet fms = new FieldMapSet(getFieldMapSet().getName()).addFieldMapList(nData);
        setFieldMapSet(fms);
        return sData;
    }

    private boolean isRow(int row, int[] rows) {
        for (int r : rows) {
            if (r == row) {
                return true;
            }
        }
        return false;
    }

    /**
     * 复制被选中的FieldMap
     *
     * @return
     */
    public FieldMap copySelectedFieldMap() {
        List<FieldMap> fms = copySelectedFieldMaps();
        return fms.isEmpty() ? null : fms.get(0);
    }

    /**
     * 复制被选中的FieldMap集合
     *
     * @return
     */
    public List<FieldMap> copySelectedFieldMaps() {
        List<FieldMap> sData = new ArrayList<FieldMap>();
        int[] rows = getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            sData.add(copyFieldMap(rows[i]));
        }
        return sData;
    }

    /**
     * 清空表格数据
     */
    public void clearData() {
        setFieldMapSet(new FieldMapSet(""));
    }

    /**
     * 刷新表格数据
     */
    public void refreshData() {
        fTableModel.fireTableDataChanged();
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

    /**
     * 向服务器查询数据
     *
     * @param con
     * @param fid
     * @return
     */
    /*public MTFieldMapSetTable2 query(TBCServerConnection con, String fid) {
        return query(con, fid, null);
    }*/

    /**
     * 向服务器查询数据,可以指定查询参数
     *
     * @param con
     * @param fid
     * @param fm
     * @return
     */
   /* public MTFieldMapSetTable2 query(TBCServerConnection con, String fid, FieldMap fm) {
        return query(con, fid, true, fm);
    }*/

    /**
     * 向服务器查询数据,指定是否要锁住界面
     *
     * @param con
     * @param fid
     * @param isLock
     * @return
     */
    /*public MTFieldMapSetTable2 query(TBCServerConnection con, String fid, boolean isLock) {
        return query(con, fid, isLock, null);
    }*/

    /**
     * 向服务器查询数据,指定是否要锁住界面,指定查询参数
     *
     * @param con
     * @param fid
     * @param isLock
     * @param fm
     * @return
     */
   /* public MTFieldMapSetTable2 query(TBCServerConnection con, String fid, boolean isLock, FieldMap fm) {
        return query(con, fid, TBCServerConnection.DefaultTimeout, isLock, fm);
    }*/

    /**
     * 向服务器查询数据,指定超时时间
     *
     * @param con
     * @param fid
     * @param timeout
     * @return
     */
    /*public MTFieldMapSetTable2 query(TBCServerConnection con, String fid, int timeout) {
        return query(con, fid, timeout, true, null);
    }*/

    /**
     * 向服务器查询数据,指定查询参数和超时时间
     *
     * @param con
     * @param fid
     * @param timeout
     * @param isLock
     * @param fm
     * @return
     */
   /* public MTFieldMapSetTable2 query(TBCServerConnection con, String fid, int timeout, boolean isLock, FieldMap fm) {
        clearData();
        if (isLock) {
            lockView();
        }
        if (fm == null) {
            con.requestRemoteService(fid, timeout, this);
        } else {
            con.requestRemoteService(fid, fm, timeout, this);
        }
        return this;
    }*/

    /**
     * 查询数据返回处理
     */
    /*public void onMessage(final CommonMsg msg) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (!isShowing()) {//如果自己已经不显示了就忽略回来的消息
                    return;
                }
                unLockView();
                if (msg.isError()) {
                    if (msg.getErrrorType() == CommonMsg.NO_DATA_FOUND) {
                        JOptionPane.showMessageDialog(MTFieldMapSetTable2.this,
                                "没有查询到任何数据");
                    } else {
                        JOptionPane.showMessageDialog(MTFieldMapSetTable2.this,
                                "查询失败\n" + msg.getErrorMsg());
                    }
                } else {
                    setFieldMapSet(msg.getFieldMapSet());
                }
            }
        });
    }*/

    private void lockView() {
        MTXTopView topView = MTXGUIUtil.getMTXTopView(this);
        if (topView != null) {
            topView.startInfiniteLock();
        }
    }

    private void unLockView() {
        MTXTopView topView = MTXGUIUtil.getMTXTopView(this);
        if (topView != null) {
            topView.stopInfiniteLock();
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

    /**
     * 字段改变
     *
     * @param field
     * @param oldValue
     * @param row
     * @param rowIndex
     * @param colIndex
     */
    protected void fieldChanged(Field field, Object oldValue, FieldMap row, int rowIndex, int colIndex) {
        //子类可以覆盖这个函数以处理一些字段被更新后的动作
    }

    /**
     * 一个对FieldMapSet和FieldColumnList封装的TableModel
     *
     * @author hanhui
     */
    class FieldMapSetTableModel extends AbstractTableModel {

        public String getColumnName(int column) {
            String cn = fcs.getFieldColumn(column).getColumnName();
            if (cn.trim().equals("")) {//纯粹的空格字符串不应该占据列头空间
                return "";
            } else {
                return cn;
            }
        }

        public int getColumnCount() {
            return fcs == null ? 0 : fcs.getFieldColumnCount();
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return fcs.getFieldColumn(columnIndex).isEditable();
        }

        public Class<?> getColumnClass(int columnIndex) {
            FieldColumn fc = fcs.getFieldColumn(columnIndex);
            Class<?> cClass = fc.getColumnClass();
            if (cClass != null) {
                return cClass;
            }

            //如果没有配置ColumnClass,根据ConverterType生成ColumnClass
            String type = fcs.getFieldColumn(columnIndex).getConverterType();
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

        public int getRowCount() {
            return data == null ? 0 : data.getFieldMapCount();
        }

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            FieldColumn fc = fcs.getFieldColumn(columnIndex);
            Field field = data.getFieldMap(rowIndex).getField(fc.getFieldName());
            if (field != null) {
                Object oldValue = field.getValue();
                setField(field, aValue, fc);
                fieldChanged(field, oldValue, data.getFieldMap(rowIndex), rowIndex, columnIndex);
            } else {
                //在FieldColumn定义的FieldName在实际的FieldMapSet中没有找到
                //System.err.println(fc.getColumnName() + "对应的" + fc.getFieldName() + "在FieldMap中没有找到");
            }

        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            FieldColumn fc = fcs.getFieldColumn(columnIndex);
            Field field = data.getFieldMap(rowIndex).getField(fc.getFieldName());
            if (field != null) {
                try {
                    if (field.getValue() != null) {
                        return convertField(field, fc);
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    //如果转换失败返回field的原值
                    return field.getValue();
                }
            } else {
                //在FieldColumn定义的FieldName在实际的FieldMapSet中没有找到
                //System.err.println(fc.getColumnName() + "对应的" + fc.getFieldName() + "在FieldMap中没有找到");
                return null;
            }

        }
    }
}
