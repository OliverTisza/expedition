package com.company.Tiles;

public class Ship extends AbstractTileObject{

    private int rowPos;
    private int colPos;

    public Ship(int rowPos, int colPos) {
        super(true, true,'H');
        this.rowPos = rowPos;
        this.colPos = colPos;
    }
}
