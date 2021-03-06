package test.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.PropertyConfigurator;

import test.table.render.CheckBoxCellRender;


import com.mt.common.gui.MTXComponent.MTXTable;

/**
 * MTXTable 样例
 * 
 * @Author: Ryan
 * 
 *          2012-7-18
 */
public class MTXTableTest {

	private JFrame frame;
	private MTXTable table;
	private TableModel model;
	private JPanel searchPanel;
	private JPanel resultPanel;
	private JButton cleanButton, addButton, saveButton;

	public MTXTableTest() {
		PropertyConfigurator.configure("etc/log4j.properties");
		
		initComponent();
		initAction();
		
		table.setCopyEnable(true);
		table.setExportEnable(true);
	}

	private void initComponent() {
		frame = new JFrame("MTXPanel样例...");
		initSearchComponent();
		initResultComponent();

		frame.getContentPane().add(resultPanel, BorderLayout.CENTER);
		frame.getContentPane().add(searchPanel, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(400, 500);
		frame.setSize(300, 200);
		frame.setVisible(true);
	}

	private void initSearchComponent() {
		searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		cleanButton = new JButton("清除数据");
		addButton = new JButton("新增数据");
		saveButton = new JButton("保存数据");

		searchPanel.add(cleanButton);
		searchPanel.add(addButton);
		searchPanel.add(saveButton);
	}

	private void initResultComponent() {
		resultPanel = new JPanel(new BorderLayout());
		model = new TableModel();
		table = new MTXTable(model);
		table.setBackground(Color.white);
		
		//设置单元格编辑器
		JComboBox com = new JComboBox(new String[] { "16", "17", "18", "19","20", "21", "22" });
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(3).setCellEditor(new DefaultCellEditor(com));
		tcm.getColumn(1).setCellRenderer(new CheckBoxCellRender());
		
		resultPanel.add(new JScrollPane(table), BorderLayout.CENTER);
	}

	private void initAction() {
		cleanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeData();
			}
		});

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addData();
			}
		});

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveData();
			}
		});
	}

	private void addData() {
		model.getContent().add(new User(model.getContent().size() + 1, "李逵", true, "19"));
		//通知所有侦听器，表的所有行单元格值可能已更改
		model.fireTableDataChanged();
	}

	private void removeData() {
		model.getContent().clear();
		model.fireTableDataChanged();
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
				new MTXTableTest();
			}
		});
	}
}

class TableModel extends AbstractTableModel {

	private static final long serialVersionUID = -7495940408592595397L;

	private List<User> content = new ArrayList<User>();

	private String[] titleName = { "ID", "姓名", "性别", "年龄" };

	public List<User> getContent() {
		return content;
	}

	public TableModel() {
		
	}
	
	/**
	 * 设置单元格是否可修改
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
		User user = content.get(row);
		switch (col) {
			case 0:
				user.setId((Integer) value);
				break;
			case 1:
				user.setName((String) value);
				break;
			case 2:
				user.setSex((Boolean) value);
				break;
			case 3:
				user.setAge((String) value);
				break;
			default:
				break;
		}

		this.fireTableCellUpdated(row, col);
	}

	/**
	 * 显示表格列名
	 */
	public String getColumnName(int col) {
		return titleName[col];
	}

	public int getColumnCount() {
		return titleName.length;
	}

	public int getRowCount() {
		return content.size();
	}

	/**
	 * 获取单元格值
	 */
	public Object getValueAt(int row, int col) {
		User user = content.get(row);
		switch (col) {
		case 0:
			return user.getId();
		case 1:
			return user.getName();
		case 2:
			return user.getSex();
		case 3:
			return user.getAge();
		default:
			return "";
		}
	}

	/**
	 * 返回数据类型
	 * 提供渲染作用
	 */
	public Class<?> getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}
}

class User {
	private Integer id;

	private String name;

	private Boolean sex;

	private String age;

	public User() {

	}

	public User(Integer id, String name, Boolean sex, String age) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
}
