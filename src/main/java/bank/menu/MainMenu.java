package bank.menu;

import java.util.Scanner;

//Once I learn how to log...
//import java.util.logging.Logger;

public final class MainMenu {

	// Once I learn how to log...
	// static Logger log = Logger.getLogger(MainMenu.class.getName());

	// Fields with mainMenu, registerMenu, and loginMenu classes.
	private static MainMenu mainMenu = null;
	private static RegisterMenu registerMenu = null;
	private static LoginMenu loginMenu = null;

	// A public scanner. This is used to gather user input.
	public Scanner scanner = new Scanner(System.in);

	// A variable to hold user input.
	public String userInput = "";

//-------------------------------------------------------------------------------------------------------

	// This is the singleton method that instantiates the only mainMenu object.
	public static synchronized MainMenu theOnlyMainMenu() {
		if (mainMenu == null) {
			mainMenu = new MainMenu();

			// When it is instantiated, its fields are set to the return value of their own
			// singleton methods.
			registerMenu = RegisterMenu.theOnlyRegisterMenu();
			loginMenu = LoginMenu.theOnlyLoginMenu();
		}
		return mainMenu;
	}
//-------------------------------------------------------------------------------------------------------		

	// Main menu UI.
	public void displayMenuItems() {
		System.out.println("<-MAIN MENU->");
		System.out.println("1) Login");
		System.out.println("2) Register");
		System.out.println("3) Quit\n");
		System.out.println("Please pick the number for a corresponding option.");
		System.out.print("-->");
		userInput = scanner.next();

		// A switch to handle user decision.
		switch (userInput) {
		case "1":
			// If 1 --> login menu.
			loginMenu.displayLoginBox(mainMenu);
			break;
		case "2":
			// If 2 --> register menu.
			registerMenu.displayRegisterBox(mainMenu);
			break;
		case "3":
			// If 3 --> exit the program.
			System.out.println("Goodbye");
			System.exit(0);
		default:
			// Print instructions, recall menu.
			System.out.println("Please type in 1, 2, or 3 for the corresponding option.");
			displayMenuItems();
		}
	}
}