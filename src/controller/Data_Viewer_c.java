package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.LinkedList;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import base_classes.Table;
import model.Address;
import model.Category;
import model.Product;
import model.Client;
import model.Order;
import view.Data_Viewer_v;

public class Data_Viewer_c {
	public Main_Window_c main;
	public Data_Viewer_v view;
	
	private int currentTable;
	public Table productTable; // 0
	public Table categoryTable; // 1
	public Table clientTable; // 2
	public Table addressTable; // 3
	private boolean active;
	
	public Data_Viewer_c(Main_Window_c main) {
		this.view = new Data_Viewer_v();
		this.main = main;
		
		buttons();
		productsTable();
	}
	
	private void buttons() {
		view.productsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				productsTable();
			}
		});
		
		view.categoryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				categoriesTable();
			}
		});
		
		view.clientsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clientTable();
			}
		});
		
		view.addressButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addressTable();
			}
		});
	}
	
	public void productsTable() {
		currentTable = 0;
		view.searchLabel.setText("Nombre del producto: ");
		search();
		
		productTable = new Table(new String[] {"ID", "Código", "Nombre", "Categoría", "Precio local", "Precio domicilio", "Veces pedido"},
				new Class<?>[] {Integer.class, Integer.class, String.class, String.class, BigDecimal.class, BigDecimal.class, Integer.class},
				new Integer[] {0, 150, 150, 150, 150, 150, 150},
				null,
				null,
				null);
		productTable.tabla.getTableHeader().setEnabled(false);
		productTable.hideColumn(0);
		view.dataPane.setViewportView(productTable.tabla);
		
		// Fill table
		productTable.modelo.setRowCount(0);
		
		for (int i = 1; i < 1000; i++) {
			Product p = Product.findByCode(i);
			
			if (p == null) {
				productTable.modelo.addRow(new Object[] {null, i, null, null, null, null, null});
			} else {
				productTable.modelo.addRow(new Object[] {p.product_id, p.code,
						p.product_name,
						p.category==null?null:p.category.category_name,
						p.price_local,
						p.price_away,
						p.findTimesOrdered()});
			}
		}
		
		// Table listener
		productTable.tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					Integer id = null;
					try {
						id = ((Integer) productTable.getValueSelected(0));
					} catch (Exception e1) {}
					new Product_Form_c(Data_Viewer_c.this, Product.load(id), ((Integer) productTable.getValueSelected(1)));
				}
			}
		});
	}
	
	public void categoriesTable() {
		currentTable = 1;
		view.searchLabel.setText("Nombre de la categoría: ");
		search();
		
		categoryTable = new Table(new String[] {"ID", "Nombre", "Nº productos"},
				new Class<?>[] {Integer.class, String.class, Integer.class},
				new Integer[] {0, 400, 400},
				null,
				null,
				null);
		categoryTable.tabla.getTableHeader().setEnabled(false);
		categoryTable.hideColumn(0);
		view.dataPane.setViewportView(categoryTable.tabla);
		
		// Fill table
		categoryTable.modelo.setRowCount(0);
		
		for (int i = 1; i < 99; i++) {
			Category c = Category.load(i);
			
			if (c != null) {
				categoryTable.modelo.addRow(new Object[] {c.category_id, c.category_name, Product.findByCategory(c.category_id).size()});
			}
		}
		
		for (int i = 0; i < 99 - categoryTable.modelo.getRowCount(); i++) {
			categoryTable.modelo.addRow(new Object[] {null, null, null});
		}
		
		// Table listener
		categoryTable.tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					Integer id = null;
					try {
						id = ((Integer) categoryTable.getValueSelected(0));
					} catch (Exception e1) {}
					new Category_Form_c(Data_Viewer_c.this, Category.load(id));
				}
			}
		});
	}
	
	public void clientTable() {
		currentTable = 2;
		view.searchLabel.setText("Número de teléfono: ");
		search();
		
		clientTable = new Table(new String[] {"ID", "Teléfono",  "Último pedido", "Nº pedidos", "Total pedido"},
				new Class<?>[] {Integer.class, String.class, String.class, Integer.class, BigDecimal.class},
				new Integer[] {0, 300, 300, 300, 300},
				null,
				null,
				null);
		clientTable.hideColumn(0);
		view.dataPane.setViewportView(clientTable.tabla);
		
		// Fill table
		for (Client c : Client.find()) {
			LinkedList<Order> o = Order.findByClient(c);
			clientTable.modelo.addRow(new Object[] {c.client_id, c.phone_number, c.last_order==null?null:c.last_order.stringFecha(), o.size(), Order.ordersAddTotalDiscount(o)});
		}
		
		// Table listener
		clientTable.tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					Integer id = ((Integer) clientTable.getValueSelected(0));
					new Client_Form_c(Data_Viewer_c.this, Client.load(id));
				}
			}
		});
	}

	public void addressTable() {
		currentTable = 3;
		view.searchLabel.setText("Nombre de la calle: ");
		search();
		
		addressTable = new Table(new String[] {"ID", "Cliente", "Calle", "Zona"},
				new Class<?>[] {Integer.class, String.class, String.class, String.class},
				new Integer[] {0, 150, 150, 150},
				null,
				null,
				null);
		addressTable.hideColumn(0);
		view.dataPane.setViewportView(addressTable.tabla);
		
		// Fill table
		for (Address a : Address.findByName("")) {
			addressTable.modelo.addRow(new Object[] {a.address_id, a.client.phone_number, a.address_name, a.zone});
		}
		
		// Table listener
		addressTable.tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					Integer id = ((Integer) addressTable.getValueSelected(0));
					new Address_Form_c(Address.load(id), false);
					addressTable();
				}
			}
		});
	}
	
	private void search() {
		active = false;
		view.searchText.setText("");
		active = true;
		view.searchText.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (active) {
					updateTable();
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (active) {
					updateTable();
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				if (active) {
					updateTable();
				}
			}
		});
	}
	
	private void updateTable() {
		String s = view.searchText.getText();
		switch (currentTable) {
		case 0:
			productTable.modelo.setRowCount(0);
			if (s.length() == 0) {
				productsTable();
			} else {
				showProductSearch(s);
			}
			break;

		case 1:
			categoryTable.modelo.setRowCount(0);
			if (s.length() == 0) {
				categoriesTable();
			} else {
				showCategorySearch(s);
			}
			break;
			
		case 2:
			clientTable.modelo.setRowCount(0);
			if (s.length() == 0) {
				clientTable();
			} else {
				showClientSearch(s);
			}
			break;
			
		case 3:
			addressTable.modelo.setRowCount(0);
			if (s.length() == 0) {
				addressTable();
			} else {
				showAddressSearch(s);
			}
			break;
		}
	}
	
	private void showProductSearch(String s) {
		for (Product p : Product.findByName(s)) {
			productTable.modelo.addRow(new Object[] {p.product_id, p.code,
						p.product_name,
						p.category==null?null:p.category.category_name,
						p.price_local,
						p.price_away,
						p.findTimesOrdered()});
		}
	}
	
	private void showCategorySearch(String s) {
		for (Category c : Category.findByName(s)) {
			categoryTable.modelo.addRow(new Object[] {c.category_id, c.category_name, Product.findByCategory(c.category_id).size()});
		}
	}
	
	private void showClientSearch(String s) {
		for (Client c : Client.findBySimilarPhone(s)) {
			LinkedList<Order> o = Order.findByClient(c);
			clientTable.modelo.addRow(new Object[] {c.client_id, c.phone_number, c.last_order==null?null:c.last_order.stringFecha(), o.size(), Order.ordersAddTotalDiscount(o)});
		}
	}
	
	private void showAddressSearch(String s) {
		for (Address a : Address.findByName(s)) {
			addressTable.modelo.addRow(new Object[] {a.address_id, a.client.phone_number, a.address_name, a.zone});
		}
	}
}
