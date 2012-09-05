package test.tree;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.mt.common.dynamicDataDef.FieldMapNode;
import com.mt.common.selectionBind.MTFieldMapNodeCheckBoxTree2;

/**
 * 带CheckBox选择功能的树
 */
public class MTFieldMapNodeCheckBoxTree2Tester {

	private JFrame frame;
	private MTFieldMapNodeCheckBoxTree2 checkBoxTree;
	
	private static FieldMapNode fmn;
	
	static {
		fmn = new FieldMapNode("RYCS管理系统");
	}
	
	public MTFieldMapNodeCheckBoxTree2Tester() {
		initComponent();
	}
	
	private void initComponent() {
		frame = new JFrame("带CheckBox选择功能的树样例...");
		checkBoxTree = new MTFieldMapNodeCheckBoxTree2();
		this.frame.getContentPane().add(checkBoxTree);
		
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(300, 300);
		this.frame.setLocation(300, 300);
	}
	
	private void loadData() {
		this.checkBoxTree.setFieldMapNode(fmn);
		this.checkBoxTree.expandAllChildren();
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
				
				MTFieldMapNodeCheckBoxTree2Tester tester =new MTFieldMapNodeCheckBoxTree2Tester();
				tester.loadData();
			}
		});
	}

}
