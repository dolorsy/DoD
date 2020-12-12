package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Plan {
    final int waitingTime;
    final Point centerPoint;
    ArrayList<Unit> unitsInPlan ;
     HashMap<Unit,Point> unitsPoints;

    public Plan(int waitingTime, Point centerPoint) {
        this.waitingTime = waitingTime;
        this.centerPoint = centerPoint;
        this.unitsInPlan = new ArrayList<>();
    }

    public void addToPlan(Unit unit){
        unitsInPlan.add(unit);
    }
    public void startPlane(){
        applyPlan();
    }


    public void applyPlan(){
        Point[] allVectors = {new Point(0,0),new Point(0,1),new Point(1,0),new Point(1,1),
                new Point(0,-1),new Point(-1,0),new Point(-1,-1),new Point(-1,1),new Point(1,-1),};

        Point currentCenter = centerPoint;
        for(int i = 0;i<=unitsInPlan.size()/8;i++){
        for (int j = 0;j<9;j++){
            Unit unit = unitsInPlan.get(j);
            unit.addTarget(new Point(centerPoint.getX() + unit.getRadius()*2*allVectors[j].getX(),
                    centerPoint.getY()+ unit.getRadius()*2*allVectors[j].getY()));
            unitsPoints.put(unit,unit.getMovement().getTarget());
        }
        currentCenter = new Point(currentCenter.getX()+100*allVectors[i+1].getX(),currentCenter.getY() + 100*allVectors[i+1].getY());
        }

        //Todo:: Here we should Decide the best Point around CenterPoint to place the Unit There
        //Todo:: make sure the unit tactic is ToTarget (The Target is this Point)
    }
    public boolean isInPlace(Unit unit){
        return unit.getPosition().equals(unitsPoints.get(unit));
    }


    public Integer getTime() {
    return waitingTime;
    }
}
