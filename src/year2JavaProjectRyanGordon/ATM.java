package year2JavaProjectRyanGordon;

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class ATM implements Overdraft{
	public static void main(String[] args) throws FileNotFoundException {
		
		
		Scanner sc = new Scanner(System.in);
		Bank theBank = new Bank("Gordon Allied National Bank");	//title of bank	
		Customer defaultUser = theBank.addUser("Ryan", "Gordon", "0000");
		
		Account newAccount = new Account("Current Account", defaultUser, theBank); // add a default Current and Credit account for user
		defaultUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		Account newCreditAccount = new CreditAccount("Credit Account", defaultUser, theBank, 1000);
		defaultUser.addAccount(newCreditAccount);
		theBank.addAccount(newCreditAccount);
		
		
		Customer overdraftCustomer = theBank.addUser("Naomi", "Hurley", "1234"); //create test user 2
		
		Account newCreditAccount2 = new CreditAccount("Credit Account", overdraftCustomer, theBank, 1000); // add a creditAccount for this user as well as a savings account
		defaultUser.addAccount(newCreditAccount2);
		theBank.addAccount(newCreditAccount2);
		
		Customer user;
		
		
		while (true) { //loop which runs until program closes/ is quit
			
			// stay in login prompt until successful login
			user = ATM.mainMenuPrompt(theBank, sc);
			
			// stay in main menu until user quits
			ATM.customerMenu(user, sc);
			
		}

	}
	
	public static Customer mainMenuPrompt(Bank theBank, Scanner sc) { //Print the ATM's login menu.
		
		String userID;
		String pin;
		Customer authUser;
		
		// prompt user for user ID/pin combo until a correct one is reached
		do {
			
			
			System.out.println(" _____________________Acme ATM Company___________________");			
			System.out.print("|\t\t\t\t\t\t\t| \n");			
			System.out.printf("|\tWelcome to %s\t\t|\n", theBank.getName());
			System.out.print("|\t\t\t\t\t\t\t| \n");
			System.out.println("|_______________________________________________________|");
			System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
			System.out.println("|_________________________Log In________________________|");
			System.out.print("|\tPlease log into your account to continue \t|\n");
			System.out.print("| Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("|Enter pin: ");
			pin = sc.nextLine();
			
			
			authUser = theBank.userLogin(userID, pin); // try to get user object corresponding to ID and pin combo
			if (authUser == null) {
				System.out.println("Incorrect user ID/pin combination. " + 
						"Please try again");
			}
			System.out.println("|_______________________________________________________|");
			System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
			System.out.println("|__________________Log In Success_______________________|");
			System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
			
		} while(authUser == null); 	// continue looping until successful login by user or quit 
		
		
		return authUser;
		
	}
	
	public static void customerMenu(Customer theUser, Scanner sc) throws FileNotFoundException { //displays ATMS menu and available commands
		
		
		System.out.println("|_______________________________________________________|");
		theUser.printAccountsSummary(); // print a summary of the user's accounts
		
		int choice;
		
		do {// user menu
			
			System.out.println("|What would you like to do?");
			System.out.println("|  1) Deposit ");
			System.out.println("|  2) Withdraw");
			System.out.println("|  3) Transfer");
			System.out.println("|  4) Show account transaction history");
			System.out.println("|  5) Print account transaction history");
			System.out.println("|  6) Overdraft options");
			System.out.println("|  7) Logout of/Quit the Application");
			System.out.println();
			System.out.print("|Enter choice: ");
			choice = sc.nextInt();
			
			if (choice < 1 || choice > 7) {
				System.out.println("Invalid choice. Please choose 1-7.");
			}
			
		} while (choice < 1 || choice > 7);
		
		
		switch (choice) { // process choice
		
		case 1:
			ATM.depositFunds(theUser, sc);
			break;
		case 2:
			ATM.withdrawFunds(theUser, sc); //withdraw funds from account
			break;
		case 3:
			ATM.transferFunds(theUser, sc); //transfer from one account to other
			break;
		case 4:
			ATM.showTransHistory(theUser, sc); //prints all transactions for user on screen
			break;
		case 5:
			ATM.PrintTransHistory(theUser, sc); //prints all transactions for user into a text file
			break;
		case 6:
			System.out.println("Please contact your branch to discuss available overdraft options \n"); //overdraft options
			ATM.overdraftMenu(theUser,sc);
			break;
		
		case 7:
			sc.nextLine(); //flush buffer
			break;
		}
		
		
		if (choice != 7) {
			ATM.customerMenu(theUser, sc); //display menu until user wants to quit
		}
		
	}
	
	public static void overdraftMenu(Customer theUser, Scanner sc) throws FileNotFoundException{
		
		theUser.printAccountsSummary();
		
		
		int choice;
		int odAcct;
		
		
		do {//overdraft menu
			
			System.out.println("What would you like to do?");
			System.out.println("  1) Check Overdraft Balance");
			System.out.println("  2) <Error payment features in progress>");
			System.out.println("  3) Go back");
			System.out.println();
			System.out.print("Enter choice: ");
			choice = sc.nextInt();
			
			if (choice < 1 || choice > 3) {
				System.out.println("Invalid choice. Please choose 1-5.");
			}
			
		} while (choice < 1 || choice > 3);
		
		// process the choice
		switch (choice) {
		
		case 1:
			System.out.println("Overdraft account balance ");
			
			do {// get account to work with overdraft from
				System.out.printf("Enter the number (1-%d) of the account to " + 
						"do overdraft functions with: ", theUser.numAccounts());
				odAcct = sc.nextInt()-1;
				if (odAcct < 0 || odAcct >= theUser.numAccounts()) {
					System.out.println("Invalid account. Please try again.");
				}
				else if (Account.getName().equals("Credit Account") == false){ //if the account does not have overdraft give this message
					System.out.println("Account is not eligble for overdraft, please contact your bank to remedy this.");
					ATM.customerMenu(theUser, sc);//brings ineligible user back to normal menu
				}
				else{
					System.out.println("Overdraft balance is: ");
					theUser.getODRate();
					
				}
			} while (odAcct < 0 || odAcct >= theUser.numAccounts());
			
			break;
		case 2:
			System.out.println("Invalid choice. ");
			break;
			
		case 3:
			ATM.customerMenu(theUser, sc);
			break;
		}
		
		
		if (choice != 3) {// display menu until user wants to quit
			ATM.customerMenu(theUser, sc);
		}
		
		
	}
	

	public Object Start() { //used to access the abstract payOverDraft method with static
		payOverDraft();
		return null;
	}
	public static void transferFunds(Customer theUser, Scanner sc) { //Process transfer from one account to another.
		
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		if (theUser.numAccounts()!=1){		
		do {// get account to transfer from
			System.out.printf("Enter the number (1-%d) of the account to " + 
					"transfer from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		}
		else{
			fromAcct=1;
		}
		
		acctBal = theUser.getAcctBalance(fromAcct);
		
		
		do {// prompt for account to transfer to
			System.out.printf("Enter the number (1-%d) of the account to " + 
					"transfer to: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		
		
		do {// prompt for amount to transfer
			System.out.printf("Enter the amount to transfer (max € %.02f): $", 
					acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than balance " +
						"of $.02f.\n", acctBal);
			}
		} while (amount < 0 || amount > acctBal);
		
		
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format( 
				"Transfer to account %s", theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, String.format(
				"Transfer from account %s", theUser.getAcctUUID(fromAcct))); // do the transfer 
		
	}
	
	public static void withdrawFunds(Customer theUser, Scanner sc) { //process a withdrawl from an account
		
		int withdrawAcct;
		double amount;
		double acctBal;
		String txReference;
		
		if (theUser.numAccounts()!=1){
		do {// get account to withdraw from
			System.out.printf("Enter the number (1-%d) of the account to " + 
					"withdraw from: ", theUser.numAccounts());
			withdrawAcct = sc.nextInt()-1;
			if (withdrawAcct < 0 || withdrawAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (withdrawAcct < 0 || withdrawAcct >= theUser.numAccounts());
		}
		else{
			withdrawAcct=1;
		}
		acctBal = theUser.getAcctBalance(withdrawAcct);
		
		// get amount to transfer
		do {
			System.out.printf("Enter the amount to withdraw (max € %.02f): € ", 
					acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than balance " +
						"of $%.02f.\n", acctBal);
			}
		} while (amount < 0 || amount > acctBal);
		
		// gobble up rest of previous input
		sc.nextLine();
		
		// get a memo
		System.out.print("Enter a memo: ");
		txReference = sc.nextLine();
		
		
		theUser.addAcctTransaction(withdrawAcct, -1*amount, txReference);
		
	}
	
	public static void depositFunds(Customer theUser, Scanner sc) { //process a deposit into an account
		
		int toAcct;
		double amount;
		String txReference;
		
		if (theUser.numAccounts()!=1){
		do {// get account to deposit to
			System.out.printf("Enter the number (1-%d) of the account to " + 
					"deposit to: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		}
		else{
			toAcct=1;
		}
		
		// get amount to transfer
		do {
			System.out.printf("Enter the amount to deposit: € ");
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} 
		} while (amount < 0);
		
		// gobble up rest of previous input
		sc.nextLine();
		
		// get a note
		System.out.print("Enter a Reference: ");
		txReference = sc.nextLine();
		
		// do the deposit
		theUser.addAcctTransaction(toAcct, amount, txReference);
		
	}
	
	public static void showTransHistory(Customer theUser, Scanner sc) {
		
		int theAcct=0;
		
		
		if (theUser.numAccounts()!=1){
		
		do {
			System.out.printf("Enter the number (1-%d) of the account\nwhose " + 
					"transactions you want to see: ", theUser.numAccounts());
			theAcct = sc.nextInt()-1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (theAcct < 0 || theAcct >= theUser.numAccounts()); //
		}
		else{
			theAcct=1;
		}
		
		theUser.displayAcctHistory(theAcct); // print the transaction history
		
	}
	
	public static void PrintTransHistory(Customer theUser, Scanner sc)  throws FileNotFoundException{ //prints transactions to a file, if no file exists it creates a file
		
		int theAcct=0;
		
		if (theUser.numAccounts()!=1){ //prompt for which account if there are more than one
		do {
			System.out.printf("Enter the number (1-%d) of the account\nwhose " + 
					"transactions you want to print to file: ", theUser.numAccounts());
			theAcct = sc.nextInt()-1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (theAcct < 0 || theAcct >= theUser.numAccounts());
		}
		else{
			theAcct=1;
		}
		
		File transactionHistoryFile = new File ("Desktop/transactionHistory.txt"); //creates file to print transactions to
		PrintWriter transPrinter = new PrintWriter("transactionHistory.txt");//print transactions to file
		
		theUser.printAcctHistory(theAcct, transPrinter);
		
		
		transPrinter.close();
		
		
	}


	public  void payOverDraft(){//takes input from user on desired amount to pay, prompts to pick an account to subtract from, sum is taken 
		
		
	
	}
	public void checkOverDraftRate(){//displays OD rates to customer
		
	}


}
