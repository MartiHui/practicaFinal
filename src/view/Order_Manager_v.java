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
import java.awt.Image;

import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import model.Address;
import model.Category;
import model.Product;
import utils.Panel_Base;
import utils.Table;

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

	public JButton resetData;

	public Order_Manager_v(boolean isLocal) {
		super();
		date.setLocation(1100, 10);
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
		orderPane.setBounds(50, 170, 850, 700);
		add(orderPane);
		
		orderTable = new Table(new String[] {"product_id", "Cantidad", "Código", "Producto", "Precio/ud", "Precio total", "Detalles", "", ""},
				new Class<?>[] {Integer.class, Integer.class, String.class, String.class, String.class, String.class, ImageIcon.class, String.class, String.class},
				new Integer[] {0, 3, 3, 300, 30, 100, 3, 1, 1},
				null,
				null,
				null);
		
		orderTable.hideColumn(0);
		orderTable.alinear('l', 1);
		orderTable.alinear('l', 2);
		orderTable.alinear('r', 3);
		orderTable.alinear('r', 4);
		orderTable.alinear('c', 6);
		orderTable.alinear('c', 7);
		
		orderTable.tabla.getTableHeader().setEnabled(false);
		orderPane.setViewportView(orderTable.tabla);
	}
	
	private void orderComment() {
		orderCommentButton = new JButton("Comentario: ");
		orderCommentButton.setMargin(new Insets(2, 2, 2, 2));
		orderCommentButton.setBounds(50, 110, 70, 40);
		add(orderCommentButton);
		
		orderCommentText = new JTextArea();
		orderCommentText.setEditable(false);
		orderCommentText.setText("");
		orderCommentText.setLineWrap(true);
		
		JScrollPane commentPane = new JScrollPane();
		commentPane.setBounds(130, 110, 770, 40);
		commentPane.setViewportView(orderCommentText);
		add(commentPane);
	}

	private void console() {
		console = new JTextField();
		console.setBorder(new LineBorder(new Color(0, 0, 0)));
		console.setBounds(950, 110, 450, 40);
		console.grabFocus();
		add(console);
		console.setColumns(10);
		
		JLabel consoleInfo = new JLabel("F1: Nuevo producto           F2: Nuevo precio          F3: Detalle de producto         Enter: A\u00F1adir");
		consoleInfo.setBounds(950, 161, 450, 20);
		add(consoleInfo);
	}
	
	private void separators() {
		JSeparator separator = new JSeparator();
		separator.setMinimumSize(new Dimension(10, 10));
		separator.setBounds(950, 192, 450, 14);
		add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setMinimumSize(new Dimension(10, 10));
		separator_1.setBounds(950, 620, 450, 14);
		add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setMinimumSize(new Dimension(10, 10));
		separator_2.setBounds(950, 490, 450, 14);
		add(separator_2);
	}
	
	private void priceSection() {
		JLabel totalLabel = new JLabel("TOTAL:");
		totalLabel.setBounds(950, 515, 200, 20);
		add(totalLabel);
		
		totalText = new JLabel("");
		totalText.setFont(new Font("Tahoma", Font.BOLD, 27));
		totalText.setBounds(950, 540, 200, 60);
		add(totalText);
		
		discountLabel = new JLabel("DESCUENTO:");
		discountLabel.setBounds(1175, 515, 200, 20);
		add(discountLabel);
		
		discountText = new JLabel("");
		discountText.setFont(new Font("Tahoma", Font.BOLD, 27));
		discountText.setBounds(1175, 540, 200, 60);
		add(discountText);
	}
	
	private void productSection() {
		categoryBox = new JComboBox<Category>();
		categoryBox.setBounds(950, 215, 215, 60);
		add(categoryBox);
		
		productBox = new JComboBox<Product>();
		productBox.setBounds(1183, 217, 215, 60);
		add(productBox);
		
		plusQuantity = new JButton("+");
		plusQuantity.setFont(new Font("Tahoma", Font.BOLD, 25));
		plusQuantity.setBounds(1100, 290, 60, 60);
		add(plusQuantity);
		
		minusQuantity = new JButton("-");
		minusQuantity.setFont(new Font("Tahoma", Font.BOLD, 25));
		minusQuantity.setBounds(950, 290, 60, 60);
		add(minusQuantity);
		
		quantity = new JLabel("1");
		quantity.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		quantity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		quantity.setHorizontalAlignment(SwingConstants.CENTER);
		quantity.setBounds(1025, 290, 60, 60);
		add(quantity);
		
		// Alternative product data
		JLabel newProductLabel = new JLabel("Nuevo producto:");
		newProductLabel.setBounds(950, 375, 90, 20);
		add(newProductLabel);
		
		JLabel newPriceLabel = new JLabel("Nuevo precio:");
		newPriceLabel.setBounds(950, 400, 90, 20);
		add(newPriceLabel);
		
		JLabel productCommentLabel = new JLabel("Detalle:");
		productCommentLabel.setBounds(950, 425, 90, 20);
		add(productCommentLabel);
		
		newProductText = new JTextField();
		newProductText.setBounds(1050, 375, 350, 20);
		add(newProductText);
		newProductText.setColumns(10);
		
		newPriceText = new JFormattedTextField(new BigDecimal(0));
		newPriceText.setBounds(1050, 400, 350, 20);
		add(newPriceText);
		newPriceText.setValue(null);
		
		JScrollPane productCommentPane = new JScrollPane();
		productCommentPane.setBounds(1050, 425, 350, 50);
		add(productCommentPane);
		
		productComment = new JTextArea();
		productCommentPane.setViewportView(productComment);
	}
	
	private void buttons() {
		addProduct = new JButton("A\u00F1adir");
		addProduct.setFont(new Font("Tahoma", Font.PLAIN, 24));
		addProduct.setBounds(1183, 290, 215, 60);
		add(addProduct);
		
		addDiscount = new JButton("A\u00F1adir descuento");
		addDiscount.setFont(new Font("Tahoma", Font.PLAIN, 24));
		addDiscount.setBounds(950, 645, 215, 70);
		add(addDiscount);
		
		eliminateOrder = new JButton("");
		Image img = (new ImageIcon(Order_Manager_v.class.getResource("/images/thrash.png"))).getImage();
		Image newImg = img.getScaledInstance(70, 50, java.awt.Image.SCALE_SMOOTH);
		eliminateOrder.setIcon(new ImageIcon(newImg));
		eliminateOrder.setBounds(50, 50, 70, 50);
		add(eliminateOrder);
		
		ticket = new JButton("Sacar ticket");
		ticket.setFont(new Font("Tahoma", Font.PLAIN, 24));
		ticket.setBounds(1175, 645, 215, 70);
		add(ticket);
		
		finishOrder = new JButton("Finalizar pedido");
		finishOrder.setFont(new Font("Tahoma", Font.PLAIN, 24));
		finishOrder.setBounds(1175, 730, 215, 70);
		add(finishOrder);
		
		resetData = new JButton("Borrar");
		resetData.setBounds(950, 455, 90, 23);
		add(resetData);
	}
	
	private void payMethod() {
		JLabel lblFormaDePago = new JLabel("Forma de pago:");
		lblFormaDePago.setBounds(1065, 726, 100, 20);
		add(lblFormaDePago);
		
		paidCash = new JRadioButton("Efectivo");
		buttonGroup.add(paidCash);
		paidCash.setBounds(1065, 755, 100, 20);
		add(paidCash);
		
		paidCard = new JRadioButton("Tarjeta");
		buttonGroup.add(paidCard);
		paidCard.setBounds(1065, 778, 100, 20);
		add(paidCard);
	}
	
	private void awayElements() {
		JLabel phoneLabel = new JLabel("Tel\u00E9fono: ");
		phoneLabel.setBounds(130, 52, 53, 14);
		add(phoneLabel);
		
		phoneText = new JLabel("");
		phoneText.setBounds(194, 50, 100, 20);
		add(phoneText);
		
		JLabel addressLabel = new JLabel("Calle: ");
		addressLabel.setBounds(130, 85, 46, 14);
		add(addressLabel);
		
		addressBox = new JComboBox<Address>();
		addressBox.setBounds(164, 82, 416, 20);
		add(addressBox);
		
		addressComment = new JTextArea();
		addressComment.setEditable(false);
		addressComment.setText("");
		addressComment.setLineWrap(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(586, 50, 314, 49);
		scrollPane.setViewportView(addressComment);
		add(scrollPane);
		
		modifyAddress = new JButton("Modificar calle");
		modifyAddress.setBounds(304, 50, 130, 20);
		add(modifyAddress);
		
		addAddress = new JButton("Nueva calle");
		addAddress.setBounds(450, 49, 130, 20);
		add(addAddress);
	}
	
	private void localElements() {
		numTableText = new JLabel("");
		numTableText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		numTableText.setBounds(240, 50, 100, 50);
		add(numTableText);
		
		JLabel tableLabel = new JLabel("Mesa n\u00BA: ");
		tableLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tableLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableLabel.setBounds(130, 50, 100, 50);
		add(tableLabel);
	}
}
