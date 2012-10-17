package test;

import java.awt.Component;

import javax.swing.JOptionPane;

public class ComponentUtil {
	
	public static void showDialog(Component parentComponent,String message) {
		JOptionPane.showConfirmDialog(parentComponent, message);
	}
	
}
