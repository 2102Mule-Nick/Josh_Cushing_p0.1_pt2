package bank.menu;

import java.util.Scanner;
import java.util.logging.Logger;

import bank.services.RegisterService;

public class RegisterMenu {

	// Get a logger
	static Logger log = Logger.getLogger(RegisterMenu.class.getName());
	
	// An object of the RegisterMenu class.
	private static RegisterMenu registerMenu = null;

	// An object of the RegisterService class.
	private RegisterService registerService = new RegisterService();
	
	// Also make a field that is a public scanner.
	public Scanner scanner = new Scanner(System.in);

//-------------------------------------------------------------------------------------------------
	// This is the singleton method that instantiates the only registerMenu object.
	public static synchronized RegisterMenu theOnlyRegisterMenu() {
		if (registerMenu == null) {
			registerMenu = new RegisterMenu();
		}
		return registerMenu;
	}
	
//-------------------------------------------------------------------------------------------------
	public void displayRegisterBox(MainMenu mainMenu) {
		
		//FIRST NAME ENTRY
		System.out.println("<-REGISTER MENU->");
		System.out.println("Please enter your first name or 0 to go back to the main Menu.");
		System.out.print("-->");
		
		// Create a string for the firstName that will be entered by the user.
		String firstName = scanner.next();
		
		// Make sure that the input is not "0"
		if (firstName.equals("0")) {
			mainMenu.displayMenuItems();
		}
		
		//Local variable to check if any non-letters are slipped into a string.
		Boolean hasOnlyLetters = true;
		
		// DATA RESTRAINT #1: firstName must be between 2 and 15 characters, and must only contains letters.
		for (int i = 0; i < firstName.length(); i++) {
			if (Character.isLetter(firstName.charAt(i)) == false) {
				hasOnlyLetters = false;
			}
		}
		while (firstName.length() > 15 || firstName.length() < 2 || hasOnlyLetters == false) {
			System.out.println("Please ensure that your first name is between\n2 and 15 characters and contains only letters");
			System.out.print("-->");
			firstName = scanner.next();

			if (firstName.equals("0")) {
				mainMenu.displayMenuItems();
			}
			
			hasOnlyLetters = true;
			for (int i = 0; i < firstName.length(); i++) {
				if (Character.isLetter(firstName.charAt(i)) == false) {
					hasOnlyLetters = false;
				}
			}
		}
		
		//LAST NAME ENTRY
		System.out.println("Please enter your last name or 0 to go back to the main Menu.");
		System.out.print("-->");
		
		// Create a string for the firstName that will be entered by the user.
		String lastName = scanner.next();
		
		// Make sure that the input is not "0"
		if (lastName.equals("0")) {
			mainMenu.displayMenuItems();
		}
		
		hasOnlyLetters = true;
	
		// DATA RESTRAINT #2: lastName must be between 2 and 15 characters, and must only contains letters.
		for (int i = 0; i < lastName.length(); i++) {
			if (Character.isLetter(lastName.charAt(i)) == false) {
				hasOnlyLetters = false;
			}
		}
		while (lastName.length() > 15 || lastName.length() < 2 || hasOnlyLetters == false) {
			//Print correction to screen.
			System.out.println("Please ensure that your first name is between\n2 and 15 characters and contains only letters");
			System.out.print("-->");
			lastName = scanner.next();

			// Make sure that the input is not "0"
			if (lastName.equals("0")) {
				mainMenu.displayMenuItems();
			}
			
			//Checks if there are any non-alphabetical characters within.
			hasOnlyLetters = true;
			for (int i = 0; i < lastName.length(); i++) {
				if (Character.isLetter(firstName.charAt(i)) == false) {
					hasOnlyLetters = false;
				}
			}
		}
		
		//USERNAME ENTRY		
		System.out.println("Please enter a username or 0 to go back to the main Menu.");
		System.out.print("-->");

		// Create a string for the userName that will be entered by the user.
		String userName = scanner.next();

		// Making sure that the input is not "0"
		if (userName.equals("0")) {
			mainMenu.displayMenuItems();
		}

		hasOnlyLetters = true;
		
		// DATA RESTRAINT #1: UserName must be between 3 and 15 characters, and must only contains letters.

		for (int i = 0; i < userName.length(); i++) {
			if (Character.isLetter(userName.charAt(i)) == false) {
				hasOnlyLetters = false;
			}
		}

		while (userName.length() > 15 || userName.length() < 3 || hasOnlyLetters == false) {
			System.out.println(
					"Please ensure that your username is\nbetween 3 and 15 characters and contains only letters.");
			System.out.print("-->");
			userName = scanner.next();

			// Making sure that the input is not "0"
			if (userName.equals("0")) {
				mainMenu.displayMenuItems();
			}

			hasOnlyLetters = true;
			for (int i = 0; i < userName.length(); i++) {
				if (Character.isLetter(userName.charAt(i)) == false) {
					hasOnlyLetters = false;
				}
			}
		}
		
		//PASSWORD ENTRY
		System.out.println("Please enter a password or BACK to go back to the main Menu.");
		System.out.print("-->");

		// Create a string for the passWord that will be entered by the user.
		String passWord = scanner.next();

		// Making sure that the input is not "0"
		if (passWord.equals("0")) {
			mainMenu.displayMenuItems();
		}

		// DATA RESTRAINT #2: Password must be between 5 and 15 characters.

		while (passWord.length() > 15 || passWord.length() < 5) {
			System.out.println("Please ensure that your password is between 5 and 15 characters.");
			System.out.print("-->");
			passWord = scanner.next();

			// Making sure that the input is not "0"
			if (passWord.equals("0")) {
				mainMenu.displayMenuItems();
			}
		}

		// Send that data to registerService to be put into the database.
		registerService.registerNewUser(firstName, lastName, userName, passWord, mainMenu, registerMenu);
	}
}
