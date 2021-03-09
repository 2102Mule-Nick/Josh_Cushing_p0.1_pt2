package bank.menu;

import java.util.Scanner;

public final class MainMenu {
	
	//A public scanner. This is used to gather user input.
	public Scanner scanner = new Scanner(System.in);
	
	//Fields with mainMenu, registerMenu, and loginMenu classes.
	private static MainMenu mainMenu = null;
	private static RegisterMenu registerMenu = null;
	private static LoginMenu loginMenu = null;
//-------------------------------------------------------------------------------------------------------
	
	// This is the singleton method that instantiates the only mainMenu object.
	public static synchronized MainMenu theOnlyMainMenu() {
		if (mainMenu == null) {
			mainMenu = new MainMenu();
				
			//When it is instantiated, its fields are set to the return value of their own singleton methods.
			registerMenu = RegisterMenu.theOnlyRegisterMenu();
			loginMenu = LoginMenu.theOnlyLoginMenu();
		}
		return mainMenu;
	}
//-------------------------------------------------------------------------------------------------------		
	
	//Main menu UI.
	public void displayMenuItems() {
		System.out.println("<-MAIN MENU->");
		System.out.println("1) Login");
		System.out.println("2) Register");
		System.out.println("3) Quit\n");
		System.out.println("Please pick the number for a corresponding option.");
		System.out.print("-->");

		// A variable to hold user input.
		String userInput = scanner.next();
		
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
