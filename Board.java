package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Board {

    private int numToks;
    private int turn = 1; // who's turn to take a token is
    private ArrayList<Token> tokens = new ArrayList<>();
    private int maxProgression=0;
    private int winner=0;


    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getMaxProgression() {
        return maxProgression;
    }

    public void setMaxProgression(int maxProgression) {
        this.maxProgression = maxProgression;
    }

    public int getWinner() {
        return winner;
    }

    public int getNumToks() {
        return numToks;
    }

    public int getTurn() {
        return turn;
    }
//the board stores the player who has acces for taking a token at a given time through the running
    public void changeTurn() {
        if (this.turn == 3)//there are 3 players , they are on turn consequently
            this.turn = 1;
        else this.turn++;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public Token getToken(int i) {
        return tokens.get(i);
    }

    public Board() {
    }

    public void addTok(Token T) {
        tokens.add(T);
        this.numToks++;
    }

    public void remTok(Token T) {
        tokens.remove(T);
        this.numToks--;
    }
}
