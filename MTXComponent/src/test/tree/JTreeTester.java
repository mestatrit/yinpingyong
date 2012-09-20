package test.tree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.Autoscroll;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
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
 * JTree使用样例 
 * 1、实现节点拖拽功能 
 * 2、节点添加右键事件 
 * 3、节点添加ToolTip显示
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

		/**
		 * 设置树的数据模型
		 */
		dataDoc = W3CDomUtil.buildDocument(simulateData());
		Element dRoot = dataDoc.getDocumentElement();
		dataTree = new ElementTree(getTreeNodeFromXML(dRoot, "OPItem"));

		/**
		 * 实现节点的拖拽
		 */
		new ElementDropTree(dataTree);
		new ElementDragSourceTree(dataTree);

		/**
		 * 设置树的编辑器和渲染器
		 */
		dataTree.setRowHeight(20);
		dataTree.setCellEditor(new ElementTreeEditor());
		dataTree.setEditable(true);
		dataTree.setCellRenderer(new ElementTreeRenderer());
		
		/**
		 * 设置树的鼠标事件
		 */
		dataTree.addMouseListener(new ElementTreeMouseListener());
		
		ComponentResizer.expandTree(dataTree);
	}

	/**
	 * 使用递归，将Dom树转成JTree所需的DataModel
	 * 
	 * @param root
	 * @param leafTag
	 * @return
	 */
	private DefaultMutableTreeNode getTreeNodeFromXML(Element root, String leafTag) {
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
				Element v = (Element) ((DefaultMutableTreeNode) lastPath).getUserObject();
				String html = "<html><table border='0' cellspacing='2' cellpadding='0'>"
								+ "<tr><td>名称:  </td><td>"
								+ v.getAttribute("name")
								+ "</td></tr>"
								+ "<tr><td>ID:  </td><td>"
								+ v.getAttribute("id") + "</td></tr></table></html>";
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

		public Component getTreeCellRendererComponent(JTree tree, Object value,boolean sel, boolean expanded, boolean leaf, int row,boolean hasFocus) {
			JLabel label = (JLabel) super.getTreeCellRendererComponent(tree,value, sel, expanded, leaf, row, hasFocus);
			if (value instanceof DefaultMutableTreeNode) {
				Element v = (Element) ((DefaultMutableTreeNode) value).getUserObject();
				/**
				 * 从树形节点中，选取name属性，加载节点
				 */
				String name = v.getAttribute("name");
				label.setText(name);
			}
			
			return label;
		}
	}

	/**
	 * JTree自定义编辑器
	 * 基于JTextField来编辑树的单元格
	 */
	class ElementTreeEditor extends AbstractCellEditor implements TreeCellEditor {

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

		public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
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

	private DefaultMutableTreeNode dropNode;

	private DefaultMutableTreeNode oldnode;

	/**
	 * 提供节点拖拽时的可视化
	 */
	class ElementDropTree implements DropTargetListener {

		private JTree tree;

		private DropTarget dt;

		public ElementDropTree(JTree tree) {
			this.tree = tree;
			this.dt = new DropTarget(tree, this);
		}

		public void dragEnter(DropTargetDragEvent dtde) 
		{
			
		}

		public void dragExit(DropTargetEvent dte) 
		{
			
		}

		public void dragOver(DropTargetDragEvent dtde) 
		{
			
		}

		public void drop(DropTargetDropEvent dtde) {
			Point point = dtde.getLocation();
			TreePath parentpath = tree.getClosestPathForLocation(point.x,point.y);
			DefaultMutableTreeNode pNode = (DefaultMutableTreeNode) parentpath.getLastPathComponent();

			if (pNode == oldnode) {
				dtde.rejectDrop();// 不接受
				return;
			}
			dropNode = pNode;

			Transferable tb = dtde.getTransferable();
			DataFlavor[] df = tb.getTransferDataFlavors();
			for (int i = 0; i < df.length; i++) {
				if (tb.isDataFlavorSupported(df[i])) {
					dtde.acceptDrop(DnDConstants.ACTION_MOVE);
					dtde.dropComplete(true);
					return;
				}
			}
			dtde.rejectDrop();// 不接受
		}

		public void dropActionChanged(DropTargetDragEvent dtde) 
		{
			
		}
	}

	/**
	 * 实现节点拖拽时的具体实现
	 */
	class ElementDragSourceTree implements DragSourceListener, DragGestureListener {

		private JTree tree;

		private DragSource ds;

		public ElementDragSourceTree(JTree tree) {
			this.tree = tree;
			ds = new DragSource();
			ds.createDefaultDragGestureRecognizer(this.tree, DnDConstants.ACTION_MOVE, this);
		}

		public void dragDropEnd(DragSourceDropEvent dsde) {
			if (dsde.getDropSuccess() && dropNode != null) {
				if (dropNode.isLeaf()) {
					// 删除
					Element oldp = (Element) ((DefaultMutableTreeNode) oldnode.getParent()).getUserObject();
					Element olde = (Element) oldnode.getUserObject();
					oldp.removeChild(olde);
					((DefaultTreeModel) tree.getModel()).removeNodeFromParent(oldnode);

					// 新增
					DefaultMutableTreeNode dropParent = (DefaultMutableTreeNode) dropNode.getParent();
					int index = dropParent.getIndex(dropNode);
					((DefaultTreeModel) tree.getModel()).insertNodeInto(oldnode, dropParent, index);
					Element pe = (Element) dropParent.getUserObject();
					pe.insertBefore((Element) oldnode.getUserObject(),(Element) dropNode.getUserObject());
				} else {
					// 删除
					Element oldp = (Element) ((DefaultMutableTreeNode) oldnode.getParent()).getUserObject();
					Element olde = (Element) oldnode.getUserObject();
					oldp.removeChild(olde);
					((DefaultTreeModel) tree.getModel()).removeNodeFromParent(oldnode);

					// 新增
					((DefaultTreeModel) tree.getModel()).insertNodeInto(oldnode, dropNode, 0);
					Element pe = (Element) dropNode.getUserObject();
					pe.insertBefore((Element) oldnode.getUserObject(),pe.getFirstChild());
				}
			}

		}

		public void dragEnter(DragSourceDragEvent dsde) {
		}

		public void dragExit(DragSourceEvent dse) {
		}

		public void dragOver(DragSourceDragEvent dsde) {
		}

		public void dropActionChanged(DragSourceDragEvent dsde) {
		}

		public void dragGestureRecognized(DragGestureEvent dge) {
			TreePath path = tree.getSelectionPath();
			if (path == null || path.getPathCount() < 1) {
				return;
			}
			oldnode = (DefaultMutableTreeNode) path.getLastPathComponent();

			ds.startDrag(dge, DragSource.DefaultMoveDrop,new TransferableTreeNode(path), this);
		}
	}

	static public class TransferableTreeNode implements Transferable {

		public static final DataFlavor TREE_PATH_FLAVOR = new DataFlavor(TreePath.class, "Tree Path");
		private DataFlavor flavors[] = { TREE_PATH_FLAVOR };
		private TreePath path;

		public TransferableTreeNode(TreePath tp) {
			path = tp;
		}

		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			if (isDataFlavorSupported(flavor)) {
				return (Object) path;
			} else {
				throw new UnsupportedFlavorException(flavor);
			}
		}

		public DataFlavor[] getTransferDataFlavors() {
			return flavors;
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return (flavor.getRepresentationClass() == TreePath.class);
		}
	}

	/**
	 * 节点添加鼠标事件
	 */
	class ElementTreeMouseListener extends MouseAdapter {

		private ElementActionListener eListener = new ElementActionListener();

		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				ElementTree tree = dataTree;

				Point p = e.getPoint();
				int selRow = tree.getRowForLocation(p.x, p.y);
				if (selRow < 0) {
					return;
				}

				TreePath path = tree.getPathForRow(selRow);
				Object lastPath = path.getLastPathComponent();
				if (lastPath instanceof DefaultMutableTreeNode) {
					tree.setSelectionPath(path);
					Element v = (Element) ((DefaultMutableTreeNode) lastPath).getUserObject();
					JPopupMenu popup = new JPopupMenu();
					JMenuItem[] items = { new JMenuItem("新增"),new JMenuItem("修改"),new JMenuItem("删除") };
					String[] cmds = { "addDI", "updateOP", "delOP" };
					for (int i = 0; i < items.length; i++) {
						items[i].setActionCommand(cmds[i]);
						items[i].addActionListener(eListener);
						popup.add(items[i]);
					}

					popup.show(tree, p.x, p.y);
				}
			}
		}
	}

	class ElementActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String menuText = ((JMenuItem) e.getSource()).getText();
			String cmd = e.getActionCommand();
			ElementTree tree = dataTree;
			TreePath tp = tree.getSelectionPath();
			if (tp == null) {
				return;
			}

			DefaultMutableTreeNode tNode = (DefaultMutableTreeNode) tp.getLastPathComponent();
			if (cmd.equals("addF") || cmd.equals("addFG")|| cmd.equals("updateF") || cmd.equals("updateFG")) {
				showFuncDialog(tNode, cmd, menuText);
			} else if (cmd.contains("del")) {
				removeTreeNode(tNode, tree);
			} else if (cmd.equals("updateDI") || cmd.equals("updateOP")) {
				dataTree.startEditingAtPath(tp);
			} else if (cmd.equals("addDI") || cmd.equals("addOP")) {
				String tag = cmd.equals("addDI") ? "DataItem" : "OPItem";
				Element temp = dataDoc.createElement(tag);
				temp.setAttribute("id", "");
				temp.setAttribute("name", "DefaultName");
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(temp);
				addTreeNode(node, tNode, tree);
				Object[] pathObject = tp.getPath();
				Object[] nPathObject = new Object[pathObject.length + 1];
				System.arraycopy(pathObject, 0, nPathObject, 0,pathObject.length);
				nPathObject[pathObject.length] = node;
				dataTree.startEditingAtPath(new TreePath(nPathObject));
			}
		}

		private void showFuncDialog(DefaultMutableTreeNode node, String type,
				String menuText) {
			/*
			 * FuncEditorDialog dialog = (FuncEditorDialog)
			 * FunctionAndDataManager
			 * .this.launchFunction(FuncEditorDialog.class);
			 * dialog.init(funcTree,funcDoc);
			 * dialog.setDefaultMutableTreeNode(node, type);
			 */
		}
	}

	private void removeTreeNode(DefaultMutableTreeNode node, ElementTree tree) {
		if (node.getParent() != null) {
			String name = ((Element) node.getUserObject()).getAttribute("name");
			int rs = JOptionPane.showConfirmDialog(frame, "确认要删除\"" + name+ "\"吗", "删除确认", JOptionPane.OK_CANCEL_OPTION);
			if (rs == JOptionPane.OK_OPTION) {
				Element pe = (Element) ((DefaultMutableTreeNode) node.getParent()).getUserObject();
				((DefaultTreeModel) tree.getModel()).removeNodeFromParent(node);

				// 同步移除Doc上的节点
				pe.removeChild((Element) node.getUserObject());
			}

		}
	}
	
	private static void addTreeNode(DefaultMutableTreeNode node,DefaultMutableTreeNode parent, ElementTree tree) {
        ((DefaultTreeModel) tree.getModel()).insertNodeInto(node, parent, 0);

        //同步加上Doc上的节点
        Element pe = (Element) parent.getUserObject();
        pe.insertBefore((Element) node.getUserObject(), pe.getFirstChild());
        ComponentResizer.expandTree(tree);
    }
}
