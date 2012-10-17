package test.hyperLink;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXHyperlink;

/**
 * 一个超链接组件的使用
 */
public class HyperLinkTester {

	private JFrame frame;
	private JXHyperlink testLink; 
	
	public HyperLinkTester() {
		this.initComponent();
		this.initAction();
	}
	
	private void initComponent() {
		this.frame = new JFrame("一个超连接组件的使用样例...");
		this.testLink = new JXHyperlink();
		this.testLink.setText("click me");
		this.testLink.setToolTipText("I'm hyper link...");
		this.frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		this.frame.getContentPane().add(this.testLink);
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocation(300,300);
		this.frame.setSize(300, 300);
		this.frame.setVisible(true);
	}
	
	private void initAction() {
		this.testLink.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(frame, "clicked me!!!");
			}
		});
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new HyperLinkTester();
			}
		});
	}

}
