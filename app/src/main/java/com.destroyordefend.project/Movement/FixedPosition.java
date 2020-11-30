package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Unit;

public class FixedPosition implements Movement {

    @Override
    public Point GetNextPoint(Unit unit) {
        return unit.getPosition();
    }
}
