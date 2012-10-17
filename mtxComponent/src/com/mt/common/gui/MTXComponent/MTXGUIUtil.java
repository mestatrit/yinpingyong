/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import com.mt.common.gui.CommonIconDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 处理GUI的一些工具方法
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-6-8
 */
public class MTXGUIUtil {

    static private final Logger logger = LoggerFactory.getLogger(MTXGUIUtil.class);

    /**
     * 获得某个组件的MTXTopView顶级视图
     * 如果顶级视图不是MTXTopView就返回null
     *
     * @param com
     * @return
     */
    static public MTXTopView getMTXTopView(Component com) {
        for (Component p = com.getParent(); p != null; p = p.getParent()) {
            if (p instanceof MTXTopView) {
                return (MTXTopView) p;
            }
        }
        return null;
    }

    static public JButton createArrowButton() {
        return new JButton(CommonIconDef.Arrow_DownIcon);
    }

    static public JPanel createSearchTextPanel(JTextField selText) {
        return createSearchTextPanel(selText, -1, -1);
    }

    static public JPanel createSearchTextPanel(final JTextField selText, int w, int h) {
        return createSearchTextPanel(selText, w, h, null, null);
    }

    static public JPanel createSearchTextPanel(final JTextField selText, int w, int h, final String text, String tip) {
        final Font oldFont = selText.getFont();
        final Color oldFr = selText.getForeground();

        final Font textFont = oldFont.deriveFont(Font.ITALIC);
        final Color textFr = Color.GRAY;
        selText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (text != null && selText.getText().equals(text)) {
                    selText.setText("");
                    selText.setFont(oldFont);
                    selText.setForeground(oldFr);
                } else {
                    selText.selectAll();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (text != null) {
                    if (selText.getText().equals("")) {
                        selText.setText(text);
                        selText.setFont(textFont);
                        selText.setForeground(textFr);
                    }
                }
            }
        });
        selText.setBorder(null);
        JPanel selTextPanel = new JPanel(new BorderLayout());
        if (w > 0 && h > 0) {
            selTextPanel.setPreferredSize(new Dimension(w, h));
        }
        selTextPanel.setBorder(UIManager.getBorder("ComboBox.border"));
        selTextPanel.setBackground(UIManager.getColor("ComboBox.background"));
        selTextPanel.setForeground(UIManager.getColor("ComboBox.foreground"));
        JLabel label = new JLabel(CommonIconDef.SearchIcon);
        if (tip != null) {
            label.setToolTipText(tip);
        }
        if (text != null) {
            selText.setText(text);
            selText.setFont(textFont);
            selText.setForeground(textFr);
        }
        selTextPanel.add(label, BorderLayout.WEST);
        selTextPanel.add(selText, BorderLayout.CENTER);
        return selTextPanel;
    }

    static public JPanel createSearchTreePanel(final MTXTree tree) {
        JPanel sPanel = new JPanel(new BorderLayout(0, 5));
        final JTextField tf = new JTextField();
        tf.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                tree.setFilterText(tf.getText());
            }
        });
        JPanel sTextPanel = createSearchTextPanel(tf);
        sPanel.add(sTextPanel, BorderLayout.NORTH);
        sPanel.add(new JScrollPane(tree), BorderLayout.CENTER);
        return sPanel;
    }

    static public void removeAllActionListeners(JButton button) {
        ActionListener[] als = button.getActionListeners();
        for (int i = 0; i < als.length; i++) {
            button.removeActionListener(als[i]);
        }
    }

    static public void removeAllKeyListeners(JComponent com) {
        KeyListener[] kls = com.getKeyListeners();
        for (int i = 0; i < kls.length; i++) {
            com.removeKeyListener(kls[i]);
        }
    }

    static public void removeAllMouseListeners(JComponent com) {
        MouseListener[] kls = com.getMouseListeners();
        for (int i = 0; i < kls.length; i++) {
            com.removeMouseListener(kls[i]);
        }
    }

    static public void waitViewLock(final MTXTopView topView, final ViewLockListener vlListener) {
        Thread thread = new Thread() {

            @Override
            public void run() {
                while (topView.isViewLock()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        vlListener.lockEnd();
                    }
                });
            }
        };
        thread.start();
    }
}
