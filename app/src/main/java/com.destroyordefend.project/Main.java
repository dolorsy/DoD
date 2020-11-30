package com.destroyordefend.project;

import com.destroyordefend.project.utility.GameTimer;


public class Main {

    public static void p(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        GameTimer gameTimer = new GameTimer(10);
        gameTimer.start();
    /*   Game game = Game.getGame();
        game.StartAnewGame();
        Log log = new Log();
        log.m(new Unit(2,2,2,"mm",2,2,2,2));
*/
    }

}
