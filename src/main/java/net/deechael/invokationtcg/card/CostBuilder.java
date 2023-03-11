package net.deechael.invokationtcg.card;

import java.util.HashMap;
import java.util.Map;

public final class CostBuilder {

    private final Map<CostType, Integer> costs = new HashMap<>();

    private CostBuilder() {
    }

    public CostBuilder with(CostType type, int amount) {
        this.costs.put(type, amount);
        return this;
    }

    public Map<CostType, Integer> build() {
        return this.costs;
    }

    public static CostBuilder of() {
        return new CostBuilder();
    }

}
