package view;

import java.util.Properties;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import utils.DateLabelFormatter;
import utils.Fecha;
import utils.Panel_Base;
import utils.Table;

import javax.swing.JLabel;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Accounting_v extends Panel_Base {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7099846884686501190L;
	public JButton singleDate;
	public JDatePickerImpl dateFrom;
	public JDatePickerImpl dateTo;
	
	public Table ordersTable;
	
	public JLabel numText;
	public JLabel totalText;
	public JLabel discountText;
	public JLabel fromLabel;
	public JLabel toLabel;
	public JLabel warning;

	public Accounting_v() {
		super();
		setLayout(null);
		
		date();
		table();
		info();
	}
	
	private void info() {
		JSeparator separator = new JSeparator();
		separator.setBounds(1078, 329, 332, 14);
		add(separator);
		
		JLabel numLabel = new JLabel("N\u00BA pedidos: ");
		numLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		numLabel.setBounds(1078, 360, 131, 20);
		add(numLabel);
		
		JLabel totalLabel = new JLabel("Total sin descuento: ");
		totalLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		totalLabel.setBounds(1078, 410, 131, 20);
		add(totalLabel);
		
		JLabel discountLabel = new JLabel("Total con descuento: ");
		discountLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		discountLabel.setBounds(1078, 460, 131, 20);
		add(discountLabel);
		
		numText = new JLabel("");
		numText.setFont(new Font("Tahoma", Font.PLAIN, 13));
		numText.setBounds(1219, 360, 191, 20);
		add(numText);
		
		totalText = new JLabel("");
		totalText.setBounds(1219, 410, 191, 20);
		add(totalText);
		
		discountText = new JLabel("");
		discountText.setBounds(1219, 460, 191, 20);
		add(discountText);
	}

	private void date() {
		fromLabel = new JLabel("");
		fromLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fromLabel.setBounds(1078, 175, 150, 30);
		add(fromLabel);
		
		toLabel = new JLabel("Hasta: ");
		toLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		toLabel.setBounds(1260, 175, 150, 30);
		add(toLabel);
		
		Properties p = new Properties();
		p.put("text.today", "Hoy");
		p.put("text.month", "Mes");
		p.put("text.year", "Año");
		
		UtilDateModel modelFrom = new UtilDateModel();
		modelFrom.setDate(new Fecha().get(Fecha.YEAR), new Fecha().get(Fecha.MONTH), new Fecha().get(Fecha.DAY_OF_MONTH));
		modelFrom.setSelected(true);
		
		JDatePanelImpl dateFromPanel = new JDatePanelImpl(modelFrom,p);
		dateFrom = new JDatePickerImpl(dateFromPanel, new DateLabelFormatter());
		dateFrom.setBounds(1078,216,150,28);
		add(dateFrom);
		
		UtilDateModel modelTo = new UtilDateModel();
		modelTo.setDate(new Fecha().get(Fecha.YEAR), new Fecha().get(Fecha.MONTH), new Fecha().get(Fecha.DAY_OF_MONTH));
		modelTo.setSelected(true);
		
		JDatePanelImpl dateToPanel = new JDatePanelImpl(modelTo,p);
		dateTo = new JDatePickerImpl(dateToPanel, new DateLabelFormatter());
		dateTo.setBounds(1260,216,150,28);
		add(dateTo);
		
		singleDate = new JButton("");
		singleDate.setBounds(1078, 255, 150, 30);
		add(singleDate);
		
		warning = new JLabel("<html>La fecha final es<br />anterior a la inicial.</html>");
		warning.setHorizontalAlignment(SwingConstants.CENTER);
		warning.setBounds(1260, 255, 150, 30);
		warning.setVisible(false);
		add(warning);
	}
	
	private void table() {
		JScrollPane ordersScrollPane = new JScrollPane();
		ordersScrollPane.setBounds(40, 80, 1000, 750);
		add(ordersScrollPane);
		
		ordersTable = new Table(new String[] {"Mesa/Teléfono", "Fecha", "Total", "Descuento", "Precio final"},
				new Class<?>[] {String.class, String.class, BigDecimal.class, Integer.class, BigDecimal.class},
				new Integer[] {150, 150, 150, 150, 150},
				null,
				null,
				null); 
		ordersScrollPane.setViewportView(ordersTable.tabla);
	}
}
