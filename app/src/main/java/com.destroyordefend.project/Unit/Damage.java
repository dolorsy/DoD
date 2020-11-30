package com.destroyordefend.project.Unit;

public interface Damage {
    int canShot = 0;

    void decrease();

    void DoDamage();

    int getDamage();

    void AcceptDamage(int damage);
}
