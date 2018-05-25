package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Address extends Model_Base {
	private static String table_name = "addresses";
	private static String[] columns = new String[] {
			"client_id",
			"zone_id",
			"address_name",
			"address_details",
			"comment"
			};
	private static char[] value_types = new char[] {
			'i',
			'i',
			's',
			's',
			's'
			};
	
	public int address_id;
	public Client client_id;
	public Zone zone_id;
	public String address_name;
	public String address_details;
	public String comment;
	
	private Address(int address_id, Client client_id, Zone zone_id, String address_name, String address_details,
			String comment) {
		super();
		this.address_id = address_id;
		this.client_id = client_id;
		this.zone_id = zone_id;
		this.address_name = address_name;
		this.address_details = address_details;
		this.comment = comment;
	}

	public static Address load(Integer id) {
		if (id == null) {
			return null;
		}
		
		ResultSet rs = Model_Base.load(table_name, "address_id", id);
		Address a = null;
		
		try {
			if (rs != null) {
				a = new Address(id,
						getClient(rs.getInt("client_id")),
						getZone(rs.getInt("zone_id")),
						rs.getString("address_name"),
						rs.getString("address_details"),
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
	
	public static LinkedList<Address> find(String value) {
		LinkedList<Address> addresses = new LinkedList<>();
		ResultSet rs = Model_Base.find(table_name, true,
				new String[] {"address_name"},
				new String[] {"LIKE"},
				new Object[] {"%"+value+"%"},
				new char[] {'s'});
		
		if (rs != null) {
			try {
				do {
					addresses.add(new Address(
							rs.getInt("address_id"),
							getClient(rs.getInt("client_id")),
							getZone(rs.getInt("zone_id")),
							rs.getString("address_name"),
							rs.getString("address_details"),
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
				client_id.client_id,
				zone_id.zone_id,
				address_name,
				address_details,
				comment
			};
		
		super.update(table_name, values, columns, value_types, "address_id", address_id);
	}
	
	public static Client getClient(int id) {
		return Client.load(id);
	}
	
	public static Zone getZone(int id) {
		return Zone.load(id);
	}
	
	public String toString() {
		return this.address_name + " " + this.address_details;
	}

}
