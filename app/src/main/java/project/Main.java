package project;


import java.util.Comparator;
import java.util.TreeSet;

import project.Unit.Unit;


public class Main {

    public static void main(String[] args) {
        //GameTimer gameTimer= new GameTimer(10);

        TreeSet<Unit> test = new TreeSet<>(new Comparator<Unit>() {
            @Override
            public int compare(Unit o1, Unit o2) {
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
        });
        test.add(new Unit(2, 2, 2, "dd", 2, 2, 2, 2));
        test.add(new Unit(2, 2, 2, "dd", 2, 2, 2, 2));

    }

}
