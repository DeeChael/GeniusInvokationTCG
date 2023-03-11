package net.deechael.invokationtcg.character.elementalburst.hydro;

import net.deechael.invokationtcg.CharacterCardIndex;
import net.deechael.invokationtcg.Match;
import net.deechael.invokationtcg.Player;
import net.deechael.invokationtcg.Round;
import net.deechael.invokationtcg.card.CostBuilder;
import net.deechael.invokationtcg.card.CostType;
import net.deechael.invokationtcg.character.elementalburst.ElementalBurst;

import java.util.HashMap;
import java.util.Map;

public class ShiningMiracle implements ElementalBurst {

    private final static Map<CostType, Integer> COST = CostBuilder.of()
            .with(CostType.HYDRO, 3)
            .with(CostType.ENERGY, 3)
            .build();

    public final static ShiningMiracle INSTANCE = new ShiningMiracle();

    private ShiningMiracle() {
    }

    @Override
    public Map<CostType, Integer> getCost() {
        return new HashMap<>(COST);
    }

    @Override
    public void trigger(Match match, Round round, Player self, Player enemy) {
        for (CharacterCardIndex index : CharacterCardIndex.values())
            self.getCharacter(index).healHealth(4);
    }

}
