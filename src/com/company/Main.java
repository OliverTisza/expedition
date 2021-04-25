package com.company;

import com.company.Manager.GameManager;
import com.company.Player.Player;

public class Main {

    public static void main(String[] args) {

        GameManager gameManager = new GameManager();

        //gameManager.init(new Player(0,0));
        gameManager.StartLevel();

    }
}
