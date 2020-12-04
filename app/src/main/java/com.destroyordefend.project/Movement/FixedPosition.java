package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Unit;

import java.util.Stack;

public class FixedPosition implements Movement {

    @Override
    public Point GetNextPoint(Unit unit) {
        return unit.getPosition();
    }

    @Override
    public Stack<Point> getTruck() {
        return null;
    }


}
