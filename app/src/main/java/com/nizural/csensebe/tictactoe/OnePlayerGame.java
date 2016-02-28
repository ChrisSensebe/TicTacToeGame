package com.nizural.csensebe.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.nizural.csensebe.tictactoe.game.TicTacToeGame;

public class OnePlayerGame extends AppCompatActivity {

    private static final String GAME_KEY = "game";
    private static final String PLAYER_NAME = "player";
    private static final String AI_NAME = "program";
    private static final int BOARD_SIZE = 3;

    private TextView mTextViewPlayerScore;
    private TextView mTextViewProgramScore;
    private TableLayout mTableLayoutGameBoard;
    private TicTacToeGame mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player_game);
        mTextViewPlayerScore = (TextView) findViewById(R.id.one_player_game_text_view_player_score);
        mTextViewProgramScore = (TextView) findViewById(R.id
                .one_player_game_text_view_ai_score);
        mTableLayoutGameBoard = (TableLayout) findViewById(R.id.one_player_game_board);
        mGame = new TicTacToeGame(PLAYER_NAME, AI_NAME, BOARD_SIZE, true);
        updateScreen(mGame.getmBoardState(), mGame.getPlayerOneScore(), mGame.getPlayerTwoScore());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(GAME_KEY, mGame);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mGame = (TicTacToeGame)savedInstanceState.getSerializable(GAME_KEY);
        updateScreen(mGame.getmBoardState(), mGame.getPlayerOneScore(), mGame.getPlayerTwoScore());
    }

    private void updateScreen(char[][] boardState, int playerOneScore, int playerTwoScore) {
        for (int i = 0; i < BOARD_SIZE; i++){
            TableRow row = (TableRow) mTableLayoutGameBoard.getChildAt(i);
            for (int j = 0; j < BOARD_SIZE; j++){
                Button button = (Button) row.getChildAt(j);
                button.setText(String.valueOf(boardState[i][j]));
            }
        }
        mTextViewPlayerScore.setText(String.valueOf(playerOneScore));
        mTextViewProgramScore.setText(String.valueOf(playerTwoScore));
    }

    public void back(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void newGame(View view){
        mGame.resetBoard();
        updateScreen(mGame.getmBoardState(), mGame.getPlayerOneScore(), mGame.getPlayerTwoScore());
    }

    public void playTurn(View view){
        TableRow row = (TableRow)view.getParent();
        int rowPosition = mTableLayoutGameBoard.indexOfChild(row);
        int colPosition = row.indexOfChild(view);
        mGame.playTurn(rowPosition, colPosition);
        updateScreen(mGame.getmBoardState(), mGame.getPlayerOneScore(), mGame.getPlayerTwoScore());
    }
}
