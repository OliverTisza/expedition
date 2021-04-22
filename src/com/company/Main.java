package com.company;

import com.company.Manager.GameManager;

public class Main {

    public static void main(String[] args) {

        GameManager gameManager = new GameManager();

        gameManager.CreateNewMap();
        gameManager.RenderMap();
        gameManager.Update();
    }
}
