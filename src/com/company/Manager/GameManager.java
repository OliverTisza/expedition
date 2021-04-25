package com.company.Manager;

import com.company.Companions.AbstractCompanion;
import com.company.Items.Food.AbstractFoodItem;
import com.company.Items.Food.Chocolate;
import com.company.Items.Food.Whiskey;
import com.company.Items.Tools.Torch;
import com.company.Player.Player;
import com.company.Player.Slot;
import com.company.Shops.AbstractShop;
import com.company.Shops.StartingShop;
import com.company.Tiles.*;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GameManager {

    private final int HEIGHT = 10;
    private final int WIDTH = 20;
    private final AbstractTileObject[][] map = new AbstractTileObject[HEIGHT][WIDTH];
    private final RandomManager randomManager = new RandomManager();

    private RenderManager renderManager = new RenderManager();
    Scanner scanner = new Scanner(System.in);

    private Player player = new Player();
    private AbstractTileObject standingOnTile;

    public void StartLevel(){

        /*TODO offer companion
           then show shop
           then create new map
         */
        //CreateNewMap();

        // TMP
        /*
        player.setEnergy(2);

        for(int i = 0; i < 60; i++){
            player.getInventory().addItem(new Torch(20,20));
        }
        for(int i = 0; i < 6; i++){
            player.getInventory().addItem(new Whiskey());
        }
        */

        StartingShop startingShop = new StartingShop();
        renderManager.RenderShopInventory(startingShop.getVendorSlots(), player);
        Preparation(startingShop);
        //CreateNewMap();


    }

    private void Preparation(StartingShop startingShop) {
        //renderManager.RenderShopInventory(startingShop.getVendorSlots(), player);

        System.out.println("Continue to map? (yes to continue)");
        String playerInput = scanner.nextLine();
        String[] playerInputWords = playerInput.split(" ");

        if(playerInputWords[0].equals("yes")) CreateNewMap();

        else if(playerInputWords[0].equals("buy")) {
            BuyFromShop(startingShop, playerInputWords);
            Preparation(startingShop);
        }
    }

    private void BuyFromShop(AbstractShop shop, String[] playerInputWords) {
        for(Slot slot : shop.getVendorSlots()){
            if(slot.getName().equals(playerInputWords[1]) && player.getGold() > 0){
                if(playerInputWords.length > 2){
                    try{
                        for (int i = 0; i < Integer.parseInt(playerInputWords[2]); i++){
                            player.getInventory().addItem(slot.getHeldItem());
                            slot.decreaseHeldCount();
                            player.setGold(player.getGold() - slot.getHeldItem().getBuyCost());
                        }

                    } catch (NullPointerException e){
                        System.out.println("I don't have any more");
                    }
                } else{
                    player.getInventory().addItem(slot.getHeldItem());
                    slot.decreaseHeldCount();
                    player.setGold(player.getGold() - slot.getHeldItem().getBuyCost());
                }

            }

        }
        renderManager.RenderShopInventory(shop.getVendorSlots(), player);
    }

    public void CreateNewMap(){

        int[] seaPos = randomManager.GenerateSeaPos(HEIGHT,WIDTH);

        int seaRowPos = seaPos[0];
        int seaColPos = seaPos[1];

        System.out.println("Sea pos: "+seaRowPos +" "+ seaColPos );


        // Sorrend fontos!
        CreateWaterAndGround(seaRowPos, seaColPos);
        randomManager.RandomizeGround(map, WIDTH, HEIGHT);
        CreateShipAndPyramid(seaRowPos, seaColPos);

        standingOnTile = new Ground();
        standingOnTile.setExplored(true);

        AbstractCompanion startingCompanion = randomManager.GenerateCompanion(standingOnTile);

        Explore();

        renderManager = new RenderManager();
        renderManager.RenderMap(HEIGHT,WIDTH,map);

        // TMP

        Update();


    }

    public void Update(){


        System.out.println("What would you like to do?");
        String playerInput = scanner.nextLine();

        String[] playerInputWords = playerInput.split(" ");

        if(playerInputWords.length < 2){
            renderManager.RenderMap(HEIGHT,WIDTH,map);
            System.out.println("Invalid action");
            System.out.println("Actual tile: " + standingOnTile.getSymbol());
            Update();
        }

        if(isValidAction(playerInputWords)){

            if (OutOfEnergy()) return;

            if(playerInputWords[0].equals("move")){
                ExecuteMoveAction(playerInputWords[1]);
            }

            else if(playerInputWords[0].equals("show") ){
                ExecuteShowAction(playerInputWords[1]);
            }
            else if(playerInputWords[0].equals("buy")){
                if(standingOnTile.getSymbol() == 'V'){
                    BuyFromShop(((Village)standingOnTile).getVillageShop(),playerInputWords);

                }
            }

            else if(playerInputWords[0].equals("use")) {

                List<Slot> slots = player.getInventory().getSlots();
                Collections.reverse(slots);
                for (Slot slot : slots) {
                    if (slot.getName().equals(playerInputWords[1])) {

                        if (playerInputWords[1].equals("whiskey")) {
                            player.increaseEnergy(((AbstractFoodItem) slot.getHeldItem()).getEnergyAmount() + player.getWhiskeyBonus());
                            if (randomManager.BecomesAddicted()) {
                                //player.CompanionArray[randomNum].setAddicted(true);
                            }
                            slot.decreaseHeldCount();
                            Collections.reverse(slots);
                            break;
                        } else if (playerInputWords[1].equals("drug")) {
                            player.increaseEnergy(((AbstractFoodItem) slot.getHeldItem()).getEnergyAmount() + player.getDrugBonus());
                            if (randomManager.BecomesAddicted()) {
                                //player.CompanionArray[randomNum].setAddicted(true);
                            }
                            slot.decreaseHeldCount();
                            Collections.reverse(slots);
                            break;
                        } else {
                            player.increaseEnergy(((AbstractFoodItem) slot.getHeldItem()).getEnergyAmount());
                            slot.decreaseHeldCount();
                            Collections.reverse(slots);
                            break;
                        }
                    }


                }
            }

            System.out.println("Player energy: " + player.getEnergy());

            Update();
        } else {
            renderManager.RenderMap(HEIGHT,WIDTH,map);
            System.out.println("Invalid action");
            System.out.println("Actual tile: " + standingOnTile.getSymbol());
            Update();

        }

    }

    private boolean OutOfEnergy() {

        //TODO: companions should always leave first

        if(player.getEnergy() <= 0){
            boolean isLeaving = randomManager.LeaveEvent();
            if(isLeaving){
                System.out.println("The explorer has left the party. \n Game Over.");
                return true;
            }
        }
        return false;
    }

    private void CreateShipAndPyramid(int seaRowPos, int seaColPos) {

        int[] uniquePos;

        uniquePos = randomManager.GeneratePyramidLocation(HEIGHT,WIDTH, seaRowPos, seaColPos);
        map[uniquePos[0]][uniquePos[1]] = new Pyramid();
        System.out.println("Pyramid location: "+uniquePos[0] +" "+ uniquePos[1]);

        uniquePos = randomManager.GenerateShipLocation(seaRowPos, seaColPos);
        map[uniquePos[0]][uniquePos[1]] = new Ship(uniquePos[0], uniquePos[1]);
        System.out.println("Ship location: "+uniquePos[0] +" "+ uniquePos[1]);

        if(map[uniquePos[0]][uniquePos[1]+1].isWalkable()){
            //player = new Player(uniquePos[0], uniquePos[1]+1);
            player.setRowPos(uniquePos[0]);
            player.setColPos(uniquePos[1]+1);
            map[uniquePos[0]][uniquePos[1]+1] = player;
        }
        else {
            //player = new Player(uniquePos[0]+1, uniquePos[1]);
            player.setRowPos(uniquePos[0]+1);
            player.setColPos(uniquePos[1]);
            map[uniquePos[0]+1][uniquePos[1]] = player;
        }
    }

    private void CreateWaterAndGround(int seaRowPos, int seaColPos) {

        for(int i = 0; i < HEIGHT; i++){
            for (int j = 0; j < WIDTH; j++){

                if(i > seaRowPos || j > seaColPos){ map[i][j] = new Ground(); }
                else { map[i][j] = new Sea(); }
            }
        }
    }

    private void ExecuteShowAction(String playerInputWord) {
        switch (playerInputWord){
            case "companion":
                randomManager.GenerateCompanion(standingOnTile);
                break;
            case "shop":
                if(standingOnTile.getSymbol() == 'V') {
                    Village tmp = (Village) standingOnTile;
                    renderManager.RenderShopInventory(tmp.getVillageShop().getVendorSlots(), player);
                }
                break;
            case "map":
                renderManager.RenderMap(HEIGHT,WIDTH,map);
                break;

            case "inventory":
                renderManager.RenderInventory(player.getInventory().getSlots());
                break;
        }
    }

    private void ExecuteMoveAction(String playerInputWord) {
        map[player.getRowPos()][player.getColPos()] = standingOnTile;
        player.Move(playerInputWord);
        standingOnTile = map[player.getRowPos()][player.getColPos()];
        map[player.getRowPos()][player.getColPos()] = player;

        System.out.println("Actual tile: " + standingOnTile.getSymbol());

        Explore();
        renderManager.RenderMap(HEIGHT,WIDTH,map);
    }

    public boolean isValidAction(String[] playerInputWords){
        try {
            switch (playerInputWords[1]){
                case "up":
                    return map[player.getRowPos()-1][player.getColPos()].isWalkable();

                case "down":
                    return map[player.getRowPos()+1][player.getColPos()].isWalkable();

                case "left":
                    return map[player.getRowPos()][player.getColPos()-1].isWalkable();

                case "right":
                    return map[player.getRowPos()][player.getColPos()+1].isWalkable();

                case "buy":

                    return true;

                case "map":
                    return true;

                case "inventory":
                    return true;

                default:
                    return true;


            }
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("You can't go out of bounds");
            return  false;
        }
        //return  false;
    }

    private void Explore() {

            for (int i = player.getVisionRange()*-1; i <= player.getVisionRange(); i++){
                for( int j = player.getVisionRange()*-1; j <= player.getVisionRange(); j++){
                    try{
                    map[player.getRowPos()+i][player.getColPos()+j].setExplored(true);
                    } catch (ArrayIndexOutOfBoundsException e){
                        continue;
                    }
                }
            }
    }

}
