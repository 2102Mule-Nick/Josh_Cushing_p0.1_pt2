package bank.services;

import java.util.Scanner;

import bank.menu.BankMenu;
import bank.menu.MainMenu;
import bank.pojo.User;

public class TransactionService {

	// Make a field that is a public scanner.
	public Scanner scanner = new Scanner(System.in);

//--------------------------------------------------------
	public void deposit(User user, MainMenu mainMenu) {
		BankMenu bankMenu = new BankMenu();
		UpdateService updateService = new UpdateService();
		String entry = "";
		String type = "";
		double amount = 0.00;
		Boolean loopEnd = false;
		

		
		// User enters a number. The entry is validated.
		while (loopEnd == false) {
			System.out.println("How much would you like to deposit?");
			entry = scanner.next();

			try {
				amount = Double.parseDouble(entry);
				if (amount >= 0) {
					loopEnd = true;
				} else {
					System.out.println(amount + " is too small!");
				}
			} catch (NumberFormatException e) {
				System.out.println(entry + " is not a valid amount of currency.");
				System.out.println("Please enter digits in this format: 33.12");
			}
		}
		// User picks an account
		System.out.println("Deposit in 1)Checking");
		System.out.println("        or 2)Saving");
		System.out.println("        or 3)Go Back");
		System.out.println("-->");
		type = scanner.next();
		
		switch (type) {
		case "1":
		case "2":
			updateService.updateUser(user, amount, type, mainMenu);
			break;
		case "3":
			bankMenu.displayWithAcct(user, mainMenu);
			break;
		default:
			System.out.println("Please enter 1, 2, or 3.");
			break;
		}		
	}


	public void withdraw(User user, MainMenu mainMenu) {
		BankMenu bankMenu = new BankMenu();
		UpdateService updateService = new UpdateService();
		String entry = "";
		String type = "";
		double amount = 0.00;
		Boolean loopEnd = false;

		// User enters a number. The entry is validated.
		while (loopEnd == false) {
			System.out.println("How much would you like to withdraw?");
			entry = scanner.next();

			try {
				amount = Double.parseDouble(entry);
				if (amount >= 0) {
					loopEnd = true;
				} else {
					System.out.println(amount + " is too small!");
				}
			} catch (NumberFormatException e) {
				System.out.println(entry + " is not a valid amount of currency.");
				System.out.println("Please enter digits in this format: 33.12");
			}
		}
		// User picks an account
		System.out.println("Withdraw from 1)Checking");
		System.out.println("           or 2)Saving");
		System.out.println("           or 3)Go Back");
		System.out.println("-->");
		type = scanner.next();
		
		switch (type) {
		case "1":
			if(amount > user.getCheckingBal()) {
				System.out.println(amount);
				System.out.println(user.getCheckingBal());
				System.out.println("You do not have that much in your Checking account!");
				withdraw(user, mainMenu);
				break;
			}else {
				amount *= -1;
				updateService.updateUser(user, amount, type, mainMenu);
			}
			break;
		case "2":
			if(amount > user.getSavingBal()) {
				System.out.println("You do not have that much in your Saving account!");
				withdraw(user, mainMenu);
				break;
			}else {
				amount *= -1;
				updateService.updateUser(user, amount, type, mainMenu);
			}
			break;
		case "3":
			bankMenu.displayWithAcct(user, mainMenu);
			break;
		default:
			System.out.println("Please enter 1, 2, or 3.");
			break;
		}
	}
	
	public void inquire(User user, MainMenu mainMenu) {
		double amount = 0.00;
		String type = "3";
		UpdateService updateService = new UpdateService();
		updateService.updateUser(user, amount, type, mainMenu);
	}
}