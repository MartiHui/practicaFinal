package controller;

import model.Order;

public class Away_Order {
	public Order order;
	public view.Away_Order view;
	
	public Away_Order(Order order) {
		this.order = order;
		this.view = new view.Away_Order();
		fillHeader();
	}
	
	private void fillHeader() {
		view.phone.setText(order.client.phone_number);
		
		if (order.address_id == null) {
			
		} else {
			view.address.setText(order.client.last_address.toString());
			view.date.setText(order.client.last_order.stringFecha());
		}
	}

}
