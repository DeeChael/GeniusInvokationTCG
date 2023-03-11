package net.deechael.invokationtcg.card.action;

import net.deechael.invokationtcg.Player;
import net.deechael.invokationtcg.card.Costable;

public interface ActionCard extends Costable {

    void trigger(Player self, Player enemy);

}
