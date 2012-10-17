package com.mt.common.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.Autoscroll;

/**
 * 支持自动滚动
 *
 * @author hanhui
 */
public class AutoscrollSupport implements Autoscroll {

    Component comp;

    Insets insets;

    Insets scrollUnits;

    public AutoscrollSupport(Component comp) {
        this(comp, new Insets(12, 12, 12, 12), new Insets(35, 35, 35, 35));
    }

    public AutoscrollSupport(Component comp, Insets insets) {
        this(comp, insets, insets);
    }

    public AutoscrollSupport(Component comp, Insets insets, Insets scrollUnits) {
        this.comp = comp;
        this.insets = insets;
        this.scrollUnits = scrollUnits;
    }

    public void autoscroll(Point cursorLoc) {
        JViewport viewport = getViewport();
        if (viewport == null)
            return;
        Point viewPos = viewport.getViewPosition();
        int viewHeight = viewport.getExtentSize().height;
        int viewWidth = viewport.getExtentSize().width;

        if ((cursorLoc.y - viewPos.y) < insets.top) { // scroll up
            viewport.setViewPosition(
                    new Point(viewPos.x,
                            Math.max(viewPos.y - scrollUnits.top, 0)));
        } else if ((viewPos.y + viewHeight - cursorLoc.y) < insets.bottom) { // scroll down
            viewport.setViewPosition(
                    new Point(viewPos.x,
                            Math.min(viewPos.y + scrollUnits.bottom,
                                    comp.getHeight() - viewHeight)));
        } else if ((cursorLoc.x - viewPos.x) < insets.left) { // scroll left
            viewport.setViewPosition(
                    new Point(Math.max(viewPos.x - scrollUnits.left, 0),
                            viewPos.y));
        } else if ((viewPos.x + viewWidth - cursorLoc.x) < insets.right) { // scroll right
            viewport.setViewPosition(
                    new Point(Math.min(viewPos.x + scrollUnits.right, comp.getWidth() - viewWidth),
                            viewPos.y));
        }
    }

    public Insets getAutoscrollInsets() {
        int height = comp.getHeight();
        int width = comp.getWidth();
        return new Insets(height, width, height, width);
    }

    JViewport getViewport() {
        return (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, comp);
    }


}
