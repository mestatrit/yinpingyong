package com.mt.common.selectionBind;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mt.common.dynamicDataDef.*;
import com.mt.common.export.ParagraphCN;
import jxl.Workbook;
import jxl.write.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * FieldMap数据文件导出器
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2010-3-20
 * Time: 23:00:52
 * To change this template use File | Settings | File Templates.
 */
public class MTFieldMapDataFileExporter {

    static private WritableCellFormat[] numberFormat;

    /**
     * 支持的最大小数位数
     */
    static private final int MaxFraction = 22;

    /**
     * FieldMapNode导出Excel
     *
     * @param data
     * @param file
     * @param fcs
     */
    static public void exportFieldMapNodeExcel(FieldMapNode data, File file, String... fcs) throws IOException, WriteException {
        exportFieldMapNodeExcel(data, file, new FieldColumnList(fcs));
    }

    /**
     * FieldMapNode导出Excel
     *
     * @param data
     * @param file
     * @param fcs
     */
    static public void exportFieldMapNodeExcel(FieldMapNode data, File file, FieldColumnList fcs) throws IOException, WriteException {
        WritableWorkbook wwb = null;
        try {
            createNumberFormat();
            List<List<WritableCell>> list = getFieldMapNodeCellList(data, fcs);
            List<WritableCell> cList = getColumnCellList(fcs);

            wwb = Workbook.createWorkbook(file);
            WritableSheet ws = wwb.createSheet("SheetA", 0);
            for (int i = 0; i < cList.size(); i++) {
                ws.setColumnView(i, 17);
            }
            for (WritableCell cell : cList) {
                ws.addCell(cell);
            }

            for (List<WritableCell> cells : list) {
                for (WritableCell cell : cells) {
                    ws.addCell(cell);
                }
            }
            wwb.write();
        } finally {
            if (wwb != null) {
                wwb.close();
            }
        }
    }

    /**
     * FieldMapSet导出Excel
     *
     * @param data
     * @param file
     * @param fcs
     */
    static public void exportFieldMapSetExcel(FieldMapSet data, File file, String... fcs) throws IOException, WriteException {
        exportFieldMapSetExcel(data, file, new FieldColumnList(fcs));

    }

    /**
     * FieldMapSet导出Excel
     *
     * @param data
     * @param file
     * @param fcs
     */
    static public void exportFieldMapSetExcel(FieldMapSet data, File file, FieldColumnList fcs) throws IOException, WriteException {
        exportFieldMapListExcel(data.toFieldMapList(), file, fcs);
    }

    /**
     * 将一组FieldMapSet导出Excel
     *
     * @param data
     * @param fcs
     * @param file
     * @throws IOException
     * @throws WriteException
     */
    static public void exportFieldMapSetListExcel(FieldMapSet[] data, FieldColumnList[] fcs, File file) throws IOException, WriteException {
        exportFieldMapSetListExcel(Arrays.asList(data), Arrays.asList(fcs), file);
    }

    /**
     * 将一组FieldMapSet导出Excel
     *
     * @param data
     * @param fcs
     * @param file
     */
    static public void exportFieldMapSetListExcel(List<FieldMapSet> data, List<FieldColumnList> fcs, File file) throws IOException, WriteException {
        WritableWorkbook wwb = null;
        try {
            wwb = Workbook.createWorkbook(file);
            WritableSheet ws = wwb.createSheet("SheetA", 0);
            int row = 0;
            for (int i = 0; i < data.size(); i++) {
                FieldMapSet fms = data.get(i);
                FieldColumnList fcl = fcs.get(i);
                row = exportFieldMapListWritableSheet(ws, row, fms.toFieldMapList(), fcl);
            }
            wwb.write();
        } finally {
            if (wwb != null) {
                wwb.close();
            }
        }
    }

    /**
     * 创建多个sheet，将多组FieldMapSet分别写入对应sheet
     *
     * @param sheetList
     * @param data
     * @param fsc
     * @param file
     * @throws IOException
     * @throws WriteException
     */
    static public void exportFieldMapSetListExcel(List<String> sheetList, HashMap<String, List<FieldMapSet>> data, HashMap<String, List<FieldColumnList>> fsc, File file) throws IOException, WriteException {
        WritableWorkbook wwb = null;
        try {
            wwb = Workbook.createWorkbook(file);
            for (int s = 0; s < sheetList.size(); s++) {
                String sheetName = sheetList.get(s);
                WritableSheet ws = wwb.createSheet(sheetName, s);
                List<FieldMapSet> sheetData = data.get(sheetName);
                List<FieldColumnList> sheetFsc = fsc.get(sheetName);
                int row = 0;
                for (int i = 0; i < sheetData.size(); i++) {
                    FieldMapSet fms = sheetData.get(i);
                    FieldColumnList fc1 = sheetFsc.get(i);
                    row = exportFieldMapListWritableSheet(ws, row, fms.toFieldMapList(), fc1);
                }
            }

            wwb.write();
        } finally {
            if (wwb != null) {
                wwb.close();
            }
        }
    }

    /**
     * FieldMapList导出Excel
     *
     * @param data
     * @param file
     * @param fcs
     * @throws IOException
     * @throws WriteException
     */
    static public void exportFieldMapListExcel(List<? extends FieldMap> data, File file, FieldColumnList fcs) throws IOException, WriteException {
        WritableWorkbook wwb = null;
        try {
            wwb = Workbook.createWorkbook(file);
            WritableSheet ws = wwb.createSheet("SheetA", 0);
            exportFieldMapListWritableSheet(ws, 0, data, fcs);
            wwb.write();
        } finally {
            if (wwb != null) {
                wwb.close();
            }
        }
    }

    /**
     * 将数据写到WritableSheet中
     *
     * @param ws
     * @param row
     * @param data
     * @param fcs
     * @return
     * @throws WriteException
     */
    static private int exportFieldMapListWritableSheet(WritableSheet ws, int row, List<? extends FieldMap> data, FieldColumnList fcs) throws WriteException {
        createNumberFormat();
        for (int i = 0; i < fcs.getFieldColumnCount(); i++) {
            ws.setColumnView(i, 17);
            ws.addCell(new Label(i, row, fcs.getFieldColumn(i).getColumnName()));
        }
        for (int i = 0; i < data.size(); i++) {
            FieldMap fm = data.get(i);
            for (int j = 0; j < fcs.getFieldColumnCount(); j++) {
                String fName = fcs.getFieldColumn(j).getFieldName();
                String type = fcs.getFieldColumn(j).getConverterType();
                String format = fcs.getFieldColumn(j).getViewFormat();
                Field f = fm.getField(fName);
                if (f == null) {
                    ws.addCell(new Label(j, i + row + 1, ""));
                    continue;
                }
                if (type != null) {
                    if (type.equals("Number") || type.equals("NumberYW") || type.equals("NumberFP")) {
                        try {
                            double v = Double.NaN;
                            if (type.equals("Number")) {
                                v = f.getDoubleValue();
                            } else if (type.equals("NumberYW")) {
                                v = f.getDoubleValue_YW();
                            } else {
                                v = f.getDoubleValue_FP();
                            }
                            if (Double.isNaN(v)) {
                                ws.addCell(new Label(j, i + row + 1, f.getStringValue()));
                            } else {
                                WritableCellFormat rs = null;
                                if (format != null) {
                                    rs = getNCellFormat(format);
                                }
                                if (rs == null) {
                                    ws.addCell(new jxl.write.Number(j, i + row + 1, v));
                                } else {
                                    ws.addCell(new jxl.write.Number(j, i + row + 1, v, rs));
                                }
                            }
                        } catch (Exception e) {
                            ws.addCell(new Label(j, i + row + 1, f.getStringValue()));
                        }
                    } else if (type.equals("Date")) {
                        try {
                            if (format != null) {
                                ws.addCell(new Label(j, i + row + 1, f.getDateStringValue(format)));
                            } else {
                                ws.addCell(new Label(j, i + row + 1, f.getDateStringValue()));
                            }
                        } catch (Throwable t) {
                            ws.addCell(new Label(j, i + row + 1, f.getStringValue()));
                        }
                    } else {
                        ws.addCell(new Label(j, i + row + 1, f.getStringValue()));
                    }
                } else {
                    ws.addCell(new Label(j, i + row + 1, f.getStringValue()));
                }
            }
        }
        return row + data.size() + 2;
    }

    /**
     * FieldMapNode导出PDF
     *
     * @param data
     * @param file
     * @param fcs
     */
    static public void exportFieldMapNodePdf(FieldMapNode data, File file, String... fcs) throws DocumentException, IOException {
        exportFieldMapNodePdf(data, file, new FieldColumnList(fcs));
    }

    /**
     * FieldMapNode导出PDF
     *
     * @param data
     * @param file
     * @param fcs
     */
    static public void exportFieldMapNodePdf(FieldMapNode data, File file, FieldColumnList fcs) throws DocumentException, IOException {
        List<FieldMapNode> list = new ArrayList<FieldMapNode>();
        changeFieldMapNodeList(data, list);
        exportFieldMapListPdf(list, file, fcs);
    }

    /**
     * FieldMapSet导出PDF
     *
     * @param data
     * @param file
     * @param fcs
     */
    static public void exportFieldMapSetPdf(FieldMapSet data, File file, String... fcs) throws DocumentException, IOException {
        exportFieldMapSetPdf(data, file, new FieldColumnList(fcs));
    }

    /**
     * FieldMapSet导出PDF
     *
     * @param data
     * @param file
     * @param fcs
     */
    static public void exportFieldMapSetPdf(FieldMapSet data, File file, FieldColumnList fcs) throws DocumentException, IOException {
        exportFieldMapListPdf(data.toFieldMapList(), file, fcs);
    }

    /**
     * FieldMapList导出PDF
     *
     * @param data
     * @param file
     * @param fcs
     */
    static public void exportFieldMapListPdf(List<? extends FieldMap> data, File file, FieldColumnList fcs) throws DocumentException, IOException {
        com.lowagie.text.Document document = null;
        PdfWriter docWriter = null;
        try {
            com.lowagie.text.Font fontChinese = ParagraphCN.getChineseFont(10);
            document = new com.lowagie.text.Document();
            docWriter = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable pdfTable = new PdfPTable(fcs.getFieldColumnCount());
            //列头
            for (int i = 0; i < fcs.getFieldColumnCount(); i++) {
                PdfPCell cell = new PdfPCell(new Paragraph(fcs.getFieldColumn(i).getColumnName(), fontChinese));
                cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
                pdfTable.addCell(cell);
            }
            //内容
            for (FieldMap fm : data) {
                for (int i = 0; i < fcs.getFieldColumnCount(); i++) {
                    FieldColumn fc = fcs.getFieldColumn(i);
                    Field f = fm.getField(fc.getFieldName());
                    String type = fc.getConverterType();
                    String vf = fc.getViewFormat();
                    PdfPCell cell = null;
                    if (f != null) {
                        String value = "";
                        if (type.equals("Number")) {
                            if (vf != null && !vf.equals("")) {
                                value = f.getNumberStringValue(Integer.parseInt(vf));
                            } else {
                                value = f.getNumberStringValue();
                            }
                        } else if (type.equals("NumberYW")) {
                            if (vf != null && !vf.equals("")) {
                                value = f.getNumberStringValue_YW(Integer.parseInt(vf));
                            } else {
                                value = f.getNumberStringValue_YW();
                            }
                        } else if (type.equals("NumberFP")) {
                            if (vf != null && !vf.equals("")) {
                                value = f.getNumberStringValue_FP(Integer.parseInt(vf));
                            } else {
                                value = f.getNumberStringValue_FP();
                            }
                        } else if (type.equals("Date")) {
                            if (vf != null && !vf.equals("")) {
                                value = f.getDateStringValue(vf);
                            } else {
                                value = f.getStringValue();
                            }
                        } else {
                            value = f.getStringValue();
                        }
                        Paragraph pg = new Paragraph(value, fontChinese);
                        cell = new PdfPCell(pg);
                        if (type.equals("Number") || type.equals("NumberYW") || type.equals("NumberFP")) {
                            cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
                        } else {
                            cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_LEFT);
                        }
                    } else {
                        Paragraph pg = new Paragraph("", fontChinese);
                        cell = new PdfPCell(pg);
                    }
                    pdfTable.addCell(cell);
                }
            }
            document.add(pdfTable);
        } finally {
            if (document != null) {
                document.close();
            }
            if (docWriter != null) {
                docWriter.close();
            }
        }
    }

    /**
     * FieldMapNode导出XML文件
     *
     * @param data
     * @param file
     */
    static public void exportFieldMapNodeXML(FieldMapNode data, File file) throws IOException {
        exportStringToFile(FieldMapUtil.createXMLString(data), file);
    }

    /**
     * FieldMapSet导出XML文件
     *
     * @param data
     * @param file
     */
    static public void exportFieldMapSetXML(FieldMapSet data, File file) throws IOException {
        exportStringToFile(FieldMapUtil.createXMLString(data), file);
    }

    /**
     * FieldMap导出XML文件
     *
     * @param data
     * @param file
     */
    static public void exportFieldMapXML(FieldMap data, File file) throws IOException {
        exportStringToFile(FieldMapUtil.createXMLString(data), file);
    }

    static private void exportStringToFile(String xmlString, File file) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            writer.write(xmlString);
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    static private void changeFieldMapNodeList(FieldMapNode node, List<FieldMapNode> list) {
        list.add(node);
        for (int i = 0; i < node.getChildCount(); i++) {
            changeFieldMapNodeList(node.getChildAt(i), list);
        }
    }

    static private void createNumberFormat() {
        numberFormat = new WritableCellFormat[MaxFraction];
        StringBuilder sb = new StringBuilder().append("0.");
        for (int i = 0; i < MaxFraction; i++) {
            sb.append("0");
            numberFormat[i] = new WritableCellFormat(new NumberFormat(sb.toString()));
        }
    }

    static private WritableCellFormat getNCellFormat(String format) throws WriteException {
        WritableCellFormat rs = null;
        int f = Integer.parseInt(format);
        if (f <= MaxFraction) {
            rs = numberFormat[f - 1];
        }
        return rs;
    }

    static int maxLevel = 0;
    static int rowIndex = 0;

    static private void procFieldMapNode(FieldMapNode node, int level, List<FieldMapNode> oldList, List<List<WritableCell>> newList, FieldColumnList fcs) {
        maxLevel = level > maxLevel ? level : maxLevel;
        rowIndex++;
        List<WritableCell> cellList = new ArrayList<WritableCell>();
        for (int i = 0; i < level; i++) {
            cellList.add(new Label(i, rowIndex, ""));
        }
        String fName = fcs.getFieldColumn(0).getFieldName();
        cellList.add(new Label(level, rowIndex, node.getStringValue(fName)));
        oldList.add(node);
        newList.add(cellList);
        //递归处理
        int count = node.getChildCount();
        for (int i = 0; i < count; i++) {
            procFieldMapNode(node.getChildAt(i), level + 1, oldList, newList, fcs);
        }
    }

    static private List<List<WritableCell>> getFieldMapNodeCellList(FieldMapNode node, FieldColumnList fcs) {
        List<FieldMapNode> oldList = new ArrayList<FieldMapNode>();
        List<List<WritableCell>> cellList = new ArrayList<List<WritableCell>>();
        maxLevel = 0;
        rowIndex = 0;
        procFieldMapNode(node, 0, oldList, cellList, fcs);
        maxLevel++;
        for (int i = 0; i < oldList.size(); i++) {
            List<WritableCell> cells = cellList.get(i);
            FieldMapNode on = oldList.get(i);
            for (int j = cells.size(); j < maxLevel; j++) {
                cells.add(new Label(j, i + 1, ""));
            }
            for (int j = 1; j < fcs.getFieldColumnCount(); j++) {
                String fName = fcs.getFieldColumn(j).getFieldName();
                String type = fcs.getFieldColumn(j).getConverterType();
                String format = fcs.getFieldColumn(j).getViewFormat();
                Field f = on.getField(fName);
                int col = (maxLevel - 1) + j;
                if (f == null) {
                    cells.add(new Label(col, i + 1, ""));
                    continue;
                }
                if (type != null) {
                    if (type.equals("Number") || type.equals("NumberYW") || type.equals("NumberFP")) {
                        try {
                            double v = Double.NaN;
                            if (type.equals("Number")) {
                                v = f.getDoubleValue();
                            } else if (type.equals("NumberYW")) {
                                v = f.getDoubleValue_YW();
                            } else {
                                v = f.getDoubleValue_FP();
                            }
                            if (Double.isNaN(v)) {
                                cells.add(new Label(col, i + 1, f.getStringValue()));
                            } else {
                                WritableCellFormat rs = null;
                                if (format != null) {
                                    rs = getNCellFormat(format);
                                }
                                if (rs == null) {
                                    cells.add(new jxl.write.Number(col, i + 1, v));
                                } else {
                                    cells.add(new jxl.write.Number(col, i + 1, v, rs));
                                }
                            }
                        } catch (Throwable t) {
                            cells.add(new Label(col, i + 1, f.getStringValue()));
                        }
                    } else if (type.equals("Date")) {
                        try {
                            if (format != null) {
                                cells.add(new Label(col, i + 1, f.getDateStringValue(format)));
                            } else {
                                cells.add(new Label(col, i + 1, f.getDateStringValue()));
                            }
                        } catch (Throwable t) {
                            cells.add(new Label(col, i + 1, f.getStringValue()));
                        }
                    } else {
                        if (f.getValue() == null)
                            cells.add(new Label(col, i + 1, ""));
                        else
                            cells.add(new Label(col, i + 1, f.getStringValue()));
                    }
                } else {
                    cells.add(new Label(col, i + 1, f.getStringValue()));
                }
            }
        }
        return cellList;
    }

    static private List<WritableCell> getColumnCellList(FieldColumnList fcs) {
        List<WritableCell> cList = new ArrayList<WritableCell>();
        cList.add(new Label(0, 0, fcs.getFieldColumn(0).getColumnName()));
        for (int i = 1; i < maxLevel; i++) {
            cList.add(new Label(i, 0, ""));
        }
        for (int i = 1; i < fcs.getFieldColumnCount(); i++) {
            cList.add(new Label((maxLevel - 1) + i, 0, fcs.getFieldColumn(i).getColumnName()));
        }
        return cList;
    }

    static public void main(String[] args) {

    }


}
