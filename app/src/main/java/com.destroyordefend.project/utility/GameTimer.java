package com.destroyordefend.project.utility;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Unit.Unit;

import java.util.Collection;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import static com.destroyordefend.project.Core.Game.getGame;
import static com.destroyordefend.project.utility.MainMethodAsyncTask.doMainThingQueue;
import static com.destroyordefend.project.utility.UpdateMapAsyncTask.updatePositionQueue;
import static com.destroyordefend.project.utility.UpdateRangeAsyncTask.updateRangeQueue;

public class GameTimer extends Thread implements Observer {
int RoundLength ;
int currentSecond = 0;
Thread updatePositionsThread = new Thread();
Thread updateRangeThread = new Thread();
    Thread updateMainThread = new Thread();
//public static ExecutorService executorService = Executors.newFixedThreadPool(5);
    public void run(){

        for(;currentSecond<=RoundLength;currentSecond++){
            try {

                //Todo: Be Careful About Time Of the Following Three Methods, it should be 0.9 Second For Them all Together
                //Todo:May Be You need Exception Handling
                //Todo: We should invoke All Players UpdateArmy

                if(getGame().getGameStateName().equals("Running")) {
                    long current = System.currentTimeMillis();


            updatePositionsThread = new Thread(UpdateMapAsyncTask::invokeUpdatePosition);
             updatePositionsThread.start();
             /*updateRangeThread = new Thread(UpdateRangeAsyncTask::invokeUpdateRange);
             updateRangeThread.start();
             */

             updateMainThread = new Thread(MainMethodAsyncTask::invokeMainMethods);
                    updateMainThread.start();

                    current = System.currentTimeMillis() - current;
                    Thread.sleep(1000 - current);
                    //getGame().UpdateUnits();
                    reFill();
                }else if(getGame().getGameStateName().equals("AttackerWin") || getGame().getGameStateName().equals("DefenderWin")){
                    Log.GameOver("GameOver, "  + getGame().getGameStateName());
                    this.interrupt();
                    break;
                }else{
                    currentSecond--;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();

                //Todo: Need To Implement
            }

        }

        if(currentSecond == RoundLength)
        GoodEnd();
       // executorService.shutdown();

    }

    public GameTimer(int roundLength) {
        RoundLength = roundLength;

    }
    private void GoodEnd(){
        getGame().setGameStateName("DefenderWin");
        System.out.println("Times up " + getGame().getGameStateName());

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
        for(Unit unit: getGame().getAllUnits()){
            if(unit.getName().equals("MAIN BASE"))
                continue;
            unit.Move();


        //Todo: here we can make damaging more real
            if( unit.getDamaging().CanShot()>0 && !unit.getName().equals("Black Eagle"))
                for(int i =0;i<unit.getDamaging().CanShot();i++)
            MainMethodAsyncTask.addMethod(() ->unit.getDamaging().DoDamage());
        }
//        Collections.shuffle(updatePositionQueue);

    }


    @Override
    public void update(Observable o, Object arg) {
        //Todo:: its not used
    }
}

