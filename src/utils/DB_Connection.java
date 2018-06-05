package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.jasypt.util.text.BasicTextEncryptor;

public class DB_Connection {
	public static String databaseName;
	public static String loginUser;
	public static String loginPassword;
	public static String hostAddress;
	public static String url;
	
	public static Connection con = null;
	
	public static void loadData() {
		Properties prop = new Properties();
		InputStream input = null;
		
		BasicTextEncryptor bse = new BasicTextEncryptor();
		bse.setPassword("pass");
		
		try {
			input = new FileInputStream("casa.properties");
			prop.load(input);
			
			databaseName = prop.getProperty("databaseName");
			loginUser = prop.getProperty("loginUser");
			loginPassword = bse.decrypt(prop.getProperty("loginPassword"));
			hostAddress = prop.getProperty("hostAddress");
			
			url = "jdbc:mysql://" + hostAddress + "/" + databaseName;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e2) {

				}
			}
		}
	}
	
	public static void saveData() {
		Properties prop = new Properties();
		OutputStream output = null;
		
		try {
			output = new FileOutputStream("connection.properties");
			
			BasicTextEncryptor textencrypt = new BasicTextEncryptor();
			textencrypt.setPassword("pass");
			
			prop.setProperty("databaseName", databaseName);
			prop.setProperty("loginUser", loginUser);
			prop.setProperty("loginPassword", textencrypt.encrypt(loginPassword));
			prop.setProperty("hostAddress", hostAddress);
			
			prop.store(output, "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e2) {

			}
		}
	}
	
	public static void connect() {
		try {
			if ((con == null) || (con.isClosed())) {
				if (url == null)
					loadData();
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection
						(url, loginUser, loginPassword);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void disconnect() {
		try {
			if (!con.isClosed()) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
