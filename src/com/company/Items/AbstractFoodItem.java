package com.company.Items;

import com.company.Player.Player;

public abstract class AbstractFoodItem extends AbstractItem{

    private int energyAmount;
    private boolean isAddictive;

    private Player player;

    public AbstractFoodItem(int energyAmount, boolean isAddictive, Player player) {
        this.energyAmount = energyAmount;
        this.isAddictive = isAddictive;
        this.player = player;
    }






}
