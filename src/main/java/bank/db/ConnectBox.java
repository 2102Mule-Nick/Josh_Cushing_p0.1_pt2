package bank.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectBox {
	private static ConnectBox connectBox = null;
	public static String URL;
	public static String USERNAME;
	public static String PASSWORD;
	
	private ConnectBox() {
		URL = "jdbc:postgresql://" + System.getenv("BANK_DB_URL") + ":5432/" + "postgres" + "?";
		USERNAME = System.getenv("BANK_DB_USERNAME");
		PASSWORD = System.getenv("BANK_DB_PASSWORD");
	}
	
	public static synchronized Connection getConnection() {
		if (connectBox == null) {
			connectBox = new ConnectBox();
		}
		return connectBox.createConnection();
	}

	public Connection createConnection() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load Driver");
		}

		try {
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Failed to connect to DB");
		}
		return null;
	}
}