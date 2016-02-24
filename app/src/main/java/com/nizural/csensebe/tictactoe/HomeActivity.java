package com.nizural.csensebe.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void launchOnePlayerGame(View view){
        Intent intent = new Intent(this, OnePlayerGame.class);
        startActivity(intent);
    }

    public void launchTwoPlayersGame(View view){

    }
}
