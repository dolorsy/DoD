package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Unit;

/**
 * the movement will be up right left down
 */
public class FixedPatrol implements Movement {
    public final static int STEP_SIZE = 4;
    private static final next[] nextArr = new next[]{
            (p) -> new Point(p.getX(), p.getY() - STEP_SIZE),//up
            (p) -> new Point(p.getX() + STEP_SIZE, p.getY()),//right
            (p) -> new Point(p.getX(), p.getY() + STEP_SIZE),//down
            (p) -> new Point(p.getX() - STEP_SIZE, p.getY())};//left
    int lastDirection = -1;

    @Override
    public Point GetNextPoint(Unit unit) {
        if (lastDirection == -1 || lastDirection > nextArr.length)
            lastDirection = 0;
        return nextArr[lastDirection++].getNext(unit.getPosition());
    }

    private interface next {
        Point getNext(Point p);
    }
}
