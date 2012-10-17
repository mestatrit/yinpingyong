package com.mt.common.gui.table;

import com.mt.common.Formatter;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.text.Collator;
import java.util.*;
import java.util.List;

/**
 * 支持多列排序的表格排序器
 *
 * @author hanhui
 */
public class MultiColumnTableSorter extends AbstractTableModel {

    protected TableModel tableModel;
    public static final int DESCENDING = -1;
    public static final int NOT_SORTED = 0;
    public static final int ASCENDING = 1;
    private static Directive EMPTY_DIRECTIVE = new Directive(-1, NOT_SORTED);
    private boolean usingClassicArrow = false;
    private int startRow = -1;
    private int endRow = -1;

    public void setUsingClassicArrow(boolean b) {
        usingClassicArrow = b;
    }

    public boolean usingClassicArrow() {
        return usingClassicArrow;
    }

    private boolean usingCollators = true;

    public void setUsingCollators(boolean b) {
        usingCollators = b;
        setupCollators();
    }

    public boolean usingCollators() {
        return usingCollators;
    }

    private Comparator comparableComparator;
    private Comparator lexicalComparator;
    private Row[] viewToModel;
    private int[] modelToView;
    private JTableHeader tableHeader;
    private MouseListener mouseListener;
    private TableModelListener tableModelListener;
    private Map columnComparators = new HashMap();
    private List sortingColumns = new ArrayList();

    public MultiColumnTableSorter() {
        this.mouseListener = new MouseHandler();
        this.tableModelListener = new TableModelHandler();
        setupCollators();
        DateComparator dc = new DateComparator();
        setColumnComparator(MCDate.class, dc);
        setColumnComparator(Date.class, dc);

        NumberComparator nc = new NumberComparator();
        setColumnComparator(MCNumber.class, nc);
        setColumnComparator(Number.class, nc);
        setColumnComparator(Double.class, nc);
        setColumnComparator(Integer.class, nc);
        setColumnComparator(Long.class, nc);

        TermComparator tc = new TermComparator();
        setColumnComparator(MCTerm.class, tc);

        TermTypeComparator ttc = new TermTypeComparator();
        setColumnComparator(MCType.class, ttc);
    }

    public MultiColumnTableSorter(TableModel tableModel) {
        this();
        setTableModel(tableModel);
    }

    public MultiColumnTableSorter(TableModel tableModel,
                                  JTableHeader tableHeader) {
        this();
        setTableHeader(tableHeader);
        setTableModel(tableModel);
    }

    protected void setupCollators() {
        if (usingCollators()) {
            comparableComparator = new Comparator() {

                public int compare(Object o1, Object o2) {
                    if (o1 instanceof String || !(o1 instanceof Comparable)) {
                        Collator collator = Collator.getInstance();
                        collator.setStrength(Collator.SECONDARY);
                        return collator.compare(o1.toString(), o2.toString());
                    } else {
                        return ((Comparable) o1).compareTo(o2);
                    }
                }
            };

            lexicalComparator = new Comparator() {

                public int compare(Object o1, Object o2) {
                    Collator collator = Collator.getInstance();
                    collator.setStrength(Collator.SECONDARY);
                    return collator.compare(o1.toString(), o2.toString());
                }
            };

        } else {

            comparableComparator = new Comparator() {

                public int compare(Object o1, Object o2) {
                    if (o1 instanceof String || !(o1 instanceof Comparable)) {
                        return o1.toString().compareTo(o2.toString());
                    } else {
                        return ((Comparable) o1).compareTo(o2);
                    }
                }
            };

            lexicalComparator = new Comparator() {

                public int compare(Object o1, Object o2) {
                    return o1.toString().compareTo(o2.toString());
                }
            };

        }
    }

    private void clearSortingState() {
        viewToModel = null;
        modelToView = null;
    }

    public void clearSorting() {
        clearSortingState();
        fireTableStructureChanged();
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        if (this.tableModel != null) {
            this.tableModel.removeTableModelListener(tableModelListener);
        }

        this.tableModel = tableModel;
        if (this.tableModel != null) {
            this.tableModel.addTableModelListener(tableModelListener);
        }

        clearSortingState();
        fireTableStructureChanged();
    }

    public JTableHeader getTableHeader() {
        return tableHeader;
    }

    public void setTableHeader(JTableHeader tableHeader) {
        if (this.tableHeader != null) {
            this.tableHeader.removeMouseListener(mouseListener);
            TableCellRenderer defaultRenderer = this.tableHeader.getDefaultRenderer();
            if (defaultRenderer instanceof SortableHeaderRenderer) {
                this.tableHeader.setDefaultRenderer(((SortableHeaderRenderer) defaultRenderer).tableCellRenderer);
            }
        }
        this.tableHeader = tableHeader;
        if (this.tableHeader != null) {
            this.tableHeader.addMouseListener(mouseListener);
            this.tableHeader.setDefaultRenderer(new SortableHeaderRenderer(
                    this.tableHeader.getDefaultRenderer()));
        }

    }

    public boolean isSorting() {
        return !sortingColumns.isEmpty();
    }

    private Directive getDirective(int column) {
        for (int i = 0; i < sortingColumns.size(); i++) {
            Directive directive = (Directive) sortingColumns.get(i);
            if (directive.column == column) {
                return directive;
            }
        }
        return EMPTY_DIRECTIVE;
    }

    public int getSortingStatus(int column) {
        return getDirective(column).direction;
    }

    private void sortingStatusChanged() {
        clearSortingState();

        fireTableDataChanged();
        if (tableHeader != null) {
            tableHeader.repaint();
        }
    }

    public void setSortingStatus(int column, int status) {
        Directive directive = getDirective(column);
        if (directive != EMPTY_DIRECTIVE) {
            sortingColumns.remove(directive);

        }
        if (status != NOT_SORTED) {
            sortingColumns.add(new Directive(column, status));
        }
        sortingStatusChanged();
    }

    protected Icon getHeaderRendererIcon(int column, int size) {
        Directive directive = getDirective(column);
        if (directive == EMPTY_DIRECTIVE) {
            return null;
        }
        return createArrow(directive.direction == DESCENDING, size,
                sortingColumns.indexOf(directive));
    }

    private void cancelSorting() {
        sortingColumns.clear();
        sortingStatusChanged();
    }

    public void setColumnComparator(Class type, Comparator comparator) {
        if (comparator == null) {
            columnComparators.remove(type);
        } else {
            columnComparators.put(type, comparator);
        }
    }

    protected Comparator getComparator(int column) {
        Class columnType = tableModel.getColumnClass(column);
        Comparator comparator = (Comparator) columnComparators.get(columnType);
        if (comparator != null) {
            return comparator;
        }

        if (Comparable.class.isAssignableFrom(columnType)) {
            return comparableComparator;
        }
        return lexicalComparator;
    }

    private Row[] getViewToModel() {
        if (viewToModel == null) {
            int tableModelRowCount = tableModel.getRowCount();
            viewToModel = new Row[tableModelRowCount];
            ArrayList<Row> sortRows = new ArrayList<Row>();
            ArrayList<Row> fixedRows = new ArrayList<Row>();

            for (int row = 0; row < tableModelRowCount; row++) {
                viewToModel[row] = new Row(row);
                if (row >= startRow && row <= endRow) {
                    fixedRows.add(new Row(row));
                } else {
                    sortRows.add(new Row(row));
                }
            }

            if (isSorting()) {
                Collections.sort(sortRows);
                if (sortRows.size() == tableModelRowCount) {
                    viewToModel = sortRows.toArray(viewToModel);
                } else {
                    sortRows.addAll(startRow, fixedRows);
                    viewToModel = sortRows.toArray(viewToModel);
                }
            }
        }
        return viewToModel;
    }

    /**
     * 设置表格固定区间，这个区间内的行将不受排序影响
     *
     * @param s
     * @param e
     */
    public void setFixedRows(int s, int e) {
        startRow = s;
        endRow = e;
    }

    public int modelIndex(int viewIndex) {
        return getViewToModel()[viewIndex].modelIndex;
    }

    private int[] getModelToView() {
        if (modelToView == null) {
            int n = getViewToModel().length;
            modelToView = new int[n];
            for (int i = 0; i < n; i++) {
                modelToView[modelIndex(i)] = i;
            }
        }
        return modelToView;
    }

    // TableModel interface methods
    public int getRowCount() {
        return (tableModel == null) ? 0 : tableModel.getRowCount();
    }

    public int getColumnCount() {
        return (tableModel == null) ? 0 : tableModel.getColumnCount();
    }

    public String getColumnName(int column) {
        return tableModel.getColumnName(column);
    }

    public Class getColumnClass(int column) {
        return tableModel.getColumnClass(column);
    }

    public boolean isCellEditable(int row, int column) {
        return tableModel.isCellEditable(modelIndex(row), column);
    }

    public Object getValueAt(int row, int column) {
        return tableModel.getValueAt(modelIndex(row), column);
    }

    public void setValueAt(Object aValue, int row, int column) {
        tableModel.setValueAt(aValue, modelIndex(row), column);
    }

    // Helper classes
    private class Row implements Comparable {

        private int modelIndex;

        public Row(int index) {
            this.modelIndex = index;
        }

        public int compareTo(Object o) {
            int row1 = modelIndex;
            int row2 = ((Row) o).modelIndex;

            for (Iterator it = sortingColumns.iterator(); it.hasNext(); ) {
                Directive directive = (Directive) it.next();
                int column = directive.column;
                Object o1 = tableModel.getValueAt(row1, column);
                Object o2 = tableModel.getValueAt(row2, column);

                int comparison = 0;
                // Define null less than everything, except null.
                if (o1 == null && o2 == null) {
                    comparison = 0;
                } else if (o1 == null) {
                    comparison = -1;
                } else if (o2 == null) {
                    comparison = 1;
                } else {
                    comparison = getComparator(column).compare(o1, o2);
                }
                if (comparison != 0) {
                    return directive.direction == DESCENDING ? -comparison
                            : comparison;
                }
            }
            return 0;
        }
    }

    /**
     * 日期比较器
     *
     * @author hanhui
     */
    private class DateComparator implements Comparator {

        public int compare(Object o1, Object o2) {
            Date d1 = null;
            Date d2 = null;
            if (o1 instanceof Date) {
                d1 = (Date) o1;
            } else {
                d1 = Formatter.getDate(o1.toString());
            }

            if (o2 instanceof Date) {
                d2 = (Date) o2;
            } else {
                d2 = Formatter.getDate(o2.toString());
            }

            if (d1 != null && d2 != null) {
                return d1.compareTo(d2);
            } else {
                // 不是日期转为字符串比较
                return o1.toString().compareTo(o2.toString());
            }
        }
    }

    /**
     * 数值比较器
     *
     * @author hanhui
     */
    private class NumberComparator implements Comparator {

        public int compare(Object o1, Object o2) {
            Number n1 = null;
            Number n2 = null;
            if (o1 instanceof Number) {
                n1 = (Number) o1;
            } else {
                n1 = Formatter.getNumber(o1.toString());
            }

            if (o2 instanceof Number) {
                n2 = (Number) o2;
            } else {
                n2 = Formatter.getNumber(o2.toString());
            }

            if (n1 != null && n2 != null) {
                double dn1 = n1.doubleValue();
                double dn2 = n2.doubleValue();
                if (dn1 > dn2) {
                    return 1;
                } else if (dn1 < dn2) {
                    return -1;
                } else {
                    return 0;
                }
            } else {
                // 不是数字转为字符串比较
                return o1.toString().compareTo(o2.toString());
            }
        }
    }

    /**
     * 用于排序的发行期限/剩余期限类型，如n年n月n日
     *
     * @author richard
     */
    private class TermComparator implements Comparator {

        public int compare(Object o1, Object o2) {

            try {
                String s1 = o1.toString();
                String s2 = o2.toString();
                int y1 = Integer.parseInt(s1.substring(0, s1.indexOf("年")));
                int m1 = Integer.parseInt(s1.substring(s1.indexOf("年") + 1, s1.indexOf("个")));
                int d1 = Integer.parseInt(s1.substring(s1.indexOf("月") + 1, s1.indexOf("天")));

                int y2 = Integer.parseInt(s2.substring(0, s2.indexOf("年")));
                int m2 = Integer.parseInt(s2.substring(s2.indexOf("年") + 1, s2.indexOf("个")));
                int d2 = Integer.parseInt(s2.substring(s2.indexOf("月") + 1, s2.indexOf("天")));

                if (y1 > y2) {
                    return 1;
                } else if (y1 < y2) {
                    return -1;
                } else {
                    if (m1 > m2) {
                        return 1;
                    } else if (m1 < m2) {
                        return -1;
                    } else {
                        if (d1 > d2) {
                            return 1;
                        } else if (d1 < d2) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }

            } catch (Exception e) {
                return o1.toString().compareTo(o2.toString());
            }

        }
    }

    /**
     * 用于排序外汇及利率产品的期限品种
     */
    private class TermTypeComparator implements Comparator {

        int count = MCType.libMap.size();

        @Override
        public int compare(Object o1, Object o2) {
            String s1 = o1.toString();
            s1 = s1.toUpperCase();
            String s2 = o2.toString();
            s2 = s2.toUpperCase();
            int i1 = getIndex(s1);
            int i2 = getIndex(s2);


            if (i1 == count && s1.length() >= 3) {
                i1 = getIndex(s1.substring(s1.length() - 3));
            }
            if (i1 == count && s1.length() >= 2) {
                i1 = getIndex(s1.substring(s1.length() - 2));
            }


            if (i2 == count && s2.length() >= 3) {
                i2 = getIndex(s2.substring(s2.length() - 3));
            }
            if (i2 == count && s2.length() >= 2) {
                i2 = getIndex(s2.substring(s2.length() - 2));
            }

            if (i1 > i2) {
                return 1;
            } else if (i1 < i2) {
                return -1;
            } else {
                return s1.compareTo(s2);
            }
        }

        private int getIndex(String s) {
            int index = count;
            if (MCType.libMap.containsKey(s)) {
                index = MCType.libMap.get(s);
            }
            return index;
        }
    }

    public static void main(String[] arg0) {
        String s1 = "12年0个月7天";
        String s2 = "6年11个月21天";

        System.err.println(s1.substring(0, s1.indexOf("年")));
        System.err.println(s1.substring(s1.indexOf("年") + 1, s1.indexOf("个")));
        System.err.println(s1.substring(s1.indexOf("月") + 1, s1.indexOf("天")));

        System.err.println(s2.substring(0, s2.indexOf("年")));
        System.err.println(s2.substring(s2.indexOf("年") + 1, s2.indexOf("个")));
        System.err.println(s2.substring(s2.indexOf("月") + 1, s2.indexOf("天")));

        String[] test = new String[]{"IBO1Y", "IBO9M", "IBO2M", "IBO021", "IBO001", "IBO007", "OR021", "1W", "2W"};
        List<String> testList = Arrays.asList(test);
        MultiColumnTableSorter sorter = new MultiColumnTableSorter();
        Collections.sort(testList, sorter.new TermTypeComparator());

        for (String i : testList) {
            System.out.println(i);
        }
    }

    private class TableModelHandler implements TableModelListener {

        public void tableChanged(TableModelEvent e) {
            // If we're not sorting by anything, just pass the event along.
            if (!isCloseSorting || !isSorting()) {
                clearSortingState();
                fireTableChanged(e);
                return;
            }

            // If the table structure has changed, cancel the sorting; the
            // sorting columns may have been either moved or deleted from
            // the model.
            if (e.getFirstRow() == TableModelEvent.HEADER_ROW) {
                cancelSorting();
                fireTableChanged(e);
                return;
            }

            // We can map a cell event through to the view without widening
            // when the following conditions apply:
            //
            // a) all the changes are on one row (e.getFirstRow() ==
            // e.getLastRow()) and,
            // b) all the changes are in one column (column !=
            // TableModelEvent.ALL_COLUMNS) and,
            // c) we are not sorting on that column
            // (getSortingStatus(column) == NOT_SORTED) and,
            // d) a reverse lookup will not trigger a sort (modelToView !=
            // null)
            //
            // Note: INSERT and DELETE events fail this test as they have
            // column == ALL_COLUMNS.
            //
            // The last check, for (modelToView != null) is to see if
            // modelToView
            // is already allocated. If we don't do this check; sorting can
            // become
            // a performance bottleneck for applications where cells
            // change rapidly in different parts of the table. If cells
            // change alternately in the sorting column and then outside of
            // it this class can end up re-sorting on alternate cell updates
            // -
            // which can be a performance problem for large tables. The last
            // clause avoids this problem.
            int column = e.getColumn();
            if (e.getFirstRow() == e.getLastRow()
                    && column != TableModelEvent.ALL_COLUMNS
                    && getSortingStatus(column) == NOT_SORTED
                    && modelToView != null) {
                int viewIndex = getModelToView()[e.getFirstRow()];
                fireTableChanged(new TableModelEvent(
                        MultiColumnTableSorter.this, viewIndex, viewIndex,
                        column, e.getType()));
                return;
            }

            // Something has happened to the data that may have invalidated
            // the row order.
            clearSortingState();
            fireTableDataChanged();
            return;
        }
    }

    private boolean isCloseSorting = true;

    public void closeSorting() {
        isCloseSorting = false;
    }

    public void openSorting() {
        isCloseSorting = true;
    }

    private class MouseHandler extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (isCloseSorting) {
                    JTableHeader h = (JTableHeader) e.getSource();
                    TableColumnModel columnModel = h.getColumnModel();
                    int viewColumn = columnModel.getColumnIndexAtX(e.getX());
                    if (viewColumn >= 0) {
                        int column = columnModel.getColumn(viewColumn).getModelIndex();
                        if (column != -1) {
                            int status = getSortingStatus(column);
                            status = status + (e.isShiftDown() ? -1 : 1);
                            status = (status + 4) % 3 - 1; // signed mod, returning
                            // {-1, 0, 1}
                            setSortingStatus(column, status);
                        }
                    }
                }
            }
        }
    }

    private Icon createArrow(boolean descending, int size, int priority) {
        return usingClassicArrow ? (Icon) new ClassicArrow(descending, size,
                priority) : new Arrow(descending, size, priority);
    }

    private static class Arrow implements Icon, Serializable {

        private boolean descending;
        private int priority;
        private int size;

        public Arrow(boolean descending, int size, int priority) {
            this.descending = descending;
            this.size = size;
            this.priority = priority;

        }

        public void paintIcon(Component c, Graphics g, int x, int y) {

            // Override base size with a value calculated from the
            // component's font.
            updateSize(c);

            Color color = c == null ? Color.BLACK : c.getForeground();
            g.setColor(color);

            int npoints = 3;
            int[] xpoints = new int[]{0, size / 2, size};
            int[] ypoints = descending ? new int[]{0, size, 0} : new int[]{
                    size, 0, size};

            Polygon triangle = new Polygon(xpoints, ypoints, npoints);

            // Center icon vertically within the column heading label.
            int dy = (c.getHeight() - size) / 2;

            g.translate(x, dy);
            g.drawPolygon(triangle);
            g.fillPolygon(triangle);
            g.translate(-x, -dy);

            g.drawString(String.valueOf(priority + 1), x + size + 2, y
                    + (c.getHeight() / 2 - size / 2) + dy);

        }

        public int getIconWidth() {
            return size;
        }

        public int getIconHeight() {
            return size;
        }

        private void updateSize(Component c) {
            if (c != null) {
                FontMetrics fm = c.getFontMetrics(c.getFont());
                int baseHeight = fm.getAscent();

                // In a compound sort, make each succesive triangle 20%
                // smaller than the previous one.
                size = (int) (baseHeight * 3 / 4);// * Math.pow(0.8,
                // priority));
            }
        }
    }

    private static class ClassicArrow implements Icon {

        private boolean descending;
        private int size;
        private int priority;

        public ClassicArrow(boolean descending, int size, int priority) {
            this.descending = descending;
            this.size = size;
            this.priority = priority;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Color color = c == null ? Color.GRAY : c.getBackground();

            // In a compound sort, make each succesive triangle 20%
            // smaller than the previous one.
            int dx = (int) (size / 2 * Math.pow(0.8, priority));
            int dy = descending ? dx : -dx;
            // Align icon (roughly) with font baseline.
            y = y + 5 * size / 6 + (descending ? -dy : 0);
            int shift = descending ? 1 : -1;
            g.translate(x, y);

            // Right diagonal.
            g.setColor(color.darker());
            g.drawLine(dx / 2, dy, 0, 0);
            g.drawLine(dx / 2, dy + shift, 0, shift);

            // Left diagonal.
            g.setColor(color.brighter());
            g.drawLine(dx / 2, dy, dx, 0);
            g.drawLine(dx / 2, dy + shift, dx, shift);

            // Horizontal line.
            if (descending) {
                g.setColor(color.darker().darker());
            } else {
                g.setColor(color.brighter().brighter());
            }
            g.drawLine(dx, 0, 0, 0);

            g.setColor(color);
            g.translate(-x, -y);
        }

        public int getIconWidth() {
            return size;
        }

        public int getIconHeight() {
            return size;
        }
    }

    private class SortableHeaderRenderer implements TableCellRenderer,
            Serializable {

        private TableCellRenderer tableCellRenderer;

        public SortableHeaderRenderer(TableCellRenderer tableCellRenderer) {
            this.tableCellRenderer = tableCellRenderer;
        }

        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus, int row,
                                                       int column) {
            Component c = tableCellRenderer.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            if (c instanceof JLabel) {
                JLabel l = (JLabel) c;
                l.setHorizontalTextPosition(JLabel.LEFT);
                int modelColumn = table.convertColumnIndexToModel(column);
                l.setIcon(getHeaderRendererIcon(modelColumn, l.getFont().getSize()));
            }
            return c;
        }
    }

    private static class Directive {

        private int column;
        private int direction;

        public Directive(int column, int direction) {
            this.column = column;
            this.direction = direction;
        }
    }
}
