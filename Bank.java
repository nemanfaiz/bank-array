 import java.util.*;
import java.lang.*;

public class Bank {
  static BankAccount[] accounts = new BankAccount[1];//This array will point to all the BankAccount objects
  static int noOfAccs = 0;
  static int currentIndex = -1;
  //Keeps track of the total number of accounts
  //static double balance;
  
  //static String  name;
  //static char userSelection;
  
  public static void main(String[] args) { 
    //call bankMenu
    bankMenu();
  }
  
  //bankMenu runs the main part of the program, until user selects 'Q'
  static void bankMenu(){
    Scanner reader = new Scanner(System.in);
    String selection;
      //This variable tells us whether we are pointing to a valid account 
    //char userSelection;
    do
    {  
        //display menu and ask for user selection
      printMenu();
      System.out.println("type one of the given prompts?");
      selection = reader.next(); 
      
      switch(selection) {
          case "O": //Open account
            currentIndex++;
            openAcc(currentIndex);
            if(noOfAccs == accounts.length)
                accounts = resize();
            //make sure you have enough space or double size OF accounts array
            //make sure the account number is not a duplicate. Assign array index to account
            //set the current index;
          
            //increment the number of accounts
            //noOfAccs++;
            break;
          case "D": //Deposit 
          //deposit only if currentIndex is not -1. you are depositing into a particular account 
            if(currentIndex != -1)        // if(currentIndex == accounts[i].getAcc())
                accounts[currentIndex].deposit();
            else
                System.out.println("select your account");
                break;
          
        case "S": //Select account
          //look for account and if it exists, set currentIndex to it  
          if (noOfAccs > 0)
              currentIndex = selectAcc();
          else
            System.out.println("no account is selected");
          break;
          
        case "C": //Close account
          //if currentindex is not -1 close the account and reset currentIndex  
          if(currentIndex!=-1) 
            {
               closeAcc();
               currentIndex = -1;
            }
           else
               System.out.println("select an account? ");
          break;
          
        case "W": //Withdraw
          //if current index is not -1, withdraw
          if(currentIndex > -1)         
            accounts[currentIndex].withdraw();
          else
            System.out.println("select an account? ");
          break;
          
        case "L": //List accounts
          //traverse through all the accounts and display their information
          if (noOfAccs < 1)
            System.out.println("there is no account!");
          else 
            listAccounts();
          break;
          
        case "Q": //Quit
            System.out.println("You quit!");
            break;
          //end the program
      }
    System.out.println("Current Index: " + currentIndex);
    System.out.println("Number of accounts: "+ noOfAccs);
    
    }while(selection != "Q");
  }
  //Print the menu, takes index of currently selected account
  static void printMenu(){
    //display menu 
    
    System.out.println("O: open\n" + "D: deposit\n" + "S: select\n" 
                       + "C: close\n" + "W: withdraw\n" + "L: list\n" + "Q: quit");
    if(currentIndex != -1)
    {
        System.out.println("Current account is: "+ accounts[currentIndex].getAcc() +
                            "\nand the Balance is : $"+ accounts[currentIndex].getBalance());
    }
      
  }

 
  static BankAccount openAcc(int currentIndex){
      //Grab account number
      Scanner reader = new Scanner(System.in);
      
      
      int inputAcc;
      double gotBalance = 0;
      
      //validate for duplicate account number 
      boolean duplicate = false;
      do
      {
          System.out.println("Enter account number?");
          inputAcc = reader.nextInt();
          first:
          for(int i = 0; i < noOfAccs; i++) ///do while for reasking the question if duplicate was found
          {
              if(inputAcc == accounts[i].getAcc())
              {
                System.out.println("duplicate account was found");
                duplicate = true;
                break first;
              }
              else
                duplicate = false;
          }
      }while(duplicate);
      
     
                
      System.out.println("input balance? ");
      gotBalance = reader.nextDouble();    
        
      //Grab balance
      accounts[currentIndex] = new BankAccount(inputAcc, gotBalance);   //initializing the balance with new bankaccount object
    
      //create new account and return the object so that the array index can point to the 
      //newly created object
      noOfAccs++;
      return accounts[currentIndex]; // what about index we are passing?
  }
  
  static BankAccount[] resize()
  {
    //resize array. Double the size
    BankAccount temp[] = new BankAccount[accounts.length * 2];
    
    System.arraycopy(accounts, 0, temp,0, accounts.length);
    accounts = temp;
    return accounts;
  }

  static void listAccounts()
  {
    //Go through all the accounts using a for loop and display their content
    for (int i = 0; i < noOfAccs; i++) 
    {
        System.out.println( (i+1) + ")   Account is: " + accounts[i].getAcc() + "   and balance is: $" + accounts[i].getBalance());
    }
  }

  static int selectAcc()
  {
    Scanner reader = new Scanner (System.in);
    //ask for the account number, check to see if it exists and return index
    System.out.println("Enter the account? ");
    int accNbr = reader.nextInt();
    //int index = 0;
    
    boolean find = false;
    first:
    for (int i = 0; i < accounts.length; i++)
    {
        if (accNbr == accounts[i].getAcc())
        {
            currentIndex =  i;
            find = true;
            break first;
         }
    }
    if (!find)
      System.out.println("Account number was not found :");
    
    /*if (accNbr != accounts.getAcc())
        System.out.println("we could not find it. "); */
    return currentIndex;
  }
  
  static void closeAcc()
  {
    //move last account to the index that needs to be deleted 
    //accounts[index-1].getAcc() = index;
    //set last account to null
    accounts[currentIndex] = null;
    if (noOfAccs > 1) 
      
      accounts[currentIndex] = accounts[noOfAccs - 1]; 
       
    //decrement noOfAccts variable
      
    noOfAccs--;
   
  }
}

class BankAccount{
  private int accNbr;
  private double balance;
  
  BankAccount(int accNbr, double balance){
    //set instance variables
    this.accNbr = accNbr;
    this.balance = balance;
  }
  
  int getAcc(){
    //return accountNumber
    return accNbr;
  }
  
  double getBalance(){
   //return balance
   return balance;
  }
  
  void deposit(){     
    //add to deposit  
     Scanner reader = new Scanner(System.in);
     double inputDeposit;
     
     System.out.println("input the amount for deposite?");
     inputDeposit = reader.nextDouble();
     
     balance += inputDeposit;
  }
  
  void withdraw (){
    //withdraw as long as there is still $1 in the account
    //this.balance = amount;
    boolean check;
    do 
    {
        Scanner reader = new Scanner(System.in);
        double inputWithdraw;
        
        System.out.println("input the amount for withdraw?");
        inputWithdraw = reader.nextDouble();
        
        if ((balance - inputWithdraw) < 1)
        {
            System.out.println("you need to have at least $1 in your balance!");
            check=true;
        }
        else
        {
            check = false;
            balance -= inputWithdraw;
        }
    }while(check);
  }
}


