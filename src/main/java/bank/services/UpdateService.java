package bank.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bank.db.ConnectBox;
import bank.menu.BankMenu;
import bank.menu.MainMenu;
import bank.pojo.User;

public class UpdateService {
	@SuppressWarnings("resource")
	public void updateUser(User user, double amount, String type, MainMenu mainMenu) {
		Connection con = null;
		ResultSet results = null;
		PreparedStatement stmt = null;
		BankMenu bankMenu = new BankMenu();
		double checkingBal = user.getCheckingBal();
		double savingBal = user.getSavingBal();
		boolean recordsFound = false;
		int id = 0;

		try {
			con = ConnectBox.getConnection();
		} catch (Exception e) {
			System.out.println("Connection Failure");
			e.printStackTrace();
		}

		// Be prepared. This string is a long one. It returns all data matching a
		// userName/passWord pair.
		String queryString = "SELECT bank_user.bank_user_id, bank_user.bank_user_f_name, bank_user.bank_user_l_name, bank_user.bank_user_user_name, bank_user.bank_user_pass_word, bank_checking_account.ch_account_bal, "
				+ "bank_saving_account.sv_account_bal FROM ((bank_user inner join bank_checking_account ON bank_user.bank_user_id = bank_checking_account.bank_user_id) "
				+ "inner join bank_saving_account ON bank_checking_account.bank_user_id = bank_saving_account.bank_user_id) WHERE bank_user_user_name = ? AND bank_user_pass_word = ?";

		try {
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getPassWord());
			results = stmt.executeQuery();
			if (results.next()) {
				recordsFound = true;
				id = results.getInt("bank_user_id");
				checkingBal = results.getDouble("ch_account_bal");
				savingBal = results.getDouble("sv_account_bal");
			}
		} catch (SQLException e) {
			System.out.println("QueryString ERROR.");
			e.printStackTrace();
		}

		if (recordsFound == true) {
			if ("1".equals(type)) {
				System.out.println("Your checking account balance was: $" + checkingBal);
				checkingBal += amount;

				String updateCheckString = "UPDATE bank_checking_account SET ch_account_bal = ? WHERE bank_user_id = ?";
				try {
					stmt = con.prepareStatement(updateCheckString);
					stmt.setDouble(1, checkingBal);
					stmt.setInt(2, Integer.valueOf(id));
					stmt.execute();
					user.setCheckingBal(checkingBal);
					System.out.println("It has been updated to: $" + checkingBal);
				} catch (SQLException e) {
					System.out.println("updateCheckString ERROR.");
					e.printStackTrace();
				}
			} else if ("2".equals(type)) {
				System.out.println("Your saving account balance was: $" + savingBal);
				savingBal += amount;

				String updateSaveString = "UPDATE bank_saving_account SET sv_account_bal = ? WHERE bank_user_id = ?";
				try {
					stmt = con.prepareStatement(updateSaveString);
					stmt.setDouble(1, savingBal);
					stmt.setInt(2, Integer.valueOf(id));
					stmt.execute();
					user.setSavingBal(savingBal);
					System.out.println("It has been updated to: $" + savingBal);
				} catch (SQLException e) {
					System.out.println("updateSaveString ERROR.");
					e.printStackTrace();
				}
			}else {
				user.setCheckingBal(checkingBal);
				user.setSavingBal(savingBal);
				System.out.println("Checking account: $" + checkingBal);
				System.out.println("Saving account: $" + savingBal);
			}
		}
		bankMenu.displayWithAcct(user, mainMenu);
	}
}