package com.dolor.destroyordefense;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.destroyordefend.project.Core.Shop;

import static com.dolor.destroyordefense.AndroidManger.currentPlayer;
import static com.dolor.destroyordefense.AndroidManger.lastBoughtUnit;
import static com.dolor.destroyordefense.AndroidManger.playerIterator;

/*
public class UnitDetailsActivity extends GeneralActivity {
    TextView name, range, speed, shotSpeed, health, damage, price;
    Button buy;
    @SuppressLint({"SetTextI18n", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_details);
        Shop shop = Shop.getInstance();
        lastBoughtUnit = shop.getUnitByName(getIntent().getStringExtra("name"));

        name = findViewById(R.id.UnitName);
        name.setText("Name:             " + lastBoughtUnit.getValues().getName());

        range = findViewById(R.id.UnitRange);
        range.setText("Range:            " + lastBoughtUnit.getRange());

        speed = findViewById(R.id.UnitSpeed);
        speed.setText("Speed:            " + lastBoughtUnit.getSpeed());

        shotSpeed = findViewById(R.id.UnitShotSpeed);
        shotSpeed.setText("ShotSpeed:    " + lastBoughtUnit.getShot_speed());

        health = findViewById(R.id.UnitHealth);
        health.setText("Health:            " + lastBoughtUnit.getHealth());

        damage = findViewById(R.id.UnitDamage);
        damage.setText("Damage:         " + lastBoughtUnit.getDamage());

        price = findViewById(R.id.UnitPrice);
        price.setText("Price:                 " + lastBoughtUnit.getValues().getPrice());

        buy = findViewById(R.id.buy);

        //TODO get player and add if for check player points


        buy.setOnClickListener(v ->
                {
                    try {
                        currentPlayer.getValue().BuyAnArmy(lastBoughtUnit);
                        currentPlayer.setValue(playerIterator.next());
                        startActivity(new Intent(UnitDetailsActivity.this, ArenaActivity.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    onBackPressed();
                    finish();
                }
        );
    }

    @Override
    public void onBackPressed() {
    }
}
*/
