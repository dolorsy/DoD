package com.dolor.destroyordefense.ArenaUtilities;

import android.util.Log;
import android.widget.ImageView;

import com.destroyordefend.project.Unit.Terrain;
import com.destroyordefend.project.Unit.Unit;
import com.dolor.destroyordefense.R;

import java.sql.SQLOutput;

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
        centerX = centerX.minus(arenaActivity.left());
        centerY = centerY.minus(arenaActivity.up());
        center.setX(centerX.toPixel());
        center.setY(centerY.toPixel());
    }
    public MyImage(Terrain terrain, ArenaActivity arenaActivity) {
        this.imageView = new ImageView(arenaActivity.relativeLayout.getContext());
        this.imageView.setImageResource(getSuitableImage(terrain.getName()));
        this.width = new TypeConverter(terrain.getRadius() * 2, Type.point);
        this.center = new com.destroyordefend.project.Core.Point(terrain.getPosition());
        TypeConverter radius = new TypeConverter(terrain.getRadius(), Type.point);
        TypeConverter centerX = new TypeConverter(center.getX(), Type.point);
        TypeConverter centerY = new TypeConverter(center.getY(), Type.point);
        centerX = centerX.minus(radius);
        centerY = centerY.minus(radius);
        centerX = centerX.minus(arenaActivity.left());
        centerY = centerY.minus(arenaActivity.up());
        center.setX(centerX.toPixel());
        center.setY(centerY.toPixel());
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
            case "Black Eagle":
                return R.drawable.a;
            case "TeslaTank":
                return  R.drawable.tt;
            case "Sniper":
                return R.drawable.sn;
            case "Mirage tank":
                return R.drawable.mirage;
            case "river":
                return R.drawable.river;
            case "vally":
                return  R.drawable.vally;
            case "Navy SEAL":
                return R.drawable.ns;
            case "Tank Destroyer":
                return R.drawable.td;
            case "Prism tank":
                return R.drawable.pt;
            case "pillbox":
                break;
            case "Prism Tower":
                return R.drawable.ptw;
            case "Grand Canon":
                return R.drawable.gc;
            case "MAIN BASE":
                return R.drawable.base;

            case "Pateriot Missile System":
                return R.drawable.pm;
        }
        return R.mipmap.test_icon;
    }
}