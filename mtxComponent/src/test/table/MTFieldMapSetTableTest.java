package test.table;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;

import test.table.render.CheckBoxCellRender;

import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapSet;
import com.mt.common.dynamicDataDef.FieldMapUtil;
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
	private MTFieldMapSetTable mtTable;
	private JPanel searchPanel,resultPanel;
	private JButton cleanButton, addButton, saveButton;
	
	/**
	 * 2:表示保留二位有效数据
	 */
	private String[] title ={
			"ID;Id;Number;2;;;false;",
			"姓名;Name;String;;;;true;",
			"性别;Sex;Boolean;;;;true;",
			"年龄;Age;Number;2;;test.table.MTFieldMapSetTableTest$AgeClass;true;"
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
		mtTable = new MTFieldMapSetTable(title);
		
		/**
		 * 设置列编辑器
		 */
		JTextField ageField = new JTextField();
		ageField.setDocument(new DoubleCommaDoc(3, 1, 4, 0));
		mtTable.setDefaultEditor(AgeClass.class, new DefaultCellEditor(ageField));
		
		/**
		 * 设置CheckBox的渲染；
		 * 默认CheckBox不做渲染，使其有别于其他的单元格
		 */
		mtTable.setDefaultRenderer(Boolean.class, new CheckBoxCellRender());
		
		mtTable.addPopupMenuSeparatorFirst();
		JMenuItem item = new JMenuItem("新增");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		mtTable.addPopupMenuItemFirst(item);
		mtTable.setPopupMenuVisible(true);
		
		resultPanel.add(new JScrollPane(mtTable),BorderLayout.CENTER);
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
	}
	
	public void addData(){
		FieldMapSet fms = mtTable.getFieldMapSet();
		FieldMap fm = new FieldMap("Result");
		fm.putDoubleStringValueField("Id", 1.152);
		fm.putField("Name", "李逵");
		fm.putField("Sex", "Y");
		fm.putDoubleStringValueField("Age", 100.1111);
		fms.addFieldMap(fm);
		
		mtTable.setFieldMapSet(fms);
	}
	
	public void saveData(){
		System.out.println(FieldMapUtil.createXMLString(mtTable.getFieldMapSet()));
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
	 * 继承的目的是为了单元格对齐(字符串：左对齐；数据：右对齐)
	 */
	class AgeClass extends MCNumber{
		
	}
}


