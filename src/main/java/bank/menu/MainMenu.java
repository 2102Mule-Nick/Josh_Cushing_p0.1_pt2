package bank.menu;

import java.util.Scanner;


public final class MainMenu { // MainMenu is a singleton class. There will only ever be one instantiated.
	
	//Make a field that is an object of the MainMenu class. Set it to null.
	private static MainMenu mainMenu = null;
	
	//Also make a field that is a public scanner. This is used ot gather user input.
	public Scanner scanner = new Scanner(System.in);
	
	//Create the singleton method that allows only one MainMenu object to ever be instantiated.
	public static synchronized MainMenu theOnlyMainMenu() {
		if (mainMenu == null) {
			mainMenu = new MainMenu();
		}
		return mainMenu;
	}

	public void displayMenuItems(LoginMenu loginMenu, RegisterMenu registerMenu) {
		System.out.println("<-MAIN MENU->");
		System.out.println("1) Login");
		System.out.println("2) Register");
		System.out.println("3) Quit\n");
		System.out.println("Please pick the number for a corresponding option.");
		System.out.print("-->");

		// Create a variable to hold user input.
		String userInput = scanner.next();
		
		// Switch to handle user decision.
		switch (userInput) {
		case "1":
			// If 1 go to login menu.
			loginMenu.displayLoginBox(mainMenu, loginMenu, registerMenu);
			break;
		case "2":
			// If 2 go to register menu.
			registerMenu.displayRegisterBox(mainMenu, loginMenu, registerMenu);
			break;
		case "3":
			// If 3 exit the program, no break needed.
			System.out.println("Goodbye");
			System.exit(0);
		default:
			// Print instructions in case the user mistypes.
			System.out.println("Please type in 1, 2, or 3 for the corresponding option.");

			// Send user back to top of menu.
			this.displayMenuItems(loginMenu, registerMenu);
		}
	}
}
