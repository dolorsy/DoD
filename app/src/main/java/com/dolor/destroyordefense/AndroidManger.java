package com.dolor.destroyordefense;

import androidx.lifecycle.MutableLiveData;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.Player;
import com.destroyordefend.project.Unit.Unit;

import java.util.Iterator;

public class AndroidManger {
    public static MutableLiveData<Player> currentPlayer = new MutableLiveData<>();
    public static Iterator<Player> playerIterator = Game.getGame().playerIterator();
    public static Unit lastBoughtUnit;


}
