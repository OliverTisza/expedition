package com.company.Items;

public abstract class ToolItems extends AbstractItem {
    public ToolItems(int buyCost, int sellCost, boolean isStackable, String name) {
        super(buyCost, sellCost, isStackable, name);
    }
}
