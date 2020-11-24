package project.utility;

import java.util.ArrayList;
import java.util.List;

public class UpdateMapAsyncTask {
    static List<Runnable> updatePositionQueue = new ArrayList<>();


    public static void addMethod(Runnable methodCall) {
        updatePositionQueue.add(methodCall);


    }

    /**
     * From any Class We Will Store Methods Like The Following
     * Runnable methodCall = () ->  MyMethodCall;
     * UpdateMapAsyncTask.addMethod(methodCall);
     * <p>
     * Example
     * Runnable method = () -> invokeUpdatePosition();
     * updatePositionQueue.add(method);
     */

    public static void invokeUpdatePosition() {
        for (Runnable updatePosition : updatePositionQueue) {

            updatePosition.run();
        }
    }


}
