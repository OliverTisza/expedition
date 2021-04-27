package com.company.Items.Food;

import com.company.Player.Player;

public class Chocolate extends AbstractFoodItem{
    public Chocolate() {
        super(20, false,"chocolate", 20,20);
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
