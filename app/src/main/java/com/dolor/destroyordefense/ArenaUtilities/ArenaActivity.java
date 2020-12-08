package com.dolor.destroyordefense.ArenaUtilities;


import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Unit.Unit;
import com.dolor.destroyordefense.R;
import com.dolor.destroyordefense.ShopActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static com.dolor.destroyordefense.AndroidManger.lastBoughtUnit;

public class ArenaActivity extends AppCompatActivity {
    //right down corner
    TypeConverter maxX, maxY;
    //current corner
    static
    TypeConverter seekX = new TypeConverter(Type.point),
            seekY = new TypeConverter(Type.point);
    RelativeLayout relativeLayout;
    SeekBar xSeekBar, ySeekBar;
    List<MyImage> inRange = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);
        setTitle("X : " + seekX.toPoint() + " Y : " + seekY.toPoint());

        xSeekBar = findViewById(R.id.XseekBar);
        ySeekBar = findViewById(R.id.YseekBar);
        xSeekBar.requestApplyInsets();
        relativeLayout = findViewById(R.id.my_relative_layout);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        TypeConverter.square = 1;

        maxX = new TypeConverter(mdispSize.x, Type.pixel);
        maxY = new TypeConverter(mdispSize.y, Type.pixel);

        Log.i("", "onCreate: maxX/Y" + maxX + " " + maxY);
        relativeLayout.setX(0);
        relativeLayout.setY(0);
        Log.d("TAG", "onCreate: allUnit.size()" + Game.getGame().getAllUnits().size());
        updateScreen(Game.getGame().getAllUnits());

        xSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekX.setValue(progress, Type.point);
                setTitle("X : " + seekX.toInteger() + " Y : " + seekY.toInteger());
                updateScreen(Game.getGame().getAllUnits());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekY.setValue(progress, Type.point);
                setTitle("X : " + seekX.toInteger() + " Y : " + seekY.toInteger());
                updateScreen(Game.getGame().getAllUnits());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateScreen(Game.getGame().getAllUnits());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customization_action_bar, menu);
        MenuItem item = menu.findItem(R.id.start_game);
        //TODO: start game
        item.setOnMenuItemClickListener(v -> {
            boolean done = Game.getGame().settingUnit(
                    new com.destroyordefend.project.Core.Point(seekX.toInteger(), seekY.toInteger())
                    , lastBoughtUnit
                    , getIntent().getIntExtra("counter", -1));
            if (done) {
                Toast.makeText(this, "unit is bought", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ShopActivity.class));
            } else {
                Toast.makeText(this, "Can not set units here", Toast.LENGTH_SHORT).show();
            }

            return true;
        });
        return super.onCreateOptionsMenu(menu);
    }

    void updateScreen(TreeSet<Unit> allUnits) {
        relativeLayout.removeAllViews();
        inRange = new ArrayList<>();
        relativeLayout.requestLayout();
        for (Unit unit : allUnits) {
            inRange.add(new MyImage(unit, this));
        }
        startUpdate();
    }

    void startUpdate() {
        for (MyImage myImage : inRange) {
            ImageView iv = myImage.imageView;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(myImage.width.toPixel(), myImage.width.toPixel());
            params.leftMargin = myImage.center.getX();
            params.topMargin = myImage.center.getY();
            relativeLayout.addView(iv, params);
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    TypeConverter.square += 1;
                    updateScreen(Game.getGame().getAllUnits());

                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN && TypeConverter.square > 1) {
                    TypeConverter.square -= 1;
                    updateScreen(Game.getGame().getAllUnits());

                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    TypeConverter down() {
        return seekY.plus(maxY);
    }

    TypeConverter left() {
        return seekX;
    }

    TypeConverter right() {
        return seekX.plus(maxX);
    }

    TypeConverter up() {
        return seekY;
    }


}
