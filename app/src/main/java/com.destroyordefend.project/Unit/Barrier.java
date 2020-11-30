package com.destroyordefend.project.Unit;

import com.destroyordefend.project.Core.Point;

public interface Barrier {
    boolean isAlive();

    Point getPosition();

    int getRadius();

    String getType();

    default boolean isSharedWith(Barrier b) {
        return getLeft() >= b.getRight() || getRight() <= b.getLeft()
                || getUp() <= b.getDown() || getDown() >= b.getUp();
    }

    default boolean is(String type) {
        return getType().equals(type);
    }

    default int getLeft() {
        return getPosition().getX() - this.getRadius();
    }

    default int getRight() {
        return getPosition().getX() + this.getRadius();
    }

    default int getUp() {
        return getPosition().getY() + this.getRadius();
    }

    default int getDown() {
        return getPosition().getY() - this.getRadius();
    }
}
