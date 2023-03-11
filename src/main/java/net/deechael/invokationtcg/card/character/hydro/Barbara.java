package net.deechael.invokationtcg.card.character.hydro;

import net.deechael.invokationtcg.Character;
import net.deechael.invokationtcg.ElementType;
import net.deechael.invokationtcg.card.character.CharacterCard;
import net.deechael.invokationtcg.character.WeaponType;
import net.deechael.invokationtcg.character.elementalburst.ElementalBurst;
import net.deechael.invokationtcg.character.elementalburst.hydro.ShiningMiracle;
import net.deechael.invokationtcg.character.elementalskill.ElementalSkill;
import net.deechael.invokationtcg.character.elementalskill.hydro.LetTheShowBegin;
import net.deechael.invokationtcg.character.normalattack.NormalAttack;
import net.deechael.invokationtcg.character.normalattack.hydro.WhisperOfWater;

public class Barbara implements CharacterCard {

    public final static Barbara INSTANCE = new Barbara();

    private Barbara() {
    }

    @Override
    public int getMaxEnergy() {
        return 3;
    }

    @Override
    public NormalAttack getNormalAttack() {
        return WhisperOfWater.INSTANCE;
    }

    @Override
    public ElementalSkill getElementalSkill() {
        return LetTheShowBegin.INSTANCE;
    }

    @Override
    public ElementalBurst getElementalBurst() {
        return ShiningMiracle.INSTANCE;
    }

    @Override
    public Character createCharacter() {
        return new Character(this);
    }

    @Override
    public ElementType getElementType() {
        return ElementType.HYDRO;
    }

    @Override
    public WeaponType getWeaponType() {
        return WeaponType.CATALYST;
    }

}
