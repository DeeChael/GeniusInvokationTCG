package net.deechael.invokationtcg.card;

import java.util.Map;

public interface Costable {

    Map<CostType, Integer> getCost();

}
