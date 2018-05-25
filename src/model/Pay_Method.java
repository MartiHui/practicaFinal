package model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pay_Method extends Model_Base {
	private static String table_name = "pay_methods";
	private static String[] columns = new String[] {
			"pay_method_name", 
			"tax"
			};
	private static char[] value_types = new char[] {
			's',
			'b'
			};
	
	public int pay_method_id;
	public String pay_method_name;
	public BigDecimal tax;
	
	private Pay_Method(int pay_method_id, String pay_method_name, BigDecimal tax) {
		this.pay_method_id = pay_method_id;
		this.pay_method_name = pay_method_name;
		this.tax = tax;
	}
	
	public static Pay_Method load(Integer id) {
		if (id == null) {
			return null;
		}
		
		ResultSet rs = Model_Base.load(table_name, "pay_method_id", id);
		Pay_Method pm = null;
		
		try {
			if (rs != null) {
				pm = new Pay_Method(id,
						rs.getString("pay_method_name"),
						BigDecimal.valueOf(rs.getFloat("tax")));
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return pm;
	}
	
	public static Pay_Method insert(Object[] values) {
		ResultSet rs = Model_Base.insert(table_name, values, columns, value_types);
		Pay_Method pm = null;
		
		try {
			if (rs != null) {
				pm = load(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return pm;
	}
	
	public void update() {
		Object[] values = new Object[] {
				pay_method_name,
				tax
			};
		
		super.update(table_name, values, columns, value_types, "pay_method_id", pay_method_id);
	}
	
	public void delete() {
		super.delete(table_name, "pay_method_name", this.pay_method_id);
	}
}
