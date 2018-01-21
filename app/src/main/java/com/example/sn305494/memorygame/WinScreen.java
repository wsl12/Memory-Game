package com.example.sn305494.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by sn305494 on 3/20/2015.
 */
public class WinScreen extends Activity{

    private String boardSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_screen);

        Intent intent = getIntent();
        boardSize = intent.getExtras().getString("boardSize");
    }

    public void returnMainScreen(View v) {
        Intent returnHome = new Intent(this, MainActivity.class);
        startActivity(returnHome);
        finish();
    }

    public void onClick(View view) {
        Intent getGameBoard = new Intent(this, Board.class);
        getGameBoard.putExtra("boardSize", boardSize);
        startActivity(getGameBoard);
        finish();
    }
}
