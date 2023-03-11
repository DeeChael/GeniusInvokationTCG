package net.deechael.invokationtcg.triggerable.summon;

import net.deechael.invokationtcg.triggerable.summon.type.SummonType;

public class Summon {

    private final SummonType summonType;
    private final int restTimes;

    public Summon(SummonType type, int restTimes) {
        this.summonType = type;
        this.restTimes = restTimes;
    }

    public SummonType getSummonType() {
        return summonType;
    }

    public int getRestTimes() {
        return restTimes;
    }

    public boolean isUsable() {
        return this.restTimes > 0;
    }

}
