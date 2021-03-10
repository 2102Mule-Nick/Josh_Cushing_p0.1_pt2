package bank.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import bank.db.ConnectBox;
import bank.menu.BankMenu;
import bank.menu.MainMenu;
import bank.pojo.User;

public class UpdateService {
	// Get a logger
	static Logger log = Logger.getLogger(UpdateService.class.getName());
	
	@SuppressWarnings("resource") //Quieting a leak.
	public void updateUser(User user, double amount, String type, MainMenu mainMenu) {
			
		// Set to null just to be safe.
		Connection con = null;
		ResultSet results = null;
		PreparedStatement stmt = null;
		
		// Values for in-method logic
		BankMenu bankMenu = new BankMenu();
		double checkingBal = user.getCheckingBal();
		double savingBal = user.getSavingBal();
		boolean recordsFound = false;
		int id = 0;

		// Try to get a connection
		try {
			con = ConnectBox.getConnection();
		} catch (Exception e) {
			System.out.println("Connection Failure");
			e.printStackTrace();
		}

		// The enormous query below returns the result set of all relevant information from the
		// three table joined on bank_user_id where condition is username and password.
				
		// I would like to make this a stored procedure but doubt I'll have time.
		String queryString = "SELECT bank_user.bank_user_id, bank_user.bank_user_f_name, bank_user.bank_user_l_name, bank_user.bank_user_user_name, bank_user.bank_user_pass_word, bank_checking_account.ch_account_bal, "
				+ "bank_saving_account.sv_account_bal FROM ((bank_user inner join bank_checking_account ON bank_user.bank_user_id = bank_checking_account.bank_user_id) "
				+ "inner join bank_saving_account ON bank_checking_account.bank_user_id = bank_saving_account.bank_user_id) WHERE bank_user_user_name = ? AND bank_user_pass_word = ?";

		// Next we try to make it a prepped statement.
		try {
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getPassWord());
			results = stmt.executeQuery();
			
			// If successful, some variables will be set.
			if (results.next()) {
				recordsFound = true;
				id = results.getInt("bank_user_id");
				checkingBal = results.getDouble("ch_account_bal");
				savingBal = results.getDouble("sv_account_bal");
			}
		} catch (SQLException e) {
			log.warning("Query Failure!");
			e.printStackTrace();
		}
		
		// This block outputs banking information based on which account was passed to it.
		// It also updates the local user object and the database of any new changes.
		if (recordsFound == true) {
			
			// Code "1" means that a checking account was selected.
			if ("1".equals(type)) {
				
				// User is alerted.
				System.out.println("Your checking account balance was: $" + checkingBal);
				checkingBal += amount;
				
				// This string will update a checking account balance by id.
				String updateCheckString = "UPDATE bank_checking_account SET ch_account_bal = ? WHERE bank_user_id = ?";
				
				// It is turned into a prepped statement
				try {
					stmt = con.prepareStatement(updateCheckString);
					stmt.setDouble(1, checkingBal);
					stmt.setInt(2, Integer.valueOf(id));
					stmt.execute();
					
					// If successful, the user object is also updated.
					user.setCheckingBal(checkingBal);
					System.out.println("It has been updated to: $" + checkingBal);
				} catch (SQLException e) {
					log.warning("Update SQL ERROR: Checking.");
					e.printStackTrace();
				}
			  // Code "2" means that a saving account was selected.	
			} else if ("2".equals(type)) {
				
				// User is alerted.
				System.out.println("Your saving account balance was: $" + savingBal);
				savingBal += amount;

				// This string will update a saving account balance by id.
				String updateSaveString = "UPDATE bank_saving_account SET sv_account_bal = ? WHERE bank_user_id = ?";
				
				// It is turned into a prepped statement
				try {
					stmt = con.prepareStatement(updateSaveString);
					stmt.setDouble(1, savingBal);
					stmt.setInt(2, Integer.valueOf(id));
					stmt.execute();
					
					// If successful, the user object is also updated.
					user.setSavingBal(savingBal);
					System.out.println("It has been updated to: $" + savingBal);
				} catch (SQLException e) {
					log.warning("Update SQL ERROR: Saving.");
					e.printStackTrace();
				}
				// Code "3" means that a balance inquiry was made. No data will change, but it will be printed out.
			}else if ("3".equals(type)) {
				user.setCheckingBal(checkingBal);
				user.setSavingBal(savingBal);
				System.out.println("Checking Balance: $" + checkingBal);
				System.out.println("Saving Balance: $" + savingBal);
			 // Any other code (like the one passed from loginService)
			// will not update anything except the user object.
			}else {
				user.setCheckingBal(checkingBal);
				user.setSavingBal(savingBal);
			}
		}
		// Finally, the user will be passed to the Bank Menu.
		bankMenu.displayWithAcct(user, mainMenu);
	}
}