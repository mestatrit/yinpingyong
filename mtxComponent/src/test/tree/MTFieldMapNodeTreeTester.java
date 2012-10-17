package test.tree;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.mt.common.dynamicDataDef.Field;
import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapCustomToString;
import com.mt.common.dynamicDataDef.FieldMapNode;
import com.mt.common.dynamicDataDef.FieldMapUtil;
import com.mt.common.gui.ComponentResizer;
import com.mt.common.selectionBind.MTFieldMapNodeTree;
import com.mt.common.xml.XMLUtil;

/**
 * 支持FieldMapNode数据结构的树
 */
public class MTFieldMapNodeTreeTester {

	private JFrame frame;
	private MTFieldMapNodeTree tree;
	private JSplitPane splitPanel;
	private JPanel contentPanel;
	private JLabel titleLabel;
	
	public MTFieldMapNodeTreeTester() {
		this.initComponent();
		this.initAction();
		this.initData();
	}

	private void initComponent() {
		frame = new JFrame("MTFieldMapNodeTree样例...");
		this.initSearchPanel();
		this.initContentPanel();
		splitPanel = new JSplitPane();
		splitPanel.setLeftComponent(tree);
		splitPanel.setRightComponent(contentPanel);

		frame.getContentPane().add(splitPanel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setLocation(300, 300);
		frame.setVisible(true);
	}

	private void initSearchPanel() {
		tree = new MTFieldMapNodeTree();
		tree.setRootVisible(true);
	}

	private void initContentPanel() {
		contentPanel = new JPanel(new BorderLayout());
		titleLabel = new JLabel();
		contentPanel.add(titleLabel,BorderLayout.NORTH);
	}

	private void initAction() {
		tree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				processMouseRel(e);
			}

		});
	}
	
	/**
	 * 处理树”单击子节点“事件
	 */
	private void processMouseRel(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			Point point = e.getPoint();
			int row = tree.getRowForLocation(point.x, point.y);
			if (row > 0) {
				TreePath path = tree.getPathForRow(row);
				Object lastPathComponent = path.getLastPathComponent();
				if (lastPathComponent instanceof DefaultMutableTreeNode) {
					tree.setSelectionPath(path);
					FieldMapNode node = (FieldMapNode)((DefaultMutableTreeNode)lastPathComponent).getUserObject();
					if (node.getName().equals("node")) {
						titleLabel.setText(node.getStringValue("name"));
					}
				}
			}
		}
	}

	private void initData() {
		try {
			Document doc = XMLUtil.createDocumentFromPath("/FieldMapNode.xml");
			FieldMapNode fmn = FieldMapUtil.createFieldMapNode(doc.getDocumentElement());
			tree.setFieldMapNode(fmn);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tree.setFieldMapCustomToString(new FieldMapCustomToString() {

			@Override
			public String toString(FieldMap fm) {
				Field field = fm.getField("name");
				return field != null ? field.getValue().toString() : "";
			}
		});
		ComponentResizer.expandTree(tree);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				new MTFieldMapNodeTreeTester();
			}
		});
	}

}
