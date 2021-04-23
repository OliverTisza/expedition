package com.company.Tiles;

import com.company.Shops.Shop;

public class Village extends AbstractTileObject{

    private Shop shop;

    public Village() {
        super(false, true, 'V');
    }


    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
