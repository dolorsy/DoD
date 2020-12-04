package com.destroyordefend.project.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class UpdateMapAsyncTask {
    static List<Runnable> updatePositionQueue = new ArrayList<>();


    public static void addMethod(Runnable methodCall) {
        updatePositionQueue.add(methodCall);
    }

    public static void clearQueue(){
        updatePositionQueue.clear();
    }
    public static void updateQueue(){

    }

    /**
     * From any Class We Will Store Methods Like The Following
     * Runnable methodCall = () ->  MyMethodCall;
     * UpdateMapAsyncTask.addMethod(methodCall);
     *
     * Example
     * Runnable method = () -> invokeUpdatePosition();
     *         updatePositionQueue.add(method);
     */

    public static void invokeUpdatePosition()
    {
        System.out.println("Move Thread name: " + updatePositionQueue.size()  + "  " + Thread.currentThread().getName());
        for(Runnable updatePosition : updatePositionQueue) {
            updatePosition.run();
        }
        clearQueue();
    }


}
