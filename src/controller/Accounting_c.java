package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;

import model.Order;

import java.util.Calendar;

import utils.Fecha;
import view.Accounting_v;

public class Accounting_c {
	public Main_Window_c main;
	public Accounting_v view;
	
	private Date dateFrom;
	private Date dateTo;
	
	boolean singleDay;
	LinkedList<Order> orders;
	
	public Accounting_c(Main_Window_c main) {
		this.view = new Accounting_v();
		this.main = main;
		
		this.dateFrom = new Date(Calendar.getInstance().getTime().getTime());
		this.dateTo = new Date(Calendar.getInstance().getTime().getTime());
		
		this.singleDay = false;
		this.orders = new LinkedList<>();
		
		button();
		buttonListener();
		date();
		table();
	}
	
	private void buttonListener() {
		view.singleDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				singleDay = !singleDay;
				button();
			}
		});
	}
	
	private void button() {
		if (singleDay) {
			view.singleDate.setText("Un día");
			view.dateTo.setVisible(false);
			view.toLabel.setVisible(false);
			view.fromLabel.setText("Día: ");
			view.warning.setVisible(false);
		} else {
			view.singleDate.setText("Varios días");
			view.dateTo.setVisible(true);
			view.toLabel.setVisible(true);
			view.fromLabel.setText("Desde: ");
		}
		view.dateTo.getModel().setDate(new Fecha().get(Fecha.YEAR), new Fecha().get(Fecha.MONTH), new Fecha().get(Fecha.DAY_OF_MONTH));
		table();
		warning();
	}
	
	private void date() {
		view.dateFrom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dateFrom = (Date) view.dateFrom.getModel().getValue();
				table();
				warning();
			}
		});
		
		view.dateTo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dateTo = (Date) view.dateTo.getModel().getValue();
				table();
				warning();
			}
		});
	}
	
	private void warning() {
		if (dateFrom.compareTo(dateTo) > 0 && view.dateTo.isVisible()) {
			view.warning.setVisible(true);
		} else {
			view.warning.setVisible(false);
		}
	}
	
	private void table() {
		view.ordersTable.modelo.setRowCount(0);
		
		Fecha fFrom = new Fecha();
		fFrom.setTime(dateFrom);
		Fecha tTo = new Fecha();
		tTo.setTime(dateTo);
		if (singleDay) {
			orders = Order.findByDate(fFrom, null);
		} else {
			orders = Order.findByDate(fFrom, tTo);
		}
		
		for (Order o : orders) {
			BigDecimal price = o.total_amount.subtract(
					o.total_amount.multiply(
							BigDecimal.valueOf(o.discount)).divide(
									BigDecimal.valueOf(100)));
			view.ordersTable.modelo.addRow(new Object[] {
					(o.num_table==null)?o.client.phone_number:Integer.toString(o.num_table),
					o.date.stringFechaReloj(),
					o.total_amount,
					o.discount,
					price});
		}
		
		info();
	}
	
	private void info() {
		view.numText.setText(Integer.toString(orders.size()));
		view.totalText.setText(Order.ordersAddTotal(orders).toString());
		view.discountText.setText(Order.ordersAddTotalDiscount(orders).toString());
	}

}
