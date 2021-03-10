package bank.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import bank.db.ConnectBox;
import bank.menu.BankMenu;
import bank.menu.MainMenu;
import bank.menu.RegisterMenu;
import bank.pojo.User;

public class RegisterService {

	// Get a logger
	static Logger log = Logger.getLogger(RegisterService.class.getName());

	public void registerNewUser(String firstName, String lastName, String userName, String passWord, MainMenu mainMenu, RegisterMenu registerMenu) {
		// Set to null just to be safe.
		Connection con = null;
		ResultSet results = null;
		PreparedStatement stmt = null;
		String queryString = "";
		BankMenu bankMenu = new BankMenu();

		// Try to get a connection
		try {
			con = ConnectBox.getConnection();
		} catch (Exception e1) {
			System.out.println("Connecction MISSING");
			e1.printStackTrace();
		}

		// The query featured here checks for existing combinations of this username and
		// password
		queryString = "select get_user(?, ?)";

		// Try to prep a statement.
		try {
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, userName);
			stmt.setString(2, passWord);
			results = stmt.executeQuery();

			// If there is a result that matches the query
			System.out.println(results.next());
			if (results.next()) {
				System.out.println("A user with that password and username exists already.");
				System.out.println("Please pick different credentials.");
				registerMenu.displayRegisterBox(mainMenu);
				results.close();
				con.close();
			} else {
				// Alert the user.
				System.out.println("That username and password are available!");
				results.close();
			}
		} catch (SQLException e) {
			log.warning("get_user Function Failure");
			e.printStackTrace();
		}

		// This function inserts the user values given into the user table.
		String insertUserString = "select insert_new(?, ?, ?, ?)";

		// Try to prep a statement.
		try {
			stmt = con.prepareStatement(insertUserString);
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, userName);
			stmt.setString(4, passWord);
			stmt.execute();
		} catch (SQLException e) {
			log.warning("insert_new Function Failure");
			e.printStackTrace();
		}

		// Next we need to insert the checking values into the checking table (default
		// is always 0.00).
		String insertCheckingString = "select insert_new_ch(?, ?)";

		// Try to prep a statement.
		try {
			stmt = con.prepareStatement(insertCheckingString);
			stmt.setString(1, userName);
			stmt.setString(2, passWord);
			stmt.execute();
		} catch (SQLException e) {
			log.warning("insert_new_ch Function Failure");
			e.printStackTrace();
		}

		// Next we need to insert the checking values into the checking table (default
		// is always 0.00).
		String insertSavingString = "select insert_new_sv(?, ?)";

		// Try to prep a statement.
		try {
			stmt = con.prepareStatement(insertSavingString);
			stmt.setString(1, userName);
			stmt.setString(2, passWord);
			stmt.execute();
		} catch (SQLException e) {
			log.warning("insert_new_sv Function Failure");
			e.printStackTrace();
		}
				
		System.out.println("Welcome to your new account " + userName + "!");

		// Instantiate a user object and send it to bankMenu.
		User user = new User(userName, passWord, firstName, lastName, 0.0, 0.0);
		bankMenu.displayWithAcct(user, mainMenu);
	}
}