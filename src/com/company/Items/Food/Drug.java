package com.company.Items.Food;

import com.company.Player.Player;

public class Drug extends AbstractFoodItem{
    public Drug() {
        super(20, true,"drug",0,0);
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
