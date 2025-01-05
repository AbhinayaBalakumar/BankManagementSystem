package com.example.bankmanagementsystem;

import java.lang.Math;

public class Bank {
    private String card;
    private String pass;
    //Create a card
    public Bank() {
        card = "" + (int) (Math.random() * (9999 - 1000 + 1) + 1000);
        for (int i = 0; i < 3; i++) {
            card = card + (int) ((Math.random() * (9999 - 1000 + 1) + 1000));
        }
    }

    public String getCard() {
        return card;
    }
}

