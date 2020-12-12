package com.destroyordefend.project.Core;

import com.destroyordefend.project.Unit.Barrier;

import java.util.Comparator;

public class PointComparator implements Comparator<Barrier> {
    @Override
    public int compare(Barrier o1, Barrier o2) {

        if ( o1.getPosition() == null || o2.getPosition() == null || o1.getId() == o2.getId() ) {
            return 0;
        }
        return o1.getPosition().compareTo(o2.getPosition());
    }
}
