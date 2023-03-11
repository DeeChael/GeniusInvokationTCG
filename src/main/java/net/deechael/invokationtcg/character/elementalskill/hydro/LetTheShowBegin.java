package net.deechael.invokationtcg.character.elementalskill.hydro;

import net.deechael.invokationtcg.ElementType;
import net.deechael.invokationtcg.Match;
import net.deechael.invokationtcg.Player;
import net.deechael.invokationtcg.Round;
import net.deechael.invokationtcg.card.CostBuilder;
import net.deechael.invokationtcg.card.CostType;
import net.deechael.invokationtcg.character.elementalskill.ElementalSkill;
import net.deechael.invokationtcg.triggerable.summon.Summon;
import net.deechael.invokationtcg.triggerable.summon.type.hydro.MelodyLoop;

import java.util.HashMap;
import java.util.Map;

public class LetTheShowBegin implements ElementalSkill {

    private final static Map<CostType, Integer> COST = CostBuilder.of()
            .with(CostType.HYDRO, 3)
            .build();

    public final static LetTheShowBegin INSTANCE = new LetTheShowBegin();

    private LetTheShowBegin() {
    }

    @Override
    public Map<CostType, Integer> getCost() {
        return new HashMap<>(COST);
    }

    @Override
    public void trigger(Match match, Round round, Player self, Player enemy) {
        self.addSummon(new Summon(MelodyLoop.INSTANCE, 2));
        enemy.getCurrentCharacter().reduceHealth(1, ElementType.HYDRO);
    }

}
