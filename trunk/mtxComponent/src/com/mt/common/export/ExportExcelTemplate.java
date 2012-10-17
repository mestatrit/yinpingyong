package com.mt.common.export;

import com.mt.common.gui.table.MCNumber;
import com.mt.log.MTLog;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.RGB;
import jxl.format.UnderlineStyle;
import jxl.write.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.lang.Number;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jxl.format.Colour;

/**
 * 通用的Excel导入模板生成模块
 */
public class ExportExcelTemplate {

    private final Logger logger = LoggerFactory.getLogger(ExportExcelTemplate.class);
    protected JTable table;
    protected String titleName;
    protected File saveFile;
    // 设置cell的格式
    protected NumberFormat nf1 = new NumberFormat("0.0000");
    protected NumberFormat nf2 = new NumberFormat("0.00");
    protected NumberFormat nf3 = new NumberFormat("0.000000");
    protected WritableCellFormat contentFormat = new WritableCellFormat(NumberFormats.DEFAULT);
    protected WritableCellFormat contentFormat1 = new WritableCellFormat(nf1);
    protected WritableCellFormat contentFormat2 = new WritableCellFormat(nf2);
    protected WritableCellFormat contentFormat3 = new WritableCellFormat(nf3);

    private boolean colorFlag = false;     //是否显示导出EXCEL字体颜色标志位,默认不显示
    private Map<Integer, Colour> forColorMap = Collections.synchronizedMap(new HashMap<Integer, Colour>());
    private Map<Integer, Colour> bakColorMap = Collections.synchronizedMap(new HashMap<Integer, Colour>());

    /**
     * 构造函数
     *
     * @param table
     * @param titleName
     * @param saveFile
     */
    public ExportExcelTemplate(JTable table, String titleName, File saveFile, boolean colorFlag) {
        this.table = table;
        this.titleName = titleName;
        this.saveFile = saveFile;
        this.colorFlag = colorFlag;
    }

    public ExportExcelTemplate(JTable table, String titleName, File saveFile) {
        this(table, titleName, saveFile, false);
    }

    /**
     * 构造函数
     *
     * @param titleName
     * @param saveFile
     */
    public ExportExcelTemplate(String titleName, File saveFile, boolean colorFlag) {
        this.titleName = titleName;
        this.saveFile = saveFile;
        this.colorFlag = colorFlag;
    }

    public ExportExcelTemplate(String titleName, File saveFile) {
        this(titleName, saveFile, false);
    }

    /**
     * 无参构造函数
     */
    public ExportExcelTemplate() {

    }

    /**
     * 得到表格列宽，隐藏列为0
     */
    protected int[] getColumnWidthNoHid() {
        int[] cellsWidth = new int[table.getColumnCount()];
        for (int i = 0; i < table.getColumnCount(); i++) {
            cellsWidth[i] = table.getColumnModel().getColumn(i).getWidth();
        }
        return cellsWidth;
    }

    /**
     * 得到JTable表格各栏位值
     *
     * @param table
     * @return
     */
    public List<List> getTableValue(JTable table) {
        List<List> lineValue = new ArrayList<List>();
        for (int row = 0; row < table.getRowCount(); row++) {
            List tem = new ArrayList();
            for (int column = 0; column < table.getColumnCount(); column++) {
                Object value = null;
                if (table.getColumnName(column).equals("抵押品") || table.getColumnName(column).equals("抵押品券面金额")) {
                    value = table.getValueAt(row, column).toString().replaceAll(",", ";");
                } else {
                    value = table.getValueAt(row, column);
                }
                value = (value == null ? "" : value);
                tem.add(value);
            }
            lineValue.add(tem);
        }
        return lineValue;
    }

    /**
     * 得到JTable定义的栏位名称
     *
     * @param table
     * @return
     */
    public String[] getColumnName(JTable table) {
        String[] colName = new String[table.getColumnCount()];
        for (int column = 0; column < table.getColumnCount(); column++) {
            colName[column] = table.getColumnName(column);
        }
        return colName;
    }

    /**
     * 得到非隐藏列的列名
     *
     * @param table
     * @param colWidth
     * @return
     */
    public Object[] getTitleName(JTable table, int[] colWidth) {
        ArrayList<String> colName = new ArrayList<String>();
        for (int column = 0; column < table.getColumnCount(); column++) {
            if (colWidth[column] == 0) {
                continue;
            }
            colName.add(table.getColumnName(column));
        }
        return colName.toArray();
    }

    /**
     * 根据列名得到Excel CellFormat
     *
     * @param colName
     * @return
     */
    public WritableCellFormat getCellFormat(String colName) {

        if (colName.contains("金额") || colName.contains("面额")) {
            if (colName.contains("万")) {
                return contentFormat3;
            } else {
                return contentFormat2;
            }
        } else if (colName.contains("年付息次数")) {
            return contentFormat;
        } else {
            return contentFormat1;
        }
    }

    /**
     * @param colName
     * @param font
     * @return
     */
    private WritableCellFormat getCellFormat(String colName, WritableFont font) {
        if (colName.contains("金额") || colName.contains("面额")) {
            if (colName.contains("万")) {
                return new WritableCellFormat(font, nf3);
            } else {
                return new WritableCellFormat(font, nf2);
            }
        } else if (colName.contains("年付息次数")) {
            return new WritableCellFormat(font, NumberFormats.DEFAULT);
        } else {
            return new WritableCellFormat(font, nf1);
        }
    }

    protected Double convertDouble(String data) {
        data = data.replaceAll(",", "");
        double number;
        try {
            number = Double.parseDouble(data);
        } catch (NumberFormatException e) {
            return null;
        }
        return number;
    }

    /**
     * 导出excel
     *
     * @param dataList
     * @param columnName
     * @param cellClassList
     * @return
     */
    public boolean export(List<List> dataList, String[] columnName, Class[] cellClassList) {
        if (saveFile == null) {
            return false;
        }

        if (dataList.isEmpty()) {
            JOptionPane.showMessageDialog(table, "没有记录需要导出");
            return false;
        }
        jxl.write.WritableWorkbook wwb = null;
        try {
            wwb = Workbook.createWorkbook(saveFile);
            jxl.write.WritableSheet ws = wwb.createSheet(titleName, 0);

            // 设置title
            for (int column = 0; column < columnName.length; column++) {
                ws.addCell(new jxl.write.Label(column, 0, columnName[column].toString()));
            }

            // 设置宽度
            for (int wid = 0; wid < columnName.length; wid++) {
                ws.setColumnView(wid, 15);
            }

            contentFormat1.setAlignment(Alignment.RIGHT);
            contentFormat2.setAlignment(Alignment.RIGHT);
            contentFormat3.setAlignment(Alignment.RIGHT);

            List data;
            WritableCellFormat wcf;
            // 设置单元格数据
            for (int i = 0; i < dataList.size(); i++) {
                data = dataList.get(i);
                for (int col = 0; col < columnName.length; col++) {
                    String colName = columnName[col];
                    Class<?> cellClass = cellClassList[col];
                    wcf = getCellFormat(colName);
                    if (cellClass == Number.class || cellClass == Double.class || cellClass == Integer.class || cellClass == MCNumber.class) {
                        Object obj = data.get(col);
                        if (obj == null) {
                            ws.addCell(new jxl.write.Label(col, i + 1, "", contentFormat));
                        } else if (obj.toString().equals("") || convertDouble(obj.toString().replaceAll(",", "")) == null) {
                            ws.addCell(new jxl.write.Label(col, i + 1, obj.toString(), contentFormat));
                        } else {
                            if (wcf.equals(contentFormat)) {
                                ws.addCell(new jxl.write.Label(col, i + 1, data.get(col) == null ? "" : data.get(col).toString(), contentFormat));
                            } else {
                                Double d = Double.valueOf(obj.toString().replaceAll(",", ""));
                                ws.addCell(new jxl.write.Number(col, i + 1, d, wcf));
                            }
                        }
                    } else {
                        ws.addCell(new jxl.write.Label(col, i + 1, data.get(col) == null ? "" : data.get(col).toString(), contentFormat));
                    }
                }
            }

            wwb.write();
        } catch (Exception e) {
            logger.error("导出Excel失败", e);
            JOptionPane.showMessageDialog(table, TableCopyExporter.SaveErrorInfo);
            return false;
        } finally {
            try {
                if (wwb != null) {
                    wwb.close();
                }
            } catch (Exception ex) {
            }
        }

        return true;
    }

    /**
     * 导出excel
     *
     * @param colWidth
     * @param dataList
     * @param columnName
     * @param colTitleName
     * @param cellClassList
     * @return
     */
    protected boolean export(int[] colWidth, List<List> dataList, String[] columnName, Object[] colTitleName, Class[] cellClassList) {
        if (saveFile == null) {
            return false;
        }

        if (dataList.isEmpty()) {
            JOptionPane.showMessageDialog(table, "没有记录需要导出");
            return false;
        }
        jxl.write.WritableWorkbook wwb = null;
        try {
            wwb = Workbook.createWorkbook(saveFile);
            jxl.write.WritableSheet ws = wwb.createSheet(titleName, 0);

            // 设置title
            for (int column = 0; column < colTitleName.length; column++) {
                ws.addCell(new jxl.write.Label(column, 0, colTitleName[column].toString()));
            }

            // 设置宽度
            for (int wid = 0; wid < columnName.length; wid++) {
                if (colWidth[wid] == 0) {
                    continue;
                }
                ws.setColumnView(wid, 15);
            }

            contentFormat1.setAlignment(Alignment.RIGHT);
            contentFormat2.setAlignment(Alignment.RIGHT);
            contentFormat3.setAlignment(Alignment.RIGHT);

            List data;
            WritableCellFormat wcf;
            // 设置单元格数据
            int col_flag = 0;
            for (int i = 0; i < dataList.size(); i++) {
                data = dataList.get(i);
                col_flag = 0;
                for (int col = 0; col < columnName.length; col++) {
                    String colName = columnName[col];
                    if (colWidth[col] == 0) {
                        continue;
                    }
                    Class<?> cellClass = cellClassList[col];
                    wcf = getCellFormat(colName);
                    if (cellClass == Number.class || cellClass == Double.class || cellClass == Integer.class) {
                        Object obj = data.get(col);
                        if (obj.toString().equals("") || convertDouble(obj.toString().replaceAll(",", "")) == null) {
                            ws.addCell(new jxl.write.Label(col_flag, i + 1, obj.toString(), contentFormat));
                        } else {
                            if (wcf.equals(contentFormat)) {
                                ws.addCell(new jxl.write.Label(col_flag, i + 1, data.get(col).toString(), contentFormat));
                            } else {
                                if (wcf.equals(contentFormat)) {
                                    ws.addCell(new jxl.write.Label(col_flag, i + 1, data.get(col).toString(), contentFormat));
                                } else {
                                    Double d = Double.valueOf(obj.toString().replaceAll(",", ""));
                                    ws.addCell(new jxl.write.Number(col_flag, i + 1, d, wcf));
                                }
                            }

                        }
                    } else if (cellClass == MCNumber.class) {
                        Object obj = data.get(col);

                        if (obj.toString().equals("") || convertDouble(obj.toString().replaceAll(",", "")) == null) {
                            ws.addCell(new jxl.write.Label(col_flag, i + 1, obj.toString(), contentFormat));
                        } else {
                            if (wcf.equals(contentFormat)) {
                                ws.addCell(new jxl.write.Label(col_flag, i + 1, data.get(col).toString(), contentFormat));
                            } else {
                                Double d = Double.valueOf(obj.toString().replaceAll(",", ""));
                                ws.addCell(new jxl.write.Number(col_flag, i + 1, d, wcf));
                            }

                        }
                    } else {
                        ws.addCell(new jxl.write.Label(col_flag, i + 1, data.get(col).toString(), contentFormat));
                    }
                    col_flag += 1;
                }
            }

            wwb.write();
        } catch (Exception e) {
            logger.error("导出Excel失败", e);
            JOptionPane.showMessageDialog(table, TableCopyExporter.SaveErrorInfo);
            return false;
        } finally {
            try {
                if (wwb != null) {
                    wwb.close();
                }
            } catch (Exception ex) {
            }
        }

        return true;
    }

    /**
     * @param colWidth
     * @param dataList
     * @param columnName
     * @param colTitleName
     * @param cellClassList
     * @param renderArray
     * @return
     */
    protected boolean export(int[] colWidth, List<List> dataList, String[] columnName, Object[] colTitleName, Class[] cellClassList, TableCellRenderer[] renderArray) {
        if (saveFile == null) {
            return false;
        }

        if (dataList.isEmpty()) {
            JOptionPane.showMessageDialog(table, "没有记录需要导出");
            return false;
        }
        jxl.write.WritableWorkbook wwb = null;
        try {
            wwb = Workbook.createWorkbook(saveFile);
            jxl.write.WritableSheet ws = wwb.createSheet(titleName, 0);

            // 设置title
            for (int column = 0; column < colTitleName.length; column++) {
                ws.addCell(new jxl.write.Label(column, 0, colTitleName[column].toString()));
            }

            // 设置宽度
            for (int wid = 0; wid < columnName.length; wid++) {
                if (colWidth[wid] == 0) {
                    continue;
                }
                ws.setColumnView(wid, 15);
            }

            contentFormat1.setAlignment(Alignment.RIGHT);
            contentFormat2.setAlignment(Alignment.RIGHT);
            contentFormat3.setAlignment(Alignment.RIGHT);
            List data;
            WritableCellFormat wcf;
            // 设置单元格数据
            int col_flag = 0;
            for (int i = 0; i < dataList.size(); i++) {
                data = dataList.get(i);
                col_flag = 0;

                for (int col = 0; col < columnName.length; col++) {
                    String colName = columnName[col];
                    if (colWidth[col] == 0) {
                        continue;
                    }
                    Class<?> cellClass = cellClassList[col];
                    wcf = getCellFormat(colName);
                    TableCellRenderer r = renderArray[col];
                    Component label = r.getTableCellRendererComponent(this.table, data.get(col), false, false, i, col);
                    if (label != null) {
                        Color color = label.getForeground();
                        Color bakcolor = label.getBackground();
                        int forRgb = color.getRGB();
                        int backRgb = bakcolor.getRGB();
                        if (!forColorMap.containsKey(forRgb)) {
                            forColorMap.put(forRgb, getNearestColour(color));
                        }
                        if (!bakColorMap.containsKey(backRgb)) {
                            bakColorMap.put(backRgb, getNearestColour(bakcolor));
                        }
                        Colour cu = forColorMap.get(forRgb);
                        Colour cubakcolor = bakColorMap.get(backRgb);
                        WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, cu);
                        wcf = getCellFormat(colName, font);
                        wcf.setBackground(cubakcolor);
                        contentFormat = new WritableCellFormat(font, NumberFormats.DEFAULT);
                        contentFormat.setBackground(cubakcolor);
                    }
                    if (cellClass == Number.class || cellClass == Double.class || cellClass == Integer.class) {
                        Object obj = data.get(col);
                        if (obj.toString().equals("") || convertDouble(obj.toString().replaceAll(",", "")) == null) {
                            ws.addCell(new jxl.write.Label(col_flag, i + 1, obj.toString(), contentFormat));
                        } else {
                            if (wcf.equals(contentFormat)) {
                                ws.addCell(new jxl.write.Label(col_flag, i + 1, data.get(col).toString(), wcf));
                            } else {
                                if (wcf.equals(contentFormat)) {
                                    ws.addCell(new jxl.write.Label(col_flag, i + 1, data.get(col).toString(), wcf));
                                } else {
                                    Double d = Double.valueOf(obj.toString().replaceAll(",", ""));
                                    ws.addCell(new jxl.write.Number(col_flag, i + 1, d, wcf));
                                }
                            }

                        }
                    } else if (cellClass == MCNumber.class) {
                        Object obj = data.get(col);

                        if (obj.toString().equals("") || convertDouble(obj.toString().replaceAll(",", "")) == null) {
                            ws.addCell(new jxl.write.Label(col_flag, i + 1, obj.toString(), contentFormat));
                        } else {
                            if (wcf.equals(contentFormat)) {
                                ws.addCell(new jxl.write.Label(col_flag, i + 1, data.get(col).toString(), wcf));
                            } else {
                                Double d = Double.valueOf(obj.toString().replaceAll(",", ""));
                                ws.addCell(new jxl.write.Number(col_flag, i + 1, d, wcf));
                            }

                        }
                    } else {
                        ws.addCell(new jxl.write.Label(col_flag, i + 1, data.get(col).toString(), contentFormat));
                    }
                    col_flag += 1;
                }
            }

            wwb.write();
        } catch (Exception e) {
            logger.error("导出Excel失败", e);
            JOptionPane.showMessageDialog(table, TableCopyExporter.SaveErrorInfo);
            return false;
        } finally {
            try {
                if (wwb != null) {
                    wwb.close();
                }
            } catch (Exception ex) {
            }
        }

        return true;
    }

    /**
     * excel处理
     */
    public boolean exportExcel() {
        int[] colWidth = getColumnWidthNoHid();
        List<List> dataList = getTableValue(table);
        String[] columnName = getColumnName(table);
        Object[] colTitleName = getTitleName(table, colWidth);
        Class[] cellClassList = getColumnClass(table);
        if (isColorFlag()) {
            TableCellRenderer[] renderArray = null;
            try {
                renderArray = getTableCellRender(table);
            } catch (Exception ex) {
                MTLog.error(ex.getMessage(), ex);
            }
            if (renderArray != null) {
                return export(colWidth, dataList, columnName, colTitleName, cellClassList, renderArray);
            } else {
                return export(colWidth, dataList, columnName, colTitleName, cellClassList);
            }
        } else {
            return export(colWidth, dataList, columnName, colTitleName, cellClassList);
        }
    }

    /**
     * 得到table columnclass
     *
     * @param table
     * @return
     */
    public Class[] getColumnClass(JTable table) {
        Class[] colClass = new Class[table.getColumnCount()];
        for (int column = 0; column < table.getColumnCount(); column++) {
            colClass[column] = table.getModel().getColumnClass(column);
        }
        return colClass;
    }

    /**
     * 获取table的DefaultTableCellRenderer数组
     *
     * @param table
     * @return
     */
    public TableCellRenderer[] getTableCellRender(JTable table) {
        TableCellRenderer[] render = new DefaultTableCellRenderer[table.getColumnCount()];
        for (int i = 0; i < table.getColumnCount(); i++) {
            render[i] = (DefaultTableCellRenderer) table.getDefaultRenderer(table.getModel().getColumnClass(i));
        }
        return render;
    }

    public boolean isColorFlag() {
        return colorFlag;
    }

    public void setColorFlag(boolean colorFlag) {
        this.colorFlag = colorFlag;
    }

    /**
     * 将java.awt.Color转换成jxl.format.Colour
     *
     * @param awtColor
     * @return
     */
    private Colour getNearestColour(Color awtColor) {
        Colour colour = null;
        Colour[] colours = Colour.getAllColours();
        if (colours != null && colours.length > 0) {
            int diff = 0;
            int minDiff = 999;
            for (Colour c : colours) {
                RGB rgb = c.getDefaultRGB();
                diff = Math.abs(rgb.getRed() - awtColor.getRed()) + Math.abs(rgb.getGreen() - awtColor.getGreen()) + Math.abs(rgb.getBlue() - awtColor.getBlue());
                if (diff < minDiff) {
                    minDiff = diff;
                    colour = c;
                }
            }
        }
        if (colour == null) {
            colour = Colour.BLACK;
        }
        return colour;
    }
}
