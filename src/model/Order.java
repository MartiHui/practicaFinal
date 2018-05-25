package model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import utils.Fecha;

public class Order extends Model_Base {
	private static String table_name = "orders";
	private static String[] columns = new String[] {
			"pay_method_id",
			"address_id",
			"num_table",
			"date",
			"total_amount",
			"discount",
			"comment"
			};
	private static char[] value_types = new char[] {
			'i',
			'i',
			'i',
			'd',
			'b',
			'i',
			's'
			};
	
	public int order_id;
	public Pay_Method pay_method_id;
	public Address address_id;
	public Integer num_table;
	public Fecha date;
	public BigDecimal total_amount;
	public int discount;
	public String comment;
	
	public Client client;
	public LinkedList<Order_Line> lines;
	public boolean isLocal;
	
	// Constructor para la base de datos
	private Order(int order_id, Pay_Method pay_method_id, Address address_id, Integer num_table, Fecha date,
			BigDecimal total_amount, int discount, String comment) {
		this.order_id = order_id;
		this.pay_method_id = pay_method_id;
		this.address_id = address_id;
		this.num_table = num_table;
		this.date = date;
		this.total_amount = total_amount;
		this.discount = discount;
		this.comment = comment;
		
		this.client = address_id.client_id;
	}
	
	// Constructor para el controlador
	public Order(String phone_number, Integer num_table, Fecha date) {
		this.num_table = num_table;
		this.date = date;
		this.total_amount = BigDecimal.valueOf(0);
		isLocal = num_table != null;
		this.lines = new LinkedList<>();
		
		if (phone_number != null) {
			this.client = Client.find(phone_number);
			this.address_id = client.last_address;
		}
	}
	
	public static Order load(Integer id) {
		if (id == null) {
			return null;
		}
		
		ResultSet rs = Model_Base.load(table_name, "order_id", id);
		Order o = null;
		Fecha f = new Fecha();
		
		try {
			if (rs != null) {
				f.setTime(rs.getDate("date"));
				
				o = new Order(id,
						getPayMethod((Integer) rs.getObject("pay_method_id")),
						getAddress((Integer) rs.getObject("address_id")),
						(Integer) rs.getObject("num_table"),
						f,
						BigDecimal.valueOf(rs.getFloat("total_amount")),
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
				pay_method_id.pay_method_id,
				address_id.address_details,
				num_table,
				new java.sql.Date(date.getTimeInMillis()),
				total_amount,
				discount,
				comment
		};
		
		Model_Base.insert(table_name, values, columns, value_types);
		
		for (Order_Line ol : lines) {
			ol.insert();
		}
	}
	
	public void addProduct(Order_Line product, int quantity) {
		if (lines.contains(product)) {
			lines.get(lines.indexOf(product)).quantity += quantity;
		} else {
			lines.add(product);
		}
		
		total_amount.add(product.price.multiply(BigDecimal.valueOf(quantity)));
	}
	
	public void removeProduct(Order_Line product, int quantity) {
		int i = lines.indexOf(product);
		Order_Line ol = lines.get(i);
		quantity = ol.quantity<quantity?ol.quantity:quantity;
		ol.quantity -= quantity;
		if (ol.quantity < 1) {
			lines.remove(ol);
		}
		
		total_amount.subtract(product.price.multiply(BigDecimal.valueOf(quantity)));
	}
	
	public void getOrderLines() {
		lines = Order_Line.find(order_id);
	}
	
	public static Pay_Method getPayMethod(Integer id) {
		return Pay_Method.load(id);
	}
	
	public static Address getAddress(Integer id) {
		return Address.load(id);
	}
}
