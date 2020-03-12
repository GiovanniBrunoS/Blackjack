package com.example.blackjack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class BlackjackActivity extends AppCompatActivity {

    private CardGenerator cardGenerator;

    private TextView textViewScoreC;
    private TextView textViewScoreU;
    private TextView textViewEnd;

    private Button buttonPedirCarta;
    private Button buttonParar;
    private Button buttonRestart;

    private int userScore;
    private int userAces;
    private int computerScore;
    private int computerAces;
    private int userCardCount;
    private int computerCardCount;
    private Card computerHiddenCard;

    private List<ImageView> userViews = new ArrayList<>();
    private List<ImageView> computerViews = new ArrayList<>();

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack);

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor = sharedPreferences.edit();;

        String title = sharedPreferences.getString("username", "") + " - V:" +sharedPreferences.getString("vitorias", "") + " D:" + sharedPreferences.getString("derrotas", "") ;
        setTitle(title);

        computerViews.add((ImageView) findViewById(R.id.imageViewC1));
        computerViews.add((ImageView) findViewById(R.id.imageViewC2));
        computerViews.add((ImageView) findViewById(R.id.imageViewC3));
        computerViews.add((ImageView) findViewById(R.id.imageViewC4));
        computerViews.add((ImageView) findViewById(R.id.imageViewC5));
        computerViews.add((ImageView) findViewById(R.id.imageViewC6));
        computerViews.add((ImageView) findViewById(R.id.imageViewC7));
        computerViews.add((ImageView) findViewById(R.id.imageViewC8));
        computerViews.add((ImageView) findViewById(R.id.imageViewC9));
        computerViews.add((ImageView) findViewById(R.id.imageViewC10));
        userViews.add((ImageView) findViewById(R.id.imageViewU1));
        userViews.add((ImageView) findViewById(R.id.imageViewU2));
        userViews.add((ImageView) findViewById(R.id.imageViewU3));
        userViews.add((ImageView) findViewById(R.id.imageViewU4));
        userViews.add((ImageView) findViewById(R.id.imageViewU5));
        userViews.add((ImageView) findViewById(R.id.imageViewU6));
        userViews.add((ImageView) findViewById(R.id.imageViewU7));
        userViews.add((ImageView) findViewById(R.id.imageViewU8));
        userViews.add((ImageView) findViewById(R.id.imageViewU9));
        userViews.add((ImageView) findViewById(R.id.imageViewU10));

        textViewScoreC = (TextView) findViewById(R.id.textViewScoreC);
        textViewScoreU = (TextView) findViewById(R.id.textViewScoreU);
        textViewEnd = (TextView) findViewById(R.id.textViewEnd);
        buttonPedirCarta = (Button) findViewById(R.id.buttonPedirCarta);
        buttonParar = (Button) findViewById(R.id.buttonParar);
        buttonRestart = (Button) findViewById(R.id.buttonRestart);

        cardGenerator = new CardGenerator(this);

        gameStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            finish();
        return super.onOptionsItemSelected(item);
    }

      public void onPedirCarta(View view) {
        userPlay();
    }

    public void onParar(View view) {
        endTurn();
    }

    public void onJogarNovamente(View view) {
        buttonParar.setVisibility(View.VISIBLE);
        buttonPedirCarta.setVisibility(View.VISIBLE);

        buttonRestart.setVisibility(View.INVISIBLE);
        textViewEnd.setVisibility(View.INVISIBLE);
        gameStart();
    }

    public void gameStart(){
        userCardCount = 0;
        computerCardCount = 0;
        userAces = 0;
        userScore = 0;
        computerAces = 0;
        computerScore = 0;

        for(ImageView views:computerViews){
            views.setImageDrawable(null);
        }

        for(ImageView views:userViews){
            views.setImageDrawable(null);
        }

        computerPlay();
        computerHidden();
        userPlay();
        userPlay();
    }

    private void userPlay(){
        Card card = cardGenerator.generateCard();
        userCardCount += 1;
        userScore += card.getValue();

        if(card.getValue() == 11)
            userAces += 1;
        if (userScore > 21 && userAces >= 1){
            userScore -= 10;
            userAces -= 1;
        }

        textViewScoreU.setText(String.valueOf(userScore));
        userViews.get(userCardCount-1).setImageResource(card.getResourceId());

        if (userScore == 21)
            endTurn();
        if (userScore > 21)
            endGame();

    }

    private void computerPlay() {
        Card card = cardGenerator.generateCard();
        computerCardCount += 1;
        computerScore += card.getValue();

        if (card.getValue() == 11)
            computerAces += 1;
        if (computerScore > 21 && computerAces >= 1) {
            computerScore -= 10;
            computerAces -= 1;
        }

        textViewScoreC.setText(String.valueOf(computerScore));
        computerViews.get(computerCardCount - 1).setImageResource(card.getResourceId());
    }

    private void computerHidden(){
        Card card = cardGenerator.generateCard();
        computerCardCount += 1;
        computerHiddenCard = card;
        computerViews.get(computerCardCount - 1).setImageResource(R.drawable.back);
    }

    private void computerUnhide(){
        computerScore += computerHiddenCard.getValue();

        if (computerHiddenCard.getValue() == 11)
            computerAces += 1;
        if (computerScore > 21 && computerAces >= 1) {
            computerScore -= 10;
            computerAces -= 1;
        }

        textViewScoreC.setText(String.valueOf(computerScore));
        computerViews.get(computerCardCount - 1).setImageResource(computerHiddenCard.getResourceId());
    }

    private void endTurn(){
        computerUnhide();
        while(computerScore < 17)
            computerPlay();
        endGame();
    }

    private void endGame(){
        int vitorias = Integer.parseInt(sharedPreferences.getString("vitorias", ""));
        int derrotas = Integer.parseInt(sharedPreferences.getString("derrotas", ""));

        buttonParar.setVisibility(View.INVISIBLE);
        buttonPedirCarta.setVisibility(View.INVISIBLE);

        buttonRestart.setVisibility(View.VISIBLE);
        textViewEnd.setVisibility(View.VISIBLE);

        if (userScore == 21 && computerScore != 21){
            textViewEnd.setText("Blackjack!");
            vitorias += 1;
        }
        else if(userScore > 21){
            textViewEnd.setText("Derrota!");
            derrotas += 1;
        }
        else if(computerScore > 21){
            textViewEnd.setText("Vitória!");
            vitorias += 1;
        }
        else if(userScore > computerScore){
            textViewEnd.setText("Vitória!");
            vitorias += 1;
        }
        else if(userScore < computerScore){
            textViewEnd.setText("Derrota!");
            derrotas += 1;
        }
        else
            textViewEnd.setText("Empate!");

        editor.putString("vitorias", String.valueOf(vitorias));
        editor.putString("derrotas", String.valueOf(derrotas));
        editor.commit();

        String title = sharedPreferences.getString("username", "") + " - V:" +sharedPreferences.getString("vitorias", "") + " D:" + sharedPreferences.getString("derrotas", "") ;
        setTitle(title);
    }
}
