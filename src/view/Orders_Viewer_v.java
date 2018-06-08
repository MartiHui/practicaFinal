package view;

import java.awt.Font;
import java.math.BigDecimal;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import base_classes.KeyPad;
import base_classes.Panel_Base;
import base_classes.Table;

public class Orders_Viewer_v extends Panel_Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4185438257464370072L;
	
	public JScrollPane localPane;
	public Table localTable;
	public JScrollPane awayPane;
	public Table awayTable;
	
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
	
	public Orders_Viewer_v() {
		super();
		tables();
		console();
		keypad();
		repaint();
		revalidate();
	}
	
	private void tables() {
		int width = 475;
		int height = 775;
		int initialX = 0;
		int initialY = 50;
		int separator = (1100 - 2*width)/3;
		int column = (width/4)-1;
		
		localPane = new JScrollPane();
		localPane.setBounds(initialX + separator, initialY, width, height);
		add(localPane);
		
		localTable = new Table(new String[] {"Mesa nº", "Hora", "Total", "Detalles", "comment"},
				new Class<?>[] {String.class, String.class, BigDecimal.class,  ImageIcon.class, String.class},
				new Integer[] {column, column, column, column, 0},
				null,
				null,
				null);
		localTable.alinear('c', 0);
		localTable.alinear('c', 1);
		localTable.alinear('c', 2);
		localTable.hideColumn(4);
		localPane.setViewportView(localTable.tabla);
		
		awayPane = new JScrollPane();
		awayPane.setBounds(initialX + width + 2*separator, initialY, width, height);
		add(awayPane);
		
		awayTable = new Table(new String[] {"Teléfono", "Hora", "Total", "Detalles", "comment"},
				new Class<?>[] {String.class, String.class, BigDecimal.class,  ImageIcon.class, String.class},
				new Integer[] {column, column, column, column},
				null,
				null,
				null);
		awayTable.alinear('c', 0);
		awayTable.alinear('c', 1);
		awayTable.alinear('c', 2);
		awayTable.hideColumn(4);
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
	
	private void console() {
		console = new JTextField();
		console.setEditable(false);
		console.setFont(new Font("Tahoma", Font.PLAIN, 30));
		console.setBounds(1140, 197, 177, 43);
		add(console);
	}
	
	private void keypad() {
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
}


