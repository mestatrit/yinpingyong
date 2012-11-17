package test.miglayout;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author:Ryan
 * @date:2012-11-17
 */
public class MigLayoutTester {

	public MigLayoutTester() {
		this.initComponent();
	}
		
	private void initComponent() {
		this.initContextComponent();
		
		frame = new JFrame("MigLayout 测试...");
		frame.getContentPane().add(contextPanel,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLocation(300, 300);
		frame.setVisible(true);
	}
	
	private void initContextComponent() {
		this.contextPanel = new JPanel();
		contextPanel.add(new JCheckBox("111", true));
	}
	
	private JFrame frame;
	private JPanel contextPanel;
	
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
				new MigLayoutTester();
			}
		});
	}

}
