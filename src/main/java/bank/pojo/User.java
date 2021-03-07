package bank.pojo;

public class User {
//Since this be set byt the DB I'm not sure I need this.
	//	private String userId; 
	
	private String userName;
	private String passWord; 
	private double checkingBal;
	private double savingBal;
//--------------------------------
	
/*
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
*/
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public double getCheckingBal() {
		return checkingBal;
	}
	public void setCheckingBal(double checkingBal) {
		this.checkingBal = checkingBal;
	}
	
	public double getSavingBal() {
		return savingBal;
	}
	public void setSavingBal(double savingBal) {
		this.savingBal = savingBal;
	}
//----------------------------------------------------

	//no-args constructor
	public User() {
		this.userName = "";
		this.passWord = "";
		this.checkingBal = 0;
		this.savingBal = 0;
	}
	
	// args constructor
	public User(String userName, String passWord) {
		super();
		//this.userId = 0;
		this.userName = userName;
		this.passWord = passWord;
		this.checkingBal = 0;
		this.savingBal = 0;
	}	
	
	// all-args constructor
		public User(String userName, String passWord, double checkingBal, double savingBal) {
			super();
			//this.userId = 0;
			this.userName = userName;
			this.passWord = passWord;
			this.checkingBal = checkingBal;
			this.savingBal = savingBal;
		}
}
