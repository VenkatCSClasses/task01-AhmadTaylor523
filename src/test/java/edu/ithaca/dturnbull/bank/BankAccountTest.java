package edu.ithaca.dturnbull.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        // Equivalence class: typical positive integer balance
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001);

        // Equivalence class: zero balance
        BankAccount zeroBal = new BankAccount("zero@bal.com", 0);
        assertEquals(0, zeroBal.getBalance(), 0.0001);

        // Equivalence class: fractional (decimal) balance
        BankAccount fractional = new BankAccount("frac@bal.com", 12.34);
        assertEquals(12.34, fractional.getBalance(), 1e-6);

        // Equivalence class: very large balance
        BankAccount large = new BankAccount("rich@bank.com", 1000000000);
        assertEquals(1000000000, large.getBalance(), 1e-3);

        // Equivalence class: negative starting balance 
        BankAccount negative = new BankAccount("neg@bal.com", -50);
        assertEquals(-50, negative.getBalance(), 1e-6);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        BankAccount bankAccount2 = new BankAccount("c@d.com", 100);

        BankAccount bankAccount3 = new BankAccount("e@f.com", 100);
        
        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        bankAccount.withdraw(100);
        assertEquals(0,bankAccount.getBalance(),0.001);//Equivalenence class : withdraw amount equal to balance
        assertEquals(0,bankAccount.getBalance(),0.001);//Equivalenence class : withdraw amount zero
       assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-50));//Equivalenence class : withdraw negative amount 
        
         
        bankAccount2.withdraw(50); //Equivalence class: withdraw amount less than balance but not negative
        assertEquals(50, bankAccount2.getBalance(), 0.001);
        //Equivalence class: withdraw amount greater than balance 
        assertThrows(InsufficientFundsException.class, () -> bankAccount2.withdraw(100));
        //Equivalence class: withdraw amount too small (non-negative)
        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(0.001));
        // Boundary Value: withdraw amount just less than balance
        bankAccount2.withdraw(49.99);
        assertEquals(0.01, bankAccount2.getBalance(), 0.001);
        // Boundary Value: withdraw amount just greater than balance
        assertThrows(InsufficientFundsException.class, () -> bankAccount2.withdraw(0.02));

        assertThrows(IllegalArgumentException.class, () -> bankAccount3.withdraw(0.111)); //Equivalence class : greater than 2 decimal places
        assertThrows(IllegalArgumentException.class, () ->bankAccount3.withdraw(-10)); //Equivalence class : negative amount withdrawn

        assertThrows(IllegalArgumentException.class, () -> bankAccount3.withdraw(2.11001)); //Equivalence class : greater than 2 decimal places
        assertThrows(IllegalArgumentException.class, () -> bankAccount3.withdraw(-1.00)); // Equivalence class : negative amount withdrawn
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address (equivalence class: valid email)
        assertFalse( BankAccount.isEmailValid(""));         // empty string (equivalence class: empty string)
        assertFalse( BankAccount.isEmailValid("ab@s..com"));  // consecutive periods in domain (equivalence class: consecutive periods)
        assertTrue(BankAccount.isEmailValid("abc-d@mail.com")); // valid email with hyphen (equivalence class: valid email with special character)
        assertFalse(BankAccount.isEmailValid(".abc@mail.com")); // period at start of local part (equivalence class: period at start)
        assertFalse(BankAccount.isEmailValid("abc#def@h.com")); // invalid character '#' (equivalence class: invalid character)

        assertFalse(BankAccount.isEmailValid("heywassup.com")); // equivalence class: missing '@' symbol
        assertFalse(BankAccount.isEmailValid("abc..def@mail..com")); // equivalence class: consecutive periods in local part
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("valid@mail.com", 100.110111)); // double starting balance with more than 2 decimal places
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("valid@email.com", 100.001)); // double starting balance with more than 2 decimal places
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("validemail2@gmail.com", -100)); // invalid negative starting balance
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("validemail12@gmail.com", -1.00)); // invalid negative starting balance

    }
    @Test
    void isAmountValidTest(){
        assertTrue(BankAccount.isAmountValid(100.00)); // equivalence class: valid amount
        assertTrue(BankAccount.isAmountValid(0.00)); // equivalence class: zero amount
        assertFalse(BankAccount.isAmountValid(-50.00)); // equivalence class: negative amount
        assertFalse(BankAccount.isAmountValid(10.999)); // equivalence class: amount with more than 2 decimal places
    }
}