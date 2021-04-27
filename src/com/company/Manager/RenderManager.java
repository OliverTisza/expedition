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

    /**
     * A térkép kirajzolása konzolba
     * @param height a teljes pálya magassága
     * @param width a teljes pálya szélessége
     * @param map a legenerált pálya
     */
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

    /**
     * A hátizsákunk tartalmának kirajzolása konzolra
     * @param slots a hátizsák rekeszei amelyek vagy tartalmaznak valamit vagy nem
     */
    public void RenderInventory(List<Slot> slots){
        for( Slot slot : slots){
            System.out.println(slot.getName() +": "+ slot.getHeldCount());
        }
    }

    /**
     * A falu boltjának kirajzolása konzolra
     * @param vendorSlots a falu boltjának termékeineki
     * @param player a játékos
     */
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

    /**
     * A faluban felajánlott csapattárs kirajzolása konzolra
     * @param standingOnTile melyik faluban van a játékos
     * @param player a játékos
     */
    public void RenderVillageCompanion(AbstractTileObject standingOnTile, Player player) {
        Scanner scanner = new Scanner(System.in);

        Village village = (Village) standingOnTile;
        if(village.getCompanion() != null){
            System.out.println("A " + village.getCompanion() + " offers his services for 150 gold. \nDo you accept? (y/n)");
            String answer = scanner.nextLine();
            if(answer.equals("y") && player.getGold() >= 150 && player.getCompanions().size() < 3){
                player.getCompanions().add(village.getCompanion());
                player.setGold(player.getGold() - 150);
                village.getCompanion().ApplyModifier(player);
                System.out.println("You hired a " + village.getCompanion());
                village.setCompanion(null);
            } else { System.out.println("Maybe another time"); }
        } else {System.out.println("It seems nobody wishes to join your party"); }
    }

    /**
     * Segítség kirajzolása, a létező parancsok
     */
    public void RenderHelp(){
        System.out.println("move up: moves you upwards");
        System.out.println("move left: moves you left");
        System.out.println("move down: moves you downwards");
        System.out.println("move right: moves you right");
        System.out.println("show map: renders the map");
        System.out.println("show inventory: renders your inventory");
        System.out.println("show shop: renders the village's shop's inventory and your inventory");
        System.out.println("show companion: shows the village's companion offer");
        System.out.println("show legend: shows which tile corresponds to which letter on the map");
        System.out.println("buy <item> <optional quanity>: buy item from shop");
        System.out.println("sell <item>: sell item to shop");
        System.out.println("go home: finish expedition, starts next one");
    }

    /**
     * A térkép jelmagyarázatának kirajzolása konzolra
     */
    public void RenderLegend(){
        System.out.println("A - Altar");
        System.out.println("C - Cave");
        System.out.println("G - Ground");
        System.out.println("J - Jungle");
        System.out.println("l - lake");
        System.out.println("M - Mountain");
        System.out.println("P - Pyramid");
        System.out.println("S - Sea");
        System.out.println("H - Ship");
        System.out.println("V - Village");
        System.out.println("g - Wet ground");
        System.out.println("* - Player");
        System.out.println("x - Undiscovered");
    }

}
