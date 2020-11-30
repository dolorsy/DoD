package com.dolor.destroyordefense;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.destroyordefend.project.Core.Shop;
import com.destroyordefend.project.Unit.Unit;


public class UnitDetailsActivity extends GeneralActivity {
    TextView name, range, speed, shotSpeed, health, damage, price;
    Button next;

    @SuppressLint({"SetTextI18n", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_details);
        Shop shop = new Shop();
        Unit unit = shop.getUnitByType(getIntent().getStringExtra("type"));

        name = findViewById(R.id.UnitName);
        name.setText("Name:             " + unit.getId());

        range = findViewById(R.id.UnitRange);
        range.setText("Range:            " + unit.getRange());

        speed = findViewById(R.id.UnitSpeed);
        speed.setText("Speed:            " + unit.getSpeed());

        shotSpeed = findViewById(R.id.UnitShotSpeed);
        shotSpeed.setText("ShotSpeed:    " + unit.getShot_speed());

        health = findViewById(R.id.UnitHealth);
        health.setText("Health:            " + unit.getHealth());

        damage = findViewById(R.id.UnitDamage);
        damage.setText("Damage:         " + unit.getDamage());

        price = findViewById(R.id.UnitPrice);
        price.setText("Price:                 " + unit.getType());

        next = findViewById(R.id.next);

        //TODO get player and add if for check player points


        next.setOnClickListener(v -> startActivity(new Intent(this, ArenaActivity.class)));


    }
}