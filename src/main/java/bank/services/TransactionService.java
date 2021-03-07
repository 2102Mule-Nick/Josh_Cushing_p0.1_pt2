package bank.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bank.db.ConnectBox;
import bank.menu.BankMenu;
import bank.menu.MainMenu;
import bank.pojo.User;

public class TransactionService {

	public void deposit(User user) {
		// TODO Auto-generated method stub
		
	}
	public void withdraw(User user) {
		// TODO Auto-generated method stub
		
	}
	public void inquire(User user, MainMenu mainMenu) {
		// TODO Auto-generated method stub
		Connection con = null;
		
		try {
			con = ConnectBox.getConnection();
		} catch (Exception e1) {
			System.out.println("Connecction MISSING");
			e1.printStackTrace();
		}
		
		PreparedStatement stmt = null;
		String queryString = "SELECT user_name, checking_bal, saving_bal FROM user_account where user_name = ? and pass_word = ? ";
		
		try {
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getPassWord());
			ResultSet results = stmt.executeQuery();

			if (results.next()) {
				
				Double checkingBal = results.getDouble("checking_bal");
				Double savingBal = results.getDouble("saving_bal");
				String userName = results.getString("user_name");
				System.out.println("User: " + userName);
				System.out.println("Current Checking Balance: $" + checkingBal);
				System.out.println("Current Checking Balance: $" + savingBal);
				BankMenu bankMenu = new BankMenu();
				bankMenu.displayWithAcct(user, mainMenu);
				results.close();
				con.close();
			} else {
				System.out.println("There was an account error");
				BankMenu bankMenu = new BankMenu();
				bankMenu.displayWithAcct(user, mainMenu);
			}

		} catch (SQLException e) {
			System.out.println("ERROR.");
			e.printStackTrace();
		}
	}
}