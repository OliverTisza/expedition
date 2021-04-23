package com.company.Manager;

import com.company.Player.Player;
import com.company.Tiles.*;

import java.util.Random;
import java.util.Scanner;

public class GameManager {

    private final int HEIGHT = 10;
    private final int WIDTH = 20;

    private AbstractTileObject[][] map = new AbstractTileObject[HEIGHT][WIDTH];
    private RandomManager randomManager = new RandomManager();
    private Random random = new Random();

    private Player player;
    private AbstractTileObject standingOnTile;


    public void CreateNewMap(){

        int seaRowPos = random.nextInt(HEIGHT-1);
        int seaColPos = random.nextInt(WIDTH/2+1);
        int[] uniquePos = new int[2];

        for(int i = 0; i < HEIGHT; i++){
            for (int j = 0; j < WIDTH; j++){

                if(i > seaRowPos || j > seaColPos){
                    map[i][j] = new Ground();
                } else {
                    map[i][j] = new Sea();
                }
            }
        }

        randomManager.RandomizeGround(map, WIDTH, HEIGHT);

        uniquePos = randomManager.GeneratePyramidLocation(HEIGHT,WIDTH,seaRowPos,seaColPos);
        map[uniquePos[0]][uniquePos[1]] = new Pyramid();
        System.out.println("Pyramid location: "+uniquePos[0] +" "+ uniquePos[1]);


        uniquePos = randomManager.GenerateShipLocation(seaRowPos, seaColPos);
        map[uniquePos[0]][uniquePos[1]] = new Ship(uniquePos[0], uniquePos[1]);
        System.out.println("Ship location: "+uniquePos[0] +" "+ uniquePos[1]);

        if(map[uniquePos[0]][uniquePos[1]+1].isWalkable()){
            player = new Player(uniquePos[0], uniquePos[1]+1);
            map[uniquePos[0]][uniquePos[1]+1] = player;
        } else {
            player = new Player(uniquePos[0]+1, uniquePos[1]);
            map[uniquePos[0]+1][uniquePos[1]] = player;
        }

        standingOnTile = new Ground();
        standingOnTile.setExplored(true);

        Explore();

    }



    public void RenderMap(){

        for(int i = 0; i < HEIGHT; i++){
            for (int j = 0; j < WIDTH; j++){

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

    public void Update(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Which way do you want to go?");
        String playerInput = scanner.nextLine();

        if(isValidAction(playerInput)){
            map[player.getRowPos()][player.getColPos()] = standingOnTile;
            player.Move(playerInput);
            standingOnTile = map[player.getRowPos()][player.getColPos()];
            map[player.getRowPos()][player.getColPos()] = player;

            System.out.println("Actual tile: " + standingOnTile.getSymbol());

            Explore();
            RenderMap();

            System.out.println("Player energy: " + player.getEnergy());

            Update();
        } else {
            RenderMap();
            System.out.println("Invalid action");
            System.out.println("Actual tile: " + standingOnTile.getSymbol());
            Update();

        }

    }

    public boolean isValidAction(String input){
        try {
            switch (input){
                case "up":
                    return map[player.getRowPos()-1][player.getColPos()].isWalkable();

                case "down":
                    return map[player.getRowPos()+1][player.getColPos()].isWalkable();

                case "left":
                    return map[player.getRowPos()][player.getColPos()-1].isWalkable();

                case "right":
                    return map[player.getRowPos()][player.getColPos()+1].isWalkable();

            }
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("You can't go out of bounds");
            return  false;
        }
        return  false;
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


/*
*
*
*
*
*
*
*
* */