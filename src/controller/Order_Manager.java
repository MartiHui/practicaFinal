package controller;

import java.math.BigDecimal;

import model.Order;
import model.Order_Line;

public class Order_Manager {
	public view.Order_Manager view;
	public Order order;
	
	public Order_Manager(Order order) {
		this.order = order;
		view = new view.Order_Manager(order.isLocal);
		fillData();
	}
	
	public void fillData() {
		if (order.isLocal) {
			view.tableText.setText(Integer.toString(order.num_table));
		} else {
			//TODO
		}
		
		view.commentText.setText(order.comment);
		
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
		
		fillTable();
		
		view.repaint();
		view.revalidate();
	}
	
	public void fillTable() {
		for (Order_Line ol : order.lines) {
			view.orderTable.modelo.addRow(new Object[] {ol.product.product_id,
					ol.quantity,
					ol.product.code,
					ol.product.product_name,
					ol.price,
					"Ver detalles",
					"+",
					"-"});
		}
	}
}
