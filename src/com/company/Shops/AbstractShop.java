package com.company.Shops;

import com.company.Items.AbstractItem;
import com.company.Items.Food.Chocolate;
import com.company.Items.Food.Meat;
import com.company.Items.Food.Whiskey;
import com.company.Items.Tools.Machete;
import com.company.Items.Tools.Marble;
import com.company.Items.Tools.Rope;
import com.company.Items.Tools.Torch;
import com.company.Player.Slot;

import java.util.Random;

public class AbstractShop {

    Slot[] vendorSlots;
    AbstractItem[] vendorItems;
    Random random = new Random();

    public AbstractShop(int slotSize, AbstractItem[] vendorItems){

        this.vendorSlots = new Slot[slotSize];
        this.vendorItems = vendorItems;

        for( int i = 0; i < vendorSlots.length; i++){
            int randomNum = random.nextInt(6)+1;
            vendorSlots[i] = new Slot();
            for(int j = 0; j < randomNum; j++){

                vendorSlots[i].addItem(vendorItems[i]);
            }
        }
    }


    public Slot[] getVendorSlots() {
        return vendorSlots;
    }


}
