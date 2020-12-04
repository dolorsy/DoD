package com.destroyordefend.project.utility;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Tactic.Tactic;
import com.destroyordefend.project.Unit.Unit;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.destroyordefend.project.Core.Game.game;
import static com.destroyordefend.project.utility.MainMethodAsyncTask.doMainThingQueue;
import static com.destroyordefend.project.utility.MainMethodAsyncTask.invokeMainMethods;
import static com.destroyordefend.project.utility.UpdateMapAsyncTask.invokeUpdatePosition;
import static com.destroyordefend.project.utility.UpdateMapAsyncTask.updatePositionQueue;
import static com.destroyordefend.project.utility.UpdateRangeAsyncTask.invokeUpdateRange;
import static com.destroyordefend.project.utility.UpdateRangeAsyncTask.updateRangeQueue;

public class GameTimer extends Thread {
int RoundLength ;
int currentSecond = 0;
//public static ExecutorService executorService = Executors.newFixedThreadPool(5);
    public void run(){
        for(;currentSecond<=RoundLength;currentSecond++){
            try {


                //Todo: Be Careful About Time Of the Following Three Methods, it should be 0.9 Second For Them all Together
                //Todo:May Be You need Exception Handling
                //Todo: We should invoke All Players UpdateArmy
//
//                for(Unit u: game.getAllUnits()){
//
//
//                    for(int i =0;i<u.getCurrentSpeed();i++)
//                    UpdateMapAsyncTask.addMethod(u::Move);
//                    UpdateRangeAsyncTask.addMethod(u::UpdateRange);
//                    if(!u.getTreeSetUnit().isEmpty()){
//                        Runnable method = () -> u.getDamaging().DoDamage();
//                        MainMethodAsyncTask.addMethod(method);
//                    }
//;
//
//                }
                /*
                  The PREVIOUS Code is a big Mistake
                  */
                long current = System.currentTimeMillis();
/*
                executorService.submit(UpdateMapAsyncTask::invokeUpdatePosition);
                executorService.submit(UpdateRangeAsyncTask::invokeUpdateRange);
                executorService.submit(MainMethodAsyncTask::invokeMainMethods);
                executorService.submit(this::reFill);
*/

                Runnable method = UpdateMapAsyncTask::invokeUpdatePosition;
                new Thread(method).start();
               /* method = UpdateRangeAsyncTask::invokeUpdateRange;
                new Thread(method).start();
                method = MainMethodAsyncTask::invokeMainMethods;
                new Thread(method);*/

                current = System.currentTimeMillis()-current;
                System.out.println("Spended Time " + current + "  " + currentSecond);
                Thread.sleep(1000 - current);
                reFill();

            } catch (InterruptedException e) {
                e.printStackTrace();

                //Todo: Need To Implement
            }

        }


       // executorService.shutdown();

    }

    public GameTimer(int roundLength) {
        RoundLength = roundLength;

    }
    public boolean onEnd(){
        return currentSecond == RoundLength;
    }

    public int getCurrentSecond() {
        return currentSecond;
    }

    void reFill(){
        updatePositionQueue.clear();
        updateRangeQueue.clear();
        doMainThingQueue.clear();
        for(Unit unit: game.getAllUnits()){

            UpdateMapAsyncTask.addMethod(unit::Move);
            UpdateRangeAsyncTask.addMethod(() -> unit.getTactic().SortMap(unit));
            MainMethodAsyncTask.addMethod(() ->unit.getDamaging().DoDamage());
        }
    }

     public boolean pause(){
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean resumee(){
        this.notify();
        return false;
    }
}

