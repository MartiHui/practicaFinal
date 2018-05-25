package view;

import model.Panel_Base;
import javax.swing.JLabel;

public class Away_Order extends Panel_Base {
	
	public JLabel phone;
	public JLabel address;
	public JLabel date;

	public Away_Order() {
		super();
		setLayout(null);
		createHeader();
	}
	
	private void createHeader() {
		JLabel phoneLabel = new JLabel("Tel\u00E9fono:");
		phoneLabel.setBounds(40, 40, 46, 14);
		add(phoneLabel);
		
		phone = new JLabel("");
		phone.setBounds(96, 40, 46, 14);
		add(phone);
		
		JLabel addressLabel = new JLabel("Domicilio:");
		addressLabel.setBounds(40, 80, 46, 14);
		add(addressLabel);
		
		address = new JLabel("");
		address.setBounds(96, 80, 220, 14);
		add(address);
		
		JLabel dateLabel = new JLabel("\u00DAltimo pedido:");
		dateLabel.setBounds(221, 40, 72, 14);
		add(dateLabel);
		
		date = new JLabel("");
		date.setBounds(303, 40, 46, 14);
		add(date);
	}
}
