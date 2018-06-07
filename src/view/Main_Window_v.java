package view;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import base_classes.Panel_Base;

public class Main_Window_v extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7125048035869540441L;
	public JMenuBar menuBar;
	public JMenuItem menuData;
	public JMenuItem menuOrders;
	public JMenuItem menuAccounting;

	public Main_Window_v() {
		menu();
		setProperties();
	}
	
	private void setProperties() {
		int width = 1440;
		int height = 900;
		
		this.setTitle("TERMINAL PUNTO DE VENTA");
		this.setResizable(false);
		this.setBounds(Panel_Base.centrar(width, height));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		this.setVisible(true);
	}
	
	private void menu() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuOrders = new JMenuItem("Pedidos");
		menuBar.add(menuOrders);
		
		menuData = new JMenuItem("Administrar");
		menuBar.add(menuData);
		
		menuAccounting = new JMenuItem("Contabilidad");
		menuBar.add(menuAccounting);
	}
}
