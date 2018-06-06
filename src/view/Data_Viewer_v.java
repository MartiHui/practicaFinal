package view;

import base_classes.Panel_Base;

import javax.swing.JScrollPane;
import javax.swing.JButton;

public class Data_Viewer_v extends Panel_Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5819652298870661206L;
	public JButton productsButton;
	public JButton categoryButton;
	public JButton clientsButton;
	public JButton addressButton;
	public JScrollPane dataPane;
	
	public Data_Viewer_v() {
		super();
		setLayout(null);
		
		table();
		buttons();
	}
	
	private void table() {
		dataPane = new JScrollPane();
		dataPane.setBounds(39, 37, 1065, 812);
		add(dataPane);
	}
	
	private void buttons() {
		productsButton = new JButton("PRODUCTOS");
		productsButton.setBounds(1158, 236, 209, 71);
		add(productsButton);
		
		categoryButton = new JButton("CATEGOR\u00CDAS");
		categoryButton.setBounds(1158, 343, 209, 71);
		add(categoryButton);
		
		clientsButton = new JButton("CLIENTES");
		clientsButton.setBounds(1158, 446, 209, 71);
		add(clientsButton);
		
		addressButton = new JButton("DIRECCIONES");
		addressButton.setBounds(1158, 547, 209, 65);
		add(addressButton);
	}
}
