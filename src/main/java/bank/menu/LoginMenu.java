package bank.menu;

import java.util.Scanner;

import bank.services.LoginService;

public class LoginMenu {
	
	// Make a field that is an object of the LoginMenu class. Set it to null.
	private static LoginMenu loginMenu = null;
	
	//Instantiate a loginService object.
	private LoginService loginService = new LoginService();

	//Make a field to store a final value for going back one menu.
	public final String back = "0";
	
	// Make a field that is a public scanner.
	public Scanner scanner = new Scanner(System.in);
	
//------------------------------------------------------------------------------------
	
	// Create the singleton method that allows only one LoginMenu object to ever be instantiated.
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
		
		// Making sure that the input is not "BACK"
		if (userName.equals(back)) {
			mainMenu.displayMenuItems();
		}

		// Prompt user for passWord
		System.out.println("Please enter your password or 0 to return to main menu");
		System.out.print("-->");

		// Create a string for the passWord that will be entered by the user.
		String passWord = scanner.next();

		// Making sure that the input is not "BACK"
		if (userName.equals(back)) {
			mainMenu.displayMenuItems();
		}

		// Send userName and passWord to loginService.
		loginService.logInUser(userName, passWord, mainMenu, loginMenu);
	}

}
