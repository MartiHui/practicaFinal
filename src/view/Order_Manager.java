package view;
import javax.swing.JScrollPane;

import java.math.BigDecimal;
import java.util.Locale.Category;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import base_classes.Panel_Base;
import base_classes.Table;
import model.Address;
import model.Product;

import java.awt.Insets;

public class Order_Manager extends Panel_Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8126866025712551977L;
	public JTextField commentText;
	public JScrollPane orderPane;
	public Table orderTable;
	public JTextField console;
	public JLabel totalText;
	public JLabel discountText;
	public JButton productButton;
	public JButton plusQuantity;
	public JButton minusQuantity;
	public JLabel quantity;
	public JButton addDiscount;
	public JButton newProduct;
	public JButton eliminate;
	public JButton ticket;
	public JButton orderEnd;
	public JButton goBack;
	public JButton commentButton;
	public JComboBox<Category> categoryBox;
	public JComboBox<Product> productBox;
	public JButton modifyAddress;
	public JButton addAddress;
	public JButton eliminateAddress;
	public JLabel phoneText;
	public JComboBox<Address> addressBox;
	public JLabel tableText;
	public JLabel dateText;
	public JLabel discountLabel;
	
	public Order_Manager(boolean isLocal) {
		super();
		setLayout(null);
		createCommentSection();
		createLinesTable();
		createConsole();
		createSeparators();
		createPriceSection();
		createProductSection();
		createButtons();
		createDateSection();
		
		if (isLocal) {
			createLocalElements();
		} else {
			createAwayElements();
		}
		
		repaint();
		revalidate();
	}
	
	private void createCommentSection() {
		commentText = new JTextField();
		commentText.setEditable(false);
		commentText.setBounds(144, 121, 818, 42);
		add(commentText);
		commentText.setColumns(10);
		
		commentButton = new JButton("Comentario: ");
		commentButton.setMargin(new Insets(2, 2, 2, 2));
		commentButton.setBounds(64, 121, 70, 42);
		add(commentButton);
	}
	
	private void createLinesTable() {
		orderPane = new JScrollPane();
		orderPane.setBounds(81, 173, 881, 671);
		add(orderPane);
		
		orderTable = new Table(new String[] {"product_id", "Cantidad", "C�digo", "Producto", "Precio", "Detalles", "", ""},
				new Class<?>[] {Integer.class, Integer.class, String.class, String.class, BigDecimal.class, String.class, String.class, String.class},
				new Integer[] {0, 10, 20, 100, 50, 15, 15, 15},
				null,
				null,
				null);
		orderTable.hideColumn(0);
		orderTable.alinear('l', 1);
		orderTable.alinear('l', 2);
		orderTable.alinear('l', 3);
		orderTable.alinear('r', 4);
		orderPane.setViewportView(orderTable.tabla);
	}
	
	private void createConsole() {
		console = new JTextField();
		console.setBorder(new LineBorder(new Color(0, 0, 0)));
		console.setEditable(false);
		console.setBounds(972, 121, 458, 42);
		add(console);
		console.setColumns(10);
	}
	
	private void createSeparators() {
		JSeparator separator = new JSeparator();
		separator.setMinimumSize(new Dimension(10, 10));
		separator.setBounds(972, 173, 458, 14);
		add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setMinimumSize(new Dimension(10, 10));
		separator_1.setBounds(972, 445, 458, 14);
		add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setMinimumSize(new Dimension(10, 10));
		separator_2.setBounds(972, 338, 458, 14);
		add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setMinimumSize(new Dimension(10, 10));
		separator_3.setBounds(972, 669, 458, 14);
		add(separator_3);
	}
	
	private void createPriceSection() {
		JLabel totalLabel = new JLabel("TOTAL:");
		totalLabel.setBounds(972, 363, 46, 14);
		add(totalLabel);
		
		totalText = new JLabel("");
		totalText.setFont(new Font("Tahoma", Font.BOLD, 27));
		totalText.setBounds(972, 374, 201, 60);
		add(totalText);
		
		discountLabel = new JLabel("DESCUENTO:");
		discountLabel.setBounds(1183, 363, 82, 14);
		add(discountLabel);
		
		discountText = new JLabel("");
		discountText.setFont(new Font("Tahoma", Font.BOLD, 27));
		discountText.setBounds(1183, 374, 201, 60);
		add(discountText);
	}
	
	private void createProductSection() {
		categoryBox = new JComboBox<Category>();
		categoryBox.setBounds(972, 196, 201, 60);
		add(categoryBox);
		
		productBox = new JComboBox<Product>();
		productBox.setBounds(1183, 196, 247, 60);
		add(productBox);
		
		productButton = new JButton("A\u00F1adir...");
		productButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		productButton.setBounds(1183, 267, 247, 60);
		add(productButton);
		
		plusQuantity = new JButton("+");
		plusQuantity.setFont(new Font("Tahoma", Font.BOLD, 25));
		plusQuantity.setBounds(972, 267, 60, 60);
		add(plusQuantity);
		
		minusQuantity = new JButton("-");
		minusQuantity.setFont(new Font("Tahoma", Font.BOLD, 25));
		minusQuantity.setBounds(1113, 267, 60, 60);
		add(minusQuantity);
		
		quantity = new JLabel("1");
		quantity.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		quantity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		quantity.setHorizontalAlignment(SwingConstants.CENTER);
		quantity.setBounds(1042, 267, 65, 60);
		add(quantity);
	}
	
	private void createButtons() {
		addDiscount = new JButton("A\u00F1adir descuento");
		addDiscount.setBounds(972, 480, 158, 76);
		add(addDiscount);
		
		newProduct = new JButton("Crear nuevo producto");
		newProduct.setBounds(1140, 479, 176, 76);
		add(newProduct);
		
		eliminate = new JButton("");
		eliminate.setBounds(1360, 11, 70, 58);
		add(eliminate);
		
		ticket = new JButton("Sacar ticket");
		ticket.setBounds(972, 567, 158, 91);
		add(ticket);
		
		orderEnd = new JButton("Finalizar pedido");
		orderEnd.setBounds(1140, 566, 176, 92);
		add(orderEnd);
		
		goBack = new JButton("");
		goBack.setBounds(10, 11, 60, 60);
		add(goBack);
		
		JLabel tableLabel = new JLabel("Mesa n\u00BA: ");
		tableLabel.setBounds(102, 37, 46, 14);
		add(tableLabel);
	}
	
	private void createAwayElements() {
		JLabel phoneLabel = new JLabel("Tel\u00E9fono: ");
		phoneLabel.setBounds(81, 22, 53, 14);
		add(phoneLabel);
		
		phoneText = new JLabel("");
		phoneText.setBounds(142, 22, 128, 14);
		add(phoneText);
		
		JLabel addressLabel = new JLabel("Calle: ");
		addressLabel.setBounds(81, 50, 46, 14);
		add(addressLabel);
		
		addressBox = new JComboBox<Address>();
		addressBox.setBounds(123, 50, 618, 20);
		add(addressBox);
		
		modifyAddress = new JButton("Modificar calle");
		modifyAddress.setBounds(123, 80, 135, 23);
		add(modifyAddress);
		
		addAddress = new JButton("Nueva calle");
		addAddress.setBounds(266, 80, 125, 23);
		add(addAddress);
		
		eliminateAddress = new JButton("Eliminar calle");
		eliminateAddress.setBounds(401, 80, 158, 23);
		add(eliminateAddress);
	}
	
	private void createDateSection() {
		dateText = new JLabel("");
		dateText.setFont(new Font("Tahoma", Font.BOLD, 30));
		dateText.setHorizontalAlignment(SwingConstants.CENTER);
		dateText.setBounds(972, 683, 437, 60);
		add(dateText);
	}
	
	private void createLocalElements() {
		tableText = new JLabel("");
		tableText.setBounds(160, 37, 46, 14);
		add(tableText);
	}
}