package view;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import utils.Panel_Base;
import java.awt.Font;

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
		dataPane.setBounds(40, 40, 1060, 750);
		add(dataPane);
	}
	
	private void buttons() {
		productsButton = new JButton("PRODUCTOS");
		productsButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		productsButton.setBounds(1150, 200, 230, 70);
		add(productsButton);
		
		categoryButton = new JButton("CATEGOR\u00CDAS");
		categoryButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		categoryButton.setBounds(1150, 300, 230, 70);
		add(categoryButton);
		
		clientsButton = new JButton("CLIENTES");
		clientsButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		clientsButton.setBounds(1150, 400, 230, 70);
		add(clientsButton);
		
		addressButton = new JButton("DIRECCIONES");
		addressButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		addressButton.setBounds(1150, 500, 230, 70);
		add(addressButton);
	}
	
	private void search() {
		JLabel label = new JLabel("Buscar");
		label.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label.setBounds(1150, 81, 46, 14);
		add(label);
		
		searchLabel = new JLabel("");
		searchLabel.setBounds(1150, 106, 230, 14);
		add(searchLabel);
		
		searchText = new JTextField();
		searchText.setFont(new Font("Tahoma", Font.PLAIN, 15));
		searchText.setBounds(1150, 120, 230, 30);
		add(searchText);
		searchText.setColumns(10);
	}
}
