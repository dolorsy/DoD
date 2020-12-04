package com.dolor.destroyordefense;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.destroyordefend.project.Core.Game;

public class MainActivity extends AppCompatActivity {

    Button next;
    EditText attackerNumber, defenderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        next = findViewById(R.id.ToAddingPlayersButton);
        attackerNumber = findViewById(R.id.AttackerPlayerNumberInput);
        defenderNumber = findViewById(R.id.DefenderPlayerNumberInput);
        next.setOnClickListener(v ->
        {
            int an, dn;
            try {
                an = Integer.parseInt(attackerNumber.getText().toString());
                dn = Integer.parseInt(defenderNumber.getText().toString());
                if (an <= 0 || dn <= 0) {
                    throw new Exception("invalid input");
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
                return;
            }
            Game.getGame().setPlayersNumbers(an, dn);
            //  startActivity(new Intent(MainActivity.this, AddingPlayerActivity.class));
            startActivity(new Intent(MainActivity.this, AddingPlayerActivity.class));

            finish();
        });
    }
}