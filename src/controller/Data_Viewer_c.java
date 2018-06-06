package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.LinkedList;

import base_classes.Table;
import model.Category;
import model.Product;
import model.Client;
import model.Order;
import view.Data_Viewer_v;

public class Data_Viewer_c {
	public Main_Window_c main;
	public Data_Viewer_v view;
	
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
	}
	
	public void productsTable() {
		// Create table
		Table productTable = new Table(new String[] {"Código", "Nombre", "Categoría", "Precio local", "Precio domicilio", "Veces pedido"},
				new Class<?>[] {Integer.class, String.class, String.class, BigDecimal.class, BigDecimal.class, Integer.class},
				new Integer[] {150, 150, 150, 150, 150, 150},
				null,
				null,
				null);
		productTable.tabla.getTableHeader().setEnabled(false);
		view.dataPane.setViewportView(productTable.tabla);
		
		// Fill table
		productTable.modelo.setRowCount(0);
		
		for (int i = 1; i < 1000; i++) {
			Product p = Product.findByCode(i);
			
			if (p == null) {
				productTable.modelo.addRow(new Object[] {i, null, null, null, null, null});
			} else {
				productTable.modelo.addRow(new Object[] {p.code,
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
					int code = ((Integer) productTable.getValueSelected(0));
					new Product_Form_c(Data_Viewer_c.this, Product.findByCode(code), code);
				}
			}
		});
	}
	
	public void categoriesTable() {
		//Create table
		Table categoryTable = new Table(new String[] {"ID", "Nombre", "Nº productos"},
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
		//Create table
		Table clientTable = new Table(new String[] {"Teléfono",  "Último pedido", "Nº pedidos", "Total pedido"},
				new Class<?>[] {String.class, String.class, Integer.class, BigDecimal.class},
				new Integer[] {300, 300, 300, 300},
				null,
				null,
				null);
		view.dataPane.setViewportView(clientTable.tabla);
		
		// Fill table
		for (Client c : Client.find()) {
			LinkedList<Order> o = Order.findByClient(c);
			clientTable.modelo.addRow(new Object[] {c.phone_number, c.last_order.stringFecha(), o.size(), Order.totalOrders(o)});
		}
		
		// Table listener
		clientTable.tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					String phone = ((String) clientTable.getValueSelected(0));
					new Client_Form_c(Data_Viewer_c.this, Client.findByPhone(phone));
				}
			}
		});
	}

	public void addressTable() {
		// Create table
		Table addressTable = new Table(new String[] {"ID", "Cliente", "Calle", "Detalles", "Zona"},
				new Class<?>[] {Integer.class, String.class, String.class, String.class, String.class},
				new Integer[] {150, 150, 150, 150, 150, 150},
				null,
				null,
				null);
	}
}
