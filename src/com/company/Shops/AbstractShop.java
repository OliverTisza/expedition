package com.company.Shops;

import com.company.Items.AbstractItem;
import com.company.Items.Food.Chocolate;
import com.company.Items.Food.Meat;
import com.company.Items.Food.Whiskey;
import com.company.Items.Tools.Machete;
import com.company.Items.Tools.Marble;
import com.company.Items.Tools.Rope;
import com.company.Items.Tools.Torch;
import com.company.Player.Inventory;
import com.company.Player.Slot;

import java.io.Serializable;
import java.util.Random;

public class AbstractShop implements Serializable {

    private static final long serialVersionUID = 1L;
    Inventory vendorInventory = new Inventory();
    AbstractItem[] vendorItems;
    Random random = new Random();

    public AbstractShop(AbstractItem[] vendorItems){

        this.vendorItems = vendorItems;

        for(AbstractItem item : vendorItems){
            for (int i = 0; i < random.nextInt(6)+1; i++){
                this.vendorInventory.addItem(item);
            }
        }
    }

    public Inventory getVendorInventory() {
        return vendorInventory;
    }
}
