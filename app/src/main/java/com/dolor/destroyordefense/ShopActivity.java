package com.dolor.destroyordefense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.destroyordefend.project.Core.Shop;
import com.destroyordefend.project.Unit.Unit;

import static com.dolor.destroyordefense.AndroidManger.currentPlayer;
import static com.dolor.destroyordefense.AndroidManger.lastBoughtUnit;
import static com.dolor.destroyordefense.AndroidManger.playerIterator;


public class ShopActivity extends GeneralActivity {
    RecyclerView recyclerView;
    UnitAdapter unitAdapter;

    TextView playerName;
    Shop shop = Shop.getInstance();
    TextView playerPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        recyclerView = findViewById(R.id.shop_recycler_view);
        playerName = findViewById(R.id.player_name);
        playerPoints = findViewById(R.id.player_points);
        currentPlayer.observe(this, player -> {

            playerName.setText(player.getName());
            playerPoints.setText(player.getPoints() + "");
            unitAdapter.setUnitList(shop.getShopUnits(), currentPlayer.getValue().getPoints());
        });
        if (currentPlayer.getValue() == null)
            currentPlayer.setValue(playerIterator.next());

        playerName.setText(currentPlayer.getValue().getName());
        playerPoints.setText(currentPlayer.getValue().getPoints() + "");
        unitAdapter = new UnitAdapter(shop.getShopUnits(), this, currentPlayer.getValue().getPoints());
        recyclerView.setAdapter(unitAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    public void show(Unit.UnitValues unit) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Dialog))
               /* .setPositiveButton("Buy", (dialog, whichButton) -> {
                    try {
                        currentPlayer.getValue().BuyAnArmy(lastBoughtUnit);
                        startActivity(new Intent(ShopActivity.this, ArenaActivity.class));
                        currentPlayer.setValue(playerIterator.next());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                })*/;
        View v = getLayoutInflater().inflate(R.layout.activity_unit_details, null);
        TextView name, range, speed, shotSpeed, health, damage, armor, currentSpeed, sortedMap, price;
        Button next;

        name = v.findViewById(R.id.UnitName);
        name.setText("Name:             " + unit.getName());

        range = v.findViewById(R.id.UnitRange);
        range.setText("Range:            " + unit.getRange());

        speed = v.findViewById(R.id.UnitSpeed);
        speed.setText("Speed:            " + unit.getSpeed());

        shotSpeed = v.findViewById(R.id.UnitShotSpeed);
        shotSpeed.setText("ShotSpeed:    " + unit.getShot_speed());

        health = v.findViewById(R.id.UnitHealth);
        health.setText("Health:            " + unit.getHealth());

        damage = v.findViewById(R.id.UnitDamage);
        damage.setText("Damage:         " + unit.getDamage());

        armor = v.findViewById(R.id.UnitArmor);
        armor.setText("Price:                " + unit.getArmor());

        currentSpeed = v.findViewById(R.id.UnitCurrentSpeed);
        currentSpeed.setText("currentSpeed:    " + unit.getCurrentSpeed());

        sortedMap = v.findViewById(R.id.UnitSortedMap);
        sortedMap.setText("Targets:    " + unit.getSortMap());

        price = v.findViewById(R.id.UnitPrice);
        price.setText("Price:                " + unit.getPrice());

        next = v.findViewById(R.id.buy);
        next.setOnClickListener((v2) -> {
            try {
                currentPlayer.getValue().BuyAnArmy(lastBoughtUnit);
                startActivity(new Intent(ShopActivity.this, ArenaActivity.class));
                currentPlayer.setValue(playerIterator.next());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        alertDialog/*.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())*/
                .setView(v)
                .create().show();
    }
}