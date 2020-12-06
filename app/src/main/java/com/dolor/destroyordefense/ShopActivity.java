package com.dolor.destroyordefense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.destroyordefend.project.Core.Shop;
import com.destroyordefend.project.Unit.Unit;
import com.dolor.destroyordefense.ArenaUtilities.ArenaActivity;

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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Dialog));
        View v = getLayoutInflater().inflate(R.layout.activity_unit_details, null);
        TextView name, range, speed, shotSpeed, health, damage, armor, radius, sortedMap, price, count;
        Button buy, plus, minus;

        name = v.findViewById(R.id.UnitName);
        name.setText("Name:             " + unit.getName());

        range = v.findViewById(R.id.UnitRange);
        range.setText("Range:             " + unit.getRange());

        speed = v.findViewById(R.id.UnitSpeed);
        speed.setText("Speed:             " + unit.getSpeed());

        shotSpeed = v.findViewById(R.id.UnitShotSpeed);
        shotSpeed.setText("ShotSpeed:     " + unit.getShot_speed());

        health = v.findViewById(R.id.UnitHealth);
        health.setText("Health:            " + unit.getHealth());

        damage = v.findViewById(R.id.UnitDamage);
        damage.setText("Damage:         " + unit.getDamage());

        armor = v.findViewById(R.id.UnitArmor);
        armor.setText("Armor:             " + unit.getArmor());

        radius = v.findViewById(R.id.UnitRadius);
        radius.setText("Radius:             " + unit.getRadius());

        sortedMap = v.findViewById(R.id.UnitSortedMap);
        sortedMap.setText("Targets:    " + unit.getSortMap());

        price = v.findViewById(R.id.UnitPrice);
        price.setText("Price:                    " + unit.getPrice());
        count = v.findViewById(R.id.count);
        buy = v.findViewById(R.id.buy);
        buy.setOnClickListener((v2) -> {
            try {
                // TODO: 05/12/2020 place the units in its correct place arround a square
                int counter = Integer.parseInt(count.getText().toString());
                if (counter == 0)
                    return;
                for (int i = 0; i < counter; i++)
                    currentPlayer.getValue().BuyAnArmy(lastBoughtUnit);
                startActivity(new Intent(ShopActivity.this, ArenaActivity.class).putExtra("counter", counter));
                currentPlayer.setValue(playerIterator.next());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        plus = v.findViewById(R.id.plus);
        plus.setOnClickListener(v2 -> {
            int playerPoints = Integer.parseInt(ShopActivity.this.playerPoints.getText().toString());
            playerPoints -= unit.getPrice();
            if (playerPoints < 0) {
                Toast.makeText(ShopActivity.this, "There is no enough points", Toast.LENGTH_SHORT).show();
            } else {
                ShopActivity.this.playerPoints.setText(playerPoints + "");
                count.setText(Integer.parseInt(count.getText().toString()) + 1 + "");
            }
        });
        minus = v.findViewById(R.id.minus);
        minus.setOnClickListener(v2 -> {
            int counter = Integer.parseInt(count.getText().toString());
            if (counter <= 0)
                return;
            int playerPoints = Integer.parseInt(ShopActivity.this.playerPoints.getText().toString());
            playerPoints += unit.getPrice();
            ShopActivity.this.playerPoints.setText(playerPoints + "");
            count.setText(--counter + "");
        });
        alertDialog/*.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())*/
                .setView(v)
                .setOnDismissListener(dialog ->
                        ShopActivity.this.playerPoints.setText("" + currentPlayer.getValue().getPoints()))
                .create().
                show();
    }
}