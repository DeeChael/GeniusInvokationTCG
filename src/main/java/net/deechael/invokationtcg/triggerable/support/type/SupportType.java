package net.deechael.invokationtcg.triggerable.support.type;

import net.deechael.invokationtcg.Character;
import net.deechael.invokationtcg.Match;
import net.deechael.invokationtcg.Player;
import net.deechael.invokationtcg.Round;
import net.deechael.invokationtcg.character.elementalburst.ElementalBurst;
import net.deechael.invokationtcg.character.elementalskill.ElementalSkill;
import net.deechael.invokationtcg.character.normalattack.NormalAttack;
import net.deechael.invokationtcg.triggerable.support.Support;

public interface SupportType {

    Support createNewSupport();

    default void onRoundStart(Match match, Round round, Player self, Player enemy) {
    }

    default void onRoundEnd(Match match, Round round, Player self, Player enemy) {
    }

    default void onSelfNormalAttack(Match match, Round round, Player self, Player enemy, Character character, NormalAttack normalAttack) {
    }

    default void onEnemyNormalAttack(Match match, Round round, Player self, Player enemy, Character character, NormalAttack normalAttack) {
    }

    default void onSelfElementalSkill(Match match, Round round, Player self, Player enemy, Character character, ElementalSkill elementalSkill) {
    }

    default void onEnemyElementalSkill(Match match, Round round, Player self, Player enemy, Character character, ElementalSkill elementalSkill) {
    }

    default void onSelfElementalBurst(Match match, Round round, Player self, Player enemy, Character character, ElementalBurst elementalBurst) {
    }

    default void onEnemyElementalBurst(Match match, Round round, Player self, Player enemy, Character character, ElementalBurst elementalBurst) {
    }

    default void onSelfTurnEnd(Match match, Round round, Player self, Player enemy) {
    }

    default void onEnemyTurnEnd(Match match, Round round, Player self, Player enemy) {
    }

    default void onSelfSwitchCharacter(Match match, Round round, Player self, Player enemy, Character from, Character to) {
    }

    default void onEnemySwitchCharacter(Match match, Round round, Player self, Player enemy, Character from, Character to) {
    }

    default void onSelfCharacterDead(Match match, Round round, Player self, Player enemy, Character character) {
    }

    default void onEnemyCharacterDead(Match match, Round round, Player self, Player enemy, Character character) {
    }

}
