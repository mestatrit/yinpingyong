package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.log4j.PropertyConfigurator;

import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapSet;
import com.mt.common.dynamicDataDef.FieldMapUtil;
import com.mt.common.gui.ColorLib;
import com.mt.common.gui.model.DoubleCommaDoc;
import com.mt.common.gui.table.MCNumber;
import com.mt.common.selectionBind.MTFieldMapSetTable;

/**
 *
 * MTFieldMapSetTable样例
 * 
 * @Author: Ryan
 * 
 * 2012-7-24
 */
public class MTFieldMapSetTableTest {

	private JFrame frame;
	private MTFieldMapSetTable mtTable,mtTable1;
	private JPanel searchPanel,resultPanel;
	private JButton cleanButton, addButton, saveButton;
	
	private String[] title ={
			"ID;ID;MCNumber;;;;false;",
			"姓名;Name;String;;;;true;",
			"性别;Sex;Boolean;;;;true;",
			"年龄;Age;MCNumber;3;;test.MTFieldMapSetTableTest$AgeClass;true;"
		};
	
	private String[] title1 ={
			"ID;ID;MCNumber;;;;false;",
			"姓名;Name;String;;;;true;",
			"性别;Sex;Boolean;;;;true;",
			"年龄1;Age1;MCNumber;3;;test.MTFieldMapSetTableTest$AgeClass;true;"
		};
	
	public MTFieldMapSetTableTest(){
		PropertyConfigurator.configure("etc/log4j.properties");
		
		initComponent();
		initAction();
	}
	
	private void initComponent(){
		frame = new JFrame("MTFieldMapSetTable样例...");
		
		initSearchComponent();
		initResultComponent();
		
		frame.getContentPane().add(searchPanel,BorderLayout.NORTH);
		frame.getContentPane().add(resultPanel,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(400, 500);
		frame.setSize(300, 200);
		frame.setVisible(true);
	}
	
	private void initSearchComponent(){
		searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		cleanButton = new JButton("清除数据");
		addButton = new JButton("新增数据");
		saveButton = new JButton("保存数据");

		searchPanel.add(cleanButton);
		searchPanel.add(addButton);
		searchPanel.add(saveButton);
	}
	
	private void initResultComponent(){
		resultPanel = new JPanel(new BorderLayout());
		mtTable1 = new MTFieldMapSetTable(title1);
		
		mtTable = new MTFieldMapSetTable(title);
		
		/**
		 * 设置列编辑器
		 */
		JTextField ageField = new JTextField();
		ageField.setDocument(new DoubleCommaDoc(3, 1, 4, 0));
		mtTable.setDefaultEditor(AgeClass.class, new DefaultCellEditor(ageField));
		
		mtTable.setDefaultEditor(Boolean.class, new DefaultCellEditor(new JCheckBox()));
		mtTable.setDefaultRenderer(Boolean.class, new DefaultTableCellRenderer(){

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				JLabel cell = (JLabel) super.getTableCellRendererComponent(table,
		                value, isSelected, hasFocus, row, column);
		        if (!isSelected) {
		        	 if (row % 2 == 0) {
		                    cell.setBackground(ColorLib.TRow_EvenColor);
		                    cell.setForeground(Color.BLACK);
		        	 } else {
		                    cell.setBackground(ColorLib.TRow_OddColor);
		                    cell.setForeground(Color.BLACK);
		        	 }
		        }
		        cell.setHorizontalAlignment(SwingConstants.CENTER);
		        return cell;
			}
			
		});
		
		resultPanel.add(new JScrollPane(mtTable),BorderLayout.CENTER);
		resultPanel.add(new JScrollPane(mtTable1),BorderLayout.SOUTH);
	}
	
	private void initAction(){
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
	
	public void removeData(){
		mtTable.clearData();
		mtTable1.clearData();
	}
	
	public void addData(){
		FieldMapSet fms = mtTable.getFieldMapSet();
		
		FieldMap fm = new FieldMap("Result");
		fm.putIntStringValueField("ID", mtTable.getFieldMapCount()+1);
		fm.putField("Name", "李逵");
		fm.putField("Sex", "Y");
		fm.putIntStringValueField("Age", 10);
		fm.putIntStringValueField("Age1", 11);
		fm.putIntStringValueField("Age2", 12);
		
		fms.addFieldMap(fm);
		mtTable.setFieldMapSet(fms);
		mtTable1.setFieldMapSet(fms);
	}
	
	public void saveData(){
		System.out.println(FieldMapUtil.createXMLString(mtTable.getFieldMapSet()));
		System.out.println(FieldMapUtil.createXMLString(mtTable1.getFieldMapSet()));
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MTFieldMapSetTableTest();
			}
		});
	}
	
	/**
	 * 标记性接口类
	 * 继承的目的是为了单元格正常实现对齐
	 */
	class AgeClass extends MCNumber{
		
	}
}


