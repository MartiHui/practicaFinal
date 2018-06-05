package view;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import base_classes.Panel_Base;

import javax.swing.JMenu;

public class Main_Window_v extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7125048035869540441L;
	public JMenuBar menuBar;
	public JMenu manageMenu;
	public JMenuItem mntmCategorias;
	public JMenuItem mntmProductos;

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
		
		manageMenu = new JMenu("Administrar");
		menuBar.add(manageMenu);
		
		mntmCategorias = new JMenuItem("Categorías");
		manageMenu.add(mntmCategorias);
		
		mntmProductos = new JMenuItem("Productos");
		manageMenu.add(mntmProductos);
	}
}
