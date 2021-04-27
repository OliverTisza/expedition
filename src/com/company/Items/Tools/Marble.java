package com.company.Items.Tools;

public class Marble extends ToolItems{
    public Marble(int buyCost, int sellCost) {
        super(20, 20, "marble");
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
