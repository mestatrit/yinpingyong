package com.mt.common.export;

import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.*;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mt.common.gui.MTXComponent.MTXFileChooser;
import com.mt.common.gui.MTXComponent.MTXTreeTable;
import com.mt.common.gui.table.MCNumber;
import com.mt.core.functionDef.ViewFunction;
import com.mt.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 把Table中的数据输出到PDF文件中,只对Table显示的内容
 *
 * @author @CL
 */
public class PdfExporter implements ActionListener {

    public static final int MAX_HEIGHT = 14400;
    public static final int MAX_WIDTH = 14400;
    private JTable table;
    private MTXTreeTable mtxTreeTable;
    private String title; //中文表头
    private String prefix; //文件名前缀,后面会跟以日期
    private int[] columnWidths; //表格各列长度,建PdfPTable时需要,没有的话为默认width
    private boolean isHid = true; //如果列宽为0;是否需要隐藏,现都需要隐藏
    private final Logger logger = LoggerFactory.getLogger(PdfExporter.class);

    public PdfExporter(
            JTable table,
            String title,
            String prefix) {
        this.table = table;
        this.title = title;
        this.prefix = prefix;

    }

    public PdfExporter(
            MTXTreeTable table,
            String title,
            String prefix) {
        this.mtxTreeTable = table;
        this.table = table.getWholeTable();
        this.title = title;
        this.prefix = prefix;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (mtxTreeTable != null) {
                mtxTreeTable.changeWholeTableUI();
            }
            File fileSave = createSaveFile();
            savePDF(fileSave, true);
        } finally {
            if (mtxTreeTable != null) {
                mtxTreeTable.changeFTableUI();
            }
        }
    }

    public synchronized void savePDF(File fileSave, boolean isRenderer) {
        if (fileSave == null) {
            return;
        }
        com.lowagie.text.Document document = null;
        PdfWriter docWriter = null;
        try {
            // Prepare fonts and documents
            // BaseFont bfChinese = BaseFont.createFont("STSong-Light",
            // "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED) ;

            Font fontChinese = ParagraphCN.getChineseFont(10);

            // 设置宽度,如果宽度为零的话,隐藏这列
            columnWidths = getTableAllColsWidth();

            document = new com.lowagie.text.Document(getPDFSize());
            docWriter = PdfWriter.getInstance(document, new FileOutputStream(
                    fileSave));

            document.open();
            // doc opened!! Hell is FREE~~
            // document.add(Image.getInstance("images/Telerate_logo.jpeg")) ;
            document.add(new Paragraph(title + "\r\n\n", fontChinese));
            // PDFTable 2

            PdfPTable pdfTable = null;

            if (isHid) {
                pdfTable = new PdfPTable(getTableColCountNoHidCol());
                pdfTable.setWidths(getColsWidthNoHidCol());
                setPDFTableFormmat(pdfTable);
                setPDFTableColNames(getColsNameNoHidCol(), pdfTable, fontChinese);
                setPDFTableValue(isHid, pdfTable, columnWidths, fontChinese, isRenderer);
            } else {
                pdfTable = new PdfPTable(table.getColumnCount());
                pdfTable.setWidths(columnWidths);
                setPDFTableFormmat(pdfTable);
                setPDFTableColNames(getTableAllColsName(), pdfTable, fontChinese);
                setPDFTableValue(isHid, pdfTable, columnWidths, fontChinese, isRenderer);
            }
            document.add(pdfTable);
        } catch (Throwable e1) {
            logger.error("导出PDF发生异常", e1);
            JOptionPane.showMessageDialog(table, TableCopyExporter.SaveErrorInfo);
            return;
        } finally {
            if (document != null) {
                document.close();
            }
            if (docWriter != null) {
                docWriter.close();
            }
        }
    }

    private static final String tempPath = "temp/pdf/";

    public void openPDF() {
        final String dateFile = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        File tempDir = new File(tempPath);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        if (mtxTreeTable != null) {
            mtxTreeTable.changeWholeTableUI();
        }
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        //
                    }
                    ViewFunction vf = ViewFunction.getViewFunction(table);
                    String dateFile2 = "";
                    if (vf != null) {
                        dateFile2 = StringUtil.rmFChar(vf.getViewTitle()) + "_" + dateFile;
                    }
                    final File tempFile = new File(tempPath + dateFile2 + ".pdf");
                    savePDF(tempFile, true);
                    try {
                        Desktop.getDesktop().open(tempFile);
                    } catch (IOException e) {
                        LoggerFactory.getLogger(PdfExporter.class).error("打开文件失败", e);
                    }
                } finally {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if (mtxTreeTable != null) {
                                mtxTreeTable.changeFTableUI();
                            }
                        }
                    });
                }
            }
        };
        thread.start();
    }

    /**
     * 创建保存的文件路径
     *
     * @return
     */
    public File createSaveFile() {
        MTXFileChooser fChooser = new MTXFileChooser();
        String dateFile = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        fChooser.setSelectedFile(new File(prefix + dateFile + ".pdf"));
        Component view = ViewFunction.getViewFunction(table);
        if (view == null) {
            view = table;
        }

        File fileSave = null;
        while (true) {
            if (fChooser.showSaveDialog(view) != MTXFileChooser.APPROVE_OPTION) {
                return null;
            }
            fileSave = fChooser.getSelectedFile();
            if (!fileSave.exists()) {
                try {
                    fileSave.createNewFile();
                } catch (Exception e1) {
                    logger.error("创建新文件发生异常", e1);
                    JOptionPane.showMessageDialog(view, TableCopyExporter.SaveErrorInfo);
                    return null;
                }
            } else {
                int rs = JOptionPane.showConfirmDialog(view, "已有相同文件名存在，是否覆盖原有文件?", "同名确认", JOptionPane.OK_CANCEL_OPTION);
                if (rs != JOptionPane.OK_OPTION) {
                    continue;
                }
            }
            break;
        }
        return fileSave;
    }

    /**
     * 设置pdf表格的列名
     * 设置表头宽度,及水平位置居中
     *
     * @param name
     * @param pdfTable
     * @param fontChinese
     */
    public void setPDFTableColNames(String[] name, PdfPTable pdfTable, Font fontChinese) {
        for (int i = 0; i < name.length; i++) {
            String value = name[i];
            if (value == null) {
                value = "";
            }
            Paragraph para = new Paragraph(value, fontChinese);
            PdfPCell cell = new PdfPCell(para);
            cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
            cell.setPadding(3);
            cell.setBorderWidth(2);
            pdfTable.addCell(cell);
        }
        pdfTable.setHeaderRows(1);
        pdfTable.getDefaultCell().setBorderWidth(1);
    }

    /**
     * 设置pdf表格的内容
     * 是否根据Renderer来设置值,如果是否则更根据表格的getValueAt设置
     * 在现券查询时用到,表格显示为4位,但导出显示12位
     * 根据单元格的类型设置水平位置,如果是数字型则靠右,否则靠左
     *
     * @param isHidCol
     * @param pdfTable
     * @param widths
     * @param fontChinese
     * @param isRenderer
     */
    public void setPDFTableValue(boolean isHidCol, PdfPTable pdfTable, int[] widths, Font fontChinese, boolean isRenderer) {
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                if (isHidCol && widths[j] == 0) {
                    continue;
                }
                String value = table.getValueAt(i, j) == null ? "" : table.getValueAt(i, j).toString();
                PdfPCell cell = null;
                if (value.equals("")) {
                    TableCellRenderer renderer = table.getCellRenderer(i, j);
                    JComponent com = (JComponent) renderer.getTableCellRendererComponent(table, value, false, false, i, j);
                    try {
                        Method method = com.getClass().getMethod("getIcon");
                        ImageIcon icon = (ImageIcon) method.invoke(com);
                        if (icon != null) {
                            Image pdfImage = Image.getInstance(icon.getImage(), null);
                            cell = new PdfPCell(pdfImage);
                        }
                    } catch (Throwable t) {
                    }
                }
                if (cell == null) {
                    Paragraph p = new Paragraph(value, fontChinese);
                    cell = new PdfPCell(p);
                }

                Class<?> cellClass = table.getColumnClass(j);
                if (cellClass == Number.class || cellClass == MCNumber.class || cellClass == Double.class || cellClass == Integer.class) {
                    cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
                } else {
                    cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_LEFT);
                }

                pdfTable.addCell(cell);
            }
        }
    }

    /**
     * 设置pdf表格格式
     *
     * @param pdfTable
     */
    public void setPDFTableFormmat(PdfPTable pdfTable) {
        pdfTable.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_LEFT);
        pdfTable.setWidthPercentage(100);
    }

    /**
     * 得到表格的宽度(如果是零就放零)
     *
     * @return
     */
    public int[] getTableAllColsWidth() {
        int[] cellsWidth = new int[table.getColumnCount()];
        for (int i = 0; i < table.getColumnCount(); i++) {
            cellsWidth[i] = table.getColumnModel().getColumn(i).getWidth();
        }
        return cellsWidth;
    }

    /**
     * 得到表格的所有列名
     *
     * @return
     */
    public String[] getTableAllColsName() {
        String[] names = new String[table.getColumnCount()];
        for (int i = 0; i < table.getColumnCount(); i++) {
            names[i] = table.getColumnName(i);
        }
        return names;
    }

    /**
     * 得到表格的不包括隐藏列的名称
     *
     * @return
     */
    public String[] getColsNameNoHidCol() {
        int[] colsId = getColsIdNoHidCol();
        String[] names = new String[colsId.length];
        for (int i = 0; i < colsId.length; i++) {
            names[i] = table.getColumnName(colsId[i]);
        }
        return names;
    }

    /**
     * 得到表格不过包括隐藏列的列数
     *
     * @return
     */
    public int getTableColCountNoHidCol() {
        return getColsIdNoHidCol().length;
    }

    /**
     * 得到不包括隐藏列的的列宽
     *
     * @return
     */
    public int[] getColsWidthNoHidCol() {
        int[] colsId = getColsIdNoHidCol();
        int[] cellsWidth = new int[colsId.length];
        for (int i = 0; i < colsId.length; i++) {
            cellsWidth[i] = columnWidths[colsId[i]];
        }
        return cellsWidth;
    }

    /**
     * 得到不包括隐藏列的序号
     *
     * @return
     */
    public int[] getColsIdNoHidCol() {
        int[] allWidth = getColumnWidths();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < allWidth.length; i++) {
            if (allWidth[i] > 0) {
                list.add(i);
            }
        }
        int[] cols = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            cols[i] = list.get(i);
        }
        return cols;
    }

    public int[] getColumnWidths() {
        if (columnWidths == null) {
            columnWidths = getTableAllColsWidth();
        }
        return columnWidths;
    }

    public void setColumnWidths(int[] columnWidths) {
        this.columnWidths = columnWidths;
    }

    /**
     * 得到pdf的大小
     *
     * @return
     */
    public Rectangle getPDFSize() {
        int tWidth = table.getWidth();
        int tHeight = table.getHeight();

        if (tWidth < PageSize.A4.getWidth()) {
            tWidth = (int) PageSize.A4.getWidth();
        }
        if (tHeight < PageSize.A4.getHeight()) {
            tHeight = (int) PageSize.A4.getHeight();
        }
        if (tHeight > MAX_HEIGHT) {
            tHeight = MAX_HEIGHT;
        }
        if (tWidth > MAX_WIDTH) {
            tWidth = MAX_WIDTH;
        }
        Rectangle r = new Rectangle(tWidth, tHeight);
        return r;
    }

    /**
     * 得到超出的列宽
     */
    private int getExceedColIndex() {
        int widths = 0;
        for (int i = 0; i < columnWidths.length; i++) {
            widths += columnWidths[i];
            if (widths > MAX_WIDTH) {
                return i;
            }
        }
        return -1;
    }
}
