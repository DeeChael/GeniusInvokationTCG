package net.deechael.invokationtcg.character.elementalburst;

import net.deechael.invokationtcg.Match;
import net.deechael.invokationtcg.Player;
import net.deechael.invokationtcg.Round;
import net.deechael.invokationtcg.card.Costable;

public interface ElementalBurst extends Costable {

    void trigger(Match match, Round round, Player self, Player enemy);

}
