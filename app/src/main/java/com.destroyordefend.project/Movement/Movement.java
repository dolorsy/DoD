package com.destroyordefend.project.Movement;


import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Barrier;
import com.destroyordefend.project.Unit.Unit;

import static com.destroyordefend.project.Core.Game.game;

public interface Movement {

    static Barrier canSetUnitPlace(Point point, Unit unit) {
        Unit temp = new Unit(unit);
        temp.setPosition(point);
        if (temp.isSharedWith(temp.getNeighbourUnit("left")))
            return temp.getNeighbourUnit("left");
        else if (temp.isSharedWith(temp.getNeighbourUnit("left")))
            return temp.getNeighbourUnit("left");
        else {
            //Todo: need to change
            for (Barrier u : game.getTerrains()) {
                if (!(u.isSharedWith(temp)) && !u.getPosition().equals(temp.getPosition())) {
                    return u;
                }
            }
            return null;
        }
    }

    Point GetNextPoint(Unit unit);

    static Point straightMove(Point src, Point dis) {
        int currentX = src.getX();
        int currentY = src.getY();
        int targetX = dis.getX();
        int targetY = dis.getY();
        if (currentX != targetX) {
            currentX += currentX < targetX ? 1 : -1;
        }
        if (currentY != targetY) {
            currentY += currentY < targetY ? 1 : -1;
        }
        return new Point(currentX, currentY);
    }

    default boolean SetNextPoint(Unit unit) {
        return true;
    }
}
