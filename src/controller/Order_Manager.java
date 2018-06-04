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

import model.Category;
import model.Order;
import model.Order_Line;
import model.Product;
import view.Product_Comment;

public class Order_Manager {
	public Main_Window main;
	public view.Order_Manager view;
	public Order order;
	public String product_comment;
	public BigDecimal product_price;
	public boolean modifiedPrice;
	
	public Order_Manager(Order order, Main_Window main) {
		this.main = main;
		this.order = order;
		this.product_comment = "";
		this.product_price = BigDecimal.valueOf(0);
		modifiedPrice = false;
		view = new view.Order_Manager(order.isLocal);
		fillData();
		console();
		createCommentListeners();
		createTableListeners();
		createProductListeners();
		returnAndCancel();
		buttonsListeners();
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
					ol.price,
					ol.price.multiply(BigDecimal.valueOf(ol.quantity)).toString(),
					"Ver detalles", // TODO cambiar por simbolito
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
		main.currentPanel = -1;
		new Order_Comment(this, this.order.comment, main);
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
		case 5: //Detalles
			main.currentPanel = -1;
			new Product_Comment(order.lines.get(row).comment, main);
			break;
			
		case 6: // + cantidad
			order.addProduct(order.lines.get(row), 1);
			actElements();
			break;
			
		case 7: // - cantidad
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
		view.totalText.setText(price.toString());
		
		if (order.discount == 0) {
			view.discountText.setVisible(false);
			view.discountLabel.setVisible(false);
		} else {
			view.discountText.setVisible(true);
			view.discountLabel.setVisible(true);
			view.discountText.setText(Integer.toString(order.discount) + "%");
		}
	}
	
	private void createProductListeners() {
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
				newComment();
			}
		});
		
		view.addPrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newPrice();
			}
		});
	}
	
	private void addProduct() {
		Order_Line ol;
		
		if (modifiedPrice) {
			ol = new Order_Line(this.order,
					((Product) view.productBox.getSelectedItem()),
					this.product_comment,
					Integer.parseInt(view.quantity.getText()),
					this.product_price);
			modifiedPrice = false;
		} else {
			ol = new Order_Line(this.order,
					((Product) view.productBox.getSelectedItem()),
					this.product_comment,
					Integer.parseInt(view.quantity.getText()),
					this.order.isLocal);
		}
		
		view.console.setText("");
		this.product_comment = "";
		view.resetQuantity();
		
		this.order.addProduct(ol, ol.quantity);
		actElements();
	}
	
	public void newComment() {
		this.product_comment = view.console.getText();
		view.console.setText("");
	}
	
	public void newPrice() {
		try {
			if (Float.parseFloat(view.console.getText()) <= 0) {
				throw new Exception();
			}
			product_price = BigDecimal.valueOf(Float.parseFloat(view.console.getText())).setScale(2, RoundingMode.DOWN);
			modifiedPrice = true;
		} catch (Exception e) {}
		view.console.setText("");
	}
	
	private void actElements() {
		fillTable();
		actPriceSection();
	}
	
	public void consoleEvent(int ascii) {
		if (ascii == 10) {
			addProduct();
		}
	}
	
	public void returnAndCancel() {
		view.goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (order.lines.isEmpty()) {
					destroy();
				}
				exit();
			}
		});
		
		view.eliminate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				destroy();
				exit();
			}
		});
	}
	
	private void exit() {
		main.view.remove(main.om_controller.view);
		main.view.getContentPane().add(BorderLayout.CENTER, main.ot_controller.view);
		main.ot_controller.fillTables();
		main.view.repaint();
		main.view.revalidate();
		main.currentPanel = 1;
	}
	
	private void destroy() {
		if (order.isLocal) {
			main.ot_controller.localOrders.remove(order);
		} else {
			main.ot_controller.localOrders.remove(order);
		}
	}
	
	private void buttonsListeners() {
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
				actPriceSection();
			}
		});
		
		view.newProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewProduct();
			}
		});
		
		view.ticket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				order.ticketOut = true;
			}
		});
		
		view.orderEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertOrder();
			}
		});
	}
	
	private void insertOrder() {
		order.insert();
		destroy();
		exit();
	}
	
	private void createNewProduct() {
		String name = view.console.getText();
		Product p = Product.insert(new Object[] {
				null,
				0,
				name,
				product_price==null?BigDecimal.valueOf(0):product_price,
				product_price==null?BigDecimal.valueOf(0):product_price,
				0});
		
		Order_Line ol;
		ol = new Order_Line(this.order,
				p,
				this.product_comment,
				Integer.parseInt(view.quantity.getText()),
				this.product_price);
		modifiedPrice = false;
		
		view.console.setText("");
		this.product_comment = "";
		view.resetQuantity();
		
		this.order.addProduct(ol, ol.quantity);
		actElements();
	}
}
