package com.mt.print;

import com.mt.common.gui.ComponentResizer;
import com.mt.common.gui.MTXComponent.MTXTreeTable;
import com.mt.core.functionDef.ViewFunction;
import com.mt.exception.MTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.print.PrinterException;
import java.text.MessageFormat;

/**
 * 简单表格打印
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 11-11-3
 * Time: 下午3:54
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTablePrinter {

    private static final Logger logger = LoggerFactory.getLogger(SimpleTablePrinter.class);

    static public void print(JTable table) {
        new PrintWorker(table).execute();
    }

    static public void print(MTXTreeTable table) {
        ViewFunction vf = null;
        try {
            JTable t = table.getWholeTable();
            vf = ViewFunction.getViewFunction(table);
            print(vf, t);
        } catch (Throwable t) {
            logger.error("PrinterException", t);
            JOptionPane.showMessageDialog(vf,
                    "打印失败\n" + MTException.getExceptionMsg(t));
        }

    }

    static private void print(ViewFunction vf, JTable table) throws PrinterException {
        String t = vf != null ? vf.getViewTitle() : "Page";
        t = (t == null || t.equals("")) ? "Page" : t;
        MessageFormat headerFormat = new MessageFormat(t);
        MessageFormat footerFormat = new MessageFormat("- {0} -");
        int cCount = 0;
        TableColumnModel tcm = table.getColumnModel();
        for (int i = 0; i < tcm.getColumnCount(); i++) {
            if (tcm.getColumn(i).getWidth() > 0) {
                cCount++;
            }
        }
        HashPrintRequestAttributeSet hrs = new HashPrintRequestAttributeSet();
        if (cCount > 11) {
            hrs.add(OrientationRequested.LANDSCAPE);
        }
        table.print(JTable.PrintMode.FIT_WIDTH, headerFormat,
                footerFormat, false, hrs, false);
    }

    static private class PrintWorker extends SwingWorker {

        ViewFunction vf = null;

        JTable table = null;

        public PrintWorker(JTable table) {
            vf = ViewFunction.getViewFunction(table);
            this.table = table;
            if (vf != null) {
                vf.startInfiniteLock("打印中,请稍后...");
            }
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            ComponentResizer.resizeTableColumnWithContent(table);
        }

        @Override
        protected Object doInBackground() throws Exception {
            print(vf, table);
            return null;
        }

        @Override
        protected void done() {
            try {
                if (vf != null) {
                    vf.stopInfiniteLock();
                }
                ComponentResizer.resizeTableColumnWithCC(table);
                get();
            } catch (Throwable ex) {
                logger.error("PrinterException", ex);
                JOptionPane.showMessageDialog(vf,
                        "打印失败\n" + MTException.getExceptionMsg(ex));
            }
        }
    }
}
