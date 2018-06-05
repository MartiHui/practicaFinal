package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.LinkedList;

import model.Category;
import model.Product;
import view.Product_Form_v;

public class Product_Form_c {
	public Product_Form_v view;
	public Products_Viewer_c pm;
	public Product p;
	public int code;
	
	public Product_Form_c(Products_Viewer_c pm, Product p, int code) {
		this.pm = pm;
		this.p = p;
		this.code = code;
		
		this.view = new Product_Form_v(p);
		
		fillData();
		buttons();
		
		view.repaint();
		view.revalidate();
		view.setModal(true);
		view.setVisible(true);
	}
	
	private void fillData() {
		LinkedList<Category> categories = Category.find();
		for (Category c : categories) {
			view.categoryBox.addItem(c);
		}
		
		view.codeText.setText(Integer.toString(code));
		if (p != null) {
			view.nameText.setText(p.product_name);
			view.categoryBox.setSelectedIndex(p.category.category_id-1);
			view.localText.setText(p.price_local.toString());
			view.awayText.setText(p.price_away.toString());
		}
	}
	
	private void buttons() {
		view.save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modifyProduct();
				pm.fillTable();
				exit();
			}
		});
		
		view.cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
	}
	
	private void modifyProduct() {
		boolean insert;
		if (p==null || !p.product_name.equals(view.nameText)) {
			insert = true;
		} else {
			insert = false;
		}
		if (insert) {
			p = new Product();
		}
		
		p.code = code;
		p.product_name = view.nameText.getText();
		p.category = ((Category) view.categoryBox.getSelectedItem());
		try {
			p.price_local = BigDecimal.valueOf(Double.parseDouble(view.localText.getText()));
		} catch (NumberFormatException e) {}

		try {
			p.price_away = BigDecimal.valueOf(Double.parseDouble(view.awayText.getText()));
		} catch (NumberFormatException e) {}
		
		if (insert)	{
			p.insert();
		} else {
			p.update();
		}
		
	}
	
	private void exit() {
		view.setVisible(false);
		view.dispose();
	}
}
