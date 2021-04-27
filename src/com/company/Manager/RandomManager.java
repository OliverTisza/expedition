package com.company.Manager;

import com.company.Companions.*;
import com.company.Tiles.*;

import java.util.List;
import java.util.Random;

public class RandomManager {

    Random random = new Random();

    /**
     * Véletlenszerűen meghatározza a tenger határát
     * @param height a teljes pálya magassága
     * @param width a teljes pálya szélessége
     * @return int[], azt a X,Y koordinátát tárolja ameddig tenger van.
     */

    public int[] GenerateSeaPos(int height, int width){
        return new int[]{random.nextInt(height-2)+1,random.nextInt(width/2)+1};
    }

    /**
     * A szárazföldön véletlenszerűen létrehoz különböző területeket
     * @param map az eddig generált pálya
     * @param WIDTH a pálya teljes szélessége
     * @param HEIGHT a pálya teljes magassága
     */

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

    /**
     * Generál egy 2x1-es tavat amely körül nedves a föld és nem szomszédos tengerrel
     * @param row a baloldali tó terület sorindexe
     * @param col a baloldali tó terület ioszlopindexe
     * @param map az eddig generált teljes pálya
     */

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

    /**
     * Véletlenszerűen genrálja a pozícióját a pályán található piramisnak a szárazföldre
     * @param HEIGHT a teljes pálya magassága
     * @param WIDTH a teljes pálya szélessége
     * @param seaRowPos a tenger végpontját határoló pont sorindexe
     * @param seaColPos a tenger végpontját határoló pont oszlopindexe
     * @return int[] a piramis sor- és oszlopindexe
     */

    public int[] GeneratePyramidLocation(int HEIGHT,int WIDTH,int seaRowPos, int seaColPos){
        int pyramidRowPos = seaRowPos + random.nextInt(HEIGHT - seaRowPos);
        int pyramidColPos = seaColPos + random.nextInt(WIDTH - seaColPos);

        return new int[]{pyramidRowPos, pyramidColPos};
    }

    /**
     * Véletlenszerűen generálja a kezdőhajó pozícióját a pályán található tenger valamelyik határára
     * @param seaRowPos a tenger végpontját határoló pont sorindexe
     * @param seaColPos a tenger végpontját határoló pont oszlopindexe
     * @return int[], a hajó sor- és oszlopindexe
     */

    public int[] GenerateShipLocation(int seaRowPos, int seaColPos){

        int edge = random.nextInt(2);

        if(edge == 0){

            return new int[]{ random.nextInt(seaRowPos), seaColPos};

        } else{
            return new int[]{ seaRowPos, random.nextInt(seaColPos)};
        }
    }

    /**
     * Elhagyja-e valaki a csapatot
     * @return boolean, valaki elhagyja a csapatot
     */

    public boolean LeaveEvent(){

        int randomNum = random.nextInt(100);
        return randomNum < 8;
    }

    /**
     * Véletlenszerűen generál egy csapattársat attól függően hogy egy faluban vagy az expedíció kezdetén vagyunk
     * @param location melyik faluban vagyunk
     * @return AbstractCompanion, egy csapattárs
     */

    public AbstractCompanion GenerateCompanion(AbstractTileObject location){
        int randomNum = random.nextInt(3);

        switch (randomNum){
            case 0:
                if (location.getSymbol() == 'V'){ return new Scout(); }
                else { return new Soldier();}
            case 1:
                if (location.getSymbol() == 'V'){ return new Shaman();}
                else {return new Trader();}
            case 2:
                if (location.getSymbol() == 'V'){ return new Sage(); }
                else {return new Donkey();}
        }
        return null;
    }

    /**
     * Függőség bekövetkeztének eldöntése
     * @return boolean, függővé vált-e
     */

    public boolean BecomesAddicted(){
        return random.nextInt(100) < 15;
    }

    /**
     * Véletlenszerű sorszám attól függően hány csapattársunk van a csapatban
     * @param bound hány csapattársunk van a csapatban
     * @return int, választott csapattárs sorszáma
     */

    public int RandomCompanion(int bound){
        return random.nextInt(bound);
    }

}
