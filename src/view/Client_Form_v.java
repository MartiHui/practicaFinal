package view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Client;
import utils.Panel_Base;

public class Client_Form_v extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4290070502271032400L;

	public Client c;
	
	public JTextField phoneText;
	
	public JButton cancel;
	public JButton save;
	
	public Client_Form_v(Client c) {
		this.c = c;
		
		getContentPane().setLayout(null);
		properties();
		
		labels();
		fillables();
		buttons();
	}
	
	private void properties() {
		this.setTitle("Cliente");
		this.setResizable(false);
		this.setBounds(Panel_Base.centrar(260, 150));
	}
	
	private void labels() {
		JLabel phoneLabel = new JLabel("Teléfono:");
		phoneLabel.setBounds(30, 25, 50, 20);
		getContentPane().add(phoneLabel);
	}
	
	private void fillables() {
		phoneText = new JTextField();
		phoneText.setBounds(90, 25, 128, 20);
		getContentPane().add(phoneText);
		phoneText.setColumns(10);
	}
	
	private void buttons() {
		cancel = new JButton("Cancelar");
		cancel.setBounds(128, 65, 90, 25);
		getContentPane().add(cancel);
		
		save = new JButton("Guardar");
		save.setBounds(30, 65, 90, 25);
		getContentPane().add(save);
	}
}
