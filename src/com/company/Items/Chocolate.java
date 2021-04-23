package com.company.Items;

import com.company.Player.Player;

public class Chocolate extends AbstractFoodItem{
    public Chocolate(Player player) {
        super(20, false, player,"Chocolate", 0,0);
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
