package com.company.Manager;

import com.company.Companions.AbstractCompanion;
import com.company.Companions.Scout;
import com.company.Items.Food.AbstractFoodItem;
import com.company.Items.Food.Drug;
import com.company.Items.Food.Whiskey;
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

    /**
     * Ajánl egy csapattársat, majd továbbléptet a boltba vásárolni az expedíció előtt
     */
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

        System.out.println("Valid actions unless specified otherwise. (you can view these when you are on an expedition by typing 'show help' ");
        renderManager.RenderHelp();

        // TODO: For testing
        //player.setGold(1000);
        //player.getInventory().addItem(new Treasure());
        //player.getInventory().addItem(new Treasure());
        player.getCompanions().add(new Scout());
        player.getCompanions().get(player.getCompanions().size() - 1).ApplyModifier(player);
        for(int i = 0; i < 30; i++){
            player.getInventory().addItem(new Drug());
        }
        // TODO: Testing end


        StartingShop startingShop = new StartingShop();
        renderManager.RenderShopInventory(startingShop.getVendorInventory().getSlots(), player);
        System.out.println(player.getCompanions().toString());

        player.ActivateSages();

        Preparation(startingShop);

        //CreateNewMap();
    }

    /**
     * A felfedezés előtti vásárlás
     * @param startingShop a felfedezés kezdete előtti bolt
     */
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
            System.out.println("Valid actions unless specified otherwise. (you can view these when you are on an expedition by typing 'show help' ");
            renderManager.RenderHelp();
        } while (!playerInputWords[0].equals("y"));
    }

    /**
     * Tárgyak vásárlása eladása a bolt és a játékos tárhelyeiről
     * @param shop a bolt ahonnan vásárolunk
     * @param playerInputWords a játékos konzolra beírt kulcsszavai
     */
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

    /**
     * Az egy kör eltelésével szükséges adatok frissítése
     */
    public void Update(){
        //TODO TEST
        System.out.println(player.getCompanions().get(0).getDrugWithrawal());
        //TODO END OF TEST

        if(standingOnTile.getSymbol() == 'J') JungleCondition();

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
                if(standingOnTile.getSymbol() == 'V')SellToShop(playerInputWords);
                break;
            case "go":
                if(playerInputWords[1].equals("home"))VisitMuseum();

        }
        if(player.getEnergy() > 100) player.setEnergy(100);
        if(standingOnTile.getSymbol() == 'P') PyramidFound();
        System.out.println("Player energy: " + String.format("%.2f",player.getEnergy()));
        Update();
    }

    /**
     * A Dzsungel feltételei, ha van macsétánk levágjuk, egyébként több energia mozogni
     */
    private void JungleCondition() {
        for(Slot slot : player.getInventory().getSlots()) {
            if (slot.getName().equals("machete")) {
                slot.decreaseHeldCount();
                standingOnTile = new Ground();
                return;
            }
        }
        player.setEnergy(player.getEnergy() - (1 + player.getInventory().getOverCommitmentPenalty() + (player.getCompanions().size() * 0.15f)));
    }

    /**
     * Megtaláltuk a pályán a piramist, megkapjuk az ezzel járó jutalmakat
     */
    private void PyramidFound() {
        System.out.println("You've found the Golden Pyramid!");
        player.setFame(player.getFame() + 1000);
        player.getInventory().addItem(new Treasure());
        System.out.println("Do you wish to go to the next expedition? (y/n)");
        String answer = scanner.nextLine();
        if(answer.equals("y")) VisitMuseum();
        player.setFoundPyramid(true);
    }

    /**
     * A játékos elad egy tárgyat a boltnak
     * @param playerInputWords az eladni kívánt tárgy
     */
    private void SellToShop(String[] playerInputWords) {
        for (Slot slot : player.getInventory().getSlots()){
            if(slot.getName().equals(playerInputWords[1])){
                player.setGold( player.getGold() + slot.getHeldItem().getSellCost());
                ((Village)standingOnTile).getVillageShop().getVendorInventory().addItem(slot.getHeldItem());
                slot.decreaseHeldCount();
            }
        }
        renderManager.RenderShopInventory(((Village)standingOnTile).getVillageShop().getVendorInventory().getSlots(), player);
    }

    /**
     * Energianövelés annyival amennyi energiát ad a játékos által kívánt tárgy ad
     * @param playerInputWords a játékos által elfogyasztani kívánt tárgy kulcsszavai
     */
    private void UseEnergyItem(String[] playerInputWords) {
        List<Slot> slots = player.getInventory().getSlots();
        Collections.reverse(slots);
        for (Slot slot : slots) {
            if (slot.getName().equals(playerInputWords[1])) {
                if (playerInputWords[1].equals("whiskey")) {
                    UseWhiskey(slots, slot);
                    break;

                } else if (playerInputWords[1].equals("drug")) {
                    UseDrug(slots, slot);
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

    /**
     * Drog használata energianövelésre
     * @param slots a játékos tárhelyének rekeszei
     * @param slot az aktuálisan vizsgált rekesz
     */
    private void UseDrug(List<Slot> slots, Slot slot) {
        player.increaseEnergy(((AbstractFoodItem) slot.getHeldItem()).getEnergyAmount() + player.getDrugBonus());
        if (randomManager.BecomesAddicted() && player.getCompanions().size() > 0 && drugUsed > 1 ) {
            int randomCompanion;
            int tries = 10;
            do{
                randomCompanion = randomManager.RandomCompanion(player.getCompanions().size());
                tries--;
                if(tries == 0) break;
            } while(player.getCompanions().get(randomCompanion).isAddictedToDrug());
            if(!player.getCompanions().get(randomCompanion).isAddictedToDrug()) System.out.println("Oh no! "+ player.getCompanions().get(randomCompanion) + " became addicted to drugs!");
            player.getCompanions().get(randomCompanion).setAddictedToDrug(true);

        }
        else if(randomManager.BecomesAddicted() && drugUsed > 1 && !player.isAddictedToDrug()){
            player.setAddictedToWhiskey(true);
            System.out.println("Oh no! You became addicted to drugs!");

        }
        slot.decreaseHeldCount();
        Collections.reverse(slots);
        ResetDrugWithraval();
        drugUsed++;
        //return;
    }

    /**
     * Drog használata energianövelésre
     * @param slots a játékos tárhelyének rekeszei
     * @param slot az aktuálisan vizsgált rekesz
     */

    private void UseWhiskey(List<Slot> slots, Slot slot) {
        player.increaseEnergy(((AbstractFoodItem) slot.getHeldItem()).getEnergyAmount() + player.getWhiskeyBonus());

        // We have companions
        if (randomManager.BecomesAddicted() && player.getCompanions().size() > 0 && whiskeyDrank > 1) {
            int randomCompanion;
            int tries = 10;
            do{
                randomCompanion = randomManager.RandomCompanion(player.getCompanions().size());
                tries--;
                if(tries == 0) break;
            } while(player.getCompanions().get(randomCompanion).isAddictedToWhiskey());
            if(!player.getCompanions().get(randomCompanion).isAddictedToWhiskey()) System.out.println("Oh no! "+ player.getCompanions().get(randomCompanion) + " became addicted to whiskey!");
            player.getCompanions().get(randomCompanion).setAddictedToWhiskey(true);
        }
        // We don't have companions
        else if(randomManager.BecomesAddicted() && drugUsed > 1 && !player.isAddictedToWhiskey()){
            System.out.println("Oh no! You became addicted to whiskey!");
            player.setAddictedToWhiskey(true);
        }

        slot.decreaseHeldCount();
        Collections.reverse(slots);
        ResetWhiskeyWithraval();
        whiskeyDrank++;
        //return;
    }

    /**
     * Ellenőrzés, hogy kifogyott-e a játékos energiából és a megfelelő módosítások végrehajtása, annak eldöntése, hogy elhagyja-e valaki a csapatot
     */
    private void OutOfEnergy() {

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

    /**
     * Új pálya generálása, inicializálása
     */
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

    /**
     * A piramis, hajó és játékos lehelyezése a legenerál, véletlenszerűsített pályára
     * @param seaRowPos a tenger végpontjának sorindexe
     * @param seaColPos a tenger végpontjának oszlopindexe
     */
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

    /**
     * A pálya szétválasztása földdé és tengerré
     * @param seaRowPos a tenger végpontjának sorindexe
     * @param seaColPos a tenger végpontjának oszlopindexe
     */
    private void CreateWaterAndGround(int seaRowPos, int seaColPos) {

        for(int i = 0; i < HEIGHT; i++){
            for (int j = 0; j < WIDTH; j++){

                if(i > seaRowPos || j > seaColPos){ map[i][j] = new Ground(); }
                else { map[i][j] = new Sea(); }
            }
        }
    }

    /**
     * A játékos által kért játékhoz tartozó elem kirajzolása
     * @param playerInputWord kulcsszavak, amelyek egyértelműsítik mit kell kirajzolni
     */
    private void ExecuteShowAction(String playerInputWord) {
        switch (playerInputWord){
            case "companion":
                if(standingOnTile.getSymbol() == 'V' && player.getRep() > 2 && randomManager.random.nextInt(5) == 0) {
                    renderManager.RenderVillageCompanion(standingOnTile,player);

                } else { System.out.println("You meet with some locals but none seem to be interested in your expedition");}
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
            case "help":
                renderManager.RenderHelp();
                break;
            case "legend":
                renderManager.RenderLegend();
                break;
        }
    }

    /**
     * A játékos mozgatása a megadott irányba, felfedezhető területek felfedezése, térkép újrarajzolása
     * @param playerInputWord kulcsszavak, amelyek egyértelműsítik melyik irányba szeretnénk lépni
     */
    private void ExecuteMoveAction(String playerInputWord) {
        map[player.getRowPos()][player.getColPos()] = standingOnTile;
        player.Move(playerInputWord);
        standingOnTile = map[player.getRowPos()][player.getColPos()];
        map[player.getRowPos()][player.getColPos()] = player;

        System.out.println("Actual tile: " + standingOnTile.getSymbol());

        Explore();
        renderManager.RenderMap(HEIGHT,WIDTH,map);
    }

    /**
     * Ellenőrzés, hogy léphet-e a játékos a megadott irányba
     * @param playerInputWords kulcsszavak, amelyek egyértelműsítik melyik irányba szeretnénk lépni
     * @return boolean, léphetünk-e az adott irányba
     */
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

    /**
     * A játékos körüli területek felfedezése
     */
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

    /**
     * Az elvonási tünetek figyelése, és végrehajtása
     */
    private void UpdateWithravalStatus(){
        for (AbstractCompanion companion : player.getCompanions()){
            if (companion.getWhiskeyWithrawal() > 30 || companion.getDrugWithrawal() > 30){
                if(randomManager.random.nextInt(10) == 0){
                    System.out.println("Due to withrawals " + companion + " has left the party");
                    companion.DestroyModifier(player);
                    player.getCompanions().remove(companion);
                }
            }
            if (companion.isAddictedToWhiskey()) companion.setWhiskeyWithrawal(companion.getWhiskeyWithrawal() + 1);
            if (companion.isAddictedToDrug()) companion.setDrugWithrawal( companion.getDrugWithrawal() + 1);
        }
    }

    /**
     * Az whiskey elvonási tünetének enyhülése
     */
    private void ResetWhiskeyWithraval(){
        for (AbstractCompanion companion : player.getCompanions()){
            companion.setWhiskeyWithrawal(0);
        }
    }

    /**
     * A drog elvonási tünetének enyhülése
     */
    private void ResetDrugWithraval(){
        for (AbstractCompanion companion : player.getCompanions()){
            companion.setDrugWithrawal(0);
        }
    }

    /**
     * A riválisok megjelenítése, a kincsek eladása, adományozása a múzeumnak
     */
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
