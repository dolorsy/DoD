package com.destroyordefend.project.Unit;

import com.destroyordefend.project.Core.Point;

public interface Barrier {
    boolean isAlive();

    Point getPosition();

    int getId();

    int getRadius();

    String getName();

    default boolean isSharedWith(Barrier b) {
        if (getPosition() == null || b == null || b.getPosition() == null)
            return false;
        return !(
                this.getUp() >= b.getDown() ||
                        this.getDown() <= b.getUp() ||
                        this.getRight() <= b.getLeft() ||
                        this.getLeft() >= b.getRight()
        );
    }

    default boolean is(String name) {
        return getName().equals(name);
    }

    default int getLeft() {
        return getPosition().getX() - this.getRadius();
    }

    default int getRight() {
        return getPosition().getX() + this.getRadius();
    }

    default int getUp() { return getPosition().getY() - this.getRadius(); }

    default int getDown() {
        return getPosition().getY() + this.getRadius();
    }

    default Point getDownRightCorner() {
        return new Point(this.getRight(), this.getDown());
    }

    default Point getDownLeftCorner() {
        return new Point(this.getLeft(), this.getDown());
    }

    default Point getUpRightCorner() {
        return new Point(this.getRight(), this.getUp());
    }

    default Point getUpLeftCorner() {
        return new Point(this.getLeft(), this.getUp());
    }

}
