package com.example.blackjack;

import android.content.Context;

import java.util.Random;

class CardGenerator {

    private Context context;

    CardGenerator(Context context){
        this.context = context;
    }

    Card generateCard(){
        int cardId = new Random().nextInt(13) + 1;
        int cardSuit = new Random().nextInt(4);

        return new Card(context, getCardSuit(cardSuit), cardId);
    }

    private String getCardSuit(int suitId){
        String suit = "s";
        switch (suitId){
            case 0:
                suit = "c";
                break;
            case 1:
                suit = "d";
                break;
            case 2:
                suit = "h";
                break;
            case 3:
                suit = "s";
                break;
        }
        return suit;
    }
}
