package com.destroyordefend.project.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class UpdateRangeAsyncTask {
    static List<Runnable> updateRangeQueue = new ArrayList<>();

    public static void addMethod(Runnable methodCall) {

        updateRangeQueue.add(methodCall);
    }

    /**
     * From any Class We Will Store Methods Like The Following
     * Runnable methodCall = () ->  MyMethodCall;
     * UpdateMapAsyncTask.addMethod(methodCall);
     *
     * Example:
     * Runnable method = () -> invokeUpdatePosition();
     *         updatePositionQueue.add(method);
     */

    public static void invokeUpdateRange()
    {
        System.out.println("Range Thread name: " + Thread.currentThread().getName());
        for(Runnable updateRange : updateRangeQueue) {
            updateRange.run();
        }
        clearQueue();
    }
    public static void clearQueue(){
        updateRangeQueue.clear();
    }

}
