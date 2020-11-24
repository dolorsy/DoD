package project.utility;

import java.util.PriorityQueue;

public class MainMethodAsyncTask {

    static PriorityQueue<Runnable> doMainThingQueue = new PriorityQueue<>();

    public static void addMethod(Runnable methodCall) {
        doMainThingQueue.add(methodCall);
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

    public static void invokeMainMethods() {
        for (Runnable updatePosition : doMainThingQueue) {
            updatePosition.run();
        }
    }

}
