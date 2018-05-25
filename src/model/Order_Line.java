package model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Order_Line extends Model_Base {
	private static String table_name = "order_lines";
	private static String[] columns = new String[] {
			"order_id",
			"product_id",
			"price",
			"quantity",
			"comment"
			};
	private static char[] value_types = new char[] {
			'i',
			'i',
			'b',
			'i',
			's'
			};
	
	public int order_line_id;
	public Order order;
	public Product product;
	public BigDecimal price;
	public int quantity;
	public String comment;
	
	
	
	private Order_Line(int order_line_id, Order order, Product product, BigDecimal price,
			int quantity, String comment) {
		this.order_line_id = order_line_id;
		this.order = order;
		this.product = product;
		this.price = price;
		this.comment = comment;
		this.quantity = quantity;
	}

	public Order_Line(Order order, Product product, String comment, int quantity, boolean isLocal) {
		this.order = order;
		this.product = product;
		this.price = isLocal?product.price_local:product.price_away;
		this.comment = comment;
		this.quantity = quantity;
	}
	
	public Order_Line(Order order, Product product, String comment, int quantity, BigDecimal price) {
		this.order = order;
		this.product = product;
		this.price = price;
		this.comment = comment;
		this.quantity = quantity;
	}
	
	public static Order_Line load(Integer id) {
		if (id == null) {
			return null;
		}
		
		ResultSet rs = Model_Base.load(table_name, "order_line_id", id);
		Order_Line ol = null;
		
		try {
			if (rs != null) {
				ol = new Order_Line(id,
						getOrder(rs.getInt("order_id")),
						getProduct(rs.getInt("product_id")),
						BigDecimal.valueOf(rs.getFloat("price")),
						rs.getInt("quantity"),
						rs.getString("comment"));
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return ol;
	}
	
	public void insert() {
		Object[] values = new Object[] {
				order.order_id,
				product.product_id,
				price,
				quantity,
				comment
		};
		
		Model_Base.insert(table_name, values, columns, value_types);
	}
	
	public static LinkedList<Order_Line> find(int order_id) {
		LinkedList<Order_Line> lines = new LinkedList<>();
		ResultSet rs = Model_Base.find(table_name, false,
				new String[] {"order_id"},
				new String[] {"="},
				new Object[] {order_id},
				new char[] {'i'});
		
		if (rs != null) {
			try {
				do {
					lines.add(new Order_Line(rs.getInt("order_line_id"),
							getOrder(rs.getInt("order_id")),
							getProduct(rs.getInt("product_id")),
							BigDecimal.valueOf(rs.getFloat("price")),
							rs.getInt("quantity"),
							rs.getString("comment")));
				} while (rs.next());
			} catch (SQLException e) {
				System.err.println(e.getErrorCode() 
						+ " - " + e.getLocalizedMessage());
			}
		}
		
		return lines;
	}
	
	public static Order getOrder(Integer id) {
		return Order.load(id);
	}
	
	public static Product getProduct(Integer id) {
		return Product.load(id);
	}
	
}
