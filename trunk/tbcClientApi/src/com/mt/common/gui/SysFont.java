/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 系统字体
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-15
 */
public class SysFont {

    static private List<String> fontNames;

    static {
        fontNames = new ArrayList<String>(Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
    }

    static public Font getSysFont(String name) {
        return getSysFont(name, Font.PLAIN, 12);
    }

    static public Font getSysFont(String name, int style) {
        return getSysFont(name, style, 12);
    }

    static public Font getSysFont(String name, float size) {
        return getSysFont(name, Font.PLAIN, size);
    }

    static public Font getSysFont(String name, int style, float size) {
        if (fontNames.contains(name)) {
            return new Font(name, style, (int) size);
        }
        return null;
    }

    /**
     * 获得微软雅黑字体
     *
     * @param size
     * @return
     */
    static public Font getMicrosoft_YaHei(float size) {
        return getMicrosoft_YaHei(Font.PLAIN, size);
    }

    /**
     * 获得微软雅黑字体
     * 繁体中文 Windows 7 上获得「微软正黑体」
     *
     * @param style
     * @param size
     * @return
     */
    static public Font getMicrosoft_YaHei(int style, float size) {
        Font font = getSysFont("微软雅黑", style, size);
        if (font == null) {
            font = getSysFont("微軟正黑體", style, size);
            if (font == null) {
                if (style == Font.BOLD) {
                    font = getSysFont("黑体", style, size);
                } else {
                    font = getSysFont("宋体", style, size);
                }
                if (font == null) {
                    font = new Font("Dialog", style, (int) size);
                }
            }
        }
        return font;
    }

    static public void main(String[] args) {
        System.err.print(fontNames);
    }
}
