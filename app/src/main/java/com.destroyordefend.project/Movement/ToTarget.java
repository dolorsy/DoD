package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Barrier;
import com.destroyordefend.project.Unit.Unit;

import java.util.Stack;

public class ToTarget implements Movement {
    private final Stack<Barrier> targetStack = new Stack<>();
    private Point target;
    private Barrier goal;

    public ToTarget(Unit target) {
        this.targetStack.push(target);
        goal = target;
    }

    @Override
    public Point GetNextPoint(Unit unit) {
        getNextTarget();
        if (unit.getType().startsWith("air")) {//air unit
            return Movement.straightMove(unit.getPosition(), target);
        } else {//ground unit
            /*if(goal.is("vally")){
                Point p = Movement.straightMove(unit.getPosition(),target);
                return p;
            }*/
            Barrier barrier = Movement.getBarrierBetween(unit, target);

            if (barrier != null) {
                target = barrier.getPosition();
                goal = barrier;
                targetStack.push(goal);
            }

            if (goal.is("vally")) {
                if (unit.getPosition().equals(target)) {
                    target = null;
                    targetStack.pop();
                    getNextTarget();
                    Point p = Movement.straightMove(target, goal.getPosition());
                    if (Movement.canSetUnitPlace(p, unit) != null) {
                        //todo: tank of sad
                    }
                }
                //this condition means new vally target is set
                if (target.equals(goal.getPosition())) {
                    //determine the closest corner
                    Point[] corners = new Point[]{
                            new Point(barrier.getDown() + unit.getRadius(), barrier.getLeft() - unit.getRadius()),
                            new Point(barrier.getDown() + unit.getRadius(), barrier.getRight() + unit.getRadius()),
                            new Point(barrier.getUp() - unit.getRadius(), barrier.getLeft() - unit.getRadius()),
                            new Point(barrier.getUp() - unit.getRadius(), barrier.getRight() + unit.getRadius())};
                    int min = 0;
                    double curDist = unit.getPosition().distance(corners[0]);
                    for (int i = 0; i < corners.length; i++) {
                        double curAns = (unit.getPosition().distance(corners[i]));
                        if (curAns < curDist) {
                            min = i;
                            curDist = curAns;
                        }
                    }
                    target = corners[min];
                }
            }
            return Movement.straightMove(unit.getPosition(), target);
        }
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
            if (target == null)
                target = Game.getGame().getBase().getPosition();

        } else if (!goal.isAlive()) {
            target = null;
            getNextTarget();
        }
    }
}