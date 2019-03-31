package com.example.memorygame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private int nrOfElements;
    private MemoryButton[] memoryButton;
    private int[] buttonResLoc;
    private int[] buttonGraph;
    private int counter = 0;
    private int pair;
    private int hs;
    private TextView counterScore;
    private TextView highScore;
    private SharedPreferences myPrefs;
    private static final String PREF_SCORE = "myPrefsFile";

    private MemoryButton button1;
    private MemoryButton button2;

    private boolean busy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GridLayout gameGrid = (GridLayout) findViewById(R.id.gameGridID);
        gameGrid.setColumnCount(4);
        gameGrid.setRowCount(5);
        int numCol = gameGrid.getColumnCount();
        int numRow = gameGrid.getRowCount();

        nrOfElements = numCol * numRow;

        buttonGraph = new int[nrOfElements /2];

        buttonGraph[0] = R.drawable.bear128;
        buttonGraph[1] = R.drawable.duck128;
        buttonGraph[2] = R.drawable.elephant128;
        buttonGraph[3] = R.drawable.fox128;
        buttonGraph[4] = R.drawable.pig128;
        buttonGraph[5] = R.drawable.penguin128;
        buttonGraph[6] = R.drawable.racoon128;
        buttonGraph[7] = R.drawable.turtle128;
        buttonGraph[8] = R.drawable.dog128;
        buttonGraph[9] = R.drawable.lion128;


        buttonResLoc = new int[nrOfElements];

        shuffleCards();

        for(int r = 0; r < numRow; r++){
            for(int c = 0; c < numCol; c++){
                MemoryButton tButton = new MemoryButton(this, r, c, buttonGraph[buttonResLoc[r * numCol + c]]);
                tButton.setId(View.generateViewId());
                tButton.setOnClickListener(this);
                gameGrid.addView(tButton);
            }
        }

        counterScore = (TextView) findViewById(R.id.counterScoreID);
        highScore = (TextView) findViewById(R.id.highScoreID);

        Button buttonStartGame = (Button) findViewById(R.id.buttonGameStart);

        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        myPrefs = getSharedPreferences(PREF_SCORE, 0);
        SharedPreferences.Editor editor = myPrefs.edit();

        editor.putInt("highScore", hs);
        editor.commit();

    }

    protected void shuffleCards(){
        Random random = new Random();

        for(int i =0; i < nrOfElements; i++){
            buttonResLoc[i] = i % nrOfElements / 2;
        }
        for(int i = 0; i < nrOfElements; i++){
            int t = buttonResLoc[i];
            int swapIndex = random.nextInt(nrOfElements);

            buttonResLoc[i] = buttonResLoc[swapIndex];

            buttonResLoc[swapIndex] = t;
        }
    }

    @Override
    public void onClick(View v) {

        if(busy){
            return;
        }
        final MemoryButton button = (MemoryButton) v;

        if(button.isMatched()){
            return;
        }
        else if(button1 == null){
            button1 = button;
            button1.flipCard();
        }
        else if(button1.getId() == button.getId()){
            return;
        }

        else if(button1.getFrontImageID() == button.getFrontImageID()){
            button.flipCard();
            button.setMatched(true);
            button1.setEnabled(false);
            button.setEnabled(false);
            button1 = null;

            counter+=1;
            pair+=1;
        }
        else{
            button2 = button;
            button2.flipCard();
            busy = true;
            counter+=1;

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    button2.flipCard();
                    button1.flipCard();
                    button1 = null;
                    button2 = null;

                    busy = false;
                }
            }, 500);

        }
        counterScore.setText(String.valueOf(counter));
        checkwin();
        checkHighScore();
 }

    public void checkwin(){
        if(pair == nrOfElements/2){
            Toast.makeText(this,R.string.fin_message, Toast.LENGTH_LONG).show();
        }

    }
    public void checkHighScore(){
        SharedPreferences prefs = getSharedPreferences(PREF_SCORE, 0);
        if(prefs.contains("highScore")){
            int score = prefs.getInt("highScore", 0);
            hs = score;
        }
        if(hs < counter){
            highScore.setText(String.valueOf(counter));

            myPrefs = getSharedPreferences(PREF_SCORE, 0);
            SharedPreferences.Editor editor = myPrefs.edit();

            editor.putInt("highScore", counter);
            editor.commit();

            //setHighScore();
        }
    }
    public void setHighScore(){
        myPrefs = getSharedPreferences(PREF_SCORE, 0);
        SharedPreferences.Editor editor = myPrefs.edit();

        editor.putInt("highScore", counter);
        editor.commit();
    }


}
