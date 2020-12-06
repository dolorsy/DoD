package com.dolor.destroyordefense.ArenaUtilities;

import android.util.Log;
import android.widget.ImageView;

import com.destroyordefend.project.Unit.Unit;
import com.dolor.destroyordefense.R;

public class MyImage {
    ImageView imageView;
    TypeConverter width;
    com.destroyordefend.project.Core.Point center;

    public MyImage(Unit unit, ArenaActivity arenaActivity) {
        this.imageView = new ImageView(arenaActivity.relativeLayout.getContext());
        this.imageView.setImageResource(getSuitableImage(unit.getName()));
        this.width = new TypeConverter(unit.getRadius() * 2, Type.point);
        this.center = new com.destroyordefend.project.Core.Point(unit.getPosition());
        TypeConverter radius = new TypeConverter(unit.getRadius(), Type.point);
        TypeConverter centerX = new TypeConverter(center.getX(), Type.point);
        TypeConverter centerY = new TypeConverter(center.getY(), Type.point);
        centerX = centerX.minus(radius);
        centerY = centerY.minus(radius);
        Log.d("TAG", "MyImage: " + radius);
        centerX = centerX.minus(arenaActivity.left());
        centerY = centerY.minus(arenaActivity.up());
        center.setX(centerX.toPixel());
        center.setY(centerY.toPixel());
        Log.d("", "MyImage: center = " + center);
    }

    public ImageView getImageView() {

        return imageView;
    }

    public int getWidth() {
        return width.toInteger();
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