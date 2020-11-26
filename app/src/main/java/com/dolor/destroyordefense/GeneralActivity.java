package com.dolor.destroyordefense;

import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class GeneralActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customization_action_bar, menu);
        MenuItem item = menu.findItem(R.id.start_game);
        //TODO: start game
        //item.setOnMenuItemClickListener();
        return super.onCreateOptionsMenu(menu);
    }
}
