package bank.menu;

import java.util.Scanner;

import bank.services.RegisterService;

public class RegisterMenu {
	//Make a field that is an object of the RegisterMenu class. Set it to null.
		private static RegisterMenu registerMenu = null;
		
		RegisterService registerService = new RegisterService();
		
		//Also make a field that is a public scanner.
		public Scanner scanner = new Scanner(System.in);
		
		//Create the singleton method that allows only one RegisterMenu object to ever be instantiated.
		public static synchronized RegisterMenu theOnlyRegisterMenu() {
			if (registerMenu == null) {
				registerMenu = new RegisterMenu();
			}
			return registerMenu;
		}
		public void displayRegisterBox(){
			registerService.registerNewUser();
		}
}
