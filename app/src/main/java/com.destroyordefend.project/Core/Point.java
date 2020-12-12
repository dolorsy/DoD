package com.destroyordefend.project.Core;

public class Point implements Comparable<Point> {
    private int x, y;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this(p.x, p.y);
    }



    @Override
    public String toString() {
        return "" + '(' + x + ',' + y + ')';
    }

    public void setPoint(Point n) {
        if(n == null)
            n = new Point(-100,-100);
        this.x = n.getX();
        this.y = n.getY();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double distance(Point point) {
        return Math.sqrt(Math.pow(x - point.x, 2) + Math.pow(y - point.y, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return getX() == point.getX() &&
                getY() == point.getY();
    }

    @Override
    public int compareTo(Point o) {
        int out = Integer.compare(x, o.x);
        if (out == 0)
            out = Integer.compare(y, o.y);
        return out;
    }
}
