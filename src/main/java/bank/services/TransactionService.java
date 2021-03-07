package bank.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import bank.db.ConnectBox;
import bank.menu.BankMenu;
import bank.menu.MainMenu;
import bank.pojo.User;

public class TransactionService {

	// Make a field that is a public scanner.
	public Scanner scanner = new Scanner(System.in);

	public void deposit(User user, MainMenu mainMenu) {
		Connection con = null;

		try {
			con = ConnectBox.getConnection();
		} catch (Exception e1) {
			System.out.println("Connecction MISSING");
			e1.printStackTrace();
		}
		String userDecision = "";
		
		PreparedStatement stmt = null;

		boolean loopEnd = false;

		double balanceToDep = 0;
		
		String queryString = "";
		
		while (loopEnd == false) {
			System.out.println("Deposit in 1)Checking");
			System.out.println("        or 2)Saving");
			System.out.println("-->");
			String userInput = scanner.next();
			if (userInput == "1") {
				 queryString = "SELECT checking_bal FROM user_account where user_name = ? and pass_word = ? ";
				loopEnd = true;
				userDecision = "checking";
			} else if (userInput == "2") {
				queryString = "SELECT saving_bal FROM user_account where user_name = ? and pass_word = ? ";
				loopEnd = true;
				userDecision = "saving";
			} else
				System.out.println("Please enter 1, or 2.");
		}
		
		System.out.println("How much would you like to deposit?");
		// Get user input.
				double total = scanner.nextDouble();

				String checkTotal = String.valueOf(total);
				Boolean isValidMoney = true;

				try {
					Float.parseFloat(checkTotal);
				} catch (NumberFormatException e) {
					System.out.println(checkTotal + " is not a valid amount for currency.");
					System.out.println("Please enter digits in this format: 33.12");
					isValidMoney = false;
				}
				
				while (isValidMoney == false){
					
					total = scanner.nextDouble();
					checkTotal = String.valueOf(total);
					isValidMoney = true;
					
					try {
						Float.parseFloat(checkTotal);
					} catch (NumberFormatException e) {
						System.out.println(checkTotal + " is not a valid amount for currency.");
						System.out.println("Please enter digits in this format: 33.12");
						isValidMoney = false;
					}		
				}
		
		
		try {
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getPassWord());
			ResultSet results = stmt.executeQuery();

			if (results.next()) {
				if("checking".equals(userDecision)) {
					balanceToDep = results.getDouble("checking_bal");
				}
				else if("saving".equals(userDecision)) {
					balanceToDep = results.getDouble("saving_bal");
				}
				balanceToDep += total;
				
				
//------------------------------------------------------------------------------------------------------------
				
				
				//Get all my values
				String userName = user.getUserName();
				String passWord = user.getPassWord();
				Double checkingBal = user.getCheckingBal();
				Double savingBal = user.getSavingBal();
				
				//Create a string to feed into a prepared statement.
				String sql = "UPDATE user_account SET ? = ? WHERE user_name = ? and pass_word = ?;";
				stmt = null;
				
				try {
					//Create a prepared statement.
					stmt = con.prepareStatement(sql);
				} catch (SQLException e2) {
					e2.printStackTrace();
				}

				try {
					if("checking".equals(userDecision)) {
						checkingBal = balanceToDep;
						stmt.setDouble(1, checkingBal);
					}
					if("saving".equals(userDecision)) {
						savingBal = balanceToDep;
						stmt.setDouble(1, savingBal);
					}
					stmt.setString(2, userName);
					stmt.setString(3, passWord);

					int i = stmt.executeUpdate();
					System.out.println(i + " records udated");
					System.out.println("Old balance: " );
					BankMenu bankMenu = new BankMenu();
		
					bankMenu.displayWithAcct(user, mainMenu);
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
//--------------------------------------------------------------------------------------------	
				
				
				
				
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

	public void withdraw(User user, MainMenu mainMenu) {
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

	public void inquire(User user, MainMenu mainMenu) {
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