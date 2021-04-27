package com.company.Items.Tools;

public class Rope extends ToolItems{
    public Rope(int buyCost, int sellCost) {
        super(20, 20, "rope");
    }

    @Override
    public boolean IsStackable() {
        return isStackable;
    }

    @Override
    public String getName() {
        return name;
    }
}
