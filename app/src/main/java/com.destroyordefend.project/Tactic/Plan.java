package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Unit.Unit;

import java.util.HashMap;

public class Plan {
    int waitingTime;
    Point centerPoint;
    HashMap<Unit, Point> unitsInPlane;


    public void applyPlan() {
        //Todo:: Here we should Decide the best Point around CenterPoint to place the Unit There
        //Todo:: make sure the unit tactic is ToTarget (The Target is this Point)
    }

    public boolean isInPlace(Unit unit) {
        return unit.getPosition().equals(unitsInPlane.get(unit));
    }

    public Point getSuitPoint(Unit unit) {
        return unitsInPlane.get(unit);
    }


}
