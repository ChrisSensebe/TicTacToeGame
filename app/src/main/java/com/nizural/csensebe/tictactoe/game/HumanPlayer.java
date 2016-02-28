package com.nizural.csensebe.tictactoe.game;

/**
 * Created by csensebe on 25/02/2016.
 */
public class HumanPlayer implements Player{

    private int mScore;
    private char mChar;
    private String mName;

    public HumanPlayer(String name, char gameChar){
        mChar = gameChar;
        mName = name;
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

    @Override
    public int[] getAiResponse(char[][] boardstate) {
        return new int[0];
    }
}
