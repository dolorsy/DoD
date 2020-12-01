package com.destroyordefend.project.Core;

import com.destroyordefend.project.Unit.Unit;

import java.util.Comparator;

public class PointComparator implements Comparator<Unit> {
    @Override
    public int compare(Unit o1, Unit o2) {
        if (o1.id != o2.id) {
            //Todo: For Delete
            return 1;
        }
        if (o1.getPosition().getX() > o2.getPosition().getX())
            return 1;

        else if (o1.getPosition().getX() == o2.getPosition().getX()) {
            // if (o1.getPosition().getY() > o2.getPosition().getY())
            return o1.getPosition().getY() - o2.getPosition().getY();
        }

        if (o1.getPosition().getX() == o2.getPosition().getX() && o1.getPosition().getY() == o2.getPosition().getY())
            return 0;


        return -1;

    }
}
