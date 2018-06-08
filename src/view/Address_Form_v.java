package view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import base_classes.Panel_Base;
import model.Address;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Address_Form_v extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6277156431141406652L;

	public Address a;
	
	public JTextField addressText;
	public JTextField zoneText;
	public JTextArea commentText;
	
	public JButton cancel;
	public JButton save;
	public JButton delete;
	
	public JLabel warning;
	
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
		addressLabel.setBounds(30, 51, 46, 14);
		getContentPane().add(addressLabel);
		
		JLabel zoneLabel = new JLabel("Zona:");
		zoneLabel.setBounds(30, 76, 46, 14);
		getContentPane().add(zoneLabel);
		
		JLabel commentLabel = new JLabel("Detalles: ");
		commentLabel.setBounds(30, 110, 46, 14);
		getContentPane().add(commentLabel);
		
		warning = new JLabel("Este cliente no tiene direcciones en el registro. Crear una nueva calle");
		warning.setBounds(30, 11, 338, 14);
		getContentPane().add(warning);
	}
	
	private void fillables() {
		addressText = new JTextField();
		addressText.setBounds(86, 48, 256, 20);
		getContentPane().add(addressText);
		addressText.setColumns(10);
		
		zoneText = new JTextField();
		zoneText.setBounds(84, 73, 256, 20);
		getContentPane().add(zoneText);
		zoneText.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(86, 104, 256, 49);
		getContentPane().add(scrollPane);
		
		commentText = new JTextArea();
		scrollPane.setViewportView(commentText);
	}
	
	private void buttons() {
		cancel = new JButton("Cancelar");
		cancel.setBounds(129, 184, 89, 23);
		getContentPane().add(cancel);
		
		save = new JButton("Guardar");
		save.setBounds(30, 184, 89, 23);
		getContentPane().add(save);
		
		delete = new JButton("Eliminar");
		delete.setBounds(228, 184, 89, 23);
		getContentPane().add(delete);
	}
}
