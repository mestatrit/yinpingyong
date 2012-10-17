package test.fileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import com.mt.common.gui.MTXComponent.MTXFileChooser;

/**
 * 文件选择组件样例 
 */
public class MTXFileChooserTester {
	
	private JFrame frame;
	private JButton importBtn,exportBtn;
	private JTextField textField;

	public MTXFileChooserTester() {
		this.initComponent();
		this.initAction();
	}
	
	private void initComponent() {
		frame = new JFrame("文件选择组件样例...");
		importBtn = new JButton("导入");
		exportBtn = new JButton("导出");
		textField = new JTextField("请选择文件...");
		
		MigLayout layout = new MigLayout("","[]5[]","[]10[]");
		JPanel contextPanel = new JPanel();
		contextPanel.setLayout(layout);
		contextPanel.add(importBtn);
		contextPanel.add(textField,"wrap");
		contextPanel.add(exportBtn);
		frame.getContentPane().add(contextPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(300, 300);
		frame.setLocation(300, 300);
	}
	
	private void initAction() {
		importBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				importXML();
			}
		});
		
		exportBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				exportXML();
			}
		});
	}
	
	private void importXML(){
		MTXFileChooser chooser = new MTXFileChooser();
    	int result = chooser.showOpenDialog(frame);
    	if(result == MTXFileChooser.APPROVE_OPTION){
    		try {
    			File file = chooser.getSelectedFile();
    			textField.setText(file.getPath());
    		} catch (Exception e) {
				JOptionPane.showMessageDialog(frame, "读取文件失败");
			}
    	}
	}
	
	private void exportXML() {
        MTXFileChooser chooser = new MTXFileChooser();
        int rs = chooser.showSaveDialog(frame);
        if (rs == MTXFileChooser.APPROVE_OPTION) {
        	JOptionPane.showMessageDialog(frame, "导出文件成功！！！");
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
				new MTXFileChooserTester();
			}
		});
	}
	
}
