package com.example.blackjack;

import android.content.Context;

class Card {

    private Context context;
    private int resourceId;
    private int value;

    Card(Context context, String suit, int id){
        this.context = context;
        String cardName = suit + id;
        this.resourceId = context.getResources().getIdentifier(cardName, "drawable", context.getPackageName());
        startValue(id);
    }

    private void startValue(int cardId){
        switch (cardId){
            case 1:
                this.value = 11;
                break;
            case 2:
                this.value = 2;
                break;
            case 3:
                this.value = 3;
                break;
            case 4:
                this.value = 4;
                break;
            case 5:
                this.value = 5;
                break;
            case 6:
                this.value = 6;
                break;
            case 7:
                this.value = 7;
                break;
            case 8:
                this.value = 8;
                break;
            case 9:
                this.value = 9;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                this.value = 10;
                break;
            default:
                break;
        }
    }

    int getResourceId() {
        return resourceId;
    }

    int getValue() {
        return value;
    }

}
