package com.dolor.destroyordefense;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.destroyordefend.project.Unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ArenaActivity extends GeneralActivity {
    int x = 30, y = 40, maxX, maxY;
    int seekx, seeky;
    ConstraintLayout rl;
    SeekBar Xseek, Yseek;
    List<MyImage> inRange = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);
        Xseek = (SeekBar) findViewById(R.id.XseekBar);
        Yseek = (SeekBar) findViewById(R.id.YseekBar);

        rl = (ConstraintLayout) findViewById(R.id.my_relative_layout);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        maxX = mdispSize.x;
        maxY = mdispSize.y;

        rl.setX(0);
        rl.setY(0);

        rl.setOnClickListener(v -> {


        });


        Xseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                seekx = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Yseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seeky = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    void updateScreen(TreeSet<Unit> allUnit) {
        inRange = new ArrayList<>();
        int left = seekx - maxX / 2;
        int right = seekx + maxX / 2;
        int up = seeky + maxY / 2;
        int down = seeky - maxY / 2;
        for (Unit unit : allUnit) {
            if ((unit.getPosition().getX() > left && unit.getPosition().getX() < right)
                    && (unit.getPosition().getY() > down && unit.getPosition().getY() < up)) {
                inRange.add(new MyImage(unit));
            }
        }


    }

    void startUpdate(ArrayList<MyImage> range) {

        RelativeLayout.LayoutParams params;

        for (MyImage myImage : range) {
            params = new RelativeLayout.LayoutParams(myImage.getCenter().getX(), myImage.getCenter().getY());

            rl.addView(myImage.getImageView(), params);
        }

    }

    class MyImage {
        ImageView imageView;
        int width;
        com.destroyordefend.project.Core.Point center;

        public MyImage(Unit unit) {
            this.imageView = new ImageView(getApplicationContext());
            this.imageView.setImageResource(getSuitableImage(unit.getType()));
            this.width = unit.getRadius() * 2;
            this.center = unit.getPosition();
            this.center.setX(center.getX() - (seekx - maxX / 2));
            this.center.setY(center.getY() - (seeky + maxX / 2));
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
            return 0;
        }
    }
}