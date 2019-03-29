package com.example.memorygame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatDrawableManager;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {

    private Button buttonStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartGame = (Button) findViewById(R.id.buttonGameStart);

        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

    }

    public static class MemoryButton extends android.support.v7.widget.AppCompatButton {

        protected int row;
        protected int column;
        protected int frontImage;
        protected boolean flipped = false;
        protected boolean matched = false;

        protected Drawable frontImageDraw;
        protected Drawable backImageDraw;

        //TODO ?????
        @SuppressLint("RestrictedApi")
        public MemoryButton(Context context, int r, int c, int frontImage){
            super(context);

            this.row = r;
            this.column = c;
            this.frontImage = frontImage;

            this.frontImageDraw = AppCompatDrawableManager.get().getDrawable(context, frontImage);
            this.backImageDraw = AppCompatDrawableManager.get().getDrawable(context, R.drawable.background128);

            setBackground(backImageDraw);
            GridLayout.LayoutParams tParams = new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c));
            tParams.width = (int) getResources().getDisplayMetrics().density * 50;
            tParams.height = (int) getResources().getDisplayMetrics().density * 50;
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

        public Drawable getFrontImageDraw() {
            return frontImageDraw;
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
}
