package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import base_classes.Model_Base;

public class Category extends Model_Base {
	private static String table_name = "categories";
	private static String[] columns = new String[] {
			"category_name"};
	private static char[] value_types = new char[] {
			's'};
	
	public Integer category_id;
	public String category_name;
	
	private Category(Integer category_id, String category_name) {
		this.category_id = category_id;
		this.category_name = category_name;
	}
	
	public static Category load(Integer id) {
		ResultSet rs = Model_Base.load(table_name, "category_id", id);
		Category c = null;
		
		try {
			if (rs != null) {
				c = new Category(id,
						rs.getString("category_name"));
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return c;
	}
	
	public static Category insert(Object[] values) {
		ResultSet rs = Model_Base.insert(table_name, values, columns, value_types);
		Category c = null;
		
		try {
			if (rs != null) {
				c = load(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return c;
	}
	
	public static LinkedList<Category> find() {
		LinkedList<Category> categories = new LinkedList<Category>();
		ResultSet rs = Model_Base.find(table_name, false, 
				new String[] {"1"}, 
				new String[] {" = "},
				new Object[] {1},
				new char[] {'i'});
		
		if (rs != null) {
			try {
				do {
					categories.add(new Category(
							rs.getInt("category_id"),
							rs.getString("category_name")));
				} while (rs.next());
			} catch (SQLException e) {
				System.err.println(e.getErrorCode() 
						+ " - " + e.getLocalizedMessage());
			}
		}
		
		return categories;
	}
	
	public static LinkedList<Category> findByName(String value) {
		LinkedList<Category> categories = new LinkedList<Category>();
		ResultSet rs = Model_Base.find(table_name, false, 
				new String[] {"category_name"}, 
				new String[] {" LIKE "},
				new Object[] {"%"+value+"%"},
				new char[] {'s'});
		
		if (rs != null) {
			try {
				do {
					categories.add(new Category(
							rs.getInt("category_id"),
							rs.getString("category_name")));
				} while (rs.next());
			} catch (SQLException e) {
				System.err.println(e.getErrorCode() 
						+ " - " + e.getLocalizedMessage());
			}
		}
		
		return categories;
	}
	
	public void update() {
		Object[] values = new Object[] {
				this.category_name};
		
		super.update(table_name, values, columns, value_types, "category_id", category_id);
	}
	
	public void delete() {
		try {
			super.delete(table_name, "category_id", this.category_id);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
			JOptionPane.showMessageDialog(null, "Esta categoría no se puede borrar.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return this.category_name;
	}
}
