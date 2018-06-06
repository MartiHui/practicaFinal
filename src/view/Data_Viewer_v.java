package view;

import base_classes.Panel_Base;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

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
	
	public JTextField searchText;
	public JLabel searchLabel;
	
	public Data_Viewer_v() {
		super();
		setLayout(null);
		
		table();
		buttons();
		search();
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
	
	private void search() {
		JLabel label = new JLabel("Buscar");
		label.setBounds(1158, 645, 46, 14);
		add(label);
		
		searchLabel = new JLabel("");
		searchLabel.setBounds(1158, 670, 209, 14);
		add(searchLabel);
		
		searchText = new JTextField();
		searchText.setBounds(1158, 706, 209, 20);
		add(searchText);
		searchText.setColumns(10);
	}
}
