package com.company.Manager;

import com.company.Player.Player;
import com.company.Tiles.AbstractTileObject;
import com.company.Tiles.Ground;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class SaveLoadManager {

    private static final String filepath="Saves/game.save";

    /**
     * Elmenti az adott játékállást
     * @param map a teljes pálya
     * @param player a játékos
     * @param rivals a riválisok
     * @param standingOnTile az adott terület amin állt a játékos amikor a mentés történt
     * @param whiskeyDrank egymás után megivott whiskey-k száma
     * @param drugUsed egymás után fogyasztott drogok száma
     */
    public void SaveGame(AbstractTileObject[][] map, Player player, HashMap<String,Integer> rivals, AbstractTileObject standingOnTile, int whiskeyDrank, int drugUsed) {

        System.out.println("Trying to save");

        try {

            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);


            objectOut.writeObject(map);
            System.out.println("The map was succesfully written to a file");
            objectOut.writeObject(player);
            System.out.println("The player was succesfully written to a file");
            objectOut.writeObject(rivals);
            objectOut.writeObject(standingOnTile);
            objectOut.writeInt(whiskeyDrank);
            objectOut.writeInt(drugUsed);


            objectOut.close();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Betölt egy korábbi játékállást
     * @param gameManager a játékmenetért felel
     */
    public void LoadGame(GameManager gameManager) {

        System.out.println("Trying to load");
        try {

            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);


            gameManager.map = (AbstractTileObject[][]) objectIn.readObject();
            System.out.println("The map was succesfully read from a file");
            gameManager.player = (Player) objectIn.readObject();
            System.out.println("The player was succesfully read from a file");
            gameManager.rivals = (HashMap) objectIn.readObject();
            gameManager.standingOnTile = (AbstractTileObject) objectIn.readObject();
            gameManager.whiskeyDrank = objectIn.readInt();
            gameManager.drugUsed = objectIn.readInt();


            objectIn.close();
            fileIn.close();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
