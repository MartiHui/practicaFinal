package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import utils.Model_Base;

public class Address extends Model_Base {
	private static String table_name = "addresses";
	private static String[] columns = new String[] {
			"client_id",
			"address_name",
			"zone",
			"comment",
			"isActive"};
	private static char[] value_types = new char[] {
			'i',
			's',
			's',
			's',
			'i'};
	
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
	
	public static Address load(Integer id, Client client) {
		ResultSet rs = Model_Base.load(table_name, "address_id", id);
		Address a = null;
		
		try {
			if (rs != null) {
				a = new Address(id,
						client,
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
				new String[] {"address_name", "isActive"},
				new String[] {" LIKE ", " = "},
				new Object[] {"%"+value+"%", 1},
				new char[] {'s', 'i'});
		
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
				new String[] {"client_id", "isActive"},
				new String[] {" = ", " = "},
				new Object[] {client.client_id, 1},
				new char[] {'i', 'i'});
		
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
				comment,
				1};
		
		super.update(table_name, values, columns, value_types, "address_id", address_id);
	}
	
	public void delete() {
		Object[] values = new Object[] {
				client.client_id,
				address_name,
				zone,
				comment,
				0};
		
		super.update(table_name, values, columns, value_types, "address_id", address_id);
	}
	
	public String toString() {
		String s = this.address_name;
		if (this.zone != null) {
			s += " (" + this.zone + ")";
		}
		return s;
	}
	
	private static Client getClient(Integer id) {
		return Client.load(id);
	}

	public static boolean equals(Address a1, Address a2) {
		if (a1 == null || a2 == null) {
			return false;
		} else {
			return (a1.address_name.equals(a2.address_name));
		}
	}
}
