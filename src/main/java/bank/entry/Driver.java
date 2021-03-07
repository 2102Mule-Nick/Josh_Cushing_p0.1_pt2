package bank.entry;

import bank.menu.LoginMenu;
import bank.menu.MainMenu;
import bank.menu.RegisterMenu;

public class Driver {

	public static void main(String[] args) {

		// Call the singleton method of the MainMenu class to instantiate the singleton mainMenu object.
		// Then, call its displayMenuItems method.
		// This method takes 2 arguments, a loginMenu object and a registerMenu object.
		// Pass these by calling their singleton methods.
		(MainMenu.theOnlyMainMenu()).displayMenuItems(LoginMenu.theOnlyLoginMenu(), RegisterMenu.theOnlyRegisterMenu());
	}
}
