package com.company.Items;

import com.company.Player.Player;

public class Fruit extends AbstractFoodItem{
    public Fruit(Player player) {
        super(15, false, player,"Fruit",0,0);
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
