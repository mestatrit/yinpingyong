package com.mt.common.gui.MTXComponent;

import com.mt.common.gui.ComponentResizer;

import java.util.ArrayList;
import java.util.List;

/**
 * MTXTable组合控件
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2010-1-22
 * Time: 18:26:43
 * To change this template use File | Settings | File Templates.
 */
public class MTXTableGroup {

    private List<MTXTable> tables = new ArrayList<MTXTable>();

    public MTXTableGroup addMTXTable(MTXTable table) {
        tables.add(table);
        table.setMTXTableGroup(this);
        return this;
    }

    public MTXTableGroup removeMTXTable(MTXTable table) {
        tables.remove(table);
        table.setMTXTableGroup(null);
        return this;
    }

    void resizeTableGroup() {
        int cCount = tables.get(0).getColumnCount();
        int[] max_size = new int[cCount];
        for (int col = 0; col < cCount; col++) {
            int max = 0;
            for (MTXTable table : tables) {
                if (table.getTableHeader() == null) continue;
                int temp = table.getTableHeader().getColumnModel().getColumn(col).getPreferredWidth();
                max = temp > max ? temp : max;
            }
            max_size[col] = max;
        }
        for (int j = 0; j < cCount; j++) {
            for (MTXTable table : tables) {
                table.getColumnModel().getColumn(j).setPreferredWidth(max_size[j]);
            }
        }

        for (MTXTable table : tables) {
            ComponentResizer.changeTableResizeMode(table);
        }
    }
}