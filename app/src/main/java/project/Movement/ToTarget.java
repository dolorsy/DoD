package project.Movement;

import project.Core.Point;
import project.Unit.Unit;

public class ToTarget implements Movement {
    private final Unit target;

    public ToTarget(Unit target) {
        this.target = target;
    }

    @Override
    public Point GetNextPoint(Point concurrent) {
        int currentX = concurrent.getX();
        int currentY = concurrent.getY();
        int targetX = target.getPosition().getX();
        int targetY = target.getPosition().getY();
        if (currentX != targetX) {
            currentX += currentX < targetX ? 1 : -1;
        }
        if (currentY != targetY) {
            currentY += currentY < targetY ? 1 : -1;
        }
        return new Point(currentX, currentY);
    }
}
