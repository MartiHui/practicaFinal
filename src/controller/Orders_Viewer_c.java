package controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import model.Order;
import view.Orders_Viewer_v;
import view.Comment_Viewer;
import view.Order_Manager_v;

public class Orders_Viewer_c {
	public Main_Window_c main;
	
	public Orders_Viewer_v view;
	
	public LinkedList<Order> localOrders;
	public LinkedList<Order> awayOrders;
	
	public Orders_Viewer_c(Main_Window_c main) {
		this.main = main;
		view = new view.Orders_Viewer_v();
		
		localOrders = new LinkedList<>();
		awayOrders = new LinkedList<>();
		
		keypad();
		fillTables();
		tables();
	}
	
	private void keypad() {
		view.keypad_c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				console(8);
			}
		});
		
		view.keypad_enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				console(10);
			}
		});
		
		view.keypad_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				console(48);
			}
		});
		
		view.keypad_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				console(49);
			}
		});
		
		view.keypad_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				console(50);
			}
		});
		
		view.keypad_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				console(51);
			}
		});
		
		view.keypad_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				console(52);
			}
		});
		
		view.keypad_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				console(53);
			}
		});
		
		view.keypad_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				console(54);
			}
		});
		
		view.keypad_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				console(55);
			}
		});
		
		view.keypad_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				console(56);
			}
		});
		
		view.keypad_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				console(57);
			}
		});
	}
	
	public void console(int ascii) {
		String consoleContent = view.console.getText();
		
		if (consoleContent.length() < 9) { // Mientras haya menos de 9 caracteres
			if (49 <= ascii && ascii <= 57) { // Si es un numero entre 1 y 9, inclusive, lo añadimos sin problemas
				addNumber(ascii);
			} else if (ascii == 48) {
				if (consoleContent.length() > 0) {
					addNumber(ascii);
				}
			}
		}
		
		if (ascii == 8 && consoleContent.length() > 0) { // Si han apretado el backspace y hay numeros
			String s = consoleContent;
			s = s.substring(0, s.length()-1);
			view.console.setText(s);
		}
		
		if (ascii == 10) {
			view.console.setText("");
			manageOrder(consoleContent);
		}
	}
	
	private void addNumber(int ascii) {
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
		main.orderManager = new Order_Manager_c(order, main);
		main.swapPanels(2, main.orderManager.view);
	}
	
	private void tables() {
		view.localTable.tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					if (view.localTable.tabla.getSelectedColumn() == 3) {
						String s = (String) view.localTable.getValueSelected(4);
						if (s != null && !s.equals("")) {
							new Comment_Viewer(s, main, 1);
						}
					} else {
						manageOrder((String) view.localTable.getValueSelected(0));
				
					}
				}
			}
		});
		
		view.awayTable.tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					if (view.awayTable.tabla.getSelectedColumn() == 3) {
						String s = (String) view.awayTable.getValueSelected(4);
						if (!(s == null || s.equals(""))) {
							new Comment_Viewer(s, main, 1);
						}
					} else {
						manageOrder((String) view.awayTable.getValueSelected(0));
				
					}
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
	
	public void fillTables() {
		view.localTable.modelo.setRowCount(0);
		view.awayTable.modelo.setRowCount(0);
		for (Order o : localOrders) {
			view.localTable.modelo.addRow(orderToTablerow(o, true));
		}
		
		for (Order o : awayOrders) {
			view.awayTable.modelo.addRow(orderToTablerow(o, false));
		}
	}
	
	private Object[] orderToTablerow(Order o, boolean isLocal) {
		Image img = (new ImageIcon(Order_Manager_v.class.getResource("/images/info.png"))).getImage();
		Image newImg = img.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		ImageIcon info = new ImageIcon(newImg);
		return new Object[] {isLocal?Integer.toString(o.num_table):o.client.phone_number,
			o.date.stringReloj(),
			o.getFinalPrice(),
			(o.comment==null||o.comment.equals(""))?null:info,
			o.comment,
			o.ticketOut};
	}

}
