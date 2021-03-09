package bank.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bank.db.ConnectBox;
import bank.menu.LoginMenu;
import bank.menu.MainMenu;
import bank.pojo.User;

public class LoginService {

	public void logInUser(String userName, String passWord, MainMenu mainMenu, LoginMenu loginMenu) {

		Connection con = null;

		try {
			con = ConnectBox.getConnection();
		} catch (Exception e) {
			System.out.println("Connecction MISSING");
			e.printStackTrace();
		}
		
//The first query to send to the database checks if the username/password entered already exists.
		PreparedStatement stmt = null;
		//Be prepared. This is a long one.
		String queryString = 
				"SELECT bank_user.bank_user_f_name, bank_user.bank_user_l_name, bank_user.bank_user_user_name, bank_user.bank_user_pass_word, bank_checking_account.ch_account_bal, " 
				+ "bank_saving_account.sv_account_bal FROM ((bank_user inner join bank_checking_account ON bank_user.bank_user_id = bank_checking_account.bank_user_id) " 
				+ "inner join bank_saving_account ON bank_checking_account.bank_user_id = bank_saving_account.bank_user_id) WHERE bank_user_user_name = ? AND bank_user_pass_word = ?";

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

				// Instantiate an updateService object and send the user there.
				double amount = 0.00;
				String type = "3";
				UpdateService updateService = new UpdateService();
				updateService.updateUser(user, amount, type, mainMenu);
				results.close();
				con.close();
				
			} else {
				System.out.println("Username or password are incorrect. Please try again.");
				loginMenu.displayLoginBox(mainMenu);
			}

		} catch (SQLException e) {
			System.out.println("QueryString ERROR.");
			e.printStackTrace();
		}
	}
}
