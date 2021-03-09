package bank.entry;

import bank.menu.MainMenu;

public class Driver {
	public static void main(String[] args) {
		// Call the singleton method of the MainMenu class to instantiate 
		// the only mainMenu object. Then, call its displayMenuItems method.
		(MainMenu.theOnlyMainMenu()).displayMenuItems();
	}
}
