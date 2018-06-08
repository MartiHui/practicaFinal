package view;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;

import model.Category;
import model.Product;
import utils.Panel_Base;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import java.math.BigDecimal;

import javax.swing.JButton;

public class Product_Form_v extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5506628312113431692L;

	public Product p;
	
	public JTextField nameText;
	public JFormattedTextField localText;
	public JFormattedTextField awayText;
	public JLabel codeText;
	public JComboBox<Category> categoryBox;
	
	public JButton cancel;
	public JButton save;
	public JButton delete;
	
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
		this.setBounds(Panel_Base.centrar(365, 320));
	}
	
	private void labels() {
		JLabel codeLabel = new JLabel("C\u00F3digo:");
		codeLabel.setBounds(30, 30, 90, 20);
		getContentPane().add(codeLabel);
		
		JLabel nameLabel = new JLabel("Nombre:");
		nameLabel.setBounds(30, 70, 90, 20);
		getContentPane().add(nameLabel);
		
		JLabel categoryLabel = new JLabel("Category:");
		categoryLabel.setBounds(30, 110, 90, 20);
		getContentPane().add(categoryLabel);
		
		JLabel localLabel = new JLabel("Precio local:");
		localLabel.setBounds(30, 150, 90, 20);
		getContentPane().add(localLabel);
		
		JLabel awayLabel = new JLabel("Precio domicilio");
		awayLabel.setBounds(30, 190, 90, 20);
		getContentPane().add(awayLabel);
	}
	
	private void fillables() {
		codeText = new JLabel("");
		codeText.setBounds(120, 30, 200, 20);
		getContentPane().add(codeText);
		
		nameText = new JTextField();
		nameText.setBounds(120, 70, 200, 20);
		getContentPane().add(nameText);
		nameText.setColumns(10);
		
		categoryBox = new JComboBox<Category>();
		categoryBox.setBounds(120, 110, 200, 20);
		getContentPane().add(categoryBox);
		
		localText = new JFormattedTextField(new BigDecimal(0));
		localText.setHorizontalAlignment(SwingConstants.RIGHT);
		localText.setBounds(120, 150, 200, 20);
		getContentPane().add(localText);
		localText.setColumns(10);
		
		awayText = new JFormattedTextField(new BigDecimal(0));
		awayText.setHorizontalAlignment(SwingConstants.RIGHT);
		awayText.setBounds(120, 190, 200, 20);
		getContentPane().add(awayText);
		awayText.setColumns(10);
	}
	
	private void buttons() {
		cancel = new JButton("Cancelar");
		cancel.setBounds(130, 230, 89, 23);
		getContentPane().add(cancel);
		
		save = new JButton("Guardar");
		save.setBounds(30, 230, 89, 23);
		getContentPane().add(save);
		
		delete = new JButton("Eliminar");
		delete.setBounds(231, 230, 89, 23);
		getContentPane().add(delete);
	}
}
