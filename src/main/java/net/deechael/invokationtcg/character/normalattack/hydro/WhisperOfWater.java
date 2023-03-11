package net.deechael.invokationtcg.character.normalattack.hydro;

import net.deechael.invokationtcg.ElementType;
import net.deechael.invokationtcg.Match;
import net.deechael.invokationtcg.Player;
import net.deechael.invokationtcg.Round;
import net.deechael.invokationtcg.card.CostBuilder;
import net.deechael.invokationtcg.card.CostType;
import net.deechael.invokationtcg.character.normalattack.NormalAttack;

import java.util.HashMap;
import java.util.Map;

public class WhisperOfWater implements NormalAttack {

    private final static Map<CostType, Integer> COST = CostBuilder.of()
            .with(CostType.HYDRO, 1)
            .with(CostType.NONE, 2)
            .build();

    public final static WhisperOfWater INSTANCE = new WhisperOfWater();

    private WhisperOfWater() {
    }

    @Override
    public Map<CostType, Integer> getCost() {
        return new HashMap<>(COST);
    }

    @Override
    public void trigger(Match match, Round round, Player self, Player enemy) {
        enemy.getCurrentCharacter().reduceHealth(1, ElementType.HYDRO);
    }

}
