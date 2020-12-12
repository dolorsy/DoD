package com.destroyordefend.project.utility;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Unit;

import java.util.HashMap;
import java.util.Map;


public class PositionHelper {
    private final static int add = 1000, remove = 2000, search = 3000;

    private PositionHelper() {
    }

    private static PositionHelper ins;
    private final Map<String, Unit> allPoints = new HashMap<>();

    public static PositionHelper getInstance() {
        if (ins == null) {
            synchronized (PositionHelper.class) {
                if (ins == null)
                    ins = new PositionHelper();
            }
        }
        return ins;
    }

    public Unit canSetAt(Unit u, Point newPoint) {
        Point current = u.getPosition();
        u.setPosition(newPoint);
        Unit result = edit(u, search);
        u.setPosition(current);
        if(u .equals(result))
            return null;
        return result;
    }

    public void setUnitPlace(Unit unit, Point newPoint) {
        Unit temp = canSetAt(unit,newPoint);
        if(temp!=null && temp.getId()!=unit.getId())
            throw new RuntimeException(unit.getLeft()+" shared set with " +temp.getRight());
        removeUnitAllocatedPosition(unit);
        unit.setPosition(newPoint);
        edit(unit, add);
    }

    public void removeUnitAllocatedPosition(Unit unit) {
       // System.out.println("size before"+allPoints.size());
        edit(unit, remove);
        //System.out.println("size after"+allPoints.size());
    }

    private Unit edit(Unit unit, final int swich) {
        Unit out ;
        int left = unit.getLeft();
        int right = unit.getRight();
        int up = unit.getUp();
        int down = unit.getDown();

        for (int x = left; x <= right; x++) {
            for (int y = up; y <= down; y++) {
                Point temp = new Point(x,y);
                if (swich == add) {
                    /*if(allPoints.get(temp.toString())!=null)
                        throw new RuntimeException(temp+"\nshared place"
                                +allPoints.get(temp.toString()).getName()+" "+allPoints.get(temp.toString()).getPosition()+unit.getId()+" "+unit.getPosition());*/
                    allPoints.put(new Point(temp).toString(), unit);
                    //System.out.println("added done" + unit.getId());
                } else if (swich == remove) {
                    allPoints.remove(temp.toString());
                } else {
                    out = allPoints.get(temp.toString());
                    // System.out.println(temp+"this is b " + out);
                    if (out != null && out.getId() != unit.getId()) {
                        //System.out.println(temp+"hello break"+out.getId()+" "+unit.getId());
                        return out;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "PositionHelper{" +allPoints.size()+"\n"+
                "allPoints=" + allPoints +
                '}';
    }
}
