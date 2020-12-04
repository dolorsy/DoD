package com.destroyordefend.project.Unit;


import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.utility.IdGenerator;

public class Terrain implements Barrier {

    public final int id;
    private Point point;
    private int speedFactory;
    private int radius;
    private int health;
    private String name;

    public Terrain() {
        id = IdGenerator.generate(this);
    }

    @Override
    public boolean isAlive() {
        return health != 0;
    }

    public Point getPosition() {
        return point;
    }

    @Override
    public int getRadius() {
        return radius;
    }

    public Terrain(Point point, int speedFactory, String name) {
        this();
        this.point = point;
        this.speedFactory = speedFactory;
        this.name = name;
    }

    public int getSpeedFactory() {
        return speedFactory;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
