package com.example.sn305494.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Button button = (Button) view;
        String boardSize = button.getText().toString();

        Intent getGameBoard = new Intent(this, Board.class);
        getGameBoard.putExtra("boardSize", boardSize);
        startActivity(getGameBoard);
        finish();
    }
}
