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

}
