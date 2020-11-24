package project.Movement;


import project.Core.Point;

public class FixedPosition implements Movement {

    @Override
    public Point GetNextPoint(Point concurrent) {
        return concurrent;
    }
}
