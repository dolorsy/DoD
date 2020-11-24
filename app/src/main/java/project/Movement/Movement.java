package project.Movement;


import project.Core.Point;
import project.Unit.Unit;

import static project.Core.Game.game;

public interface Movement {
    static int SetUnitPlace(Point point, Unit unit) {
        Unit temp = new Unit(unit);
        temp.setPoint(point);
        for (Unit u : game.getAllUnits()) {
            if (!(u.getLeft() >= temp.getRight() || u.getRight() <= temp.getLeft()
                    || u.getUp() <= temp.getDown() || u.getDown() >= temp.getUp())) {
                return 0;
            }
        }
        return 1;
        //Todo: if point on river return 2 else return 1
    }

    Point GetNextPoint(Point concurrent);

}
