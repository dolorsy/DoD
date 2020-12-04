package com.destroyordefend.project;

import com.destroyordefend.project.Core.Game;

public class Main {

    public static void p(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
//        GameTimer gameTimer= new GameTimer(10);
//        gameTimer.start();
       Game game = Game.getGame();
        game.StartAnewGame();


    }
}
