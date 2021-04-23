package com.company.Companions;

import com.company.Player.Player;

public abstract class AbstractCompanion {

    private int cost;
    private boolean isAddicted;

    public AbstractCompanion() {
        this.cost = 150;
        this.isAddicted = false;
    }

    public abstract void ApplyModifier(Player player);


    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isAddicted() {
        return isAddicted;
    }

    public void setAddicted(boolean addicted) {
        isAddicted = addicted;
    }
}
