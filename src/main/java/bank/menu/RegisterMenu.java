package bank.menu;

import java.util.Scanner;

import bank.pojo.User;
import bank.services.RegisterService;

public class RegisterMenu {
	
	// Make a field that is an object of the RegisterMenu class. Set it to null.
	private static RegisterMenu registerMenu = null;

	// Create the singleton method that allows only one RegisterMenu object to ever
		// be instantiated.
		public static synchronized RegisterMenu theOnlyRegisterMenu() {
			if (registerMenu == null) {
				registerMenu = new RegisterMenu();
			}
			return registerMenu;
		}
		
	// Instantiate RegisterService to pass the data to once registration is done.
	RegisterService registerService = new RegisterService();

	

	//Additionally, a field to store a final value for going back one menu.
	final String back = "BACK";
	
	// Also make a field that is a public scanner.
		public Scanner scanner = new Scanner(System.in);
//---------------------------------------------------------------------------------------
	
	public void displayRegisterBox(MainMenu mainMenu) {

		//Instructions
		System.out.println("Please enter a username or BACK to go back to the main Menu.");
		System.out.print("-->");

		// Create a string for the userName that will be entered by the user.
		String userName = scanner.next();
		
		//Making sure that the input is not "BACK"
		if(userName.equals(back)) {
			mainMenu.displayMenuItems();
		}
		
// ------------------------------------------------------------------------------------------------
// DATA RESTRAINT #1: UserName must be between 3 and 15 characters, and must
// only contains letters.

		Boolean hasOnlyLetters = true;
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

			//Making sure that the input is not "BACK"
			if(userName.equals(back)) {
				mainMenu.displayMenuItems();
			}
			
			hasOnlyLetters = true;
			for (int i = 0; i < userName.length(); i++) {
				if (Character.isLetter(userName.charAt(i)) == false) {
					hasOnlyLetters = false;
				}
			}
		}

		// If the proper type of username is entered, we make it the password prompt.
		//Instructions
			System.out.println("Please enter a password or BACK to go back to the main Menu.");
			System.out.print("-->");

		// Create a string for the passWord that will be entered by the user.
		String passWord = scanner.next();
		
		//Making sure that the input is not "BACK"
		if(userName.equals(back)) {
			mainMenu.displayMenuItems();
		}
		
// ---------------------------------------------------------------------------------------------------------
// DATA RESTRAINT #2: Password must be between 5 and 15 characters.

		while (passWord.length() > 15 || passWord.length() < 5) {
			System.out.println("Please ensure that your password is between 5 and 15 characters.");
			System.out.print("-->");
			passWord = scanner.next();
			
			//Making sure that the input is not "BACK"
			if(userName.equals(back)) {
				mainMenu.displayMenuItems();
			}
		}

		// Create a new user object using the userName/passWord constructor.
		User user = new User(userName, passWord);

		// Send that user to registerService to be put into the database.
		registerService.registerNewUser(user, mainMenu, registerMenu);
	}
}
