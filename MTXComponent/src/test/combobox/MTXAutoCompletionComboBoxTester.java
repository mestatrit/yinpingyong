package test.combobox;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.mt.common.gui.MTXComponent.MTXAutoCompletionComboBox;
import com.mt.common.selectionBind.NameCodeItem;

/**
 * 支持模糊搜索的下拉框组件样例
 */
public class MTXAutoCompletionComboBoxTester {

	private JFrame frame;
	private JPanel searchPanel;
	private MTXAutoCompletionComboBox box;

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
		this.searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		this.box = new MTXAutoCompletionComboBox(testV);
		this.box.setAutoCSelectedIndex(1);
		
		this.searchPanel.add(new JLabel("搜索下拉框："));
		this.searchPanel.add(box);
		this.searchPanel.add(new JLabel("普通下拉框："));
		this.searchPanel.add(new JComboBox(testV));
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MTXAutoCompletionComboBoxTester();
			}
		});
	}

}
