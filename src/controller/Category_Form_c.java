package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Category;
import view.Category_Form_v;

public class Category_Form_c {
	public Category_Form_v view;
	public Data_Viewer_c dv;
	public Category c;
	
	public Category_Form_c(Data_Viewer_c dv, Category c) {
		this.dv = dv;
		this.c = c;
		
		this.view = new Category_Form_v(c);
		
		fillData();
		buttons();
		
		view.repaint();
		view.revalidate();
		view.setModal(true);
		view.setVisible(true);
	}
	
	private void fillData() {
		if (c != null) {
			view.nameText.setText(c.category_name);
		} else {
			view.delete.setEnabled(false);
		}
	}
	
	private void buttons() {
		view.save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modifyCategory();
				exit();
			}
		});
		
		view.cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		
		view.delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.delete();
				exit();
			}
		});
	}
	
	private void modifyCategory() {
		if (c == null) {
			Category.insert(new Object[] {view.nameText.getText()});
		} else {
			c.category_name = view.nameText.getText();
			c.update();
		}
	}
	
	private void exit() {
		dv.categoriesTable();
		view.setVisible(false);
		view.dispose();
	}

}
