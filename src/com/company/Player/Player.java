package com.company.Player;

import com.company.Tiles.AbstractTileObject;

public class Player extends AbstractTileObject {

    private float energy;
    private int gold;
    private int rep;
    private int fame;
    private int visionRange;

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
        this.visionRange = 1;
    }

    public void Move(String input){

        try {
            switch (input){
                case "up":
                    setRowPos(getRowPos()-1);
                    break;
                case "down":
                    setRowPos(getRowPos()+1);
                    break;
                case "left":
                    setColPos(getColPos()-1);
                    break;
                case "right":
                    setColPos(getColPos()+1);
                    break;
            }

            if(getEnergy() >0){
                setEnergy(getEnergy() - 1);
            }
            

        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("You can't go out of bounds");
        }

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

    public int getVisionRange() {
        return visionRange;
    }

    public void setVisionRange(int visionRange) {
        this.visionRange = visionRange;
    }
}

