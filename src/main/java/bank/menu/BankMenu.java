package bank.menu;

import java.util.Scanner;
import java.util.logging.Logger;

import bank.pojo.User;
import bank.services.TransactionService;

public class BankMenu {

	// Get a logger
	static Logger log = Logger.getLogger(BankMenu.class.getName());
	
	// Make a field that is a public scanner.
	public Scanner scanner = new Scanner(System.in);
	
	// We will need a transactionService
	TransactionService transactionService = new TransactionService();

	// This method acts similarly to the MainMenu.displayMenuItems
	// method except it holds the suer for as long as the user doesn't back out to the main menu.
	public void displayWithAcct(User user, MainMenu mainMenu) {

		// Print instructions
		System.out.println("<-ACCOUNT MENU->");
		System.out.println("-->1) Deposit");
		System.out.println("-->2) Withdraw");
		System.out.println("-->3) Balance");
		System.out.println("-->4) Back");
		System.out.println("-->5) Quit");
		System.out.println("Please type in the number for\none of the following options");
		System.out.println("-->");
		
		// Scan user input
		String userInput = scanner.next();

		// Decision logic based on what the user answered.
		switch (userInput) {

		// Case if the user typed "1"
		case "1":
			transactionService.deposit(user, mainMenu);
			break;
		// Case if the user typed "2"
		case "2":
			transactionService.withdraw(user, mainMenu);
			break;
		// Case if the user typed "3"
		case "3":
			transactionService.inquire(user, mainMenu);
			break;
		// Case if the user typed "4"
		case "4":
			mainMenu.displayMenuItems();
			break;
		// Case if the user typed "5"
		case "5":
			System.out.println("Goodbye");
			System.exit(0);
		default :
			System.out.println("Please type in 1, 2, 3, 4, or 5 for the corresponding option.");
			displayWithAcct(user, mainMenu);
		}
	}
}
