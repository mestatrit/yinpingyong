package test;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.mt.common.gui.MTXComponent.MTXDatePicker;
import com.mt.util.DateUtil;

/**
 * MTXDataPicker 日期组件样例
 * 
 * @Author: Ryan
 * 
 * 2012-7-28
 */
public class MTXDataPickerTest {
	
	private JFrame frame;
	private JButton btn;
	private MTXDatePicker dataPicker;
	
	public MTXDataPickerTest() {
		this.initComponent();
		this.initAction();
	}
	
	private void initComponent() {
		frame = new JFrame("MTXDataPicker 样例");
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
		
		JLabel label = new JLabel("当前日期");
		searchPanel.add(label);
		
		dataPicker = new MTXDatePicker();
		dataPicker.setDate(new Date());
		searchPanel.add(dataPicker);
		
		btn = new JButton("查询");
		searchPanel.add(btn);
		
		frame.getContentPane().add(searchPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 100);
		frame.setLocation(500, 500);
		frame.setVisible(true);
	}
	
	private void initAction() {
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, DateUtil.getDateString(dataPicker.getDate()));
			}
		});
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MTXDataPickerTest();
			}
		});
	}
	
}
