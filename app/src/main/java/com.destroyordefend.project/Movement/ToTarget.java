package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.Log;

import java.util.Stack;

public class ToTarget implements Movement {

    private final Stack<Point> track = new Stack<>();

    public ToTarget(Unit target) {
        track.push(target.getPosition());
    }

    @Override
    public void StartMove(Unit unit) {
        unit.getTactic().SortMap(unit);
        if (unit.getTreeSetUnit().size() != 0) {
            System.out.println("Size: " + unit.getTreeSetUnit().size());
            System.out.println("\n\n\n");
            return;
        }

        Point p = unit.getMovement().GetNextPoint(unit);
        if(p.equals(unit.getPosition())){
            return;
        }
        boolean f = Movement.setNext(unit,p);

        if(f){
            unit.getValues().setCurrentSpeed(unit.getSpeed()/2);


        }else{
            unit.getValues().setCurrentSpeed(unit.getSpeed()); ;

        }


        Log.move(unit);

    }

    public void addTarget(Point p, Unit u){
        track.clear();
        if(u.getRole().name().equals("Attacker"))
            track.push(Game.getGame().getBase().getPosition());
        track.push(p);
    }

    @Override
    public Point GetNextPoint(Unit unit) {
        if(track.size() == 0){
            System.out.println(unit.getName()+" "+unit.getId()+" has no targets");
            return unit.getPosition();

        }
        if(unit.getPosition().equals(track.peek()) && !track.empty()){
            System.out.println("\n\n\n" + unit.getPosition() + "   " + track.peek());
            track.pop();
        }
        if(track.empty())
            return unit.getPosition();
        Point p =Movement.straightMove(unit.getPosition(),track.peek());
        return p;
    }


    @Override
    public Stack<Point> getTruck() {
        return track;
    }

//    @Override
//    public boolean SetNextPoint(Unit unit) {
//
//       Point n = Movement.straightMove(unit.getPosition(),track.peek());
//        Barrier barrier = Movement.canSetUnitPlace(n,unit);
//        if(barrier != null){
//            if(barrier.getName().equals("river")) {
//                //unit.setPosition(n);
//                PositionHelper.getInstance().setUnitPlace(unit,n);
//                return true;
//            }
//            Point[] corners = {barrier.getDownLeftCorner(),barrier.getDownRightCorner(),barrier.getUpRightCorner(),barrier.getUpLeftCorner()};
//            int min = 0;
//            int nextp = 1 ;
//            double curDist = unit.getPosition().distance(corners[0]);
//            for (int i = 0; i < 4; i++) {
//                double curAns = (unit.getPosition().distance(corners[i]));
//                if (curAns < curDist ) {
//                    min = i;
//                    if(corners[i].getX() > unit.getRight())
//                        nextp = 3;
//                    else if(corners[i].getX() < unit.getLeft())
//                        nextp = 4;
//                    else if(corners[i].getY() < unit.getDown())
//                        nextp = 2;
//                    else
//                        nextp = 1;
//
//                    curDist = curAns;
//                }
//            }
//            switch (min) {
//                case 1 : {
//                    corners[min].setX(corners[min].getX() - unit.getRadius());
//                    corners[min].setY(corners[min].getY() - unit.getRadius());
//                    break;
//                }
//                case 2 : {
//                    corners[min].setX(corners[min].getX() + unit.getRadius());
//                    corners[min].setY(corners[min].getY() - unit.getRadius());
//                    break;
//                }
//                case 3 : {
//                    corners[min].setX(corners[min].getX() + unit.getRadius());
//                    corners[min].setY(corners[min].getY() + unit.getRadius());
//                    break;
//                }
//                case 4 : {
//                    corners[min].setX(corners[min].getX() - unit.getRadius());
//                    corners[min].setY(corners[min].getY() + unit.getRadius());
//                    break;
//                }
//                default :
//                    throw new IllegalStateException("Unexpected value: " + min);
//            }
//
//            switch (nextp) {
//                case 1: {
//                    corners[nextp].setX(corners[nextp].getX() - unit.getRadius());
//                    corners[nextp].setY(corners[nextp].getY() - unit.getRadius());
//                    break;
//                }
//                case 2 : {
//                    corners[nextp].setX(corners[nextp].getX() + unit.getRadius());
//                    corners[nextp].setY(corners[nextp].getY() - unit.getRadius());
//                    break;
//                }
//                case 3 : {
//                    corners[nextp].setX(corners[nextp].getX() + unit.getRadius());
//                    corners[nextp].setY(corners[nextp].getY() + unit.getRadius());
//                    break;
//                }
//                case 4 : {
//                    corners[nextp].setX(corners[nextp].getX() - unit.getRadius());
//                    corners[nextp].setY(corners[nextp].getY() + unit.getRadius());
//                    break;
//                }
//                default :
//                    throw new IllegalStateException("Unexpected value: " + min);
//            }
//
//            track.push(corners[nextp]);
//            track.push(corners[min]);
//        }
//        //unit.setPosition(n);
//        PositionHelper.getInstance().setUnitPlace(unit,n);
//        return false;
//    }

    @Override
    public Point getTarget() {
        return track.peek();
    }

    @Override
    public String toString() {
        return "ToTarget";
    }
}










/*

package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Barrier;
import com.destroyordefend.project.Unit.Unit;

import java.util.Stack;

public class ToTarget implements Movement {
    private Point target;
    private Barrier goal;
    private final Stack<Barrier> targetStack = new Stack<>();
    private final Stack<Point> truck = new Stack<>();

    public ToTarget(Unit target) {
        this.targetStack.push(target);
        truck.push(target.getPosition());
    }

    @Override
    public Point GetNextPoint(Unit unit) {
            Barrier barrier = Movement.getBarrierBetween(unit, target);
            if (barrier!= null) {
                target = barrier.getPosition();
                goal = barrier;
                targetStack.push(goal);
                truck.push(goal.getPosition());
            }

            if (goal.is("vally") ) {
                if(unit.getPosition().equals(target)){
                    target = null;
                    targetStack.pop();
                    truck.pop();
                    getNextTarget();
                    Point p = Movement.straightMove(target,goal.getPosition());
                    if(Movement.canSetUnitPlace(p,unit)!=null){
                        //todo: tank of sad
                    }
                }

                if (target.equals(goal.getPosition())) {
                    //determine the closest corner
                    Point[] corners = new Point[]{
                            new Point(barrier.getDown() + unit.getRadius(), barrier.getLeft() - unit.getRadius()),
                            new Point(barrier.getDown() + unit.getRadius(), barrier.getRight() + unit.getRadius()),
                            new Point(barrier.getUp() - unit.getRadius(), barrier.getLeft() - unit.getRadius()),
                            new Point(barrier.getUp() - unit.getRadius(), barrier.getRight() + unit.getRadius())};
                    int min = 0;
                    double curDist = unit.getPosition().distance(corners[0]);
                    for (int i = 0; i < 4; i++) {
                        double curAns = (unit.getPosition().distance(corners[i]));
                        if (curAns < curDist ) {
                            min = i;
                            curDist = curAns;
                        }
                    }
                    target = corners[min];
                }
            }
            return Movement.straightMove(unit.getPosition(), target);

    }

    private void getNextTarget() {
        if (target == null) {
            while (!targetStack.empty()) {
                Barrier barrier = targetStack.peek();
                if (barrier.isAlive()) {
                    target = barrier.getPosition();
                    goal = barrier;
                    break;
                }
                targetStack.pop();
            }
            if (target == null) {
                goal = Game.getGame().getBase();
                target = goal.getPosition();
            }
        }else if(!goal.isAlive()){
            target = null;
            getNextTarget();
        }
    }
}

* */