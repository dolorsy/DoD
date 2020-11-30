package com.destroyordefend.project.Movement;


import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Barrier;
import com.destroyordefend.project.Unit.Unit;

import static com.destroyordefend.project.Core.Game.game;

public interface Movement {

    static Barrier canSetUnitPlace(Point point, Unit unit) {
        Unit temp = new Unit(unit);
        temp.setPoint(point);
        for (Unit u : game.getAllUnits()) {
            if (!(u.isSharedWith(temp)) && u.getId() != temp.getId()) {
                return u;
            }
        }
        for (Barrier u : game.getTerrains()) {
            if (!(u.isSharedWith(temp)) && !u.getPosition().equals(temp.getPosition())) {
                return u;
            }
        }
        return null;
        //Todo: if point on river return 2 else return 1
    }

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

    static Barrier getBarrierBetween(Unit src, Point dis) {
        Point p = src.getPosition();

        Barrier barrier = null;
        while (!p.equals(dis)) {
            p = straightMove(p, dis);
            barrier = canSetUnitPlace(p, src);
            if (barrier != null)
                break;
        }
        return barrier;
    }

    Point GetNextPoint(Unit unit);
}
