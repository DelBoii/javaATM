package year2JavaProjectRyanGordon;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Account {
	
	private static String name;
	private String uuid;
	
	private Customer accHolder; //the customer object that owns the account
	private ArrayList<AccountTransaction> transactions; //The list of transactions for this account.
	
	
	public Account(String name, Customer holder, Bank theBank) { //create a account object
		
		
		this.name = name;
		this.accHolder = holder; // set the account name and holder
		
		// get next account UUID
		this.uuid = theBank.getNewAccountUUID();
		
		// init transactions
		this.transactions = new ArrayList<AccountTransaction>();
		
	}
	
	
	public String getUUID() {
		return this.uuid;
	}
	
	public void addTransaction(double amount) {
		AccountTransaction newTrans = new AccountTransaction(amount, this);  //add transaction to list
		this.transactions.add(newTrans);
		
	}
	public void addTransaction(double amount, String note) { //add transaction to list
		
		
		AccountTransaction newTrans = new AccountTransaction(amount, note, this); // create new transaction and add it to our list
		this.transactions.add(newTrans);
		
	}
	
	public double getBalance() { //get balance of this account by adding its transactions' values
		
		double balance = 0;
		for (AccountTransaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
		
	}
	public static String getName(){
		return name;
	}
	
	public String getSummaryLine() {// Get summary for account
		
		double balance = this.getBalance(); // get the account's balance
		
		
		if (balance >= 0) {
			return String.format("%s : € %.02f : %s", this.uuid, balance,  //format line as normal
					this.name);
		} else {
			return String.format("%s : € (%.02f) : %s", this.uuid, balance,  //in event of negative value
					this.name);
		}
		
	}
	
	public void printTransHistory() { //print transactions
		
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for (int t = this.transactions.size()-1; t >= 0; t--) {
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
		
	}
	public void filePrintTransHistory(PrintWriter writer) { //prints transactions to a text file
	
		writer.printf("\nTransaction history for account %s\n", this.uuid);
		for (int t = this.transactions.size()-1; t >= 0; t--) {
			writer.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
		
	}
}//account

