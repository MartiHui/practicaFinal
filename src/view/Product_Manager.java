package view;

import base_classes.Panel_Base;
import base_classes.Table;

import java.math.BigDecimal;

import javax.swing.JScrollPane;

public class Product_Manager extends Panel_Base{
	public Table productTable;
	
	public Product_Manager() {
		super();
		datetime.setBounds(1050, 80, 340, 30);
		date.setBounds(1050, 50, 340, 30);
		setLayout(null);
		
		createProductsTable();
	}
	
	private void createProductsTable() {
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
