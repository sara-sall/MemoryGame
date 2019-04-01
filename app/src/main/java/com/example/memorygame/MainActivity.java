package com.example.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonStartGame4x4;
    private Button buttonStartGame4x5;
    private Button buttonStartGame4x6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartGame4x4 = (Button) findViewById(R.id.buttonGameStart4x4);
        buttonStartGame4x5 = (Button) findViewById(R.id.buttonGameStart4x5);
        buttonStartGame4x6 = (Button) findViewById(R.id.buttonGameStart4x6);

        buttonStartGame4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("row", 4);
                intent.putExtra("column", 4);
                intent.putExtra("densityNr", 130);
                intent.putExtra("valueKey", "hs4x4");
                startActivity(intent);
            }
        });

        buttonStartGame4x5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("row", 5);
                intent.putExtra("column", 4);
                intent.putExtra("densityNr", 130);
                intent.putExtra("valueKey", "hs4x5");
                startActivity(intent);
            }
        });

        buttonStartGame4x6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("row", 6);
                intent.putExtra("column", 4);
                intent.putExtra("densityNr", 110);
                intent.putExtra("valueKey", "hs4x6");
                startActivity(intent);
            }
        });

    }
}
