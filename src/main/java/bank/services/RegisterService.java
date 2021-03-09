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

	public void registerNewUser(String firstName, String lastName, String userName, String passWord, MainMenu mainMenu, RegisterMenu registerMenu) {
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
		// If it exists, we want to stop it from being inserted into the database as
		// redundant data.

		String queryString = "SELECT bank_user_user_name, bank_user_pass_word FROM bank_user WHERE bank_user_user_name = ? and bank_user_pass_word = ?";

		try {
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, userName);
			stmt.setString(2, passWord);
			ResultSet results = stmt.executeQuery();

			// If there is a result that matches the query
			if (results.next()) {
				System.out.println("A user with that password and username exists already.");
				System.out.println("Please pick different credentials.");
				registerMenu.displayRegisterBox(mainMenu);
				results.close();
				con.close();
			} else {
				// Create a user object with the values retrieved.
				System.out.println("That username and password are available!");
				results.close();
			}
		} catch (SQLException e) {
			System.out.println("QueryString ERROR.");
			e.printStackTrace();
		}

		// Next we need to insert the user values given.
		String insertUserString = "INSERT INTO bank_user(bank_user_f_name, bank_user_l_name, bank_user_user_name, bank_user_pass_word) VALUES (?, ?, ?, ?)";
		try {
			stmt = con.prepareStatement(insertUserString);
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, userName);
			stmt.setString(4, passWord);
			stmt.execute();			
		} catch (SQLException e) {
			System.out.println("insertUserString ERROR");
			e.printStackTrace();
		}

		// Next we need to insert the checking values (default is always 0.00).
		String insertCheckingString = "INSERT INTO bank_checking_account(bank_user_id, ch_account_bal) VALUES ((SELECT bank_user_id FROM bank_user WHERE bank_user_user_name = ? AND bank_user_pass_word = ?), 0.00)";
		try {
			stmt = con.prepareStatement(insertCheckingString);
			stmt.setString(1, userName);
			stmt.setString(2, passWord);
			stmt.execute();
		} catch (SQLException e) {
			System.out.println("insertCheckingString ERROR");
			e.printStackTrace();
		}

		// Finally we need to insert the saving values (default is always 0.00).
		String insertSavingString = "INSERT INTO bank_saving_account(bank_user_id, sv_account_bal) VALUES ((SELECT bank_user_id FROM bank_user WHERE bank_user_user_name = ? AND bank_user_pass_word = ?), 0.00)";
		try {
			stmt = con.prepareStatement(insertSavingString);
			stmt.setString(1, userName);
			stmt.setString(2, passWord);
			stmt.execute();
			con.close();
		} catch (SQLException e) {
			System.out.println("insertSavingString ERROR");
			e.printStackTrace();
		}
		
		System.out.println("Welcome to your new account " + userName + "!");

		// Instantiate a user object and send it to bankMenu.
		User user = new User(userName, passWord, firstName, lastName, 0.0, 0.0);
		bankMenu.displayWithAcct(user, mainMenu);
	}
}