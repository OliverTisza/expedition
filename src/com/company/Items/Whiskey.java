package com.company.Items;

import com.company.Player.Player;

public class Whiskey extends AbstractFoodItem{
    public Whiskey(Player player) {
        super(20, true, player,"Whiskey",0,0);
    }

    @Override
    public boolean IsStackable() {
        return this.isStackable;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
