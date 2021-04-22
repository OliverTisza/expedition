package com.company.Player;

import com.company.Tiles.AbstractTileObject;

public class Player extends AbstractTileObject {

    private float energy;
    private int gold;
    private int rep;
    private int fame;

    private int rowPos;
    private int colPos;


    private Inventory inventory;

    public Player(int rowPos, int colPos) {
        super(true,true, '*');
        this.energy = 100;
        this.gold = 250;
        this.rep = 3;
        this.fame = 0;
        this.inventory = new Inventory();
        this.rowPos = rowPos;
        this.colPos = colPos;
    }

    public void Move(){


    }


    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

    public int getFame() {
        return fame;
    }

    public void setFame(int fame) {
        this.fame = fame;
    }

    public int getRowPos() {
        return rowPos;
    }

    public void setRowPos(int rowPos) {
        this.rowPos = rowPos;
    }

    public int getColPos() {
        return colPos;
    }

    public void setColPos(int colPos) {
        this.colPos = colPos;
    }
}

