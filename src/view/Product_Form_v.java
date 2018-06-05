package view;

import javax.swing.JDialog;

import base_classes.Panel_Base;
import model.Category;
import model.Product;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class Product_Form_v extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5506628312113431692L;

	public Product p;
	
	public JTextField nameText;
	public JTextField localText;
	public JTextField awayText;
	public JLabel codeText;
	public JComboBox<Category> categoryBox;
	
	public JButton cancel;
	public JButton save;
	
	public Product_Form_v(Product p) {
		this.p = p;
		
		getContentPane().setLayout(null);
		properties();
		
		labels();
		fillables();
		buttons();
	}
	
	private void properties() {
		this.setTitle("Producto");
		this.setResizable(false);
		this.setBounds(Panel_Base.centrar(400, 350));
	}
	
	private void labels() {
		JLabel codeLabel = new JLabel("C\u00F3digo:");
		codeLabel.setBounds(30, 30, 46, 14);
		getContentPane().add(codeLabel);
		
		JLabel nameLabel = new JLabel("Nombre:");
		nameLabel.setBounds(30, 70, 46, 14);
		getContentPane().add(nameLabel);
		
		JLabel cateogryLabel = new JLabel("Category:");
		cateogryLabel.setBounds(30, 111, 57, 14);
		getContentPane().add(cateogryLabel);
		
		JLabel localLabel = new JLabel("Precio local:");
		localLabel.setBounds(30, 152, 80, 14);
		getContentPane().add(localLabel);
		
		JLabel awayLabel = new JLabel("Precio domicilio");
		awayLabel.setBounds(30, 212, 80, 14);
		getContentPane().add(awayLabel);
	}
	
	private void fillables() {
		codeText = new JLabel("");
		codeText.setBounds(86, 30, 46, 14);
		getContentPane().add(codeText);
		
		nameText = new JTextField();
		nameText.setBounds(86, 67, 156, 20);
		getContentPane().add(nameText);
		nameText.setColumns(10);
		
		categoryBox = new JComboBox<Category>();
		categoryBox.setBounds(104, 108, 170, 20);
		getContentPane().add(categoryBox);
		
		localText = new JTextField();
		localText.setHorizontalAlignment(SwingConstants.RIGHT);
		localText.setBounds(104, 149, 115, 20);
		getContentPane().add(localText);
		localText.setColumns(10);
		
		awayText = new JTextField();
		awayText.setHorizontalAlignment(SwingConstants.RIGHT);
		awayText.setBounds(120, 209, 143, 20);
		getContentPane().add(awayText);
		awayText.setColumns(10);
	}
	
	private void buttons() {
		cancel = new JButton("Cancelar");
		cancel.setBounds(185, 263, 89, 23);
		getContentPane().add(cancel);
		
		save = new JButton("Guardar");
		save.setBounds(60, 263, 89, 23);
		getContentPane().add(save);
	}
}
