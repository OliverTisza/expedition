package com.company.Companions;

import com.company.Player.Player;

public class Scout extends AbstractCompanion{
    @Override
    public void ApplyModifier(Player player) {
        //TODO: +1 vision range
        player.setVisionRange(player.getVisionRange() + 1);
    }

    @Override
    public void DestroyModifier(Player player) {
        player.setVisionRange(player.getVisionRange() - 1);
    }

    public String toString() {
        return "Scout";
    }
}
