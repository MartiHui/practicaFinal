package controller;

import java.math.BigDecimal;

import model.Product;

public class Product_Manager {
	public Main_Window main;
	public view.Product_Manager view;
	
	public Product_Manager(Main_Window main) {
		this.main = main;
		fillTable();
	}
	
	private void fillTable() {
		view.productTable.modelo.setRowCount(0);
		
		for (int i = 1; i < 1000; i++) {
			Product p = Product.findByCode(i);
			
			if (p == null) {
				view.productTable.modelo.addRow(new Object[] {i, "", "", BigDecimal.valueOf(0), BigDecimal.valueOf(0), 0});
			} else {
				view.productTable.modelo.addRow(new Object[] {p.code,
						p.product_name,
						p.category.category_name,
						BigDecimal.valueOf(0),
						BigDecimal.valueOf(0),
						p.findTimesOrdered()});
			}
		}
	}
}
