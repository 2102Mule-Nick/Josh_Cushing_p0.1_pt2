package bank.menu;

import java.util.Scanner;

public final class MainMenu {
	
	//Make a field that is a public scanner. This is used to gather user input.
	public Scanner scanner = new Scanner(System.in);
	
	//Make a field that is an object of the MainMenu class. Set it to null.
	private static MainMenu mainMenu = null;
	
	//Make a field that is an object of the RegisterMenu class. Set it to null.
	private static RegisterMenu registerMenu = null;
	
	//Make a field that is an object of the LoginMenu class. Set it to null.
	private static LoginMenu loginMenu = null;

//-------------------------------------------------------------------------------------------------------
	
		//Create the singleton method that allows only one MainMenu object to ever be instantiated.
		public static synchronized MainMenu theOnlyMainMenu() {
			if (mainMenu == null) {
				mainMenu = new MainMenu();
				//When it is instantiated, its fields are set to the return
				//value of their own singleton methods.
				registerMenu = RegisterMenu.theOnlyRegisterMenu();
				loginMenu = LoginMenu.theOnlyLoginMenu();
			}
			return mainMenu;
		}
		
	public void displayMenuItems() {
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
			loginMenu.displayLoginBox(mainMenu);
			break;
		case "2":
			// If 2 go to register menu.
			registerMenu.displayRegisterBox(mainMenu);
			break;
		case "3":
			// If 3 exit the program, no break needed.
			System.out.println("Goodbye");
			System.exit(0);
		default:
			// Print instructions in case the user mistypes.
			System.out.println("Please type in 1, 2, or 3 for the corresponding option.");

			// Send user back to top of menu.
			this.displayMenuItems();
		}
	}
}
