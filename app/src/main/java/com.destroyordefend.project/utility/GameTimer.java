package com.destroyordefend.project.utility;

import com.destroyordefend.project.Unit.Unit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.destroyordefend.project.Core.Game.game;

public class GameTimer extends Thread {
    public static ExecutorService executorService = Executors.newFixedThreadPool(5);
    int RoundLength = 30;
    int currentSecond = 0;

    public GameTimer(int roundLength) {
        RoundLength = roundLength;

    }

    public void run() {
        System.out.println("Here");
        for (; currentSecond <= RoundLength; currentSecond++) {
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
                System.out.println("Here");


                long current = System.currentTimeMillis();

                executorService.submit(UpdateMapAsyncTask::invokeUpdatePosition);
                executorService.submit(UpdateRangeAsyncTask::invokeUpdateRange);
                executorService.submit(MainMethodAsyncTask::invokeMainMethods);
                executorService.submit(this::reFill);

                //todo: try invoke all , or set a time out for the above methods


                current = System.currentTimeMillis() - current;
                System.out.println("Spended Time" + current + "  " + currentSecond);
                Thread.sleep(1000 - current);

            } catch (InterruptedException e) {
                e.printStackTrace();

                //Todo: Need To Implement
            }

        }

        executorService.shutdown();

    }

    public boolean onEnd() {
        return currentSecond == RoundLength;
    }

    public int getCurrentSecond() {
        return currentSecond;
    }

    void reFill() {
        for (Unit unit : game.getAllUnits()) {
            UpdateMapAsyncTask.addMethod(unit::Move);
            UpdateRangeAsyncTask.addMethod(() -> unit.getTactic().SortMap(unit));
            MainMethodAsyncTask.addMethod(() -> unit.getDamaging().DoDamage());
        }
    }
}

