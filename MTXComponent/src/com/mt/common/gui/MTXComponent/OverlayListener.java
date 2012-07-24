/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

/**
 * 叠加监听器
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-27
 */
public class OverlayListener extends MouseInputAdapter implements TreeSelectionListener, ComponentListener {

    private JTree tree;
    private Component oldGlassPane;
    private TreePath path;
    private int row;
    private Rectangle bounds;

    public OverlayListener(JTree tree) {
        this.tree = tree;
        tree.addMouseListener(this);
        tree.addComponentListener(this);
        tree.addMouseMotionListener(this);
    }

    JComponent c = new JComponent() {

        public void paint(Graphics g) {
            boolean selected = tree.isRowSelected(row);
            Component renderer = tree.getCellRenderer().getTreeCellRendererComponent(tree, path.getLastPathComponent(),
                    tree.isRowSelected(row), tree.isExpanded(row), tree.getModel().isLeaf(path.getLastPathComponent()), row,
                    selected);
            c.setFont(tree.getFont());
            Rectangle paintBounds = SwingUtilities.convertRectangle(tree, bounds, this);
            getCellRendererPane(renderer, this).paintComponent(g, renderer, this, paintBounds.x, paintBounds.y, paintBounds.width, paintBounds.height, true);
            if (selected) {
                return;
            }

            g.setColor(Color.blue);
            ((Graphics2D) g).draw(paintBounds);
        }
    };

    public void mouseExited(MouseEvent e) {
        resetGlassPane();
    }

    public void resetGlassPane() {
        if (oldGlassPane != null) {
            c.setVisible(false);
            tree.getRootPane().setGlassPane(oldGlassPane);
            oldGlassPane = null;
            tree.removeTreeSelectionListener(this);
        }
    }

    public void mouseMoved(MouseEvent me) {
        path = tree.getPathForLocation(me.getX(), me.getY());
        if (path == null) {
            resetGlassPane();
            return;
        }
        row = tree.getRowForPath(path);
        bounds = tree.getPathBounds(path);
        if (!tree.getVisibleRect().contains(bounds)) {
            if (oldGlassPane == null) {
                oldGlassPane = tree.getRootPane().getGlassPane();
                c.setOpaque(false);
                tree.getRootPane().setGlassPane(c);
                c.setVisible(true);
                tree.addTreeSelectionListener(this);
            } else {
                tree.getRootPane().repaint();
            }
        } else {
            resetGlassPane();
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        tree.getRootPane().repaint();
    }

    private static CellRendererPane getCellRendererPane(Component c, Container p) {
        Container shell = c.getParent();
        if (shell instanceof CellRendererPane) {
            if (shell.getParent() != p) {
                p.add(shell);
            }
        } else {
            shell = new CellRendererPane();
            shell.add(c);
            p.add(shell);
        }
        return (CellRendererPane) shell;
    }

    @Override
    public void componentResized(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        resetGlassPane();
    }
}
