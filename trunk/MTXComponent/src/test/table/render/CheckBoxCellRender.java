package test.table.render;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.mt.common.gui.ColorLib;

/**
 * CheckBox的渲染器
 * 
 * @Author: Ryan
 * 
 *          2012-8-7
 */
public class CheckBoxCellRender extends JCheckBox implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	private int hAlignment = CENTER;

	public CheckBoxCellRender() {

	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		if (value instanceof Boolean) {
			this.setSelected((Boolean) value);
		} else {
			this.setSelected(value.toString().equals("Y") ? true : false);
		}
		
        if (!isSelected) {
        	if (row % 2 == 0) {
                this.setBackground(ColorLib.TRow_EvenColor);
                this.setForeground(Color.BLACK);
            } else {
            	this.setBackground(ColorLib.TRow_OddColor);
            	this.setForeground(Color.BLACK);
            }
        }
        
        this.setHorizontalAlignment(hAlignment);
        
        return this;
	}

}
