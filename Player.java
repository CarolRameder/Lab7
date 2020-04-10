package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Player implements Runnable {
    private String name;
    private int order;
    private Board board;
    private List<Token> toks = new ArrayList<>();
    AtomicBoolean gameOn = new AtomicBoolean(true);
    public Player(String name, int order, Board board,AtomicBoolean gameOn) {
        this.order = order;
        this.name = name;
        this.board = board;
        this.gameOn=gameOn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTok(Token T) {
        toks.add(T);
    }


    public int maxProg() {
        // https://www.geeksforgeeks.org/longest-arithmetic-progression-dp-35/
        int poz = 0;
        int[] set = new int[30];
        for (Token t : this.toks) {
            set[poz] = t.getValue();
            poz++;
        }
        Arrays.sort(set);
        int n = poz;
        if (n <= 2)
            return n;
        // Create a table and initialize all
        // values as 2. The value ofL[i][j] stores
        // LLAP with set[i] and set[j] as first two
        // elements of AP. Only valid entries are
        // the entries where j>i
        int[][] L = new int[n][n];

        // Initialize the result
        int llap = 2;

        // Fill entries in last column as 2.
        // There will always be two elements in
        // AP with last number of set as second
        // element in AP
        for (int i = 0; i < n; i++)
            L[i][n - 1] = 2;

        // Consider every element as second element of AP
        for (int j = n - 2; j >= 1; j--) {
            // Search for i and k for j
            int i = j - 1, k = j + 1;
            while (i >= 0 && k <= n - 1) {
                if (set[i] + set[k] < 2 * set[j])
                    k++;

                    // Before changing i, set L[i][j] as 2
                else if (set[i] + set[k] > 2 * set[j]) {
                    L[i][j] = 2;
                    i--;

                } else {
                    // Found i and k for j, LLAP with i and j as first two
                    // elements is equal to LLAP with j and k as first two
                    // elements plus 1. L[j][k] must have been filled
                    // before as we run the loop from right side
                    L[i][j] = L[j][k] + 1;

                    // Update overall LLAP, if needed
                    llap = Math.max(llap, L[i][j]);

                    // Change i and k to fill
                    // more L[i][j] values for current j
                    i--;
                    k++;
                }
            }

            // If the loop was stopped due
            // to k becoming more than
            // n-1, set the remaining
            // entties in column j as 2
            while (i >= 0) {
                L[i][j] = 2;
                i--;
            }
        }
        return llap;

    }

    @Override
    public void run() {
        this.playerTurn();
    }

    public synchronized void playerTurn() {
        int pos;
        Token t;
        //while exista tokens si este randul lui
        while ((this.board.getNumToks() != 0) && (gameOn.get())) {
            //cat timp mai avem tokeni se desfasoara jocul
            //si nimeni nu a facut maxProg = 4
            if (this.board.getTurn() == this.order) {
                //playerul alege un token daca este randul lui
                //iau un token random din cei ramasi
                pos = (int) (Math.random() * this.board.getNumToks());
                t = this.board.getToken(pos);
                this.addTok(t);//adaug tokenul la playerul curent
                this.board.remTok(t);//il scot din board
                this.board.changeTurn();
                //max progression is updated for the case in which
                System.out.println(this.name+" took the token with value : "+t.getValue());
                if(this.maxProg()>this.board.getMaxProgression()) {
                    this.board.setWinner(this.order);
                    this.board.setMaxProgression(this.maxProg());
                }
                if (this.maxProg() > 4) {//hardcoded sizeK
                    //System.out.println("maxProgexceeded");
                    gameOn.set(false);
                    this.board.setWinner(this.order);
                    //System.out.println(this.board.getWinner());
                }

            }

        }

    }


}
