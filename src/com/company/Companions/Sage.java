package com.company.Companions;

import com.company.Player.Player;

public class Sage extends AbstractCompanion{

    @Override
    public void ApplyModifier(Player player) {
        //TODO: +3 rep on new map
        player.setPlusRepFromSagesOnNextMap(player.getPlusRepFromSagesOnNextMap() + 3);
    }

    @Override
    public void DestroyModifier(Player player) {
        player.setPlusRepFromSagesOnNextMap(player.getPlusRepFromSagesOnNextMap() - 3);
    }
}
