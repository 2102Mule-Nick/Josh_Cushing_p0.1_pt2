package bank.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import bank.db.ConnectBox;
import bank.menu.BankMenu;
import bank.menu.MainMenu;
import bank.menu.RegisterMenu;
import bank.pojo.User;

public class RegisterService {

	public void registerNewUser(String userName, String passWord, MainMenu mainMenu, RegisterMenu registerMenu) {
		Connection con = null;
		PreparedStatement stmt = null;
		BankMenu bankMenu = new BankMenu();

		try {
			con = ConnectBox.getConnection();
		} catch (Exception e1) {
			System.out.println("Connecction MISSING");
			e1.printStackTrace();
		}

		// The first query to check is whether or not the user entered exists.
		// If it exists, we want to stop it from being inserted into the database
		// as redundant data.

		String queryString = "SELECT user_name, pass_word FROM user_account where user_name = ? and pass_word = ?";

		try {
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, userName);
			stmt.setString(2, passWord);
			ResultSet results = stmt.executeQuery();

			// If there is a result that matches the query
			if (results.next()) {
				System.out.println("A user with that password and username exists already.");
				System.out.println("Please pick different credentials.");
				// registerMenu.displayRegisterBox(mainMenu);
				results.close();
				con.close();
			} else {
				// Create a user object with the values retrieved.
				System.out.println("That username and password are available!");
				// User user = new User(userName, passWord, 0.0, 0.0);

				results.close();

				String queryString2 = "INSERT INTO user_account(checking_bal, saving_bal, user_name, pass_word) VALUES (?, ?, ?, ?)";

				try {
					stmt = con.prepareStatement(queryString2);
					stmt.setDouble(1, 0.0);
					stmt.setDouble(2, 0.0);
					stmt.setString(3, userName);
					stmt.setString(4, passWord);
					
					stmt.execute();
					System.out.println("Welcome to your new account " + userName + "!");

					// Instantiate a user object and send it to bankMenu.
					User user = new User(userName, passWord, 0.0, 0.0);
					bankMenu.displayWithAcct(user, mainMenu);

				} catch (Exception e) {
					System.out.println("Second QueryString ERROR");
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.out.println("First QueryString ERROR.");
			e.printStackTrace();
		}
	}
}
