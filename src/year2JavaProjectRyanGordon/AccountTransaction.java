package year2JavaProjectRyanGordon;

import java.util.Date;

public class AccountTransaction {
	
	private double amount;
	private Date timestamp; //The time and date of this transaction.
	private String txReference; //a note for the transaction
	private Account inAccount;
	
	public AccountTransaction(double amount, Account inAccount) { //create transaction
		
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.txReference = "";
		
	}
	
	public AccountTransaction(double amount, String txReference, Account inAccount) { //create tx with a note
		
		// call the single-arg constructor first
		this(amount, inAccount);
		
		this.txReference = txReference;
		
	}
	
	public double getAmount() { //get tx amount
		return this.amount;
	}
	
	public String getSummaryLine() {  //returns a string summarizing the tx
		
		if (this.amount >= 0) {
			return String.format("%s, € %.02f : %s", 
					this.timestamp.toString(), this.amount, this.txReference);
		} else {
			return String.format("%s, € (%.02f) : %s", 
					this.timestamp.toString(), -this.amount, this.txReference);
		}
	}

}//accountTransaction
