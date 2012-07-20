package com.mt.common.export;

import com.mt.common.gui.MTXComponent.MTXFileChooser;
import com.mt.common.selectionBind.NameCodeItem;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 把Table中的数据输出到CVS文件中,只对Table显示的内容
 *
 * @author @CL
 */
public class ExcelExporter implements ActionListener {

    private JTable table;
    protected String title; // 中文表头
    private String prefix; // 文件名前缀,后面会跟以日期
    private boolean colorFlag = false;     //是否显示导出EXCEL字体颜色标志位,默认不显示

    public ExcelExporter(JTable table, String title, String prefix) {
        this.table = table;
        this.title = title;
        this.prefix = prefix;
    }

    // 传入特定的列表
    protected ArrayList<NameCodeItem> valueList;

    public ExcelExporter(ArrayList<NameCodeItem> valueList, String title, String prefix) {
        this.valueList = valueList;
        this.title = title;
        this.prefix = prefix;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // 抵押品/抵押品券面金额的格式中有逗号，导出时转换为 ；其他的分号都是用于科学计数法，需去除
    ArrayList<Integer> pawnList = new ArrayList<Integer>();

    public void actionPerformed(ActionEvent e) {

        MTXFileChooser fChooser = new MTXFileChooser();
        fChooser.setDialogType(MTXFileChooser.SAVE_DIALOG);
        String dateFile = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        fChooser.setSelectedFile(new File(prefix + dateFile + ".xls"));
        
        /*
         * modify on 2012.07.20
         * 弹出框的弹出位置，以当前表格为基准；不再以表格所在的父容器为基准
         */
        /*Component view = ViewFunction.getViewFunction(table);
        if (view == null) {
            view = table;
        }*/
        Component view = table;
        while (true) {
            if (fChooser.showSaveDialog(view) != MTXFileChooser.APPROVE_OPTION) {
                return;
            }
            File fileSave = fChooser.getSelectedFile();
            if (!fileSave.exists()) {
                try {
                    fileSave.createNewFile();
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(view, "无法建立新文件\n" + "文件存取错误");
                    return;
                }
            } else {
                int rs = JOptionPane.showConfirmDialog(view, "已有相同文件名存在，是否覆盖原有文件?", "同名确认", JOptionPane.OK_CANCEL_OPTION);
                if (rs != JOptionPane.OK_OPTION) {
                    continue;
                }
            }

            ExportExcelTemplate eet = new ExportExcelTemplate(table, dateFile, fileSave,colorFlag);
            eet.exportExcel();
            break;
        }
    }

    private static final String tempPath = "temp/excel/";

    /**
     * 用Excel打开
     */
    public void openExcel() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                String dateFile = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
                File tempDir = new File(tempPath);
                if (!tempDir.exists()) {
                    tempDir.mkdirs();
                }
                /**
                 * modify on 2012.07.20
                 * Excel的文件名称不再取所在容器的名称；直接取日期做为文件名
                 */
                /*ViewFunction vf = ViewFunction.getViewFunction(table);
                if (vf != null) {
                    dateFile = StringUtil.rmFChar(vf.getViewTitle()) + "_" + dateFile;
                }*/
                File tempFile = new File(tempPath + dateFile + ".xls");
                ExportExcelTemplate eet = new ExportExcelTemplate(table, dateFile, tempFile,colorFlag);
                eet.exportExcel();
                try {
                    Desktop.getDesktop().open(tempFile);
                } catch (IOException e) {
                    LoggerFactory.getLogger(ExcelExporter.class).error("打开文件失败", e);
                }
            }
        };
        thread.start();

    }

    /**
     * 是否设置导出颜色
     * @return
     */
    public boolean isExportColorFlag() {
        return colorFlag;
    }

    /**
     * 设置导出颜色
     * @param colorFlag
     */
    public void setExportColorFlag(boolean colorFlag) {
        this.colorFlag = colorFlag;
    }
}
