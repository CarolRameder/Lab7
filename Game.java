package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Game {


    public void play()  {
        Board B = new Board();

        AtomicBoolean gameOn = new AtomicBoolean(true);
        Token t0 = new Token(0);
        Token t1 = new Token(1);
        Token t2 = new Token(2);
        Token t4 = new Token(4);
        Token t8 = new Token(8);
        Token t3 = new Token(3);
        Token t5 = new Token(5);
        Token t9 = new Token(9);
        Token t11 = new Token(11);
        Token t12 = new Token(12);
        Token t6 = new Token(6);
        Token t7 = new Token(7);

        B.addTok(t0);
        B.addTok(t1);
        B.addTok(t2);
        B.addTok(t4);
        B.addTok(t8);
        B.addTok(t3);
        B.addTok(t5);
        B.addTok(t9);
        B.addTok(t11);
        B.addTok(t12);
        B.addTok(t6);
        B.addTok(t7);

        Runnable p1 = new Player("Andrei",1,B,gameOn);
        Runnable p2 = new Player("Marius",2,B,gameOn);
        Runnable p3 = new Player("Florin",3,B,gameOn);

        Thread thread1 = new Thread(p1);
        thread1.start();
        Thread thread2 = new Thread(p2);
        thread2.start();
        Thread thread3 = new Thread(p3);
        thread3.start();
        while(thread1.isAlive()&&thread2.isAlive()&&thread3.isAlive()) {
            try {
                synchronized (this) {
                    wait(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int var=B.getWinner();

        if(var==1)System.out.println("Winner is Andrei.");
        else if(var==2)System.out.println("Winner is Marius.");
        else if(var==3)System.out.println("Winner is Florin.");
    }
}
