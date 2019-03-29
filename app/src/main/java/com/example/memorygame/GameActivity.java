package com.example.memorygame;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private int nrOfElements;
    private MemoryButton[] memoryButton;
    private int[] buttonResLoc;
    private int[] buttonGraph;

    private MemoryButton button1;
    private MemoryButton button2;

    private boolean busy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GridLayout gameGrid = (GridLayout) findViewById(R.id.gameGridID);
        gameGrid.setColumnCount(4);
        gameGrid.setRowCount(4);
        int numCol = gameGrid.getColumnCount();
        int numRow = gameGrid.getRowCount();

        nrOfElements = numCol * numRow;

        buttonGraph = new int[nrOfElements /2];

        buttonGraph[0] = R.drawable.bear128;
        buttonGraph[1] = R.drawable.duck128;
        buttonGraph[2] = R.drawable.elephant128;
        buttonGraph[3] = R.drawable.fox128;
        buttonGraph[4] = R.drawable.pig128;
        buttonGraph[5] = R.drawable.rabbit128;
        buttonGraph[6] = R.drawable.racoon128;
        buttonGraph[7] = R.drawable.turtle128;

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
            return;
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
            return;
        }
        else{
            button2 = button;
            button2.flipCard();
            busy = true;

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
    }
}
