package com.company.Manager;

import com.company.Companions.AbstractCompanion;
import com.company.Items.Food.AbstractFoodItem;
import com.company.Items.Treasure;
import com.company.Player.Player;
import com.company.Player.Slot;
import com.company.Shops.AbstractShop;
import com.company.Shops.StartingShop;
import com.company.Tiles.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class GameManager {

    private final int HEIGHT = 10;
    private final int WIDTH = 20;
    private final AbstractTileObject[][] map = new AbstractTileObject[HEIGHT][WIDTH];
    private final RandomManager randomManager = new RandomManager();

    private RenderManager renderManager = new RenderManager();
    Scanner scanner = new Scanner(System.in);

    private final Player player = new Player();
    private final HashMap<String, Integer> rivals = new HashMap<>()
    {{
        put("Fredrick", 1000);
        put("Alvarez", 750);
        put("Wordsworth", 500);
        put("Lee", 0);
    }};
    private AbstractTileObject standingOnTile;

    private int whiskeyDrank = 0;
    private int drugUsed = 0;

    public void StartLevel(){

        AbstractCompanion purchasableCompanion = randomManager.GenerateCompanion(new Ship(0,0));
        System.out.println("You may hire <" + purchasableCompanion.toString() + "> as your new companion! The cost is: " + purchasableCompanion.getCost() + " gold pieces!");
        System.out.println("Would you like to hire? (y/n): ");

        String answer;
        do {
            answer = scanner.nextLine().strip();
            System.out.println("Would you like to hire? (y/n): ");
        } while(!(answer.equals("y") || answer.equals("n")));

        if(answer.equals("y")) {
            if(player.getGold() >= purchasableCompanion.getCost())
            {
                player.setGold(player.getGold() - purchasableCompanion.getCost());
                player.getCompanions().add(purchasableCompanion);
                purchasableCompanion.ApplyModifier(player);
            }else {
                System.out.println("Not enough gold!");
            }
        }

        StartingShop startingShop = new StartingShop();
        renderManager.RenderShopInventory(startingShop.getVendorInventory().getSlots(), player);
        System.out.println(player.getCompanions().toString());

        player.ActivateSages();

        player.setGold(1000);

        Preparation(startingShop);

        //CreateNewMap();
    }

    private void Preparation(StartingShop startingShop) {
        //renderManager.RenderShopInventory(startingShop.getVendorSlots(), player);

        String[] playerInputWords;
        do{
            System.out.println("Continue to map? (y to continue)");
            String playerInput = scanner.nextLine();
            playerInputWords = playerInput.split(" ");

            if(playerInputWords[0].equals("y")) CreateNewMap();

            else if(playerInputWords[0].equals("buy")) {
                BuyFromShop(startingShop, playerInputWords);
                Preparation(startingShop);
            }
            renderManager.RenderShopInventory(startingShop.getVendorInventory().getSlots(), player);
            System.out.println(player.getCompanions().toString());
        } while (!playerInputWords[0].equals("y"));
    }

    private void BuyFromShop(AbstractShop shop, String[] playerInputWords) {
        for(Slot slot : shop.getVendorInventory().getSlots()){
            if(slot.getName().equals(playerInputWords[1]) && player.getGold() > 0){
                if(playerInputWords.length > 2){
                    try{
                        for (int i = 0; i < Integer.parseInt(playerInputWords[2]); i++){
                            player.getInventory().addItem(slot.getHeldItem());
                            player.setGold(player.getGold() - slot.getHeldItem().getBuyCost());
                            slot.decreaseHeldCount();
                        }

                    } catch (NullPointerException e){
                        System.out.println("I don't have any more");
                    }
                } else{

                    player.getInventory().addItem(slot.getHeldItem());
                    player.setGold(player.getGold() - slot.getHeldItem().getBuyCost());
                    slot.decreaseHeldCount();
                }

            }

        }
        renderManager.RenderShopInventory(shop.getVendorInventory().getSlots(), player);
        System.out.println(player.getCompanions().toString());
    }


    public void Update(){


        if(standingOnTile.getSymbol() == 'J'){
            for(Slot slot : player.getInventory().getSlots()) {
                if (slot.getName().equals("machete")) {
                    slot.decreaseHeldCount();
                    standingOnTile = new Ground();
                    return;
                }
            }
            player.setEnergy(player.getEnergy() - (1 + player.getInventory().getOverCommitmentPenalty() + (player.getCompanions().size() * 0.15f)));
        }

        System.out.println("What would you like to do?");
        String playerInput = scanner.nextLine();

        String[] playerInputWords = playerInput.split(" ");

        if(playerInputWords.length < 2){
            renderManager.RenderMap(HEIGHT,WIDTH,map);
            System.out.println("Invalid action");
            System.out.println("Actual tile: " + standingOnTile.getSymbol());
            Update();
        }



        OutOfEnergy();
        UpdateWithravalStatus();

        switch (playerInputWords[0]) {
            case "move":
                if(isValidAction(playerInputWords)) ExecuteMoveAction(playerInputWords[1]);
                else {
                    renderManager.RenderMap(HEIGHT, WIDTH, map);
                    System.out.println("Invalid action");
                    System.out.println("Actual tile: " + standingOnTile.getSymbol());
                }
                break;
            case "show":
                ExecuteShowAction(playerInputWords[1]);
                break;
            case "buy":
                if (standingOnTile.getSymbol() == 'V') BuyFromShop(((Village) standingOnTile).getVillageShop(), playerInputWords);
                break;
            case "use":
                UseEnergyItem(playerInputWords);
                break;
            case "sell":
                if(standingOnTile.getSymbol() == 'V'){
                    for (Slot slot : player.getInventory().getSlots()){
                        if(slot.getName().equals(playerInputWords[1])){
                            player.setGold( player.getGold() + slot.getHeldItem().getSellCost());
                            ((Village)standingOnTile).getVillageShop().getVendorInventory().addItem(slot.getHeldItem());
                            slot.decreaseHeldCount();
                        }
                    }
                    renderManager.RenderShopInventory(((Village)standingOnTile).getVillageShop().getVendorInventory().getSlots(), player);
                }
                break;
            case "go":
                if(playerInputWords[1].equals("home"))VisitMuseum();


            if(player.getEnergy() > 100) player.setEnergy(100);

            if(standingOnTile.getSymbol() == 'P'){
                System.out.println("You've found the Golden Pyramid!");
                player.setFame(player.getFame() + 1000);
                player.getInventory().addItem(new Treasure());
                System.out.println("Do you wish to go to the next expedition? (y/n)");
                String answer = scanner.nextLine();
                if(answer.equals("y")) VisitMuseum();
                player.setFoundPyramid(true);
            }
        }
        System.out.println("Player energy: " + String.format("%.2f",player.getEnergy()));
        Update();

    }

    private void UseEnergyItem(String[] playerInputWords) {
        List<Slot> slots = player.getInventory().getSlots();
        Collections.reverse(slots);
        for (Slot slot : slots) {
            if (slot.getName().equals(playerInputWords[1])) {
                if (playerInputWords[1].equals("whiskey")) {
                    player.increaseEnergy(((AbstractFoodItem) slot.getHeldItem()).getEnergyAmount() + player.getWhiskeyBonus());

                    // We have companions
                    if (randomManager.BecomesAddicted() && player.getCompanions().size() > 0 && whiskeyDrank > 1) {
                        int randomCompanion;
                        do{
                            randomCompanion = randomManager.RandomCompanion(player.getCompanions().size());
                        } while(!player.getCompanions().get(randomCompanion).isAddictedToWhiskey());
                        player.getCompanions().get(randomCompanion).setAddictedToWhiskey(true);
                        System.out.println("Oh no! " + player.getCompanions().get(randomCompanion).toString() + " became addicted to whiskey!");
                    }
                    // We don't have companions
                    else if(randomManager.BecomesAddicted()) player.setAddictedToWhiskey(true);

                    slot.decreaseHeldCount();
                    Collections.reverse(slots);
                    ResetWhiskeyWithraval();
                    whiskeyDrank++;
                    break;

                } else if (playerInputWords[1].equals("drug")) {
                    player.increaseEnergy(((AbstractFoodItem) slot.getHeldItem()).getEnergyAmount() + player.getDrugBonus());
                        if (randomManager.BecomesAddicted() && player.getCompanions().size() > 0 && drugUsed > 1) {
                            int randomCompanion;
                            do{
                                randomCompanion = randomManager.RandomCompanion(player.getCompanions().size());
                            } while(!player.getCompanions().get(randomCompanion).isAddictedToDrug());
                            player.getCompanions().get(randomCompanion).setAddictedToDrug(true);
                            System.out.println("Oh no! " + player.getCompanions().get(randomCompanion).toString() + " became addicted to drugs!");
                        }
                        else if(randomManager.BecomesAddicted()) player.setAddictedToWhiskey(true);
                    slot.decreaseHeldCount();
                    Collections.reverse(slots);
                    ResetDrugWithraval();
                    drugUsed++;
                    break;
                } else {
                    player.increaseEnergy(((AbstractFoodItem) slot.getHeldItem()).getEnergyAmount());
                    slot.decreaseHeldCount();
                    Collections.reverse(slots);
                    whiskeyDrank = 0;
                    drugUsed = 0;
                    break;
                }
            }
        }
    }

    private void OutOfEnergy() {

        //TODO: companions should always leave first

        if(player.getEnergy() <= 0){
            boolean isLeaving = randomManager.LeaveEvent();

            if(isLeaving && player.getCompanions().size() > 0){
                int randomCompanion = randomManager.RandomCompanion(player.getCompanions().size());
                System.out.println(player.getCompanions().get(randomCompanion) + " has left the party");
                player.getCompanions().get(randomCompanion).DestroyModifier(player);
                player.getCompanions().remove(player.getCompanions().get(randomCompanion));

            } else if(isLeaving) {
                System.out.println("Game Over");
                System.exit(0);
            }
        }

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

        //AbstractCompanion startingCompanion = randomManager.GenerateCompanion(standingOnTile);

        Explore();

        renderManager = new RenderManager();
        renderManager.RenderMap(HEIGHT,WIDTH,map);

        // TMP

        Update();


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
                if(standingOnTile.getSymbol() == 'V') {
                    renderManager.RenderVillageCompanion(standingOnTile,player);

                } else { System.out.println("We don't want to offer you anyone outsider");}
                break;
            case "shop":
                if(standingOnTile.getSymbol() == 'V') {
                    Village village = (Village) standingOnTile;
                    renderManager.RenderShopInventory(village.getVillageShop().getVendorInventory().getSlots(), player);
                }
                break;
            case "map":
                renderManager.RenderMap(HEIGHT,WIDTH,map);
                break;

            case "inventory":
                renderManager.RenderInventory(player.getInventory().getSlots());
                System.out.println(player.getCompanions().toString());
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

                default:
                    return false;
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
                    } catch (ArrayIndexOutOfBoundsException ignored){
                    }
                }
            }
    }

    private void UpdateWithravalStatus(){
        for (AbstractCompanion companion : player.getCompanions()){
            if (companion.getWhiskeyWithrawal() > 30 || companion.getDrugWithrawal() > 30){
                if(randomManager.random.nextInt(10) == 0){
                    player.getCompanions().remove(companion);
                }
            }
            if (companion.isAddictedToWhiskey()) companion.setWhiskeyWithrawal(companion.getWhiskeyWithrawal() + 1);
            if (companion.isAddictedToDrug()) companion.setDrugWithrawal( companion.getDrugWithrawal() + 1);
        }
    }

    private void ResetWhiskeyWithraval(){
        for (AbstractCompanion companion : player.getCompanions()){
            companion.setWhiskeyWithrawal(0);
        }
    }

    private void ResetDrugWithraval(){
        for (AbstractCompanion companion : player.getCompanions()){
            companion.setDrugWithrawal(0);
        }
    }

    private void VisitMuseum(){

        rivals.replaceAll((n, v) -> rivals.get(n) + randomManager.random.nextInt(1500));

        for(String rival : rivals.keySet()){
            System.out.println( rival + " has " + rivals.get(rival) + " fame");
        }

        System.out.println("Your fame: "+ player.getFame());

        for (Slot slot : player.getInventory().getSlots()){
            try{
                if (slot.getName().equals("treasure")){
                    System.out.println("Do you wish to sell (s) a treasure for 250 gold, \ndonate (d) for 250 fame or \nkeep (k) the treaure?");
                    String answer = scanner.nextLine();
                    switch (answer){
                        case "s":
                            player.getInventory().removeItem(slot.getHeldItem());
                            player.setGold(player.getGold() + 250);
                            break;
                        case "d":
                            player.getInventory().removeItem(slot.getHeldItem());
                            player.setFame(player.getFame() + 250);
                            break;
                        case "k":
                    }
                }
            } catch (NullPointerException ignored) {
            }
        }
        StartLevel();
    }

}
