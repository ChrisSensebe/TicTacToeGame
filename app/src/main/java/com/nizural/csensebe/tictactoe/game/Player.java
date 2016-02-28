package com.nizural.csensebe.tictactoe.game;

/**
 * Created by csensebe on 25/02/2016.
 */
public interface Player {
    void incScore();

    void setScore(int startingPlayerScore);

    char getChar();

    int getScore();

    String getName();

    int[] getAiResponse(char[][] boardstate);
}
