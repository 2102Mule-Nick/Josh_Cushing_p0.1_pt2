package bank.menu;

import java.util.Scanner;

import bank.services.LoginService;

public class LoginMenu {
	// Make a field that is an object of the LoginMenu class. Set it to null.
	private static LoginMenu loginMenu = null;

	LoginService loginService = new LoginService();
	
	// Also make a field that is a public scanner.
	public Scanner scanner = new Scanner(System.in);

	// Create the singleton method that allows only one LoginMenu object to ever be
	// instantiated.
	public static synchronized LoginMenu theOnlyLoginMenu() {
		if (loginMenu == null) {
			loginMenu = new LoginMenu();
		}
		return loginMenu;
	}
	
	public void displayLoginBox(){
		loginService.logInUser();
	}
	
}
