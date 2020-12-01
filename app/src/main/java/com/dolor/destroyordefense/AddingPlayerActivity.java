package com.dolor.destroyordefense;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.Player;


public class AddingPlayerActivity extends AppCompatActivity {

    Button toShopAddButton;
    EditText name, point;
    TextView role;

    View.OnClickListener toShopClickListener = v -> {
        try {
            getPlayerFromView();
            startActivity(new Intent(AddingPlayerActivity.this, ShopActivity.class));

        } catch (Exception e) {
            Toast.makeText(this, "Invalid data", Toast.LENGTH_LONG).show();
        }
    };

    View.OnClickListener addClickListener = v -> {
        try {
            getPlayerFromView();
            if (Game.getGame().fullDefender())
                role.setText("Attacker");
            AddingPlayerActivity.this.name.setText("");
            AddingPlayerActivity.this.point.setText("");
            Log.i("", "addListener: " + Game.getGame().oneMore());
            if (Game.getGame().oneMore()) {
                toShopAddButton.setText("Shop");
                toShopAddButton.setOnClickListener(toShopClickListener);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Invalid data", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_player);

        toShopAddButton = findViewById(R.id.ToShopAddButton);
        name = findViewById(R.id.PlayerNameInput);
        point = findViewById(R.id.PlayerPointsInput);
        role = findViewById(R.id.Role);
        toShopAddButton.setOnClickListener(addClickListener);

    }

    private void getPlayerFromView() throws Exception {
        if (point.getText().toString().isEmpty() || name.getText().toString().isEmpty())
            throw new Exception();
        int points = Integer.parseInt(point.getText().toString());
        String name = this.name.getText().toString();
        Player p = new Player(points, Player.TeamRole.valueOf(role.getText().toString()), name);
        Game.getGame().addPlayer(p);
    }
}