package com.company.Items.Food;

import com.company.Player.Player;

public class Meat extends AbstractFoodItem{


    public Meat() {
        super(25, false,"meat",20,20);
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
