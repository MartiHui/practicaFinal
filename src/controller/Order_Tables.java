package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.Timer;

import model.Order;

public class Order_Tables {
	public view.Order_Tables ot_view;
	public LinkedList<Order> localOrders;
	public LinkedList<Order> awayOrders;
	
	public Order_Tables() {
		ot_view = new view.Order_Tables();
		localOrders = new LinkedList<>();
		awayOrders = new LinkedList<>();
		
		consoleControllerKeyPad();
		updateDate();
		fillTables();
		tableListeners();
	}
	
	private void consoleControllerKeyPad() {
		ot_view.keypad_c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(8);
			}
		});
		
		ot_view.keypad_enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(10);
			}
		});
		
		ot_view.keypad_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(48);
			}
		});
		
		ot_view.keypad_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(49);
			}
		});
		
		ot_view.keypad_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(50);
			}
		});
		
		ot_view.keypad_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(51);
			}
		});
		
		ot_view.keypad_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(52);
			}
		});
		
		ot_view.keypad_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(53);
			}
		});
		
		ot_view.keypad_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(54);
			}
		});
		
		ot_view.keypad_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(55);
			}
		});
		
		ot_view.keypad_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(56);
			}
		});
		
		ot_view.keypad_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(57);
			}
		});
	}
	
	public void consoleEvent(int ascii) {
		String consoleContent = ot_view.console.getText();
		
		if (consoleContent.length() < 9) { // Mientras haya menos de 9 caracteres
			if (49 <= ascii && ascii <= 57) { // Si es un numero entre 1 y 9, inclusive, lo añadimos sin problemas
				addText(ascii);
			} else if (ascii == 48) {
				if (consoleContent.length() > 0) {
					addText(ascii);
				}
			}
		}
		
		if (ascii == 8 && consoleContent.length() > 0) { // Si han apretado el backspace y hay numeros
			String s = consoleContent;
			s = s.substring(0, s.length()-1);
			ot_view.console.setText(s);
		}
		
		if (ascii == 10) {
			manageOrder(consoleContent);
		}
	}
	
	private void addText(int ascii) {
		ot_view.console.setText(ot_view.console.getText() + Character.toString((char) ascii));
	}
	
	private void manageOrder(String console) {
		Order currentOrder;
		
		if (console.length() == 9) {
			currentOrder = insideAwayOrders(console);
			
			if (currentOrder == null) {
				currentOrder = new Order(console, null);
				awayOrders.add(currentOrder);
			}
			
			// TODO manage away order
		} else if (console.length() > 0 && console.length() < 4) {
			currentOrder = insideLocalOrders(console);
			
			if (currentOrder == null) {
				currentOrder = new Order(null, Integer.parseInt(console));
				localOrders.add(currentOrder);
			}
			
			// TODO manage local order
		}
	}
	
	private void tableListeners() {
		ot_view.localTable.tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					manageOrder((String) ot_view.localTable.getValueSelected(0));
				}
			}
		});
		
		ot_view.awayTable.tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					manageOrder((String) ot_view.localTable.getValueSelected(0));
				}
			}
		});
	}
	
	private Order insideAwayOrders(String console) {
		for (Order o : awayOrders) {
			if (console.equals(o.client.phone_number)) {
				return o;
			}
		}
		
		return null;
	}
	
	private Order insideLocalOrders(String console) {
		for (Order o : localOrders) {
			if (console.equals(Integer.toString(o.num_table))) {
				return o;
			}
		}
		
		return null;
	}
	
	private void updateDate() {
		int interval = 1000;
		new Timer(interval, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ot_view.date.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				ot_view.datetime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			}
		}).start();
	}
	
	private void fillTables() {
		for (Order o : localOrders) {
			ot_view.localTable.modelo.addRow(
					new Object[] {Integer.toString(o.num_table), o.date.stringReloj(), o.total_amount});
		}
		
		for (Order o : awayOrders) {
			ot_view.awayTable.modelo.addRow(
					new Object[] {o.client.phone_number, o.date.stringReloj(), o.total_amount});
		}
	}

}
