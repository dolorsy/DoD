package com.destroyordefend.project.Unit;


import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.utility.IdGenerator;

public class Terrain implements Barrier{

    private Point point;
    private final int id;
    private int speedFactory;
    private int radius;
    private int health;
    private String name;

    public Terrain(){
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

    @Override
    public int getId() {
        return id;
    }

    public int getSpeedFactory() {
        System.out.println("Factory " + speedFactory);
        return speedFactory;
    }

    public String getName() {
        return name;
    }

    public Terrain(Point point, int speedFactory, String name) {
        this();
        this.point = point;
        this.speedFactory = speedFactory;
        this.name = name;
        this.radius=10;
    }

}
