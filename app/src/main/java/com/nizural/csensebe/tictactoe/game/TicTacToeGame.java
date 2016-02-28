package com.nizural.csensebe.tictactoe.game;

import java.io.Serializable;

public class TicTacToeGame implements Serializable{

    private static final int STARTING_PLAYER_SCORE = 0;
    public static final char NEUTRAL_CHAR = '-';
    private static final char PLAYER_ONE_CHAR = 'O';
    private static final char PLAYER_TWO_CHAR = 'X';

    private Player mPlayerOne;
    private Player mPlayerTwo;
    private Player mPlayerInFocus;
    private int mBoardSize;
    private char[][] mBoardState;
    private int mNumberTurnsLeft;
    private boolean mIsOnePlayerGame;

    public TicTacToeGame(String playerOneName, String playerTwoName, int boardSize, boolean
            isOnePlayerGame){
        mPlayerOne = new HumanPlayer(playerOneName, PLAYER_ONE_CHAR);
        if(isOnePlayerGame){
            mPlayerTwo = new AiPlayer(playerTwoName, PLAYER_TWO_CHAR);
        } else {
            mPlayerTwo = new HumanPlayer(playerTwoName, PLAYER_TWO_CHAR);
        }
        mBoardSize = boardSize;
        mNumberTurnsLeft = mBoardSize * mBoardSize;
        mIsOnePlayerGame = isOnePlayerGame;
        initGame();
    }

    public void initGame() {
        mPlayerOne.setScore(STARTING_PLAYER_SCORE);
        mPlayerTwo.setScore(STARTING_PLAYER_SCORE);
        initBoard();
        mPlayerInFocus = mPlayerOne;
    }

    public void resetBoard() {
        mNumberTurnsLeft = mBoardSize * mBoardSize;
        mPlayerInFocus = mPlayerOne;
        initBoard();
    }

    private void initBoard() {
        mBoardState = new char[mBoardSize][mBoardSize];
        for (int i = 0; i < mBoardSize; i++){
            for (int j = 0; j < mBoardSize; j++){
                mBoardState[i][j] = NEUTRAL_CHAR;
            }
        }
    }

    public void playTurn(int row, int column){
        if(mBoardState[row][column] == NEUTRAL_CHAR && gameHasNextTurn()){
            mBoardState[row][column] = mPlayerInFocus.getChar();
            if(isVictory(mPlayerInFocus.getChar(), row, column)){
                if(mPlayerInFocus.equals(mPlayerOne)){
                    mPlayerOne.incScore();
                } else {
                    mPlayerTwo.incScore();
                }
                resetBoard();
            } else {
                togglePlayersFocus();
                mNumberTurnsLeft--;
                if (mIsOnePlayerGame && mPlayerInFocus.equals(mPlayerTwo) && gameHasNextTurn()){
                    int[] aiResponse = mPlayerTwo.getAiResponse(mBoardState);
                    mBoardState[aiResponse[0]][aiResponse[1]] = mPlayerTwo.getChar();
                    if(isVictory(mPlayerTwo.getChar(), aiResponse[0], aiResponse[1])){
                        mPlayerTwo.incScore();
                        resetBoard();
                    } else {
                        togglePlayersFocus();
                        mNumberTurnsLeft--;
                    }
                }
            }
        }
        if(!gameHasNextTurn()){
            resetBoard();
        }
    }

    private void togglePlayersFocus(){
        if(mPlayerInFocus.equals(mPlayerOne)){
            mPlayerInFocus = mPlayerTwo;
        } else {
            mPlayerInFocus = mPlayerOne;
        }
    }

    public char[][] getmBoardState(){
        return mBoardState;
    }

    public boolean gameHasNextTurn(){
        return mNumberTurnsLeft > 0;
    }

    public boolean isVictory(char playerChar, int row, int column){
        if(isWinningRow(playerChar, row)){
            return true;
        } else if (isWinningColumn(playerChar, column)){
            return true;
        } else if (isWinningForwardDiagonal(playerChar)){
            return true;
        } else if (isWinningBackwardDiagonal(playerChar)){
            return true;
        } return false;
    }

    private boolean isWinningBackwardDiagonal(char playerChar) {
        int colPosition = mBoardSize;
        boolean isWinning = true;
        for (int i = 0; i < mBoardSize; i++){
            isWinning = isWinning && playerChar == mBoardState[i][--colPosition];
        }
        return isWinning;
    }

    private boolean isWinningForwardDiagonal(char playerChar) {
        boolean isWinning = true;
        for (int i = 0; i < mBoardSize; i++){
            isWinning = isWinning && playerChar == mBoardState[i][i];
        }
        return isWinning;
    }

    private boolean isWinningColumn(char playerChar, int column) {
        boolean isWinning = true;
        for (int i = 0; i < mBoardSize; i++){
            isWinning = isWinning && playerChar == mBoardState[i][column];
        }
        return isWinning;
    }

    private boolean isWinningRow(char playerChar, int row) {
        boolean isWinning = true;
        for (int i = 0; i < mBoardSize; i++){
            isWinning = isWinning && playerChar == mBoardState[row][i];
        }
        return isWinning;
    }

    public int getPlayerOneScore() {
        return mPlayerOne.getScore();
    }

    public int getPlayerTwoScore(){
        return mPlayerTwo.getScore();
    }
}
