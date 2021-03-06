package bank.entry;

import java.util.Scanner;

import bank.menu.LoginMenu;
import bank.menu.MainMenu;
import bank.menu.RegisterMenu;

public class Driver {
	Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		// Call the singleton method of the MainMenu class to instantiate
		// the only mainMenu object and call its displayMenuItems method.
		// This method takes 2 arguments, a loginMenu and a registerMenu.
		// Pass these via singleton methods of their own.
		(MainMenu.theOnlyMainMenu()).displayMenuItems(LoginMenu.theOnlyLoginMenu(), RegisterMenu.theOnlyRegisterMenu());
	}
}
