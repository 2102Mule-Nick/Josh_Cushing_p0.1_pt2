package bank.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import bank.db.ConnectBox;
import bank.pojo.User;

public class RegisterService {

	public void registerNewUser(User user) {

		Connection con=null;
		try {
			con = ConnectBox.getConnection();
		} catch (Exception e1) {
			System.out.println("Connecction MISSING");
			e1.printStackTrace();
		}
		
		//Get all my values
		String userName = user.getUserName();
		String passWord = user.getPassWord();
		Double checkingBal = user.getCheckingBal();
		Double savingBal = user.getSavingBal();
		
		//Create a string to feed into a prepared statement.
		String sql = "insert into user_account (user_name, pass_word, checking_bal, saving_bal) values (?, ?, ?, ?)";
		PreparedStatement stmt = null;
		
		try {
			//Create a prepared statement.
			stmt = con.prepareStatement(sql);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		try {
			stmt.setString(1, userName);
			stmt.setString(2, passWord);
			stmt.setDouble(3, checkingBal);
			stmt.setDouble(4, savingBal);
			System.out.println(stmt);
			int i = stmt.executeUpdate();
			System.out.println(i + " records inserted");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
