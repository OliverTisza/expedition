package com.company.Tiles;

import com.company.Shops.AbstractShop;
import com.company.Shops.VillageShop;

public class Village extends AbstractTileObject{

    private VillageShop villageShop = new VillageShop();

    public Village() {

        super(false, true, 'V');
    }


    public VillageShop getVillageShop() {
        return villageShop;
    }
}
