package com.example.bankmanagementsystem;

import java.util.ArrayList;

public class Customer {
    private String username;
    private String password;
    private double balance;
    private double checking;
    private double savings;
    private double payments = 0;
    private double budget;
    //Need to get the essential things from the file
    public Customer(String u, String pass, double b, double c, double s, double budget) {
        username = u;
        password = pass;
        balance = b;
        checking = c;
        savings = s;
        this.budget = budget;
    }
    //Need some getters, subtracters and adders
    public double subBalance(double x) {
        balance = x;
        return balance;
    }

    public double subChecking(double x) {
        checking = x;
        return checking;
    }

    public double subSavings(double x) {
        savings = x;
        return savings;
    }

    public String getUser() {
        return username;
    }

    public String everything() {
        String s = "Balance: $" + balance + "\nSavings: $" + savings + "\nChecking: $" + checking
                + "\nBudget: $" + budget + "\n ";
        return s;
    }

    public double checkBalance() {
        return balance;
    }

    public double addBalance(double x) {
        balance = balance + x;
        return balance;
    }

    public double checkSavings() {
        return savings;
    }

    public double addSavings(double x) {
        savings = savings + x;
        return savings;
    }

    public double checkChecking() {
        return checking;
    }

    public double addChecking(double x) {
        checking = checking + x;
        return checking;
    }

    //Pay through accounts
    public double makePayments(double account, int num) {

        if (account >= 0) {
            account = account - num;
            payments += num;
        }
        return account;
    }
    //Pay through balance
    public double transferFunds(double b, int p) {
        if (b >= 0) {
            b = b - p;
        }
        return b;
    }
    //Give budget
    public double makeBudget(double income, double expense) {
        budget = income - expense - payments;
        return budget;
    }
    //Puts everything into an arraylist and returns it as a string
    public String toString() {
        ArrayList<String> l = new ArrayList<String>();
        l.add(username);
        l.add(password);
        l.add(balance + "");
        l.add(checking + "");
        l.add(savings + "");
        l.add(budget + "");
        return l + "";
    }
}


