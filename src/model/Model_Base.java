package model;

import utils.DB_Connection;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Model_Base {
	
	public static ResultSet load(String table_name, String id_name, Integer id) {
		DB_Connection.connect();
		PreparedStatement pstm;
		ResultSet rs;
		
		String query = "SELECT * FROM " + table_name + " WHERE "
				+ id_name + " = ?";
		
		try {
			pstm = DB_Connection.con.prepareStatement(query);
			pstm.setInt(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				return rs;
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return null;
	}
	
	public static ResultSet insert(String table_name, Object[] values, String[] columns,
			char[] value_types) {
		DB_Connection.connect();
		PreparedStatement pstm;
		ResultSet rs;
		
		String query = formatInsertQuery(table_name, columns);
		
		try {
			pstm = DB_Connection.con.prepareStatement(query
					, PreparedStatement.RETURN_GENERATED_KEYS);
			fillPreparedStatement(pstm, values, value_types);
			
			pstm.executeUpdate();
			
			rs = pstm.getGeneratedKeys();
			
			if (rs.next()) {
				return rs;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return null;
	}
	
	private static String formatInsertQuery(String table_name, String[] columns) {
		int i;
		String query = "INSERT INTO " + table_name + "(";
		
		for (i = 0; i < columns.length; i++) {
			query += columns[i] + (i==columns.length-1?") ":", ");
		}
		
		query += "VALUES(";
		for (i = 0; i < columns.length; i++) {
			query += "?" + (i==columns.length-1?") ":", ");
		}
		
		return query;
	}
	
	public void update(String table_name, Object[] values, String[] columns,
			char[] value_types, String id_name, Integer id_value) {
		DB_Connection.connect();
		PreparedStatement pstm;
		
		String query = formatUpdateQuery(table_name, columns, id_name);
		
		try {
			pstm = DB_Connection.con.prepareStatement(query);
			fillPreparedStatement(pstm, values, value_types);
			pstm.setInt(columns.length+1, id_value);
			
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
	}
	
	private String formatUpdateQuery(String table_name, String[] columns, String id_name) {
		int i;
		String query = "UPDATE "  + table_name + " SET ";
		
		// El último valor de columns será el id del objeto a modificar
		for (i = 0; i < columns.length; i++) {
			query += columns[i] + " = ?" + (i==columns.length-1?" ":", ");
		}
		query += "WHERE " + id_name + " = ?";
		
		return query;
	}
	
	public static ResultSet find(String table_name, Boolean isDistinct, String[] columns, String[] comparision,
			Object[] values, char[] value_types) {
		DB_Connection.connect();
		PreparedStatement pstm;
		ResultSet rs ;
		
		String query = formatFindQuery(table_name, isDistinct, columns, comparision);
		
		try {
			pstm = DB_Connection.con.prepareStatement(query);
			fillPreparedStatement(pstm, values, value_types);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				return rs;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
		
		return null;
	}
	
	private static String formatFindQuery(String table_name, boolean isDistinct, String[] columns, String[] comparision) {
		String query = "SELECT " + (isDistinct?"DISTINCT":"") + " * FROM " + table_name + " WHERE ";
		
		for (int i = 0; i < columns.length; i++) {
			query += columns[i] + " " + comparision[i] + " ? " + (i==columns.length-1?" ":"AND ");
		}
		
		return query;
	}
	
	public void delete(String table_name, String column, Integer value) {
		DB_Connection.connect();
		PreparedStatement pstm;
		
		String query = "DELETE FROM " + table_name + " WHERE " + column + " = ?";
		
		try {
			pstm = DB_Connection.con.prepareStatement(query);
			pstm.setInt(1, value);
			
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
	}
	
	/*
	 * values_types:
	 * 		s -> String
	 * 		i -> Integer
	 * 		f -> Float
	 * 		b -> BigDecimal
	 * 		d -> Date
	 */
	public static void fillPreparedStatement(PreparedStatement pstm, Object[] values, char[] value_types) {
		try {
			for (int i = 0; i < values.length; i++) {
				if (values[i] == null) {
					switch (value_types[i]) {
					case 's':
						pstm.setNull(i+1, java.sql.Types.VARCHAR);
						break;
						
					case 'i':
						pstm.setNull(i+1, java.sql.Types.INTEGER);	
						break;
							
					case 'b':
					case 'f':
						pstm.setNull(i+1, java.sql.Types.FLOAT);
						break;
						
					case 'd':
						pstm.setNull(i+1, java.sql.Types.DATE);
						break;
					}
				} else {
					switch (value_types[i]) {
					case 's':
						pstm.setString(i+1, ((String) values[i]));
						break;
						
					case 'i':
						pstm.setInt(i+1, ((Integer) values[i]));	
						break;
									
					case 'f':
						pstm.setFloat(i+1, ((Float) values[i]));
						break;
						
					case 'b':
						pstm.setFloat(i+1, ((BigDecimal) values[i]).floatValue());
						break;
						
					case 'd':
						pstm.setDate(i+1, ((Date) values[i]));
						break;
					}
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getErrorCode() 
					+ " - " + e.getLocalizedMessage());
		}
	}

}
