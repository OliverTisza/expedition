package com.company.Manager;

import com.company.Companions.AbstractCompanion;
import com.company.Player.Inventory;
import com.company.Player.Player;
import com.company.Player.Slot;
import com.company.Tiles.AbstractTileObject;

import java.util.List;

public class RenderManager {

    /*
    int height;
    int width;
    AbstractTileObject[][] map;

     */

    public RenderManager() {
        /*
        this.height = height;
        this.width = width;
        this.map = map;
         */
    }

    public void RenderMap(int height, int width, AbstractTileObject[][] map){

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

    public void RenderInventory(List<Slot> slots){

        for( Slot slot : slots){
            System.out.println(slot.getName() +": "+ slot.getHeldCount());
        }

    }

    public void RenderShopInventory(Slot[] vendorSlots, Player player){
        System.out.println("\nShop inventory:");
        for( Slot slot : vendorSlots){
            try {
                System.out.println(slot.getName() +": "+ slot.getHeldCount() + " | Buy cost: " + slot.getHeldItem().getBuyCost() +" gold | Sell cost: " + slot.getHeldItem().getSellCost()+ " gold");
            } catch (NullPointerException e){
                continue;
            }
        }
        System.out.println("\nMy money: " + player.getGold() +" gold");
        System.out.println("My inventory:");
        RenderInventory(player.getInventory().getSlots());

    }

}
