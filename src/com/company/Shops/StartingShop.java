package com.company.Shops;

import com.company.Items.AbstractItem;
import com.company.Items.Food.*;
import com.company.Items.Tools.Machete;
import com.company.Items.Tools.Marble;
import com.company.Items.Tools.Rope;
import com.company.Items.Tools.Torch;
import com.company.Player.Slot;

import java.util.Random;

public class StartingShop extends AbstractShop{

    public StartingShop() {
        super(new AbstractItem[]{ new Marble(10,10),new Torch(20,20),new Rope(20,20),
                        new Machete(20,20), new Chocolate(), new Whiskey(), new Meat() });
    }
}
