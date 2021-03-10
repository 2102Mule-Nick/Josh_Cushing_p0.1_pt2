package bank.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectBox {
	// Get a logger
	static Logger log = Logger.getLogger(ConnectBox.class.getName());
	
	//Fields
	private static ConnectBox connectBox = null;
	public static String URL;
	public static String USERNAME;
	public static String PASSWORD;
	
	//BANK_DB_URL, BANK_DB_USERNAME, BANK_DB_PASSWORD stored in environment variables for security.
	private ConnectBox() {
		URL = "jdbc:postgresql://" + System.getenv("BANK_DB_URL") + ":5432/" + "postgres" + "?";
		USERNAME = System.getenv("BANK_DB_USERNAME");
		PASSWORD = System.getenv("BANK_DB_PASSWORD");
	}
	
	//Singleton method, calls createConnecction, returns connection.
	public static synchronized Connection getConnection() {
		if (connectBox == null) {
			connectBox = new ConnectBox();
		}
		return connectBox.createConnection();
	}

	//Creates connection
	public Connection createConnection() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			log.warning("Failed to load Driver");
		}

		try {
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			log.warning("Failed to connect to DB");
		}
		return null;
	}
}