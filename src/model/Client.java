package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import base_classes.Model_Base;
import utils.Fecha;

public class Client extends Model_Base {
	private static String table_name = "clients";
	private static String[] columns = new String[] {
			"last_address",
			"last_order",
			"phone_number"};
	private static char[] value_types = new char[] {
			'i',
			'd',
			's'};
	
	public int client_id;
	public Address last_address;
	public Fecha last_order;
	public String phone_number;
	
	private Client(int client_id, Address last_address, Fecha last_order,
			String phone_number) {
		this.client_id = client_id;
		this.last_address = last_address;
		this.last_order = last_order;
		this.phone_number = phone_number;
	}
	
	public static Client load(Integer id) {
		ResultSet rs = Model_Base.load(table_name, "client_id", id);
		Client c = null;
		Fecha f = null;

		try {
			if (rs != null) {
				Date d = rs.getDate("last_order");
				if (d != null) {
					f = new Fecha();
					f.setTime(d);
				} else {
					f = null;
				}
				
				c = new Client(rs.getInt("client_id"), null, f,
						rs.getString("phone_number"));
				c.last_address = c.getLastAddress(rs.getInt("last_address"));
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
				phone_number};
		
		super.update(table_name, values, columns, value_types, "client_id", client_id);
	}
	
	public static Client findByPhone(String value) {
		Client c = null;
		Fecha f = null;
		ResultSet rs = Model_Base.find(table_name, false,
				new String[] {"phone_number"},
				new String[] {" LIKE "},
				new Object[] {value},
				new char[] {'s'});
		
		if (rs != null) {
			try {
				Date d = rs.getDate("last_order");
				if (d != null) {
					f = new Fecha();
					f.setTime(d);
				} else {
					f = null;
				}
				
				c = new Client(rs.getInt("client_id"), null, f,
						rs.getString("phone_number"));
				c.last_address = c.getLastAddress(rs.getInt("last_address"));
			} catch (SQLException e) {
				System.err.println(e.getErrorCode() 
						+ " - " + e.getLocalizedMessage());
			}
		}
		
		return c;
	}
	
	public static LinkedList<Client> findBySimilarPhone(String value) {
		LinkedList<Client> clients = new LinkedList<>();
		Fecha f = null;
		ResultSet rs = Model_Base.find(table_name, false,
				new String[] {"phone_number"},
				new String[] {" LIKE "},
				new Object[] {"%"+value+"%"},
				new char[] {'s'});
		
		if (rs != null) {
			try {
				do {
					Date d = rs.getDate("last_order");
					if (d != null) {
						f = new Fecha();
						f.setTime(d);
					} else {
						f = null;
					}
					Client c = new Client(rs.getInt("client_id"), null, f,
							rs.getString("phone_number"));
					c.last_address = c.getLastAddress(rs.getInt("last_address"));
					clients.add(c);
				} while (rs.next());
			} catch (SQLException e) {
				System.err.println(e.getErrorCode() 
						+ " - " + e.getLocalizedMessage());
			}
		}
		
		return clients;
	}
	
	public static LinkedList<Client> find() {
		LinkedList<Client> clients = new LinkedList<>();
		Fecha f = null;
		ResultSet rs = Model_Base.find(table_name, false,
				new String[] {"1"}, 
				new String[] {" = "},
				new Object[] {1},
				new char[] {'i'});
		
		if (rs != null) {
			try {
				do {
					Date d = rs.getDate("last_order");
					if (d != null) {
						f = new Fecha();
						f.setTime(d);
					} else {
						f = null;
					}
					Client c = new Client(rs.getInt("client_id"), null, f,
							rs.getString("phone_number"));
					c.last_address = c.getLastAddress(rs.getInt("last_address"));
					clients.add(c);
				} while (rs.next());
			} catch (SQLException e) {
				System.err.println(e.getErrorCode() 
						+ " - " + e.getLocalizedMessage());
			}
		}
		
		return clients;
	}
	
	private Address getLastAddress(int id) {
		return Address.load(id, this);
	}
	
}
