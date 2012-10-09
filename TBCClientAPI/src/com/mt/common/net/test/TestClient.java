package com.mt.common.net.test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

/**
 * @author:Ryan
 * @date:2012-10-9
 */
public class TestClient {

	private JFrame frame;
	private JPanel searchPanel, contextPanel;
	private JComboBox modelBox, sslBox;
	private JTextField searchField;
	private JButton searchBtn;
	private JTextArea resultArea;
	
	private static Vector<String> modelModel, sslModel;
	static {
		modelModel = new Vector<String>();
		modelModel.add("Socket");
		modelModel.add("XSocket");
		modelModel.add("Http");
		
		sslModel = new Vector<String>();
		sslModel.add("Y");
		sslModel.add("N");
	}
	
	public TestClient() {
		initComponent();
		initAction();
	}

	private void initComponent() {
		frame = new JFrame("TBC Client 测试样例...");

		initSearchComponent();
		initContextComponent();

		frame.getContentPane().add(searchPanel, BorderLayout.NORTH);
		frame.getContentPane().add(contextPanel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLocation(300, 300);
		frame.setVisible(true);
	}

	private void initSearchComponent() {
		searchPanel = new JPanel(new FlowLayout(10,10,FlowLayout.LEFT));
		modelBox = new JComboBox(modelModel);
		sslBox = new JComboBox(sslModel);
		searchField = new JTextField(20);
		searchBtn = new JButton("Send");
		
		searchPanel.add(new JLabel("Model"));
		searchPanel.add(modelBox);
		searchPanel.add(new JLabel("SSL"));
		searchPanel.add(sslBox);
		searchPanel.add(new JLabel("查询条件"));
		searchPanel.add(searchField);
		searchPanel.add(searchBtn);
	}

	private void initContextComponent() {
		contextPanel = new JPanel(new BorderLayout());
		contextPanel.setBorder(new TitledBorder("结果集"));
		resultArea = new JTextArea();
		contextPanel.add(resultArea);
	}

	private void initAction() {
		
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
				new TestClient();
			}
		});
	}
}
