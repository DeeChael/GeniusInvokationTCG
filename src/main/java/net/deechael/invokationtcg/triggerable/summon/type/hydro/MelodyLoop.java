package net.deechael.invokationtcg.triggerable.summon.type.hydro;

import net.deechael.invokationtcg.*;
import net.deechael.invokationtcg.triggerable.TriggerAt;
import net.deechael.invokationtcg.triggerable.summon.type.SummonType;

public class MelodyLoop implements SummonType {

    public static final MelodyLoop INSTANCE = new MelodyLoop();

    private MelodyLoop() {
    }

    @Override
    public TriggerAt getTriggerAt() {
        return TriggerAt.TURN_END;
    }

    @Override
    public void trigger(Match match, Round round, Player self, Player enemy) {
        for (CharacterCardIndex index : CharacterCardIndex.values())
            self.getCharacter(index).healHealth(1);
        self.getCurrentCharacter().addElementalApplication(ElementType.HYDRO);
    }

}
