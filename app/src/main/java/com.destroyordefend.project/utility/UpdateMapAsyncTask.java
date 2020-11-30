package com.destroyordefend.project.utility;

import java.util.ArrayList;
import java.util.List;

public class UpdateMapAsyncTask {
    static List<Runnable> updatePositionQueue = new ArrayList<>();


    public static void addMethod(Runnable methodCall) {
        updatePositionQueue.add(methodCall);
    }

    public static void clearQueue() {
        updatePositionQueue.clear();
    }

    public static void updateQueue() {

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
        System.out.println("Move Thread name: " + Thread.currentThread().getName());
        System.out.println("dddd" + updatePositionQueue.size());
        for (Runnable updatePosition : updatePositionQueue) {
            updatePosition.run();
        }
        clearQueue();
    }


}