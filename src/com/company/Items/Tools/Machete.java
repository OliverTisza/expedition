package com.company.Items.Tools;

public class Machete extends ToolItems{

    public Machete(int buyCost, int sellCost) {
        super(20, 20, "machete");
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
