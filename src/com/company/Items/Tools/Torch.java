package com.company.Items.Tools;

public class Torch extends ToolItems{
    public Torch(int buyCost, int sellCost) {
        super(20, 20, "torch");
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
