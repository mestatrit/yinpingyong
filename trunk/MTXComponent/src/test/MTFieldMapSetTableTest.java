package test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;

import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapSet;
import com.mt.common.dynamicDataDef.FieldMapUtil;
import com.mt.common.gui.model.DoubleCommaDoc;
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
	
	private String[] title ={
			"ID;ID;MCNumber;",
			"姓名;Name;String;",
			"性别;Sex;Boolean;;;;true;",
			"年龄;Age;MCNumber;3;;test.MTFieldMapSetTableTest$AgeClass;true;"
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
		
		JTextField ageField = new JTextField();
		ageField.setDocument(new DoubleCommaDoc(3, 1, 4, 0));
		mtTable.setDefaultEditor(AgeClass.class, new DefaultCellEditor(ageField));
		JCheckBox sexBox = new JCheckBox();
		//mtTable.setDefaultEditor(Boolean.class, new DefaultCellEditor(sexBox));
		
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
		fm.putIntStringValueField("ID", mtTable.getFieldMapCount()+1);
		fm.putField("Name", "李逵");
		fm.putField("Sex", "Y");
		fm.putIntStringValueField("Age", 10);
		
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
	 */
	class AgeClass{
		
	}
}


