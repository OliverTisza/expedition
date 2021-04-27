package com.company.Items.Food;

import com.company.Player.Player;

public class Fruit extends AbstractFoodItem{
    public Fruit() {
        super(15, false,"fruit",20,20);
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
