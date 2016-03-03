package year2JavaProjectRyanGordon;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Bank {
	
	
	private String name; //The name of the bank.
	private ArrayList<Customer> usersList; //The account holders of the bank.
	private ArrayList<Account> accountsList; //The accounts of the bank.
	
	public Bank(String name) {
		
		this.name = name;
		
		usersList = new ArrayList<Customer>();
		accountsList = new ArrayList<Account>();
		
	}
	public String getNewUserUUID() {  //generate a new unique ID for an account
		
		String uuid;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		
		
		do {
			
			
			uuid = "";
			for (int c = 0; c < len; c++) {
				uuid += ((Integer)rng.nextInt(10)).toString(); // generate the number
			}
			
			
			nonUnique = false;
			for (Customer u : this.usersList) {
				if (uuid.compareTo(u.getUUID()) == 0) {  //if the number is unique, we will use
					nonUnique = true;
					break;
				}
			}
			
		} while (nonUnique); // continue looping until we get a unique ID
		
		return uuid;
	}
	
	public String getNewAccountUUID() { //generate a new unique ID for an account
		
		String uuid;
		Random rng = new Random();
		int len = 10;
		boolean nonUnique = false;
		
		do {
			uuid = "";
			for (int c = 0; c < len; c++) {
				uuid += ((Integer)rng.nextInt(10)).toString(); //generates a number
			}
			
			for (Account a : this.accountsList) {
				if (uuid.compareTo(a.getUUID()) == 0) { //if the number is unique, we will use
					nonUnique = true;
					break;
				}
			}
			
		} while (nonUnique); // continue looping until we get a unique ID
		
		return uuid;
				
	}

	public Customer addUser(String firstName, String lastName, String pin) { //creates a new user in the banks system
		
		// create a new User object and add it to our list
		Customer newUser = new Customer(firstName, lastName, pin, this);
		this.usersList.add(newUser);
		
		// create a savings account for the user and add it to our list
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.accountsList.add(newAccount);
		
		return newUser;
		
	}
	
	public void addAccount(Account newAccount) { 
		this.accountsList.add(newAccount);
	}

	public Customer userLogin(String userID, String pin) {	//checks using given info is given and if so opens account
		
		
		for (Customer u : this.usersList) { // search until end of list of users
			
			
			if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) { // if user found, and pin is correct, return User object
				return u;
			}
		}
		
		return null; //if pin incorrect give back null
		
	}
	public String getName() {
		return this.name;
	}

}//bank
