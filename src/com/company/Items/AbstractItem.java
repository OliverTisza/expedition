package com.company.Items;

public abstract class AbstractItem {

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
}
