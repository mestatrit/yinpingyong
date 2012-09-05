package test.combobox;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.mt.common.gui.MTXComponent.MTXAutoCompletionCellEditor;
import com.mt.common.gui.MTXComponent.MTXAutoCompletionCellRenderer;
import com.mt.common.gui.MTXComponent.MTXTable;
import com.mt.common.selectionBind.NameCodeItem;

public class ComboboxTable {

	private JFrame frame;
	private JTable mtTable;

	private Object[] testV = { new NameCodeItem("华为23434", "dfdsf1"),
			new NameCodeItem("华为23434", "dfdsf2"),
			new NameCodeItem("招商银行金葵花服务项目（XXXXXXXX）", "dfdsf3"),
			new NameCodeItem("ewrewrewr", "dfdsf4"),
			new NameCodeItem("华为2sfdsfdsf3434", "dfdsf5"),
			new NameCodeItem("华为23434", "dfdsf6"),
			new NameCodeItem("sdfdsfdsf34", "dfdsf7"),
			new NameCodeItem("华sdsfdsf23434", "dfdsf8"),
			new NameCodeItem("华dsfdsf3434", "dfdsf9"),
			new NameCodeItem("sdfdsfdsfdsf34", "dfdsf10") };

	public ComboboxTable() {
		this.initComponent();
		this.initAcion();
	}

	private void initComponent() {
		this.frame = new JFrame("带模糊搜索的下拉框表格样例...");
		this.mtTable = new MTXTable();
		
		/**
		 * 设置表格的数据模型
		 */
		this.mtTable.setModel(new DefaultTableModel(5, 6));
		
		/**
		 * 设置表格的编辑器
		 */
		MTXAutoCompletionCellEditor editor = new MTXAutoCompletionCellEditor(testV);
		editor.setAutoWide(true);
		mtTable.setDefaultEditor(Object.class, editor);
		
		/**
		 * 设置表格的渲染
		 */
		mtTable.setDefaultRenderer(Object.class, new MTXAutoCompletionCellRenderer(testV));
		
		this.frame.getContentPane().add(mtTable, BorderLayout.CENTER);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(500, 500);
		this.frame.setLocation(300, 300);
		this.frame.setVisible(true);
	}

	private void initAcion() {

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new ComboboxTable();
			}
		});
	}

}
