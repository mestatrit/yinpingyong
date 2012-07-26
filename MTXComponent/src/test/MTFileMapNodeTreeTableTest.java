package test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;

import com.mt.common.dynamicDataDef.Field;
import com.mt.common.dynamicDataDef.FieldMapNode;
import com.mt.common.dynamicDataDef.FieldMapUtil;
import com.mt.common.gui.model.DoubleCommaDoc;
import com.mt.common.selectionBind.MTFieldMapNodeTreeTable;

/**
 * MTFieldMapNodeTreeTable 样式样例
 * 
 * @Author: Ryan
 * 
 * 2012-7-26
 */
public class MTFileMapNodeTreeTableTest {
	
	private JFrame frame;
	private MTFieldMapNodeTreeTable mtTable;
	private JPanel searchPanel,resultPanel;
	private JButton cleanButton, addButton, saveButton;
	
	//数据源
	private FieldMapNode commonTree; 
	//表头
	private String[] title ={
			"ID;ID;Number;",
			"姓名;Name;String;",
			"性别;Sex;Boolean;;;;true;",
			"年龄;Age;String;3;;test.MTFileMapNodeTreeTableTest$AgeClass;true;"
	};
	
	public MTFileMapNodeTreeTableTest(){
		PropertyConfigurator.configure("etc/log4j.properties");
		
		initComponent();
		initAction();
		initData();
	}
	
	private void initComponent(){
		frame = new JFrame("MTFieldMapSetTable样例...");
		
		initSearchComponent();
		initResultComponent();
		
		frame.getContentPane().add(searchPanel,BorderLayout.NORTH);
		frame.getContentPane().add(resultPanel,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(400, 500);
		frame.setSize(500, 500);
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
		mtTable = new MTFieldMapNodeTreeTable(this.title){

			private static final long serialVersionUID = 1L;

			/**
			 * 设定单元格是否可以编辑
			 */
			@Override
			protected Boolean isCellEditable(FieldMapNode node, Field field) {
				String name = node.getStringValue("Name");
				System.out.println(name);
				if (!"根节点".equals(name) && !"部门节点".equals(name)) {
					return true;
				}
				return false;
			}
			
		};
		
		//设置单元格的编辑器
		JTextField amount = new JTextField();
        amount.setDocument(new DoubleCommaDoc(12,1,2,0,true,true));
		mtTable.setDefaultEditor(AgeClass.class, new DefaultCellEditor(amount));
		
		//启用表格可编辑总开关
		mtTable.setEditable(true);
		resultPanel.add(mtTable);
		mtTable.setAutoColumnResize(true);
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
	
	private void initData(){
		this.commonTree = new FieldMapNode("TREE");
		this.commonTree.addField("Name", "根节点");
		this.mtTable.setFieldMapNode(this.commonTree);
	}
	
	private void removeData(){
		this.mtTable.clearData();
	}
	
	private void addData(){
		FieldMapNode fmd = new FieldMapNode("TREE");
		fmd.addField("Name","部门节点");
		
		FieldMapNode childFmd = new FieldMapNode("LEAF");
		childFmd.addField("Name", "李逵");
		childFmd.addField("Sex", "TRUE");
		childFmd.putDoubleStringValueField("Age", 20);
		
		fmd.addChildNode(childFmd);
		this.commonTree.addChildNode(fmd);
		this.mtTable.setFieldMapNode(commonTree);
	}
	
	private void saveData(){
		System.out.println(FieldMapUtil.createXMLString(commonTree));
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MTFileMapNodeTreeTableTest();
			}
		});
	}
	
	/**
	 * 标记性接口
	 * @author Ryan
	 *
	 */
	class AgeClass {
		
	}
}
