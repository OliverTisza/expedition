package com.company.Manager;

import com.company.Player.Player;
import com.company.Tiles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {

    private final int HEIGHT = 10;
    private final int WIDTH = 20;

    private AbstractTileObject[][] map = new AbstractTileObject[HEIGHT][WIDTH];
    private RandomManager randomManager = new RandomManager();
    private Random random = new Random();

    private Player player;


    public void CreateNewMap(){

        int seaRowPos = random.nextInt(HEIGHT);
        int seaColPos = random.nextInt(WIDTH/2);
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






    }

    public void RenderMap(){

        for(int i = 0; i < HEIGHT; i++){
            for (int j = 0; j < WIDTH; j++){

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
    }


}
