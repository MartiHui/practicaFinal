package model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Zone extends Model_Base {
	private static String table_name = "zones";
	private static String[] columns = new String[] {
			"zone_name", 
			"postal_code",
			"inRange",
			"avg_time",
			"min_amoun",
			"tax"
			};
	private static char[] value_types = new char[] {
			's',
			's',
			'i',
			'i',
			'b',
			'b'
			};
	
	public int zone_id;
	public String zone_name;
	public String postal_code;
	public int inRange;
	public int avg_time;
	public BigDecimal min_amount;
	public BigDecimal tax;
	
	private Zone(int zone_id, String zone_name, String postal_code, int inRange, int avg_time, BigDecimal min_amount,
			BigDecimal tax) {
		super();
		this.zone_id = zone_id;
		this.zone_name = zone_name;
		this.postal_code = postal_code;
		this.inRange = inRange;
		this.avg_time = avg_time;
		this.min_amount = min_amount;
		this.tax = tax;
	}
	
	public static Zone load(Integer id) {
		if (id == null) {
			return null;
		}
		
		ResultSet rs = Model_Base.load(table_name, "product_id", id);
		Zone z = null;
		
		try {
			if (rs != null) {
				z = new Zone(id,
						rs.getString("zone_name"),
						rs.getString("postal_code"),
						rs.getInt("inRange"),
						rs.getInt("avg_time"),
						BigDecimal.valueOf(rs.getFloat("min_amount")),
						BigDecimal.valueOf(rs.getFloat("tax")));
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return z;
	}
	
	public static Zone insert(Object[] values) {
		ResultSet rs = Model_Base.insert(table_name, values, columns, value_types);
		Zone z = null;
		
		try {
			if (rs != null) {
				z = load(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return z;
	}
	
	public void update() {
		Object[] values = new Object[] {
				zone_name,
				postal_code,
				inRange,
				avg_time,
				min_amount,
				tax
			};
		
		super.update(table_name, values, columns, value_types, "zone_id", zone_id);
	}
	
	public static LinkedList<Zone> find(String value) {
		LinkedList<Zone> zones = new LinkedList<>();
		ResultSet rs = Model_Base.find(table_name, false,
				new String[] {"zone_name"},
				new String[] {"LIKE"},
				new Object[] {"%"+value+"%"},
				new char[] {'s'});
		
		if (rs != null) {
			try {
				do {
					zones.add(new Zone(
							rs.getInt("zone_id"),
							rs.getString("zone_name"),
							rs.getString("postal_code"),
							rs.getInt("inRange"),
							rs.getInt("avg_time"),
							BigDecimal.valueOf(rs.getFloat("min_amount")),
							BigDecimal.valueOf(rs.getFloat("tax"))));
				} while (rs.next());
			} catch (SQLException e) {
				System.err.println(e.getErrorCode() 
						+ " - " + e.getLocalizedMessage());
			}
		}
		
		return zones;
	}
	
	public void delete() {
		super.delete(table_name, "zone_id", zone_id);
	}
	
	public boolean isInRange() {
		return inRange == 1;
	}
	
	public boolean overMinAmount(BigDecimal total) {
		return total.floatValue() >= min_amount.floatValue();
	}
}
