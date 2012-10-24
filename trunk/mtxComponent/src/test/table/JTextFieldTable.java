package test.table;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapSet;
import com.mt.common.selectionBind.MTFieldMapSetTable;

/**
 * 重写表格编辑器和表格渲染器
 * 
 * @author:Ryan
 * @date:2012-10-24
 */
public class JTextFieldTable {
	
	private JFrame frame;
	
	private JButton addBtn, dltBtn, queryBtn;
	
	private MTFieldMapSetTable mtTable;
	
	private static MaskFormatter mf;
	
	private static FieldMapSet fms = new FieldMapSet("Page");
	
	static {
		FieldMap fm = new FieldMap("DETAIL");
		fm.putField("INDEX", 1);
		fm.putField("REMIND_TIME", "12:01");
		for (int index = 0; index<10; index++) {
			fms.addFieldMap(fm);
		}
		
		try {
			mf = new MaskFormatter("##:##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		mf.setPlaceholderCharacter('_');
	}
	
	private static  String[] columnDef = {
		"序号;INDEX;Boolean;;;;true;",
		"提醒时间;REMIND_TIME;String;;;;true;"
	};
	
	private JTextFieldTable() {
		this.initComponent();
		this.initAction();
	}
	
	private void initComponent() {
		frame = new JFrame("JFormattedTextField表格...");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);
		
		this.initSearchPanel();
		this.initContextPanel();
	}
	
	private void initSearchPanel() {
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5));
		addBtn = new JButton("新增");
		dltBtn = new JButton("删除");
		queryBtn = new JButton("查询");
		searchPanel.add(addBtn);
		searchPanel.add(dltBtn);
		searchPanel.add(queryBtn);
		frame.getContentPane().add(searchPanel, BorderLayout.NORTH);
	}
	
	private void initContextPanel() {
		mtTable = new MTFieldMapSetTable(columnDef);
		mtTable.setDefaultRenderer(String.class, new TimeTableCellRenderer());
		mtTable.setDefaultEditor(String.class, new TimeTableCellEditor());
		
		mtTable.setFieldMapSet(fms);
		frame.getContentPane().add(new JScrollPane(mtTable), BorderLayout.CENTER);
	}
	
	private void initAction() {
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		dltBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		queryBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
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
				
				new JTextFieldTable();
			}
		});
	}
	
	private void print() {
		
		for (int index=0;index < fms.getFieldMapCount(); index ++) {
			System.out.println(fms.getFieldMap(index).getStringValue("REMIND_TIME"));
			
		}
	}
	
	class TimeTableCellEditor extends AbstractCellEditor implements TableCellEditor {
		
		private JFormattedTextField textField;
		
		private DefaultFormatterFactory defaultFormatterFactory;
		
		public TimeTableCellEditor () {
			initTableCell();
		}
		
		private void initTableCell() {
			textField = new JFormattedTextField();
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(mf);
			textField.setFormatterFactory(defaultFormatterFactory);
			textField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		}
		
		@Override
		public Object getCellEditorValue() {
			return textField.getText();
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			
			if (value != null) {
				textField.setText(value.toString());	
			}
			print();
			return textField;
		}
		
	}
	
	class TimeTableCellRenderer extends JFormattedTextField implements TableCellRenderer{
		
		private DefaultFormatterFactory defaultFormatterFactory;
		
		public TimeTableCellRenderer() {
			initTableCell();
		}
		
		private void initTableCell() {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(mf);
			this.setFormatterFactory(defaultFormatterFactory);
			this.setFocusLostBehavior(JFormattedTextField.PERSIST);
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			
			if (value != null) {
				this.setText(value.toString());	
			} 
			
			return this;
		}
		
	}
}

