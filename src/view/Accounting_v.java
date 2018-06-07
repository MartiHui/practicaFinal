package view;

import java.util.Properties;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import base_classes.Panel_Base;
import base_classes.Table;
import utils.DateLabelFormatter;
import utils.Fecha;
import javax.swing.JLabel;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

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
		separator.setBounds(1096, 382, 320, 50);
		add(separator);
		
		JLabel numLabel = new JLabel("N\u00BA pedidos: ");
		numLabel.setBounds(1096, 474, 69, 14);
		add(numLabel);
		
		JLabel totalLabel = new JLabel("Total sin descuento: ");
		totalLabel.setBounds(1096, 526, 100, 14);
		add(totalLabel);
		
		JLabel discountLabel = new JLabel("Total con descuento: ");
		discountLabel.setBounds(1096, 589, 120, 14);
		add(discountLabel);
		
		numText = new JLabel("");
		numText.setBounds(1237, 474, 120, 14);
		add(numText);
		
		totalText = new JLabel("");
		totalText.setBounds(1237, 526, 120, 14);
		add(totalText);
		
		discountText = new JLabel("");
		discountText.setBounds(1237, 589, 120, 14);
		add(discountText);
	}

	private void date() {
		fromLabel = new JLabel("");
		fromLabel.setBounds(1078, 191, 46, 14);
		add(fromLabel);
		
		toLabel = new JLabel("Hasta: ");
		toLabel.setBounds(1296, 191, 46, 14);
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
		dateFrom.setBounds(1078,216,120,28);
		add(dateFrom);
		
		UtilDateModel modelTo = new UtilDateModel();
		modelTo.setDate(new Fecha().get(Fecha.YEAR), new Fecha().get(Fecha.MONTH), new Fecha().get(Fecha.DAY_OF_MONTH));
		modelTo.setSelected(true);
		
		JDatePanelImpl dateToPanel = new JDatePanelImpl(modelTo,p);
		dateTo = new JDatePickerImpl(dateToPanel, new DateLabelFormatter());
		dateTo.setBounds(1296,216,120,28);
		add(dateTo);
		
		singleDate = new JButton("");
		singleDate.setBounds(1078, 255, 123, 23);
		add(singleDate);
		
		warning = new JLabel("<html>La fecha final es<br />anterior a la inicial.</html>");
		warning.setHorizontalAlignment(SwingConstants.CENTER);
		warning.setBounds(1306, 248, 108, 30);
		warning.setVisible(false);
		add(warning);
	}
	
	private void table() {
		JScrollPane ordersScrollPane = new JScrollPane();
		ordersScrollPane.setBounds(43, 80, 1014, 770);
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
