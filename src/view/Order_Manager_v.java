package view;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
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
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class Order_Manager_v extends Panel_Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8126866025712551977L;
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
	public JComboBox<model.Category> categoryBox;
	public JComboBox<Product> productBox;
	public JButton modifyAddress;
	public JButton addAddress;
	public JButton eliminateAddress;
	public JLabel phoneText;
	public JComboBox<Address> addressBox;
	public JLabel tableText;
	public JLabel dateText;
	public JLabel discountLabel;
	public JScrollPane commentPane;
	public JTextArea commentText;
	public JButton addComment;
	public JButton addPrice;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel priceLabel;
	
	public Order_Manager_v(boolean isLocal) {
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
		commentButton = new JButton("Comentario: ");
		commentButton.setMargin(new Insets(2, 2, 2, 2));
		commentButton.setBounds(64, 121, 70, 42);
		add(commentButton);
		
		commentText = new JTextArea();
		commentText.setEditable(false);
		commentText.setText("");
		commentText.setLineWrap(true);
		
		commentPane = new JScrollPane();
		commentPane.setBounds(144, 121, 818, 42);
		commentPane.setViewportView(commentText);
		add(commentPane);
	}
	
	private void createLinesTable() {
		orderPane = new JScrollPane();
		orderPane.setBounds(81, 173, 881, 671);
		add(orderPane);
		
		orderTable = new Table(new String[] {"product_id", "Cantidad", "Código", "Producto", "Precio/ud", "Precio total", "Detalles", "", ""},
				new Class<?>[] {Integer.class, Integer.class, String.class, String.class, String.class, String.class, ImageIcon.class, String.class, String.class},
				new Integer[] {0, 3, 3, 100, 125, 125, 3, 3, 3},
				null,
				null,
				null);
		orderTable.hideColumn(0);
		orderTable.alinear('l', 1);
		orderTable.alinear('l', 2);
		orderTable.alinear('r', 3);
		orderTable.alinear('r', 4);
		orderTable.alinear('c', 5);
		orderTable.alinear('c', 7);
		orderTable.tabla.getTableHeader().setEnabled(false);
		orderPane.setViewportView(orderTable.tabla);
	}
	
	private void createConsole() {
		console = new JTextField();
		console.setBorder(new LineBorder(new Color(0, 0, 0)));
		console.setBounds(972, 78, 458, 42);
		console.grabFocus();
		add(console);
		console.setColumns(10);
	}
	
	private void createSeparators() {
		JSeparator separator = new JSeparator();
		separator.setMinimumSize(new Dimension(10, 10));
		separator.setBounds(972, 131, 458, 14);
		add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setMinimumSize(new Dimension(10, 10));
		separator_1.setBounds(972, 578, 458, 14);
		add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setMinimumSize(new Dimension(10, 10));
		separator_2.setBounds(972, 430, 458, 14);
		add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setMinimumSize(new Dimension(10, 10));
		separator_3.setBounds(972, 792, 458, 14);
		add(separator_3);
	}
	
	private void createPriceSection() {
		JLabel totalLabel = new JLabel("TOTAL:");
		totalLabel.setBounds(972, 449, 46, 14);
		add(totalLabel);
		
		totalText = new JLabel("");
		totalText.setFont(new Font("Tahoma", Font.BOLD, 27));
		totalText.setBounds(972, 474, 201, 60);
		add(totalText);
		
		discountLabel = new JLabel("DESCUENTO:");
		discountLabel.setBounds(1183, 449, 82, 14);
		add(discountLabel);
		
		discountText = new JLabel("");
		discountText.setFont(new Font("Tahoma", Font.BOLD, 27));
		discountText.setBounds(1183, 474, 201, 60);
		add(discountText);
	}
	
	private void createProductSection() {
		categoryBox = new JComboBox<model.Category>();
		categoryBox.setBounds(972, 143, 201, 60);
		add(categoryBox);
		
		productBox = new JComboBox<Product>();
		productBox.setBounds(1183, 143, 247, 60);
		add(productBox);
		
		productButton = new JButton("A\u00F1adir...(Enter)");
		productButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		productButton.setBounds(972, 285, 458, 60);
		add(productButton);
		
		plusQuantity = new JButton("+");
		plusQuantity.setFont(new Font("Tahoma", Font.BOLD, 25));
		plusQuantity.setBounds(1113, 214, 60, 60);
		add(plusQuantity);
		
		minusQuantity = new JButton("-");
		minusQuantity.setFont(new Font("Tahoma", Font.BOLD, 25));
		minusQuantity.setBounds(972, 214, 60, 60);
		add(minusQuantity);
		
		quantity = new JLabel("1");
		quantity.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		quantity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		quantity.setHorizontalAlignment(SwingConstants.CENTER);
		quantity.setBounds(1038, 214, 65, 60);
		add(quantity);
	}
	
	private void createButtons() {
		addDiscount = new JButton("A\u00F1adir descuento");
		addDiscount.setBounds(972, 603, 158, 76);
		add(addDiscount);
		
		newProduct = new JButton("Crear nuevo producto");
		newProduct.setBounds(1140, 603, 176, 76);
		add(newProduct);
		
		eliminate = new JButton("");
		eliminate.setBounds(1360, 11, 70, 58);
		add(eliminate);
		
		ticket = new JButton("Sacar ticket");
		ticket.setBounds(972, 690, 158, 91);
		add(ticket);
		
		orderEnd = new JButton("Finalizar pedido");
		orderEnd.setBounds(1140, 689, 176, 92);
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
		dateText.setBounds(972, 811, 437, 60);
		add(dateText);
		
		addComment = new JButton("Comentario(F1)");
		addComment.setBounds(1183, 214, 104, 60);
		add(addComment);
		
		addPrice = new JButton("Precio(F2)");
		addPrice.setBounds(1305, 214, 104, 60);
		add(addPrice);
		
		JLabel lblFormaDePago = new JLabel("Forma de pago:");
		lblFormaDePago.setBounds(1326, 603, 83, 14);
		add(lblFormaDePago);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(1321, 630, 109, 23);
		add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("New radio button");
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(1322, 656, 109, 23);
		add(rdbtnNewRadioButton_1);
		
		priceLabel = new JLabel("Nuevo precio:");
		priceLabel.setBounds(972, 376, 91, 14);
		add(priceLabel);
		
		JLabel productLabel = new JLabel("Nuevo producto:");
		productLabel.setBounds(972, 356, 91, 14);
		add(productLabel);
		
		JLabel productComment = new JLabel("Detalle:");
		productComment.setBounds(972, 405, 91, 14);
		add(productComment);
		
		JLabel newProductText = new JLabel("");
		newProductText.setBounds(1099, 356, 217, 14);
		add(newProductText);
		
		JLabel newPriceText = new JLabel("");
		newPriceText.setBounds(1090, 376, 208, 14);
		add(newPriceText);
		
		JLabel productCommentText = new JLabel("");
		productCommentText.setBounds(1096, 405, 191, 14);
		add(productCommentText);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(1341, 372, 89, 23);
		add(btnNewButton);
	}
	
	private void createLocalElements() {
		tableText = new JLabel("");
		tableText.setBounds(160, 37, 46, 14);
		add(tableText);
	}
	
	public void resetQuantity() {
		this.quantity.setText("1");
	}
}
