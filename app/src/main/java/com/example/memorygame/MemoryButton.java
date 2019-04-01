package com.example.memorygame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.Button;
import android.widget.GridLayout;

public class MemoryButton extends android.support.v7.widget.AppCompatButton {

    private int row;
    private int column;
    private int frontImageID;
    private int densityNr;

    private boolean flipped = false;
    private boolean matched = false;

    private Drawable frontImageDraw;
    private Drawable backImageDraw;

    @SuppressLint("RestrictedApi")
    public MemoryButton(Context context, int row, int column, int frontImageID, int densityNr) {
        super(context);

        this.row = row;
        this.column = column;
        this.frontImageID = frontImageID;
        this.densityNr = densityNr;

        frontImageDraw = AppCompatDrawableManager.get().getDrawable(context, frontImageID);
        backImageDraw = AppCompatDrawableManager.get().getDrawable(context, R.drawable.background128);

        setBackground(backImageDraw);

        GridLayout.LayoutParams tParams = new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(column));

        tParams.width = (int) getResources().getDisplayMetrics().density * densityNr; //130
        tParams.height = (int) getResources().getDisplayMetrics().density * densityNr; //130
        setLayoutParams(tParams);
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public int getFrontImageID() {
        return frontImageID;
    }

    public void flipCard(){
        if(matched){
            return;
        }
        else if(flipped){
            setBackground(backImageDraw);
            flipped = false;
        }
        else{
            setBackground(frontImageDraw);
            flipped = true;
        }
    }
}
