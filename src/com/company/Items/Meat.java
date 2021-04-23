package com.company.Items;

import com.company.Player.Player;

public class Meat extends AbstractFoodItem{


    public Meat(Player player) {
        super(25, false, player,"Meat",0,0);
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
