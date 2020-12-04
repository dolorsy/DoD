package com.dolor.destroyordefense;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static com.dolor.destroyordefense.AndroidManger.lastBoughtUnit;

public class ArenaActivity extends GeneralActivity {
    int maxX, maxY;
    int seekX, seekY;
    ConstraintLayout constraintLayout;
    SeekBar xSeekBar, ySeekBar;
    List<MyImage> inRange = new ArrayList<>();
    int square;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);
        setTitle("X : " + seekX + " Y : " + seekY);

        xSeekBar = findViewById(R.id.XseekBar);
        ySeekBar = findViewById(R.id.YseekBar);

        constraintLayout =  findViewById(R.id.my_relative_layout);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        square = mdispSize.x / 11;

        maxX = mdispSize.x;
        maxY = mdispSize.y;
        constraintLayout.setX(0);
        constraintLayout.setY(0);
        System.out.println(maxX);
        System.out.println(maxY);
        System.out.println(square);
        Log.d("TAG", "onCreate: allUnit.size()" + Game.getGame().getAllUnits().size());
          updateScreen(Game.getGame().getAllUnits());
        constraintLayout.setOnClickListener(v -> {
            lastBoughtUnit.setPosition(new com.destroyordefend.project.Core.Point(seekX, seekY));
             System.out.println(lastBoughtUnit.getValues().getName());
            Game.getGame().getAllUnits().add(lastBoughtUnit);
            Log.i("TAG", "onClick: lastBoughtUnit.pos " + lastBoughtUnit.getPosition());
            Toast.makeText(this, "unit is bought", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ShopActivity.class));
        });

        xSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekX = progress;
                setTitle("X : " + seekX + " Y : " + seekY);
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
                seekY = progress;
                setTitle("X : " + seekX + " Y : " + seekY);
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

    void updateScreen(TreeSet<Unit> allUnit) {
        Log.i("", "updateScreen: allUnit.size()" + allUnit.size());
        constraintLayout.removeAllViews();
        inRange = new ArrayList<>();
        int left = seekX;
        int right = seekX + maxX;
        int up = seekY;
        int down = seekY + maxY;
        left /= square;
        up /= square;
        right /= square;
        down /= square;
        Log.d("Yaaa", "updateScreen: " + left + " " + right + " " + up + " " + " " + down);
        for (Unit unit : allUnit) {
            System.out.println(unit.getPosition());
            if ((unit.getPosition().getX() >= left && unit.getPosition().getX() <= right)
                    && (unit.getPosition().getY() <= down && unit.getPosition().getY() >= up)) {
                inRange.add(new MyImage(unit));
            }
        }
        startUpdate();
    }

    void startUpdate() {
        ConstraintLayout.LayoutParams params;
        for (MyImage myImage : inRange) {
            System.out.println("startUpdate: img width" + myImage.getImageView().getWidth());
            params = new ConstraintLayout.LayoutParams(myImage.getWidth(), myImage.getWidth());
            constraintLayout.addView(myImage.getImageView(), params);
        }
    }

    @Override
    public void onBackPressed() {

    }

    class MyImage {
        ImageView imageView;
        int width;
        com.destroyordefend.project.Core.Point center;

        public MyImage(Unit unit) {
            this.imageView = new ImageView(ArenaActivity.this);
            this.imageView.setImageResource(getSuitableImage(unit.getName()));
            this.width = unit.getRadius() * 2 * square;
            imageView.setLayoutParams(new ConstraintLayout.LayoutParams(width,width));
           // imageView.getLayoutParams().height = width*square;
            this.center = new com.destroyordefend.project.Core.Point(unit.getPosition());
           /* this.center.setX(center.getX() - (seekX / 11));
            this.center.setY(center.getY() + (seekY / 11));*/
            center.setX(seekX - center.getX()*square);
            center.setY(seekY - center.getY()*square);
            Log.d("", "MyImage: center = " + center);
        }

        public ImageView getImageView() {

            return imageView;
        }

        public int getWidth() {
            return width;
        }

        public com.destroyordefend.project.Core.Point getCenter() {
            return center;
        }

        private int getSuitableImage(String type) {
            switch (type) {
                case "TeslaTank":
                    break;
                case "Sniper":
                    break;
                case "Mirage Tank":
                    break;
                case "Infantry":
                    break;
                case "Grizzly Tank":
                    break;
                case "Navy SEAL":
                    break;
                case "Tank Destroyer":
                    break;
                case "Prism Tank":
                    break;
                case "pillbox":
                    break;
                case "Prism Tower":
                    break;
                case "Grand Canon":
                    break;
                case "MainBase":
                    break;
                case "Black Eagle":
                    break;
                case "Pateriot Missile System":
                    break;
            }
            return R.mipmap.test_icon;
        }
    }
}