package bank.menu;

import java.util.Scanner;
import java.util.logging.Logger;
import bank.services.LoginService;

public class LoginMenu {
	// Get a logger
	static Logger log = Logger.getLogger(LoginMenu.class.getName());
	
	// Make a field that is an object of the LoginMenu class.
	private static LoginMenu loginMenu = null;
	
	//Instantiate a loginService object.
	private LoginService loginService = new LoginService();
	
	// A public scanner. This is used to gather user input.
	public Scanner scanner = new Scanner(System.in);
	
//------------------------------------------------------------------------------------
	
	// This is the singleton method that instantiates the only mainMenu object.
	public static synchronized LoginMenu theOnlyLoginMenu() {
		if (loginMenu == null) {
			loginMenu = new LoginMenu();
		}
		return loginMenu;
	}
//----------------------------------------------------------------------------------------------------
	
	public void displayLoginBox(MainMenu mainMenu) {

		// Prompt user for userName
		System.out.println("<-LOGIN MENU->");
		System.out.println("Please enter your username or 0 to return to main menu");
		System.out.print("-->");
		
		// Create a string for the userName that will be entered by the user.
		String userName = scanner.next();
		
		// Making sure that the input is not "0"
		if (userName.equals("0")) {
			mainMenu.displayMenuItems();
		}

		// Prompt user for passWord
		System.out.println("Please enter your password or 0 to return to main menu");
		System.out.print("-->");

		// Create a string for the passWord that will be entered by the user.
		String passWord = scanner.next();

		// Making sure that the input is not "0"
		if (passWord.equals("0")) {
			mainMenu.displayMenuItems();
		}

		// Send userName and passWord to loginService.
		loginService.logInUser(userName, passWord, mainMenu, loginMenu);
	}
}
