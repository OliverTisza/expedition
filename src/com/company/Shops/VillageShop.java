package com.company.Shops;

import com.company.Items.AbstractItem;
import com.company.Items.Food.Drug;
import com.company.Items.Food.Fruit;
import com.company.Items.Food.Meat;
import com.company.Items.Tools.Rope;
import com.company.Items.Tools.Torch;
import com.company.Player.Slot;

import java.util.Random;

public class VillageShop extends AbstractShop{

    public VillageShop(){
        super(5, new AbstractItem[]{ new Torch(20,20),new Rope(20,20), new Meat(), new Drug() , new Fruit() });

    }

}
