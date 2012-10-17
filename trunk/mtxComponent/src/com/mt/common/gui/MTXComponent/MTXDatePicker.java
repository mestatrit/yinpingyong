/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;


/**
 * 日期组件
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-9-4
 */
public class MTXDatePicker extends JFormattedTextField implements MouseListener, MouseMotionListener {

    //年月栏颜色
    public static Color YEAR_AND_MONTH_COLOR = new Color(30, 60, 115);
    //年月字体颜色
    public static Color YEAR_AND_MONTH_FONT_COLOR = new Color(255, 255, 255);
    //星期栏渐变色
    public static Color WEEK_COLOR1 = new Color(215, 231, 247);
    public static Color WEEK_COLOR2 = new Color(199, 216, 232);
    //白色的透明渐变，增加玻璃效果
    public static Color GLASS_COLOR1 = new Color(244, 244, 244, 100);
    public static Color GLASS_COLOR2 = new Color(224, 224, 204, 50);
    //星期栏字体色
    public static Color WEEK_FONT_COLOR = new Color(78, 84, 118);
    //日历中间边框色
    public static Color DAY_BORDER_COLOR = new Color(170, 187, 118);
    //天数背景色
    public static Color DAY_COLOR = new Color(0, 0, 0);
    //选择渐变背景色
    public static Color CHOICE_COLOR1 = new Color(244, 244, 244);
    public static Color CHOICE_COLOR2 = new Color(209, 225, 251);
    //日历缩略图颜色
    public static Color WEEK_COLOR = new Color(30, 60, 115);
    private Calendar cc = null;
    public static Dimension pop_Size = new Dimension(280, 250);
    private CalendarPanel cp = null;
    private PickerLabel[][] pl = null;
    private int choiceYear;
    private int choiceMonth;
    private int choiceDay;
    private int thisYear;
    private int thisMonth;
    private int today;
    private BufferedImage img;
    private Insets insets;
    private boolean pressed;
    private HashMap hm;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    //动画
    ActionListener ac;

    public MTXDatePicker(String title) {
        super(title);
        init();
    }

    public MTXDatePicker(int i) {
        super(i);
        init();
    }

    public MTXDatePicker() {
        init();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (pressed && isEnabled()) {
            g.drawImage(img, this.getWidth() - 20 + 1, (this.getHeight() - 16) / 2 + 1, this);
        } else {
            g.drawImage(img, this.getWidth() - 20, (this.getHeight() - 16) / 2, this);
        }
    }

    public Insets getMargin() {
        insets = super.getMargin();
        if (insets == null) {
            return new Insets(0, 0, 0, 20);
        }
        return new Insets(insets.top, insets.left + 20, insets.bottom, insets.right + 20);
    }

    public boolean isTextFieldNull() {
        return getText().equals("    -  -  ");
    }

    public Date getDate() {
        try {
            if (getText() != null && getText().trim().length() > 0) {
                return df.parse(getText().trim());
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public void setDate(Date date) {
        if (date == null) {
            setText("");
        } else {
            setText(df.format(date));
        }
    }

    public void setDateWithAction(Date date) {
        setDate(date);
        fireActionPerformed();
    }

    public void mouseMoved(MouseEvent e) {
        if ((e.getX() - this.getWidth() + 20 >= 0)) {
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            this.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        }
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (!isEnabled()) {
            return;
        }
        if ((e.getX() - this.getWidth() + 20 >= 0)) {
            pressed = true;
            repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (!isEnabled()) {
            return;
        }
        if ((e.getX() - this.getWidth() + 20 >= 0)) {
            pressed = false;
            repaint();
            if (cp == null) {
                cp = new CalendarPanel(this);
            }
            Date selDate = getDate();
            if (selDate == null) {
                selDate = new Date();
            }
            Calendar cTemp = Calendar.getInstance();
            cTemp.setTime(selDate);
            choiceYear = cTemp.get(Calendar.YEAR);
            choiceMonth = cTemp.get(Calendar.MONTH);
            choiceDay = cTemp.get(Calendar.DAY_OF_MONTH);
            cp.setDay(choiceYear, choiceMonth, choiceDay);
            cp.show(this, 0, this.getHeight());
        }
    }

    public void init() {
        df.setLenient(false);
        initFormattedTextField();
        hm = getHashMap();

        pressed = false;
        this.addMouseMotionListener(this);
        this.addMouseListener(this);

        img = new BufferedImage(16, 16, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = img.getGraphics();

        g.setColor(WEEK_COLOR);
        g.drawRect(0, 0, 15, 15);
        g.fillRect(0, 0, 15, 3);

        for (int i = 1; i < 5; i++) {
            g.drawLine(3 * i, 0, 3 * i, 15);
            g.drawLine(0, 3 * i, 15, 3 * i);
        }

        g.fillRect(9, 9, 3, 3);

        //reset();
        //初始化文本框显示为当前日期
        cc = Calendar.getInstance();
        choiceYear = thisYear = cc.get(Calendar.YEAR);
        choiceMonth = thisMonth = cc.get(Calendar.MONTH);
        choiceDay = today = cc.get(Calendar.DAY_OF_MONTH);

        //resetToday();
        //事先初始化
        cp = new CalendarPanel(this);
        super.setPreferredSize(new Dimension(100, 22));
        //this.setFont(new Font("", Font.PLAIN, 14));
    }

    private void initFormattedTextField() {
        this.setFormatterFactory(getTextDateFormat());
        this.setFocusLostBehavior(3);
        this.setHorizontalAlignment(4);
    }

    private DefaultFormatterFactory getTextDateFormat() {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("####-##-##");
        } catch (ParseException e) {
            //logger.error(e.getMessage(),e);;
        }
        DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory();
        defaultFormatterFactory.setDefaultFormatter(formatter);

        return defaultFormatterFactory;
    }

    public void resetToday() {
        String mon = thisMonth + "";
        if (thisMonth < 10) {
            mon = "0" + (thisMonth + 1);
        }

        String d = today + "";
        if (today < 10) {
            d = "0" + today;
        }

        this.setText(thisYear + "-" + mon + "-" + d);
    }

    public void reset() {
        this.setText("");
    }

    private HashMap getHashMap() {

        if (hm == null) {
            hm = new HashMap();
            hm.put("一月", new Integer(0));
            hm.put("二月", new Integer(1));
            hm.put("三月", new Integer(2));
            hm.put("四月", new Integer(3));
            hm.put("五月", new Integer(4));
            hm.put("六月", new Integer(5));
            hm.put("七月", new Integer(6));
            hm.put("八月", new Integer(7));
            hm.put("九月", new Integer(8));
            hm.put("十月", new Integer(9));
            hm.put("十一月", new Integer(10));
            hm.put("十二月", new Integer(11));
        }
        return hm;
    }

    class PickerToolBar extends JToolBar {

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            GradientPaint gp = new GradientPaint(0, 0, MTXDatePicker.WEEK_COLOR1, 0, this.getHeight(), MTXDatePicker.WEEK_COLOR2);
            g2.setPaint(gp);
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    class LabelGroup {

        private PickerLabel[] plGroup;
        private PickerLabel choiceLabel;

        public PickerLabel getChoiceLabel() {
            return choiceLabel;
        }

        public void setChoiceLabel(PickerLabel choiceLabel) {
            if (this.choiceLabel != null) {
                this.choiceLabel.choice = false;
                this.choiceLabel.updateUI();
            }

            this.choiceLabel = choiceLabel;
            this.choiceLabel.choice = true;
            this.choiceLabel.updateUI();
        }

        public PickerLabel[] getPlGroup() {
            return plGroup;
        }

        public LabelGroup(PickerLabel[] pl) {
            this.plGroup = pl;
            for (int i = 0; i < plGroup.length; i++) {

                final PickerLabel pl1 = plGroup[i];

                pl1.addMouseListener(new MouseAdapter() {

                    public void mouseEntered(MouseEvent e) {
                        pl1.setBackground(MTXDatePicker.WEEK_COLOR1);
                        pl1.updateUI();
                    }

                    public void mouseExited(MouseEvent e) {
                        if (pl1 != choiceLabel) {
                            pl1.setBackground(MTXDatePicker.YEAR_AND_MONTH_FONT_COLOR);
                        }
                        pl1.updateUI();
                    }

                    public void mousePressed(MouseEvent e) {
                        if (choiceLabel != null) {
                            choiceLabel.choice = false;
                            choiceLabel.updateUI();
                        }
                        pl1.choice = true;
                        choiceLabel = pl1;
                        pl1.updateUI();
                    }
                });
            }
        }
    }

    class PickerLabel extends JLabel {

        public boolean choice = false;

        public PickerLabel(String string) {
            super(string);
            init();
        }

        public PickerLabel() {
            init();
        }

        private void init() {
            this.setOpaque(true);
            this.setBackground(MTXDatePicker.YEAR_AND_MONTH_FONT_COLOR);
            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.setForeground(MTXDatePicker.DAY_COLOR);
            this.setFont(new Font("", Font.PLAIN, 14));

        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (choice) {
                this.setBackground(MTXDatePicker.WEEK_COLOR1);
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(0, 0, MTXDatePicker.GLASS_COLOR1, 0, this.getHeight(), MTXDatePicker.GLASS_COLOR2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                g2.dispose();
                g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
            } else {
                this.setBackground(MTXDatePicker.YEAR_AND_MONTH_FONT_COLOR);
            }
        }
    }

    class PickerTable extends JTable implements MouseListener, MouseMotionListener {

        private PickerLabel choiceLabel = null;
        private PickerLabel hoverLabel = null;

        public PickerTable() {
            this.setRowSelectionAllowed(false);
            this.setRowHeight((int) (MTXDatePicker.pop_Size.getHeight() - 70) / 6);

            DefaultTableModel dtm = new DefaultTableModel(pl, new String[7]) {

                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            this.setModel(dtm);
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            this.setCellSelectionEnabled(false);
        }

        public void setChoiceLabel(int day) {
            for (int i = 0; i < getRowCount(); i++) {
                for (int j = 0; j < getColumnCount(); j++) {
                    PickerLabel pl = ((PickerLabel) getModel().getValueAt(i, j));
                    if (pl.getText() != null && pl.getText().trim().length() > 0) {
                        if (day == Integer.parseInt(pl.getText())) {
                            if (choiceLabel != null) {
                                choiceLabel.setBackground(MTXDatePicker.YEAR_AND_MONTH_FONT_COLOR);
                                choiceLabel.choice = false;
                            }
                            choiceLabel = pl;
                            choiceLabel.choice = true;

                            if (hoverLabel != null) {
                                hoverLabel.setBackground(MTXDatePicker.YEAR_AND_MONTH_FONT_COLOR);
                            }
                            hoverLabel = pl;
                            hoverLabel.setBackground(MTXDatePicker.WEEK_COLOR1);
                        }
                    }
                }
            }
        }

        public TableCellRenderer getCellRenderer(int row, int column) {
            return new TableCellRenderer() {

                public Component getTableCellRendererComponent(JTable table,
                                                               Object value, boolean isSelected, boolean hasFocus,
                                                               int row, int column) {

                    PickerLabel pl = (PickerLabel) value;

                    if (hasFocus && pl.getText().trim().length() > 0) {
                        pl.setBackground(MTXDatePicker.WEEK_COLOR1);
                    }

                    return pl;
                }
            };
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            PickerLabel jl = (PickerLabel) this.getValueAt(this.rowAtPoint(e.getPoint()), this.columnAtPoint(e.getPoint()));
            if (jl.getText().trim().length() > 0) {
                if (choiceLabel != null) {
                    choiceLabel.setBackground(MTXDatePicker.YEAR_AND_MONTH_FONT_COLOR);
                    choiceLabel.choice = false;
                }
                choiceLabel = jl;
                choiceLabel.choice = true;
            }
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
            if (hoverLabel != null) {
                hoverLabel.setBackground(MTXDatePicker.YEAR_AND_MONTH_FONT_COLOR);
            }
            int rowIndex = this.rowAtPoint(e.getPoint());
            int colIndex = this.columnAtPoint(e.getPoint());
            if (rowIndex < 0 || colIndex < 0) {
                return;
            }
            hoverLabel = (PickerLabel) this.getValueAt(rowIndex, colIndex);
            if (!hoverLabel.getText().trim().equals("")) {
                hoverLabel.setBackground(MTXDatePicker.WEEK_COLOR1);
            }

            this.updateUI();
        }
    }

    public static void main(String args[]) throws Exception {

//        System.setProperty("swing.noxp", "true");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //UIManager.setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
        //UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
        JFrame jf = new JFrame();
        jf.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
        final MTXDatePicker jcb = new MTXDatePicker();
        //jcb.setEnabled(false);
        jcb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.err.println("getText" + jcb.getText());
                System.err.println("getDate" + jcb.getDate());
            }
        });
        jcb.setDate(new Date());
        jcb.setDateWithAction(new Date());
        jf.getContentPane().add(jcb);
        jf.getContentPane().add(new JButton(new AbstractAction("test") {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.err.println("getText" + jcb.getText());
            }
        }));
        //jf.getContentPane().add(new DatePicker());
        jf.setSize(320, 240);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }

    class CalendarPanel extends JPopupMenu {

        private JLayeredPane jlp = null;
        private JPanel jp_yearAndMonth = null;
        private JPanel jp_weekAndDay = null;
        //下拉选择年月的面板
        private JPanel jp_yearChoice = null;
        //显示主日期的面板
        private JPanel jp_main = null;
        private JButton jb_left = null;
        private JButton jb_right = null;
        Object[] item_year = new Object[100];
        Object[] item_month = new Object[12];
        private PickerTable jt = null;
        private static final int ANIMATION_FRAMES = 15;
        //当前动画帧
        private int frameIndex;
        private Timer timer = null;
        //存放返回日期的
        private JTextField jtf = null;
        //返回的日期字符串
        public String date = null;
        private JButton jb_today = null;
        private JButton jb_Ok = null;
        private JButton jb_Canel = null;
        private JLabel jl_MonthYearC1 = null;
        private LabelGroup monthGroup = null;
        private LabelGroup yearGroup = null;
        private PickerLabel[] pl_Year = null;
        private PickerLabel[] pl_Month = null;
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        //移入还是移除
        private boolean move_Out = false;
        private JLabel[] jl_week = new JLabel[]{new JLabel("日"), new JLabel("一"), new JLabel("二"), new JLabel("三"),
                new JLabel("四"), new JLabel("五"), new JLabel("六"),};

        class PickerPopAciton implements ActionListener {

            int y = jp_yearChoice.getY();

            public void actionPerformed(ActionEvent e) {
                int add_Y = jp_yearChoice.getHeight() * frameIndex / ANIMATION_FRAMES;

                //一直都是拉出去时会有问题
                //经测试估计是因为下面的组件不停updateUI导致界面停顿
                //此处如果用g = create()的话就会上拉的时候没有效果
                //但是如果使用g本身的话就会出现一块虚影。。。
                //最好的解决办法就是做动画时移出所有组件的位置到0,-y,然后做完后将位置移动回来即可

                //1.6直接用setLocation
                //cp.getGraphics().drawImage(dayImage, 1,1,cp.getWidth(),cp.getHeight(),null);
                //cp.getGraphics().drawImage(yearImage,0,(move_Out?y-add_Y:y+add_Y),cp.getWidth(),cp.getHeight(),null);

                jp_yearChoice.setLocation(0, (move_Out ? y - add_Y : y + add_Y));

                if (frameIndex == ANIMATION_FRAMES) {
                    //最后一帧，动画停止
                    timer.stop();

                    /*//如果是移入，就最后把组件放到指定位置
                    if(!move_Out){
                    jp_yearChoice.setLocation(0, 0);
                    }else{
                    jp_main.setLocation(0, 0);
                    }*/

                    updateUI();
                    frameIndex = 0;
                    y = jp_yearChoice.getY();
                } else {
                    frameIndex++;
                }
            }
        }

        public CalendarPanel(final JTextField jtf) {

            pl = new PickerLabel[6][7];

            for (int i = 0; i < pl.length; i++) {
                for (int j = 0; j < pl[i].length; j++) {
                    pl[i][j] = new PickerLabel();
                }
            }

            this.jtf = jtf;
            jt = new PickerTable();

            jt.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                        PickerLabel pl = (PickerLabel) jt.getValueAt(jt.rowAtPoint(e.getPoint()), jt.columnAtPoint(e.getPoint()));
                        if (pl != null && pl.getText().trim().length() > 0) {
                            CalendarPanel.this.setVisible(false);

                            String month = (choiceMonth < 9) ? ("0" + (choiceMonth + 1)) : ("" + (choiceMonth + 1));
                            choiceDay = Integer.parseInt(pl.getText());
                            String day = (choiceDay < 10) ? ("0" + choiceDay) : ("" + choiceDay);

                            jtf.setText(choiceYear + "-" + month + "-" + day);
                            //引发Action事件
                            fireActionPerformed();
                        }
                    }
                }
            });

            this.setLayout(new BorderLayout());

            //设置上部选择框
            this.jp_yearAndMonth = getYearAndMonthPanel();

            //设置中部选择框
            this.jp_weekAndDay = getWeekAndDayPanel();

            this.jp_yearChoice = getYearMonthChoicePanel();

            jlp = new JLayeredPane();

            jp_main = new JPanel();
            jp_main.setLocation(0, 0);
            jp_main.setSize(MTXDatePicker.pop_Size);
            jp_main.setLayout(new BorderLayout());

            jp_main.add(this.jp_yearAndMonth, BorderLayout.NORTH);
            jp_main.add(this.jp_weekAndDay, BorderLayout.CENTER);

            jlp.add(jp_main, JLayeredPane.DEFAULT_LAYER);
            jlp.add(this.jp_yearChoice, JLayeredPane.POPUP_LAYER);
            this.add(jlp);

            //设置大小
            this.setPreferredSize(MTXDatePicker.pop_Size);
            timer = new Timer(15, new PickerPopAciton());
        }

        public Insets getInsets() {
            return new Insets(1, 1, 1, 1);
        }

        private JPanel getYearMonthChoicePanel() {
            if (this.jp_yearChoice == null) {
                jp_yearChoice = new JPanel();
                this.jp_yearChoice.setLocation(0, -(int) MTXDatePicker.pop_Size.getHeight());
                this.jp_yearChoice.setSize(MTXDatePicker.pop_Size);

                jsp.setDividerSize(1);
                jsp.setEnabled(false);
                jsp.setBorder(new LineBorder(MTXDatePicker.WEEK_COLOR1));
                jsp.setDividerLocation((int) MTXDatePicker.pop_Size.getWidth() / 2);

                jsp.setLeftComponent(getMonthChoicePanel());
                jsp.setRightComponent(getYearChoicePanel());

                PickerToolBar jp_Ope = new PickerToolBar();
                jp_Ope.setFloatable(false);
                jp_Ope.setLayout(new FlowLayout());

                jb_Ok = new PickerButton("确定");
                jb_Ok.setFocusPainted(false);
                jb_Canel = new PickerButton("取消");
                jb_Canel.setFocusPainted(false);
                jb_Ok.setFont(new Font("", Font.PLAIN, 12));
                jb_Canel.setFont(new Font("", Font.PLAIN, 12));
                //jb_Ok.setUI(new BasicButtonUI());
                //jb_Canel.setUI(new BasicButtonUI());
                jp_Ope.add(jb_Ok);
                jp_Ope.add(jb_Canel);
                jp_Ope.setOpaque(false);

                this.jp_yearChoice.setLayout(new BorderLayout());
                this.jp_yearChoice.add(jsp, BorderLayout.CENTER);
                this.jp_yearChoice.add(jp_Ope, BorderLayout.SOUTH);
                this.jp_yearChoice.setBackground(MTXDatePicker.YEAR_AND_MONTH_FONT_COLOR);

                ac = new MoveOutListener();

                //aaaaaaaa
                jb_Ok.addActionListener(ac);
                jb_Ok.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        changeMonthYearChoice();
                    }
                });
                jb_Canel.addActionListener(ac);
            }
            return this.jp_yearChoice;
        }

        public void changeMonthYearChoice() {
            choiceYear = Integer.parseInt(yearGroup.getChoiceLabel().getText());
            choiceMonth = ((Integer) hm.get(monthGroup.getChoiceLabel().getText())).intValue();

            flushDay(choiceYear, choiceMonth);

            if (choiceMonth < 9) {
                jl_MonthYearC1.setText("0" + (choiceMonth + 1) + "月 " + choiceYear + "年");
            } else {
                jl_MonthYearC1.setText((choiceMonth + 1) + "月 " + choiceYear + "年");
            }
        }

        private JPanel getYearChoicePanel() {
            JPanel jp = new JPanel();

            jp.setLayout(new TableLayout2(6, 2, 8, 8));

            PickerLabel thisYearLabel = new PickerLabel("" + thisYear);

            pl_Year = new PickerLabel[]{
                    new PickerLabel("" + (thisYear - 4)), new PickerLabel("" + (thisYear + 1)),
                    new PickerLabel("" + (thisYear - 3)), new PickerLabel("" + (thisYear + 2)), new PickerLabel("" + (thisYear - 2)), new PickerLabel("" + (thisYear + 3)),
                    new PickerLabel("" + (thisYear - 1)), new PickerLabel("" + (thisYear + 4)), thisYearLabel, new PickerLabel("" + (thisYear + 5))
            };

            yearGroup = new LabelGroup(pl_Year);

            for (int i = 0; i < pl_Year.length; i++) {
                if (pl_Year[i].getText().equals(choiceYear + "")) {
                    yearGroup.setChoiceLabel(pl_Year[i]);
                }
                pl_Year[i].addMouseListener(new MouseAdapter() {

                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() >= 2) {
                            changeMonthYearChoice();
                            ac.actionPerformed(null);
                        }
                    }
                });
            }

            StyleArrowButton leftYear = new StyleArrowButton(BasicArrowButton.WEST, new Color(215, 231, 247), new Color(100, 120, 200),
                    new Color(233, 222, 233).brighter(), new Color(233, 244, 233).brighter(), Color.BLUE);

            StyleArrowButton rightYear = new StyleArrowButton(BasicArrowButton.EAST, new Color(215, 231, 247), new Color(100, 120, 200),
                    new Color(233, 222, 233).brighter(), new Color(233, 244, 233).brighter(), Color.BLUE);

            leftYear.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    for (int i = 0; i < pl_Year.length; i++) {
                        pl_Year[i].setText("" + (Integer.parseInt(pl_Year[i].getText()) - 10));
                        if (pl_Year[i].getText().equals(choiceYear + "")) {
                            yearGroup.setChoiceLabel(pl_Year[i]);
                        } else {
                            pl_Year[i].choice = false;
                        }
                    }
                }
            });

            rightYear.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < pl_Year.length; i++) {
                        pl_Year[i].setText("" + (Integer.parseInt(pl_Year[i].getText()) + 10));
                        if (pl_Year[i].getText().equals(choiceYear + "")) {
                            yearGroup.setChoiceLabel(pl_Year[i]);
                        } else {
                            pl_Year[i].choice = false;
                        }
                    }
                }
            });

            jp.add(leftYear);
            jp.add(rightYear);

            for (int i = 0; i < pl_Year.length; i++) {
                pl_Year[i].setForeground(MTXDatePicker.WEEK_FONT_COLOR);
                jp.add(pl_Year[i]);
            }
            jp.setBackground(MTXDatePicker.YEAR_AND_MONTH_FONT_COLOR);
            return jp;
        }

        private JPanel getMonthChoicePanel() {
            JPanel jp = new JPanel();
            jp.setLayout(new TableLayout2(6, 2, 8, 8));
            pl_Month = new PickerLabel[]{
                    new PickerLabel("一月"), new PickerLabel("二月"), new PickerLabel("三月"), new PickerLabel("四月"),
                    new PickerLabel("五月"), new PickerLabel("六月"), new PickerLabel("七月"), new PickerLabel("八月"),
                    new PickerLabel("九月"), new PickerLabel("十月"), new PickerLabel("十一月"), new PickerLabel("十二月")
            };

            monthGroup = new LabelGroup(pl_Month);
            monthGroup.setChoiceLabel(pl_Month[choiceMonth]);
            for (int i = 0; i < pl_Month.length; i++) {
                pl_Month[i].setOpaque(true);
                pl_Month[i].setForeground(MTXDatePicker.WEEK_FONT_COLOR);

                pl_Month[i].addMouseListener(new MouseAdapter() {

                    public void mouseClicked(MouseEvent e) {

                        if (e.getClickCount() >= 2) {
                            //aaaaa
                            changeMonthYearChoice();
                            ac.actionPerformed(null);
                        }
                    }
                });

                jp.add(pl_Month[i]);
            }
            jp.setBackground(Color.WHITE);
            return jp;
        }

        class MoveOutListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                move_Out = true;
                timer.start();
            }
        }

        private JToolBar getButtonToolBar() {

            JToolBar jtb = new PickerToolBar();
            jtb.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
            jtb.add(getJb_today());
            jtb.setFloatable(false);
            jtb.setPreferredSize(new Dimension(jtb.getPreferredSize().width, 33));
            return jtb;
        }

        private JButton getJb_today() {

            if (this.jb_today == null) {
                jb_today = new PickerButton("今天");
                jb_today.setPreferredSize(new Dimension(100, 24));
                jb_today.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        String mon = ((thisMonth + 1) < 10) ? ("0" + (thisMonth + 1)) : ("" + (thisMonth + 1));
                        String day = (today < 10) ? ("0" + today) : ("" + today);

                        jtf.setText(thisYear + "-" + mon + "-" + day);
                        jl_MonthYearC1.setText(mon + "月 " + thisYear + "年");

                        choiceYear = thisYear;
                        choiceMonth = thisMonth;
                        choiceDay = today;

                        flushDay(thisYear, thisMonth);
                        cp.setVisible(false);
                        monthGroup.setChoiceLabel(monthGroup.getPlGroup()[thisMonth]);
                        int m = 1;
                        int n = 1;
                        for (int i = 0; i < pl_Year.length; i++) {
                            pl_Year[i].setText(
                                    "" + (((i + 1) % 2) == 0 ? (thisYear + m++) : (thisYear - 5 + n++)));
                            if (pl_Year[i].getText().equals(choiceYear + "")) {
                                yearGroup.setChoiceLabel(pl_Year[i]);
                            }
                        }
                        fireActionPerformed();
                    }
                });
            }
            return jb_today;
        }

        private JPanel getWeekAndDayPanel() {
            if (this.jp_weekAndDay == null) {

                this.jp_weekAndDay = new JPanel();
                this.jp_weekAndDay.setLayout(new BorderLayout());
                this.jp_weekAndDay.add(getJToolBar(), BorderLayout.NORTH);

                this.jp_weekAndDay.add(jt, BorderLayout.CENTER);
                this.jp_weekAndDay.add(getButtonToolBar(), BorderLayout.SOUTH);
                this.jp_weekAndDay.setBackground(Color.white);

            }
            return this.jp_weekAndDay;
        }

        private JPanel getYearAndMonthPanel() {
            if (this.jp_yearAndMonth == null) {
                this.jp_yearAndMonth = new JPanel();

                this.jp_yearAndMonth.setBackground(MTXDatePicker.YEAR_AND_MONTH_COLOR);

                final StyleArrowButton bab = new StyleArrowButton(BasicArrowButton.SOUTH, new Color(215, 231, 247), new Color(100, 120, 200),
                        new Color(233, 222, 233).brighter(), new Color(233, 244, 233).brighter(), Color.BLUE);

                if (choiceMonth < 9) {
                    jl_MonthYearC1 = new JLabel("0" + (choiceMonth + 1) + "月 " + choiceYear + "年");
                } else {
                    jl_MonthYearC1 = new JLabel((choiceMonth + 1) + "月 " + choiceYear + "年");
                }

                jl_MonthYearC1.setForeground(MTXDatePicker.YEAR_AND_MONTH_FONT_COLOR);
                jl_MonthYearC1.setFont(new Font("", Font.PLAIN, 16));

                jb_left = new StyleArrowButton(BasicArrowButton.WEST, new Color(215, 231, 247), new Color(100, 120, 200),
                        new Color(233, 222, 233).brighter(), new Color(233, 244, 233).brighter(), Color.BLUE);

                jb_right = new StyleArrowButton(BasicArrowButton.EAST, new Color(215, 231, 247), new Color(100, 120, 200),
                        new Color(233, 222, 233).brighter(), new Color(233, 244, 233).brighter(), Color.BLUE);

                jp_yearAndMonth.setPreferredSize(new Dimension((int) MTXDatePicker.pop_Size.getWidth(), 20));
                jb_left.setBounds((int) (this.jp_yearAndMonth.getPreferredSize().getWidth() * 0.02), 2, 15, 15);
                jb_right.setBounds((int) (this.jp_yearAndMonth.getPreferredSize().getWidth() * 0.92), 2, 15, 15);

                int width = (int) (this.jp_yearAndMonth.getPreferredSize().getWidth() - jl_MonthYearC1.getPreferredSize().getWidth() - bab.getPreferredSize().getWidth()) / 2;

                jl_MonthYearC1.setBounds(width, 0, (int) jl_MonthYearC1.getPreferredSize().getWidth(), (int) jl_MonthYearC1.getPreferredSize().getHeight());
                bab.setBounds((int) (width + jl_MonthYearC1.getPreferredSize().getWidth()), 3, (int) bab.getPreferredSize().getWidth(), (int) bab.getPreferredSize().getHeight());

                jp_yearAndMonth.setLayout(null);
                jp_yearAndMonth.add(jb_left);
                jp_yearAndMonth.add(jb_right);
                jp_yearAndMonth.add(jl_MonthYearC1);
                jp_yearAndMonth.add(bab);

                jl_MonthYearC1.addMouseListener(new MouseAdapter() {

                    public void mousePressed(MouseEvent e) {
                        if (e.getSource() == jl_MonthYearC1) {
                            move_Out = false;
                            timer.start();
                        }
                    }
                });

                bab.addMouseListener(new MouseAdapter() {

                    public void mousePressed(MouseEvent e) {
                        if (e.getSource() == bab) {
                            move_Out = false;
                            timer.start();
                        }
                    }
                });

                jb_left.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        if (choiceMonth == 0) {
                            choiceMonth = 11;
                            choiceYear = choiceYear - 1;
                        } else {
                            choiceMonth = choiceMonth - 1;
                        }

                        flushDay(choiceYear, choiceMonth);

                        if (choiceMonth < 9) {
                            jl_MonthYearC1.setText("0" + (choiceMonth + 1) + "月 " + choiceYear + "年");
                        } else {
                            jl_MonthYearC1.setText((choiceMonth + 1) + "月 " + choiceYear + "年");
                        }
                    }
                });

                jb_right.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        if (choiceMonth == 11) {
                            choiceMonth = 0;
                            choiceYear = choiceYear + 1;
                        } else {
                            choiceMonth = choiceMonth + 1;
                        }

                        flushDay(choiceYear, choiceMonth);

                        if (choiceMonth < 9) {
                            jl_MonthYearC1.setText("0" + (choiceMonth + 1) + "月 " + choiceYear + "年");
                        } else {
                            jl_MonthYearC1.setText((choiceMonth + 1) + "月 " + choiceYear + "年");
                        }
                    }
                });
            }
            return this.jp_yearAndMonth;
        }

        private JToolBar getJToolBar() {

            JToolBar jtb = new PickerToolBar();
            jtb.setLayout(new GridLayout(1, 7));
            //把星期加进去
            for (int i = 0; i < jl_week.length; i++) {
                jl_week[i].setForeground(MTXDatePicker.WEEK_FONT_COLOR);
                jl_week[i].setFont(jl_week[i].getFont().deriveFont(15f));
                jl_week[i].setOpaque(false);
                jl_week[i].setHorizontalAlignment(JLabel.CENTER);
                jtb.add(jl_week[i]);
            }
            jtb.setFloatable(false);
            jtb.setPreferredSize(new Dimension(jtb.getPreferredSize().width, 22));
            return jtb;
        }

        public void setDay(int year, int month, int day) {
            if (month < 9) {
                jl_MonthYearC1.setText("0" + (month + 1) + "月 " + year + "年");
            } else {
                jl_MonthYearC1.setText((month + 1) + "月 " + year + "年");
            }
            for (PickerLabel pl : yearGroup.getPlGroup()) {
                if (year == Integer.parseInt(pl.getText())) {
                    yearGroup.setChoiceLabel(pl);
                    break;
                }
            }
            for (PickerLabel pl : monthGroup.getPlGroup()) {
                int plInt = ((Integer) hm.get(pl.getText())).intValue();
                if (month == plInt) {
                    monthGroup.setChoiceLabel(pl);
                    break;
                }
            }
            flushDay(year, month);
            setDay(day);
        }

        public void flushDay(int year, int month) {

            //cc.set(field, value),最关键的方法，通过设置为某年，某月，来取得日子信息
            //例如:取得1990年5月 注意：月份是当前物理月份+1，但是set()方法里面还是get()的那个值
            cc.set(Calendar.YEAR, year);
            cc.set(Calendar.MONTH, month);
            cc.set(Calendar.DAY_OF_MONTH, 1);
            int maxDayNo = cc.getActualMaximum(Calendar.DAY_OF_MONTH);
            int dayNo = 2 - cc.get(Calendar.DAY_OF_WEEK);

            //把日加进去
            for (int i = 0; i < jt.getRowCount(); i++) {
                for (int j = 0; j < jt.getColumnCount(); j++) {

                    if (dayNo >= 1 && dayNo <= maxDayNo) {

                        ((PickerLabel) jt.getModel().getValueAt(i, j)).setText("" + dayNo);

                        if (dayNo == today && year == thisYear && month == thisMonth) {
                            ((PickerLabel) jt.getModel().getValueAt(i, j)).setBackground(Color.ORANGE);
                        }
                    } else {
                        ((PickerLabel) jt.getModel().getValueAt(i, j)).setText("");
                    }

                    dayNo++;
                }
            }
            this.updateUI();
        }

        public void setDay(int day) {
            jt.setChoiceLabel(day);
        }
    }
}

class StyleArrowButton extends JButton implements SwingConstants {

    protected int direction;
    //边框渐变色
    private Color borderColor1;
    //边框渐变色
    private Color borderColor2;
    //填充渐变色
    private Color fillColor1;
    //填充渐变色
    private Color fillColor2;
    //三角色平常色
    private Color triangleColor;

    public StyleArrowButton(int direction, Color BorderColor1, Color BorderColor2,
                            Color fillColor1, Color fillColor2, Color triangleColor) {

        super();
        this.setRolloverEnabled(false);
        this.setOpaque(false);
        this.setFocusPainted(false);
        setRequestFocusEnabled(false);
        setDirection(direction);
        this.borderColor1 = BorderColor1;
        this.borderColor2 = BorderColor2;
        this.fillColor1 = fillColor1;
        this.fillColor2 = fillColor2;
        this.triangleColor = triangleColor;
    }

    public StyleArrowButton(int direction) {
        this(direction, UIManager.getColor("control"), UIManager.getColor("controlShadow"),
                UIManager.getColor("controlDkShadow"), UIManager.getColor("controlLtHighlight"),
                Color.BLACK);
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int dir) {
        direction = dir;
    }

    public void paint(Graphics g) {
        Color origColor;
        boolean isPressed, isEnabled;
        int w, h, size;

        w = getSize().width;
        h = getSize().height;

        origColor = g.getColor();
        isPressed = getModel().isPressed();
        isEnabled = isEnabled();

        //fill RoundRect
        GradientPaint fillGP = new GradientPaint(0, 0, this.fillColor1, 0, h, this.fillColor2);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(fillGP);
        g2.fillRoundRect(1, 1, w - 2, h - 2, 3, 3);

        //Draw the proper Border
        //pressPaint
        GradientPaint borderGP1 = new GradientPaint(0, 0, this.borderColor1, w, h, this.borderColor2);
        g2.setPaint(borderGP1);
        g2.drawRoundRect(0, 0, w - 1, h - 1, 3, 3);

        if (isPressed) {
            GradientPaint borderGP2 = new GradientPaint(0, 0, this.borderColor2, w, h, this.borderColor1);
            g2.setPaint(borderGP2);
            g2.drawRoundRect(0, 0, w - 1, h - 1, 3, 3);
        }

        //If there's no room to draw arrow, bail
        if (h < 5 || w < 5) {
            g.setColor(origColor);
            return;
        }

        /*if (isPressed) {
        g.translate(1, 1);
        }*/

        // Draw the arrow
        size = Math.min((h - 4) / 3, (w - 4) / 3);
        size = Math.max(size, 2);
        paintTriangle(g, (w - size) / 2, (h - size) / 2,
                size, direction, isEnabled);

        // Reset the Graphics back to it's original settings
//            if (isPressed) {
//                g.translate(-1, -1);
//            }
        g.setColor(origColor);

    }

    public Dimension getPreferredSize() {
        return new Dimension(16, 16);
    }

    public Dimension getMinimumSize() {
        return new Dimension(5, 5);
    }

    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public boolean isFocusTraversable() {
        return false;
    }

    public void paintTriangle(Graphics g, int x, int y, int size,
                              int direction, boolean isEnabled) {

        Color oldColor = g.getColor();
        int mid, i, j;

        j = 0;
        size = Math.max(size, 2);
        mid = (size / 2) - 1;

        g.translate(x, y);
        if (isEnabled) {
            g.setColor((Color) UIManager.getColor("controlDkShadow"));
        } else {
            g.setColor(triangleColor);
        }

        switch (direction) {
            case NORTH:
                for (i = 0; i < size; i++) {
                    g.drawLine(mid - i, i, mid + i, i);
                }
                if (!isEnabled) {
                    g.setColor(triangleColor);
                    g.drawLine(mid - i + 2, i, mid + i, i);
                }
                break;
            case SOUTH:
                if (!isEnabled) {
                    g.translate(1, 1);
                    g.setColor(triangleColor);
                    for (i = size - 1; i >= 0; i--) {
                        g.drawLine(mid - i, j, mid + i, j);
                        j++;
                    }
                    g.translate(-1, -1);
                    g.setColor(triangleColor);
                }

                j = 0;
                for (i = size - 1; i >= 0; i--) {
                    g.drawLine(mid - i, j, mid + i, j);
                    j++;
                }
                break;
            case WEST:
                for (i = 0; i < size; i++) {
                    g.drawLine(i, mid - i, i, mid + i);
                }
                if (!isEnabled) {
                    g.setColor(triangleColor);
                    g.drawLine(i, mid - i + 2, i, mid + i);
                }
                break;
            case EAST:
                if (!isEnabled) {
                    g.translate(1, 1);
                    g.setColor(triangleColor);
                    for (i = size - 1; i >= 0; i--) {
                        g.drawLine(j, mid - i, j, mid + i);
                        j++;
                    }
                    g.translate(-1, -1);
                    g.setColor(triangleColor);
                }

                j = 0;
                for (i = size - 1; i >= 0; i--) {
                    g.drawLine(j, mid - i, j, mid + i);
                    j++;
                }
                break;
        }
        g.translate(-x, -y);
        g.setColor(oldColor);
    }
}

class PickerButton extends JButton {

    //透明度
    private float transparence = .3f;
    private int pressed = 0;

    public PickerButton() {
        setFont(new Font(null, Font.PLAIN, 12));
        setBorderPainted(false);
        setForeground(Color.BLACK);
        setFocusPainted(false);
        setContentAreaFilled(false);

        addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {

                transparence = 1f;
                pressed = 1;
            }

            public void mouseExited(MouseEvent e) {
                transparence = .3f;
                pressed = 0;
            }

            public void mousePressed(MouseEvent e) {
                pressed = 2;
            }

            public void mouseReleased(MouseEvent e) {
                transparence = .3f;
                pressed = 0;
            }
        });
        setPreferredSize(new Dimension(50, 24));
    }

    public PickerButton(String string) {
        this();
        setText(string);
    }

    public PickerButton(String title, JPopupMenu jpm) {
        this();
        setText(title);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        int h = getHeight();
        int w = getWidth();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint p1;
        GradientPaint p2;

        if (getModel().isPressed()) {
            p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1, new Color(100, 100, 100));
            p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 3, new Color(255, 255, 255, 50));
        } else {
            //边框从0,0点到最下面是从灰到黑的渐变
            p1 = new GradientPaint(0, 0, new Color(100, 100, 100), 0, h - 1, new Color(0, 0, 0));
            //边框从0,1点到最下面是从白到黑的渐变
            p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 50), 0, h - 3, new Color(0, 0, 0, 50));
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparence));

        //这个决定背景的形状和颜色
        RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, 10, 10);

        Shape clip = g2d.getClip();
        g2d.clip(r2d);

        GradientPaint gp = null;
        //这个决定背景颜色的渐变
        if (pressed == 0) {
            gp = new GradientPaint(0f, 0f, new Color(247, 246, 245), 0f, h, new Color(222, 222, 221), true);
        }
        if (pressed == 1) {
            gp = new GradientPaint(0f, 0f, new Color(197, 219, 245, 110), 0f, h, new Color(197, 219, 245, 110), true);
        }
        if (pressed == 2) {
            gp = new GradientPaint(0f, 0f, new Color(148, 195, 245, 40), 0f, h, new Color(148, 195, 245, 40), true);
        }

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        g2d.setClip(clip);
        g2d.setPaint(p1);
        //这个后面的两个参数要和r2d的两个参数对应上，即弧度要一样
        g2d.drawRoundRect(0, 0, w - 1, h - 1, 10, 10);
        g2d.setPaint(p2);
        g2d.drawRoundRect(1, 1, w - 3, h - 3, 9, 9);
        g2d.dispose();
        super.paintComponent(g);

    }
}

class TableLayout2 implements LayoutManager2 {

    public static final Integer LEFT = new Integer(-1);
    public static final Integer CENTER = new Integer(0);
    public static final Integer RIGHT = new Integer(1);
    int cellWidth = 0;
    int cellHeight = 0;
    //水平间距
    public int hgap = 5;
    //垂直间距
    public int vagp = 5;
    public int align = 0;
    private Hashtable ht;
    private int rows;
    private int columns;

    public TableLayout2(int rows, int columns) {
        ht = new Hashtable();
        this.rows = rows;
        this.columns = columns;
    }

    public TableLayout2(int rows, int columns, int hgap, int vagp) {
        this(rows, columns);
        this.hgap = hgap;
        this.vagp = vagp;
    }

    public TableLayout2(int rows, int columns, int align) {
        this(rows, columns);
        this.align = align;
    }

    public TableLayout2(int rows, int columns, int hgap, int vagp, int align) {
        this(rows, columns, hgap, vagp);
        this.align = align;
    }

    public void addLayoutComponent(Component comp, Object constraints) {
        //没设置每个单元格的对其格式就全局对齐方式
        if (constraints == null) {
            synchronized (comp.getTreeLock()) {
                ht.put(comp, new Integer(align));
            }
        } else if (constraints != null && !(constraints instanceof Integer)) {
            throw new IllegalArgumentException("align must be Integer");
        } else {
            synchronized (comp.getTreeLock()) {
                ht.put(comp, constraints);
            }
        }
    }

    public float getLayoutAlignmentX(Container target) {

        return 0;
    }

    public float getLayoutAlignmentY(Container target) {

        return 0;
    }

    public void invalidateLayout(Container target) {
    }

    public Dimension maximumLayoutSize(Container target) {

        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public void addLayoutComponent(String name, Component comp) {
    }

    //所有布局类中最重要的一个类,布局管理器会先执行这个，然后执行大小
    public void layoutContainer(Container parent) {

        int x = hgap;
        int y = vagp;
        int count = 0;
        int width = 0;
        int height = 0;

        Component[] comps = parent.getComponents();

        //如果有设置大小就强制内容大小，如果没有就自适应大小
        if (parent.getPreferredSize().getWidth() > 0 && parent.getPreferredSize().getHeight() > 0) {
            Insets inset = parent.getInsets();
            cellWidth = (int) (parent.getWidth() - inset.left - inset.right - vagp * (columns + 1)) / columns;
            cellHeight = (int) (parent.getHeight() - inset.top - inset.bottom - hgap * (rows + 1)) / rows;
        } else {
            for (int i = 0; i < comps.length; i++) {
                Dimension d = comps[i].getPreferredSize();
                if (cellWidth < d.width) {
                    cellWidth = d.width;
                }
                if (cellHeight < d.height) {
                    cellHeight = d.height;
                }
            }
        }


        //布局每个单元格
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {

                //获取对齐方式,根据对齐方式 设置组件位置
                int align = ((Integer) ht.get(comps[count])).intValue();

                //设置了preferredSize之后 如果组件大小大于 单元格大小，那组件大小会强制为单元格大小
                width = comps[count].getPreferredSize().width;
                height = comps[count].getPreferredSize().height;

                if (width > cellWidth) {
                    width = cellWidth;
                }
                if (height > cellHeight) {
                    height = cellHeight;
                }

                //左
                int offSetX = 0;
                int offSetY = (cellHeight - height) / 2;

                //中
                if (align == 0) {
                    offSetX = (cellWidth - width) / 2;
                    //右
                } else if (align == 1) {
                    offSetX = cellWidth - width;
                }

                comps[count].setBounds(x + offSetX, y + offSetY, width, height);

                count++;
                x += cellWidth + hgap;
            }
            x = hgap;
            y += cellHeight + vagp;
        }
    }

    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(50, 50);
    }

    //很显然在1.4就算设置了也是会进来的,顺序layoutContainer----preferredLayoutSize
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            return new Dimension(insets.left + insets.right + cellWidth * columns + (columns + 1) * hgap,
                    insets.top + insets.bottom + cellHeight * rows + (rows + 1) * vagp);
        }
    }

    public void removeLayoutComponent(Component comp) {
        synchronized (comp.getTreeLock()) {
            ht.remove(comp);
        }
    }
}
