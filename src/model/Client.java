package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import base_classes.Model_Base;
import utils.Fecha;

public class Client extends Model_Base {
	private static String table_name = "clients";
	private static String[] columns = new String[] {
			"last_address",
			"last_order",
			"phone_number",
			"comment"};
	private static char[] value_types = new char[] {
			'i',
			'd',
			's',
			's'};
	
	public int client_id;
	public Address last_address;
	public Fecha last_order;
	public String phone_number;
	public String comment;
	
	private Client(int client_id, Address last_address, Fecha last_order,
			String phone_number, String comment) {
		this.client_id = client_id;
		this.last_address = last_address;
		this.last_order = last_order;
		this.phone_number = phone_number;
		this.comment = comment;
	}
	
	public static Client load(Integer id) {
		ResultSet rs = Model_Base.load(table_name, "client_id", id);
		Client c = null;
		Fecha f = new Fecha();

		try {
			if (rs != null) {
				f.setTime(rs.getDate("last_order"));
				
				c = new Client(id,
						getAddress(rs.getInt("last_address")),
						f,
						rs.getString("phone_number"),
						rs.getString("comment"));
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return c;
	}
	
	public static Client insert(Object[] values) {
		ResultSet rs = Model_Base.insert(table_name, values, columns, value_types);
		Client c = null;
		
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
				(last_address==null)?null:last_address.address_id,
				new java.sql.Date(last_order.getTimeInMillis()),
				phone_number,
				comment};
		
		super.update(table_name, values, columns, value_types, "client_id", client_id);
	}
	
	public static Client find(String value) {
		Client c = null;
		Fecha f = new Fecha();
		ResultSet rs = Model_Base.find(table_name, false,
				new String[] {"phone_number"},
				new String[] {"LIKE"},
				new Object[] {value},
				new char[] {'s'});
		
		if (rs != null) {
			try {
				f.setTime(rs.getDate("last_order"));
				
				c = new Client(rs.getInt("client_id"),
						getAddress(rs.getInt("last_address")),
						f,
						rs.getString("phone_number"),
						rs.getString("comment"));
			} catch (SQLException e) {
				System.err.println(e.getErrorCode() 
						+ " - " + e.getLocalizedMessage());
			}
		}
		
		return c;
	}
	
	private static Address getAddress(int id) {
		return Address.load(id);
	}
	
}
