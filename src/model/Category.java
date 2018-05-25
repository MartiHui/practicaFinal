package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Category extends Model_Base {
	private static String table_name = "categories";
	private static String[] columns = new String[] {
			"category_name",
			"isMenu"
			};
	private static char[] value_types = new char[] {
			's',
			'i'
			};
	
	public Integer category_id;
	public String category_name;
	public int isMenu;
	
	private Category(Integer category_id, String category_name, int isMenu) {
		this.category_id = category_id;
		this.category_name = category_name;
		this.isMenu = isMenu;
	}
	
	public static Category load(Integer id) {
		if (id == null) {
			return null;
		}
		
		ResultSet rs = Model_Base.load(table_name, "category_id", id);
		Category c = null;
		
		try {
			if (rs != null) {
				c = new Category(id, rs.getString("category_name"), rs.getInt("isMenu"));
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
	
	public void update() {
		Object[] values = new Object[] {
				this.category_name, 
				this.isMenu};
		
		super.update(table_name, values, columns, value_types, "category_id", category_id);
	}
	
	public void delete() {
		super.delete(table_name, "category_id", this.category_id);
	}
}
