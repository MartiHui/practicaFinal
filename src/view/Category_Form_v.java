package view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Category;
import utils.Panel_Base;

public class Category_Form_v extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Category c;
	
	public JTextField nameText;
	
	public JButton cancel;
	public JButton save;
	public JButton delete;
	
	public Category_Form_v(Category c) {
		this.c = c;
		
		getContentPane().setLayout(null);
		properties();
		
		labels();
		fillables();
		buttons();
	}
	
	private void properties() {
		this.setTitle("Categoría");
		this.setResizable(false);
		this.setBounds(Panel_Base.centrar(400, 140));
	}
	
	private void labels() {
		JLabel nameLabel = new JLabel("Nombre:");
		nameLabel.setBounds(30, 25, 50, 20);
		getContentPane().add(nameLabel);
	}
	
	private void fillables() {
		nameText = new JTextField();
		nameText.setBounds(90, 25, 256, 20);
		getContentPane().add(nameText);
		nameText.setColumns(10);
	}
	
	private void buttons() {
		cancel = new JButton("Cancelar");
		cancel.setBounds(143, 65, 90, 25);
		getContentPane().add(cancel);
		
		save = new JButton("Guardar");
		save.setBounds(30, 65, 90, 25);
		getContentPane().add(save);
		
		delete = new JButton("Eliminar");
		delete.setBounds(256, 65, 90, 25);
		getContentPane().add(delete);
	}
}
