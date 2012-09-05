package test.panel;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.mt.common.gui.MTXComponent.MTXGUIUtil;

/**
 * 基于 MTXGUIUtil获取SearchPanel
 */
public class SearchPanelTester {

	private JFrame frame;
	private JTextField serachField;
	private JPanel serachPanel;
	
	public SearchPanelTester() {
		initComponent();
	}
	
	private void initComponent() {
		frame = new JFrame("基于 MTXGUIUtil获取SearchPanel...");
		serachField = new JTextField();
		serachPanel = MTXGUIUtil.createSearchTextPanel(serachField, 120, 22);
		this.frame.getContentPane().add(this.serachPanel, BorderLayout.NORTH);
		
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
				
				new SearchPanelTester();
			}
		});
	}

}
