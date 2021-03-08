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

//--------------------------------------------------------
	public void deposit(User user, MainMenu mainMenu) {
		BankMenu bankMenu = new BankMenu();
		boolean loopEnd = false;
		String userAccount = "";
		String userInput = "";
		String queryString = "";
		Connection con = null;
		PreparedStatement stmt = null;
		float amount = 0;
		ResultSet results = null;
		double dbBal = 0;
		double nuBal = 0;

		try {
			con = ConnectBox.getConnection();
		} catch (Exception e1) {
			System.out.println("Connection failure");
			e1.printStackTrace();
		}

		while (loopEnd == false) {
			System.out.println("Deposit in 1)Checking");
			System.out.println("        or 2)Saving");
			System.out.println("        or 3)Go Back");
			System.out.println("-->");
			userAccount = scanner.next();

			switch (userAccount) {
			case "1":
				queryString = "SELECT checking_bal FROM user_account where user_name = ? and pass_word = ? ";
				loopEnd = true;
				break;
			case "2":
				queryString = "SELECT saving_bal FROM user_account where user_name = ? and pass_word = ? ";
				loopEnd = true;
				break;
			case "3":
				bankMenu.displayWithAcct(user, mainMenu);
				break;
			default:
				System.out.println("Please enter 1, 2, or 3.");
				break;
			}
		}

		loopEnd = false;

		while (loopEnd == false) {
			System.out.println("How much would you like to deposit?");
			System.out.println("Enter an amount or 0 to go back");
			userInput = scanner.next();

			try {
				amount = Float.parseFloat(userInput);
				if (amount > 0) {
					loopEnd = true;
				} else {
					System.out.println("That amount is too small!");
				}
			} catch (NumberFormatException e) {
				System.out.println(userInput + " is not a valid amount of currency.");
				System.out.println("Please enter digits in this format: 33.12");
			}
		}

		try {
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getPassWord());
			results = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Results Query error");
		}

		if ("1".equals(userAccount)) {
			try {
				if (results.next()) {
					dbBal = results.getDouble("checking_bal");
					nuBal = dbBal + amount;

					queryString = "UPDATE user_account SET checking_bal = ? where user_name = ? and pass_word = ?";

					stmt = con.prepareStatement(queryString);
					stmt.setDouble(1, nuBal);
					stmt.setString(2, user.getUserName());
					stmt.setString(3, user.getPassWord());
					stmt.execute();
					System.out.println("You deposited: $" + amount);
					System.out.println("Your Checking Account now contains: $" + nuBal);
					bankMenu.displayWithAcct(user, mainMenu);
					results.close();
					con.close();
				} else {
					System.out.println("Checking Query error");
				}
			} catch (SQLException e) {
				System.out.println("Checking Query error");
				e.printStackTrace();
			}
		} else {
			try {
				if (results.next()) {
					dbBal = results.getDouble("saving_bal");
					nuBal = dbBal + amount;

					queryString = "UPDATE user_account SET saving_bal = ? where user_name = ? and pass_word = ?";

					stmt = con.prepareStatement(queryString);
					stmt.setDouble(1, nuBal);
					stmt.setString(2, user.getUserName());
					stmt.setString(3, user.getPassWord());
					stmt.execute();
					System.out.println("You deposited: $" + amount);
					System.out.println("Your Savings Account now contains: $" + nuBal);
					bankMenu.displayWithAcct(user, mainMenu);
					results.close();
					stmt.close();
					con.close();
				} else {
					System.out.println("Saving Query error");
				}
			} catch (SQLException e) {
				System.out.println("Saving Query error");
				e.printStackTrace();
			}

		}
	}

//--------------------------------------------------------
	public void withdraw(User user, MainMenu mainMenu) {
		BankMenu bankMenu = new BankMenu();
		boolean loopEnd = false;
		String userAccount = "";
		String userInput = "";
		String queryString = "";
		Connection con = null;
		PreparedStatement stmt = null;
		double amount = 0;
		ResultSet results = null;
		double dbBal = 0;
		double nuBal = 0;

		try {
			con = ConnectBox.getConnection();
		} catch (Exception e1) {
			System.out.println("Connection failure");
			e1.printStackTrace();
		}

		while (loopEnd == false) {
			System.out.println("Withdraww from 1)Checking");
			System.out.println("            or 2)Saving");
			System.out.println("            or 3)Go Back");
			System.out.println("-->");
			userAccount = scanner.next();

			switch (userAccount) {
			case "1":
				queryString = "SELECT checking_bal FROM user_account where user_name = ? and pass_word = ? ";
				loopEnd = true;
				break;
			case "2":
				queryString = "SELECT saving_bal FROM user_account where user_name = ? and pass_word = ? ";
				loopEnd = true;
				break;
			case "3":
				bankMenu.displayWithAcct(user, mainMenu);
				break;
			default:
				System.out.println("Please enter 1, 2, or 3.");
				break;
			}
		}

		try {
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getPassWord());
			results = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Results Query error");
		}

		loopEnd = false;

		while (loopEnd == false) {
			System.out.println("How much would you like to withdraw?");
			System.out.println("Enter an amount or 0 to go back");
			userInput = scanner.next();

			try {
				amount = Double.parseDouble(userInput);
				if (amount <= 0) {
					System.out.println("Cannot withdraw amount less than or equal to zero");
				} else {
					if ("1".equals(userAccount)) {
						try {
							if (results.next()) {
								dbBal = results.getDouble("checking_bal");
								if (amount > dbBal) {
									System.out.println("You cannot withdraw that much from your account!");
								} else {
									loopEnd = true;
								}
							}
						} catch (SQLException e) {
							System.out.println("Query Error");
							e.printStackTrace();
						}
					} else {
						try {
							if (results.next()) {
								dbBal = results.getDouble("saving_bal");
								if (amount > dbBal) {
									System.out.println("You cannot withdraw that much from your account!");
								} else {
									loopEnd = true;
								}
							}
						} catch (SQLException e) {
							System.out.println("Query Error");
							e.printStackTrace();
						}
					}
				}
			} catch (NumberFormatException e) {
				System.out.println(userInput + " is not a valid amount of currency.");
				System.out.println("Please enter digits in this format: 33.12");
				e.printStackTrace();
			}

		}

		if ("1".equals(userAccount)) {
			try {
				nuBal = dbBal - amount;

				queryString = "UPDATE user_account SET checking_bal = ? where user_name = ? and pass_word = ?";

				stmt = con.prepareStatement(queryString);
				stmt.setDouble(1, nuBal);
				stmt.setString(2, user.getUserName());
				stmt.setString(3, user.getPassWord());
				stmt.execute();
				System.out.println("You withdrew: $" + amount);
				System.out.println("Your Checking Account now contains: $" + nuBal);
				bankMenu.displayWithAcct(user, mainMenu);
				results.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("Checking Insert error");
				e.printStackTrace();
			}
		} else {
			try {
				dbBal = results.getDouble("saving_bal");
				nuBal = dbBal - amount;

				queryString = "UPDATE user_account SET saving_bal = ? where user_name = ? and pass_word = ?";

				stmt = con.prepareStatement(queryString);
				stmt.setDouble(1, nuBal);
				stmt.setString(2, user.getUserName());
				stmt.setString(3, user.getPassWord());
				stmt.execute();
				System.out.println("You withdrew: $" + amount);
				System.out.println("Your Savings Account now contains: $" + nuBal);
				bankMenu.displayWithAcct(user, mainMenu);
				results.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("Saving Insert error");
				e.printStackTrace();
			}

		}
	}

	public void inquire(User user, MainMenu mainMenu) {
		
		Connection con = null;

		try {
			con = ConnectBox.getConnection();
		} catch (Exception e1) {
			System.out.println("Connection failure");
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
