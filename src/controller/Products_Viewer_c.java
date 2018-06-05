package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Product;
import view.Products_Viewer_v;

public class Products_Viewer_c {
	public Main_Window_c main;
	public Products_Viewer_v view;
	
	public Products_Viewer_c(Main_Window_c main) {
		this.view = new Products_Viewer_v();
		this.main = main;
		
		fillTable();
		table();
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
	
	private void table() {
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
		new Product_Form_c(this, Product.findByCode(code), code);
	}
}
