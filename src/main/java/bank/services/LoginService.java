package bank.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import bank.db.ConnectBox;
import bank.menu.BankMenu;
import bank.menu.LoginMenu;
import bank.menu.MainMenu;
import bank.pojo.User;

public class LoginService {
	// Get a logger
	static Logger log = Logger.getLogger(LoginService.class.getName());

	public void logInUser(String userName, String passWord, MainMenu mainMenu, LoginMenu loginMenu) {
		// Set to null just to be safe.
		Connection con = null;
		
		PreparedStatement stmt = null;
		ResultSet results = null;

		// Set to 0 just to be safe.
		String userId = "0";
		
		// Set to "" just to be safe.
		String queryString = "";
		String userFn = "";
		String userLn = "";
		Double userCh = 0.00;
		Double userSv = 0.00;

		// Try to get a connection.
		try {
			con = ConnectBox.getConnection();
		} catch (Exception e) {
			log.warning("Connection Failure");
			e.printStackTrace();
		}

		// GET THE USER'S USER ID --------------------------------------------

		// This SQL query returns the userId of the entered password/username.
		queryString = "select get_user(?, ?)";

		// Try to make it a prepped statement.
		try {
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, userName);
			stmt.setString(2, passWord);
			results = stmt.executeQuery();
			if (results.next()) {
				userId = results.getString("get_user");
				System.out.println("User authenticated. Welcome " + userName + "!");
			} else {
				System.out.println("Username or password are incorrect. Please try again.");
				loginMenu.displayLoginBox(mainMenu);
			}
		} catch (SQLException e1) {
			log.warning("get_user Function Failure!");
			e1.printStackTrace();
		}
		if (!"0".equals(userId)) {
			
			// GET THE USER'S CHECKING BALANCE-----------------------------------

			// This SQL query returns the checking account balance of the userId.
			queryString = "select get_user_ch(?)";

			// Try to make it a prepped statement.
			try {
				stmt = con.prepareStatement(queryString);
				// SQL function takes an integer so convert to int.
				stmt.setInt(1, Integer.valueOf(userId));
				results = stmt.executeQuery();
				if (results.next()) {
					userCh = results.getDouble("get_user_ch");
				} else {
					log.warning("No Checking Results");
				}
			} catch (SQLException e1) {
				log.warning("get_user_ch Function Failure!");
				e1.printStackTrace();
			}

			// GET THE USER'S SAVING BALANCE-----------------------------------

			// This SQL query returns the saving account balance of the userId.
			queryString = "select get_user_sv(?)";

			// Try to make it a prepped statement.
			try {
				stmt = con.prepareStatement(queryString);
				// SQL function takes an integer so convert to int.
				stmt.setInt(1, Integer.valueOf(userId));
				results = stmt.executeQuery();
				if (results.next()) {
					userSv = results.getDouble("get_user_sv");
				} else {
					log.warning("Error: No Savings Results");
				}
			} catch (SQLException e1) {
				log.warning("get_user_sv Function Failure!");
				e1.printStackTrace();
			}

			// GET THE USER'S FIRST NAME--------------------------------------

			// This SQL query returns the first name of the userId.
			queryString = "select get_user_fn(?)";

			// Try to make it a prepped statement.
			try {
				stmt = con.prepareStatement(queryString);
				// SQL function takes an integer so convert to int.
				stmt.setInt(1, Integer.valueOf(userId));
				results = stmt.executeQuery();
				if (results.next()) {
					userFn = results.getString("get_user_fn");
				} else {
					log.warning("Error: No First Name Results");
				}
			} catch (SQLException e1) {
				log.warning("get_user_fn Function Failure!");
				e1.printStackTrace();
			}

			// GET THE USER'S LAST NAME--------------------------------------

			// This SQL query returns the first name of the userId.
			queryString = "select get_user_ln(?)";

			// Try to make it a prepped statement.
			try {
				stmt = con.prepareStatement(queryString);
				// SQL function takes an integer so convert to int.
				stmt.setInt(1, Integer.valueOf(userId));
				results = stmt.executeQuery();
				if (results.next()) {
					userLn = results.getString("get_user_ln");
					results.close();
					con.close();
				} else {
					log.warning("Error: No Last Name Results");
				}
			} catch (SQLException e1) {
				log.warning("get_user_ln Function Failure!");
				e1.printStackTrace();
			}
		}
		
		User user = new User(userName, passWord, userFn, userLn, userCh, userSv);
		// Finally, the user will be passed to the Bank Menu.
		BankMenu bankMenu = new BankMenu();
		bankMenu.displayWithAcct(user, mainMenu);
	}
}