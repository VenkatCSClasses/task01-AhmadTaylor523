package edu.ithaca.dturnbull.bank;

import java.math.BigDecimal;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     * @throws IllegalArgumentException if startingBalance is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
        if (!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Starting balance: " + startingBalance + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * if amount is larger than balance, throws InsufficientFundsException. 
     * if amount is negative, it throws IllegalArgumentException
     * 
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (!isAmountValid(amount) || amount == 0){
            throw new IllegalArgumentException("Invalid withdraw amount: " + amount);
        }
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){
            return false;
        }
        else if (email.contains("..") || email.contains("#") || email.startsWith(".") || email.endsWith(".")) {
            return false;
        }

        else {
            return true;
        }
    }
    /**
     * Checks if amount is a valid amount (non-negative, no money amounts should have more than 2 decimal places)
     * @param amount
     * @return true if amount is valid, false otherwise
     */
    public static boolean isAmountValid(double amount){
        
    // reject NaN / Infinity
    if (!Double.isFinite(amount)) return false;

    // reject negatives
    if (amount < 0) return false;

    // Use BigDecimal to inspect decimal places robustly
    BigDecimal bd = BigDecimal.valueOf(amount).stripTrailingZeros();
    int scale = Math.max(0, bd.scale()); // normalize negative scale to 0
    return scale <= 2;
}
    
    /** 
     * Deposits the given amount into the bank account.
     * @param amount the amount to deposit
     * @throws IllegalArgumentException if the amount is invalid (negative or more than 2 decimal places) or zero
     */
    public void deposit(double amount){
       if (!isAmountValid(amount) || amount == 0){
            throw new IllegalArgumentException("Invalid deposit amount: " + amount);
        }
        balance += amount;
    } 

     

    /**
     * Transfers the given amount from this account to the specified toAccount.     
     * @param toAccount
     * @param amount
     * @throws InsufficientFundsException
     * @throws IllegalArgumentException if toAccount is null or amount is invalid (negative or more than 2 decimal places) or zero
     */
    public void transfer(BankAccount toAccount, double amount) throws InsufficientFundsException {
       if (toAccount == null) {
            throw new IllegalArgumentException("Destination account cannot be null");
        }
        if (!isAmountValid(amount) || amount == 0) {
            throw new IllegalArgumentException("Invalid transfer amount: " + amount);
        }
        else{
        this.withdraw(amount); 
        toAccount.deposit(amount);
    }

}

}