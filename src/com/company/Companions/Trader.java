package com.company.Companions;

import com.company.Player.Player;

public class Trader extends AbstractCompanion{
    @Override
    public void ApplyModifier(Player player) {
        //TODO: cheaper buy higher sell
        player.setTraderSellModifier(player.getTraderSellModifier() + 1);
    }

    @Override
    public void DestroyModifier(Player player) {
        player.setTraderSellModifier(player.getTraderSellModifier() - 1);
    }

    public String toString() {
        return "Trader";
    }
}
