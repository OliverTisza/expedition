package com.company.Companions;

import com.company.Player.Player;

public class Shaman extends AbstractCompanion{
    @Override
    public void ApplyModifier(Player player) {
        //TODO: +20% (0.2) energy from drugs
        player.setDrugBonus(player.getDrugBonus() + 0.2f);
    }
}
