package com.mt.common.export;

import com.mt.print.SimpleTablePrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 使一个表格具备copy,导出CSV文件,导出PDF文件.的能力
 * 使用时只需要new TableCopyExporter(你的表格);
 *
 * @author hanhui
 */
public class TableCopyExporter {

    private Logger logger = LoggerFactory.getLogger(TableCopyExporter.class);
    public static final String SaveErrorInfo = "导出失败,可能名字相同的文件正在被使用";

    private JPopupMenu menu;
    private ExcelExporter excel;
    private PdfExporter pdf;
    private ExcelCopyPaster exp;
    private JMenuItem exceltem;
    private JMenuItem openExcel;
    private JMenuItem pdfItem;
    private JMenuItem openPdf;
    private JMenuItem printItem;
    private JTable table;

    public TableCopyExporter(JTable table) {
        this(table, "");
    }

    public TableCopyExporter(JTable table, String title) {
        this.table = table;
        exp = new ExcelCopyPaster(table);
        menu = exp.getPopupMenu();

        printItem = new JMenuItem("打印表格");
        exceltem = new JMenuItem("导出Excel文件");
        openExcel = new JMenuItem("用Excel查看");
        pdfItem = new JMenuItem("导出PDF文件");
        openPdf = new JMenuItem("用PDF查看");
        excel = new ExcelExporter(table, title, "");
        pdf = new PdfExporter(table, title, "");
        exceltem.addActionListener(excel);
        openExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excel.openExcel();
            }
        });
        pdfItem.addActionListener(pdf);
        openPdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdf.openPDF();
            }
        });
        printItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                print();
            }
        });
        menu.add(printItem);
        menu.add(openExcel);
        menu.add(openPdf);
        menu.add(exceltem);
        menu.add(pdfItem);
    }

    public void exportExcel() {
        excel.actionPerformed(null);
    }

    public void exportPDF() {
        pdf.actionPerformed(null);
    }

    public void print() {
        SimpleTablePrinter.print(table);
    }

    public void setTitle(String title) {
        this.excel.setTitle(title);
        this.pdf.setTitle(title);
    }

    public JPopupMenu getPopupMenu() {
        return menu;
    }

    public void setPDFMenuItemVisible(boolean isV) {
        pdfItem.setVisible(isV);
    }

    public void setExcelMenuItemVisible(boolean isV) {
        exceltem.setVisible(isV);
    }

    public void setOpenExcelMenuItemVisible(boolean isV) {
        openExcel.setVisible(isV);
    }

    public void setOpenPDFMenuItemVisible(boolean isV) {
        openPdf.setVisible(isV);
    }

    public void setPrintMenuItemVisible(boolean isV) {
        printItem.setVisible(isV);
    }

    boolean isExportVisible = true;

    public void setExportVisible(boolean isV) {
        isExportVisible = isV;
        setExcelMenuItemVisible(isV);
        setPDFMenuItemVisible(isV);
        setOpenExcelMenuItemVisible(isV);
        setOpenPDFMenuItemVisible(isV);
        setPrintMenuItemVisible(isV);
        if (!isV && !exp.getCopyEnable() && !isContainOtherMenuItem)
            exp.setShowPopupMenu(false);
        else if (isV)
            exp.setShowPopupMenu(true);
    }

    public boolean getExportVisible() {
        return isExportVisible;
    }

    public void setPopupMenuVisible(boolean isVisible) {
        exp.setShowPopupMenu(isVisible);
    }

    public void addMenuItem(JComponent item) {
        menu.add(item);
        isContainOtherMenuItem = true;
    }

    boolean isContainOtherMenuItem = false;

    @SuppressWarnings("unchecked")
    public void addMenuItemFirst(JComponent item) {
        int count = menu.getComponentCount();
        List<Component> temp = new ArrayList<Component>();
        for (int i = 0; i < count; i++) {
            temp.add(menu.getComponent(i));
        }
        menu.removeAll();
        menu.add(item);
        for (int i = 0; i < temp.size(); i++) {
            menu.add((JComponent) temp.get(i));
        }
        isContainOtherMenuItem = true;
    }

    /**
     * 加上分隔符
     */
    public void addSeparator() {
        menu.addSeparator();
    }

    public void addSeparatorFirst() {
        menu.insert(new JPopupMenu.Separator(), 0);
    }

    /**
     * 删除某个菜单
     *
     * @param item
     */
    public void removeMenuItem(JMenuItem item) {
        try {
            menu.remove(item);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 根据名字得到的菜单
     *
     * @return
     */
    public JMenuItem getMenuByName(String name) {
        for (int i = 0; i < menu.getComponentCount(); i++) {
            if (menu.getComponent(i) instanceof JMenuItem) {
                String text = ((JMenuItem) menu.getComponent(i)).getText();
                if (name.equals(text)) {
                    return (JMenuItem) menu.getComponent(i);
                }
            }
        }
        return null;
    }

    public void setCopyEnable(boolean isEnable) {
        exp.setCopyEnable(isEnable);
        if (!isEnable && !getExportVisible() && !isContainOtherMenuItem)
            exp.setShowPopupMenu(false);
        else if (isEnable)
            exp.setShowPopupMenu(true);
    }

    public boolean isExcelExportColorFlag() {
        return excel.isExportColorFlag();
    }

    public void setExcelExportColorFlag(boolean colorFlag) {
        excel.setExportColorFlag(colorFlag);
    }
}
