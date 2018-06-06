package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import base_classes.Model_Base;

public class Address extends Model_Base {
	private static String table_name = "addresses";
	private static String[] columns = new String[] {
			"client_id",
			"address_name",
			"zone",
			"comment"};
	private static char[] value_types = new char[] {
			'i',
			's',
			's',
			's'};
	
	public int address_id;
	public Client client;
	public String address_name;
	public String zone;
	public String comment;
	
	private Address(int address_id, Client client, String address_name, String zone, String comment) {
		this.address_id = address_id;
		this.client = client;
		this.address_name = address_name;
		this.zone = zone;
		this.comment = comment;
	}

	public static Address load(Integer id) {
		ResultSet rs = Model_Base.load(table_name, "address_id", id);
		Address a = null;
		
		try {
			if (rs != null) {
				a = new Address(id,
						getClient(rs.getInt("client_id")),
						rs.getString("address_name"),
						rs.getString("zone"),
						rs.getString("comment"));
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return a;
	}
	
	public static Address insert(Object[] values) {
		ResultSet rs = Model_Base.insert(table_name, values, columns, value_types);
		Address a = null;
		
		try {
			if (rs != null) {
				a = load(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return a;
	}
	
	public static LinkedList<Address> findByName(String value) {
		LinkedList<Address> addresses = new LinkedList<>();
		ResultSet rs = Model_Base.find(table_name, true,
				new String[] {"address_name"},
				new String[] {" LIKE "},
				new Object[] {"%"+value+"%"},
				new char[] {'s'});
		
		if (rs != null) {
			try {
				do {
					addresses.add(new Address(
							rs.getInt("address_id"),
							getClient(rs.getInt("client_id")),
							rs.getString("address_name"),
							rs.getString("zone"),
							rs.getString("comment")));
				} while (rs.next());
			} catch (SQLException e) {
				System.err.println(e.getErrorCode() 
						+ " - " + e.getLocalizedMessage());
			}
		}
		
		return addresses;
	}
	
	public static LinkedList<Address> findByClient(Client client) {
		LinkedList<Address> addresses = new LinkedList<>();
		ResultSet rs = Model_Base.find(table_name, true,
				new String[] {"client_id"},
				new String[] {" = "},
				new Object[] {client.client_id},
				new char[] {'i'});
		
		if (rs != null) {
			try {
				do {
					addresses.add(new Address(
							rs.getInt("address_id"),
							getClient(rs.getInt("client_id")),
							rs.getString("address_name"),
							rs.getString("zone"),
							rs.getString("comment")));
				} while (rs.next());
			} catch (SQLException e) {
				System.err.println(e.getErrorCode() 
						+ " - " + e.getLocalizedMessage());
			}
		}
		
		return addresses;
	}
	
	public void update() {
		Object[] values = new Object[] {
				client.client_id,
				address_name,
				zone,
				comment};
		
		super.update(table_name, values, columns, value_types, "address_id", address_id);
	}
	
	public void delete() {
		try {
			super.delete(table_name, "address_id", this.address_id);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return this.address_name;
	}
	
	private static Client getClient(Integer id) {
		return Client.load(id);
	}

}
