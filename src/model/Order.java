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
			"paidWithCash",
			"client_id",
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
			'i',
			't',
			'b',
			'i',
			's'
			};
	
	public int order_id;
	public int paidWithCash;
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
	private Order(int order_id, int paidWithCash, Client client, Address address,
			Integer num_table, Fecha date, BigDecimal total_amount, int discount,
			String comment) {
		this.order_id = order_id;
		this.paidWithCash = paidWithCash;
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
			this.client = Client.findByPhone(phone_number);
			if (this.client == null) {
				this.client = Client.insert(new Object[] {null, null, phone_number});
			}
			this.address = client.last_address;
		} else {
			this.client = null;
			this.address = null;
		}
		this.num_table = num_table;
		this.date = new Fecha();
		this.total_amount = BigDecimal.valueOf(0);
		this.discount = 0;
		this.paidWithCash = 1;

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
				f.setTime(rs.getTimestamp("date"));
				
				o = new Order(id,
						rs.getInt("paidWithCash"),
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
		
		System.out.println((new java.sql.Timestamp(date.getTimeInMillis())).toString());;
		Object[] values = new Object[] {
				paidWithCash,
				(client==null)?null:client.client_id, // Para evitar NullPointerException
				(address==null)?null:address.address_id,
				num_table,
				new java.sql.Timestamp(date.getTimeInMillis()),
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
	
	public static LinkedList<Order> findByClient(Client c) {
		LinkedList<Order> orders = new LinkedList<>();
		Fecha f = new Fecha();
		ResultSet rs = Model_Base.find(table_name, false, 
				new String[] {"client_id"}, 
				new String[] {" = "},
				new Object[] {c.client_id},
				new char[] {'i'});
		
		try {
			if (rs != null) {
				do {
					f.setTime(rs.getTimestamp("date"));
					orders.add(new Order(rs.getInt("order_id"), rs.getInt("paidWithCash"),
							getClient((Integer) rs.getObject("client_id")),
							getAddress((Integer) rs.getObject("address_id")), (Integer) rs.getObject("num_table"), f,
							rs.getBigDecimal("total_amount"), rs.getInt("discount"), rs.getString("comment")));
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return orders;
	}
	
	public static LinkedList<Order> findByDate(Fecha fromDate, Fecha toDate) {
		String[] col;
		String[] com;
		Object[] val;
		char[] dt;
		if (toDate == null) {
			col = new String[] {"DATE(date)"};
			com = new String[] {" = "};
			val = new Object[] {new java.sql.Date(fromDate.getTimeInMillis())};
			dt = new char[] {'d'};
		} else {
			col = new String[] {"DATE(date)", ""};
			com = new String[] {" BETWEEN ", ""};
			val = new Object[] {new java.sql.Date(fromDate.getTimeInMillis()), new java.sql.Date(toDate.getTimeInMillis())};
			dt = new char[] {'d', 'd'};
		}
		
		LinkedList<Order> orders = new LinkedList<>();
		Fecha f = new Fecha();
		ResultSet rs = Model_Base.find(table_name, false, col, com, val, dt);
		
		try {
			if (rs != null) {
				do {
					f.setTime(rs.getTimestamp("date"));
					orders.add(new Order(rs.getInt("order_id"), rs.getInt("paidWithCash"),
							getClient((Integer) rs.getObject("client_id")),
							getAddress((Integer) rs.getObject("address_id")), (Integer) rs.getObject("num_table"), f,
							rs.getBigDecimal("total_amount"), rs.getInt("discount"), rs.getString("comment")));
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return orders;
	}
	
	public void addProduct(Order_Line product) {
		boolean found = false;
		for (Order_Line ol : this.lines) {
			if (Order_Line.isSame(product, ol)) {
				found = true;
				ol.quantity += product.quantity;
				break;
			}
		}
		if (!found) {
			lines.add(product);
		}

		total_amount = total_amount.add(product.price.multiply(BigDecimal.valueOf(product.quantity)));
	}
	
	public void addProductThroughTable(Order_Line product) {
		product.quantity++;
		this.total_amount.add(product.price);
	}
	
	public void removeProduct(Order_Line product, int quantity) {
		int i = lines.indexOf(product);
		Order_Line ol = lines.get(i);
		quantity = ol.quantity<quantity?ol.quantity:quantity; // No podemos quitar más de lo que hay
		ol.quantity -= quantity;
		if (ol.quantity < 1) {
			lines.remove(ol);
		}
		
		total_amount = total_amount.subtract(product.price.multiply(BigDecimal.valueOf(quantity)));
	}
	
	public static BigDecimal ordersAddTotalDiscount(LinkedList<Order> os) {
		BigDecimal total = BigDecimal.valueOf(0);
		for (Order o : os) {
			total.add(o.getFinalPrice());
		}
		return total;
	}
	
	public static BigDecimal ordersAddTotal(LinkedList<Order> os) {
		BigDecimal total = BigDecimal.valueOf(0);
		for (Order o : os) {
			total.add(o.total_amount);
		}
		return total;
	}
	
	public BigDecimal getFinalPrice() {
		BigDecimal price = this.total_amount.subtract(
				this.total_amount.multiply(
						BigDecimal.valueOf(this.discount)).divide(
								BigDecimal.valueOf(100)));
		
		return price;
	}
	
	public void getOrderLines() {
		lines = Order_Line.find(order_id);
	}
	
	public static Client getClient(Integer id) {
		return Client.load(id);
	}
	
	public static Address getAddress(Integer id) {
		return Address.load(id);
	}
}
