package test.tree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.dnd.Autoscroll;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.mt.common.gui.AutoscrollSupport;
import com.mt.common.gui.ComponentResizer;
import com.mt.common.gui.MTXComponent.MTXFileChooser;
import com.mt.util.W3CDomUtil;

/**
 * JTree使用样例 1、实现节点拖拽功能 2、节点添加右键事件 3、节点添加ToolTip显示
 */
public class JTreeTester {

	private JFrame frame;
	private JPanel contentPanel, searchPanel;
	private JButton exportBtn;

	private ElementTree dataTree;
	private Document dataDoc;

	public JTreeTester() {
		this.initComponent();
		this.initAction();
	}

	private void initComponent() {
		frame = new JFrame("JTree使用样例...");
		this.initSearchPanel();
		this.initContentPanel();
		frame.getContentPane().add(searchPanel, BorderLayout.NORTH);
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setLocation(300, 300);
		frame.setVisible(true);
	}

	private void initSearchPanel() {
		searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		exportBtn = new JButton("导出");
		searchPanel.add(exportBtn);
	}

	private void initContentPanel() {
		contentPanel = new JPanel(new BorderLayout());
		initTree();
		contentPanel.add(new JScrollPane(dataTree));
	}

	private void initAction() {
		exportBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exportXML();
			}
		});
	}

	private void initTree() {
		dataDoc = W3CDomUtil.buildDocument(simulateData());
		Element dRoot = dataDoc.getDocumentElement();
		dataTree = new ElementTree(getTreeNodeFromXML(dRoot, "OPItem"));

		dataTree.setRowHeight(20);
		dataTree.setCellEditor(new ElementTreeEditor());
		dataTree.setEditable(true);
		dataTree.setCellRenderer(new ElementTreeRenderer());

		ComponentResizer.expandTree(dataTree);
	}

	/**
	 * 使用递归，将Dom树转成JTree所需的DataModel
	 * 
	 * @param root
	 * @param leafTag
	 * @return
	 */
	private DefaultMutableTreeNode getTreeNodeFromXML(Element root,
			String leafTag) {
		DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(root);

		if (root.getTagName().equals(leafTag)) {
			return treeNode;
		}

		int childNum = root.getChildNodes().getLength();
		for (int i = 0; i < childNum; i++) {
			Node node = root.getChildNodes().item(i);
			if (node instanceof Element) {
				treeNode.add(getTreeNodeFromXML((Element) node, leafTag));
			}
		}
		return treeNode;
	}

	private String simulateData() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
		stringBuilder.append("<FuncGroup icon=\"\" id=\"FT01\" name=\"功能定义\">");
		stringBuilder.append("<FuncGroup icon=\"/new/home.png\" id=\"FT02\" name=\"系统功能\">");
		stringBuilder.append("<Function class=\"\" hotkey=\"\" icon=\"\" id=\"FT0223\" name=\"从当前布局生成新视角(Ctrl+N)\"/>");
		stringBuilder.append("<Function class=\"\" hotkey=\"\" icon=\"/16/config.png\" id=\"FT0215\" name=\"全局参数设置\"/>");
		stringBuilder.append("</FuncGroup>");
		stringBuilder.append("</FuncGroup>");
		return stringBuilder.toString();
	}

	private void exportXML() {
		MTXFileChooser chooser = new MTXFileChooser();
		int rs = chooser.showSaveDialog(frame);
		if (rs == MTXFileChooser.APPROVE_OPTION) {
			exportXMLFromDoc(dataDoc, chooser.getSelectedFile());
		}
	}

	private void exportXMLFromDoc(Document doc, File file) {
		TransformerFactory tFactory = TransformerFactory.newInstance("com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl",null);
		try {
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			System.err.println(e.getStackTrace());
		} catch (TransformerException e) {
			System.err.println(e.getStackTrace());
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
				}
				new JTreeTester();
			}
		});
	}

	/**
	 * 支持ToolTips和边框自动调整的树
	 */
	class ElementTree extends JTree implements Autoscroll {

		private AutoscrollSupport scrollSupport;

		public ElementTree(TreeNode root) {
			super(root);
			this.setToolTipText("");
			scrollSupport = new AutoscrollSupport(this);
		}

		public String getToolTipText(MouseEvent event) {
			Point p = event.getPoint();
			int selRow = getRowForLocation(p.x, p.y);
			if (selRow < 0) {
				return null;
			}
			TreePath path = getPathForRow(selRow);
			Object lastPath = path.getLastPathComponent();
			if (lastPath instanceof DefaultMutableTreeNode) {
				Element v = (Element) ((DefaultMutableTreeNode) lastPath)
						.getUserObject();
				String html = "<html><table border='0' cellspacing='2' cellpadding='0'>"
						+ "<tr><td>名称:  </td><td>"
						+ v.getAttribute("name")
						+ "</td></tr>"
						+ "<tr><td>ID:  </td><td>"
						+ v.getAttribute("id") + "</td></tr>";
				return html;
			}

			return null;
		}

		public void autoscroll(Point p) {
			scrollSupport.autoscroll(p);
		}

		public Insets getAutoscrollInsets() {
			return scrollSupport.getAutoscrollInsets();
		}
	}

	/**
	 * JTree自定义渲染器
	 */
	class ElementTreeRenderer extends DefaultTreeCellRenderer {

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			JLabel label = (JLabel) super.getTreeCellRendererComponent(tree,
					value, sel, expanded, leaf, row, hasFocus);
			if (value instanceof DefaultMutableTreeNode) {
				Element v = (Element) ((DefaultMutableTreeNode) value)
						.getUserObject();
				String name = v.getAttribute("name");
				label.setText(name);
			}

			return label;
		}
	}

	/**
	 * JTree自定义编辑器
	 */
	class ElementTreeEditor extends AbstractCellEditor implements
			TreeCellEditor {

		private JTextField text = new JTextField();
		private JPanel panel = new JPanel(new BorderLayout());
		private JLabel label = new JLabel();
		private DefaultMutableTreeNode value;

		public ElementTreeEditor() {
			text.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					stopCellEditing();

				}
			});
			panel.setOpaque(false);
			panel.add(text, BorderLayout.CENTER);
			panel.add(label, BorderLayout.WEST);
		}

		public Object getCellEditorValue() {
			if (!text.getText().trim().equals("")) {
				Element e = (Element) this.value.getUserObject();
				e.setAttribute("name", text.getText());
			}
			return this.value.getUserObject();
		}

		public Component getTreeCellEditorComponent(JTree tree, Object value,
				boolean isSelected, boolean expanded, boolean leaf, int row) {
			this.value = (DefaultMutableTreeNode) value;
			Element e = (Element) this.value.getUserObject();
			String name = e.getAttribute("name");
			text.setText(name);
			if (leaf) {
				label.setIcon(UIManager.getIcon("Tree.leafIcon"));
			} else if (expanded) {
				label.setIcon(UIManager.getIcon("Tree.openIcon"));
			} else {
				label.setIcon(UIManager.getIcon("Tree.closedIcon"));
			}
			return panel;
		}

		public boolean isCellEditable(EventObject anEvent) {
			if (anEvent instanceof MouseEvent) {
				return ((MouseEvent) anEvent).getClickCount() >= 2;
			}
			return true;
		}
	}

}
