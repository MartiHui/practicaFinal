package view;

import base_classes.Panel_Base;
import base_classes.Table;

import java.math.BigDecimal;

import javax.swing.JScrollPane;

public class Products_Viewer_v extends Panel_Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5819652298870661206L;
	public Table productTable;
	
	public Products_Viewer_v() {
		super();
		setLayout(null);
		
		table();
	}
	
	private void table() {
		JScrollPane productPane = new JScrollPane();
		productPane.setBounds(39, 37, 1065, 812);
		add(productPane);
		
		productTable = new Table(new String[] {"Código", "Nombre", "Categoría", "Precio local", "Precio domicilio", "Veces pedido"},
				new Class<?>[] {Integer.class, String.class, String.class, BigDecimal.class, BigDecimal.class, Integer.class},
				new Integer[] {150, 150, 150, 150, 150, 150},
				null,
				null,
				null);
		productTable.tabla.getTableHeader().setEnabled(false);;
		productPane.setViewportView(productTable.tabla);
	}
}
