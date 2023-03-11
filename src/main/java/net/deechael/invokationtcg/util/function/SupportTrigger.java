package net.deechael.invokationtcg.util.function;

import net.deechael.invokationtcg.Character;
import net.deechael.invokationtcg.Match;
import net.deechael.invokationtcg.Player;
import net.deechael.invokationtcg.Round;
import net.deechael.invokationtcg.card.Costable;
import net.deechael.invokationtcg.triggerable.support.type.SupportType;

@FunctionalInterface
public interface SupportTrigger<T extends Costable> {

    void trigger(SupportType type, Match match, Round round, Player self, Player enemy, Character character, T t);

    @SuppressWarnings("unchecked")
    default void castAndTrigger(SupportType type, Match match, Round round, Player self, Player enemy, Character character, Costable costable) {
        this.trigger(type, match, round, self, enemy, character, (T) costable);
    }

}
