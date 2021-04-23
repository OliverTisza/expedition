package com.company.Companions;

import com.company.Player.Player;

public class Soldier extends AbstractCompanion{
    @Override
    public void ApplyModifier(Player player) {
        //TODO: +20% (0.2) energy from whiskey
        player.setWhiskeyBonus(player.getWhiskeyBonus() + 0.2f);
    }
}
