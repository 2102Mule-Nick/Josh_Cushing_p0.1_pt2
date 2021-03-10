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

	@SuppressWarnings("resource")
	public void updateUser(User user, double amount, String type, MainMenu mainMenu) {

		// Set to null just to be safe.
		Connection con = null;
		ResultSet results = null;
		PreparedStatement stmt = null;

		// Values for in-method logic
		BankMenu bankMenu = new BankMenu();
		String queryString = "";
		Double chBal = 0.00;
		Double svBal = 0.00;
		int id = 0;
		
		//Object variables for brevity.
		double checkingBal = user.getCheckingBal();
		double savingBal = user.getSavingBal();
		String userName = user.getUserName();
		String passWord = user.getPassWord();
		
		
		
		// Try to get a connection
		try {
			con = ConnectBox.getConnection();
		} catch (Exception e) {
			System.out.println("Connection Failure");
			e.printStackTrace();
		}

		// GET THE USER ID
		queryString = "select get_user(?,?)";

		try {
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, userName);
			stmt.setString(2, passWord);
			results = stmt.executeQuery();
			if (results.next()) {
				id = results.getInt("get_user");
			}
		} catch (SQLException e1) {
			log.warning("get_user Function Failure!");
			e1.printStackTrace();
		}

		// GET THE CHECKING BAL
		queryString = "select get_user_ch(?)";

		try {
			stmt = con.prepareStatement(queryString);
			stmt.setInt(1, id);
			results = stmt.executeQuery();
			if (results.next()) {
				chBal = results.getDouble("get_user_ch");
			}
		} catch (SQLException e1) {
			log.warning("get_user_ch Function Failure!");
			e1.printStackTrace();
		}

		// GET THE SAVING BAL
		queryString = "select get_user_sv(?)";

		try {
			stmt = con.prepareStatement(queryString);
			stmt.setInt(1, id);
			results = stmt.executeQuery();
			if (results.next()) {
				svBal = results.getDouble("get_user_sv");
			}
		} catch (SQLException e1) {
			log.warning("get_user_sv Function Failure!");
			e1.printStackTrace();
		}
		
		if ("1".equals(type)) {
			// UPDATE THE CHECKING BAL
			
			
			System.out.println("Your checking account balance was: $" + checkingBal);
			
			// New balance
			checkingBal+=amount;

			// Update the user object
			user.setCheckingBal(checkingBal);

			// Function
			queryString = "select update_ch(?, ?)";

			// Try to prep
			try {
				stmt = con.prepareStatement(queryString);
				stmt.setInt(1, id);
				stmt.setDouble(2, checkingBal);
				results = stmt.executeQuery();
				results.close();
				con.close();
				System.out.println("It has been updated to: $" + checkingBal);
			} catch (SQLException e1) {
				log.warning("update_ch Function Failure!");
				e1.printStackTrace();
			}
		} else if ("2".equals(type)) {
			// UPDATE THE SAVING BAL
			
			// New balance
			savingBal += amount;

			System.out.println("Your Saving account balance was: $" + savingBal);
			
			// Update the user object
			user.setSavingBal(savingBal);
			
			// Function
			queryString = "select update_sv(?, ?)";

			// Try to prep
			try {
				stmt = con.prepareStatement(queryString);
				stmt.setInt(1, id);
				stmt.setDouble(2, savingBal);
				results = stmt.executeQuery();
				results.close();
				con.close();
				System.out.println("It has been updated to: $" + savingBal);
			} catch (SQLException e1) {
				log.warning("update_sv Function Failure!");
				e1.printStackTrace();
			}
			//Else print a balance statement.
		}else {
			System.out.println("Checking: $" + checkingBal);
			System.out.println("Saving: $" + savingBal);
		}
		
		// Finally, the user will be passed back to the Bank Menu.
		bankMenu.displayWithAcct(user, mainMenu);
	}
}
		
		
		
		
		
//-----------------------------------------------------------------------------------------------------------------------------------
		/*
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
			*/