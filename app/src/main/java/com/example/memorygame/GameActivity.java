package com.example.memorygame;

import android.content.Context;
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
    private SharedPreferences sharedPref;
    private static final String MyPREFERENCES = "MyPREFERENCES";
    final String HS_KEY = "highscore";
    private String hScore;

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

        sharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        hScore = sharedPref.getString(HS_KEY, null);
        if(hScore != null){
            highScore.setText(hScore);
        }

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
        if(checkwin()){
            if(checkHighScore()){
                Toast.makeText(this,R.string.record_message, Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,R.string.fin_message, Toast.LENGTH_LONG).show();
            }
        }

 }

    public boolean checkwin(){
        if(pair == nrOfElements/2){
            return true;
        }
        return false;
    }

    public boolean checkHighScore(){
        if(hScore != null){
            hs = Integer.parseInt(hScore);
            if(hs > counter){
                hs = counter;
                highScore.setText(String.valueOf(hs));
                saveHighScore(hs);
                return true;
            }
       }else{
            highScore.setText(String.valueOf(counter));
            saveHighScore(counter);
            return true;
        }

        return false;
    }
    public void saveHighScore(int c){
        String saveScore = String.valueOf(c);
        SharedPreferences.Editor editPref = sharedPref.edit();
        editPref.putString(HS_KEY, saveScore);
        editPref.commit();
    }


}
