package view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import base_classes.Panel_Base;
import model.Address;

public class Address_Form_v extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6277156431141406652L;

	public Address a;
	
	public JTextField addressText;
	public JTextField zoneText;
	
	public JButton cancel;
	public JButton save;
	public JButton delete;
	
	public Address_Form_v(Address a) {
		this.a = a;
		
		getContentPane().setLayout(null);
		properties();
		
		labels();
		fillables();
		buttons();
	}
	
	private void properties() {
		this.setTitle("Dirección");
		this.setResizable(false);
		this.setBounds(Panel_Base.centrar(400, 300));
	}
	
	private void labels() {
		JLabel addressLabel = new JLabel("Calle:");
		addressLabel.setBounds(30, 25, 46, 14);
		getContentPane().add(addressLabel);
		
		JLabel zoneLabel = new JLabel("Zona:");
		zoneLabel.setBounds(30, 50, 46, 14);
		getContentPane().add(zoneLabel);
	}
	
	private void fillables() {
		addressText = new JTextField();
		addressText.setBounds(84, 22, 156, 20);
		getContentPane().add(addressText);
		addressText.setColumns(10);
		
		zoneText = new JTextField();
		zoneText.setBounds(84, 50, 156, 20);
		getContentPane().add(zoneText);
		zoneText.setColumns(10);
	}
	
	private void buttons() {
		cancel = new JButton("Cancelar");
		cancel.setBounds(125, 102, 89, 23);
		getContentPane().add(cancel);
		
		save = new JButton("Guardar");
		save.setBounds(26, 102, 89, 23);
		getContentPane().add(save);
		
		delete = new JButton("Eliminar");
		delete.setBounds(226, 102, 89, 23);
		getContentPane().add(delete);
	}
}
