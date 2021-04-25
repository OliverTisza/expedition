package com.company.Items.Tools;

import com.company.Items.AbstractItem;

public abstract class ToolItems extends AbstractItem {
    public ToolItems(int buyCost, int sellCost, String name) {
        super(buyCost, sellCost, true, name);
    }
}
