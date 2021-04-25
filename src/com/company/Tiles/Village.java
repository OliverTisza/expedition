package com.company.Tiles;

import com.company.Companions.AbstractCompanion;
import com.company.Manager.RandomManager;
import com.company.Shops.AbstractShop;
import com.company.Shops.VillageShop;

public class Village extends AbstractTileObject{

    private VillageShop villageShop = new VillageShop();
    private AbstractCompanion companion;
    private RandomManager randomManager = new RandomManager();
    private boolean isFriendly;

    public Village() {

        super(false, true, 'V');
        companion = randomManager.GenerateCompanion(this);
    }


    public VillageShop getVillageShop() {
        return villageShop;
    }

    public AbstractCompanion getCompanion() {
        return companion;
    }

    public void setCompanion(AbstractCompanion companion) {
        this.companion = companion;
    }

    public boolean isFriendly() {
        return isFriendly;
    }

    public void setFriendly(boolean friendly) {
        isFriendly = friendly;
    }
}
