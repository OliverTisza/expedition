package com.company.Items;

import com.company.Player.Player;

public class Drug extends AbstractFoodItem{
    public Drug(Player player) {
        super(20, true, player,"Drug",0,0);
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
