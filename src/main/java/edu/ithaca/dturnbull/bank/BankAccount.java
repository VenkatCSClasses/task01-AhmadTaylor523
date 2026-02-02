package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
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
     */
    public void withdraw (double amount) throws InsufficientFundsException{
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
        if (amount < 0){
            return false;
        }
        String amountStr = Double.toString(amount);
        int indexOfDecimal = amountStr.indexOf('.');
        if (indexOfDecimal == -1){
            return true; // no decimal point, so it's a whole number
        }
        int numDecimalPlaces = amountStr.length() - indexOfDecimal - 1;
        return numDecimalPlaces <= 2;
    }
}