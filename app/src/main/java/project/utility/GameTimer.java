package project.utility;

import static project.Core.Game.game;
import static project.utility.MainMethodAsyncTask.invokeMainMethods;
import static project.utility.UpdateMapAsyncTask.invokeUpdatePosition;
import static project.utility.UpdateRangeAsyncTask.invokeUpdateRange;

public class GameTimer extends Thread {
    int RoundLength = 30;
    int currentSecond = 0;

    public GameTimer(int roundLength) {
        RoundLength = roundLength;
        this.start();

    }

    public void run() {
        for (int i = 1; i <= RoundLength; i++) {
            try {
                //Todo: Each tikTok we should do that game.allUnits = new List(attackersUnits + DefendersUnit);


                //Todo: Be Careful About Time Of the Following Three Methods, it should be 0.9 Second For Them all Together
                //Todo:May Be You need Exception Handling
                //Todo: We should invoke All Players UpdateArmy
                invokeUpdatePosition();
                invokeUpdateRange();
                invokeMainMethods();
                Thread.sleep(1000);
                game.UpdateUnits();
            } catch (InterruptedException e) {
                e.printStackTrace();
                //Todo: Need To Implement
            }

        }


    }

    public boolean onEnd() {
        return currentSecond == RoundLength;
    }

    public int getCurrentSecond() {
        return currentSecond;
    }
}

