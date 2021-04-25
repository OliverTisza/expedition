package com.company.Items.Food;

import com.company.Player.Player;

public class Whiskey extends AbstractFoodItem{
    public Whiskey() {
        super(20, true,"whiskey",0,0);
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
