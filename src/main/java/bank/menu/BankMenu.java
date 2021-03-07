package bank.menu;

import bank.pojo.User;

public class BankMenu {

	public void displayWithAcct(User user) {
		System.out.println("We have made it to the bank menu");
	/*
		// Instantiate a scanner to read console input.
		Scanner scanner = new Scanner(System.in);

		// Print instructions
		System.out.println("<-ACCOUNT MENU->");
		System.out.println("-->1) Deposit");
		System.out.println("-->2) Withdraw");
		System.out.println("-->3) Balance");
		System.out.println("-->4) Back");
		System.out.println("-->5) Quit");
		System.out.print("Please type in the number for\none of the following options-->");

		// Scan user input
		String userInput = scanner.next();

		// Decision logic based on what the user answered.
		switch (userInput) {

		// Case if the user typed "1"
		case "1":
			Menu depositMenu = new DepositMenu();
			depositMenu.displayWithAcct(userAcct);
			break;
		// Case if the user typed "2"
		case "2":
			Menu withdrawMenu = new WithdrawMenu();
			withdrawMenu.displayWithAcct(userAcct);
			break;
		// Case if the user typed "3"
		case "3":
			TransactionService transactionService = new TransactionService();
			transactionService.inquire(userAcct);
			break;
		// Case if the user typed "4"
		case "4":
			break;
		// Case if the user typed "5"
		case "5":
			// Print a polite goodbye message.
			System.out.println("Goodbye");

			// Exit the program. No break needed.
			System.exit(0);

			// What happens if the user types LITERALLY anything else.
		default:
			// Print instructions.
			// In case the user didn't read the prompt, or mistyped.
			System.out.println("Please type in 1, 2, 3, or 4 for the corresponding option.");
			// Send user back to top of menu.
			displayWithAcct(userAcct);
		}
			*/
	}
}
