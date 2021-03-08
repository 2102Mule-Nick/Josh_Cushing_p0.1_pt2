package bank.menu;

import java.util.Scanner;
import bank.pojo.User;
import bank.services.TransactionService;

public class BankMenu {

	// Make a field that is a public scanner.
	public Scanner scanner = new Scanner(System.in);
		
	TransactionService transactionService = new TransactionService();

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
			// Print a polite goodbye message.
			System.out.println("Goodbye");

			// Exit the program. No break needed.
			System.exit(0);
		default:
			// Print instructions.
			// In case the user didn't read the prompt, or mistyped.
			System.out.println("Please type in 1, 2, 3, 4, or 5 for the corresponding option.");
			// Send user back to top of menu.
			displayWithAcct(user, mainMenu);
		}
	}
}
