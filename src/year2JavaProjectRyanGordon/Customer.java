package year2JavaProjectRyanGordon;

import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Customer {
	
	private String firstName;
	private String lastName;
	private String uuid; // ID number for each customer
	private byte accountPinHash[]; //The hash of the user's pin number.
	private ArrayList<Account> custsAccounts; //The list of accounts for this user.
	
	public Customer (String firstName, String lastName, String pin, Bank theBank) { //create new customer with given pin, name and branch
		
		// set user's name
		this.firstName = firstName;
		this.lastName = lastName;
		
		// store the pin's MD5 hash, rather than the original value, for 
		// security reasons
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.accountPinHash = md.digest(pin.getBytes());
		} catch (Exception e) {
			System.err.println("error, caught exeption : " + e.getMessage());
			System.exit(1);
		}
		
		// get a new, unique universal unique ID for the user
		this.uuid = theBank.getNewUserUUID();
		
		// create empty list of accounts
		this.custsAccounts = new ArrayList<Account>();
		
		// print log message
		System.out.printf("New user %s, %s with ID %s created.\n", 
				lastName, firstName, this.uuid);
		
	}

	public String getUUID() {
		return this.uuid;
	}
	
	public void addAccount(Account anAcct) {
		this.custsAccounts.add(anAcct);
	}
	
	public int numAccounts() {
		return this.custsAccounts.size(); //get number of user accounts
	}
	
	public double getAcctBalance(int acctIdx) {
		return this.custsAccounts.get(acctIdx).getBalance(); 
	}
	
	public double getoDBalance(int acctIdx) {
		return this.custsAccounts.get(acctIdx).getBalance(); 
	}
	
	public String getAcctUUID(int acctIdx) { 
		return this.custsAccounts.get(acctIdx).getUUID();
	}
	
	public void displayAcctHistory(int acctIdx) {
		this.custsAccounts.get(acctIdx).printTransHistory(); //displays custs history on screen
	}
	public void printAcctHistory(int acctIdx, PrintWriter writer) {
		this.custsAccounts.get(acctIdx).filePrintTransHistory(writer); //prints customers history to a text file for them
	}
	
	public void addAcctTransaction(int acctIdx, double amount, String memo) {
		this.custsAccounts.get(acctIdx).addTransaction(amount, memo);
	}
	
	public boolean validatePin(String aPin) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), //tries to valid the given pin with the pin on file's MD5 hash
					this.accountPinHash);
		} catch (Exception e) {
			System.err.println("error, caught exeption : " + e.getMessage()); 
			System.exit(1);
		}
		
		return false; //if pin matches allow access if not valid return false
	}
	
	public void printAccountsSummary() { //prints a summary of all accounts for the user 
		
		System.out.printf("|\t\t\t\t\t\t\t|\n|%s's accounts summary\t\t\t\t|\n", this.firstName);
		System.out.println("|_______________________________________________________|");
		for (int a = 0; a < this.custsAccounts.size(); a++) {
			System.out.printf("%d) %s\n", a+1, 
					this.custsAccounts.get(a).getSummaryLine());
		}
		System.out.println();
		
	}
	public void getODRate (){
		for (int a = 0; a < this.custsAccounts.size(); a++) {
			System.out.printf("%d) %s\n", a+1, 
					this.custsAccounts.get(a).getBalance());
		}
		
		
	}
	
}//customer
