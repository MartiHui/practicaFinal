package model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import base_classes.Model_Base;
import utils.Fecha;

public class Order extends Model_Base {
	private static String table_name = "orders";
	private static String[] columns = new String[] {
			"pay_method",
			"client_id",
			"address_id",
			"num_table",
			"date",
			"total_amount",
			"discount",
			"comment"
			};
	private static char[] value_types = new char[] {
			's',
			'i',
			'i',
			'i',
			'd',
			'b',
			'i',
			's'
			};
	
	public int order_id;
	public Pay_Method pay_method;
	public Client client;
	public Address address;
	public Integer num_table;
	public Fecha date;
	public BigDecimal total_amount;
	public int discount;
	public String comment;
	
	public LinkedList<Order_Line> lines;
	public boolean isLocal;
	public boolean ticketOut;
	
	// Constructor para la base de datos
	private Order(int order_id, Pay_Method pay_method, Client client, Address address,
			Integer num_table, Fecha date, BigDecimal total_amount, int discount,
			String comment) {
		this.order_id = order_id;
		this.pay_method = pay_method;
		this.client = client;
		this.address = address;
		this.num_table = num_table;
		this.date = date;
		this.total_amount = total_amount;
		this.discount = discount;
		this.comment = comment;
	}
	
	// Constructor para el controlador
	public Order(String phone_number, Integer num_table) {
		if (phone_number != null) {
			this.client = Client.find(phone_number);
			this.address = client.last_address;
		} else {
			this.client = null;
			this.address = null;
		}
		this.num_table = num_table;
		this.date = new Fecha();
		this.total_amount = BigDecimal.valueOf(0);
		this.discount = 0;
		this.pay_method = Pay_Method.CASH;

		this.lines = new LinkedList<>();
		isLocal = num_table != null;
		ticketOut = false;
	}
	
	public static Order load(Integer id) {
		ResultSet rs = Model_Base.load(table_name, "order_id", id);
		Order o = null;
		Fecha f = new Fecha();
		
		try {
			if (rs != null) {
				f.setTime(rs.getDate("date"));
				
				o = new Order(id,
						getPayMethod((String) rs.getObject("pay_method")),
						getClient((Integer) rs.getObject("client_id")),
						getAddress((Integer) rs.getObject("address_id")),
						(Integer) rs.getObject("num_table"),
						f,
						rs.getBigDecimal("total_amount"),
						rs.getInt("discount"),
						rs.getString("comment"));
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return o;
	}
	
	/*
	 * La clase Order no se insertara hasta que no se confirme totalmente el pedido,
	 * y una vez dentro de la base de datos no se podrá modificar para nada.
	 */
	public void insert() {
		Object[] values = new Object[] {
				setPayMethod(this.pay_method),
				(client==null)?null:client.client_id, // Para evitar NullPointerException
				(address==null)?null:address.address_details,
				num_table,
				new java.sql.Date(date.getTimeInMillis()),
				total_amount,
				discount,
				comment
		};
		
		ResultSet rs = Model_Base.insert(table_name, values, columns, value_types);
		
		try {
			if (rs != null) {
				this.order_id = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		for (Order_Line ol : lines) {
			ol.insert();
		}
	}
	
	public void addProduct(Order_Line product, int quantity) {
		if (lines.contains(product)) {
			lines.get(lines.indexOf(product)).quantity += quantity;
		} else {
			boolean found = false;
			for (Order_Line ol : this.lines) {
				if (Order_Line.isSame(product, ol)) {
					found = true;
					ol.quantity += quantity;
					break;
				}
			}
			if (!found) {
				lines.add(product);
				product.quantity = quantity;
			}
		}

		total_amount = total_amount.add(product.price.multiply(BigDecimal.valueOf(quantity)));
	}
	
	public void removeProduct(Order_Line product, int quantity) {
		int i = lines.indexOf(product);
		Order_Line ol = lines.get(i);
		quantity = ol.quantity<quantity?ol.quantity:quantity;
		ol.quantity -= quantity;
		if (ol.quantity < 1) {
			lines.remove(ol);
		}
		
		total_amount = total_amount.subtract(product.price.multiply(BigDecimal.valueOf(quantity)));
	}
	
	public void getOrderLines() {
		lines = Order_Line.find(order_id);
	}
	
	public static Pay_Method getPayMethod(String method) {
		switch (method) {
		case "cash":
			return Pay_Method.CASH;
			
		case "credit_card":
			return Pay_Method.CREDIT_CARD;
			
		case "criptocurrency":
			return Pay_Method.CRIPTOCURRENCY;
			
		case "paypal":
			return Pay_Method.PAYPAL;

		default:
			return null;
		}
	}
	
	public static String setPayMethod(Pay_Method p) {
		switch (p) {
		case CASH:
			return "cash";
			
		case CREDIT_CARD:
			return "credit_card";
			
		case CRIPTOCURRENCY:
			return "criptocurrency";
			
		case PAYPAL:
			return "paypal";

		default:
			return null;
		}
	}
	
	public static Client getClient(Integer id) {
		return Client.load(id);
	}
	
	public static Address getAddress(Integer id) {
		return Address.load(id);
	}
}
