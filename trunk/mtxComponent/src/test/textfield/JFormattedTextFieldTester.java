package test.textfield;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 * @author:Ryan
 * @date:2012-10-24
 */
public class JFormattedTextFieldTester {

	private JFrame frame;
	
	private JFormattedTextField dealTimeField;
	
	private JButton btn;
	
	private JTextField field;
	
	public JFormattedTextFieldTester() {
		initComponent();
		initAction();
	}
	
	private void initComponent() {
		btn = new JButton("Click");
		field = new JTextField(30);
		
		MaskFormatter mf = null;
        try {
            mf = new MaskFormatter("##:##");
            mf.setPlaceholderCharacter('_');
        } catch (ParseException e) {
        }
		dealTimeField = new JFormattedTextField();
		DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory();
		defaultFormatterFactory.setDefaultFormatter(mf);
		dealTimeField.setFormatterFactory(defaultFormatterFactory);
		dealTimeField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		dealTimeField.setSize(30, 10);
		dealTimeField.setText("12:02");
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10 ,10));
		panel.add(new JLabel("当前时间"));
		panel.add(dealTimeField);
		panel.add(btn);
		panel.add(field);
		
		frame = new JFrame("JFormattedTextField 测试...");
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initAction() {
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, dealTimeField.getText());
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
				new JFormattedTextFieldTester();
			}
		});
		
	}

}
