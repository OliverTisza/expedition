package com.company.Items.Tools;

public class Machete extends ToolItems{

    public Machete(int buyCost, int sellCost) {
        super(buyCost, sellCost, "machete");
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
