package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;

import model.Product;

public class Product_Manager {
	public Main_Window main;
	public view.Product_Manager view;
	
	public Product_Manager(Main_Window main) {
		this.view = new view.Product_Manager();
		this.main = main;
		fillTable();
		tableListener();
	}
	
	public void fillTable() {
		view.productTable.modelo.setRowCount(0);
		
		for (int i = 1; i < 1000; i++) {
			Product p = Product.findByCode(i);
			
			if (p == null) {
				view.productTable.modelo.addRow(new Object[] {i, null, null, null, null, null});
			} else {
				view.productTable.modelo.addRow(new Object[] {p.code,
						p.product_name,
						p.category.category_name,
						p.price_local,
						p.price_away,
						p.findTimesOrdered()});
			}
		}
	}
	
	private void tableListener() {
		view.productTable.tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					openProductForm((Integer) view.productTable.getValueSelected(0));
				}
			}
		});
	}
	
	private void openProductForm(int code) {
		new Product_Form(this, Product.findByCode(code), code);
	}
}
