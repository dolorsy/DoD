package com.dolor.destroyordefense;

import android.content.Intent;
import android.os.Bundle;
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

    public void show(Unit unit) {
        new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Dialog))
                .setTitle(unit.getName())
                .setMessage(unit.getValues().toString())
                .setPositiveButton("Buy", (dialog, whichButton) -> {
                    try {
                        currentPlayer.getValue().BuyAnArmy(lastBoughtUnit);
                        startActivity(new Intent(ShopActivity.this, ArenaActivity.class));
                        currentPlayer.setValue(playerIterator.next());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create().show();
    }
}