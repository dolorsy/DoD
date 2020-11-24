package project.utility;

import java.util.ArrayList;
import java.util.List;

public class UpdateRangeAsyncTask {
    static List<Runnable> updateRangeQueue = new ArrayList<>();

    public static void addMethod(Runnable methodCall) {

        updateRangeQueue.add(methodCall);
    }

    /**
     * From any Class We Will Store Methods Like The Following
     * Runnable methodCall = () ->  MyMethodCall;
     * UpdateMapAsyncTask.addMethod(methodCall);
     * <p>
     * Example:
     * Runnable method = () -> invokeUpdatePosition();
     * updatePositionQueue.add(method);
     */

    public static void invokeUpdateRange() {
        for (Runnable updateRange : updateRangeQueue) {
            updateRange.run();
        }
    }

}
