package bank.pojo;

public class User {

	//POJO fields
	private String userName;
	private String passWord;
	private String firstName;
	private String lastName;
	private double checkingBal;
	private double savingBal;
	
//Getters and Setters
//--------------------------------------------------
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
//--------------------------------------------------
	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
//--------------------------------------------------
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
//--------------------------------------------------
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
//--------------------------------------------------
	public double getCheckingBal() {
		return checkingBal;
	}

	public void setCheckingBal(double checkingBal) {
		this.checkingBal = checkingBal;
	}
//--------------------------------------------------
	public double getSavingBal() {
		return savingBal;
	}

	public void setSavingBal(double savingBal) {
		this.savingBal = savingBal;
	}
//--------------------------------------------------
	
	// no-args constructor
	public User() {
		super();
		this.userName = "";
		this.passWord = "";
		this.firstName = "";
		this.lastName = "";
		this.checkingBal = 0;
		this.savingBal = 0;
	}
	
	// all-args constructor
	public User(String userName, String passWord, String firstName, String lastName, double checkingBal, double savingBal) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.firstName = firstName;
		this.lastName = lastName;
		this.checkingBal = checkingBal;
		this.savingBal = savingBal;
	}
}
