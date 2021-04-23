package com.company.Manager;

import com.company.Companions.AbstractCompanion;
import com.company.Player.Inventory;
import com.company.Player.Slot;
import com.company.Tiles.AbstractTileObject;

import java.util.List;

public class RenderManager {

    int height;
    int width;
    AbstractTileObject[][] map;

    public RenderManager(int height, int width, AbstractTileObject[][] map) {
        this.height = height;
        this.width = width;
        this.map = map;
    }

    public void RenderMap(){

        for(int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){

                System.out.print(map[i][j].getSymbol() + " ");
                /*
                if (map[i][j].isExplored()){
                    System.out.print(map[i][j].getSymbol() + " ");
                }
                else { System.out.print("x ");}
                 */

            }
            System.out.println();
        }
        System.out.println();
    }

    public void RenderShop(){

    }

    public void RenderInventory(List<Slot> slots){

        for( Slot slot : slots){
            System.out.println(slot.getName() +": "+ slot.getHeldCount());
        }

    }

}
