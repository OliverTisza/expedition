package com.company.Companions;

import com.company.Player.Player;

public class Trader extends AbstractCompanion{
    @Override
    public void ApplyModifier(Player player) {
        //TODO: cheaper buy higher sell
        player.setTraderSellModifier(player.getTraderSellModifier() + 0.1f);
    }

    @Override
    public void DestroyModifier(Player player) {
        player.setTraderSellModifier(player.getTraderSellModifier() - 0.1f);
    }
}
