package com.nizural.csensebe.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class OnePlayerGame extends AppCompatActivity {

    private static final int BOARD_ROWS_AND_COLUMNS_LENGTH = 3;
    private static final int STARTING_PLAYER_SCORE = 0;
    private static final int STARTING_PROGRAM_SCORE = 0;
    private static final char NEUTRAL_CHAR = '-';
    private static final char PLAYER_CHAR = 'O';
    private static final char PROGRAM_CHAR = 'X';
    private static final String PLAYER_SCORE_KEY = "playerScore";
    private static final String PROGRAM_SCORE_KEY = "programScore";

    private TextView mTextViewPlayerScore;
    private TextView mTextViewProgramScore;
    private TableLayout mTableLayoutGameBoard;
    private int mPlayerScore;
    private int mProgrammScore;
    private char[][] mBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player_game);
        initGame();
    }

    private void initGame() {
        mPlayerScore = STARTING_PLAYER_SCORE;
        mProgrammScore = STARTING_PROGRAM_SCORE;
        mTextViewPlayerScore = (TextView) findViewById(R.id.one_player_game_text_view_player_score);
        mTextViewProgramScore = (TextView) findViewById(R.id
                .one_player_game_text_view_programm_score);
        mTableLayoutGameBoard =(TableLayout) findViewById(R.id.one_player_game_board);
        initBoard();
        updateScreen();
    }

    private void initBoard(){
        mBoard = new char[BOARD_ROWS_AND_COLUMNS_LENGTH][BOARD_ROWS_AND_COLUMNS_LENGTH];
        for (int i = 0; i < BOARD_ROWS_AND_COLUMNS_LENGTH; i++){
            for (int j = 0; j < BOARD_ROWS_AND_COLUMNS_LENGTH; j++){
                mBoard[i][j] = NEUTRAL_CHAR;
            }
        }
    }

    private boolean updateBoardState(int rowNumber, int columnNumber, char titleState) {
        if(mBoard[rowNumber][columnNumber] == NEUTRAL_CHAR){
            mBoard[rowNumber][columnNumber] = titleState;
            return true;
        }
        return false;
    }

    private void updateScreen() {
        for (int i = 0; i < BOARD_ROWS_AND_COLUMNS_LENGTH; i++){
            TableRow row = (TableRow) mTableLayoutGameBoard.getChildAt(i);
            for (int j = 0; j < BOARD_ROWS_AND_COLUMNS_LENGTH; j++){
                Button button = (Button) row.getChildAt(j);
                button.setText(String.valueOf(mBoard[i][j]));
            }
        }
        mTextViewPlayerScore.setText(String.valueOf(mPlayerScore));
        mTextViewProgramScore.setText(String.valueOf(mProgrammScore));
    }

    private boolean isEndGame(char playerChar) {
        if(isVictory(playerChar)){
            Toast.makeText(this, getString(R.string.player_victory_message), Toast.LENGTH_SHORT).show();
            mPlayerScore++;
            initBoard();
            updateScreen();
            return true;
        } else if (isFullBoard()) {
            Toast.makeText(this, getString(R.string.no_victory_message), Toast.LENGTH_SHORT).show();
            initBoard();
            updateScreen();
            return true;
        }
        return false;
    }

    private boolean isVictory(char playerChar) {
        return isWinningRow(playerChar) || isWinningColumn(playerChar) ||
                isWInningBackwardDiagonal(playerChar) || isWinningForwardDiagonal(playerChar);
    }

    private boolean isWinningRow(char playerChar) {
        for (int i = 0; i < BOARD_ROWS_AND_COLUMNS_LENGTH; i++){
            boolean isWinning = true;
            for (int j = 0; j < BOARD_ROWS_AND_COLUMNS_LENGTH; j++){
                isWinning = isWinning && playerChar == mBoard[i][j];
            }
            if(isWinning){
                return true;
            }
        }
        return false;
    }

    private boolean isWinningColumn(char playerChar) {
        for (int i = 0; i < BOARD_ROWS_AND_COLUMNS_LENGTH; i++){
            boolean isWinning = true;
            for (int j = 0; j < BOARD_ROWS_AND_COLUMNS_LENGTH; j++){
                isWinning = isWinning && playerChar == mBoard[j][i];
            }
            if(isWinning){
                return true;
            }
        }
        return false;
    }

    private boolean isWInningBackwardDiagonal(char playerChar) {
        int colPosition = BOARD_ROWS_AND_COLUMNS_LENGTH;
        boolean isWinning = true;
        for(int i = 0; i < BOARD_ROWS_AND_COLUMNS_LENGTH; i++){
            isWinning = isWinning && playerChar == mBoard[i][--colPosition];
        }
        return isWinning;
    }

    private boolean isWinningForwardDiagonal(char playerChar) {
        boolean isWinning = true;
        for (int i = 0; i < BOARD_ROWS_AND_COLUMNS_LENGTH; i++){
            isWinning = isWinning && playerChar == mBoard[i][i];
        }
        return isWinning;
    }

    private boolean isFullBoard() {
        for (int i = 0; i < BOARD_ROWS_AND_COLUMNS_LENGTH; i++){
            for (int j = 0; j < BOARD_ROWS_AND_COLUMNS_LENGTH; j++){
                if(mBoard[i][j] == NEUTRAL_CHAR){
                    return false;
                }
            }
        }
        return true;
    }

    public void playerTurn(View view) {
        Button buttonClicked = (Button) view;
        TableRow tableRow = (TableRow) buttonClicked.getParent();
        int rowNumber = mTableLayoutGameBoard.indexOfChild(tableRow);
        int columnNumber = tableRow.indexOfChild(buttonClicked);
        if(updateBoardState(rowNumber, columnNumber, PLAYER_CHAR)){
            updateScreen();
            if(!isEndGame(PLAYER_CHAR)){
                programTurn();
            }
        }
    }

    private void programTurn() {
        int rowNumber;
        int columnNumber;
        do {
            rowNumber = randomPosition(0, BOARD_ROWS_AND_COLUMNS_LENGTH-1);
            columnNumber = randomPosition(0, BOARD_ROWS_AND_COLUMNS_LENGTH-1);
        }
        while (!updateBoardState(rowNumber, columnNumber, PROGRAM_CHAR));
        updateScreen();
        isEndGame(PROGRAM_CHAR);
    }

    private int randomPosition(int minPosition, int maxPosition) {
        Random random = new Random();
        return random.nextInt((maxPosition - minPosition) + 1) + minPosition;
    }

    public void back(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void newGame(View view) {
        initGame();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PLAYER_SCORE_KEY, mPlayerScore);
        outState.putInt(PROGRAM_SCORE_KEY, mProgrammScore);
        for (int i = 0; i < BOARD_ROWS_AND_COLUMNS_LENGTH; i++){
            outState.putCharArray(String.valueOf(i), mBoard[i]);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPlayerScore = savedInstanceState.getInt(PLAYER_SCORE_KEY);
        mProgrammScore = savedInstanceState.getInt(PROGRAM_SCORE_KEY);
        for (int i = 0; i < BOARD_ROWS_AND_COLUMNS_LENGTH; i++){
            mBoard[i] = savedInstanceState.getCharArray(String.valueOf(i));
        }
        updateScreen();
    }
}
