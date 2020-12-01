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
    Button buy;

    @SuppressLint({"SetTextI18n", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_details);
        Shop shop = Shop.getInstance();
        Unit unit = shop.getUnitByName(getIntent().getStringExtra("name"));

        name = findViewById(R.id.UnitName);
        name.setText("Name:             " + unit.getValues().getName());

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
        price.setText("Price:                 " + unit.getValues().getPrice());

        buy = findViewById(R.id.buy);

        //TODO get player and add if for check player points


      /*  buy.setOnClickListener(v ->
                {
                    try {
                        ShopActivity.currentPlayer.BuyAnArmy(unit);
                        ShopActivity.currentPlayer = ShopActivity.playerIterator.next();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    onBackPressed();
                    finish();
                }
        );*/
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ShopActivity.class));
    }
}