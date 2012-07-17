package com.mt.common.gui;

/**
 * @author hanhui
 * 整个程序中共享的颜色库 */

import java.awt.*;

/**
 * 颜色库
 */
public class ColorLib {

    public final static Color editableColor = new Color(208, 208, 255);
    //日期输入框取得焦点时的color
    public final static Color fieldGainColor = new Color(193, 193, 193);
    public final static Color fontColor = new Color(103, 83, 252);
    public final static Color tableHeaderColor = new Color(227, 210, 228);
    /**
     * 债券评价-------------〉收益分析结果的color
     */
    public final static Color tableColor1 = new Color(221, 253, 255);
    public final static Color tableColor2 = new Color(208, 208, 255);
    public final static Color tableColor3 = new Color(251, 243, 206);
    /**
     * 债券评价-------------〉市场行情的color
     */
    public final static Color tableColor4 = new Color(250, 244, 236);
    /**
     * 共享收益率曲线 table的中间色
     */
    public final static Color tableColor5 = new Color(240, 240, 240);
    public final static Color RowColor = new Color(212, 208, 200);
    public final static Font CFont = new Font("", 3, 14);
    public final static Font BFont = new Font("", 3, 18);
    //损益分析中总计栏位的background
    public final static Color statisticColor = new Color(188, 158, 224);
    //损益分析中类别总计栏位的background
    public final static Color statisticColor1 = new Color(254, 223, 255);
    //损益分析中是模拟交易栏位的background
    public final static Color statisticColor2 = new Color(0, 204, 204);
    //标题名blue(包含JLabel)
    public final static Color parentColumn = new Color(88, 146, 125);
    //parent
    public final static Color childColumn = new Color(172, 204, 193);
    //child
    public final static Color TitleName = new Color(0, 0, 255);
    //被删除记录的颜色,不能编辑的颜色
    public final static Color delTradeColor = new Color(211, 211, 211);

    public final static Color MixTradeColor = new Color(229, 183, 161);

    /**
     * A very dark red color.
     */
    public static final Color VERY_DARK_RED = new Color(0x80, 0x00, 0x00);

    /**
     * A dark red color.
     */
    public static final Color DARK_RED = new Color(0xc0, 0x00, 0x00);

    /**
     * A light red color.
     */
    public static final Color LIGHT_RED = new Color(0xFF, 0x40, 0x40);

    /**
     * A very light red color.
     */
    public static final Color VERY_LIGHT_RED = new Color(0xFF, 0x80, 0x80);

    /**
     * A very dark yellow color.
     */
    public static final Color VERY_DARK_YELLOW = new Color(0x80, 0x80, 0x00);

    /**
     * A dark yellow color.
     */
    public static final Color DARK_YELLOW = new Color(0xC0, 0xC0, 0x00);

    /**
     * A light yellow color.
     */
    public static final Color LIGHT_YELLOW = new Color(0xFF, 0xFF, 0x40);

    /**
     * A very light yellow color.
     */
    public static final Color VERY_LIGHT_YELLOW = new Color(0xFF, 0xFF, 0x80);

    /**
     * A very dark green color.
     */
    public static final Color VERY_DARK_GREEN = new Color(0x00, 0x80, 0x00);

    /**
     * A dark green color.
     */
    public static final Color DARK_GREEN = new Color(0x00, 0xC0, 0x00);

    /**
     * A light green color.
     */
    public static final Color LIGHT_GREEN = new Color(0x40, 0xFF, 0x40);

    /**
     * A very light green color.
     */
    public static final Color VERY_LIGHT_GREEN = new Color(0x80, 0xFF, 0x80);

    /**
     * A very dark cyan color.
     */
    public static final Color VERY_DARK_CYAN = new Color(0x00, 0x80, 0x80);

    /**
     * A dark cyan color.
     */
    public static final Color DARK_CYAN = new Color(0x00, 0xC0, 0xC0);

    /**
     * A light cyan color.
     */
    public static final Color LIGHT_CYAN = new Color(0x40, 0xFF, 0xFF);

    /**
     * Aa very light cyan color.
     */
    public static final Color VERY_LIGHT_CYAN = new Color(0x80, 0xFF, 0xFF);

    /**
     * A very dark blue color.
     */
    public static final Color VERY_DARK_BLUE = new Color(0x00, 0x00, 0x80);

    /**
     * A dark blue color.
     */
    public static final Color DARK_BLUE = new Color(0x00, 0x00, 0xC0);

    /**
     * A light blue color.
     */
    public static final Color LIGHT_BLUE = new Color(0x40, 0x40, 0xFF);

    /**
     * A very light blue color.
     */
    public static final Color VERY_LIGHT_BLUE = new Color(0x80, 0x80, 0xFF);

    /**
     * A very dark magenta/purple color.
     */
    public static final Color VERY_DARK_MAGENTA = new Color(0x80, 0x00, 0x80);

    /**
     * A dark magenta color.
     */
    public static final Color DARK_MAGENTA = new Color(0xC0, 0x00, 0xC0);

    /**
     * A light magenta color.
     */
    public static final Color LIGHT_MAGENTA = new Color(0xFF, 0x40, 0xFF);

    /**
     * A very light magenta color.
     */
    public static final Color VERY_LIGHT_MAGENTA = new Color(0xFF, 0x80, 0xFF);

    public static final Color FIS_SELECTED_BGCOLOR = new Color(153, 51, 0);
    public static final Color FIS_EVEN_ROW_COLOR = new Color(17, 38, 79);
    public static final Color FIS_ODD_ROW_COLOR = new Color(35, 35, 35);
    public static final Color FIS_TULLET_EVEN_ROW_COLOR = new Color(51, 00, 00);
    public static final Color FIS_TEXT_COLOR = new Color(255, 247, 152);
    public static final Color FIS_SELECTED_TEXT_COLOR = Color.white; //new Color(204, 204, 204);
    public static final Color FIS_UP_BGCOLOR = new Color(204, 255, 205);
    public static final Color FIS_DN_BGCOLOR = new Color(255, 204, 204);
    public static final Color FIS_UP_TEXT_COLOR = new Color(0, 102, 0);
    public static final Color FIS_DN_TEXT_COLOR = new Color(153, 0, 0);
    public static final Color FIS_TABLE_BACKGROUND_COLOR = Color.gray;

    public final static Color TRow_OddColor = new Color(224, 233, 246);
    public final static Color TRow_EvenColor = Color.white;

    public final static Color HomeFuncSel = new Color(19, 160, 11);

    public final static Color HomeFuncM = new Color(106, 186, 73);

    public final static Color SNFI = new Color(204, 255,204);

    public final static Color SPCX = new Color(234, 234,234);

    public final static Color SPK = new Color(255, 235,207);


}
