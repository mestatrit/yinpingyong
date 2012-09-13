package test.tree;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import test.ComponentUtil;

import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapCustomToString;
import com.mt.common.dynamicDataDef.FieldMapNode;
import com.mt.common.gui.MTXComponent.MTXGUIUtil;
import com.mt.common.selectionBind.MTFieldMapNodeCheckBoxTree2;

/**
 * 带CheckBox选择功能的树
 */
public class MTFieldMapNodeCheckBoxTree2Tester {

	private JFrame frame;
	private JButton saveBtn;
	private JPanel contextPanel, searchPanel;
	private JTextField serachField;
	private MTFieldMapNodeCheckBoxTree2 checkBoxTree;
	
	private static FieldMapNode fmn;
	private static final String NODE_NAME = "NODE_NAME";
	
	static {
		fmn = new FieldMapNode("root");
		fmn.addField(NODE_NAME, "根节点");

		List<FieldMapNode> nodeList = new ArrayList<FieldMapNode>();
		for (int index = 0; index < 10; index++) {
			FieldMapNode childNode = new FieldMapNode("根子节点");
			childNode.addField(NODE_NAME, index + "");
			nodeList.add(childNode);
		}
		fmn.addChildNodeList(nodeList);

		FieldMapNode cn = new FieldMapNode("0");
		cn.addField(NODE_NAME, "0");
		fmn.getChildAt(0).addChildNode(cn);
	}

	public MTFieldMapNodeCheckBoxTree2Tester() {
		initComponent();
		initAction();
		initData();
	}

	private void initComponent() {
		frame = new JFrame("带CheckBox选择功能的树样例...");
		initSearchPanel();
		initContextPanel();
		this.frame.getContentPane().add(searchPanel,BorderLayout.NORTH);
		this.frame.getContentPane().add(contextPanel,BorderLayout.CENTER);
		
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(300, 300);
		this.frame.setLocation(300, 300);
	}
	
	private void initSearchPanel() {
		searchPanel = new JPanel();
		serachField = new JTextField();
		searchPanel = MTXGUIUtil.createSearchTextPanel(serachField, 120, 22);
	}
	
	private void initContextPanel() {
		contextPanel = new JPanel(new BorderLayout());
		checkBoxTree = new MTFieldMapNodeCheckBoxTree2();
		saveBtn = new JButton("保存");
		contextPanel.add(checkBoxTree, BorderLayout.CENTER);
		contextPanel.add(saveBtn, BorderLayout.SOUTH);
	}
	
	private void initData() {
		checkBoxTree.setFieldMapNode(fmn);

		/**
		 * 设置FieldMapNode自定义显示
		 */
		checkBoxTree.setFieldMapCustomToString(new FieldMapCustomToString() {

			@Override
			public String toString(FieldMap fm) {
				return fm.getStringValue(NODE_NAME);
			}
		});
		
		/**
		 *  设置选中行
		 */
		List<String> vList = new ArrayList<String>();
		vList.add("1");
		checkBoxTree.setSelNodeValue(vList, NODE_NAME);
	}
	
	private void initAction() {
		saveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnAction();
			}
		});
		
		serachField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				checkBoxTree.setFilterText(serachField.getText());
			}
			
		});
	}
	
	private void btnAction() {
		List<String> name = checkBoxTree.getSelNodeValue(NODE_NAME);
		StringBuilder sb = new StringBuilder();
		for (String value: name) {
			sb.append(value);
		}
		ComponentUtil.showDialog(this.frame, sb.toString());
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					return;
				}

				new MTFieldMapNodeCheckBoxTree2Tester();
			}
		});
	}

}
