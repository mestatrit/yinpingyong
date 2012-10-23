package test.combobox;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.mt.common.gui.MTXComponent.MTXAutoCompletionComboBox;
import com.mt.common.gui.MTXComponent.MTXAutoCompletionComboPanel;
import com.mt.common.gui.MTXComponent.MTXAutoCompletionDoubleComboPanel;
import com.mt.common.selectionBind.NameCodeItem;

/**
 * 支持模糊搜索的下拉框组件样例
 */
public class MTXAutoCompletionComboBoxTester {

	private JFrame frame;
	private JPanel searchPanel;
	private JComboBox pBox;
	private MTXAutoCompletionComboBox box;
	private MTXAutoCompletionComboPanel boxPanel;
	private MTXAutoCompletionDoubleComboPanel doubleBoxPanel;
	
	private JButton btn1,btn2,btn3,btn4;
	
	private Object[] testV = { 
			new NameCodeItem("华为23434", "dfdsf1"),
			new NameCodeItem("华为23434", "dfdsf2"),
			new NameCodeItem("招商银行金葵花服务项目（XXXXXXXX）", "dfdsf3"),
			new NameCodeItem("ewrewrewr", "dfdsf4"),
			new NameCodeItem("华为2sfdsfdsf3434", "dfdsf5"),
			new NameCodeItem("华为23434", "dfdsf6"),
			new NameCodeItem("sdfdsfdsf34", "dfdsf7"),
			new NameCodeItem("华sdsfdsf23434", "dfdsf8"),
			new NameCodeItem("华dsfdsf3434", "dfdsf9"),
			new NameCodeItem("sdfdsfdsfdsf34", "dfdsf10") 
	};

	public MTXAutoCompletionComboBoxTester() {
		this.initComponent();
		this.initAction();
	}

	private void initComponent() {
		this.initSearchPanel();
		this.frame = new JFrame("支持模糊搜索的下拉框组件样例...");
		this.frame.getContentPane().add(this.searchPanel, BorderLayout.CENTER);
		
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(300, 300);
		this.frame.setLocation(300, 300);
	}

	private void initSearchPanel() {
		this.pBox = new JComboBox(testV);
		this.pBox.setSelectedIndex(0);
		
		this.box = new MTXAutoCompletionComboBox(testV);
		this.box.setAutoCSelectedIndex(1);
		
		this.boxPanel = new MTXAutoCompletionComboPanel(testV);
		this.boxPanel.setAutoCSelectedIndex(1);
		
		String[] dlist = {"华为", "招商"};
		this.doubleBoxPanel = new MTXAutoCompletionDoubleComboPanel(dlist, testV);
		this.doubleBoxPanel.setAutoCSelectedIndex(1);
		
		this.searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		this.searchPanel.add(new JLabel("普通下拉框："));
		this.searchPanel.add(pBox);
		this.searchPanel.add(new JLabel("搜索下拉框："));
		this.searchPanel.add(box);
		this.searchPanel.add(new JLabel("搜索下拉框面板："));
		this.searchPanel.add(boxPanel);
		this.searchPanel.add(new JLabel("搜索下拉框双组合面板："));
		this.searchPanel.add(doubleBoxPanel);
		
		this.btn1 = new JButton("普通");
		this.btn2 = new JButton("搜索");
		this.btn3 = new JButton("搜面板");
		this.btn4 = new JButton("双搜面板");
		this.searchPanel.add(btn1);
		this.searchPanel.add(btn2);
		this.searchPanel.add(btn3);
		this.searchPanel.add(btn4);
	}
	
	private void initAction() {
		this.btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				NameCodeItem item = (NameCodeItem)pBox.getSelectedItem();
				JOptionPane.showConfirmDialog(frame, item.getName());
			}
		});
		
		this.btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				/**
				 * 获取选中项
				 */
				Object obj = box.getAutoCSelectedItem();
				if (obj != null) {
					JOptionPane.showConfirmDialog(frame, ((NameCodeItem)obj).getName());	
				}
				
				/**
				 * 获取当前输入框值
				 */
				JOptionPane.showConfirmDialog(frame, box.getEditData());	
			}
		});
		
		this.btn3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boxPanel.getListItem();
				boxPanel.getCurFListItem();
			}
		});
		
		this.btn4.addActionListener(new ActionListener() {
			
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
				} catch (Exception e) {
					return;
				}
				
				new MTXAutoCompletionComboBoxTester();
			}
		});
	}

}
