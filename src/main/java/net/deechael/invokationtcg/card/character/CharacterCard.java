package net.deechael.invokationtcg.card.character;

import net.deechael.invokationtcg.Character;
import net.deechael.invokationtcg.ElementType;
import net.deechael.invokationtcg.card.Card;
import net.deechael.invokationtcg.character.WeaponType;
import net.deechael.invokationtcg.character.elementalburst.ElementalBurst;
import net.deechael.invokationtcg.character.elementalskill.ElementalSkill;
import net.deechael.invokationtcg.character.normalattack.NormalAttack;

public interface CharacterCard extends Card {

    int getMaxEnergy();

    NormalAttack getNormalAttack();

    ElementalSkill getElementalSkill();

    ElementalBurst getElementalBurst();

    Character createCharacter();

    ElementType getElementType();

    WeaponType getWeaponType();

}
