package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.LinkedList;

import model.Category;
import model.Order;
import model.Order_Line;
import model.Product;

public class Order_Manager {
	public view.Order_Manager view;
	public Order order;
	public String product_comment;
	public BigDecimal product_price;
	
	public Order_Manager(Order order) {
		this.order = order;
		this.product_comment = "";
		this.product_price = BigDecimal.valueOf(0);
		view = new view.Order_Manager(order.isLocal);
		fillData();
		createCommentListeners();
		createTableListeners();
		createProductListeners();
	}
	
	public void fillData() {
		if (order.isLocal) {
			view.tableText.setText(Integer.toString(order.num_table));
		} else {
			//TODO
		}
		
		view.commentText.setText(order.comment);
		view.dateText.setText(order.date.stringReloj());
		
		actPriceSection();
		fillTable();
		
		view.repaint();
		view.revalidate();
	}
	
	public void fillTable() {
		view.orderTable.modelo.setRowCount(0);
		for (Order_Line ol : order.lines) {
			view.orderTable.modelo.addRow(new Object[] {ol.product.product_id,
					ol.quantity,
					ol.product.code,
					ol.product.product_name,
					ol.price.multiply(BigDecimal.valueOf(ol.quantity)),
					"Ver detalles",
					"+",
					"-"});
		}
	}

	public void createCommentListeners() {
		view.commentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				commentWindow();
			}
		});
	}
	
	private void commentWindow() {
		new Order_Comment(this, this.order.comment);
	}
	
	private void createTableListeners() {
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
		case 4: //Detalles
			new Product_Comment(this, order.lines.get(row).comment, row);
			break;
			
		case 5: // + cantidad
			order.addProduct(order.lines.get(row), 1);
			actElements();
			break;
			
		case 6: // - cantidad
			order.removeProduct(order.lines.get(row), 1);
			actElements();
			break;
		}
	}
	
	// TODO consola
	
	private void actPriceSection() {
		BigDecimal price = order.total_amount.subtract(
				order.total_amount.multiply(
						BigDecimal.valueOf(order.discount)).divide(
								BigDecimal.valueOf(100)));
		view.totalText.setText(Float.toString(price.floatValue()));
		
		if (order.discount == 0) {
			view.discountText.setVisible(false);
			view.discountLabel.setVisible(false);
		} else {
			view.discountText.setText(Integer.toString(order.discount));
		}
	}
	
	private void createProductListeners() {
		LinkedList<Category> categories = Category.find();
		for (Category c : categories) {
			view.categoryBox.addItem(c);
		}
		
		if (view.categoryBox.getComponentCount() > 0) {
			LinkedList<Product> products = Product.findByCategory(((Category) view.categoryBox.getItemAt(0)).category_id);
			for (Product p : products)
				view.productBox.addItem(p);
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
				view.resetQuantity();
			}
		});
		
		view.productButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProduct();
			}
		});

		view.addComment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comment();
			}
		});
	}
	
	private void comment() {
		new Addig_Product_Comment(this, this.product_comment);
	}
	
	private void addProduct() {
		Order_Line ol = new Order_Line(this.order,
				((Product) view.productBox.getSelectedItem()),
				this.product_comment,
				Integer.parseInt(view.quantity.getText()),
				this.order.isLocal);
		
		this.product_comment = "";
		view.resetQuantity();
		
		this.order.addProduct(ol, ol.quantity);
		actElements();
	}
	
	private void actElements() {
		fillTable();
		actPriceSection();
	}
}
