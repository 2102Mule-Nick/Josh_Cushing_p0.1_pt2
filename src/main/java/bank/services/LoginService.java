package bank.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bank.db.ConnectBox;
import bank.menu.BankMenu;
import bank.menu.LoginMenu;
import bank.menu.MainMenu;
import bank.menu.RegisterMenu;
import bank.pojo.User;

public class LoginService {

	public void logInUser(String userName, String passWord, MainMenu mainMenu, LoginMenu loginMenu,
			RegisterMenu registerMenu) {

		Connection con = null;

		try {
			con = ConnectBox.getConnection();
		} catch (Exception e1) {
			System.out.println("Connecction MISSING");
			e1.printStackTrace();
		}

		PreparedStatement stmt = null;
		String queryString = "SELECT user_name, pass_word, checking_bal, saving_bal FROM user_account where user_name = ? and pass_word = ? ";

		try {
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, userName);
			stmt.setString(2, passWord);
			ResultSet results = stmt.executeQuery();

			if (results.next()) {
				System.out.println("User authenitcated. Welcome " + userName + "!");
				Double checkingBal = results.getDouble("checking_bal");
				Double savingBal = results.getDouble("saving_bal");
				User user = new User(userName, passWord, checkingBal, savingBal);
				BankMenu bankMenu = new BankMenu();
				bankMenu.displayWithAcct(user);
				results.close();
				con.close();
			} else {
				System.out.println("Username or password are incorrect. Please try again.");
				loginMenu.displayLoginBox(mainMenu, loginMenu, registerMenu);
			}

		} catch (SQLException e) {
			System.out.println("ERROR.");
			e.printStackTrace();
		}
	}

}
