package test.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

/**
 * JTable的实用小例子
 * 
 * @author 五斗米 <如转载请保留作者和出处>
 * @blog http://blog.csdn.net/mq612
 */
public class JTableTest {
	
	private JFrame frame = null;
	private JTable table = null;
	private Table_Model model = null;
	private JScrollPane s_pan = null;
	private JButton button_1 = null, button_2 = null, button_3 = null;
	private JPanel pane = null;

	public JTableTest() {
		frame = new JFrame("JTableTest");
		pane = new JPanel();
		button_1 = new JButton("清除数据");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeData();
			}
		});
		button_2 = new JButton("添加数据");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addData();
			}
		});
		button_3 = new JButton("保存数据");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveData();
			}
		});
		pane.add(button_1);
		pane.add(button_2);
		pane.add(button_3);
		model = new Table_Model(20);
		table = new JTable(model);
		table.setBackground(Color.white);
		String[] age = { "16", "17", "18", "19", "20", "21", "22" };
		JComboBox com = new JComboBox(age);
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(3).setCellEditor(new DefaultCellEditor(com));
		tcm.getColumn(0).setPreferredWidth(50);
		tcm.getColumn(1).setPreferredWidth(100);
		tcm.getColumn(2).setPreferredWidth(50);

		s_pan = new JScrollPane(table);

		frame.getContentPane().add(s_pan, BorderLayout.CENTER);
		frame.getContentPane().add(pane, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setVisible(true);
	}

	private void addData() {
		model.addRow("李逵", true, "19");
		table.updateUI();
	}

	private void removeData() {
		model.removeRows(0, model.getRowCount());
		table.updateUI();
	}

	// 保存数据，暂时是将数据从控制台显示出来
	private void saveData() {
		int col = model.getColumnCount();
		int row = model.getRowCount();
		for (int i = 0; i < col; i++) {
			System.out.print(model.getColumnName(i) + "\t");
		}
		System.out.print("\r\n");
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print(model.getValueAt(i, j) + "\t");
			}
			System.out.print("\r\n");
		}
		System.out.println("------------------------------------");
	}

	public static void main(String args[]) {
		new JTableTest();
		System.out.println("按下保存按钮将会把JTable中的内容显示出来\r\n------------------------------------");
	}

}

class Table_Model extends AbstractTableModel {

	private static final long serialVersionUID = -7495940408592595397L;

	private Vector content = null;
	private String[] title_name = { "ID", "姓名", "性别", "年龄" };

	public Table_Model() {
		content = new Vector();
	}

	public Table_Model(int count) {
		content = new Vector(count);
	}

	public void addRow(String name, boolean sex, String age) {
		Vector v = new Vector(4);
		v.add(0, new Integer(content.size()));
		v.add(1, name);
		v.add(2, new Boolean(sex));
		v.add(3, age);
		content.add(v);
	}

	public void removeRow(int row) {
		content.remove(row);
	}

	public void removeRows(int row, int count) {
		for (int i = 0; i < count; i++) {
			if (content.size() > row) {
				content.remove(row);
			}
		}
	}

	/**
	 * 让表格中某些值可修改，但需要setValueAt(Object value, int row, int col)方法配合才能使修改生效
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 使修改的内容生效
	 */
	public void setValueAt(Object value, int row, int col) {
		((Vector) content.get(row)).remove(col);
		((Vector) content.get(row)).add(col, value);
		this.fireTableCellUpdated(row, col);
	}

	public String getColumnName(int col) {
		return title_name[col];
	}

	public int getColumnCount() {
		return title_name.length;
	}

	public int getRowCount() {
		return content.size();
	}

	public Object getValueAt(int row, int col) {
		return ((Vector) content.get(row)).get(col);
	}

	/**
	 * 返回数据类型
	 */
	public Class getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}
}
