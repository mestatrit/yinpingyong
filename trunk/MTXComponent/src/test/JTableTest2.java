package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

/**
 * 二个Table使用同一个数据集合，
 * 但是不同的Model，从集合中取出不同的列来填充表格
 */
public class JTableTest2 {
	
	private JFrame frame;
	private JPanel pane;
	private JTable table,table1;
	private Table_Model2 model,model1;
	private JScrollPane s_pan;
	private JButton button_1, button_2, button_3;
	
	private String[] title_name  = { "ID", "姓名", "性别", "年龄" };
	
	private String[] title_name1  = { "ID", "姓名", "性别", "中国人" };

	public JTableTest2() {
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
		
		model = new Table_Model2(20,title_name,"0");
		table = new JTable(model);
		table.setBackground(Color.white);
		String[] age = { "16", "17", "18", "19", "20", "21", "22" };
		JComboBox com = new JComboBox(age);
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(3).setCellEditor(new DefaultCellEditor(com));
		tcm.getColumn(0).setPreferredWidth(50);
		tcm.getColumn(1).setPreferredWidth(100);
		tcm.getColumn(2).setPreferredWidth(50);
		
		JCheckBox box = new JCheckBox();
		tcm.getColumn(2).setCellEditor(new DefaultCellEditor(box));
		tcm.getColumn(2).setCellRenderer(new CheckBoxCellRender());
		
		s_pan = new JScrollPane(table);

		model1 = new Table_Model2(20,title_name1,"1");
		table1 = new JTable(model1);
		
		frame.getContentPane().add(s_pan, BorderLayout.CENTER);
		frame.getContentPane().add(pane, BorderLayout.NORTH);
		frame.getContentPane().add(new JScrollPane(table1), BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setVisible(true);
	}

	private void addData() {
		model.addRow("李逵", true, "19",true);
		model1.addRow("李逵", true, "19",true);
		
		table.updateUI();
		table1.updateUI();
	}

	private void removeData() {
		model.removeRows(0, model.getRowCount());
		model1.removeRows(0, model1.getRowCount());
		
		table.updateUI();
		table1.updateUI();
	}

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
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new JTableTest2();
				
				System.out.println("按下保存按钮将会把JTable中的内容显示出来\r\n------------------------------------");
			}
		});
	}
}

class Table_Model2 extends AbstractTableModel {

	private static final long serialVersionUID = -7495940408592595397L;

	private Vector content = null;
	
	private String[] title_name;
	
	private String type;
	
	public Table_Model2(String[] title_name) {
		this.title_name = title_name;
		content = new Vector();
	}

	public Table_Model2(int count,String[] title_name,String type) {
		this.title_name = title_name;
		content = new Vector(count);
		this.type = type;
	}

	public void addRow(String name, boolean sex, String age,boolean isChina) {
		Vector v = new Vector(4);
		v.add(0, new Integer(content.size()));
		v.add(1, name);
		v.add(2, new Boolean(sex));
		v.add(3, age);
		v.add(4, new Boolean(isChina));
		content.add(v);
		
		fireTableDataChanged();
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

	public String getColumnName(int col) {
		return title_name[col];
	}

	public int getColumnCount() {
		return title_name.length;
	}

	public int getRowCount() {
		return content.size();
	}

	/**
	 * 控制model，从数据集合中取出不同的数据填充页面
	 */
	public Object getValueAt(int row, int col) {
		if (col == 3) {
			if (type.equals("0")) {
				return ((Vector) content.get(row)).get(col);
			} else if (type.equals("1")) {
				return ((Vector) content.get(row)).get(col+1);
			}
		}
		
		return ((Vector) content.get(row)).get(col);
	}

	/**
	 * 返回数据类型
	 */
	public Class getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}
}

