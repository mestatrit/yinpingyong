package test.tree;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapCustomToString;
import com.mt.common.dynamicDataDef.FieldMapNode;
import com.mt.common.selectionBind.MTFieldMapNodeCheckBoxTree2;

/**
 * 带CheckBox选择功能的树
 */
public class MTFieldMapNodeCheckBoxTree2Tester {

	private JFrame frame;
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
	}

	private void initComponent() {
		frame = new JFrame("带CheckBox选择功能的树样例...");
		checkBoxTree = new MTFieldMapNodeCheckBoxTree2();
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
		
		this.frame.getContentPane().add(checkBoxTree);
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(300, 300);
		this.frame.setLocation(300, 300);
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
