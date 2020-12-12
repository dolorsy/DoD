package com.destroyordefend.project.Movement;


import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Barrier;
import com.destroyordefend.project.Unit.Terrain;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.PositionHelper;
import org.json.simple.JSONObject;

import java.util.Stack;

public interface Movement {


    void StartMove(Unit unit);
    void addTarget(Point p, Unit u);

    Point GetNextPoint(Unit unit);

    Stack<Point> getTruck();


    static Barrier canSetUnitPlace(Point point, Unit unit) {

        Barrier b = PositionHelper.getInstance().canSetAt(unit, point);
        if (b != null)
            return b;
        Point cur = unit.getPosition();
        unit.setPosition(point);
        for (Terrain t : Game.getGame().getTerrains()) {
            if (t.isSharedWith(unit)) {
                unit.setPosition(cur);
                return t;
            }
        }
        unit.setPosition(cur);
        return null;
    }

    static Point straightMove(Point src, Point dis) {
        int currentX = src.getX();
        int currentY = src.getY();
        int targetX = dis.getX();
        int targetY = dis.getY();
        double curdist = src.distance(dis);
        if (currentX != targetX) {
            //currentX += currentX < targetX ? 1 : -1;
            if(currentX>targetX)
                currentX--;
            else
                currentX++;
        }
        if (currentY != targetY) {
            //// currentY += currentY < targetY ? 1 : -1;
            if(currentY>targetY)
                currentY--;
            else
                currentY++;
        }
        dis = new Point(currentX, currentY);
        double newDist = src.distance(dis);
        if(curdist<newDist)
            throw new RuntimeException("move does not work"+ currentX+" "+currentY);
        return dis;
    }

    static boolean setNext(Unit unit, Point n) {
        Barrier barrier = Movement.canSetUnitPlace(n, unit);
        if (barrier == null || barrier.is("river")) {
            PositionHelper.getInstance().setUnitPlace(unit, n);
            System.out.println(unit.getPosition());
            return barrier != null;
        }
        System.out.println("gomu gomu noo");

        Point[] corners = {
                new Point(barrier.getLeft() - unit.getRadius()-1, barrier.getDown() + unit.getRadius()+1),
                new Point(barrier.getRight() + unit.getRadius()+1, barrier.getDown() + unit.getRadius()+1),
                new Point(barrier.getLeft() - unit.getRadius()-1, barrier.getUp() - unit.getRadius()-1),
                new Point(barrier.getRight() + unit.getRadius()+1, barrier.getUp() - unit.getRadius()-1)
        };
        Point target = unit.getMovement().getTarget();
        Point closer = corners[getCloserTo(target, corners)];
        unit.getMovement().getTruck().push(closer);
        int indexOfFarther = getFartherTo(closer, corners);
        corners[indexOfFarther] = null;
        indexOfFarther = getCloserTo(unit.getPosition(),corners);
        unit.getMovement().getTruck().push(corners[indexOfFarther]);
//        if(canSetUnitPlace(p,unit)==null)
//            PositionHelper.getInstance().setUnitPlace(unit, n);
        /*
        if (barrier != null) {
            if (barrier.getName().equals("river")) {
                PositionHelper.getInstance().setUnitPlace(unit, n);
                return true;
            }
            Point[] corners = {barrier.getDownLeftCorner(), barrier.getDownRightCorner(),
                    barrier.getUpLeftCorner(), barrier.getUpRightCorner()};
            int min = 0;
            int nextp = 1;
            double curDist = unit.getPosition().distance(corners[0]);
            for (int i = 0; i < 4; i++) {
                double curAns = (unit.getPosition().distance(corners[i]));
                if (curAns < curDist) {
                    min = i;
                    if (corners[i].getX() > unit.getRight())
                        nextp = 3;
                    else if (corners[i].getX() < unit.getLeft())
                        nextp = 4;
                    else if (corners[i].getY() < unit.getDown())
                        nextp = 2;
                    else
                        nextp = 1;

                    curDist = curAns;
                }
            }
            switch (min) {
                case 0: {
                    corners[min].setX(corners[min].getX() - unit.getRadius());
                    corners[min].setY(corners[min].getY() - unit.getRadius());
                    break;
                }
                case 1: {
                    corners[min].setX(corners[min].getX() + unit.getRadius());
                    corners[min].setY(corners[min].getY() - unit.getRadius());
                    break;
                }
                case 2: {
                    corners[min].setX(corners[min].getX() + unit.getRadius());
                    corners[min].setY(corners[min].getY() + unit.getRadius());
                    break;
                }
                case 3: {
                    corners[min].setX(corners[min].getX() - unit.getRadius());
                    corners[min].setY(corners[min].getY() + unit.getRadius());
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + min);
            }

            switch (nextp) {
                case 0: {
                    corners[nextp].setX(corners[nextp].getX() - unit.getRadius());
                    corners[nextp].setY(corners[nextp].getY() - unit.getRadius());
                    break;
                }
                case 1: {
                    corners[nextp].setX(corners[nextp].getX() + unit.getRadius());
                    corners[nextp].setY(corners[nextp].getY() - unit.getRadius());
                    break;
                }
                case 2: {
                    corners[nextp].setX(corners[nextp].getX() + unit.getRadius());
                    corners[nextp].setY(corners[nextp].getY() + unit.getRadius());
                    break;
                }
                case 3: {
                    corners[nextp].setX(corners[nextp].getX() - unit.getRadius());
                    corners[nextp].setY(corners[nextp].getY() + unit.getRadius());
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + nextp);
            }

            unit.getMovement().getTruck().push(corners[nextp]);
            unit.getMovement().getTruck().push(corners[min]);
        }
        //Todo: Should Update n here? n = makeAnewPoint(unit); ???? no it should be in unit.move();
*/
        //PositionHelper.getInstance().setUnitPlace(unit, n);
        return false;
    }

    Point getTarget();

    @Override
    String toString();

     static int getCloserTo(Point target, Point[] points) {
        int index = -1;
        double minValue = 1000000000;
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null || target.equals(points[i]))
                continue;
            if (minValue > points[i].distance(target)) {
                minValue = points[i].distance(target);
                index = i;
            }
        }
        return index;
    }

     static int getFartherTo(Point target, Point[] points) {
        int index = -1;
        double maxValue = -10000000;
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null || target.equals(points[i]))
                continue;
            if (maxValue < points[i].distance(target)) {
                maxValue = points[i].distance(target);
                index = i;
            }
        }
        return index;
    }
}
