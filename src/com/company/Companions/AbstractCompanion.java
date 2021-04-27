package com.company.Companions;

import com.company.Player.Player;

public abstract class AbstractCompanion {

    private int cost;
    private int drugWithrawal;
    private int whiskeyWithrawal;

    private boolean isAddictedToWhiskey;
    private boolean isAddictedToDrug;

    public AbstractCompanion() {
        this.cost = 150;
        this.isAddictedToWhiskey = false;
        this.isAddictedToDrug = false;
        this.drugWithrawal = 0;
        this.whiskeyWithrawal = 0;
    }

    public AbstractCompanion(int cost) {
        this.cost = cost;
        this.isAddictedToWhiskey = false;
        this.isAddictedToDrug = false;
    }


    /**
     * Végrehajtja a bónuszokat amiket a csapattársak adnak, ezek csapattársanként változnak
     * @param player a játékos akin végre lesz hajtva a módosítás
     */
    public abstract void ApplyModifier(Player player);

    /**
     * A csapattársak bónuszainak visszavonása, csapattársanként változó
     * @param player a játékos akiről visszavonódik a módosítás
     */

    public abstract void DestroyModifier(Player player);

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isAddictedToWhiskey() {
        return isAddictedToWhiskey;
    }

    public void setAddictedToWhiskey(boolean addictedToWhiskey) {
        isAddictedToWhiskey = addictedToWhiskey;
    }

    public boolean isAddictedToDrug() {
        return isAddictedToDrug;
    }

    public void setAddictedToDrug(boolean addictedToDrug) {
        isAddictedToDrug = addictedToDrug;
    }

    public int getDrugWithrawal() {
        return drugWithrawal;
    }

    public void setDrugWithrawal(int drugWithrawal) {
        this.drugWithrawal = drugWithrawal;
    }

    public int getWhiskeyWithrawal() {
        return whiskeyWithrawal;
    }

    public void setWhiskeyWithrawal(int whiskeyWithrawal) {
        this.whiskeyWithrawal = whiskeyWithrawal;
    }
}
