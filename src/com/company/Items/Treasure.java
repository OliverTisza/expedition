package com.company.Items;

public class Treasure extends AbstractItem{
    public Treasure() {
        super(0, 250, false, "treasure");
    }

    @Override
    public boolean IsStackable() {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }
}
