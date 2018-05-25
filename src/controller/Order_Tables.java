package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import model.Order;

public class Order_Tables {
	public view.Order_Tables ot_view;
	public LinkedList<Order> localOrders;
	public LinkedList<Away_Order> awayOrders;
	
	public Order_Tables() {
		ot_view = new view.Order_Tables();
		localOrders = new LinkedList<>();
		awayOrders = new LinkedList<>();
		consoleController();
	}
	
	public void consoleController() {
		consoleControllerKeyPad();
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
		if (console.length() == 9) {
			
		} else if (console.length() > 0 && console.length() < 4) {
			
		}
	}
	
	public static Away_Order insideOf(LinkedList<Away_Order> orders, String identifier) {
		for (Away_Order o : orders) {
			if (identifier.equals(o.order.client.phone_number)) {
				return o;
			}
		}
		
		return null;
	}

}
