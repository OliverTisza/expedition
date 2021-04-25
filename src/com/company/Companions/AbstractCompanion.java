package com.company.Companions;

import com.company.Player.Player;

public abstract class AbstractCompanion {

    private int cost;
    private boolean isAddictedToWhiskey;
    private boolean isAddictedToDrug;

    public AbstractCompanion() {
        this.cost = 150;
        this.isAddictedToWhiskey = false;
        this.isAddictedToDrug = false;
    }

    public AbstractCompanion(int cost) {
        this.cost = cost;
        this.isAddictedToWhiskey = false;
        this.isAddictedToDrug = false;
    }

    public abstract void ApplyModifier(Player player);

    public abstract void DestroyModifier(Player player);

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isAddictedToWhiskey() {
        return isAddictedToWhiskey;
    }

    public void setAddictedToWhiskey(boolean addictedToWhiskey) {
        isAddictedToWhiskey = addictedToWhiskey;
    }

    public boolean isAddictedToDrug() {
        return isAddictedToDrug;
    }

    public void setAddictedToDrug(boolean addictedToDrug) {
        isAddictedToDrug = addictedToDrug;
    }
}
