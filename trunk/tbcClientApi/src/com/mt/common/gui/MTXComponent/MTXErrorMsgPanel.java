package com.mt.common.gui.MTXComponent;

import com.mt.common.gui.SysFont;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 错误信息面板
 *
 * @author liangxin
 */
public class MTXErrorMsgPanel extends JPanel {

    /**
     * 设置全局参数，是否自动将错误信息保存到外部文件
     */
    private static boolean isAutoSave = false;
    private static String savePath;
    private static final String extendName = "详细信息>>";
    private static final String un_extendName = "<<详细信息";
    private static final String DFTitleName = "错误信息";
    private final Logger logger = LoggerFactory.getLogger(MTXErrorMsgPanel.class);

    public MTXErrorMsgPanel(String msg, String detailInfo) {
        initComponents();
        jPanel2.setOpaque(false);
        msg_text.setOpaque(false);
        msg_text.setFont(SysFont.getMicrosoft_YaHei(12));
        init(msg, detailInfo);
    }

    private void init(String msg, String detailInfo) {
        msg_text.setText(msg);
        if (detailInfo == null) {
            detail_btn.setEnabled(false);
        } else {
            detailText.setText(detailInfo);
            detailText.setCaretPosition(0);
        }
        setErrorShow(false);
        detail_btn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (detail_btn.getText().equals(extendName)) {
                    detail_btn.setText(un_extendName);
                    setErrorShow(true);
                } else {
                    detail_btn.setText(extendName);
                    setErrorShow(false);
                }
            }
        });

        confirm_btn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Window rootWindow = SwingUtilities.getWindowAncestor(MTXErrorMsgPanel.this);
                if (rootWindow != null) {
                    rootWindow.dispose();
                }
            }
        });
    }

    private void setErrorShow(boolean status) {
        jScrollPane2.setVisible(status);
        Window rootWindow = SwingUtilities.getWindowAncestor(this);
        if (rootWindow != null) {
            rootWindow.pack();
        }
    }

    public static void setAutoSave(boolean isSave) {
        isAutoSave = isSave;
    }

    public static void setSavePath(String filePath) {
        savePath = filePath;
    }

    private void autoSaveErrorInfo(String content) {
        if (savePath != null) {
            File errorFile = new File(savePath + "/错误信息.txt");

            try {
                FileUtils.writeStringToFile(errorFile, content, "UTF-8");
            } catch (Exception e) {
                logger.error("无法将错误信息写入文件", e);
                return;
            }
        }
    }

    static Window createDialog(Component comp) {
        if (comp == null) {
            return null;
        } else if (comp instanceof JFrame || comp instanceof JInternalFrame) {
            return (Window) comp;
        } else {
            return SwingUtilities.getWindowAncestor(comp);
        }
    }

    public static void showMessageDialog(Component comp, String msg, String detailInfo) {
        showMessageDialog(comp, DFTitleName, msg, detailInfo);
    }

    public static void showMessageDialog(Component comp, String msg) {
        showMessageDialog(comp, DFTitleName, msg);
    }

    public static void showMessageDialog(Component comp, String msg, Throwable ex) {
        showMessageDialog(comp, DFTitleName, msg, ex);
    }

    public static void showMessageDialog(Component comp, String title, String msg, String detailInfo) {

        MTXErrorMsgPanel mtxPanel = new MTXErrorMsgPanel(msg, detailInfo);

        /**
         * 是否自动保存错误信息至文件
         */
        if (isAutoSave) {
            mtxPanel.autoSaveErrorInfo(msg + "\r\n" + detailInfo);
        }

        Window window = createDialog(comp);
        JDialog dialog;
        if (window == null) {
            dialog = new JDialog();
        } else {
            if (window instanceof Frame) {
                dialog = new JDialog((Frame) window, title, true);
            } else {
                dialog = new JDialog((Dialog) window, title, true);
            }
        }
        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(mtxPanel, BorderLayout.CENTER);

        dialog.pack();
        dialog.setLocationRelativeTo(comp);

        dialog.setVisible(true);

    }

    public static void showMessageDialog(Component comp, String title, String msg, Throwable ex) {
        if (ex == null) {
            showMessageDialog(comp, title, msg, "null");
        } else {
            StringWriter writer = new StringWriter();
            ex.printStackTrace(new PrintWriter(writer));
            String stackTrace = writer.toString();
            showMessageDialog(comp, title, msg, stackTrace);
        }
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        msg_text = new javax.swing.JTextArea();
        confirm_btn = new javax.swing.JButton();
        detail_btn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        detailText = new javax.swing.JTextArea();

        jScrollPane1.setBorder(null);

        msg_text.setBackground(new java.awt.Color(236, 233, 216));
        msg_text.setColumns(20);
        msg_text.setEditable(false);
        msg_text.setLineWrap(true);
        msg_text.setRows(3);
        msg_text.setBorder(null);
        jScrollPane1.setViewportView(msg_text);

        confirm_btn.setText("确定");

        detail_btn.setText("详细信息>>");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/errorMsg.png"))); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jPanel2Layout.createSequentialGroup().addContainerGap().add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING).add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup().add(jLabel1).addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED).add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)).add(jPanel2Layout.createSequentialGroup().add(confirm_btn).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(detail_btn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 105, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(12, 12, 12))).add(1, 1, 1)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup().addContainerGap().add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jLabel1).add(jPanel2Layout.createSequentialGroup().add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 68, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(confirm_btn).add(detail_btn)))).addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        detailText.setColumns(20);
        detailText.setEditable(false);
        detailText.setRows(5);
        jScrollPane2.setViewportView(detailText);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE).add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(layout.createSequentialGroup().add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(0, 0, 0).add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)));
    }// </editor-fold>

    // Variables declaration - do not modify
    private javax.swing.JButton confirm_btn;
    private javax.swing.JTextArea detailText;
    private javax.swing.JButton detail_btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea msg_text;
    // End of variables declaration
}
