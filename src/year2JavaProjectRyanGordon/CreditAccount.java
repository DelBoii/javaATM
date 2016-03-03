package year2JavaProjectRyanGordon;

import java.util.ArrayList;

public class CreditAccount extends Account {
	

	
	private ArrayList<AccountTransaction> transactions;
	private double overDraftLimit;
	private String name;

	public CreditAccount(String name, Customer holder, Bank theBank,double overDraftLimit) {
		super(name, holder, theBank);
		// TODO Auto-generated constructor stub
		
		this.name = name;
		
		 this.overDraftLimit = overDraftLimit;	//can be negative or positive will allow a user to pay their over draft if their limit is minus and check the interest rate on OD sums
		
		this.transactions = new ArrayList<AccountTransaction>();
		
		
	}
	
	public double getBalance() { //get balance of this account by adding its transactions' values
		
		double balance = 0;
		balance = overDraftLimit;
		return balance;
		
	}
	
	
	
	

}//creditAccount
