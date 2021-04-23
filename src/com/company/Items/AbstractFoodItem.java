package com.company.Items;

import com.company.Player.Player;

public abstract class AbstractFoodItem extends AbstractItem{

    private int energyAmount;
    private boolean isAddictive;

    private Player player;

    public AbstractFoodItem(int energyAmount, boolean isAddictive, Player player, String name, int buyCost, int sellCost) {
        super(buyCost,sellCost,true,name);
        this.energyAmount = energyAmount;
        this.isAddictive = isAddictive;
        this.player = player;
    }


    public int getEnergyAmount() {
        return energyAmount;
    }

    public void setEnergyAmount(int energyAmount) {
        this.energyAmount = energyAmount;
    }

    public boolean isAddictive() {
        return isAddictive;
    }

    public void setAddictive(boolean addictive) {
        isAddictive = addictive;
    }
}
