package com.destroyordefend.project.Unit;

public interface Damage {
    void decrease();
    void DoDamage();
    int getDamage();
    void AcceptDamage(int damage);
}
