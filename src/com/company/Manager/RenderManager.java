package com.company.Manager;

import com.company.Companions.AbstractCompanion;
import com.company.Player.Inventory;
import com.company.Player.Player;
import com.company.Player.Slot;
import com.company.Tiles.AbstractTileObject;
import com.company.Tiles.Village;

import java.util.List;
import java.util.Scanner;

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
                //System.out.print(map[i][j].getSymbol() + " ");
                if (map[i][j].isExplored()){
                    System.out.print(map[i][j].getSymbol() + " ");
                }
                else { System.out.print("x ");}
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

    public void RenderShopInventory(List<Slot> vendorSlots, Player player){
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

    public void RenderVillageCompanion(AbstractTileObject standingOnTile, Player player) {
        Scanner scanner = new Scanner(System.in);

        Village village = (Village) standingOnTile;
        if(village.getCompanion() != null){
            System.out.println("A " + village.getCompanion() + " offers his services for 150 gold. \nDo you accept? (y/n)");
            String answer = scanner.nextLine();
            if(answer.equals("y") && player.getGold() >= 150 && player.getCompanions().size() < 3){
                player.getCompanions().add(village.getCompanion());
                player.setGold(player.getGold() - 150);
                System.out.println("You hired a " + village.getCompanion());
                village.setCompanion(null);
            } else { System.out.println("Maybe another time"); }
        } else {System.out.println("It seems nobody wishes to join your party"); }
    }

}
