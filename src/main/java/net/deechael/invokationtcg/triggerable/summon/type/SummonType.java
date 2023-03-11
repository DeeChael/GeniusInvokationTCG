package net.deechael.invokationtcg.triggerable.summon.type;

import net.deechael.invokationtcg.Match;
import net.deechael.invokationtcg.Player;
import net.deechael.invokationtcg.Round;
import net.deechael.invokationtcg.triggerable.TriggerAt;

public interface SummonType {

    TriggerAt getTriggerAt();

    void trigger(Match match, Round round, Player self, Player enemy);

}
