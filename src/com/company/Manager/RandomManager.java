package com.company.Manager;

import com.company.Tiles.*;

import java.util.List;
import java.util.Random;

public class RandomManager {

    Random random = new Random();

    public void RandomizeGround(AbstractTileObject[][] map, int WIDTH, int HEIGHT){

        for(int i = 0; i < HEIGHT; i++){
            for (int j = 0; j < WIDTH; j++){
                int randomNumber = random.nextInt(100);
                if (map[i][j].getSymbol() == 'G'){
                    if (randomNumber > 94 && i > 3 && j < WIDTH-2 && j > 0){
                        CreateLake(i,j, map);
                    }
                    if (randomNumber > 42){ }
                    else if (randomNumber > 32){
                        map[i][j] = new Village();
                    }
                    else if (randomNumber > 30){
                        map[i][j] = new Jungle();
                    }
                    else if (randomNumber > 18){
                        map[i][j] = new Mountain();
                    }
                    else if (randomNumber > 12){
                        map[i][j] = new Cave();
                    }
                    else if (randomNumber > 6){
                        map[i][j] = new Altar();
                    }




                }
            }
        }
    }

    private void CreateLake(int row, int col, AbstractTileObject[][] map) {

        if( map[row-1][col].getSymbol() != 'S' && map[row][col-1].getSymbol() != 'S' && map[row-1][col-1].getSymbol() != 'S'){
            map[row][col] = new Lake();
            map[row][col+1] = new Lake();

            for (int i = -1; i < 2; i++){
                for (int j = -1; j < 3; j++){

                    try{
                        if(map[row+i][col+j].getSymbol() != 'l'){
                            map[row+i][col+j] = new WetGround();
                        }

                    } catch (ArrayIndexOutOfBoundsException e){
                        continue;
                    }


                }
            }

        }



    }

    public int[] GeneratePyramidLocation(int HEIGHT,int WIDTH,int seaRowPos, int seaColPos){
        int pyramidRowPos = seaRowPos + random.nextInt(HEIGHT - seaRowPos);
        int pyramidColPos = seaColPos + random.nextInt(WIDTH - seaColPos);

        return new int[]{pyramidRowPos, pyramidColPos};
    }

    public int[] GenerateShipLocation(int seaRowPos, int seaColPos){

        int edge = random.nextInt(2);

        if(edge == 0){

            return new int[]{ random.nextInt(seaRowPos), seaColPos};

        } else{
            return new int[]{ seaRowPos, random.nextInt(seaColPos)};
        }



    }



}
