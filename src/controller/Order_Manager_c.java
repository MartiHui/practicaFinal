package controller;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import model.Address;
import model.Category;
import model.Order;
import model.Order_Line;
import model.Product;
import view.Comment_Viewer;
import view.Order_Manager_v;

public class Order_Manager_c {
	public Main_Window_c main;
	public Order_Manager_v view;
	
	public Order order;
	
	public Order_Manager_c(Order order, Main_Window_c main) {
		this.main = main;
		this.order = order;
		
		view = new Order_Manager_v(order.isLocal);
		
		fillData();
		console();
		orderComment();
		productsTable();
		productSection();
		buttons();
		payMethod();
	}
	
	public void fillData() {
		if (order.isLocal) {
			view.numTableText.setText(Integer.toString(order.num_table));
		} else {
			awayElements();
			view.phoneText.setText(order.client.phone_number);
			updateAddressBox();
		}
		
		view.orderCommentText.setText(order.comment);
		
		updateOrderData();
		
		view.repaint();
		view.revalidate();
	}
	
	private void updateAddressBox() {
		LinkedList<Address> addresses = Address.findByClient(order.client);
		if (addresses.isEmpty()) {
			manageAddress(Address.insert(new Object[] {order.client.client_id, null, null, null, 1}), true);
			addresses = Address.findByClient(order.client);
		}
		Address la = order.client.last_address;
		view.addressBox.removeAllItems();
		for (Address a : addresses) {
			if (Address.equals(la, a)) {
				la = a;
			}
			view.addressBox.addItem(a);
		}
		if (la != null && addresses.contains(la)) {
			view.addressBox.setSelectedItem(la);
		}
		updateAddressComment();
	}
	
	private void updateAddressComment() {
		try {
			view.addressComment.setText(((Address) view.addressBox.getSelectedItem()).comment);
		} catch (Exception e) {
			view.addressComment.setText("");
		}
	}
	
	private void manageAddress(Address a, boolean newAddress) {
		new Address_Form_c(a, newAddress);
		updateAddressBox();
	}
	
	private void awayElements() {
		view.addressBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				order.address = ((Address) view.addressBox.getSelectedItem());
				updateAddressComment();
			}
		});
		
		view.modifyAddress.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAddress((Address) view.addressBox.getSelectedItem(), false);
			}
		});
		
		view.addAddress.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAddress(Address.insert(new Object[] {order.client.client_id, null, null, null, 1}), true);
			}
		});
	}
	
	public void fillProductsTable() {
		view.orderTable.modelo.setRowCount(0);
		for (Order_Line ol : order.lines) {
			ImageIcon info = new ImageIcon(getClass().getResource("../images/info.png"));
			view.orderTable.modelo.addRow(new Object[] {ol.product.product_id,
					ol.quantity,
					ol.product.code,
					ol.product.product_name,
					ol.price,
					ol.price.multiply(BigDecimal.valueOf(ol.quantity)).toString(),
					(ol.comment==null||ol.comment.equals(""))?null:info, 
					"+",
					"-"});
		}
	}

	private void productsTable() {
		view.orderTable.tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manageTable(view.orderTable.tabla.getSelectedRow(), 
						view.orderTable.tabla.getSelectedColumn());;
			}
		});
	}
	
	private void manageTable(int row, int column) {
		switch (column) {
		case 5: 
			if (view.orderTable.getValueAt(row, column) != null) {
				new Comment_Viewer(order.lines.get(row).comment, main, 2);
			}
			break;
			
		case 6:  
			order.addProductThroughTable(order.lines.get(row));
			updateOrderData();
			break;
			
		case 7: 
			order.removeProduct(order.lines.get(row), 1);
			updateOrderData();
			break;
		}
	}
	
	public void orderComment() {
		view.orderCommentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.currentPanel = -1;
				new Comment_Manager_c(Order_Manager_c.this, Order_Manager_c.this.order.comment, main);
			}
		});
	}
	
	private void console() {
		EventQueue.invokeLater(new Runnable() {
			@Override
		     public void run() {
		         view.console.grabFocus();
		         view.console.requestFocus();//or inWindow
		     }
		});
	}
	
	// TODO consola
	
	private void updatePriceSection() {
		view.totalText.setText(order.getFinalPrice().toString());
		
		if (order.discount == 0) {
			view.discountText.setVisible(false);
			view.discountLabel.setVisible(false);
		} else {
			view.discountText.setVisible(true);
			view.discountLabel.setVisible(true);
			view.discountText.setText(Integer.toString(order.discount) + "%");
		}
	}
	
	private void productSection() {
		try {
			LinkedList<Category> categories = Category.find();
			for (Category c : categories) {
				view.categoryBox.addItem(c);
			}
			
			if (view.categoryBox.getComponentCount() > 0) {
				LinkedList<Product> products = Product.findByCategory(((Category) view.categoryBox.getItemAt(0)).category_id);
				for (Product p : products)
					view.productBox.addItem(p);
			}
		} catch (Exception e1) {
			System.out.println("No hay productos ni categorias");
			e1.printStackTrace();
		}
		
		view.categoryBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Integer id = ((Category) view.categoryBox.getSelectedItem()).category_id;
				LinkedList<Product> products = Product.findByCategory(id);
				view.productBox.removeAllItems();
				for (Product p : products)
					view.productBox.addItem(p);
			}
		});
		
		view.plusQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = Integer.parseInt(view.quantity.getText());
				view.quantity.setText(Integer.toString(++i));
			}
		});
		
		view.minusQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = Integer.parseInt(view.quantity.getText());
				if (i > 1) {
					view.quantity.setText(Integer.toString(--i));
				}
			}
		});
		
		view.quantity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resetQuantity();
			}
		});
		
		view.newPriceText.setValue(null);
	}
	
	public void newProduct() {
		String s = view.console.getText();
		if (!stringEmpty(s)) {
			this.view.newProductText.setText(s);
		}
		view.console.setText("");
	}
	
	public void newComment() {
		String s = view.console.getText();
		if (!stringEmpty(s)) {
			this.view.productComment.setText(s);
		}
		view.console.setText("");
	}
	
	public void newPrice() {
		try {
			if (Float.parseFloat(view.console.getText()) <= 0) {
				throw new Exception();
			}
			this.view.newPriceText.setValue(BigDecimal.valueOf(Float.parseFloat(view.console.getText())).setScale(2, RoundingMode.DOWN));
		} catch (Exception e) {
			this.view.newPriceText.setValue(null);;
		}
		view.console.setText("");
	}
	
	private void resetAlternativeData() {
		view.newProductText.setText("");
		view.newPriceText.setValue(null);
		view.productComment.setText("");
	}
	
	private void buttons() {
		view.addProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProduct();
			}
		});
		
		view.addDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int num = Integer.parseInt(view.console.getText());
					if (num < 0) {
						throw new Exception();
					}
					order.discount = num;
				} catch (Exception ex) {}
				view.console.setText("");
				updatePriceSection();
			}
		});
		
		view.eliminateOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				destroy();
				exit();
			}
		});
		
		view.ticket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				order.ticketOut = true;
			}
		});
		
		view.finishOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertOrder();
			}
		});
	}
	
	public void payMethod() {
		view.paidCash.setSelected(true);
		view.paidCash.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				order.paidWithCash = 1;
				System.out.println(order.paidWithCash);
			}
		});
		
		view.paidCard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				order.paidWithCash = 0;
				System.out.println(order.paidWithCash);
			}
		});;
	}
	
	public void exit() {
		main.view.remove(main.orderManager.view);
		main.view.getContentPane().add(BorderLayout.CENTER, main.ordersViewer.view);
		main.ordersViewer.fillTables();
		main.view.repaint();
		main.view.revalidate();
		main.currentPanel = 1;
	}
	
	private void destroy() {
		if (order.isLocal) {
			main.ordersViewer.localOrders.remove(order);
		} else {
			main.ordersViewer.awayOrders.remove(order);
		}
	}
	
	private void insertOrder() {
		order.insert();
		destroy();
		exit();
	}
	
	private void addProduct() {
		Order_Line ol;
		Product product;
		String comment = "";
		BigDecimal price = (BigDecimal) view.newPriceText.getValue();
		int quantity;
		
		
		if (!stringEmpty(view.newProductText.getText())) {
			product = Product.insert(new Object[] {
					1, 0, view.newProductText.getText(),
					BigDecimal.valueOf(0),
					BigDecimal.valueOf(0), 0});
		} else {
			product = ((Product) view.productBox.getSelectedItem());
		}
		
		if (stringEmpty(view.console.getText())) {
			quantity = Integer.parseInt(view.quantity.getText());
		} else {
			try {
				quantity = Integer.parseInt(view.console.getText());
			} catch (Exception e) {
				quantity = Integer.parseInt(view.quantity.getText());
			}
		}
		
		if (!stringEmpty(view.productComment.getText())) {
			comment = view.productComment.getText();
		}
		
		if (price == null) {
			ol = new Order_Line(this.order, product, comment,
					quantity, this.order.isLocal);
		} else {
			ol = new Order_Line(this.order, product, comment,
					quantity, price);
		}
		
		view.console.setText("");
		resetAlternativeData();
		
		this.order.addProduct(ol);
		updateOrderData();
	}
	
	private void updateOrderData() {
		fillProductsTable();
		updatePriceSection();
	}
	
	public void consoleEvent(int ascii) {
		if (ascii == 10) {
			addProduct();
		}
	}
	
	public void resetQuantity() {
		view.quantity.setText("1");
	}
	
	private boolean stringEmpty(String s) {
		return (s == null || s.equals(""));
	}
}
