package com.nizural.csensebe.tictactoe.game;

import java.util.Random;

/**
 * Created by csensebe on 26/02/2016.
 */
public class AiPlayer implements Player {

    private char mChar;
    private int mScore;
    private String mName;

    public AiPlayer(String playerName, char playerChar){
        mChar = playerChar;
        mName = playerName;
    }

    @Override
    public void incScore() {
        mScore++;
    }

    @Override
    public void setScore(int playerScore) {
        mScore = playerScore;
    }

    @Override
    public char getChar() {
        return mChar;
    }

    @Override
    public int getScore() {
        return mScore;
    }

    @Override
    public String getName() {
        return mName;
    }

    public int[] getAiResponse(char[][] boardstate) {
        int[] playerMove = choseRandom(boardstate);
        return playerMove;
    }

    private int[] choseRandom(char[][] boardState) {
        int randomRow;
        int randomCol;
        int[] moves = new int[2];
        do {
            randomRow = randomPos(boardState.length);
            randomCol = randomPos(boardState.length);
        } while (boardState[randomRow][randomCol] != TicTacToeGame.NEUTRAL_CHAR);
        moves[0] = randomRow;
        moves[1] = randomCol;
        return moves;
    }

    private int randomPos(int maxPos) {
        Random random = new Random();
        return random.nextInt(maxPos);
    }
}
