package com.company.Companions;

import com.company.Player.Player;

public class Donkey extends AbstractCompanion{
    @Override
    public void ApplyModifier(Player player) {
        //TODO: Inventory slot +2
        player.getInventory().setMaxSlotCountWithoutPenalty(player.getInventory().getMaxSlotCountWithoutPenalty() + 2);
    }

    @Override
    public void DestroyModifier(Player player) {
        player.getInventory().setMaxSlotCountWithoutPenalty(player.getInventory().getMaxSlotCountWithoutPenalty() - 2);
    }
}
