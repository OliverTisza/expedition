package com.company.Tiles;

import java.io.Serializable;

public abstract class AbstractTileObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean isExplored;
    private boolean isWalkable;
    private char symbol;


    public AbstractTileObject(boolean isExplored, boolean isWalkable, char symbol) {
        this.isExplored = isExplored;
        this.isWalkable = isWalkable;
        this.symbol = symbol;
    }

    public boolean isExplored() { return isExplored; }

    public void setExplored(boolean explored) { isExplored = explored; }

    public boolean isWalkable() { return isWalkable; }

    public void setWalkable(boolean walkable) { isWalkable = walkable; }

    public char getSymbol() { return symbol; }

    public void setSymbol(char symbol) { this.symbol = symbol; }


}
