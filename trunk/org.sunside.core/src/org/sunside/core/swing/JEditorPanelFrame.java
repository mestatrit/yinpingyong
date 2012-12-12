package org.sunside.core.swing;

import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * @author:Ryan
 * @date:2012-12-13
 */
public class JEditorPanelFrame {

	private JFrame frame;
	
	private JEditorPane jep ;
	
	public JEditorPanelFrame(){
		initComponent();
	}
	
	private void initComponent() {
		jep = new JEditorPane();
		jep.setEditable(true);
		try {
			jep.setPage("http://www.baidu.com");
		} catch (IOException e) {
			jep.setContentType("text/html");
			jep.setText("<heml>无法打开：http://www.baidu.com</html>");
		}
		
		frame = new JFrame("www.baidu.com");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new JScrollPane(jep));
		frame.setSize(800, 500);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new JEditorPanelFrame();
			}
		});
	}

}
