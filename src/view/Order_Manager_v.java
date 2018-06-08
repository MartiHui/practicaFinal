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
import model.Category;
import model.Product;

import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.math.BigDecimal;
import javax.swing.JFormattedTextField;

public class Order_Manager_v extends Panel_Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8126866025712551977L;
	
	public Table orderTable;

	public JTextArea orderCommentText;
	public JButton orderCommentButton;
	
	public JTextField console;
	
	public JLabel totalText;
	public JLabel discountLabel;
	public JLabel discountText;

	public JComboBox<Category> categoryBox;
	public JComboBox<Product> productBox;
	public JButton plusQuantity;
	public JButton minusQuantity;
	public JLabel quantity;
	public JTextField newProductText;
	public JFormattedTextField newPriceText;
	public JTextArea productComment;
	
	public JButton addProduct;
	public JButton addDiscount;
	public JButton eliminateOrder;
	public JButton ticket;
	public JButton finishOrder;
	
	public JLabel phoneText;
	public JComboBox<Address> addressBox;
	public JButton modifyAddress;
	public JButton addAddress;
	public JTextArea addressComment;
	
	public JLabel numTableText;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public JRadioButton paidCash;
	public JRadioButton paidCard;

	public Order_Manager_v(boolean isLocal) {
		super();
		setLayout(null);

		productsTable();
		orderComment();
		console();
		separators();
		priceSection();
		productSection();
		buttons();
		payMethod();
		
		if (isLocal) {
			localElements();
		} else {
			awayElements();
		}
		
		repaint();
		revalidate();
	}
	
	private void productsTable() {
		JScrollPane orderPane = new JScrollPane();
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
	
	private void orderComment() {
		orderCommentButton = new JButton("Comentario: ");
		orderCommentButton.setMargin(new Insets(2, 2, 2, 2));
		orderCommentButton.setBounds(64, 121, 70, 42);
		add(orderCommentButton);
		
		orderCommentText = new JTextArea();
		orderCommentText.setEditable(false);
		orderCommentText.setText("");
		orderCommentText.setLineWrap(true);
		
		JScrollPane commentPane = new JScrollPane();
		commentPane.setBounds(144, 121, 818, 42);
		commentPane.setViewportView(orderCommentText);
		add(commentPane);
	}

	private void console() {
		console = new JTextField();
		console.setBorder(new LineBorder(new Color(0, 0, 0)));
		console.setBounds(972, 121, 458, 42);
		console.grabFocus();
		add(console);
		console.setColumns(10);
		
		JLabel consoleInfo = new JLabel("F1: Nuevo producto   F2: Nuevo precio  F3: Detalle de producto Enter: A\u00F1adir");
		consoleInfo.setBounds(972, 173, 458, 14);
		add(consoleInfo);
	}
	
	private void separators() {
		JSeparator separator = new JSeparator();
		separator.setMinimumSize(new Dimension(10, 10));
		separator.setBounds(972, 192, 458, 14);
		add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setMinimumSize(new Dimension(10, 10));
		separator_1.setBounds(972, 641, 458, 14);
		add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setMinimumSize(new Dimension(10, 10));
		separator_2.setBounds(972, 484, 458, 14);
		add(separator_2);
	}
	
	private void priceSection() {
		JLabel totalLabel = new JLabel("TOTAL:");
		totalLabel.setBounds(972, 512, 46, 14);
		add(totalLabel);
		
		totalText = new JLabel("");
		totalText.setFont(new Font("Tahoma", Font.BOLD, 27));
		totalText.setBounds(972, 537, 201, 60);
		add(totalText);
		
		discountLabel = new JLabel("DESCUENTO:");
		discountLabel.setBounds(1183, 509, 82, 14);
		add(discountLabel);
		
		discountText = new JLabel("");
		discountText.setFont(new Font("Tahoma", Font.BOLD, 27));
		discountText.setBounds(1174, 554, 201, 60);
		add(discountText);
	}
	
	private void productSection() {
		categoryBox = new JComboBox<Category>();
		categoryBox.setBounds(972, 217, 201, 60);
		add(categoryBox);
		
		productBox = new JComboBox<Product>();
		productBox.setBounds(1183, 217, 247, 60);
		add(productBox);
		
		plusQuantity = new JButton("+");
		plusQuantity.setFont(new Font("Tahoma", Font.BOLD, 25));
		plusQuantity.setBounds(1116, 288, 60, 60);
		add(plusQuantity);
		
		minusQuantity = new JButton("-");
		minusQuantity.setFont(new Font("Tahoma", Font.BOLD, 25));
		minusQuantity.setBounds(972, 288, 60, 60);
		add(minusQuantity);
		
		quantity = new JLabel("1");
		quantity.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		quantity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		quantity.setHorizontalAlignment(SwingConstants.CENTER);
		quantity.setBounds(1041, 291, 65, 60);
		add(quantity);
		
		// Alternative product data
		JLabel newProductLabel = new JLabel("Nuevo producto:");
		newProductLabel.setBounds(972, 374, 91, 14);
		add(newProductLabel);
		
		JLabel newPriceLabel = new JLabel("Nuevo precio:");
		newPriceLabel.setBounds(972, 399, 91, 14);
		add(newPriceLabel);
		
		JLabel productCommentLabel = new JLabel("Detalle:");
		productCommentLabel.setBounds(972, 424, 91, 14);
		add(productCommentLabel);
		
		newProductText = new JTextField();
		newProductText.setBounds(1073, 371, 347, 20);
		add(newProductText);
		newProductText.setColumns(10);
		
		newPriceText = new JFormattedTextField(new BigDecimal(0));
		newPriceText.setBounds(1073, 396, 347, 20);
		add(newPriceText);
		newPriceText.setValue(null);
		
		JScrollPane productCommentPane = new JScrollPane();
		productCommentPane.setBounds(1073, 424, 347, 42);
		add(productCommentPane);
		
		productComment = new JTextArea();
		productCommentPane.setViewportView(productComment);
	}
	
	private void buttons() {
		addProduct = new JButton("A\u00F1adir...");
		addProduct.setFont(new Font("Tahoma", Font.PLAIN, 24));
		addProduct.setBounds(1183, 288, 237, 60);
		add(addProduct);
		
		addDiscount = new JButton("A\u00F1adir descuento");
		addDiscount.setBounds(972, 666, 158, 76);
		add(addDiscount);
		
		eliminateOrder = new JButton("");
		eliminateOrder.setBounds(1360, 11, 70, 58);
		add(eliminateOrder);
		
		ticket = new JButton("Sacar ticket");
		ticket.setBounds(1140, 670, 158, 68);
		add(ticket);
		
		finishOrder = new JButton("Finalizar pedido");
		finishOrder.setBounds(1140, 752, 176, 92);
		add(finishOrder);
	}
	
	private void payMethod() {
		JLabel lblFormaDePago = new JLabel("Forma de pago:");
		lblFormaDePago.setBounds(999, 758, 83, 14);
		add(lblFormaDePago);
		
		paidCash = new JRadioButton("Efectivo");
		buttonGroup.add(paidCash);
		paidCash.setBounds(997, 779, 109, 23);
		add(paidCash);
		
		paidCard = new JRadioButton("Tarjeta");
		buttonGroup.add(paidCard);
		paidCard.setBounds(999, 805, 109, 23);
		add(paidCard);
	}
	
	private void awayElements() {
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
		addressBox.setBounds(123, 50, 436, 20);
		add(addressBox);
		
		addressComment = new JTextArea();
		addressComment.setEditable(false);
		addressComment.setText("");
		addressComment.setLineWrap(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(593, 50, 350, 42);
		scrollPane.setViewportView(addressComment);
		add(scrollPane);
		
		modifyAddress = new JButton("Modificar calle");
		modifyAddress.setBounds(123, 80, 135, 23);
		add(modifyAddress);
		
		addAddress = new JButton("Nueva calle");
		addAddress.setBounds(266, 80, 125, 23);
		add(addAddress);
	}
	
	private void localElements() {
		numTableText = new JLabel("");
		numTableText.setBounds(160, 37, 46, 14);
		add(numTableText);
		
		JLabel tableLabel = new JLabel("Mesa n\u00BA: ");
		tableLabel.setBounds(102, 37, 46, 14);
		add(tableLabel);
	}
}
