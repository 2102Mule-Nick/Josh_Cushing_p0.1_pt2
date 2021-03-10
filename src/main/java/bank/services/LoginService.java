package bank.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import bank.db.ConnectBox;
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
		
		// Try to get a connection.
		try {
			con = ConnectBox.getConnection();
		} catch (Exception e) {
			log.warning("Connection Failure");
			e.printStackTrace();
		}
		
		// The enormous query below returns the result set of all relevant information from the
		// three table joined on bank_user_id where condition is username and password.
		
		// I would like to make this a stored procedure but doubt I'll have time.
		String queryString = 
				"SELECT bank_user.bank_user_f_name, bank_user.bank_user_l_name, bank_user.bank_user_user_name, bank_user.bank_user_pass_word, bank_checking_account.ch_account_bal, " 
				+ "bank_saving_account.sv_account_bal FROM ((bank_user inner join bank_checking_account ON bank_user.bank_user_id = bank_checking_account.bank_user_id) " 
				+ "inner join bank_saving_account ON bank_checking_account.bank_user_id = bank_saving_account.bank_user_id) WHERE bank_user_user_name = ? AND bank_user_pass_word = ?";

		// Next we try to make it a prepped statement.
		try {
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, userName);
			stmt.setString(2, passWord);
			ResultSet results = stmt.executeQuery();

			// If there is a result that matches the query
			if (results.next()) {
				System.out.println("User authenticated. Welcome " + userName + "!");
				
				// Create a user object with the values retrieved.
				String firstName = results.getString("bank_user_f_name");
				String lastName = results.getString("bank_user_l_name");
				User user = new User(userName, passWord, firstName, lastName, 0.00, 0.00);
				// Note that the checkingBal and savingBal default is 0.
				
				// Instantiate an updateService object and send the user there.
				// Here amount and type are dummy variables to pass to updateService 
				double amount = 0.00;
				String type = "4";
				UpdateService updateService = new UpdateService();
				updateService.updateUser(user, amount, type, mainMenu);
				results.close();
				con.close();
			} else {
				// Send user back one menu to try again.
				System.out.println("Username or password are incorrect. Please try again.");
				loginMenu.displayLoginBox(mainMenu);
			}

		} catch (SQLException e) {
			log.warning("Query Failure!");
			e.printStackTrace();
		}
	}
}
