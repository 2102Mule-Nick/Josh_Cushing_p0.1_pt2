package bank.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import bank.db.ConnectBox;
import bank.menu.LoginMenu;
import bank.menu.MainMenu;
import bank.menu.RegisterMenu;
import bank.services.LoginService;
import bank.services.TransactionService;

@ExtendWith(MockitoExtension.class)
class functiontest {

	static Logger log = Logger.getLogger(functiontest.class.getName());

	@Spy
	LoginService spyLoginService = new LoginService();
	
	@Mock
	LoginService loginServiceMock;
	
	@Mock
	TransactionService transactionServiceMock;
	
	@BeforeEach
	public void setUp() throws Exception {
		// TODO setUp function
	}

	@AfterEach
	public void tearDown() throws Exception {
		// TODO tearDown function
	}

	// 1
	@Test
	public void testMainMenuSingleton() {
		assertNotNull("Initial main menu call must not be null", MainMenu.theOnlyMainMenu());
	}

	// 2
	@Test
	public void testRegisterMenuSingleton() {
		assertNotNull("Initial register menu call must not be null", RegisterMenu.theOnlyRegisterMenu());
	}

	// 3
	@Test
	public void testLoginMenuSingleton() {
		assertNotNull("Initial login menu call must not be null", LoginMenu.theOnlyLoginMenu());
	}
	
/*
	// 4
	@Test
	public void testConnection() {
		// Can't get this test to function even though I can always connect when the application is running.
		// Will ask for help later.
		assertNotNull("Connection must not be null", ConnectBox.getConnection());
	}
	
	// 5 -- SPY TEST
	@Test
	public void testSpyLoginService() {
		MainMenu mainMenu = new MainMenu();
		LoginMenu loginMenu = new LoginMenu();
		String userName = "joscus";
		String passWord = "password";
		spyLoginService.logInUser(userName, passWord, mainMenu, loginMenu);
		
		Mockito.verify(spyLoginService).logInUser(userName, passWord, mainMenu, loginMenu);
	}
*/

}