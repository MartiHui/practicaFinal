package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import utils.KeyPad;
import utils.Panel_Base;
import utils.Table;

public class Order_Tables extends Panel_Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4185438257464370072L;
	public JLabel date;
	public JLabel datetime;
	public JTable table;
	public JTable table_1;
	public JScrollPane localPane;
	public Table localTable;
	public Table awayTable;
	public JScrollPane awayPane;
	public JTextField console;
	public KeyPad keypad_c;
	public KeyPad keypad_0;
	public KeyPad keypad_1;
	public KeyPad keypad_2;
	public KeyPad keypad_3;
	public KeyPad keypad_4;
	public KeyPad keypad_5;
	public KeyPad keypad_6;
	public KeyPad keypad_7;
	public KeyPad keypad_8;
	public KeyPad keypad_9;
	public KeyPad keypad_enter;
	
	public Order_Tables() {
		super();
		createElements();
		updateDate();
	}
	
	private void createElements() {
		createClock();
		createOrderTables();
		createConsole();
		createKeypad();
	}
	
	private void createClock() {
		int width = 340;
		int height = 30;
		int initialX = 1050;
		int initialY = 50;
		
		date = new JLabel("");
		date.setFont(new Font("Tahoma", Font.PLAIN, 20));
		date.setHorizontalAlignment(SwingConstants.CENTER);
		date.setBounds(initialX, initialY, width, height);
		add(date);
		
		datetime = new JLabel("");
		datetime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		datetime.setHorizontalAlignment(SwingConstants.CENTER);
		datetime.setBounds(initialX, initialY+height, width, height);
		add(datetime);
	}
	
	private void createOrderTables() {
		int width = 475;
		int height = 775;
		int initialX = 0;
		int initialY = 50;
		int separator = (1100 - 2*width)/3;
		int column = (width/3)-1;
		
		localPane = new JScrollPane();
		localPane.setBounds(initialX + separator, initialY, width, height);
		add(localPane);
		
		localTable = new Table(new String[] {"Mesa nº", "Hora", "Total"},
				new Class<?>[] {String.class, String.class, String.class},
				new Integer[] {column, column, column},
				null,
				null,
				null);
		localTable.alinear('c', 0);
		localTable.alinear('c', 1);
		localTable.alinear('c', 2);
		localPane.setViewportView(localTable.tabla);
		
		awayPane = new JScrollPane();
		awayPane.setBounds(initialX + width + 2*separator, initialY, width, height);
		add(awayPane);
		
		awayTable = new Table(new String[] {"Teléfono", "Hora", "Total"},
				new Class<?>[] {String.class, String.class, String.class},
				new Integer[] {column, column, column},
				null,
				null,
				null);
		awayTable.alinear('c', 0);
		awayTable.alinear('c', 1);
		awayTable.alinear('c', 2);
		awayPane.setViewportView(awayTable.tabla);
		
		height = initialY;
		initialX = separator;
		initialY = 0;
		int fontSize = 30;
		
		JLabel localTableLabel = new JLabel("LOCAL");
		localTableLabel.setFont(new Font("Tahoma", Font.BOLD, fontSize));
		localTableLabel.setHorizontalAlignment(SwingConstants.CENTER);
		localTableLabel.setBounds(initialX, initialY, width, height);
		add(localTableLabel);
		
		JLabel awayTableLabel = new JLabel("DOMICILIO");
		awayTableLabel.setFont(new Font("Tahoma", Font.BOLD, fontSize));
		awayTableLabel.setHorizontalAlignment(SwingConstants.CENTER);
		awayTableLabel.setBounds(initialX+width+separator, initialY, width, height);
		add(awayTableLabel);
	}
	
	private void createConsole() {
		console = new JTextField();
		console.setEditable(false);
		console.setFont(new Font("Tahoma", Font.PLAIN, 30));
		console.setBounds(1140, 197, 177, 43);
		add(console);
	}
	
	private void createKeypad() {
		int initialX = 1100;
		int initialY = 600;
		int jumpX = 100;
		int jumpY = -100;
		int num = 1;
		
		keypad_0 = new KeyPad(initialX, initialY - jumpY, "0");
		add(keypad_0);
		
		keypad_c = new KeyPad(initialX + jumpX, initialY - jumpY, "C");
		add(keypad_c);
		
		keypad_enter = new KeyPad(initialX + 2*jumpX, initialY - jumpY, "→");
		keypad_enter.setFont(new Font("Arial", Font.BOLD, 30));
		add(keypad_enter);
		
		keypad_1 = new KeyPad(initialX + ((num-1)%3)*jumpX, initialY + (((num-1)/3)%3)*jumpY, Integer.toString(num++));
		add(keypad_1);
		
		keypad_2 = new KeyPad(initialX + ((num-1)%3)*jumpX, initialY + (((num-1)/3)%3)*jumpY, Integer.toString(num++));
		add(keypad_2);
		
		keypad_3 = new KeyPad(initialX + ((num-1)%3)*jumpX, initialY + (((num-1)/3)%3)*jumpY, Integer.toString(num++));
		add(keypad_3);
		
		keypad_4 = new KeyPad(initialX + ((num-1)%3)*jumpX, initialY + (((num-1)/3)%3)*jumpY, Integer.toString(num++));
		add(keypad_4);
		
		keypad_5 = new KeyPad(initialX + ((num-1)%3)*jumpX, initialY + (((num-1)/3)%3)*jumpY, Integer.toString(num++));
		add(keypad_5);
		
		keypad_6 = new KeyPad(initialX + ((num-1)%3)*jumpX, initialY + (((num-1)/3)%3)*jumpY, Integer.toString(num++));
		add(keypad_6);
		
		keypad_7 = new KeyPad(initialX + ((num-1)%3)*jumpX, initialY + (((num-1)/3)%3)*jumpY, Integer.toString(num++));
		add(keypad_7);
		
		keypad_8 = new KeyPad(initialX + ((num-1)%3)*jumpX, initialY + (((num-1)/3)%3)*jumpY, Integer.toString(num++));
		add(keypad_8);
		
		keypad_9 = new KeyPad(initialX + ((num-1)%3)*jumpX, initialY + (((num-1)/3)%3)*jumpY, Integer.toString(num++));
		add(keypad_9);
	}
	
	private void updateDate() {
		int interval = 1000;
		new Timer(interval, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				date.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				datetime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			}
		}).start();
	}
}


