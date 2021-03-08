package bank.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bank.db.ConnectBox;
import bank.menu.BankMenu;
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
		String queryString = "SELECT user_name, pass_word, checking_bal, saving_bal FROM user_account where user_name = ? and pass_word = ?";

		try {
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, userName);
			stmt.setString(2, passWord);
			ResultSet results = stmt.executeQuery();

			// If there is a result that matches the query
			if (results.next()) {
				System.out.println("User authenticated. Welcome " + userName + "!");

				// Create a user object with the values retrieved.
				Double checkingBal = results.getDouble("checking_bal");
				Double savingBal = results.getDouble("saving_bal");
				User user = new User(userName, passWord, checkingBal, savingBal);

				// Instantiate a bankMenu object and send the user there.
				BankMenu bankMenu = new BankMenu();
				bankMenu.displayWithAcct(user, mainMenu);
				results.close();
				con.close();
			} else {
				System.out.println("Username or password are incorrect. Please try again.");
				loginMenu.displayLoginBox(mainMenu);
			}

		} catch (SQLException e) {
			System.out.println("Query ERROR.");
			e.printStackTrace();
		}
	}
}
