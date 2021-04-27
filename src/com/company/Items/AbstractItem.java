package com.company.Items;

import java.io.Serializable;

public abstract class AbstractItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private int buyCost;
    private int sellCost;
    protected boolean isStackable;
    protected String name;


    public abstract boolean IsStackable();
    public abstract String getName();

    public AbstractItem(int buyCost, int sellCost, boolean isStackable, String name) {
        this.buyCost = buyCost;
        this.sellCost = sellCost;
        this.isStackable = isStackable;
        this.name = name;
    }

    public int getBuyCost() {
        return buyCost;
    }

    public void setBuyCost(int buyCost) {
        this.buyCost = buyCost;
    }

    public int getSellCost() {
        return sellCost;
    }

    public void setSellCost(int sellCost) {
        this.sellCost = sellCost;
    }
}
