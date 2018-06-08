package model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import base_classes.Model_Base;
import utils.DB_Connection;

public class Product extends Model_Base {
	private static String table_name = "products";
	private static String[] columns = new String[] {
			"category_id",
			"code",
			"product_name",
			"price_local",
			"price_away",
			"isActive"
			};
	private static char[] value_types = new char[] {
			'i',
			'i',
			's',
			'b',
			'b',
			'i'
			};
	
	public Integer product_id;
	public Category category;
	public Integer code;
	public String product_name;
	public BigDecimal price_local;
	public BigDecimal price_away;
	
	private Product(Integer product_id, Category category, Integer code, String product_name, BigDecimal price_local,
			BigDecimal price_away) {
		this.product_id = product_id;
		this.category = category;
		this.code = code;
		this.product_name = product_name;
		this.price_local = price_local;
		this.price_away = price_away;
	}
	
	public Product() {}
	
	public static Product load(Integer id) {
		ResultSet rs = Model_Base.load(table_name, "product_id", id);
		Product p = null;
		
		try {
			if (rs != null) {
				p = new Product(id,
						getCategory((Integer) rs.getObject("category_id")),
						rs.getInt("code"),
						rs.getString("product_name"),
						rs.getBigDecimal("price_local"),
						rs.getBigDecimal("price_away"));
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return p;
	}
	
	public static Product insert(Object[] values) {
		ResultSet rs = Model_Base.insert(table_name, values, columns, value_types);
		Product p = null;
		
		try {
			if (rs != null) {
				p = load(rs.getInt(1));
				p.substituteProduct();
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return p;
	}
	
	public Product insert() {
		Object[] values = new Object[] {
				this.category.category_id,
				this.code,
				this.product_name,
				this.price_local,
				this.price_away,
				1
		};
		ResultSet rs = Model_Base.insert(table_name, values, columns, value_types);
		Product p = null;
		
		try {
			if (rs != null) {
				p = load(rs.getInt(1));
				p.substituteProduct();
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return p;
	}
	
	public void update() {
		Object[] values = new Object[] {
				category.category_id,
				code,
				product_name,
				price_local,
				price_away,
				1
			};
		
		super.update(table_name, values, columns, value_types, "product_id", product_id);
		substituteProduct();
	}
	
	public void deactivate() {
		DB_Connection.connect();
		PreparedStatement pstm;
		
		String query = "UPDATE products SET isActive = 0 WHERE product_id = ?";
		
		try {
			pstm = DB_Connection.con.prepareStatement(query);
			pstm.setInt(1, this.product_id);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
	}
	
	private void substituteProduct() {
		DB_Connection.connect();
		PreparedStatement pstm;
		
		String query = "CALL substitute_product(?, ?)";
		
		try {
			pstm = DB_Connection.con.prepareStatement(query);
			pstm.setInt(1, this.product_id);
			pstm.setInt(2, this.code);
			
			pstm.executeQuery();
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
	}
	
	public static LinkedList<Product> findByName(String value) {
		LinkedList<Product> products = new LinkedList<Product>();
		ResultSet rs = Model_Base.find(table_name, false, 
				new String[] {"product_name", "isActive"}, 
				new String[] {" LIKE ", " = "},
				new Object[] {"%"+value+"%", 1},
				new char[] {'s', 'i'});
		
		if (rs != null) {
			try {
				do {
					products.add(new Product(
							rs.getInt("product_id"),
							getCategory((Integer) rs.getObject("category_id")),
							rs.getInt("code"),
							rs.getString("product_name"),
							rs.getBigDecimal("price_local"),
							rs.getBigDecimal("price_away")));
				} while (rs.next());
			} catch (SQLException e) {
				System.err.println(e.getErrorCode() 
						+ " - " + e.getLocalizedMessage());
			}
		}
		
		return products;
	}
	
	public static Product findByCode(int value) {
		Product p = null;
		ResultSet rs = Model_Base.find(table_name, false, 
				new String[] {"code", "isActive"}, 
				new String[] {" = ", " = "},
				new Object[] {value, 1},
				new char[] {'i', 'i'});
		
		if (rs != null) {
			try {
				p = new Product(
						rs.getInt("product_id"),
						getCategory((Integer) rs.getObject("category_id")),
						rs.getInt("code"),
						rs.getString("product_name"),
						rs.getBigDecimal("price_local"),
						rs.getBigDecimal("price_away"));
			} catch (SQLException e) {
				System.err.println(e.getErrorCode() 
						+ " - " + e.getLocalizedMessage());
			}
		}
		
		return p;
	}
	
	public static LinkedList<Product> findByCategory(Integer id) {
		LinkedList<Product> products = new LinkedList<Product>();
		ResultSet rs = Model_Base.find(table_name, false, 
				new String[] {"category_id", " isActive"}, 
				new String[] {" = ", " = "},
				new Object[] {id, 1},
				new char[] {'i', 'i'});
		
		if (rs != null) {
			try {
				do {
					products.add(new Product(
							rs.getInt("product_id"),
							getCategory((Integer) rs.getObject("category_id")),
							rs.getInt("code"),
							rs.getString("product_name"),
							rs.getBigDecimal("price_local"),
							rs.getBigDecimal("price_away")));
				} while (rs.next());
			} catch (SQLException e) {
				System.err.println(e.getErrorCode() 
						+ " - " + e.getLocalizedMessage());
			}
		}
		
		return products;
	}
	
	public int findTimesOrdered() {
		int times_ordered = 0;
		ResultSet rs = Model_Base.find(table_name, false,
				new String[] {"product_id"}, 
				new String[] {"="},
				new Object[] {this.product_id},
				new char[] {'i'});
		
		if (rs != null) {
			try {
				times_ordered = rs.getInt("times_ordered");
			} catch (SQLException e) {
				System.err.println(e.getErrorCode() 
						+ " - " + e.getLocalizedMessage());
			}
		}
		
		return times_ordered;
	}
	
	public static Category getCategory(Integer category_id) {
		if (category_id == null) {
			return null;
		} else {
			return Category.load(category_id);
		}
	}
	
	public String toString() {
		return this.product_name;
	}
}
