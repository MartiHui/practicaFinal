package controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import model.Order;

public class Order_Tables {
	public Main_Window main;
	public view.Order_Tables view;
	public LinkedList<Order> localOrders;
	public LinkedList<Order> awayOrders;
	
	public Order_Tables(Main_Window main) {
		this.main = main;
		view = new view.Order_Tables();
		localOrders = new LinkedList<>();
		awayOrders = new LinkedList<>();
		
		consoleControllerKeyPad();
		fillTables();
		tableListeners();
	}
	
	private void consoleControllerKeyPad() {
		view.keypad_c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(8);
			}
		});
		
		view.keypad_enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(10);
			}
		});
		
		view.keypad_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(48);
			}
		});
		
		view.keypad_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(49);
			}
		});
		
		view.keypad_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(50);
			}
		});
		
		view.keypad_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(51);
			}
		});
		
		view.keypad_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(52);
			}
		});
		
		view.keypad_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(53);
			}
		});
		
		view.keypad_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(54);
			}
		});
		
		view.keypad_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(55);
			}
		});
		
		view.keypad_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(56);
			}
		});
		
		view.keypad_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consoleEvent(57);
			}
		});
	}
	
	public void consoleEvent(int ascii) {
		String consoleContent = view.console.getText();
		
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
			view.console.setText(s);
		}
		
		if (ascii == 10) {
			manageOrder(consoleContent);
		}
	}
	
	private void addText(int ascii) {
		view.console.setText(view.console.getText() + Character.toString((char) ascii));
	}
	
	private void manageOrder(String console) {
		Order currentOrder;
		
		if (console.length() == 9) {
			currentOrder = insideAwayOrders(console);
			
			if (currentOrder == null) {
				currentOrder = new Order(console, null);
				awayOrders.add(currentOrder);
			}
			
			executeManageOrder(currentOrder);
		} else if (console.length() > 0 && console.length() < 4) {
			currentOrder = insideLocalOrders(console);
			
			if (currentOrder == null) {
				currentOrder = new Order(null, Integer.parseInt(console));
				localOrders.add(currentOrder);
			}
			
			executeManageOrder(currentOrder);
		}
	}
	
	private void executeManageOrder(Order order) {
		main.om_controller = new Order_Manager(order);
		main.view.remove(main.ot_controller.view);
		main.view.getContentPane().add(BorderLayout.CENTER, main.om_controller.view);
		main.view.repaint();
		main.view.revalidate();
		main.currentPanel = 2;
	}
	
	private void tableListeners() {
		view.localTable.tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					manageOrder((String) view.localTable.getValueSelected(0));
				}
			}
		});
		
		view.awayTable.tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					manageOrder((String) view.localTable.getValueSelected(0));
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
	
	private void fillTables() {
		for (Order o : localOrders) {
			view.localTable.modelo.addRow(
					new Object[] {Integer.toString(o.num_table), o.date.stringReloj(), o.total_amount});
		}
		
		for (Order o : awayOrders) {
			view.awayTable.modelo.addRow(
					new Object[] {o.client.phone_number, o.date.stringReloj(), o.total_amount});
		}
	}

}
